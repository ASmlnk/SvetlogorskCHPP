package com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
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
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.DeepLink
import com.example.svetlogorskchpp.databinding.ItemElectricalAsseblyBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentElMotorBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentLightingBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentMechanismInfoBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentOtherBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentSwitchgearBinding
import com.example.svetlogorskchpp.databinding.ItemElectricalEquipmentSwitchgearLightingBinding

class ElectricalEquipmentAdapter(
    private val onClick: (deepLink: DeepLink, id: String) -> Unit,
) : ListAdapter<ElectricalEquipment, RecyclerView.ViewHolder>(ItemElectricalEquipmentCallback()) {


    private var onItemClickDelete: ((item: ElectricalEquipment) -> Unit)? = null

    // Метод для установки функции обратного вызова
    fun setOnItemClickListener(listener: (item: ElectricalEquipment) -> Unit) {
        this.onItemClickDelete = listener
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ElectricalEquipment.Vl -> (holder as ElectricalEquipmentVlHolder).bind(item, onClick)
            is ElectricalEquipment.Tr -> (holder as ElectricalEquipmentTrHolder).bind(item, onClick)
            is ElectricalEquipment.Tsn -> (holder as ElectricalEquipmentTsnHolder).bind(
                item,
                onClick
            )
            is ElectricalEquipment.Tg -> (holder as ElectricalEquipmentTgHolder).bind(item, onClick)

            is ElectricalEquipment.ElMotor -> (holder as ElMotorHolder).bind(item, onClick, onItemClickDelete)
            is ElectricalEquipment.Switchgear -> {
                if (item.category == ElAssembly.LIGHTING) (holder as ElectricalEquipmentSwitchgearLightingHolder).bind(
                    item, onClick, onItemClickDelete
                )
                else (holder as ElectricalEquipmentSwitchgearHolder).bind(
                    item,
                    onClick, onItemClickDelete
                )
            }


            is ElectricalEquipment.LightOther -> if (item.isLighting) {
                (holder as LightingHolder).bind(item, onClick, onItemClickDelete)
            } else (holder as OtherHolder).bind(item, onClick, onItemClickDelete)

            is ElectricalEquipment.MechanismInfo -> (holder as MechanismInfoHolder).bind(item, onClick, onItemClickDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ElectricalEquipmentVlHolder.inflateFrom(parent)
            1 -> ElectricalEquipmentTrHolder.inflateFrom(parent)
            2 -> ElectricalEquipmentTsnHolder.inflateFrom(parent)
            3 -> ElectricalEquipmentTgHolder.inflateFrom(parent)
            4 -> ElectricalEquipmentSwitchgearHolder.inflateFrom(parent)
            5 -> ElMotorHolder.inflateFrom(parent)
            6 -> LightingHolder.inflateFrom(parent)
            7 -> OtherHolder.inflateFrom(parent)
            8 -> ElectricalEquipmentSwitchgearLightingHolder.inflateFrom(parent)
            9 -> MechanismInfoHolder.inflateFrom(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is ElectricalEquipment.Vl -> 0
            is ElectricalEquipment.Tr -> 1
            is ElectricalEquipment.Tsn -> 2
            is ElectricalEquipment.Tg -> 3
            is ElectricalEquipment.ElMotor -> 5
            is ElectricalEquipment.Switchgear -> {
                if (item.category == ElAssembly.LIGHTING) 8 else 4
            }

            is ElectricalEquipment.LightOther -> {
                if (item.isLighting) 6 else 7
            }

            is ElectricalEquipment.MechanismInfo -> 9
        }
    }
}

class ElectricalEquipmentVlHolder(val binding: ItemOpenSwitchgearVlBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Vl,
        onClick: (deepLink: DeepLink, id: String) -> Unit,
    ) {
        binding.apply {
            tvNameEquipment.text = item.nameEquipment
            tvTransit.isGone = !item.isTransit
            tvCell.text = itemView.resources.getString(R.string.cell, item.cell.toString())
            tvOry.text = itemView.resources.getString(R.string.ory_item, item.voltage.str)
            tvBysSystem.text = itemView.resources.getString(R.string.bys_system, item.bysSystem)
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
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
        onClick: (deepLink: DeepLink, id: String) -> Unit,
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
                onClick(item.deepLink, item.id)
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
        onClick: (deepLink: DeepLink, id: String) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment
            tvSpare.visibility = if (item.isSpare) View.VISIBLE else View.INVISIBLE
            tvVoltage.text = item.voltage.str

            tvTextOry.setBackgroundResource(R.drawable.background_item_open_switchgear_tr_parameter_right)

            tvType.text = item.type
            tvTypeParameter.text = item.typeParameter
            tvTextOry.text = item.powerSupplyName +
                    if (item.powerSupplyCell.isNotEmpty()) " яч." + item.powerSupplyCell else ""
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
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
        onClick: (deepLink: DeepLink, id: String) -> Unit,
    ) {
        binding.apply {
            tvName.text = item.nameEquipment
            // tvTypeParameter.text = Html.fromHtml(itemView.resources.getString(R.string.type_parameter_tg, item.powerEl, item.powerThermal, item.steamConsumption), Html.FROM_HTML_MODE_LEGACY)
            tvTypeGenerator.text = item.typeGenerator
            tvTypeTurbin.text = item.typeTurbin

            val spannableString = SpannableString(
                String.format(
                    "%sМВт  %sГКал  %sт/ч",
                    item.powerEl,
                    item.powerThermal,
                    item.steamConsumption
                )
            )
            // Устанавливаем стиль для первого значения (жирный и цвет)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                item.powerEl.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.text_content_rza
                    )
                ), // Замените на нужный вам цвет
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
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.text_content_rza
                    )
                ), // Замените на нужный вам цвет
                secondValueStart,
                secondValueStart + item.powerThermal.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

