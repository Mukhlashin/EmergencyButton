package com.example.emergencybutton.activity.maps

import android.location.Location

interface MapsConstruct {
    interface View {
        fun showDexterPermission()
        fun buildLocationRequest()
        fun buildLocationCallback()
    }

    interface Presenter {
        fun getAllEmergencies(lastLocation: Location)
    }
}