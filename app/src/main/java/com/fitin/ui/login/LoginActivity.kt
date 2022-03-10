package com.fitin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.fitin.R
import com.fitin.databinding.ActivityLoginBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.home.HomeActivity

class LoginActivity : AppCompatActivity(), LoginView {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.e("login","Mulai Login");
        presenter = LoginPresenter (
            this,
            PrefManager(this),
            ApiClient.getService()
        )

    }

    override fun setupListener() {
        binding.login.setOnClickListener {
            Log.e("login","Mulai Login");
            val params = HashMap<String?, String?>()
            params["email"] = binding.email.text.toString()
            params["password"] =binding.password.text.toString()
            presenter.fetchLogin(
                params
            )
        }
    }

    override fun loginLoading(loading: Boolean) {
        binding.login.isEnabled = loading.not()
        when (loading ) {
            true -> binding.login.text = "Please Wait.."
            false -> binding.login.text = "Sign In"
        }
    }

    override fun loginResponse( response: LoginResponse ) {
        if (response.metadata!!.code==200) {
            Toast.makeText(applicationContext, response.metadata!!.msg, Toast.LENGTH_SHORT).show()
            presenter.saveSession( response.data!!, binding.password.text.toString() )
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(applicationContext, response.metadata!!.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun loginError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        loginLoading(false)
    }

}