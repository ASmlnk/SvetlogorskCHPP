package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ItemElectricalEquipmentCallback
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.DeepLink
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentTgBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentTrBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentTsnBinding
import com.example.svetlogorskchpp.databinding.ItemEquipmentElMotorBinding
import com.example.svetlogorskchpp.databinding.ItemEquipmentMechanismInfoBinding
import com.example.svetlogorskchpp.databinding.ItemEquipmentSwitchgearBinding

class PowerSupplySelectionAdapter(
    private var id: String = "",
    private val onClick: (id: String, name: String, dl: DeepLink) -> Unit,
) : ListAdapter<ElectricalEquipment, RecyclerView.ViewHolder>(ItemElectricalEquipmentCallback()) {

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is ElectricalEquipment.Tr -> (holder as TrHolder).bind(item, onClick)
            is ElectricalEquipment.Tsn -> (holder as TsnHolder).bind(item, onClick)
            is ElectricalEquipment.Tg -> (holder as TgHolder).bind(item, onClick)
            is ElectricalEquipment.Switchgear -> (holder as SwitchgearHolder).bind(item, onClick)
            is ElectricalEquipment.MechanismInfo -> (holder as MechanismInfoHolder).bind(item, onClick)
            is ElectricalEquipment.ElMotor -> (holder as ElMotorHolder).bind(item, onClick)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> TrHolder.inflateFrom(id,parent)
            2 -> TgHolder.inflateFrom(id,parent)
            3 -> TsnHolder.inflateFrom(id,parent)
            4 -> SwitchgearHolder.inflateFrom(id,parent)
            5 -> MechanismInfoHolder.inflateFrom(id, parent)
            6 -> ElMotorHolder.inflateFrom(id, parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ElectricalEquipment.Tr -> 1
            is ElectricalEquipment.Tg -> 2
            is ElectricalEquipment.Tsn -> 3
            is ElectricalEquipment.Switchgear -> 4
            is ElectricalEquipment.MechanismInfo -> 5
            is ElectricalEquipment.ElMotor -> 6
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
}

class TrHolder(
    private val id: String,
    val binding: ItemElectricalEquipmentTrBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Tr,
        onClick: (id: String, name: String, dl: DeepLink) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment

            layout.setOnClickListener {
                onClick(item.id, item.nameEquipment, item.deepLink)
            }
        }
    }


    companion object {
        fun inflateFrom(id:String, parentContext: ViewGroup): TrHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding =
                ItemElectricalEquipmentTrBinding.inflate(layoutInflater, parentContext, false)
            return TrHolder(id,binding)
        }
    }
}

class TgHolder(
    private val id: String,
    val binding: ItemElectricalEquipmentTgBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Tg,
        onClick: (id: String, name: String, dl: DeepLink) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment

            layout.setOnClickListener {
                onClick(item.id, item.nameEquipment, item.deepLink)
            }
        }
    }


    companion object {
        fun inflateFrom(id:String, parentContext: ViewGroup): TgHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding =
                ItemElectricalEquipmentTgBinding.inflate(layoutInflater, parentContext, false)
            return TgHolder(id,binding)
        }
    }
}

class TsnHolder(
    private val id: String ,
    val binding: ItemElectricalEquipmentTsnBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Tsn,
        onClick: (id: String, name: String, dl: DeepLink) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment
            layout.setOnClickListener {
                onClick(item.id, item.nameEquipment, item.deepLink)
            }
        }
    }


    companion object {
        fun inflateFrom(id:String, parentContext: ViewGroup): TsnHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding =
                ItemElectricalEquipmentTsnBinding.inflate(layoutInflater, parentContext, false)
            return TsnHolder(id,binding)
        }
    }
}

class SwitchgearHolder(
    private val id: String ,
    val binding: ItemEquipmentSwitchgearBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Switchgear,
        onClick: (id: String, name: String, dl: DeepLink) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.name
            layout.setOnClickListener {
                onClick(item.id, item.name, item.deepLink)
            }
        }
    }


    companion object {
        fun inflateFrom(id:String, parentContext: ViewGroup): SwitchgearHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding =
                ItemEquipmentSwitchgearBinding.inflate(layoutInflater, parentContext, false)
            return SwitchgearHolder(id,binding)
        }
    }
}

class MechanismInfoHolder(private val id: String ,val binding: ItemEquipmentMechanismInfoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.MechanismInfo,
        onClick: (id: String, name: String, dl: DeepLink) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.name
            layout.setOnClickListener {
                onClick(item.id, item.name, item.deepLink)
            }
        }
    }

    companion object {
        fun inflateFrom(id:String, parentContext: ViewGroup): MechanismInfoHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemEquipmentMechanismInfoBinding.inflate(layoutInflater, parentContext, false)
            return MechanismInfoHolder(id, binding)
        }
    }
}

class ElMotorHolder(private val id: String ,val binding: ItemEquipmentElMotorBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.ElMotor,
        onClick: (id: String, name: String, dl: DeepLink) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.name
            layout.setOnClickListener {
                onClick(item.id, item.name, item.deepLink)
            }
        }
    }

    companion object {
        fun inflateFrom(id:String, parentContext: ViewGroup): ElMotorHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemEquipmentElMotorBinding.inflate(layoutInflater, parentContext, false)
            return ElMotorHolder(id, binding)
        }
    }
}