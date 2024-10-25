package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.adapter

import androidx.recyclerview.widget.DiffUtil

class ItemStringCallback: DiffUtil.ItemCallback<String>() {
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}