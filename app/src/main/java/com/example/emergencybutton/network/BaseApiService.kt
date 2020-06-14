package com.example.emergencybutton.network

import com.example.emergencybutton.model.EmergencyItem
import com.example.emergencybutton.model.ResponseNotification
import com.example.emergencybutton.model.UserItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface BaseApiService {
    
    @FormUrlEncoded
    @POST("user_login.php")
    fun loginRequest(
        @Field("email") email: String?,
        @Field("pass") password: String?
    ): Call<UserItem>

    @FormUrlEncoded
    @POST("user_register.php")
    fun registerRequest(
        @Field("name") nama: String?,
        @Field("number") noHp: String?,
        @Field("email") email: String?,
        @Field("pass") password: String?
    ): Call<UserItem>

    @FormUrlEncoded
    @POST("user_update.php")
    fun updateRequest(
        @Field("name") nama: String?,
        @Field("number") noHp: String?,
        @Field("email") email: String?,
        @Field("old_email") oldEmail: String?,
        @Field("pass") password: String?
    ): Call<UserItem>

    @GET("things_lost_get_all.php")
    fun getAllLostThings() : Call<ResponseNotification>

    @FormUrlEncoded
    @POST("emergency_get_all.php")
    fun getAllEmergencies(
        @Field("lat") lat: String?,
        @Field("lng") lng: String?
    ) : io.reactivex.Observable<List<EmergencyItem>>

    @FormUrlEncoded
    @POST("emergency_insert.php")
    fun insertEmergencyRequest(
        @Field("name") name: String?,
        @Field("lat") lat: String?,
        @Field("lng") lng: String?
    ): Call<EmergencyItem>

    @FormUrlEncoded
    @POST("emergency_delete.php")
    fun deleteEmergencyRequest(
        @Field("name") name: String?
    ): Call<EmergencyItem>

    @FormUrlEncoded
    @POST("upload.php")
    fun uploadUserPhoto(
        @Field("name") name: String?,
        @Part("picture") image: RequestBody?
    ): Call<EmergencyItem>
}