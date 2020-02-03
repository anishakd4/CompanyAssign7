package com.developer.anishakd4.pilabsassignment.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developer.anishakd4.pilabsassignment.Model.ResultModel

@Database(entities = [ResultModel::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase(){

    abstract val currencyDao: CurrencyDao

    companion object{

        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(context: Context):CurrencyDatabase {
            var instance = INSTANCE

            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "currency_database").fallbackToDestructiveMigration().build()

                INSTANCE = instance
            }

            return instance
        }
    }

}