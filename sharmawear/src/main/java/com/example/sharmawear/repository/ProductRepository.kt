package com.example.sharmainteriordesign.repository

import com.example.sharmainteriordesign.api.ProductApi
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.response.*
import com.example.sharmainteriordesign.ui.model.Product
import com.mindorks.framework.roomdatabaseandapi.api.MyApiRequest
import okhttp3.MultipartBody

class ProductRepository: MyApiRequest() {
    val myApi= ServiceBuilder.buildServices(ProductApi::class.java)
    suspend fun insertProduct(product: Product): productResponse {
        return apiRequest {
            myApi.insertProduct(ServiceBuilder.token!!,product)
        }
    }
suspend fun getallProduct():productResponse{
    return apiRequest {
        myApi.getallProduct(ServiceBuilder.token!!)
    }
}


    suspend fun uploadImage(id:String,file: MultipartBody.Part):loginResponse{
        return apiRequest {
            myApi.uploadImage(ServiceBuilder.token!!,id,file)
        }
    }

}