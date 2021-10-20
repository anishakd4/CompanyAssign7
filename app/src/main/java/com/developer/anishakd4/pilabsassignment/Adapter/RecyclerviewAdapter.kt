package com.developer.anishakd4.pilabsassignment.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.developer.anishakd4.pilabsassignment.Model.ResultModel
import com.developer.anishakd4.pilabsassignment.R
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerviewAdapter(val currencies: List<ResultModel>): RecyclerView.Adapter<RecyclerviewAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val currency: TextView = view.currency
        val currencyLong: TextView = view.currencylong
        val txFee: TextView = view.txfee

        fun bind(current: ResultModel) {
            currency.text = current.Currency
            currencyLong.text = current.CurrencyLong
            txFee.text = current.TxFee.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as RecyclerViewHolder).bind(currencies.get(position))
    }
}