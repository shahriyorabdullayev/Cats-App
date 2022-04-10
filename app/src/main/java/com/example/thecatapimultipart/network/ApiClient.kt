package com.example.thecatapimultipart.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object ApiClient {

    var BASE_URL = "https://api.thecatapi.com/v1/"



    fun getRetrofit(): Retrofit {
        val build = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return build
    }

    fun <T> createService(service: Class<T>): T {
        return getRetrofit().create(service)
    }





}

