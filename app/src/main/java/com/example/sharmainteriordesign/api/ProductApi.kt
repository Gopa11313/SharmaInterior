package com.example.sharmainteriordesign.api

import com.example.sharmainteriordesign.response.*
import com.example.sharmainteriordesign.ui.model.OwnProduct
import com.example.sharmainteriordesign.ui.model.Product
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {
    @POST("product/insert")
    suspend fun insertProduct(
        @Header("Authorization") token:String,
        @Body product: Product
    ):Response<productResponse>

    @GET("interior/{id}")
    suspend fun getallProduct(
            @Header("Authorization") token: String,
            @Path("id") userId:String
    ):Response<AddFavResponse>

    @Multipart
    @PUT("product/image/{id}")
    suspend fun uploadImage(
            @Header("Authorization") token:String,
            @Path("id") id:String,
            @Part file: MultipartBody.Part
    ): Response<loginResponse>


    @GET("get/products")
    suspend fun getallProduct(
            @Header("Authorization") token:String
    ):Response<productResponse>


    @GET("interior/web/{id}")
    suspend fun getInter(
            @Header("Authorization") token:String,
            @Path("id") id:String
    ):Response<OwnProductResponse>
}