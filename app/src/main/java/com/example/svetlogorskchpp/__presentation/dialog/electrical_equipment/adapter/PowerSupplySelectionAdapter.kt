package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ItemElectricalEquipmentCallback
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.DeepLink
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentTrBinding

class PowerSupplySelectionAdapter (
    private val onClick: (id: String, name: String, dl: DeepLink) -> Unit,
) : ListAdapter<ElectricalEquipment, RecyclerView.ViewHolder>(ItemElectricalEquipmentCallback()) {

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is ElectricalEquipment.Tr -> (holder as TrHolder).bind(item, onClick)
            else ->  throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> TrHolder.inflateFrom(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ElectricalEquipment.Tr -> 1
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
}

class TrHolder(val binding: ItemElectricalEquipmentTrBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ElectricalEquipment.Tr,
            onClick: (id: String, name: String, dl: DeepLink) -> Unit,
        ) {
            binding.apply {
                tvName.text = item.nameEquipment
                layout.setOnClickListener{
                    onClick(item.id, item.nameEquipment, item.deepLink)
                }
            }
        }


    companion object {
        fun inflateFrom(parentContext: ViewGroup): TrHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemElectricalEquipmentTrBinding.inflate(layoutInflater, parentContext, false)
            return TrHolder(binding)
        }
    }
}