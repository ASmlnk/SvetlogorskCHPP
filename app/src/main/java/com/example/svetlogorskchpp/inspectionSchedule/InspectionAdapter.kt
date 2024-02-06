package com.example.svetlogorskchpp.inspectionSchedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemInspectionScheduleDayBinding
import com.example.svetlogorskchpp.databinding.ItemInspectionScheduleNightBinding
import com.example.svetlogorskchpp.databinding.ItemInspectionScheduleWithoutNumberBinding
import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection
import com.example.svetlogorskchpp.model.inspectionSchedule.InspectionChecklist

class InspectionAdapter :
    ListAdapter<Inspection, RecyclerView.ViewHolder>(InspectionCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].workingShift) {
            "день" -> if (currentList[position].withoutNumber) 2 else 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> InspectionHolderDay.inflateFrom(parent)
            1 -> InspectionHolderNight.inflateFrom(parent)
            else -> InspectionHolderWithoutNumber.inflateFrom(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is InspectionHolderDay -> holder.bind(item)
            is InspectionHolderNight -> holder.bind(item)
            is InspectionHolderWithoutNumber -> holder.bind(item)
        }
    }

    class InspectionHolderDay(val binding: ItemInspectionScheduleDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): InspectionHolderDay {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemInspectionScheduleDayBinding.inflate(layoutInflater, parent, false)
                return InspectionHolderDay(binding)
            }
        }

        fun bind(item: Inspection) {
            binding.apply {
                textExecutor.text = item.executor
                textNameContent.text = item.content
                textTimeSpending.text = item.timeSpending
            }
        }
    }

    class InspectionHolderNight(val binding: ItemInspectionScheduleNightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): InspectionHolderNight {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemInspectionScheduleNightBinding.inflate(layoutInflater, parent, false)
                return InspectionHolderNight(binding)
            }
        }

        fun bind(item: Inspection) {
            binding.apply {
                textExecutor.text = item.executor
                textNameContent.text = item.content
                textTimeSpending.text = item.timeSpending
            }
        }
    }

    class InspectionHolderWithoutNumber(val binding: ItemInspectionScheduleWithoutNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): InspectionHolderWithoutNumber {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemInspectionScheduleWithoutNumberBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return InspectionHolderWithoutNumber(binding)
            }
        }

        fun bind(item: Inspection) {
            binding.apply {
                textExecutor.text = item.executor
                textNameContent.text = item.content
                textTimeSpending.text = item.timeSpending
            }
        }
    }
}