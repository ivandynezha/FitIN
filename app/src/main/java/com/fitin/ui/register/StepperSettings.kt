package com.fitin.ui.register

import androidx.annotation.ColorInt
import com.aceinteract.android.stepper.StepperNavigationView

/**
 * Data class representing settings for a [StepperNavigationView] view
 *
 * @property iconColor the color of the icon in the view.
 * @property textColor the color of the text in the view.
 * @property textSize the size of the text in the view.
 * @property iconSize the size of the icon in the view.
 */
data class StepperSettings(
    @ColorInt var iconColor: Int = -1,
    @ColorInt var textColor: Int = -1,
    var textSize: Int = -1,
    var iconSize: Int = -1
)
