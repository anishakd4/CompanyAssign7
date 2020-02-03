package com.developer.anishakd4.pilabsassignment.Services

import android.app.IntentService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import com.developer.anishakd4.pilabsassignment.AppWidget.NewAppWidget


class ClickIntentService() : IntentService("ClickIntentService") {

    companion object {
        val ACTION_NEXT = "com.anishakd4.android.widgets.click.next"
        val ACTION_PREVIOUS = "com.anishakd4.android.widgets.click.previous"
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action: String? = intent.getAction();
            if (ACTION_NEXT.equals(action)) {
                handleClickNext();
            }else if (ACTION_PREVIOUS.equals(action)) {
                handleClickPrevious();
            }
        }
    }

    private fun handleClickNext() {

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, NewAppWidget::class.java))
        for (appWidgetId in widgetIds) {
            NewAppWidget.current_index++
            NewAppWidget.updateAppWidget(applicationContext, appWidgetManager, appWidgetId)
        }
    }

    private fun handleClickPrevious() {

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, NewAppWidget::class.java))
        for (appWidgetId in widgetIds) {
            NewAppWidget.current_index--
            NewAppWidget.updateAppWidget(applicationContext, appWidgetManager, appWidgetId)
        }
    }
}


//var clicks = getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("clicks", 0)
//clicks++
//getSharedPreferences("sp", Context.MODE_PRIVATE).edit().putInt("clicks", clicks).apply()