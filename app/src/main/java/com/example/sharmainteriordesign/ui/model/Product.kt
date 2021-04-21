package com.example.sharmainteriordesign.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
        @PrimaryKey(autoGenerate = false) val _id:String="",
    val area:String?=null,
    val price:String?=null,
    val location:String?=null,
    val phNo:String?=null,
    val userId:String?=null,
        val image:String?=null
    ) {
}