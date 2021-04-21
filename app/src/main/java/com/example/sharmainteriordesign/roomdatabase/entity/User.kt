package com.example.sharmainteriordesign.roomdatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
        @PrimaryKey(autoGenerate = false)
        var _id:String="",
        val name:String?=null,
        val address:String?=null,
        val email:String?=null,
        val password:String?=null,
        val image:String?=null
){

}