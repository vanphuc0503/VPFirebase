package com.vanphuc0503.vpfirebase.fragment.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentHomeBinding
import com.vanphuc0503.vpfirebase.listener.HomeFragmentListener

class HomeFragment : BaseFragment<FragmentHomeBinding, ViewModel>(), HomeFragmentListener {
    private lateinit var auth: FirebaseAuth
    override var layoutID: Int = R.layout.fragment_home
    override val viewModel: ViewModel by viewModels()

    override fun initView() {
        auth = Firebase.auth
        binding.apply {
            listener = this@HomeFragment
        }
    }

    override fun initObservable() {

    }

    override fun clickLogoutOneTap() {
        auth.signOut()
        LoginManager.getInstance().logOut()
        findNavController().navigate(HomeFragmentDirections.openLoginFirebase())
    }
}