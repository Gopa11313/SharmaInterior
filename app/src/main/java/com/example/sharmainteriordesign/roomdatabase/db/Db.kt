package com.example.noteasap.RoomDatabase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sharmainteriordesign.roomdatabase.dao.FavProductDao
import com.example.sharmainteriordesign.roomdatabase.dao.PersonalInterior
import com.example.sharmainteriordesign.roomdatabase.dao.ProductDao
import com.example.sharmainteriordesign.roomdatabase.dao.UserDao
import com.example.sharmainteriordesign.roomdatabase.entity.User
import com.example.sharmainteriordesign.ui.model.ForFavProduct
import com.example.sharmainteriordesign.ui.model.OwnProduct
import com.example.sharmainteriordesign.ui.model.Product


@Database(
        entities = [(User::class),(Product::class),(ForFavProduct::class),(OwnProduct::class)],
        version = 6,
        exportSchema = false
)
abstract class Db:RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getProductDao():ProductDao
    abstract fun getFavDao():FavProductDao
    abstract fun getOwnproductDao():PersonalInterior
    companion object{
        @Volatile
        private var instance:Db?=null
        fun getInstance(context: Context):Db{
            if(instance==null){
                synchronized(Db::class){
                    instance=builderDatabase(context)
                }
            }
            return instance!!
        }

        private fun builderDatabase(context: Context)=Room.databaseBuilder(
                context.applicationContext,
                Db::class.java,
                "UserDatabse"
        ).build()
    }
}
