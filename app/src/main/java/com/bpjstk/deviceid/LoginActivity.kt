package com.bpjstk.deviceid


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.bpjstk.deviceid.session.AppConstans
import com.bpjstk.deviceid.session.AppConstans.key_login
import com.bpjstk.deviceid.session.RegisPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnIntent: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnIntent = findViewById(R.id.btn_login)
        btnIntent.setOnClickListener(this)
        val registrasi = findViewById<TextView>(R.id.registrasi)
        registrasi.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {

                val btn_login: Button = findViewById(R.id.btn_login)
                val email: EditText = findViewById(R.id.email)
                val password: EditText = findViewById(R.id.password)
                btn_login.setOnClickListener {
                    val message: String = email.text.toString().trim()
                    val messagee: String = password.text.toString().trim()

                    if (message.isEmpty() || messagee.isEmpty()) {
                        Toast.makeText(this, "Masukan Email dan Password", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener

                    }
                    val email = RegisPreferences.getString(this, AppConstans.key_email, "")
                    val password = RegisPreferences.getString(this, AppConstans.key_password, "")
                    if (message == email && messagee == password) {
                        val progressDialog = ProgressDialog(this)
                        progressDialog.isIndeterminate = true
                        progressDialog.setMessage("Loading...")
                        progressDialog.show()
                        RegisPreferences.saveBoolean(this,key_login, true)
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            progressDialog.hide()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this, "Pastikan Email dan Password Benar", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            R.id.registrasi -> {
                val intent = Intent(this, RegistrasiActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
