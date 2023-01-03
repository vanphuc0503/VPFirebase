package com.vanphuc0503.vpfirebase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.base.BaseActivity
import com.vanphuc0503.vpfirebase.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, ViewModel>() {

    override val layoutId: Int = R.layout.activity_main
    override val viewModel: ViewModel by viewModels()
    override val container: Int = R.id.container

    override fun initView() {
        controller.addOnDestinationChangedListener {controller, _, _ ->
            when(controller.currentDestination?.id) {
                 else -> {}
            }
        }
    }

    override fun initObservable() {

    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val NOTIFICATION_REQUEST_CODE = 1234
    }
}