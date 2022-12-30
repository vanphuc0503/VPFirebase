package com.vanphuc0503.vpfirebase.extension

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.ui.R

fun NavController.navigateWithAnim(action: NavDirections) {
    val anim = NavOptions.Builder().apply {
        setEnterAnim(R.anim.nav_default_enter_anim)
        setExitAnim(R.anim.nav_default_exit_anim)
        setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
        setPopExitAnim(R.anim.nav_default_pop_exit_anim)
    }
    this.navigate(action.actionId, null, anim.build())
}