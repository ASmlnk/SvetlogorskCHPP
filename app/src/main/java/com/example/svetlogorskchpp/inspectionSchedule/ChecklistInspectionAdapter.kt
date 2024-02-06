package com.example.svetlogorskchpp.inspectionSchedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemNameChecklistBinding

class ChecklistInspectionAdapter(private val clickListener: (nameCategory: String) -> Unit) :
    ListAdapter<String, ChecklistInspectionAdapter.ChecklistInspectionHolder>(ItemStringCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistInspectionHolder {
        return ChecklistInspectionHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: ChecklistInspectionHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ChecklistInspectionHolder(val binding: ItemNameChecklistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ChecklistInspectionHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNameChecklistBinding.inflate(layoutInflater, parent, false)
                return ChecklistInspectionHolder(binding)
            }
        }

        fun bind(item: String, clickListener: (nameCategory: String) -> Unit) {
            binding.apply {
                textNameCategory.text = item
                root.setOnClickListener {
                    clickListener(item)
                }
            }
        }
    }
}