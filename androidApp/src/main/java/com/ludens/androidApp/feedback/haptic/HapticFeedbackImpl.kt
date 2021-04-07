package com.ludens.androidApp.feedback.haptic

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

private const val DO_NOT_REPEAT = -1

class HapticFeedbackImpl(
    private val vibrator: Vibrator
) : HapticFeedback {

    override fun vibrateForError() {
        if (Build.VERSION.SDK_INT >= 29) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK))
        }
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    longArrayOf(90, 60, 60),
                    intArrayOf(VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE),
                    DO_NOT_REPEAT
                )
            )
        } else {
            vibrator.vibrate(longArrayOf(80, 60, 80), DO_NOT_REPEAT)
        }
    }

    override fun vibrateForSuccess() {
        if (Build.VERSION.SDK_INT >= 29) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
        }
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    longArrayOf(90),
                    intArrayOf(VibrationEffect.DEFAULT_AMPLITUDE),
                    DO_NOT_REPEAT
                )
            )
        } else {
            vibrator.vibrate(longArrayOf(80), DO_NOT_REPEAT)
        }
    }
}
