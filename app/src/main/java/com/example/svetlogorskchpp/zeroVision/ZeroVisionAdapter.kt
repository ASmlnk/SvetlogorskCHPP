package com.example.svetlogorskchpp.zeroVision

import android.content.Context
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemDialogZeroVision1Binding
import com.example.svetlogorskchpp.databinding.ItemDialogZeroVision2Binding

class ZeroVisionAdapter(val indexZeroVision: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when(indexZeroVision) {
            "1" -> ZeroVisionHolder1.inflateFrom(parent)
           "2" -> ZeroVisionHolder2.inflateFrom(parent)
           else -> ZeroVisionHolder1.inflateFrom(parent)
       }
    }

    override fun getItemCount(): Int = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (indexZeroVision) {
            "1" -> {
                holder as ZeroVisionHolder1
                holder.bind()
            }
            "2" -> {
                holder as ZeroVisionHolder2
                holder.bind()
            }
            else -> {
                holder as ZeroVisionHolder1
                holder.bind()
            }
        }
    }

    class ZeroVisionHolder1(val binding: ItemDialogZeroVision1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ZeroVisionHolder1 {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemDialogZeroVision1Binding.inflate(inflate, parent, false)
                return ZeroVisionHolder1(binding)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {

            val textSupervisor = TextUtils.concat(
                textColorForegroundSpannable(
                ZeroVisionType.ZERO_VISION_1_SUPERVISION_1.text,
                "• Я подаю",
                itemView.context
            ), textColorForegroundSpannable(
                ZeroVisionType.ZERO_VISION_1_SUPERVISION_2.text,
                "• Я следую",
                itemView.context
            ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_3.text,
                    "• Я немедленно",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_4.text,
                    "• Я не допускаю",
                    itemView.context
                )
            )

            val textWorker = TextUtils.concat(
                textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_1.text,
                    "• Я несу",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_2.text,
                    "• Я соблюдаю",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_3.text,
                    "• Я использую",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_4.text,
                    "• Я обращаюсь",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_5.text,
                    "• Я применяю",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_6.text,
                    "• Я внимателен",
                    itemView.context
                )
            )
            binding.apply {
                textSupervision1.text = textSupervisor
                textWorker1.text = textWorker
            }
        }
    }

    class ZeroVisionHolder2(val binding: ItemDialogZeroVision2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ZeroVisionHolder2 {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemDialogZeroVision2Binding.inflate(inflate, parent, false)
                return ZeroVisionHolder2(binding)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {

            val textDanger = TextUtils.concat(
                textColorForegroundSpannableDanger(
                    ZeroVisionType.ZERO_VISION_2_DANGER_1.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannableDanger(
                    ZeroVisionType.ZERO_VISION_2_DANGER_2.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannableDanger(
                    ZeroVisionType.ZERO_VISION_2_DANGER_3.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannableDanger(
                    ZeroVisionType.ZERO_VISION_2_DANGER_4.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannableDanger(
                    ZeroVisionType.ZERO_VISION_2_DANGER_5.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannableDanger(
                    ZeroVisionType.ZERO_VISION_2_DANGER_6.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                )
            )

            val textSupervisor = TextUtils.concat(
                textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_1.text,
                    "• Я подаю",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_2.text,
                    "• Я следую",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_3.text,
                    "• Я немедленно",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_4.text,
                    "• Я не допускаю",
                    itemView.context
                )
            )

            val textWorker = TextUtils.concat(
                textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_1.text,
                    "• Я несу",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_2.text,
                    "• Я соблюдаю",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_3.text,
                    "• Я использую",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_4.text,
                    "• Я обращаюсь",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_5.text,
                    "• Я применяю",
                    itemView.context
                ), textColorForegroundSpannable(
                    ZeroVisionType.ZERO_VISION_1_WORKER_6.text,
                    "• Я внимателен",
                    itemView.context
                )
            )
            binding.apply {
                textZeroVisionDanger.text = textDanger
                textSupervision1.text = textSupervisor
                textWorker1.text = textWorker
            }
        }
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.M)
        fun textColorForegroundSpannable(
            text: String,
            textColor: String,
            context: Context
        ): SpannableStringBuilder {
            val foregroundSpannableText = SpannableStringBuilder(text)
            var startIndex = 0
            var amountOfCharacters = textColor.length
            foregroundSpannableText.setSpan(
                ForegroundColorSpan(context.getColor(R.color.red_text_zero_vision)),
                startIndex,
                startIndex + amountOfCharacters,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return foregroundSpannableText
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun textColorForegroundSpannableDanger(
            text: String,
            textColor: String,
            context: Context
        ): SpannableStringBuilder {
            val foregroundSpannableText = SpannableStringBuilder(text)
            var startIndex = 0
            var amountOfCharacters = textColor.length
            foregroundSpannableText.setSpan(
                ForegroundColorSpan(context.getColor(R.color.red_danger)),
                startIndex,
                startIndex + amountOfCharacters,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return foregroundSpannableText
        }
    }
}