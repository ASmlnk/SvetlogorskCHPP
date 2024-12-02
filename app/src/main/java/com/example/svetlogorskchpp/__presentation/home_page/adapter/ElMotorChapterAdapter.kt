package com.example.svetlogorskchpp.__presentation.home_page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElMotorChapter
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemElMotorChapterV1Binding
import com.example.svetlogorskchpp.databinding.ItemElMotorChapterV3Binding

class ElMotorChapterAdapter(
    private val onClick: (elMotorChapter: ElMotorChapter) -> Unit,
) : ListAdapter<ElMotorChapter, RecyclerView.ViewHolder>(ItemElMotorChapterCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return ElMotorChapterHolderV3.inflateFrom(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {

        (holder as ElMotorChapterHolderV3).bind(getItem(position), onClick)
    }
}

class ElMotorChapterHolderV1(val binding: ItemElMotorChapterV1Binding)
    : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ElMotorChapter,
            onClick: (ElMotorChapter) -> Unit
        ) {
            binding.apply {
                tvName.text = item.str
                when (item) {
                    ElMotorChapter.OTHER -> ivCategory.setImageResource(R.drawable.flash_4049918)
                    ElMotorChapter.HOV -> ivCategory.setImageResource(R.drawable.chemistry_class_6837437)
                    ElMotorChapter.KTC_TO -> ivCategory.setImageResource(R.drawable.factory_1643683)
                    ElMotorChapter.KTC_KO -> ivCategory.setImageResource(R.drawable.water_boiler)
                    ElMotorChapter.TY -> ivCategory.setImageResource(R.drawable.flash_4049918)
                    ElMotorChapter.EC -> ivCategory.setImageResource(R.drawable.flash_4049918)
                    ElMotorChapter.KA -> ivCategory.setImageResource(R.drawable.water_boiler)
                    ElMotorChapter.RG -> ivCategory.setImageResource(R.drawable.generator_1)
                    ElMotorChapter.REP -> ivCategory.setImageResource(R.drawable.high_voltage_8107242)
                }
            }

        }
    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElMotorChapterHolderV1 {
            val layout = LayoutInflater.from(parentContext.context)
            val binding = ItemElMotorChapterV1Binding.inflate(layout, parentContext, false)
            return ElMotorChapterHolderV1(binding)
        }
    }
    }

class ElMotorChapterHolderV3(val binding: ItemElMotorChapterV3Binding)
    : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ElMotorChapter,
            onClick: (ElMotorChapter) -> Unit
        ) {
            binding.apply {
                linearLayout.setOnClickListener {
                    onClick(item)
                }
                tvName.text = item.str
            }

        }
    companion object {
        fun inflateFrom(parentContext: ViewGroup): ElMotorChapterHolderV3 {
            val layout = LayoutInflater.from(parentContext.context)
            val binding = ItemElMotorChapterV3Binding.inflate(layout, parentContext, false)
            return ElMotorChapterHolderV3(binding)
        }
    }
    }