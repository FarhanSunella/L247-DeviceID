package com.bpjstk.deviceid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.bpjstk.deviceid.session.AppConstans
import com.bpjstk.deviceid.session.AppConstans.key_alamat
import com.bpjstk.deviceid.session.AppConstans.key_email
import com.bpjstk.deviceid.session.AppConstans.key_handphone
import com.bpjstk.deviceid.session.AppConstans.key_nama
import com.bpjstk.deviceid.session.AppConstans.key_tanggal
import com.bpjstk.deviceid.session.AppConstans.key_tempat
import com.bpjstk.deviceid.session.RegisPreferences
import org.w3c.dom.Text

class ProfileFragment : Fragment(), View.OnClickListener {
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_exit = view.findViewById<Button>(R.id.btn_exit)
        val textNama =view.findViewById<TextView>(R.id.textNama)
        val textTempat = view.findViewById<TextView>(R.id.textTempat)
        val textTanggal = view.findViewById<TextView>(R.id.textTanggal)
        val textHandphone = view.findViewById<TextView>(R.id.textHandphone)
        val textAlamat = view.findViewById<TextView>(R.id.textAlamat)

        textNama.text=RegisPreferences.getString(requireContext(), key_nama, "")
        textTempat.text=RegisPreferences.getString(requireContext(), key_tempat, "")
        textTanggal.text=RegisPreferences.getString(requireContext(), key_tanggal, "")
        textHandphone.text=RegisPreferences.getString(requireContext(), key_handphone, "")
        textAlamat.text=RegisPreferences.getString(requireContext(), key_alamat, "")



        btn_exit?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){
                R.id.btn_exit ->{
                    RegisPreferences.clearData(requireContext())
                    val intent2 = Intent(requireContext(),LoginActivity::class.java)
                    startActivity(intent2)
                    activity?.finish()
                }
            }
        }
    }
}