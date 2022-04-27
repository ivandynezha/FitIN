package com.fitin.ui.food

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.video.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDiaryPresenter (
    private val view: FoodDiaryView,
    private val api: ApiService,
    private val pref: PrefManager
) {

    init {
        view.setupListener()

    }


    fun fetchFoodDiary(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.food_diary( params )
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

interface FoodDiaryView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: FoodDiaryModel)
    fun viewError(msg: String)
}