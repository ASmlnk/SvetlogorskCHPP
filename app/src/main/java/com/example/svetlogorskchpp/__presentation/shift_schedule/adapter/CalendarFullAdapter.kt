package com.example.svetlogorskchpp.__presentation.shift_schedule.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemFullCalendarMonthBinding
import com.example.svetlogorskchpp.databinding.ItemFullCalendarMonthV2Binding
import com.example.svetlogorskchpp.databinding.ItemFullCalendarPrevMonthBinding
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.__domain.model.MonthCalendar
import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.AdapterUiState

class CalendarFullAdapter(
    private val onClick: (calendarDateModel: CalendarFullDayModel) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<CalendarFullDayModel>()

    // private var shift = Shift.NO_SHIFT
    private var stateUi = AdapterUiState()

    class CalendarMonthViewHolder(val binding: ItemFullCalendarMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(
            calendarDateModel: CalendarFullDayModel,
            shift: Shift,
            onClick: (calendarDateModel: CalendarFullDayModel) -> Unit,
        ) {

            binding.apply {
                textPrevNightShift.text = shift(calendarDateModel.prevNightShift)
                textDayShift.text = shift(calendarDateModel.dayShift)
                textNexNightShift.text = shift(calendarDateModel.nextNightShift)

                tvCalendarDate.text = calendarDateModel.calendarDate

                if (calendarDateModel.prevNightShift == shift) {
                    imagePrevNightShift.apply {
                        background =
                            itemView.context.getDrawable(R.drawable.background_calendar_select_shift_night)
                        alpha = 0.6F
                    }
                }
                if (calendarDateModel.dayShift == shift) {
                    textDayShift.apply {
                        background =
                            itemView.context.getDrawable(R.drawable.background_calendar_select_shift_day)
                        alpha = 0.85F
                    }
                }
                if (calendarDateModel.nextNightShift == shift) {
                    imageNexNightShift.apply {
                        background =
                            itemView.context.getDrawable(R.drawable.background_calendar_select_shift_night)
                        alpha = 0.6F
                    }
                }

                if (calendarDateModel.calendarDayWeekend) {
                    tvCalendarDate.setTextColor(itemView.context.getColor(R.color.orange_zero_vision))
                }
                if (calendarDateModel.calendarNoteTag?.isTechnical == true) {
                    textDayShift.apply {
                        background = itemView.context.getDrawable(R.drawable.background_calendar_select_technical)
                        alpha = 0.85F
                    }
                }

                if (calendarDateModel.calendarNoteTag?.isNotes == true) {
                    tvCalendarDate.apply {
                        background = itemView.context.getDrawable(R.drawable.background_calendar_select_note)
                        alpha = 0.85F
                    }
                    itemLayout.background = itemView.context.getDrawable(R.drawable.background_layout_calendar_select_note)
                }

                if (calendarDateModel.dateDay) {
                    itemLayout.background =
                        itemView.context.getDrawable(R.drawable.background_callendar_day_actual)
                }

                itemView.setOnClickListener {
                    onClick(calendarDateModel)
                }
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): CalendarMonthViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFullCalendarMonthBinding.inflate(layoutInflater, parent, false)
                return CalendarMonthViewHolder(binding)
            }
        }
    }

    class CalendarMonthViewHolderV2(val binding: ItemFullCalendarMonthV2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(
            calendarDateModel: CalendarFullDayModel,
            shift: Shift,
            onClick: (calendarDateModel: CalendarFullDayModel) -> Unit,
        ) {
            val shiftText = shift(calendarDateModel, shift)

            binding.apply {
                textShift.text = shiftText

                tvCalendarDate.text = calendarDateModel.calendarDate

                if (shiftText == "4" || shiftText == "8/4" || shiftText == "8") {
                    layoutShift.apply {
                        background =
                            itemView.context.getDrawable(R.drawable.background_calendar_select_shift_night)
                        alpha = 0.6F
                    }
                }
                if (shiftText == "12") {
                    layoutShift.apply {
                        background =
                            itemView.context.getDrawable(R.drawable.background_calendar_select_shift_day)
                        alpha = 0.85F
                    }
                }


                if (calendarDateModel.calendarDayWeekend) {
                    tvCalendarDate.setTextColor(itemView.context.getColor(R.color.orange_zero_vision))
                }
                if (calendarDateModel.calendarNoteTag?.isTechnical == true) {
                    textShift.text = "ТУ"
                    layoutShift.apply {
                        background = itemView.context.getDrawable(R.drawable.background_calendar_select_technical)
                        alpha = 0.85F
                    }
                }

                if (calendarDateModel.calendarNoteTag?.isNotes == true) {
                    tvCalendarDate.apply {
                        background = itemView.context.getDrawable(R.drawable.background_calendar_select_note)
                        alpha = 0.85F
                    }
                    itemLayout.background = itemView.context.getDrawable(R.drawable.background_layout_calendar_select_note)
                }

                if (calendarDateModel.dateDay) {
                    itemLayout.background =
                        itemView.context.getDrawable(R.drawable.background_callendar_day_actual)
                }
                itemView.setOnClickListener {
                    onClick(calendarDateModel)
                }
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): CalendarMonthViewHolderV2 {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFullCalendarMonthV2Binding.inflate(layoutInflater, parent, false)
                return CalendarMonthViewHolderV2(binding)
            }
        }
    }

    class CalendarPrevMonthViewHolder(val binding: ItemFullCalendarPrevMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): CalendarPrevMonthViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemFullCalendarPrevMonthBinding.inflate(layoutInflater, parent, false)
                return CalendarPrevMonthViewHolder(binding)
            }
        }

        fun bind(calendarDateModel: CalendarFullDayModel) {
            binding.tvCalendarDate.text = calendarDateModel.calendarDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> CalendarMonthViewHolder.inflateFrom(parent)
            2 -> CalendarMonthViewHolderV2.inflateFrom(parent)
            else -> CalendarPrevMonthViewHolder.inflateFrom(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CalendarMonthViewHolder -> holder.bind(list[position], stateUi.shift, onClick)
            is CalendarMonthViewHolderV2 -> holder.bind(list[position], stateUi.shift, onClick)
            is CalendarPrevMonthViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            list[position].month == MonthCalendar.PREV_MONTH -> 3
            list[position].month == MonthCalendar.ACTUAL_MONTH && stateUi.calendarView == "1" -> 1
            list[position].month == MonthCalendar.NEXT_MONTH -> 3
            else -> 2

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(calendarList: List<CalendarFullDayModel>, state: AdapterUiState) {
        list.clear()
        list.addAll(calendarList)
        stateUi = state
        notifyDataSetChanged()
    }

    companion object {
        fun shift(shift: Shift): String {
            return when (shift) {
                Shift.A_SHIFT -> "А"
                Shift.B_SHIFT -> "Б"
                Shift.C_SHIFT -> "В"
                Shift.D_SHIFT -> "Г"
                Shift.E_SHIFT -> "Д"
                Shift.NO_SHIFT -> ""
            }
        }

        fun shift(calendarDateModel: CalendarFullDayModel, shift: Shift): String {
            return if (calendarDateModel.dayShift == shift) {
                "12"
            } else if (calendarDateModel.nextNightShift == shift && calendarDateModel.prevNightShift == shift) {
                "8/4"
            } else if (calendarDateModel.nextNightShift == shift && calendarDateModel.prevNightShift != shift) {
                "4"
            } else if (calendarDateModel.nextNightShift != shift && calendarDateModel.prevNightShift == shift) {
                "8"
            } else "В"
        }
    }
}