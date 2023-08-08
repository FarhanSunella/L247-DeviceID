package com.bpjstk.deviceid

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bpjstk.deviceid.databinding.ActivityMainBinding
import com.bpjstk.deviceid.session.AppConstans.key_alamat
import com.bpjstk.deviceid.session.AppConstans.key_foto
import com.bpjstk.deviceid.session.AppConstans.key_galeri
import com.bpjstk.deviceid.session.AppConstans.key_handphone
import com.bpjstk.deviceid.session.AppConstans.key_nama
import com.bpjstk.deviceid.session.AppConstans.key_tanggal
import com.bpjstk.deviceid.session.AppConstans.key_tempat
import com.bpjstk.deviceid.session.RegisPreferences
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    lateinit var photoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        val image1 = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.image1)
        val btn_exit = view.findViewById<Button>(R.id.btn_logout)
        val textNama = view.findViewById<TextView>(R.id.textNama)
        val textTempat = view.findViewById<TextView>(R.id.textTempat)
        val textTanggal = view.findViewById<TextView>(R.id.textTanggal)
        val textHandphone = view.findViewById<TextView>(R.id.textHandphone)
        val textAlamat = view.findViewById<TextView>(R.id.textAlamat)
        val setFoto = view.findViewById<TextView>(R.id.setFoto)

        textNama.text = RegisPreferences.getString(requireContext(), key_nama, "")
        textTempat.text = RegisPreferences.getString(requireContext(), key_tempat, "")
        textTanggal.text = RegisPreferences.getString(requireContext(), key_tanggal, "")
        textHandphone.text = RegisPreferences.getString(requireContext(), key_handphone, "")
        textAlamat.text = RegisPreferences.getString(requireContext(), key_alamat, "")
        val foto = RegisPreferences.getString(requireContext(), key_galeri, "")
        val foto1 = RegisPreferences.getString(requireContext(), key_foto, "")
        if (foto.isNotEmpty()) {
            image1?.setImageBitmap(getImageFile(foto))
        }
        if (foto1.isNotEmpty()) {
            image1?.setImageBitmap(getImageFile(foto1))
        }


        setFoto.setOnClickListener {
            cameraCheckPermission()
        }

        image1.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(requireContext())
            pictureDialog.setTitle("Pilih")
            val pictureDialogItem = arrayOf(
                "Galeri",
                "kamera"
            )
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->

                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }

            pictureDialog.show()
        }

        btn_exit?.setOnClickListener(this)
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun cameraCheckPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRotationalDialogForPermission("")
                    }

                }
            ).onSameThread().check()

//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if(intent.resolveActivity(packageManager) != null){
//            var photoFile: File? = null
//            try{
//                photoFile = createImageFile()
//            }catch(e:IOException){}
//            if(photoFile != null){
//                val photoUri = FileProvider.getUriForFile(
//                    requireContext(),"com.example.android.fileprovider",photoFile
//                )
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
//                startActivityForResult(intent,CAMERA_REQUEST_CODE)
//            }
//        }
    }
//
//    private fun createImageFile(): File? {
//        val fileName = "MyPicture"
//        val storeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        val image = File.createTempFile(
//            fileName,"jpg",storeDir
//        )
//        photoPath = image.absolutePath
//        return image
//    }


    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }
    fun saveImageToGallery(context: Context, image: Bitmap, title: String, description: String) {
        val fileName = "${title}_${
            SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()
            ).format(Date())
        }.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.TITLE, title)
            put(MediaStore.Images.Media.DESCRIPTION, description)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }

        var imageUri: String? = null
        var fos: OutputStream? = null

        try {
            val contentResolver: ContentResolver = context.contentResolver
            val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val imageUriResult = contentResolver.insert(collection, contentValues)
            imageUriResult?.let {
                imageUri = it.toString()
                fos = contentResolver.openOutputStream(it)
                fos?.use {
                    image.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos?.close()
        }

        if (imageUri != null) {
            try {
                val root = Environment.getExternalStorageDirectory().toString()
                val myDir = File("$root/saved_images")
                myDir.mkdirs()
                val file = File(myDir, fileName)
                val out = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                RegisPreferences.saveString(requireContext(), key_foto,getImagePathFromUri(requireContext(), Uri.parse(imageUri)).toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getImagePathFromUri(context: Context, imageUri: Uri): String? {
        var imagePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(imageUri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            imagePath = it.getString(columnIndex)
        }
        cursor?.close()
        return imagePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    val image1 = view?.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.image1)
                    val bitmap = data?.extras?.get("data") as Bitmap
                    image1?.rotation =90f
                    image1?.setImageBitmap(bitmap)
                    if (bitmap != null) {
                        saveImageToGallery(requireContext(), bitmap, "kamera","imageCapture")
                    }
                }

                GALLERY_REQUEST_CODE -> {
                    val image1 = view?.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.image1)
                    val imageUri = data?.data
                    image1?.setImageURI(imageUri)
                    if (imageUri != null) {
                        RegisPreferences.saveString(requireContext(), key_galeri,getPath(requireContext(),imageUri).toString())
                    }

                }
            }
        }
    }

    private fun showRotationalDialogForPermission(packageName: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "Berikan izin untuk akses kamera")

            .setPositiveButton("Menuju Setting") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_logout -> {
                    RegisPreferences.clearData(requireContext())
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri!!, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

//    fun getPath(context: Context, bitmap: Bitmap?): String {
//        var result: String? = null
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = context.contentResolver.query(bitmap!!, proj, null, null, null)
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                val column_index = cursor.getColumnIndexOrThrow(proj[0])
//                result = cursor.getString(column_index)
//            }
//            cursor.close()
//        }
//        if (result == null) {
//            result = "Not found"
//        }
//        return result
//    }

    fun getImageFile(image: String) : Bitmap {
        val imgFile = File(image)
        return BitmapFactory.decodeFile(imgFile.absolutePath)
    }
}