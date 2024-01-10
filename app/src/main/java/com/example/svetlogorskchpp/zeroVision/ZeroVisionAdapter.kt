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
import com.example.svetlogorskchpp.databinding.ItemDialogZeroVision3Binding
import com.example.svetlogorskchpp.databinding.ItemDialogZeroVision4Binding
import com.example.svetlogorskchpp.databinding.ItemDialogZeroVision5Binding
import com.example.svetlogorskchpp.databinding.ItemDialogZeroVision6Binding
import com.example.svetlogorskchpp.databinding.ItemDialogZeroVision7Binding

class ZeroVisionAdapter(val indexZeroVision: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (indexZeroVision) {
            "1" -> ZeroVisionHolder1.inflateFrom(parent)
            "2" -> ZeroVisionHolder2.inflateFrom(parent)
            "3" -> ZeroVisionHolder3.inflateFrom(parent)
            "4" -> ZeroVisionHolder4.inflateFrom(parent)
            "5" -> ZeroVisionHolder5.inflateFrom(parent)
            "6" -> ZeroVisionHolder6.inflateFrom(parent)
            "7" -> ZeroVisionHolder7.inflateFrom(parent)
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

            "3" -> {
                holder as ZeroVisionHolder3
                holder.bind()
            }

            "4" -> {
                holder as ZeroVisionHolder4
                holder.bind()
            }

            "5" -> {
                holder as ZeroVisionHolder5
                holder.bind()
            }

            "6" -> {
                holder as ZeroVisionHolder6
                holder.bind()
            }

            "7" -> {
                holder as ZeroVisionHolder7
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
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_1.text,
                    "• Я подаю",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_2.text,
                    "• Я следую",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_3.text,
                    "• Я немедленно",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_SUPERVISION_4.text,
                    "• Я не допускаю",
                    itemView.context
                )
            )

            val textWorker = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_WORKER_1.text,
                    "• Я несу",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_WORKER_2.text,
                    "• Я соблюдаю",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_WORKER_3.text,
                    "• Я использую",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_WORKER_4.text,
                    "• Я обращаюсь",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_1_WORKER_5.text,
                    "• Я применяю",
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
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
                textColorForegroundSpannable(
                    R.color.red_danger,
                    ZeroVisionType.ZERO_VISION_2_DANGER_1.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_danger,
                    ZeroVisionType.ZERO_VISION_2_DANGER_2.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_danger,
                    ZeroVisionType.ZERO_VISION_2_DANGER_3.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_danger,
                    ZeroVisionType.ZERO_VISION_2_DANGER_4.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_danger,
                    ZeroVisionType.ZERO_VISION_2_DANGER_5.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_danger,
                    ZeroVisionType.ZERO_VISION_2_DANGER_6.text,
                    ZeroVisionType.ZERO_VISION_2_DANGER_COLOR.text,
                    itemView.context
                )
            )

            val textSupervisor = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_1.text,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_2.text,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_3.text,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_4.text,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_COLOR_4.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_5.text,
                    ZeroVisionType.ZERO_VISION_2_SUPERVISION_COLOR_5.text, itemView.context
                )
            )


            val textWorker = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_1.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_2.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_COLOR_2.text, itemView.context
                )
            )

            val textMemo1 = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_3.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_4.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_5.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_6.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_7.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_8.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_9.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                )
            )
            val textMemo2 = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_10.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_11.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                )
            )

            val textMemo3 = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_12.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_13.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_14.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_15.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_16.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                )
            )
            val textMemo4 = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_17.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_18.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_2_WORKER_19.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                )
            )


            binding.apply {
                textZeroVisionDanger.text = textDanger
                textSupervision2.text = textSupervisor
                textWorker2.text = textWorker
                textWorkerMemo1.text = textMemo1
                textWorkerMemo2.text = textMemo2
                textWorkerMemo3.text = textMemo3
                textWorkerMemo4.text = textMemo4
                textZeroVisionEnd1.text = ZeroVisionType.ZERO_VISION_2_WORKER_20.text
                textZeroVisionEnd2.text = ZeroVisionType.ZERO_VISION_2_WORKER_21.text
            }
        }
    }

    class ZeroVisionHolder3(val binding: ItemDialogZeroVision3Binding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ZeroVisionHolder3 {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemDialogZeroVision3Binding.inflate(inflate, parent, false)
                return ZeroVisionHolder3(binding)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {

            val textPurposes = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_TARGET_1.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_TARGET_2.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                )
            )

            val textTasks = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_TASK_1.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_TASK_2.text,
                    ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text,
                    itemView.context
                )
            )


            val textSupervisor = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_1.text,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_2.text,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_3.text,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_4.text,
                    ZeroVisionType.ZERO_VISION_3_SUPERVISION_COLOR_4.text, itemView.context
                )
            )

            val textWorker = TextUtils.concat(
                textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_WORKER_1.text,
                    ZeroVisionType.ZERO_VISION_3_WORKER_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_WORKER_2.text,
                    ZeroVisionType.ZERO_VISION_3_WORKER_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_WORKER_3.text,
                    ZeroVisionType.ZERO_VISION_3_WORKER_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_WORKER_4.text,
                    ZeroVisionType.ZERO_VISION_3_WORKER_COLOR_4.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_WORKER_5.text,
                    ZeroVisionType.ZERO_VISION_3_WORKER_COLOR_5.text, itemView.context
                ), textColorForegroundSpannable(
                    R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_3_WORKER_6.text,
                    ZeroVisionType.ZERO_VISION_3_WORKER_COLOR_6.text, itemView.context
                )
            )

            binding.apply {
                textZeroVisionPurposes.text = textPurposes
                textZeroVisionTasks.text = textTasks
                textSupervision2.text = textSupervisor
                textWorker2.text = textWorker
            }
        }
    }

    class ZeroVisionHolder4(val binding: ItemDialogZeroVision4Binding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ZeroVisionHolder4 {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemDialogZeroVision4Binding.inflate(inflate, parent, false)
                return ZeroVisionHolder4(binding)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {


            val textSyotT = TextUtils.concat (
                textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_1.text,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_1.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_2.text ,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_2.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_3.text ,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_3.text  ,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_4.text,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_4.text  ,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_5.text ,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_5.text  ,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_6.text ,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_6.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_7.text ,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_7.text,
                    itemView.context
                ), textColorForegroundSpannable(
                    R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SYOT_8.text ,
                    ZeroVisionType.ZERO_VISION_4_SYOT_COLOR_8.text,
                    itemView.context
                )
            )

            val textSupervision = TextUtils.concat (
                textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SUPERVISION_1.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SUPERVISION_2.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SUPERVISION_3.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SUPERVISION_4.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_SUPERVISION_5.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                )
            )

            val textMaster = TextUtils.concat (
                textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_1.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_2.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_3.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_4.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_5.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_6.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_7.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_MASTER_8.text ,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                )
            )

            val textWorker = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_WORKER_1.text,
                    ZeroVisionType.ZERO_VISION_4_WORKER_COLOR_1.text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_WORKER_2.text,
                    ZeroVisionType.ZERO_VISION_4_WORKER_COLOR_2.text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_WORKER_3.text ,
                    ZeroVisionType.ZERO_VISION_4_WORKER_COLOR_3.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_WORKER_4.text ,
                    ZeroVisionType.ZERO_VISION_4_WORKER_COLOR_4.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_4_WORKER_5.text ,
                    "",  itemView.context
                ))



            binding.apply {
                textZeroSyot.text = textSyotT
                textZeroVisionKontrolSyotSupervision.text = textSupervision
                textZeroVisionMaster.text = textMaster
                textZeroWorker.text = textWorker

            }
        }
    }

    class ZeroVisionHolder5(val binding: ItemDialogZeroVision5Binding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ZeroVisionHolder5 {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemDialogZeroVision5Binding.inflate(inflate, parent, false)
                return ZeroVisionHolder5(binding)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {

            val textSupervisor = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_1.text ,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_COLOR_1.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_2.text ,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_COLOR_2.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_3.text ,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_COLOR_3.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_4.text ,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_COLOR_4.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_5.text ,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_COLOR_5.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_6.text ,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_COLOR_6.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_7.text ,
                    ZeroVisionType.ZERO_VISION_5_SUPERVISION_COLOR_7.text  , itemView.context
                )
            )

            val textWorker = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_WORKER_1.text ,
                    ZeroVisionType.ZERO_VISION_5_WORKER_COLOR_1 .text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_WORKER_2.text ,
                    ZeroVisionType.ZERO_VISION_5_WORKER_COLOR_2.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_WORKER_3.text ,
                    ZeroVisionType.ZERO_VISION_5_WORKER_COLOR_3.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_WORKER_4.text ,
                    ZeroVisionType.ZERO_VISION_5_WORKER_COLOR_4.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_WORKER_5.text ,
                    ZeroVisionType.ZERO_VISION_5_WORKER_COLOR_5 .text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_WORKER_6.text ,
                    ZeroVisionType.ZERO_VISION_5_WORKER_COLOR_6.text  , itemView.context
                ))

            val textEngineer = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_ENGINEER_1.text ,
                    ZeroVisionType.ZERO_VISION_5_ENGINEER_COLOR_1.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_ENGINEER_2.text ,
                    ZeroVisionType.ZERO_VISION_5_ENGINEER_COLOR_2.text  , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_5_ENGINEER_3.text ,
                    ZeroVisionType.ZERO_VISION_5_ENGINEER_COLOR_3.text  , itemView.context
                ))

            binding.apply {
                textSupervision1.text = textSupervisor
                textWorker1.text = textWorker
                textZeroEngineer.text = textEngineer
            }
        }
    }

    class ZeroVisionHolder6(val binding: ItemDialogZeroVision6Binding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ZeroVisionHolder6 {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemDialogZeroVision6Binding.inflate(inflate, parent, false)
                return ZeroVisionHolder6(binding)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {

            val textEducation = TextUtils.concat (
                textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_EDUCATION_1.text,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_EDUCATION_2.text,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_EDUCATION_3.text,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_EDUCATION_4.text,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_EDUCATION_5.text,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_EDUCATION_6.text,ZeroVisionType.ZERO_VISION_2_WORKER_ORANGE.text, itemView.context
                ))

            val textSupervision = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_1.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_2.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_3.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_4.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_4.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_5.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_5.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_6.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_6.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_7.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_7.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_8.text,
                    ZeroVisionType.ZERO_VISION_6_SUPERVISION_COLOR_8.text, itemView.context
                ))

            val textSafelyName = textColorForegroundSpannable(R.color.purple_700,
                ZeroVisionType.ZERO_VISION_6_SAFELY_NAME.text,
                ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR.text, itemView.context
            )

            val textSafely = TextUtils.concat (
                textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_1.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_2.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_3.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_4.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_4.text, itemView.context
                ))

            val textUnsafeName = textColorForegroundSpannable(R.color.red_danger,
                ZeroVisionType.ZERO_VISION_6_UNSAFE_NAME.text,
                ZeroVisionType.ZERO_VISION_6_UNSAFE_COLOR.text, itemView.context
            )

            val textUnsafe = TextUtils.concat (
                textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_UNSAFE.text, ZeroVisionType.ZERO_VISION_6_UNSAFE2.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_UNSAFE_1.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_UNSAFE_2.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_UNSAFE_3.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_UNSAFE_4.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_4.text, itemView.context
                ), textColorForegroundSpannable(R.color.orange_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_UNSAFE_5.text,
                    ZeroVisionType.ZERO_VISION_6_SAFELY_COLOR_5.text, itemView.context
                ))

            val textWorker = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_WORKER_1.text,
                    ZeroVisionType.ZERO_VISION_6_WORKER_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_WORKER_2.text,
                    ZeroVisionType.ZERO_VISION_6_WORKER_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_WORKER_3.text,
                    ZeroVisionType.ZERO_VISION_6_WORKER_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_WORKER_4.text,
                    ZeroVisionType.ZERO_VISION_6_WORKER_COLOR_4.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_WORKER_5.text,
                    ZeroVisionType.ZERO_VISION_6_WORKER_COLOR_5.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_WORKER_6.text,
                    ZeroVisionType.ZERO_VISION_6_WORKER_COLOR_6.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_6_WORKER_7.text,
                    ZeroVisionType.ZERO_VISION_6_WORKER_COLOR_7.text, itemView.context
                ))

            binding.apply {
                textZeroEducation.text = textEducation
                textSupervision1.text = textSupervision
                textViewSafelyName.text = textSafelyName
                textViewSafely.text = textSafely
                textViewUnsafelyName.text = textUnsafeName
                textViewUnsafely.text = textUnsafe
                textWorker1.text = textWorker
            }
        }
    }

    class ZeroVisionHolder7(val binding: ItemDialogZeroVision7Binding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ZeroVisionHolder7 {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemDialogZeroVision7Binding.inflate(inflate, parent, false)
                return ZeroVisionHolder7(binding)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {

            val textSupervision = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_1.text,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_COLOR_1.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_2.text,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_COLOR_2.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_3.text,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_COLOR_3.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_4.text,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_COLOR_4.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_5.text,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_COLOR_5.text, itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_6.text,
                    ZeroVisionType.ZERO_VISION_7_SUPERVISION_COLOR_6.text, itemView.context
                ))

            val textWorker = TextUtils.concat (
                textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_WORKER_1.text ,
                    ZeroVisionType.ZERO_VISION_7_WORKER_COLOR_1.text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_WORKER_2.text ,
                    ZeroVisionType.ZERO_VISION_7_WORKER_COLOR_2.text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_WORKER_3.text ,
                    ZeroVisionType.ZERO_VISION_7_WORKER_COLOR_3.text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_WORKER_4.text ,
                    ZeroVisionType.ZERO_VISION_7_WORKER_COLOR_4.text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_WORKER_5.text ,
                    ZeroVisionType.ZERO_VISION_7_WORKER_COLOR_5.text , itemView.context
                ), textColorForegroundSpannable(R.color.red_text_zero_vision,
                    ZeroVisionType.ZERO_VISION_7_WORKER_6.text ,
                    ZeroVisionType.ZERO_VISION_7_WORKER_COLOR_6.text , itemView.context
                ))


            binding.apply {
                textSupervision1.text = textSupervision
                textWorker1.text = textWorker
            }
        }
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.M)
        fun textColorForegroundSpannable(
            color: Int,
            text: String,
            textColor: String,
            context: Context
        ): SpannableStringBuilder {
            val foregroundSpannableText = SpannableStringBuilder(text)
            var startIndex = foregroundSpannableText.indexOf(textColor)
            var amountOfCharacters = textColor.length
            foregroundSpannableText.setSpan(
                ForegroundColorSpan(context.getColor(color)),
                startIndex,
                startIndex + amountOfCharacters,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return foregroundSpannableText
        }
    }
}