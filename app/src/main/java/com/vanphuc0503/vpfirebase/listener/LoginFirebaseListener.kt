package com.vanphuc0503.vpfirebase.listener

interface LoginFirebaseListener {
    fun onClickLogin()
    fun onClickLoginAnonymous()
    fun onClickRegister()
    fun onClickLoginPhone()
    fun onClickLoginGoogle()
    fun onClickLoginFacebook()
    fun onClickLoginGithub()
}