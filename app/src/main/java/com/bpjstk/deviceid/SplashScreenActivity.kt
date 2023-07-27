package com.bpjstk.deviceid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bpjstk.deviceid.session.AppConstans
import com.bpjstk.deviceid.session.AppConstans.key_login
import com.bpjstk.deviceid.session.RegisPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val login = RegisPreferences.getBoolean(this, key_login, false)

        if (login) {
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, SPLASH_TIME_OUT)
        }else{
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, SPLASH_TIME_OUT)

        }
    }
}
