package com.vanphuc0503.vpfirebase.fragment.login

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vanphuc0503.vpfirebase.Constance.CLIENT_WEB_ID
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentLoginFirebaseBinding
import com.vanphuc0503.vpfirebase.extension.navigateWithAnim
import com.vanphuc0503.vpfirebase.listener.LoginFirebaseListener

class LoginFirebaseFragment : BaseFragment<FragmentLoginFirebaseBinding, ViewModel>(),
    LoginFirebaseListener {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    //--------------------- OneTapGoogle -------------------------------
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    // Control whether user declined One Tap UI
    private var userDeclinedOneTap = false

    override var layoutID: Int = R.layout.fragment_login_firebase

    override fun initView() {
        binding.apply {
            listener = this@LoginFirebaseFragment
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_WEB_ID)
            .requestEmail()
            .build()

        // Initialize Firebase Auth
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        auth = Firebase.auth

        //--------------------- OneTapGoogle -------------------------------
        initOpenTapGoogle()
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
            activity?.let {
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
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            // Result returned from launching the Intent from startIntentSenderForResult(...)
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
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

    }

    override fun onClickLoginAnonymous() {

    }

    override fun onClickRegister() {

    }

    override fun onClickLoginPhone() {

    }

    override fun onClickLoginGoogle() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, REQ_ONE_TAP)
    }

    override fun onClickLoginFacebook() {

    }

    override fun onClickLoginGithub() {

    }

    companion object {
        private const val TAG = "LoginFirebaseFragment"
        const val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    }
}