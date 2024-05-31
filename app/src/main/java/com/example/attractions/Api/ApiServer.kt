package com.example.attractions.Api

import com.example.attractions.Model.AttrModel.AttrModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import com.google.gson.JsonObject


interface ApiServer {

    @GET("Attractions/All")
    fun getAllAttractions(
        @Header("accept") token: String,
        @Query("nlat") nlat: Double,
        @Query("elong") elong: Double,
        @Query("page") page: Int
    ):Call<AttrModel>

}