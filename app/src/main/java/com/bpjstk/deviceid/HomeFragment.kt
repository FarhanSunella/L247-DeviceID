package com.bpjstk.deviceid

import android.app.Dialog
import android.content.ClipboardManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bpjstk.deviceid.session.AppConstans
import com.bpjstk.deviceid.session.AppConstans.key_email
import com.bpjstk.deviceid.session.RegisPreferences

class HomeFragment : Fragment() {

    private var session: RegisPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_1 = view?.findViewById<Button>(R.id.btn_1)
        val edittext = view?.findViewById<EditText>(R.id.edittext)
        val textEmail = view?.findViewById<TextView>(R.id.textEmail)
        val email = RegisPreferences.getString(requireContext(), AppConstans.key_email, "")
        val hasilEmail = "$email"
        textEmail?.text = hasilEmail

        btn_1?.setOnClickListener {
            val message: String = edittext?.text.toString().trim()
            if (message.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Kode Kantor Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showCustomDialogBox(message)
            }
        }
    }


    private fun showCustomDialogBox(message: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout)

        val btn_oke: Button = dialog.findViewById(R.id.btn_oke)
        val kodeKantor: TextView = dialog.findViewById(R.id.tvKodeKantor)
        val deviceId: TextView = dialog.findViewById(R.id.tvDeviceId)
        kodeKantor.text = message
        deviceId.text =
            Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        deviceId.setOnClickListener(View.OnClickListener {
            val cm: ClipboardManager =
                requireContext().getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            cm.setText(deviceId.getText())
            Toast.makeText(requireContext(), "Device ID Berhasil di Salin", Toast.LENGTH_SHORT)
                .show()
        })
        btn_oke.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    companion object {
        fun newInstance(email: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("EXTRA_EMAIL", email)
                }
            }
    }
}
