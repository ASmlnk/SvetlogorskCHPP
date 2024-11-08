package com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import com.example.svetlogorskchpp.databinding.ItemOpenSwitchgearTrBinding
import com.example.svetlogorskchpp.databinding.ItemOpenSwitchgearVlBinding

class ElectricalEquipmentAdapter(
    private val onClick: (id: String) -> Unit,
) : ListAdapter<ElectricalEquipment, RecyclerView.ViewHolder>(ItemElectricalEquipmentCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ElectricalEquipment.Vl -> (holder as ElectricalEquipmentVlHolder).bind(item, onClick)
            is ElectricalEquipment.Tr -> (holder as ElectricalEquipmentTrHolder).bind(item, onClick)
            is ElectricalEquipment.Tsn -> TODO()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ElectricalEquipmentVlHolder.inflateFrom(parent)
            1 -> ElectricalEquipmentTrHolder.inflateFrom(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ElectricalEquipment.Vl -> 0
            is ElectricalEquipment.Tr -> 1
            is ElectricalEquipment.Tsn -> 2
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

class ElectricalEquipmentTrHolder(val binding: ItemOpenSwitchgearTrBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Tr,
        onClick: (id: String) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment
            tvSpare.visibility = if (item.isSpare) View.VISIBLE else View.INVISIBLE

            if (item.isThreeWinding) {
                tvTextOry.setBackgroundResource(R.drawable.background_item_open_switchgear_tr_parameter_right_2)
            } else {
                tvTextOry.setBackgroundResource(R.drawable.background_item_open_switchgear_tr_parameter_right)
            }
            tvType.text = item.type
            tvTypeParameter.text = item.typeParameter
            tvTextOry.text = item.parameterOry
            linearLayout.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElectricalEquipmentTrHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemOpenSwitchgearTrBinding.inflate(layoutInflater, parentContext, false)
            return ElectricalEquipmentTrHolder(binding)
        }
    }
}