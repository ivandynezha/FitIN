package com.fitin.ui.workout

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.history.HistoryModel
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryPresenter (
    private val view: HistoryView,
    private val api: ApiService,
    private val pref:PrefManager
) {

    init {
        view.setupListener()

    }


    fun fetchHistory(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.history( params )
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

interface HistoryView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: HistoryModel)
    fun viewError(msg: String)
}