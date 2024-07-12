package com.example.retrofit

import com.example.retrofit.Models.Users
import okhttp3.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/posts")
    suspend fun getAllUsers():retrofit2.Response<Users>
}