package com.vanphuc0503.vpfirebase.fragment.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginFirebaseViewModel : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val phone = MutableLiveData("")
}