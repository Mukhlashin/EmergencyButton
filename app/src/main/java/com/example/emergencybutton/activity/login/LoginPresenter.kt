package com.example.emergencybutton.activity.forpass

import android.util.Log
import com.example.emergencybutton.model.UserResponse
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPresenter : LoginConstruct.Presenter {
    override fun pushLoginData(email: String, pass: String) {
        var mApiService: BaseApiService = UtilsApi.getAPIService()!!

        var view: LoginConstruct.View? = null

        mApiService.loginRequest(email, pass)
            .enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable?) {
                    Log.d("failure", t.toString())
                    view?.isFailure(t.toString())
                }

                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response?.isSuccessful!!) {
                        Log.d("success", response.message())
                        view?.goToHome()
                        view?.isSuccess(response.message())
                    }
                }
            })
    }
}