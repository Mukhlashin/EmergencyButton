package com.example.emergencybutton.fragment.profile

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.emergencybutton.BuildConfig
import com.example.emergencybutton.model.EditProfileModel
import com.example.emergencybutton.model.EmergencyItem
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfilePresenter(val view: ProfileConstruct.View) : ProfileConstruct.Presenter {

    var mApiService: BaseApiService = UtilsApi.getAPIService()!!

    override fun pushUserData(model: EditProfileModel, companion: EditProfileModel.Companion) {
        mApiService.updateRequest(companion.getNama(model), companion.getNumber(model), companion.getEmail(model), companion.getOldEmail(model), companion.getPassword(model))
            .enqueue(object : Callback<UserItem> {
                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                }

                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    view.onSuccess(response.message())
                }
            })
    }

    override fun uploadUserImage(model: EditProfileModel, companion: EditProfileModel.Companion) {
        AndroidNetworking.upload(BuildConfig.BASE_URL + "upload.php")
            .setPriority(Priority.HIGH)
            .addMultipartFile("image", companion.getFileGambar(model))
            .addMultipartParameter("email", companion.getOldEmail(model))
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    view.onSuccess("Berhasil upload foto")
                    Log.d("success", response.toString())
                }

                override fun onError(anError: ANError?) {
                    anError?.errorBody?.let { view.onFailure(it) }
                    anError?.errorDetail?.let { view.onFailure(it) }
                }
            })
    }
}