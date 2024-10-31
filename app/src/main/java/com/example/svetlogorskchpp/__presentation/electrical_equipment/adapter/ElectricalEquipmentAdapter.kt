package com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import com.example.svetlogorskchpp.databinding.ItemOpenSwitchgearVlBinding

class ElectricalEquipmentAdapter(
    private val onClick: (id: String) -> Unit,
) : ListAdapter<ElectricalEquipment, RecyclerView.ViewHolder>(ItemElectricalEquipmentCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ElectricalEquipment.Vl -> (holder as ElectricalEquipmentVlHolder).bind(item, onClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ElectricalEquipmentVlHolder.inflateFrom(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ElectricalEquipment.Vl -> 0
        }
    }
}

class ElectricalEquipmentVlHolder(val binding: ItemOpenSwitchgearVlBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Vl,
        onClick: (id: String) -> Unit,
    ) {
        binding.apply {
            tvNameEquipment.text = item.nameEquipment
            tvTransit.isGone = !item.isTransit
            tvCell.text = itemView.resources.getString(R.string.cell, item.cell.toString())
            tvOry.text = itemView.resources.getString(R.string.ory_item, item.voltage.str)
            tvBysSystem.text = itemView.resources.getString(R.string.bys_system, item.bysSystem)
            linearLayout.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElectricalEquipmentVlHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemOpenSwitchgearVlBinding.inflate(layoutInflater, parentContext, false)
            return ElectricalEquipmentVlHolder(binding)
        }
    }
}