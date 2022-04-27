package com.fitin.ui.video

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoPresenter (
    private val view: VideoView,
    private val api: ApiService,
    private val pref:PrefManager
) {

    init {
        view.setupListener()
        val params = HashMap<String?, String?>()
        params["keyword"] = ""
        fetchVideo(params)
    }


    fun fetchVideo(params:HashMap<String?, String?>) {

        view.viewLoading(true)
        GlobalScope.launch {
            val response = api.video( params )
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

interface VideoView {
    fun setupListener()
    fun viewLoading(loading: Boolean)
    fun viewResponse(response: VideoModel)
    fun viewError(msg: String)
}