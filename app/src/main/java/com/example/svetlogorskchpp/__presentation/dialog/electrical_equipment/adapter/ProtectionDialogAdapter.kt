package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemProtectionDialogBinding
import com.example.svetlogorskchpp.inspectionSchedule.ItemStringCallback
import com.google.api.Context

class ProtectionDialogAdapter: ListAdapter<String, ProtectionHolder>(ItemStringCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProtectionHolder {
        return ProtectionHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(
        holder: ProtectionHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class ProtectionHolder (val binding: ItemProtectionDialogBinding):
RecyclerView.ViewHolder(binding.root){

    fun bind(item: String) {
        val spannableString = SpannableString(item)

        var startIndex = -1

        for (i in item.indices) {
            when (item[i]) {
                '(' -> {
                    // Если мы находим открывающую скобку, запоминаем индекс
                    startIndex = i
                }
                ')' -> {
                    // Если мы находим закрывающую скобку, применяем обычный стиль к тексту в скобках
                    if (startIndex != -1) {
                        spannableString.setSpan(
                            StyleSpan(android.graphics.Typeface.NORMAL),
                            startIndex,
                            i + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        val color = ContextCompat.getColor(itemView.context, R.color.text_content_rza_2)
                        spannableString.setSpan(
                            ForegroundColorSpan(color), // Здесь можно выбрать любой цвет
                            startIndex,
                            i + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        startIndex = -1 // Сбрасываем индекс начала
                    }
                }
            }
        }

        // Применяем жирный стиль ко всему тексту, кроме текста в скобках
        var lastEndIndex = 0
        for (i in item.indices) {
            if (item[i] == '(') {
                if (lastEndIndex < i) {
                    spannableString.setSpan(
                        StyleSpan(android.graphics.Typeface.BOLD),
                        lastEndIndex,
                        i,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                lastEndIndex = item.indexOf(')', i) + 1 // Пропускаем текст в скобках
            }
        }

        // Если остался текст после последней закрывающей скобки, делаем его жирным
        if (lastEndIndex < item.length) {
            spannableString.setSpan(
                StyleSpan(android.graphics.Typeface.BOLD),
                lastEndIndex,
                item.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Если остался текст после последней закрывающей скобки, делаем его жирным
        binding.tvProtectionContent.text = spannableString
    }

    companion object {
        fun inflateFrom(parentContext: ViewGroup): ProtectionHolder {
            val inflate = LayoutInflater.from(parentContext.context)
            val binding = ItemProtectionDialogBinding.inflate(inflate, parentContext, false)
            return ProtectionHolder(binding)
        }
    }
}