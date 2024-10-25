package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemProtectionBinding

class ProtectionEditAdapter(
    private val onClickDelete: (itemProtection: String) -> Unit,
) : ListAdapter<String, ProtectionEditAdapter.ProtectionHolder>(ItemStringCallback()) {

    class ProtectionHolder(val binding: ItemProtectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item:String, onClickDelete: (itemProtection: String) -> Unit) {
                binding.apply {
                    tvProtection.text = item
                    ivDelete.setOnClickListener {
                        onClickDelete(item)
                    }
                }
            }

        companion object {
            fun inflateFrom(parentContext: ViewGroup): ProtectionHolder {
                val layoutInflater = LayoutInflater.from(parentContext.context)
                val binding = ItemProtectionBinding.inflate(layoutInflater, parentContext, false)
                return ProtectionHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ProtectionHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProtectionHolder =
        ProtectionHolder.inflateFrom(parent)
}

