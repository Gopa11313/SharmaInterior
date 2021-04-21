package com.example.sharmainteriordesign.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sharmainteriordesign.roomdatabase.entity.User


@Dao
interface UserDao {
    @Insert
    suspend fun AddUSer(user:User)

    @Query("select * from User where email=(:email) and password=(:password)")
    suspend fun checkUSer(email:String,password:String):User

    @Query("Delete from User")
    suspend fun logout()
}