package com.example.emergencybutton.fragment.profile

import com.example.emergencybutton.model.EmergencyItem
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfilePresenter(val view: ProfileConstruct.View) : ProfileConstruct.Presenter {

    var mApiService: BaseApiService = UtilsApi.getAPIService()!!

    override fun pushUserData(nama: String, number: String, email: String, oldEmail: String, pass: String) {
        mApiService.updateRequest(nama, number, email, oldEmail, pass)
            .enqueue(object : Callback<UserItem> {
                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                }

                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    view.onSuccess(response.message())
                }
            })
    }

    override fun uploadUserImage(nama: String, image: File?) {
        mApiService.uploadUserPhoto(nama, image)
            .enqueue(object : Callback<EmergencyItem> {
                override fun onFailure(call: Call<EmergencyItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                }

                override fun onResponse(call: Call<EmergencyItem>, response: Response<EmergencyItem>) {
                    response.body()?.msg?.let { view.onSuccess(it) }
                }

            })
    }
}