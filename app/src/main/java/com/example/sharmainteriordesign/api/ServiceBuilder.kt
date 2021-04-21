package com.example.sharmainteriordesign.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mindorks.framework.roomdatabaseandapi.api.MyApiRequest
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder: MyApiRequest() {
//    private const val BASE_URL="http://10.0.2.2:90/"
    private const val BASE_URL="http://192.168.1.184:90/"
//    private const val BASE_URL="http://localhost:90/"
    var token:String?=null
    var id:String?=null
    var name:String?=null
    private val okhttp=OkHttpClient.Builder()
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofitBuilder=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okhttp.build())


    private val retrofit= retrofitBuilder.build()

    fun <T> buildServices(serviceType: Class<T>):T{
        return retrofit.create(serviceType)
    }
    // Load image path in Service Builder class
    fun loadImagePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/images/"
    }
}