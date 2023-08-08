package com.bpjstk.deviceid

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bpjstk.deviceid.session.AppConstans
import com.bpjstk.deviceid.session.AppConstans.key_alamat
import com.bpjstk.deviceid.session.AppConstans.key_email
import com.bpjstk.deviceid.session.AppConstans.key_handphone
import com.bpjstk.deviceid.session.AppConstans.key_konpassword
import com.bpjstk.deviceid.session.AppConstans.key_nama
import com.bpjstk.deviceid.session.AppConstans.key_password
import com.bpjstk.deviceid.session.AppConstans.key_tanggal
import com.bpjstk.deviceid.session.AppConstans.key_tempat
import com.bpjstk.deviceid.session.RegisPreferences
import java.util.Calendar

class RegistrasiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnIntent: Button
    private lateinit var dateEdt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        btnIntent = findViewById(R.id.btn_regis)
        btnIntent.setOnClickListener(this)

        dateEdt = findViewById(R.id.RegisTanggal)
        dateEdt.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val dat = (dayOfMonth.toString() + " - " + (monthOfYear + 1) + " - " + year)
                dateEdt.setText(dat)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_regis -> {
                val btn_regis: Button = findViewById(R.id.btn_regis)
                val RegisEmail: EditText = findViewById(R.id.RegisEmail)
                val RegisPassword: EditText = findViewById(R.id.RegisPassword)
                val RegisKonPassword: EditText = findViewById(R.id.RegisKonPassword)
                val RegisNama: EditText = findViewById(R.id.RegisNama)
                val RegisTempat: EditText = findViewById(R.id.RegisTempat)
                val RegisTanggal: EditText = findViewById(R.id.RegisTanggal)
                val RegisHandphone: EditText = findViewById(R.id.RegisHandphone)
                val RegisAlamat: EditText = findViewById(R.id.RegisAlamat)
                btn_regis.setOnClickListener {
                    val regis1: String = RegisEmail.text.toString().trim()
                    val regis2: String = RegisNama.text.toString().trim()
                    val regis3: String = RegisTempat.text.toString().trim()
                    val regis4: String = RegisTanggal.text.toString().trim()
                    val regis5: String = RegisHandphone.text.toString().trim()
                    val regis6: String = RegisAlamat.text.toString().trim()
                    val regis7: String = RegisPassword.text.toString().trim()
                    val regis8: String = RegisKonPassword.text.toString().trim()


                    if (regis1.isEmpty() || regis2.isEmpty() || regis3.isEmpty() || regis4.isEmpty() || regis5.isEmpty() || regis6.isEmpty() || regis7.isEmpty() || regis8.isEmpty()) {
                        Toast.makeText(this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                    }

                    val validPass = RegisPreferences.getString(this, AppConstans.key_password, "")
                    if (regis7 == validPass && regis8 == validPass) {
                        val progressDialog = ProgressDialog(this)
                        progressDialog.isIndeterminate = true
                        progressDialog.setMessage("Loading...")
                        progressDialog.show()
                        Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        }
                    if(regis7 != regis8){
                        Toast.makeText(this, "Konfirmasi Password", Toast.LENGTH_SHORT).show()
                    }

                    if (regis1.isNotEmpty() && regis2.isNotEmpty() && regis3.isNotEmpty() && regis4.isNotEmpty() && regis5.isNotEmpty() && regis6.isNotEmpty() && regis7.isNotEmpty() && regis8.isNotEmpty()) {
                        RegisPreferences.saveString(this, key_email, regis1)
                        RegisPreferences.saveString(this, key_nama, regis2)
                        RegisPreferences.saveString(this, key_tempat, regis3)
                        RegisPreferences.saveString(this, key_tanggal, regis4)
                        RegisPreferences.saveString(this, key_handphone, regis5)
                        RegisPreferences.saveString(this, key_alamat, regis6)
                        RegisPreferences.saveString(this, key_password, regis7)
                        RegisPreferences.saveString(this, key_konpassword, regis8)

                    }
                }
            }
        }
    }
}