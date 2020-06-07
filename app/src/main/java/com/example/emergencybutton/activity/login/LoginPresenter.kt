package com.example.emergencybutton.activity.forpass

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.model.UserResponse
import com.example.emergencybutton.network.BaseApiService
import org.json.JSONObject


class LoginPresenter(val view: LoginView) {

    fun postLoginData(email: String?, pass: String?){
        AndroidNetworking.post("http://192.168.64.2/emergencybutton/user_login.php")
            .addBodyParameter("email", email)
            .addBodyParameter("pass", pass)
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(UserResponse::class.java, object :
                ParsedRequestListener<UserResponse> {
                override fun onResponse(response: UserResponse?) {
                    response?.user?.let { view.saveUserData(it) }
                    Log.d("response", response?.user?.get(0)?.data?.name)
                }

                override fun onError(anError: ANError?) {

                }

            })
    }
}