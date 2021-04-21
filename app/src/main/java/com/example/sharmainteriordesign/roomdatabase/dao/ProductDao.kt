package com.example.sharmainteriordesign.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sharmainteriordesign.roomdatabase.entity.User
import com.example.sharmainteriordesign.ui.model.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun AddProdcut(list:List<Product>?)

    @Query("select * from Product")
    suspend fun getproduct(): List<Product>?

    @Query("delete from Product")
    suspend fun dropTable()
}