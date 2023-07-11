package com.bpjstk.deviceid

import android.app.Dialog
import android.content.ClipboardManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_1: Button = findViewById(R.id.btn_1)
        val edittext: EditText = findViewById(R.id.edittext)
        btn_1.setOnClickListener {
            val message: String = edittext.text.toString().trim()
            if (message.isEmpty()) {
                Toast.makeText(this, "Kode Kantor Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            } else {
                showCustomDialogBox(message)
            }
        }
    }

    private fun showCustomDialogBox(message: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout)

        val btn_oke: Button = dialog.findViewById(R.id.btn_oke)
        val kodeKantor: TextView = dialog.findViewById(R.id.tvKodeKantor)
        val deviceId: TextView = dialog.findViewById(R.id.tvDeviceId)
        kodeKantor.text = message
        deviceId.text =
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        deviceId.setOnClickListener(View.OnClickListener {
            val cm: ClipboardManager =
                this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            cm.setText(deviceId.getText())
            Toast.makeText(this, "Device ID Berhasil di Salin", Toast.LENGTH_SHORT).show()
        })
        btn_oke.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}