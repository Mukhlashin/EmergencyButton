package com.example.emergencybutton.network

public object UtilsApi {

    val BASE_URL_API = "http://192.168.64.2/"

    // Mendeklarasikan Interface BaseApiService
    fun getAPIService(): BaseApiService? {
        return RetrofitClient.getClient(BASE_URL_API)?.create(BaseApiService::class.java)
    }
}