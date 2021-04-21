package com.example.sharmainteriordesign.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import com.example.sharmainteriordesign.ui.model.OwnProduct
import com.example.sharmainteriordesign.ui.model.Product
@Dao
interface PersonalInterior {
    @Insert
    suspend fun ownProduct(list:List<OwnProduct>?)

    @Query("select * from OwnProduct")
    suspend fun getproduct(): List<OwnProduct>?

    @Query("delete from OwnProduct")
    suspend fun dropTable()
}