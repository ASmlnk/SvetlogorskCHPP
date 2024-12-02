package com.example.svetlogorskchpp.__presentation.home_page.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElMotorChapter

class ItemElMotorChapterCallback: DiffUtil.ItemCallback<ElMotorChapter>() {
    override fun areItemsTheSame(
        oldItem: ElMotorChapter,
        newItem: ElMotorChapter,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ElMotorChapter,
        newItem: ElMotorChapter,
    ): Boolean {
        return oldItem == newItem
    }
}