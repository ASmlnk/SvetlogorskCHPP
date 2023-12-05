package com.example.svetlogorskchpp.electricMotor

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor

class ElectricMotorItemCallback: DiffUtil.ItemCallback<ElectricMotor>() {
    override fun areItemsTheSame(oldItem: ElectricMotor, newItem: ElectricMotor): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ElectricMotor, newItem: ElectricMotor): Boolean =
        oldItem == newItem
}