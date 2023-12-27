package com.example.svetlogorskchpp.electricalAssembly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.model.ItemElectricalAssembly
import com.example.svetlogorskchpp.databinding.ItemDialogElectricalAsseblyBinding

class ItemElectricalAssemblyAdapter :
    ListAdapter<ItemElectricalAssembly, ItemElectricalAssemblyAdapter.ItemElectricalAssemblyHolder>(
        ItemElectricalAssemblyCallback()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemElectricalAssemblyHolder = ItemElectricalAssemblyHolder.inflaterFrom(parent)

    override fun onBindViewHolder(holder: ItemElectricalAssemblyHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ItemElectricalAssemblyHolder(val binding: ItemDialogElectricalAsseblyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflaterFrom(parent: ViewGroup): ItemElectricalAssemblyHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemDialogElectricalAsseblyBinding.inflate(layoutInflater, parent, false)
                return ItemElectricalAssemblyHolder(binding)
            }
        }

        fun bind(item: ItemElectricalAssembly) {
            binding.apply {
                textViewNamePowerLines.text = item.name
                if (item.electricMotor) imageElectricMotor.isVisible = true
                else imageElectricMotor.isGone = true
            }
        }
    }
}


