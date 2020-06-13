package com.example.emergencybutton.network

object UtilsApi {

    private const val BASE_URL_API = "http://emergency.unjgwjforindonesia.com/"

    // Mendeklarasikan Interface BaseApiService
    fun getAPIService(): BaseApiService? {
        return RetrofitClient.getClient(BASE_URL_API)?.create(BaseApiService::class.java)
    }
}