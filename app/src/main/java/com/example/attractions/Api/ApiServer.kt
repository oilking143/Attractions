package com.example.attractions.Api

import com.example.attractions.Model.AttrModel.AttrModel
import com.example.attractions.Model.ThemeModel.ThemeModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import com.google.gson.JsonObject


interface ApiServer {

    @GET("Attractions/All")
    fun getAllAttractions(
        @Query("nlat") nlat: Double,
        @Query("elong") elong: Double,
        @Query("page") page: Int
    ):Call<AttrModel>

    @GET("Tours/Theme")
    fun getAllTheme(
        @Query("page") page: Int
    ):Call<ThemeModel>

}