// Устанавливаем стиль для третьего значения (жирный и цвет)
            val thirdValueStart =
                secondValueStart + item.powerThermal.length + 6 // 6 - длина строки " ГКал "
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                thirdValueStart,
                thirdValueStart + item.steamConsumption.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.text_content_rza
                    )
                ), // Замените на нужный вам цвет
                thirdValueStart,
                thirdValueStart + item.steamConsumption.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            tvTypeParameter.text = spannableString
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
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

class ElectricalEquipmentSwitchgearHolder(val binding: ItemElectricalEquipmentSwitchgearBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Switchgear,
        onClick: (deepLink: DeepLink, id: String) -> Unit,
        onItemClickDelete: ((ElectricalEquipment) -> Unit)?,
    ) {
        binding.apply {
            tvName.text = item.name
            tvPowerSupply.text = if (item.powerSupplyCell.isEmpty()) item.powerSupplyName else itemView.resources.getString(
                R.string.power_supply_item,
                item.powerSupplyName,
                item.powerSupplyCell
            )
            ivDelete.isGone = !item.isDelete
            ivDelete.setOnLongClickListener {
                onItemClickDelete?.let { it -> it(item) }
                return@setOnLongClickListener true
            }
            tvNameDepartment.text = item.nameDepartment.str
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
            }
            when (item.nameDepartment) {
                NameDepartment.BNS -> iv.setImageResource(R.drawable.icon_station)
                NameDepartment.COOLING_TOWER -> iv.setImageResource(R.drawable.icon_nuclear_power)
                NameDepartment.KRY -> iv.setImageResource(R.drawable.power_supply_283291)
                NameDepartment.KTC_KO -> iv.setImageResource(R.drawable.water_boiler)
                NameDepartment.KTC_TO -> iv.setImageResource(R.drawable.factory_1643683)
                NameDepartment.RY -> iv.setImageResource(R.drawable.icon_electrical_panel)
                NameDepartment.HC -> iv.setImageResource(R.drawable.chemistry_class_6837437)
                NameDepartment.SHIELD_BLOCK -> iv.setImageResource(R.drawable.high_voltage_8107242)
                NameDepartment.OTHER -> iv.setImageResource(R.drawable.flash_4049918)
                NameDepartment.POST_TOK -> iv.setImageResource(R.drawable.icon_electric_power)
                NameDepartment.KTC_TY -> iv.setImageResource(R.drawable.free_icon_oil_tank)
            }
            val tintColorRtzo =
                ContextCompat.getColor(itemView.context, R.color.floatingActionButton)
            val tintColor = ContextCompat.getColor(itemView.context, R.color.text_content_rza_2)

            if (item.category == ElAssembly.RTZO) {
                tvRtzo.visibility = View.VISIBLE
                iv.imageTintList = ColorStateList.valueOf(tintColorRtzo)
            } else {
                iv.imageTintList = ColorStateList.valueOf(tintColor)
                tvRtzo.visibility = View.INVISIBLE
            }

        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElectricalEquipmentSwitchgearHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemElectricalEquipmentSwitchgearBinding.inflate(
                layoutInflater,
                parentContext,
                false
            )
            return ElectricalEquipmentSwitchgearHolder(binding)
        }
    }
}

