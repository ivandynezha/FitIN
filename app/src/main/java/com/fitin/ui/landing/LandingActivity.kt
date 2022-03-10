package com.fitin.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitin.R
import com.fitin.ui.login.LoginActivity
import com.fitin.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_landing.registerBtn
import kotlinx.android.synthetic.main.activity_landing.loginBtn


class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        registerBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}