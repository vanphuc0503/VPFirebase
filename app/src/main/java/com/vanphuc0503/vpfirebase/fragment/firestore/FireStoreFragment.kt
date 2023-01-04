package com.vanphuc0503.vpfirebase.fragment.firestore

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentFirestoreBinding

class FireStoreFragment : BaseFragment<FragmentFirestoreBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_firestore

    override fun initView() {

    }

    override fun initObservable() {

    }
}