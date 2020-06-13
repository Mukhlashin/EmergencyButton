package com.example.emergencybutton.activity.forpass

import com.example.emergencybutton.model.UserResponse
import retrofit2.Response

interface LoginConstruct {
    interface View {
        fun goToHome()
        fun saveUserData()
        fun isFailure(msg : String)
        fun isSuccess(msg : String)
    }

    interface Presenter {
        fun pushLoginData(email : String, pass : String)
    }
}