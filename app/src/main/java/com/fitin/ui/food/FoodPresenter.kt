package com.fitin.ui.food

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.video.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodPresenter (
    private val view: FoodView,
    private val api: ApiService,
    private val pref: PrefManager
) {

    init {
        view.setupListener()

    }


    fun fetchFood(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.food( params )
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

    fun saveFood(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.save_food( params )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.viewSaveResponse( response.body()!! )
                    Log.e("response",response.body().toString())
                    view.viewLoading(false)
                }
            } else {
                view.viewError("Internal Error")
            }
        }


    }

}

interface FoodView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: FoodModel)
    fun viewSaveResponse(response: FoodModel)
    fun viewError(msg: String)
}