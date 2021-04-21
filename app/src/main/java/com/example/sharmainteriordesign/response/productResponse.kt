package com.example.sharmainteriordesign.response

import com.example.sharmainteriordesign.ui.model.Product

data class productResponse(
    val success:Boolean?=null,
    val data:List<Product>?=null,
    val msg:String?=null,
    val id:String?=null
) {
}