package com.example.sufatura.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sufatura.R
import com.example.sufatura.data.local.entities.Tariff
import javax.inject.Singleton

@Singleton
class TariffAdapter(private val tariffs: List<Tariff>) :
    RecyclerView.Adapter<TariffAdapter.TariffViewHolder>() {

    class TariffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val startTextView: TextView = view.findViewById(R.id.startInput)
        val endTextView: TextView = view.findViewById(R.id.endInput)
        val priceTextView: TextView = view.findViewById(R.id.priceInput)

        fun bind(tariff: Tariff) {
            startTextView.text = tariff.startLimit.toString()
            endTextView.text = tariff.endLimit.toString()
            if (tariff.endLimit == -1) {
                endTextView.text = "-"
            }
            priceTextView.text = tariff.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TariffViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tariff, parent, false)
        return TariffViewHolder(view)
    }

    override fun onBindViewHolder(holder: TariffViewHolder, position: Int) {
        val tariff = tariffs[position]
        if (tariff.endLimit == -1) {
            holder.endTextView.text = "-"
            holder.endTextView.isEnabled = false
        }
        holder.bind(tariff)
    }

    override fun getItemCount(): Int {
        return tariffs.size
    }
}