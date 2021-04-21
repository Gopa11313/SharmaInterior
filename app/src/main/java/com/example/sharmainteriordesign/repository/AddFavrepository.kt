package com.example.sharmainteriordesign.repository

import com.example.sharmainteriordesign.api.AddFavApi
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.response.AddFavResponse
import com.example.sharmainteriordesign.response.ForFavproductReponse
import com.example.sharmainteriordesign.ui.model.AddFav
import com.mindorks.framework.roomdatabaseandapi.api.MyApiRequest

class AddFavrepository: MyApiRequest(){
    val myApi= ServiceBuilder.buildServices(AddFavApi::class.java)
    suspend fun getallFavProdcut(id:String): ForFavproductReponse {
        return apiRequest {
            myApi.getAllFavProduct(ServiceBuilder.token!!,id)
        }
    }
    suspend fun AddFav(addFav: AddFav):AddFavResponse{
        return apiRequest {
            myApi.AddFavtheProduct(ServiceBuilder.token!!,addFav)
        }
    }
    suspend fun getParticularNote():AddFavResponse{
        return apiRequest {
            myApi.getParticularFavPRoduct(ServiceBuilder.token!!,ServiceBuilder.id!!)
        }
    }
    suspend fun deleteFavProduct(noteId:String):AddFavResponse{
        return apiRequest {
            myApi.deleteFavPRoduct(ServiceBuilder.token!!,noteId)
        }
    }
}