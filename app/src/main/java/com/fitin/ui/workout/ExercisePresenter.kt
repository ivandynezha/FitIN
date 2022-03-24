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


class ExercisePresenter (
    private val view: ExerciseView,
    private val api: ApiService,
    private val pref: PrefManager
) {

    init {
        view.setupListener()
        val params = HashMap<String?, String?>()
        params["keyword"] = ""
        fetchExercise(params)
        fetchCategories()
    }


    fun fetchExercise(params:HashMap<String?, String?>) {
        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.exercise( params )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.exerciseResponse( response.body()!! )

                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }
    }

    fun fetchCategories() {
        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.categories()
            Log.e("response",response.body().toString())
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.categoriesResponse( response.body()!! )
                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }
    }

}

interface ExerciseView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun exerciseResponse(response: ExerciseModel)
    fun categoriesResponse(response: CategoriesModel)
    fun viewError(msg: String)
}