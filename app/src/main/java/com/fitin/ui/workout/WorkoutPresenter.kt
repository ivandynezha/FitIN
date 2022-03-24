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

class WorkoutPresenter (
    private val view: WorkoutView,
    private val api: ApiService,
    private val pref:PrefManager
) {

    init {
        view.setupListener()

    }


    fun fetchWorkout(params:HashMap<String?, String?>) {

            view.workoutLoading(true)
            GlobalScope.launch {
                val response = api.workout( params )
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        view.workoutResponse( response.body()!! )

//                    Log.e("response",response.body().toString())
                        view.workoutLoading(false)
                    }
                } else {
                    view.workoutError("Internal Error")
                }
            }


    }

}

interface WorkoutView {
    fun setupListener()
    fun workoutLoading(loading: Boolean)
    fun workoutResponse(response: WorkoutModel)
    fun workoutError(msg: String)
}