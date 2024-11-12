package com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import com.example.svetlogorskchpp.databinding.ItemEquipmentTgBinding
import com.example.svetlogorskchpp.databinding.ItemEquipmentTsnBinding
import com.example.svetlogorskchpp.databinding.ItemOpenSwitchgearTrBinding
import com.example.svetlogorskchpp.databinding.ItemOpenSwitchgearVlBinding
import android.text.style.StyleSpan
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat

class ElectricalEquipmentAdapter(
    private val onClick: (id: String) -> Unit,
) : ListAdapter<ElectricalEquipment, RecyclerView.ViewHolder>(ItemElectricalEquipmentCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ElectricalEquipment.Vl -> (holder as ElectricalEquipmentVlHolder).bind(item, onClick)
            is ElectricalEquipment.Tr -> (holder as ElectricalEquipmentTrHolder).bind(item, onClick)
            is ElectricalEquipment.Tsn -> (holder as ElectricalEquipmentTsnHolder).bind(item, onClick)
            is ElectricalEquipment.Tg -> (holder as ElectricalEquipmentTgHolder).bind(item, onClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ElectricalEquipmentVlHolder.inflateFrom(parent)
            1 -> ElectricalEquipmentTrHolder.inflateFrom(parent)
            2 -> ElectricalEquipmentTsnHolder.inflateFrom(parent)
            3 -> ElectricalEquipmentTgHolder.inflateFrom(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ElectricalEquipment.Vl -> 0
            is ElectricalEquipment.Tr -> 1
            is ElectricalEquipment.Tsn -> 2
            is ElectricalEquipment.Tg -> 3
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

class ElectricalEquipmentTsnHolder(val binding: ItemEquipmentTsnBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(
        item: ElectricalEquipment.Tsn,
        onClick: (id: String) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment
            tvSpare.visibility = if (item.isSpare) View.VISIBLE else View.INVISIBLE
            tvVoltage.text = item.voltage.str

            tvTextOry.setBackgroundResource(R.drawable.background_item_open_switchgear_tr_parameter_right)

            tvType.text = item.type
            tvTypeParameter.text = item.typeParameter
            tvTextOry.text = item.powerSupplyName +
                    if(item.powerSupplyCell.isNotEmpty()) " яч." +item.powerSupplyCell else ""
            linearLayout.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElectricalEquipmentTsnHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemEquipmentTsnBinding.inflate(layoutInflater, parentContext, false)
            return ElectricalEquipmentTsnHolder(binding)
        }
    }
}

class ElectricalEquipmentTgHolder(val binding: ItemEquipmentTgBinding) :
RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(
        item: ElectricalEquipment.Tg,
        onClick: (id: String) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment
           // tvTypeParameter.text = Html.fromHtml(itemView.resources.getString(R.string.type_parameter_tg, item.powerEl, item.powerThermal, item.steamConsumption), Html.FROM_HTML_MODE_LEGACY)
            tvTypeGenerator.text = item.typeGenerator
            tvTypeTurbin.text = item.typeTurbin

            val spannableString = SpannableString(String.format("%sМВт  %sГКал  %sт/ч", item.powerEl, item.powerThermal, item.steamConsumption))
            // Устанавливаем стиль для первого значения (жирный и цвет)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                item.powerEl.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(itemView.context,  R.color.home_page_gradient)), // Замените на нужный вам цвет
                0,
                item.powerEl.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

// Устанавливаем стиль для второго значения (жирный и цвет)
            val secondValueStart = item.powerEl.length + 5 // 6 - длина строки " МВт "
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                secondValueStart,
                secondValueStart + item.powerThermal.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(itemView.context,  R.color.home_page_gradient)), // Замените на нужный вам цвет
                secondValueStart,
                secondValueStart + item.powerThermal.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

// Устанавливаем стиль для третьего значения (жирный и цвет)
            val thirdValueStart = secondValueStart + item.powerThermal.length + 6 // 6 - длина строки " ГКал "
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                thirdValueStart,
                thirdValueStart + item.steamConsumption.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.home_page_gradient)), // Замените на нужный вам цвет
                thirdValueStart,
                thirdValueStart + item.steamConsumption.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            tvTypeParameter.text = spannableString
            linearLayout.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElectricalEquipmentTgHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemEquipmentTgBinding.inflate(layoutInflater, parentContext, false)
            return ElectricalEquipmentTgHolder(binding)
        }
    }
}