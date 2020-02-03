package com.developer.anishakd4.pilabsassignment

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.anishakd4.pilabsassignment.Adapter.RecyclerviewAdapter
import com.developer.anishakd4.pilabsassignment.AppWidget.NewAppWidget
import com.developer.anishakd4.pilabsassignment.Database.CurrencyDatabase
import com.developer.anishakd4.pilabsassignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val dataSource = CurrencyDatabase.getInstance(application).currencyDao
        val viewModelFactory = MainActivityViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.fetchCurrency()

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.news.observe(this, Observer {
            if (it != null) {
                viewModel.insertIntoDb()
            }
        })

        viewModel.news2.observe(this, Observer {
            if (it != null) {

                val appWidgetManager = AppWidgetManager.getInstance(this)
                val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, NewAppWidget::class.java))
                for (appWidgetId in widgetIds) {
                    NewAppWidget.currencies = it
                    NewAppWidget.updateAppWidget(applicationContext, appWidgetManager, appWidgetId)
                }

                binding.currencyList.visibility = View.VISIBLE
                val adapter = RecyclerviewAdapter(it)
                binding.currencyList.layoutManager = LinearLayoutManager(this)
                binding.currencyList.adapter = adapter

                viewModel.haveData()
            }
        })

        viewModel.loadError.observe(this, Observer { isError ->
            binding.listError.visibility = if (isError == "" || isError == null) View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.currencyList.visibility = View.GONE
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
//        val clicks = getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("clicks", 0)
//        clicksTextView.text = clicks.toString()
    }
}
