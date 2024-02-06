package com.example.svetlogorskchpp.inspectionSchedule

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection
import com.example.svetlogorskchpp.model.inspectionSchedule.InspectionChecklist

class InspectionItemCallback: DiffUtil.ItemCallback<InspectionChecklist>() {
    override fun areItemsTheSame(oldItem: InspectionChecklist, newItem: InspectionChecklist): Boolean {
       return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(oldItem: InspectionChecklist, newItem: InspectionChecklist): Boolean {
        return oldItem == newItem
    }
}