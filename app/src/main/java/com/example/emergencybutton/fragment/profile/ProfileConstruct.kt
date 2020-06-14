package com.example.emergencybutton.fragment.profile

import android.app.AlertDialog

interface ProfileConstruct {
    interface View{
        fun showDataUser()
        fun clearLoginData(): AlertDialog?
        fun goToLogin()
        fun onFailure(msg: String)
        fun onSuccess(msg: String)
    }
    interface Presenter{
        fun pushUserData(nama: String, number: String, email: String, oldEmail: String, pass: String)
    }
}