package com.example.svetlogorskchpp.inspectionSchedule

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection

class InspectionCallback: DiffUtil.ItemCallback<Inspection>() {
    override fun areItemsTheSame(oldItem: Inspection, newItem: Inspection): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Inspection, newItem: Inspection): Boolean {
        return oldItem == newItem
    }
}