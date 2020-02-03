package com.developer.anishakd4.pilabsassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.anishakd4.pilabsassignment.Database.CurrencyDao

class MainActivityViewModelFactory(private val dataSource: CurrencyDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}