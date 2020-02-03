package com.developer.anishakd4.pilabsassignment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.anishakd4.pilabsassignment.Database.CurrencyDao
import com.developer.anishakd4.pilabsassignment.Model.MainModel
import com.developer.anishakd4.pilabsassignment.Model.ResultModel
import com.developer.anishakd4.pilabsassignment.Networking.GetCurrencyService
import kotlinx.coroutines.*

class MainActivityViewModel(val database: CurrencyDao) : ViewModel(){

    val getData = GetCurrencyService.getGetCurrencyService()

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        //onError("Exception: ${throwable.localizedMessage}")
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var news = MutableLiveData<MainModel>()
    val loadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    var news2: LiveData<List<ResultModel>>

    init {
        news2 = database.getAllCurrencies()
    }

    fun fetchCurrency(){
        refreshSlots()
    }

    fun refreshSlots(){
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            Log.i("anisham", "anisham start")
            val response = getData.getCurrencies()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    news.value = response.body()
                    Log.i("anisham", "anisham success")
                    loadError.value = null
                    loading.value = false
                }else{
                    Log.i("anisham", "anisham else")
                    onError("Error ${response.message()}")
                }
            }
        }
    }

    fun haveData(){
        loadError.value = null
        loading.value = false
    }

    fun insertIntoDb(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val value = news.value
                if(value != null){
                    for (i in value.result){
                        database.insert(i)
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        loadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        viewModelJob.cancel()
    }
}