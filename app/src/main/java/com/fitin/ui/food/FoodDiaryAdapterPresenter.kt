package com.fitin.ui.food

import com.fitin.ui.workout.deleteResponse

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDiaryAdapterPresenter (
    private val view: FoodDiaryAdapterView,
    private val api: ApiService,
    private val pref:PrefManager
) {

    init {
        view.setupListener()

    }


    fun removeFoodDiary(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.delete_food( params )
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

    fun updateFoodDiary(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.update_food( params )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.editResponse( response.body()!! )
                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }


    }

}

interface FoodDiaryAdapterView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: deleteResponse)
    fun editResponse(response: deleteResponse)
    fun viewError(msg: String)
}