class ElectricalEquipmentSwitchgearLightingHolder(val binding: ItemElectricalEquipmentSwitchgearLightingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Switchgear,
        onClick: (deepLink: DeepLink, id: String) -> Unit,
        onItemClickDelete: ((ElectricalEquipment) -> Unit)?,
    ) {
        binding.apply {
            tvName.text = item.name
            ivDelete.isGone = !item.isDelete
            tvPowerSupply.text = if (item.powerSupplyCell.isEmpty()) item.powerSupplyName else itemView.resources.getString(
                R.string.power_supply_item,
                item.powerSupplyName,
                item.powerSupplyCell
            )
            ivDelete.setOnLongClickListener {
                onItemClickDelete?.let { it -> it(item) }
                return@setOnLongClickListener true
            }
            tvNameDepartment.text = item.nameDepartment.str
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
            }
            when (item.nameDepartment) {
                NameDepartment.BNS -> iv.setImageResource(R.drawable.icon_station)
                NameDepartment.COOLING_TOWER -> iv.setImageResource(R.drawable.icon_nuclear_power)
                NameDepartment.KRY -> iv.setImageResource(R.drawable.power_supply_283291)
                NameDepartment.KTC_KO -> iv.setImageResource(R.drawable.water_boiler)
                NameDepartment.KTC_TO -> iv.setImageResource(R.drawable.factory_1643683)
                NameDepartment.RY -> iv.setImageResource(R.drawable.icon_electrical_panel)
                NameDepartment.HC -> iv.setImageResource(R.drawable.chemistry_class_6837437)
                NameDepartment.SHIELD_BLOCK -> iv.setImageResource(R.drawable.high_voltage_8107242)
                NameDepartment.OTHER -> iv.setImageResource(R.drawable.flash_4049918)
                NameDepartment.POST_TOK -> iv.setImageResource(R.drawable.icon_electric_power)
                NameDepartment.KTC_TY -> iv.setImageResource(R.drawable.free_icon_oil_tank)
            }
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElectricalEquipmentSwitchgearLightingHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemElectricalEquipmentSwitchgearLightingBinding.inflate(
                layoutInflater,
                parentContext,
                false
            )
            return ElectricalEquipmentSwitchgearLightingHolder(binding)
        }
    }
}


class ElectricalEquipmentSwitchgearHolder2(val binding: ItemElectricalAsseblyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.Switchgear,
        onClick: (deepLink: DeepLink, id: String) -> Unit,
    ) {
        binding.apply {
            textNameElectricalAssembly.text = item.name
            textInputAssembly.text = item.nameDepartment.str
            textDepartment.isGone = true
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElectricalEquipmentSwitchgearHolder2 {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding = ItemElectricalAsseblyBinding.inflate(layoutInflater, parentContext, false)
            return ElectricalEquipmentSwitchgearHolder2(binding)
        }
    }
}

