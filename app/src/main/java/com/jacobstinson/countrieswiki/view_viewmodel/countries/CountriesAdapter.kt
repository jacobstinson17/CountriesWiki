package com.jacobstinson.countrieswiki.view_viewmodel.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacobstinson.countrieswiki.R
import com.jacobstinson.countrieswiki.model.countries.models.Country
import kotlinx.android.synthetic.main.item_country.view.*
import javax.inject.Inject

class CountriesAdapter @Inject constructor(var countries: List<Country>): RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_country, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = countries[position].name
        holder.textCapital.text = countries[position].capital
        holder.textCode.text = countries[position].code
        holder.textCurrency.text = countries[position].currency
        holder.textPhone.text = countries[position].phone
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textName: TextView = view.text_name
        val textCapital: TextView = view.text_capital
        val textCode: TextView = view.text_code
        val textCurrency: TextView = view.text_currency
        val textPhone: TextView = view.text_phone
    }
}