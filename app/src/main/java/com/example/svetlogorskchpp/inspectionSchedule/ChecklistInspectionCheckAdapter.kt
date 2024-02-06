package com.example.svetlogorskchpp.inspectionSchedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemDialogChecklistInspectionBinding
import com.example.svetlogorskchpp.databinding.ItemValveBinding
import com.example.svetlogorskchpp.model.inspectionSchedule.ChecklistInspection
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc

class ChecklistInspectionCheckAdapter(private val nameChecklist: String) :
    ListAdapter<ChecklistInspection, RecyclerView.ViewHolder>(
        ChecklistInspectionItemCallback()
    ) {

    override fun getItemViewType(position: Int): Int {
        return when (nameChecklist) {
            InSc.INSPECTION.get -> 1
            InSc.VALVE.get -> 2
            else -> 3
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> CheckInspectionHolder.inflateFrom(parent)
            2-> ValveHolder.inflateFrom(parent)
            else -> CheckInspectionHolder.inflateFrom(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is CheckInspectionHolder -> holder.bind(item, position)
            is ValveHolder -> holder.bind(item)
        }
    }

    class CheckInspectionHolder(val binding: ItemDialogChecklistInspectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): CheckInspectionHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemDialogChecklistInspectionBinding.inflate(layoutInflater, parent, false)
                return CheckInspectionHolder(binding)
            }
        }

        fun bind(item: ChecklistInspection, position: Int) {
            var textCheck = ""
            item.nameCheck.keys.toList().forEach { textCheck += it }
            var textDetail = ""
            item.nameCheck.values.toList().forEach { textDetail += it }

            binding.apply {
                textNameCheck.text = textCheck
                textNumberCheck.text = (position + 1).toString()
                textDetailCheck.text = textDetail

            }
        }
    }

    class ValveHolder(val binding: ItemValveBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup) : ValveHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemValveBinding.inflate(layoutInflater, parent, false)
                return ValveHolder(binding)
            }
        }

        fun bind (item: ChecklistInspection) {
            binding.apply {
                textViewValve.text = item.nameBlank
                textLocation.text = item.nameCheck[InSc.LOCATION.get]
                textControl.text = item.nameCheck[InSc.CONTROL.get]
                textScheme.text = item.nameCheck[InSc.SCHEME.get]
                textControl.isGone = item.nameCheck[InSc.CONTROL.get] == ""
                textView23.isVisible = item.nameCheck[InSc.CONTROL.get] != ""
                textView33.isVisible = item.nameCheck[InSc.SCHEME.get] != ""
                textScheme.isGone = item.nameCheck[InSc.SCHEME.get] == ""
            }
        }
    }
}