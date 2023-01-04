package com.vanphuc0503.vpfirebase.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.vanphuc0503.vpfirebase.Constance.ANALYTICS
import com.vanphuc0503.vpfirebase.Constance.APP_CHECK
import com.vanphuc0503.vpfirebase.Constance.AUTHENTICATION
import com.vanphuc0503.vpfirebase.Constance.A_B_TESTING
import com.vanphuc0503.vpfirebase.Constance.CLOUD_FIRE_STORE
import com.vanphuc0503.vpfirebase.Constance.CRASHLYTICS
import com.vanphuc0503.vpfirebase.Constance.DYNAMIC_LINKS
import com.vanphuc0503.vpfirebase.Constance.FIREBASE_CLOUD_MESSAGING
import com.vanphuc0503.vpfirebase.Constance.FIRE_REMOTE_CONFIG
import com.vanphuc0503.vpfirebase.Constance.GOOGLE_ADMOB
import com.vanphuc0503.vpfirebase.Constance.IN_APP_MESSAGING
import com.vanphuc0503.vpfirebase.Constance.IN_APP_PURCHASE
import com.vanphuc0503.vpfirebase.Constance.ML
import com.vanphuc0503.vpfirebase.Constance.PERFORMANCE_MONITORING
import com.vanphuc0503.vpfirebase.Constance.REALTIME_DATABASE
import com.vanphuc0503.vpfirebase.Constance.STORAGE
import com.vanphuc0503.vpfirebase.Constance.TEST_LAB
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.adapter.FirebaseRecyclerView
import com.vanphuc0503.vpfirebase.adapter.FirebaseRecyclerViewListener
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentMainBinding
import com.vanphuc0503.vpfirebase.extension.navigateWithAnim

class MainFragment : BaseFragment<FragmentMainBinding, ViewModel>(),
    FirebaseRecyclerViewListener<String> {

    override var layoutID: Int = R.layout.fragment_main
    override val viewModel: ViewModel by viewModels()

    private val firebaseAdapter by lazy {
        FirebaseRecyclerView(
            R.layout.item_firebase_option,
            this
        )
    }

    override fun initView() {
        binding.apply {
            adapter = firebaseAdapter
        }
        firebaseAdapter.setData(getFirebaseOptions())
    }

    private fun getFirebaseOptions() = listOf(
        AUTHENTICATION,
        CLOUD_FIRE_STORE,
        REALTIME_DATABASE,
        STORAGE,
        FIREBASE_CLOUD_MESSAGING,
        DYNAMIC_LINKS,
        ML,
        APP_CHECK,
        PERFORMANCE_MONITORING,
        TEST_LAB,
        ANALYTICS,
        FIRE_REMOTE_CONFIG,
        A_B_TESTING,
        IN_APP_MESSAGING,
        GOOGLE_ADMOB,
        CRASHLYTICS,
        IN_APP_PURCHASE,
    )

    override fun initObservable() {

    }

    override fun onItemClick(item: String) {
        when (item) {
            AUTHENTICATION -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openLoginFirebase())
            }
            CLOUD_FIRE_STORE -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openFireStore())
            }
            REALTIME_DATABASE -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openRealtimeDatabase())
            }
            STORAGE -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openStorage())
            }
            FIREBASE_CLOUD_MESSAGING -> {

            }
            ML -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openML())
            }
            APP_CHECK -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openAppCheck())
            }
            PERFORMANCE_MONITORING -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openPerformanceMonitoring())
            }
            TEST_LAB -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openTestLab())
            }
            ANALYTICS -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openAnalytics())
            }
            FIRE_REMOTE_CONFIG -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openFireRemoteConfig())
            }
            A_B_TESTING -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openABTesting())
            }
            IN_APP_MESSAGING -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openInAppMessaging())
            }
            GOOGLE_ADMOB -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openGoogleAdmob())
            }
            DYNAMIC_LINKS -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openDynamicLink())
            }
            CRASHLYTICS -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openCrashlytics())
            }
            IN_APP_PURCHASE -> {
                findNavController().navigateWithAnim(MainFragmentDirections.openInAppPurchase())
            }
        }
    }
}