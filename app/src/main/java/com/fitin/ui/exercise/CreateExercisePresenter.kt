package com.fitin.ui.exercise

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateExercisePresenter (
    private val view: CreateExerciseView,
    private val api: ApiService,
    private val pref: PrefManager
) {

    init {
        view.setupListener()

    }


    fun saveExercise(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.save_exercise( params )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.viewResponse( response.body()!! )
                    Log.e("response",response.body().toString())
                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }


    }

    fun deleteExercise(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.delete_exercise( params )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.viewResponse( response.body()!! )
                    Log.e("response",response.body().toString())
                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }


    }

}

interface CreateExerciseView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: AddResponse)
    fun viewError(msg: String)
}