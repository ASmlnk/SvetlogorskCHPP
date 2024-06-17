package com.example.svetlogorskchpp.electricalAssembly

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.ElectricalAssemblySearch

class ElectricalAssemblySearchCallback : DiffUtil.ItemCallback<ElectricalAssemblySearch>() {
    override fun areItemsTheSame(
        oldItem: ElectricalAssemblySearch,
        newItem: ElectricalAssemblySearch
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: ElectricalAssemblySearch,
        newItem: ElectricalAssemblySearch
    ): Boolean {
        return oldItem == newItem
    }
}