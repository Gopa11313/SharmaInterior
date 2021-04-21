package com.example.sharmainteriordesign.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sharmainteriordesign.ui.model.ForFavProduct
import com.example.sharmainteriordesign.ui.model.Product
@Dao
interface FavProductDao {
    @Insert
    suspend fun AddProdcut(list:List<ForFavProduct>?)

    @Query("select * from forfavproduct")
    suspend fun getproduct(): List<ForFavProduct>?

    @Query("delete from forfavproduct")
    suspend fun dropTable()
}