package com.developer.anishakd4.pilabsassignment.AppWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.developer.anishakd4.pilabsassignment.Database.CurrencyDao
import com.developer.anishakd4.pilabsassignment.Database.CurrencyDatabase
import com.developer.anishakd4.pilabsassignment.Services.ClickIntentService
import com.developer.anishakd4.pilabsassignment.Model.ResultModel
import com.developer.anishakd4.pilabsassignment.R
import com.developer.anishakd4.pilabsassignment.Services.LaunchService


class NewAppWidget : AppWidgetProvider() {


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        val intent1 = Intent(context, LaunchService::class.java)
        intent1.action = LaunchService.ACTION_GETDATA
        context.startService(intent1)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        if (context != null) {
            if (appWidgetManager != null) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        };
    }

    companion object {

        var currencies: List<ResultModel>? = null
        var current_index: Int = 0

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            if(current_index < 0){
                current_index = 0
            }
            if(currencies != null && current_index >= currencies!!.size){
                current_index = currencies!!.size - 1
            }
            placeCurrentCurrencyData(views)
            placePendingIntents(context, views )
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun placePendingIntents(context: Context, views: RemoteViews){
            val intent1 = Intent(context, ClickIntentService::class.java)
            intent1.action = ClickIntentService.ACTION_NEXT
            val pendingIntent1 = PendingIntent.getService(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.next, pendingIntent1)


            val intent2 = Intent(context, ClickIntentService::class.java)
            intent2.action = ClickIntentService.ACTION_PREVIOUS
            val pendingIntent2 = PendingIntent.getService(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.previous, pendingIntent2)
        }

        fun placeCurrentCurrencyData(views: RemoteViews) {
            if(currencies != null && currencies!!.size > 0 && current_index < currencies!!.size){
                views.setTextViewText(R.id.currencyLong, currencies!!.get(current_index).CurrencyLong)
                views.setTextViewText(R.id.currency, currencies!!.get(current_index).Currency)
                views.setTextViewText(R.id.tx_fee, currencies!!.get(current_index).TxFee.toString())
            }

        }
    }
}


//    val widgetText = context.getString(R.string.appwidget_text)
//    // Construct the RemoteViews object
//    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
//    views.setTextViewText(R.id.appwidget_text, widgetText)
//
//    // Instruct the widget manager to update the widget
//    appWidgetManager.updateAppWidget(appWidgetId, views)

//    val intent = Intent(context, MainActivity::class.java)
//    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
//
//    val remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
//
//    remoteViews.setOnClickPendingIntent(R.id.textView, pendingIntent)
//
//    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

//val intent = Intent(context, ClickIntentService::class.java)
//intent.action = ClickIntentService.ACTION_CLICK
//val pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//views.setOnClickPendingIntent(R.id.currencyLong, pendingIntent)

//val clicks = context.getSharedPreferences("sp", MODE_PRIVATE).getInt("clicks", 0)
//val options = appWidgetManager.getAppWidgetOptions(appWidgetId)
//val width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
//val clicksStr: String = if (width < 200) {
//    clicks.toString() + "s"
//} else {
//    clicks.toString() + "l"
//}
//views.setTextViewText(R.id.currencyLong, clicksStr)