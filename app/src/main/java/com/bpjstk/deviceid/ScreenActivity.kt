package com.bpjstk.deviceid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bpjstk.deviceid.session.RegisPreferences

class ScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)
//        session = RegisPreferences(this)
//        if(session.isRegis()){
//            var i: Intent = Intent(applicationContext, LoginActivity::class.java)
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(i)
//            finish()
//        }
    }
}