package com.example.sharmainteriordesign.response

import com.example.sharmainteriordesign.ui.model.OwnProduct

data class OwnProductResponse(val success:Boolean?=null,
                              val data:List<OwnProduct>?=null,
                              val msg:String?=null,
                              val id:String?=null) {

}