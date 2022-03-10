package com.fitin.ui.splashscreen

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.fitin.R
import com.fitin.preferences.PrefManager
import com.fitin.ui.androidversion.AndroidVersionActivity
import com.fitin.ui.home.HomeActivity
import com.fitin.ui.landing.LandingActivity

class SplashScreenActivity : AppCompatActivity() {



    private val load_time = 2400
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        transparentStatusAndNavigation()

        Handler().postDelayed({ //setelah loading maka akan langsung berpindah ke home activity
            if (Build.VERSION.SDK_INT >= 24) {
                val pref = PrefManager(this@SplashScreenActivity);
                val login: Int= pref.getInt("is_login");
                if (login==1) {
                    val intent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val home = Intent(this@SplashScreenActivity, LandingActivity::class.java)
                    startActivity(home)
                    finish()
                }
            } else {
                val home = Intent(this@SplashScreenActivity, AndroidVersionActivity::class.java)
                startActivity(home)
                finish()
            }
        }, load_time.toLong())
    }

    private fun transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true
            )
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(
                (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION), false
            )
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}