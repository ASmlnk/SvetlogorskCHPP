package com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemShiftScheduleEditCompositionBinding
import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model.JobTitlePersonal

class JobTitlePersonalAdapter(
    var adapterStaff: StaffListAdapter,
    private val onSave: (jobTitlePersonal: JobTitlePersonal) -> Unit,
) :
    ListAdapter<JobTitlePersonal, JobTitlePersonalAdapter.JobTitlePersonalHolder>(
        ItemJobTitlePersonalCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobTitlePersonalHolder =
        JobTitlePersonalHolder.inflateFrom(parent)

    override fun onBindViewHolder(
        holder: JobTitlePersonalHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        val item = getItem(position)
        if (payloads.isEmpty()) {

            holder.bind(item, onSave, adapterStaff)
        } else {

            holder.binding.apply {
                payloads.forEach { payload ->
                    if (payload is String) {
                        when (payload) {
                            "A" -> tvNamePersonalShiftA.text = item.namePersonalShiftA
                            "B" -> tvNamePersonalShiftB.text = item.namePersonalShiftB
                            "C" -> tvNamePersonalShiftC.text = item.namePersonalShiftC
                            "D" -> tvNamePersonalShiftD.text = item.namePersonalShiftD
                            "E" -> tvNamePersonalShiftE.text = item.namePersonalShiftE
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: JobTitlePersonalHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onSave, adapterStaff)
    }

    class JobTitlePersonalHolder(val binding: ItemShiftScheduleEditCompositionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var editShift = Shift.NO_SHIFT

        fun bind(
            jobTitlePersonal: JobTitlePersonal,
            onSave: (jobTitlePersonal: JobTitlePersonal) -> Unit,
            adapter: StaffListAdapter,
        ) {

            binding.apply {

                autoComplete.setAdapter(adapter)
                autoComplete.setOnItemClickListener { _, _, position, _ ->
                    val selectedName = adapter.getItem(position)
                    autoComplete.setText(selectedName.name)
                }

                tvJobTitle.text = jobTitlePersonal.jobTitle.nameApp
                tvNamePersonalShiftA.text = jobTitlePersonal.namePersonalShiftA
                tvNamePersonalShiftB.text = jobTitlePersonal.namePersonalShiftB
                tvNamePersonalShiftC.text = jobTitlePersonal.namePersonalShiftC
                tvNamePersonalShiftD.text = jobTitlePersonal.namePersonalShiftD
                tvNamePersonalShiftE.text = jobTitlePersonal.namePersonalShiftE

                ivNamePersonalShiftEditA.setOnClickListener {
                    viewAutoComplete(true)
                    autoComplete.setText(tvNamePersonalShiftA.text)
                    editShift = Shift.A_SHIFT
                    colorSelectEdit()
                }
                ivNamePersonalShiftEditB.setOnClickListener {
                    viewAutoComplete(true)
                    autoComplete.setText(tvNamePersonalShiftB.text)
                    editShift = Shift.B_SHIFT
                    colorSelectEdit()
                }
                ivNamePersonalShiftEditC.setOnClickListener {
                    viewAutoComplete(true)
                    autoComplete.setText(tvNamePersonalShiftC.text)
                    editShift = Shift.C_SHIFT
                    colorSelectEdit()
                }
                ivNamePersonalShiftEditD.setOnClickListener {
                    viewAutoComplete(true)
                    autoComplete.setText(tvNamePersonalShiftD.text)
                    editShift = Shift.D_SHIFT
                    colorSelectEdit()
                }
                ivNamePersonalShiftEditE.setOnClickListener {
                    viewAutoComplete(true)
                    autoComplete.setText(tvNamePersonalShiftE.text)
                    editShift = Shift.E_SHIFT
                    colorSelectEdit()
                }

                ivSave.setOnClickListener {
                    colorDefault()
                    save()
                    val saveJobTitlePersonal = jobTitlePersonal.copy(
                        namePersonalShiftA = tvNamePersonalShiftA.text.toString(),
                        namePersonalShiftB = tvNamePersonalShiftB.text.toString(),
                        namePersonalShiftC = tvNamePersonalShiftC.text.toString(),
                        namePersonalShiftD = tvNamePersonalShiftD.text.toString(),
                        namePersonalShiftE = tvNamePersonalShiftE.text.toString(),
                    )
                    onSave(saveJobTitlePersonal)
                }
            }
        }

        private fun viewAutoComplete(isGone: Boolean) {
            binding.apply {
                autoComplete.isGone = !isGone
                ivSave.isGone = !isGone
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun colorSelectEdit() {
            binding.apply {
                when (editShift) {
                    Shift.A_SHIFT -> {
                        colorDefault()
                        ivNamePersonalShiftEditA.setImageDrawable(itemView.context.getDrawable(R.drawable.baseline_edit_red))
                        tvNamePersonalShiftA.setTextColor(itemView.context.getColor(R.color.red_danger))
                    }
                    Shift.B_SHIFT -> {
                        colorDefault()
                        ivNamePersonalShiftEditB.setImageDrawable(itemView.context.getDrawable(R.drawable.baseline_edit_red))
                        tvNamePersonalShiftB.setTextColor(itemView.context.getColor(R.color.red_danger))
                    }
                    Shift.C_SHIFT -> {
                        colorDefault()
                        ivNamePersonalShiftEditC.setImageDrawable(itemView.context.getDrawable(R.drawable.baseline_edit_red))
                        tvNamePersonalShiftC.setTextColor(itemView.context.getColor(R.color.red_danger))
                    }
                    Shift.D_SHIFT -> {
                        colorDefault()
                        ivNamePersonalShiftEditD.setImageDrawable(itemView.context.getDrawable(R.drawable.baseline_edit_red))
                        tvNamePersonalShiftD.setTextColor(itemView.context.getColor(R.color.red_danger))
                    }
                    Shift.E_SHIFT -> {
                        colorDefault()
                        ivNamePersonalShiftEditE.setImageDrawable(itemView.context.getDrawable(R.drawable.baseline_edit_red))
                        tvNamePersonalShiftE.setTextColor(itemView.context.getColor(R.color.red_danger))
                    }
                    Shift.NO_SHIFT -> return
                }
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun colorDefault() {
            binding.apply {
                ivNamePersonalShiftEditA.setImageDrawable(itemView.context.getDrawable(R.drawable.shift_schedule_edit_click))
                tvNamePersonalShiftA.setTextColor(itemView.context.getColor(R.color.name_devices_appbar))
                ivNamePersonalShiftEditB.setImageDrawable(itemView.context.getDrawable(R.drawable.shift_schedule_edit_click))
                tvNamePersonalShiftB.setTextColor(itemView.context.getColor(R.color.name_devices_appbar))
                ivNamePersonalShiftEditC.setImageDrawable(itemView.context.getDrawable(R.drawable.shift_schedule_edit_click))
                tvNamePersonalShiftC.setTextColor(itemView.context.getColor(R.color.name_devices_appbar))
                ivNamePersonalShiftEditD.setImageDrawable(itemView.context.getDrawable(R.drawable.shift_schedule_edit_click))
                tvNamePersonalShiftD.setTextColor(itemView.context.getColor(R.color.name_devices_appbar))
                ivNamePersonalShiftEditE.setImageDrawable(itemView.context.getDrawable(R.drawable.shift_schedule_edit_click))
                tvNamePersonalShiftE.setTextColor(itemView.context.getColor(R.color.name_devices_appbar))
            }
        }

        private fun save() {
            binding.apply {
                viewAutoComplete(false)
                val textEdit = autoComplete.text

                when (editShift) {
                    Shift.A_SHIFT -> {
                        tvNamePersonalShiftA.text = textEdit
                    }

                    Shift.B_SHIFT -> {
                        tvNamePersonalShiftB.text = textEdit
                    }

                    Shift.C_SHIFT -> {
                        tvNamePersonalShiftC.text = textEdit
                    }

                    Shift.D_SHIFT -> {
                        tvNamePersonalShiftD.text = textEdit
                    }

                    Shift.E_SHIFT -> {
                        tvNamePersonalShiftE.text = textEdit
                    }

                    Shift.NO_SHIFT -> return
                }
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): JobTitlePersonalHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemShiftScheduleEditCompositionBinding.inflate(layoutInflater, parent, false)
                return JobTitlePersonalHolder(binding)
            }
        }
    }
}