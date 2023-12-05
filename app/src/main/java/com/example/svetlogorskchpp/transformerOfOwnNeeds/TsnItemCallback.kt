package com.example.svetlogorskchpp.transformerOfOwnNeeds

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.transformerNeeds.Tsn

class TsnItemCallback: DiffUtil.ItemCallback<Tsn>() {
    override fun areItemsTheSame(oldItem: Tsn, newItem: Tsn): Boolean {
       return oldItem.nameTsn == newItem.nameTsn
    }

    override fun areContentsTheSame(oldItem: Tsn, newItem: Tsn): Boolean {
        return oldItem == newItem
    }
}