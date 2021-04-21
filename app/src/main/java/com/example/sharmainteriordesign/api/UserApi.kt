package com.example.sharmainteriordesign.api

import com.example.sharmainteriordesign.response.loginResponse
import com.example.sharmainteriordesign.roomdatabase.entity.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("insert")
    suspend fun userAdd(@Body users: User): Response<loginResponse>

    @POST("user/login")
    suspend fun checkUSer(
        @Body users: User
    ):Response<loginResponse>

    @GET("get/me/{id}")
    suspend fun getMe(
        @Header("Authorization") token:String,

        @Path("id") id:String
    ):Response<loginResponse>

    @Multipart
    @PUT("user/img/image/{id}")
    suspend fun uploadUserImage(
            @Header("Authorization") token:String,
            @Path("id") id:String,
            @Part file: MultipartBody.Part
    ): Response<loginResponse>

    @PUT("android/user/update")
    suspend fun updateUser(
        @Header("Authorization") token:String,
        @Body users: User
    ):Response<loginResponse>

}