package com.fitin.ui.workout

import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateTemplatePresenter (
    private val view: CreateTemplateView,
    private val api: ApiService,
    private val pref: PrefManager
) {

    init {
        view.setupListener()
    }


    fun saveWorkout(params:HashMap<String?, String?>) {
        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.save_workout( params )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.viewResponse( response.body()!! )
                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }
    }

    fun saveWorkoutHistory(params:HashMap<String?, String?>) {
        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.save_workout_history( params )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.historyResponse( response.body()!! )
                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }
    }

}

interface CreateTemplateView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: WorkoutResponse)
    fun historyResponse(response: FinishModel)
    fun viewError(msg: String)
}