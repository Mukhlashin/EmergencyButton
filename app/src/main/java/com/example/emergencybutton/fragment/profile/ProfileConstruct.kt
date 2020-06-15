package com.example.emergencybutton.fragment.profile

import android.app.AlertDialog
import okhttp3.RequestBody
import java.io.File

interface ProfileConstruct {
    interface View{
        fun showDataUser()
        fun setFotoProfile()
        fun clearLoginData(): AlertDialog?
        fun goToLogin()
        fun uploadPhotoSucces(photo: String?)
        fun onFailure(msg: String)
        fun onSuccess(msg: String)
    }
    interface Presenter{
        fun pushUserData(nama: String, number: String, email: String, oldEmail: String, pass: String)
        fun uploadUserImage(nama: String, image: File?)
    }
}