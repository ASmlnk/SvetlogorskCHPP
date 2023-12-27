package com.example.svetlogorskchpp.electricalAssembly

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.ElectricalAssembly
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase

class ElectricalAssemblyItemCallback : DiffUtil.ItemCallback<ElectricalAssemblyFirebase>() {
    override fun areItemsTheSame(
        oldItem: ElectricalAssemblyFirebase,
        newItem: ElectricalAssemblyFirebase
    ): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: ElectricalAssemblyFirebase,
        newItem: ElectricalAssemblyFirebase
    ): Boolean = oldItem == newItem
}