class ElMotorHolder(val binding: ItemElectricalEquipmentElMotorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.ElMotor,
        onClick: (deepLink: DeepLink, id: String) -> Unit,
        onItemClickDelete: ((ElectricalEquipment) -> Unit)?,
    ) {
        binding.apply {
            tvName.text = item.name
            ivDelete.isGone = !item.isDelete
            ivDelete.setOnLongClickListener {
                onItemClickDelete?.let { it -> it(item) }
                return@setOnLongClickListener true
            }
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
            }
            tvCategory.text = item.category.str
            tvPowerSupply.text =
                if (item.powerSupplyCell.isEmpty()) item.powerSupplyName else itemView.resources.getString(
                    R.string.power_supply_item,
                    item.powerSupplyName,
                    item.powerSupplyCell
                )
            tvVoltage.text =
                itemView.resources.getString(R.string.voltage_el_motor_item, item.voltage.str)
            tvPower.text = itemView.resources.getString(R.string.power_el_motor_item, item.powerEl)
            tvI.text = itemView.resources.getString(R.string.i_el_motor_item, item.i)
            tvRep.visibility = if (item.isRep) View.VISIBLE else View.INVISIBLE

            when (item.category) {
                ElCategory.OTHER -> ivCategory.setImageResource(R.drawable.flash_4049918)
                ElCategory.TREATMENT_FACILITIES -> ivCategory.setImageResource(R.drawable.free_icon_water_treatment)
                ElCategory.DESALTING -> ivCategory.setImageResource(R.drawable.free_icon_filters)
                ElCategory.BNT -> ivCategory.setImageResource(R.drawable.free_icon_bnt)
                ElCategory.Bagernaya -> ivCategory.setImageResource(R.drawable.free_icon_bagern)
                ElCategory.KO -> ivCategory.setImageResource(R.drawable.free_icon_water_ko)
                ElCategory.KTC_KO -> ivCategory.setImageResource(R.drawable.free_icon_water_ko)
                ElCategory.KTC_TO -> ivCategory.setImageResource(R.drawable.factory_1643683)
                ElCategory.NDV -> ivCategory.setImageResource(R.drawable.free_icon_tower)
                ElCategory.PN -> ivCategory.setImageResource(R.drawable.free_icon_pn)
                ElCategory.PEN -> ivCategory.setImageResource(R.drawable.free_icon_pump)
                ElCategory.PRETREATMENT -> ivCategory.setImageResource(R.drawable.free_icon_water_cleaning)
                ElCategory.SN -> ivCategory.setImageResource(R.drawable.factory_1643683)
                ElCategory.TY -> ivCategory.setImageResource(R.drawable.free_icon_oil_tank)
                ElCategory.CN -> ivCategory.setImageResource(R.drawable.icon_nuclear_power)
                ElCategory.CCR -> ivCategory.setImageResource(R.drawable.free_icon_ccr)
                ElCategory.AMMONIA -> ivCategory.setImageResource(R.drawable.free_icon_ammonia)
                ElCategory.GIDROZIYNOE -> ivCategory.setImageResource(R.drawable.icon_pngwing)
                ElCategory.ACIDIC -> ivCategory.setImageResource(R.drawable.free_icon_acid)
                ElCategory.COAGULANT -> ivCategory.setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.N_TS -> ivCategory.setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.SOVEVOE -> ivCategory.setImageResource(R.drawable.free_icon_clean)
                ElCategory.LIMESTONE -> ivCategory.setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.PHOSPHATE -> ivCategory.setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.NVK -> ivCategory.setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.ALKALINE -> ivCategory.setImageResource(R.drawable.free_icon_ph)
                ElCategory.TG_1 -> ivCategory.setImageResource(R.drawable.generator_1)
                ElCategory.TG_3 -> ivCategory.setImageResource(R.drawable.generator_1)
                ElCategory.TG_4 -> ivCategory.setImageResource(R.drawable.generator_1)
                ElCategory.TG_5 -> ivCategory.setImageResource(R.drawable.generator_1)
                ElCategory.TG_6 -> ivCategory.setImageResource(R.drawable.generator_1)
                ElCategory.KA_1 -> ivCategory.setImageResource(R.drawable.water_boiler)
                ElCategory.KA_6 -> ivCategory.setImageResource(R.drawable.water_boiler)
                ElCategory.KA_7 -> ivCategory.setImageResource(R.drawable.water_boiler)
                ElCategory.KA_8 -> ivCategory.setImageResource(R.drawable.water_boiler)
                ElCategory.KA_9 -> ivCategory.setImageResource(R.drawable.water_boiler)
                ElCategory.EC -> ivCategory.setImageResource(R.drawable.flash_4049918)
            }
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElMotorHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding =
                ItemElectricalEquipmentElMotorBinding.inflate(layoutInflater, parentContext, false)
            return ElMotorHolder(binding)
        }
    }
}

