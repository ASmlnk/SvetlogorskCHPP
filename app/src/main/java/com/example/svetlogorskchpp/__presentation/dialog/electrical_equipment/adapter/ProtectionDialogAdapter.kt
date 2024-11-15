package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemProtectionDialogBinding
import com.example.svetlogorskchpp.inspectionSchedule.ItemStringCallback
import com.google.api.Context

class ProtectionDialogAdapter: ListAdapter<String, ProtectionHolder>(ItemStringCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProtectionHolder {
        return ProtectionHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(
        holder: ProtectionHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class ProtectionHolder (val binding: ItemProtectionDialogBinding):
RecyclerView.ViewHolder(binding.root){

    fun bind(item: String) {
        binding.tvProtectionContent.text = item
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ProtectionHolder {
            val inflate = LayoutInflater.from(parentContext.context)
            val binding = ItemProtectionDialogBinding.inflate(inflate, parentContext, false)
            return ProtectionHolder(binding)
        }
    }
}