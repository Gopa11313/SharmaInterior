package com.example.sharmainteriordesign.response

import com.example.sharmainteriordesign.ui.model.ForFavProduct
import com.example.sharmainteriordesign.ui.model.Product

data class AddFavResponse (val success:Boolean?=null,
                      val data:List<ForFavProduct>?=null,
                      val msg:String?=null,
                      val id:String?=null){
}