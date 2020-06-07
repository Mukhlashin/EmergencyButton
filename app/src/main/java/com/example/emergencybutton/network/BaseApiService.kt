package com.example.emergencybutton.network

import com.example.emergencybutton.BuildConfig.BASE_URL

interface BaseApiService {
    fun postLogin(): String{
        return BASE_URL + "user_login.php"
    }
}