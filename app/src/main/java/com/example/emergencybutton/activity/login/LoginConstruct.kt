package com.example.emergencybutton.activity.forpass

import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.model.UserResponse

interface LoginConstruct {
    interface View {
        fun goToHome()
        fun saveUserData(data: List<UserResponse>)
        fun isFailure(msg : String)
        fun isSuccess(msg : String)
    }

    interface Presenter {
        fun pushLoginData(email : String, pass : String)
    }
}