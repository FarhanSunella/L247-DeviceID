package com.bpjstk.deviceid

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class RegistrasiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnIntent: Button
    private lateinit var dateEdt: EditText
    private lateinit var RegisEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        btnIntent = findViewById(R.id.btn_regis)
        btnIntent.setOnClickListener(this)
        RegisEmail = findViewById(R.id.RegisEmail)
        dateEdt = findViewById(R.id.RegisTanggalLahir)
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
                val RegisTempatLahir: EditText = findViewById(R.id.RegisTempatLahir)
                val RegisTanggalLahir: EditText = findViewById(R.id.RegisTanggalLahir)
                val RegisHandphone: EditText = findViewById(R.id.RegisHandphone)
                val RegisAlamat: EditText = findViewById(R.id.RegisAlamat)
                btn_regis.setOnClickListener {
                    val regis1: String = RegisEmail.text.toString().trim()
                    val regis2: String = RegisPassword.text.toString().trim()
                    val regis3: String = RegisKonPassword.text.toString().trim()
                    val regis4: String = RegisNama.text.toString().trim()
                    val regis5: String = RegisTempatLahir.text.toString().trim()
                    val regis6: String = RegisTanggalLahir.text.toString().trim()
                    val regis7: String = RegisHandphone.text.toString().trim()
                    val regis8: String = RegisAlamat.text.toString().trim()

                    if (regis1.isEmpty() || regis2.isEmpty() || regis3.isEmpty() || regis4.isEmpty() || regis5.isEmpty() || regis6.isEmpty() || regis7.isEmpty() || regis8.isEmpty()) {
                        Toast.makeText(this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if (regis2 != regis3) {
                        Toast.makeText(this, "Konfirmasi Password", Toast.LENGTH_SHORT).show()
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
//                            val intent4 = Intent(this@RegistrasiActivity, Profile::class.java)
//                            intent4.putExtra("NAMA", regis4)
//                            startActivity(intent4)
//
//                            val intent5 = Intent(this@RegistrasiActivity, Profile::class.java)
//                            intent5.putExtra("TEMPAT", regis5)
//                            startActivity(intent5)
//
//                            val intent6 = Intent(this@RegistrasiActivity, Profile::class.java)
//                            intent6.putExtra("TANGGAL", regis6)
//                            startActivity(intent6)
//
//                            val intent7 = Intent(this@RegistrasiActivity, Profile::class.java)
//                            intent7.putExtra("HANDPHONE", regis7)
//                            startActivity(intent7)
//
//                            val intent8 = Intent(this@RegistrasiActivity, Profile::class.java)
//                            intent8.putExtra("ALAMAT", regis8)
//                            startActivity(intent8)
                        }
                    }
                }
            }
        }
    }
}
