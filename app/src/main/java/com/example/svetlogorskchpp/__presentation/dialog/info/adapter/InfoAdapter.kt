package com.example.svetlogorskchpp.__presentation.dialog.info.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemInfoContentBinding
import com.example.svetlogorskchpp.inspectionSchedule.ItemStringCallback

class InfoAdapter: ListAdapter<String, InfoContentHolder>(ItemStringCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InfoContentHolder {
        return InfoContentHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(
        holder: InfoContentHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}

class InfoContentHolder (val binding: ItemInfoContentBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.tvTextContent.text = HtmlCompat.fromHtml(item, HtmlCompat.FROM_HTML_MODE_LEGACY )
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): InfoContentHolder {
            val inflate = LayoutInflater.from(parentContext.context)
            val binding = ItemInfoContentBinding.inflate(inflate, parentContext, false)
            return InfoContentHolder(binding)
        }
    }
}