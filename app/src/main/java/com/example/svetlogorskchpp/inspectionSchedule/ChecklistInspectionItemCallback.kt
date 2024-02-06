package com.example.svetlogorskchpp.inspectionSchedule

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.inspectionSchedule.ChecklistInspection

class ChecklistInspectionItemCallback: DiffUtil.ItemCallback<ChecklistInspection>() {
    override fun areItemsTheSame(
        oldItem: ChecklistInspection,
        newItem: ChecklistInspection
    ): Boolean {
        return oldItem.nameCheck== newItem.nameCheck
    }

    override fun areContentsTheSame(
        oldItem: ChecklistInspection,
        newItem: ChecklistInspection
    ): Boolean {
        return oldItem == newItem
    }
}