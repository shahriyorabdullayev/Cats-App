package com.example.thecatapimultipart.network.service


import com.example.thecatapimultipart.model.Breed
import com.example.thecatapimultipart.model.Cat
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.security.Policy

interface ApiService {

    @GET("images/search?")
    fun getCatImages(@Query("page") page: Int, @Query("limit") limit: Int): Call<List<Cat>>

    @GET("breeds")
    fun getBreeds(@Query("page") page: Int, @Query("limit") limit: Int): Call<List<Breed>>

    @GET("images/search?")
    fun getSearchByBreed(@Query("breed_ids") breed_ids: String, @Query("page") page: Int, @Query("limit") limit: Int = 20): Call<List<Cat>>

    @Headers("x-api-key:878a6e62-e801-47a1-afc4-1245b3aa362e")
    @Multipart
    @POST("images/upload")
    fun uploadCatImage(@Part file: MultipartBody.Part): Call<Cat>






}