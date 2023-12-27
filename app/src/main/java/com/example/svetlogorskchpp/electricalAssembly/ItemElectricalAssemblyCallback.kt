package com.example.svetlogorskchpp.electricalAssembly

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.ItemElectricalAssembly

class ItemElectricalAssemblyCallback: DiffUtil.ItemCallback<ItemElectricalAssembly>() {
    override fun areItemsTheSame(
        oldItem: ItemElectricalAssembly,
        newItem: ItemElectricalAssembly
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: ItemElectricalAssembly,
        newItem: ItemElectricalAssembly
    ): Boolean = oldItem == newItem
}