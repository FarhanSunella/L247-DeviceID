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

class ProfileFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_exit = view?.findViewById<Button>(R.id.btn_exit)
//        val textNama = view?.findViewById<TextView>(R.id.textNama)
//        val Nama = arguments?.getString("NAMA")
//        val hasilNama = "$Nama"
//        textNama?.text = hasilNama
//
//        val textTempat = view?.findViewById<TextView>(R.id.textTempat)
//        val Tempat = arguments?.getString("TEMPAT")
//        val hasilTempat = "$Tempat"
//        textTempat?.text = hasilTempat
//
//        val textTanggal = view?.findViewById<TextView>(R.id.textTanggal)
//        val Tanggal = arguments?.getString("TANGGAL")
//        val hasilTanggal = "$Tanggal"
//        textTanggal?.text = hasilTanggal
//
//        val textNoHp = view?.findViewById<TextView>(R.id.textNoHp)
//        val Nohp = arguments?.getString("NOHP")
//        val hasilNohp = "$Nohp"
//        textNoHp?.text = hasilNohp
//
//        val textAlamat = view?.findViewById<TextView>(R.id.textAlamat)
//        val Alamat = arguments?.getString("ALAMAT")
//        val hasilAlamat = "$Alamat"
//        textAlamat?.text = hasilAlamat


        btn_exit?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){
                R.id.btn_exit ->{
                    val intent2 = Intent(requireContext(),LoginActivity::class.java)
                    startActivity(intent2)
                    activity?.finish()
                }
            }
        }
    }
//    companion object {
//        fun newInstance(nama: String) =
//            ProfileFragment().apply {
//                arguments = Bundle().apply {
//                    putString("Nama", nama)
//                }
//            }
//    }
}