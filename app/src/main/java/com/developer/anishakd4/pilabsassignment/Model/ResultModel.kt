package com.developer.anishakd4.pilabsassignment.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Currencies")
data class ResultModel(

    @PrimaryKey
    @ColumnInfo(name = "title")
    val Currency: String,

    @ColumnInfo(name = "CurrencyLong")
    val CurrencyLong: String,

    @ColumnInfo(name = "TxFee")
    val TxFee: Double
)