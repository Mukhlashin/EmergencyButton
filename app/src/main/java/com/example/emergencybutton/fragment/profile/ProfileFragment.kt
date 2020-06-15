package com.example.emergencybutton.fragment.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.login.LoginActivity
import com.example.emergencybutton.model.EditProfileModel
import com.example.emergencybutton.model.EditProfileModel.Companion.model
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), ProfileConstruct.View {

    lateinit var myPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var loginEditor: SharedPreferences.Editor

    var presenter: ProfilePresenter = ProfilePresenter(this)

    private val RC_CAMERA = 1
    val REQUEST_TAKE_PHOTO = 1
    val REQUEST_CHOOSE_PHOTO = 2

    var file: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPref = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        editor = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit()
        loginEditor = context!!.getSharedPreferences("login", Context.MODE_PRIVATE).edit()

        showDataUser()
        setFotoProfile()

        view.btn_logout.setOnClickListener {
            clearLoginData()
        }

        view.cmv_profile.setOnClickListener {
            checkCameraPermission()
            cropImageAutoSelection()
        }

        view.btn_edit_profile.setOnClickListener {

            var fileGambar: File

            if (file != null) {
                fileGambar = file as File
                var modelCompanion : EditProfileModel.Companion = EditProfileModel
                modelCompanion.setEmail(edt_profile_email.text.toString())
                modelCompanion.setNama(edt_profile_nama.text.toString())
                modelCompanion.setPassword(edt_profile_pass.text.toString())
                modelCompanion.setNumber(edt_profile_number.text.toString())
                modelCompanion.setFileGambar(fileGambar)

                presenter.pushUserData(model, modelCompanion)
                presenter.uploadUserImage(model, modelCompanion)
            } else {
                var modelCompanion : EditProfileModel.Companion = EditProfileModel
                modelCompanion.setEmail(edt_profile_email.text.toString())
                modelCompanion.setNama(edt_profile_nama.text.toString())
                modelCompanion.setPassword(edt_profile_pass.text.toString())
                modelCompanion.setNumber(edt_profile_number.text.toString())

                presenter.pushUserData(model, modelCompanion)
            }
        }
    }

    private fun cropImageAutoSelection() {
        activity?.let {
            CropImage.activity()
                .setAspectRatio(2, 2)
                .start(it)
        }
    }

    override fun showDataUser() {
        var name = myPref.getString("nama", "")
        var number = myPref.getString("nomor", "")
        var email = myPref.getString("email", "")
        var pass = myPref.getString("pass", "")
        var image = myPref.getString("image", "")

        edt_profile_nama.setText(name)
        edt_profile_number.setText(number)
        edt_profile_email.setText(email)
        edt_profile_pass.setText(pass)

    }

    override fun setFotoProfile() {
        var image = myPref.getString("image", "")
        view?.cmv_profile?.let { Glide.with(this).load(image).into(it) }
    }

    override fun clearLoginData(): AlertDialog? {
        var builder = AlertDialog.Builder(context)
        builder.setTitle("Logout")
        builder.setMessage("Apakah anda ingin logout?")
        builder.setIcon(R.drawable.ic_warning)
        builder.setPositiveButton("Ya") { dialog, which ->
            editor.remove("id")
            editor.remove("nama")
            editor.remove("nomor")
            editor.remove("email")
            editor.remove("pass")
            editor.remove("image")
            loginEditor.remove("isLogin")
            editor.apply()
            loginEditor.apply()
            goToLogin()
        }
        builder.setNegativeButton("Tidak") { dialog, which ->  }
        builder.create()
        return builder.show()
    }

    override fun goToLogin() {
        val intent = Intent(this.activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun uploadPhotoSucces(photo: String?) {
        Toast.makeText(context, photo, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            val bitmap = extras!!["data"] as Bitmap?
            //ByteArrayOutputStream bos = new ByteArrayOutputStream();
//bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            val filesDir: File = context!!.filesDir
            val imageFile = File(filesDir, "image" + ".jpg")
            val os: OutputStream
            try {
                os = FileOutputStream(imageFile)
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()
                file = imageFile
                view?.cmv_profile?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e(javaClass.simpleName, "Error writing bitmap", e)
            }
        } else if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            //try crop
            this.activity?.let { CropImage.activity(uri).setAspectRatio(1, 1).start(it) }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult? = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val imageUri: Uri = result!!.uri
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.context?.contentResolver,
                        imageUri
                    ) as Bitmap
                    val filesDir: File = context!!.filesDir
                    val imageFile = File(filesDir, "image" + ".jpg")
                    editor.putString("image", imageFile.toString())
                    val os: OutputStream
                    os = FileOutputStream(imageFile)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                    os.flush()
                    os.close()
                    this.file = imageFile
                    if (bitmap != null) {
                        view!!.cmv_profile.setImageBitmap(bitmap)
                    } else {
                        setFotoProfile()
                    }
                } catch (e: IOException) {
                    Log.e(javaClass.simpleName, "Error writing bitmap", e)
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkCameraPermission() {
        val perm = Manifest.permission.CAMERA
        if (view?.context?.let { EasyPermissions.hasPermissions(it, perm) }!!) {
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Butuh permission camera",
                RC_CAMERA,
                perm
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    fun getPicFromCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (context?.packageManager?.let { takePictureIntent.resolveActivity(it) } != null) {
            startActivityForResult(
                takePictureIntent,
                REQUEST_TAKE_PHOTO
            )
        }
    }
}
