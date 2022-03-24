package com.fitin.ui.workout

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkoutAdapterPresenter (
    private val view: WorkoutAdapterView,
    private val api: ApiService,
    private val pref:PrefManager
) {

    init {
        view.setupListener()

    }


    fun removeWorkout(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.delete_workout( params )
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

}

interface WorkoutAdapterView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: deleteResponse)
    fun viewError(msg: String)
}