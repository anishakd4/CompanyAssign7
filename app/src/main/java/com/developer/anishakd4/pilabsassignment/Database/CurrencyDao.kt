package com.developer.anishakd4.pilabsassignment.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developer.anishakd4.pilabsassignment.Model.ResultModel

@Dao
interface CurrencyDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(night: ResultModel)

    @Query("SELECT * FROM Currencies")
    fun getAllCurrencies(): LiveData<List<ResultModel>>

    @Query("SELECT * FROM Currencies")
    fun getAllCurrencies2(): List<ResultModel>

}