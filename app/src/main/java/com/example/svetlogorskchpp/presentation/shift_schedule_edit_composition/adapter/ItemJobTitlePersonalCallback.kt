package com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.JobTitlePersonal

class ItemJobTitlePersonalCallback: DiffUtil.ItemCallback<JobTitlePersonal>() {
    override fun areContentsTheSame(oldItem: JobTitlePersonal, newItem: JobTitlePersonal) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: JobTitlePersonal, newItem: JobTitlePersonal) =
        oldItem.jobTitle == newItem.jobTitle

    override fun getChangePayload(oldItem: JobTitlePersonal, newItem: JobTitlePersonal): Any? {
        val diff = mutableListOf<String>()
        if (oldItem != newItem) {
            if (oldItem.namePersonalShiftA != newItem.namePersonalShiftA) diff.add("A")
            if (oldItem.namePersonalShiftB != newItem.namePersonalShiftB) diff.add("B")
            if (oldItem.namePersonalShiftC != newItem.namePersonalShiftC) diff.add("C")
            if (oldItem.namePersonalShiftD != newItem.namePersonalShiftD) diff.add("D")
            if (oldItem.namePersonalShiftE != newItem.namePersonalShiftE) diff.add("E")
        }
        return if (diff.isNotEmpty()) diff else null
    }
}