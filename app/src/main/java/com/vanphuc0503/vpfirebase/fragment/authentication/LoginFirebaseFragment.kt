package com.vanphuc0503.vpfirebase.fragment.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vanphuc0503.vpfirebase.Constance.CLIENT_WEB_ID
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentLoginFirebaseBinding
import com.vanphuc0503.vpfirebase.extension.navigateWithAnim
import com.vanphuc0503.vpfirebase.listener.LoginFirebaseListener
import java.util.*

class LoginFirebaseFragment : BaseFragment<FragmentLoginFirebaseBinding, ViewModel>(),
    LoginFirebaseListener {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    override val viewModel: LoginFirebaseViewModel by viewModels()
    lateinit var callbackManager: CallbackManager
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var storedVerificationId: String? = ""

    //--------------------- OneTapGoogle -------------------------------
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    // Control whether user declined One Tap UI
    private var userDeclinedOneTap = false

    override var layoutID: Int = R.layout.fragment_login_firebase


    private var resultGoogleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK
                && result.data != null
            ) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                    // ...
                }
            }
        }

    private var resultGoogleOneTapLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK
                && result.data != null
            ) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    // This credential contains a googleIdToken which
                    // we can use to authenticate with FirebaseAuth
                    credential.googleIdToken?.let {
                        firebaseAuthWithGoogle(it)
                    }
                    // If the user used email/password, the credential
                    // should provide the user's email and password
                    credential.password?.let { password ->
                        signInWithPassword(credential.id, password)
                    }
                } catch (e: ApiException) {
                    when (e.statusCode) {
                        CommonStatusCodes.CANCELED -> {
                            // The user closed the dialog
                            userDeclinedOneTap = true
                        }
                        CommonStatusCodes.NETWORK_ERROR -> {
                            // No Internet connection ?
                        }
                        else -> {
                            // Some other error
                        }
                    }
                }
            }
        }

    override fun initView() {
        binding.apply {
            listener = this@LoginFirebaseFragment
            viewModel = this@LoginFirebaseFragment.viewModel
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_WEB_ID)
            .requestEmail()
            .build()

        binding.countryPicker.registerCarrierNumberEditText(binding.tvPhone)

        // Initialize Firebase Auth
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        auth = Firebase.auth

        //--------------------- OneTapGoogle -------------------------------
//        initOpenTapGoogle()
        initLoginFacebook()
    }

    private fun initLoginFacebook() {
        binding.tvFacebook.apply {
            this@LoginFirebaseFragment.callbackManager = CallbackManager.Factory.create()
            setPermissions(EMAIL, PUBLIC_PROFILE)
            setFragment(this@LoginFirebaseFragment)
            registerCallback(
                this@LoginFirebaseFragment.callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onCancel() {
                        Log.d(TAG, "facebook:onCancel")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d(TAG, "facebook:onError", error)
                    }

                    override fun onSuccess(result: LoginResult) {
                        handleFacebookAccessToken(result.accessToken)
                    }
                })
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun initOpenTapGoogle() {
        context?.let { context ->
            oneTapClient = Identity.getSignInClient(context)
            signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                    BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                )
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(CLIENT_WEB_ID)
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                )
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        currentUser?.let {
            // If currentUser != null, let's go to the next screen
            findNavController().navigateWithAnim(LoginFirebaseFragmentDirections.openHomeFragment())
        } ?: kotlin.run {
            // Check if the user has saved credentials on our app
            // and display the One Tap UI
/*            activity?.let {
                oneTapClient.beginSignIn(signInRequest)
                    .addOnSuccessListener(it) { result ->
                        try {
                            startIntentSenderForResult(
                                result.pendingIntent.intentSender, REQ_ONE_TAP,
                                null, 0, 0, 0, null
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                        }
                    }
                    .addOnFailureListener(it) { e ->
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, "No saved credentials: ${e.localizedMessage} ")
                    }
            }*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    // Sign in to an existing password account
    private fun signInWithPassword(email: String, password: String) {
        Log.e(TAG, "Signing in with email '$email' and '$password'")
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val currentUser = authResult.user
                updateUI(currentUser)
            }
            .addOnFailureListener {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", it)
                Toast.makeText(
                    requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // Create a new password account
    private fun signUpWithPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val currentUser = authResult.user
                updateUI(currentUser)
            }
            .addOnFailureListener {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", it)
                Toast.makeText(
                    requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun firebaseAuthWithGoogle(googleIdToken: String) {
        val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(
                        requireView(),
                        "Authentication Failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }

    override fun initObservable() {

    }

    override fun onClickLogin() {
        if (viewModel.email.value?.isNotEmpty() == true && viewModel.password.value?.isNotEmpty() == true) {
            signInWithPassword(
                viewModel.email.value.toString(),
                viewModel.password.value.toString()
            )
        }
    }

    override fun onClickLoginAnonymous() {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    override fun onClickRegister() {
        if (viewModel.email.value?.isNotEmpty() == true && viewModel.password.value?.isNotEmpty() == true) {
            signUpWithPassword(
                viewModel.email.value.toString(),
                viewModel.password.value.toString()
            )
        }
    }

    override fun onClickLoginPhone() {
        if (!viewModel.phone.value.isNullOrEmpty()) {
            findNavController().navigate(LoginFirebaseFragmentDirections.openOTPFragment(binding.countryPicker.fullNumberWithPlus))
        }
    }

    override fun onClickLoginGoogle() {
        signInGoogle()
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultGoogleLauncher.launch(signInIntent)
    }

    override fun onClickLoginGoogleOneTap() {
        val intent = googleSignInClient.signInIntent
        resultGoogleOneTapLauncher.launch(intent)
    }

    override fun onClickLoginGithub() {
        val provider = OAuthProvider.newBuilder("github.com")
        // Target specific email with login hint.
        provider.addCustomParameter("login", "vanphuc.earn.money@gmail.com")
        // Request read access to a user's email addresses.
        // This must be preconfigured in the app's API permissions.
        val scopes = arrayListOf<String>().apply {
            add("user:email")
        }
        provider.scopes = scopes
        val pendingResultTask = auth.pendingAuthResult
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener {
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                }
                .addOnFailureListener {
                    // Handle failure.
                }
        } else {
            auth
                .startActivityForSignInWithProvider( /* activity= */requireActivity(),
                    provider.build()
                )
                .addOnSuccessListener { results ->
                    (results.credential as? OAuthCredential)?.let { authCredential ->
                        authCredential.accessToken?.let { it -> handleGithubAccessToken(it) }
                    }
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                }
                .addOnFailureListener {
                    it.message
                    // Handle failure.
                }
        }
    }

    private fun handleGithubAccessToken(token: String) {
        val credential = GithubAuthProvider.getCredential(token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    updateUI(user)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    updateUI(null)
                }
            }
    }

    companion object {
        private const val TAG = "LoginFirebaseFragment"
        const val EMAIL = "email"
        const val PUBLIC_PROFILE = "public_profile"
    }
}