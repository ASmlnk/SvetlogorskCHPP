package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemShiftScheduleShiftPersonalDialogBinding
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_edit_composition.adapter.ItemJobTitlePersonalCallback
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_edit_composition.model.JobTitlePersonal

class ShiftPersonalAdapter(private val shift: Shift) :
    ListAdapter<JobTitlePersonal, ShiftPersonalAdapter.ShiftPersonalHolder>(
        ItemJobTitlePersonalCallback()
    ) {

    override fun onBindViewHolder(holder: ShiftPersonalHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, shift)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShiftPersonalHolder.layoutFrom(parent)

    class ShiftPersonalHolder(val binding: ItemShiftScheduleShiftPersonalDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(jobTitlePersonal: JobTitlePersonal, shift: Shift) {
            binding.apply {
                tvJobTitle.text = jobTitlePersonal.jobTitle.nameApp
                tvNamePersonalShift.text = filterShiftPersonal(jobTitlePersonal, shift)

            }
        }

        private fun filterShiftPersonal(jobTitlePersonal: JobTitlePersonal, shift: Shift): String {
            return when(shift) {
                Shift.A_SHIFT -> jobTitlePersonal.namePersonalShiftA
                Shift.B_SHIFT -> jobTitlePersonal.namePersonalShiftB
                Shift.C_SHIFT -> jobTitlePersonal.namePersonalShiftC
                Shift.D_SHIFT -> jobTitlePersonal.namePersonalShiftD
                Shift.E_SHIFT -> jobTitlePersonal.namePersonalShiftE
                Shift.NO_SHIFT -> ""
            }
        }

        companion object {
                fun layoutFrom(parent: ViewGroup): ShiftPersonalHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemShiftScheduleShiftPersonalDialogBinding.inflate(layoutInflater, parent, false)
                    return ShiftPersonalHolder(binding)
                }
            }

    }
}