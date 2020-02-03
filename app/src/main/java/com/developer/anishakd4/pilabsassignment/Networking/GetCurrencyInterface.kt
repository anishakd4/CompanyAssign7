package com.developer.anishakd4.pilabsassignment.Networking

import com.developer.anishakd4.pilabsassignment.Model.MainModel
import retrofit2.Response
import retrofit2.http.GET

interface GetCurrencyInterface {

    @GET("api/v1.1/public/getcurrencies")
    suspend fun getCurrencies(): Response<MainModel>
}