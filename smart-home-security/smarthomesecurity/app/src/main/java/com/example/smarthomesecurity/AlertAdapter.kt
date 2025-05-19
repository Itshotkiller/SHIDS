package com.example.smarthomesecurity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthomesecurity.databinding.ItemAlertBinding

class AlertAdapter(private val alerts: List<Alert>) : RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {
    class AlertViewHolder(val binding: ItemAlertBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding = ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert = alerts[position]
        holder.binding.alertType.text = "${alert.type} Alert"
        holder.binding.alertMessage.text = alert.message
        holder.binding.alertTimestamp.text = alert.timestamp
    }

    override fun getItemCount(): Int = alerts.size
}