package com.example.emergencybutton.fragment.home

import android.app.AlertDialog

interface HomeConstruct {
    interface View{
        fun isFailure(msg : String)
        fun isSuccess(msg : String)
        fun changeImageON()
        fun changeImageOFF()
        fun goToNotification()
        fun showDialog(): AlertDialog?
        fun showDexterPermission()
        fun buildLocationRequest()
        fun buildLocationCallback()
    }
    interface Presenter{
        fun pushEmergencyON(name: String, lat: String, lng: String)
        fun pushEmergencyOFF(name: String)
    }
}