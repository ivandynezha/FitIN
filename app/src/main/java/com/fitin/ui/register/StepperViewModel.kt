package com.fitin.ui.register

import androidx.lifecycle.ViewModel
import com.fitin.ui.register.StepperSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * View model for managing a stepper activity.
 */
@ExperimentalCoroutinesApi
class StepperViewModel : ViewModel() {

    private val _stepperSettings = MutableStateFlow(StepperSettings())
    /**
     * Public immutable accessor for [_stepperSettings].
     */
    val stepperSettings: StateFlow<StepperSettings> get() = _stepperSettings

    /**
     * Update the settings of the stepper navigation.
     *
     * @param newStepperSettings the settings to update.
     */
    fun updateStepper(newStepperSettings: StepperSettings) {
        _stepperSettings.value = newStepperSettings
    }
}
