package com.developer.anishakd4.pilabsassignment.Networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetCurrencyService {

    private val BASE_URL = "https://bittrex.com";

    fun getGetCurrencyService(): GetCurrencyInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetCurrencyInterface::class.java)
    }

}