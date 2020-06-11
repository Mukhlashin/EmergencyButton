package com.example.emergencybutton.network

import android.database.Observable
import com.example.emergencybutton.model.ResponseEmergency
import com.example.emergencybutton.model.ResponseNotification
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface BaseApiService {
    
    @FormUrlEncoded
    @POST("user_login.php")
    fun loginRequest(
        @Field("email") email: String?,
        @Field("pass") password: String?
    ): Call<UserResponse>

    @GET("things_lost_get_all.php")
    fun getAllLostThings() : Call<ResponseNotification>

    @FormUrlEncoded
    @POST("emergency_get_all.php")
    fun getAllEmergencies(
        @Field("lat") lat: String?,
        @Field("lng") lng: String?
    ) : io.reactivex.Observable<List<ResponseEmergency>>
}