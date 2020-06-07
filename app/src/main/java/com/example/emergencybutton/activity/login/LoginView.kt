package com.example.emergencybutton.activity.forpass

import com.example.emergencybutton.model.UserItem

interface LoginView {
    fun goToHome()
    fun saveUserData(data: List<UserItem>)
}