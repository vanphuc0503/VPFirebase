package com.vanphuc0503.vpfirebase.fragment

import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.Constance.AD_MOD
import com.vanphuc0503.vpfirebase.Constance.AUTHENTICATION
import com.vanphuc0503.vpfirebase.Constance.CLOUD_FIRE_STORE
import com.vanphuc0503.vpfirebase.Constance.DYNAMIC_LINKS
import com.vanphuc0503.vpfirebase.Constance.MESSAGING
import com.vanphuc0503.vpfirebase.Constance.REALTIME_DATABASE
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.adapter.FirebaseRecyclerView
import com.vanphuc0503.vpfirebase.adapter.FirebaseRecyclerViewListener
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, ViewModel>(),
    FirebaseRecyclerViewListener<String> {

    override var layoutID: Int = R.layout.fragment_main

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
        MESSAGING,
        DYNAMIC_LINKS,
        AD_MOD,
    )

    override fun initObservable() {

    }

    override fun onItemClick(item: String) {
        when(item) {
            AUTHENTICATION -> {}
            CLOUD_FIRE_STORE -> {}
            REALTIME_DATABASE -> {}
            MESSAGING -> {}
            DYNAMIC_LINKS -> {}
            AD_MOD -> {}
        }
    }
}