package com.developer.anishakd4.pilabsassignment.Services

import android.app.IntentService
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.developer.anishakd4.pilabsassignment.AppWidget.NewAppWidget
import com.developer.anishakd4.pilabsassignment.Database.CurrencyDao
import com.developer.anishakd4.pilabsassignment.Database.CurrencyDatabase
import com.developer.anishakd4.pilabsassignment.Model.ResultModel
import kotlinx.coroutines.*

class LaunchService : Service() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var currencyDao: CurrencyDao? = null
    var currencies: List<ResultModel>? = null

    companion object {
        val ACTION_GETDATA = "com.anishakd4.android.widgets.click.getdata"
    }

    override fun onCreate() {
        super.onCreate()
        currencyDao = CurrencyDatabase.getInstance(this).currencyDao
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action: String? = intent.getAction();
            if (ACTION_GETDATA.equals(action)) {
                handleClickNext();
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleClickNext() {
        uiScope.launch {
            withContext(Dispatchers.IO){
                currencies = getData()
            }
            refreshAppWidget()
            stopSelf()
        }
    }

    private suspend fun getData(): List<ResultModel>?{
        return currencyDao?.getAllCurrencies2()
    }

    private fun refreshAppWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, NewAppWidget::class.java))
        for (appWidgetId in widgetIds) {
            NewAppWidget.currencies = currencies
            NewAppWidget.updateAppWidget(applicationContext, appWidgetManager, appWidgetId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelJob.cancel()
    }
}