package com.example.emergencybutton.fragment.profile

import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
}