package com.ludens.androidApp.navigation.extensions

import androidx.fragment.app.FragmentTransaction
import com.ludens.androidApp.R

fun FragmentTransaction.applyBottomSlideEnterAndExitAnimation() {
    setCustomAnimations(R.anim.fragment_bottom_enter, R.anim.nothing, R.anim.nothing, R.anim.fragment_bottom_exit)
}

fun FragmentTransaction.applySubtleRightEnterLeftExitAnimation() {
    setCustomAnimations(
        R.anim.slide_in_right,
        R.anim.slide_out_left,
        R.anim.slide_in_left,
        R.anim.slide_out_right
    )
}

fun FragmentTransaction.applyMaterialFadeThroughAnimation() {
    setCustomAnimations(
        R.anim.fragment_fade_in_and_scale_in,
        R.anim.fragment_fade_out_and_scale_out,
        R.anim.fragment_fade_in_and_scale_in,
        R.anim.fragment_fade_out_and_scale_out
    )
}

fun FragmentTransaction.applyRightEnterAndLeftExitAnimation() {
    setCustomAnimations(
        R.anim.fragment_right_enter,
        R.anim.fragment_left_exit,
        R.anim.fragment_left_enter,
        R.anim.fragment_right_exit
    )
}
