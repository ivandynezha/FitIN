package com.fitin.ui.login

import android.util.Log
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.*

class LoginPresenter (
    private val view: LoginView,
    private val pref: PrefManager,
    private val api: ApiService,
) {

    init {
        view.setupListener()
    }

    fun fetchLogin(params:HashMap<String?, String?>) {
        view.loginLoading(true)
        GlobalScope.launch {
            val response = api.login(params)
            Log.e("response",response.toString())
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.loginResponse( response.body()!! )
                    view.loginLoading(false)
                }
            } else {
                view.loginError("Terjadi kesalahan")
            }
        }
    }

    fun saveSession(data: LoginData, password: String){
        pref.put("is_login", 1)
        pref.put( "user_login", Gson().toJson( data ) )
        pref.put( "user_password", password )
    }

}

interface LoginView {
    fun setupListener()
    fun loginLoading(loading: Boolean)
    fun loginResponse(response: LoginResponse)
    fun loginError(msg: String)
}