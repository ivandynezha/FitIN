package com.fitin.ui.history

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.workout.deleteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryDetailPresenter (
    private val view: HistoryDetailView,
    private val api: ApiService,
    private val pref: PrefManager
) {

    init {
        view.setupListener()

    }


    fun removeHistory(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.delete_history( params )
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

interface HistoryDetailView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: deleteResponse)
    fun viewError(msg: String)
}