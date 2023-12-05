package com.example.svetlogorskchpp.openSwitchgear

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.powerLines.PowerLines

class PowerLinesItemCallback: DiffUtil.ItemCallback<PowerLines>() {
    override fun areItemsTheSame(oldItem: PowerLines, newItem: PowerLines) =
        (oldItem.name == newItem.name)

    override fun areContentsTheSame(oldItem: PowerLines, newItem: PowerLines) = (oldItem == newItem)
}