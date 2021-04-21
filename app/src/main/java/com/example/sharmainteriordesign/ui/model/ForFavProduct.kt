package com.example.sharmainteriordesign.ui.model

import androidx.core.content.PermissionChecker
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForFavProduct(
        @PrimaryKey( autoGenerate = true) val primaryKey:Int=0,
        val _id:String?=null,
        val area:String?=null,
        val price:String?=null,
        val location:String?=null,
        val phNo:String?=null,
        val userId:String?=null,
        val image:String?=null
) {
}