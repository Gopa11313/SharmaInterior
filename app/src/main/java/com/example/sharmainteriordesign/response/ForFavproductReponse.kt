package com.example.sharmainteriordesign.response

import com.example.sharmainteriordesign.ui.model.AddFav
import com.example.sharmainteriordesign.ui.model.ForFavProduct

data class ForFavproductReponse (
        val success:Boolean?=null,
        val msg:String?=null,
        val data:List<AddFav>?=null,
        val id:String?=null
)