class LightingHolder(val binding: ItemElectricalEquipmentLightingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.LightOther,
        onClick: (deepLink: DeepLink, id: String) -> Unit,
        onItemClickDelete: ((ElectricalEquipment) -> Unit)?,
    ) {
        binding.apply {
            tvName.text = item.name
            ivDelete.isGone = !item.isDelete
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
            }
            tvPowerSupply.text = itemView.resources.getString(
                R.string.power_supply_item,
                item.powerSupplyName,
                item.powerSupplyCell
            )
            tvPowerSupply.text =
                if (item.powerSupplyCell.isEmpty()) item.powerSupplyName else itemView.resources.getString(
                    R.string.power_supply_item,
                    item.powerSupplyName,
                    item.powerSupplyCell
                )
            ivDelete.setOnLongClickListener {
                onItemClickDelete?.let { it -> it(item) }
                return@setOnLongClickListener true
            }

        }

    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): LightingHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding =
                ItemElectricalEquipmentLightingBinding.inflate(layoutInflater, parentContext, false)
            return LightingHolder(binding)
        }
    }
}

class OtherHolder(val binding: ItemElectricalEquipmentOtherBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ElectricalEquipment.LightOther,
        onClick: (deepLink: DeepLink, id: String) -> Unit,
        onItemClickDelete: ((item: ElectricalEquipment) -> Unit)?,
    ) {
        binding.apply {
            tvName.text = item.name
            ivDelete.isGone = !item.isDelete
            linearLayout.setOnClickListener {
                onClick(item.deepLink, item.id)
            }
            tvPowerSupply.text = itemView.resources.getString(
                R.string.power_supply_item,
                item.powerSupplyName,
                item.powerSupplyCell
            )
            tvPowerSupply.text =
                if (item.powerSupplyCell.isEmpty()) item.powerSupplyName else itemView.resources.getString(
                    R.string.power_supply_item,
                    item.powerSupplyName,
                    item.powerSupplyCell
                )
            ivDelete.setOnLongClickListener {
                onItemClickDelete?.let { it -> it(item) }
                return@setOnLongClickListener true
            }
        }
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): OtherHolder {
            val layoutInflater = LayoutInflater.from(parentContext.context)
            val binding =
                ItemElectricalEquipmentOtherBinding.inflate(layoutInflater, parentContext, false)
            return OtherHolder(binding)
        }
    }
}

class MechanismInfoHolder(val binding: ItemElectricalEquipmentMechanismInfoBinding)
    : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ElectricalEquipment.MechanismInfo,
            onClick: (deepLink: DeepLink, id: String) -> Unit,
            onItemClickDelete: ((item: ElectricalEquipment) -> Unit)?,
        ) {
            binding.apply {
                tvName.text = item.name
                tvGeneralCategory.text = item.category.str

                linearLayout.setOnClickListener {
                    onClick(item.deepLink, item.id)
                }

                when(item.category) {
                    ElGeneralCategory.OTHER -> iv.setImageResource(R.drawable.flash_4049918)
                    ElGeneralCategory.HOV -> iv.setImageResource(R.drawable.chemistry_class_6837437)
                    ElGeneralCategory.KTC_TO -> iv.setImageResource(R.drawable.factory_1643683)
                    ElGeneralCategory.KTC_KO -> iv.setImageResource(R.drawable.free_icon_water_ko)
                    ElGeneralCategory.TY -> iv.setImageResource(R.drawable.free_icon_oil_tank)
                    ElGeneralCategory.EC -> iv.setImageResource(R.drawable.flash_4049918)
                    ElGeneralCategory.KA -> iv.setImageResource(R.drawable.water_boiler)
                    ElGeneralCategory.RG -> iv.setImageResource(R.drawable.generator_1)
                }
            }
        }


        companion object {
            fun inflateFrom(parentContext: ViewGroup): MechanismInfoHolder {
                val layoutInflater = LayoutInflater.from(parentContext.context)
                val binding = ItemElectricalEquipmentMechanismInfoBinding.inflate(layoutInflater, parentContext, false)
                return MechanismInfoHolder(binding)
            }
        }
    }