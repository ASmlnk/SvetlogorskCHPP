package com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment

class ItemElectricalEquipmentCallback: DiffUtil.ItemCallback<ElectricalEquipment>() {
    override fun areContentsTheSame(
        oldItem: ElectricalEquipment,
        newItem: ElectricalEquipment,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: ElectricalEquipment,
        newItem: ElectricalEquipment,
    ): Boolean {
        return when {
            oldItem is ElectricalEquipment.Vl && newItem is ElectricalEquipment.Vl -> oldItem.id == newItem.id
            oldItem is ElectricalEquipment.Tr && newItem is ElectricalEquipment.Tr -> oldItem.id == newItem.id

            else -> false
        }
    }
}