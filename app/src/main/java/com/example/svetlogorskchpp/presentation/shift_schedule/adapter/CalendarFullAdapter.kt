package com.example.svetlogorskchpp.presentation.shift_schedule.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemFullCalendarMonthBinding
import com.example.svetlogorskchpp.databinding.ItemFullCalendarPrevMonthBinding
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.domain.model.MonthCalendar
import com.example.svetlogorskchpp.domain.model.Shift

class CalendarFullAdapter(private val listener: (calendarFullDayDateModel: CalendarFullDayModel, position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<CalendarFullDayModel>()
    private var shift = Shift.NO_SHIFT

    class CalendarMonthViewHolder(val binding: ItemFullCalendarMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(calendarDateModel: CalendarFullDayModel, shift: Shift) {

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
                if (calendarDateModel.dateDay) {
                    itemLayout.background =
                        itemView.context.getDrawable(R.drawable.background_callendar_day_actual)
                }
                if (calendarDateModel.calendarDayWeekend) {
                    tvCalendarDate.setTextColor(itemView.context.getColor(R.color.orange_zero_vision))
                }
                itemView.setOnClickListener {
                    //listener.invoke(calendarDateModel, adapterPosition)
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
            2 -> CalendarMonthViewHolder.inflateFrom(parent)
            else -> CalendarPrevMonthViewHolder.inflateFrom(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CalendarMonthViewHolder -> holder.bind(list[position], shift)
            is CalendarPrevMonthViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].month) {
            MonthCalendar.PREV_MONTH -> 1
            MonthCalendar.ACTUAL_MONTH -> 2
            MonthCalendar.NEXT_MONTH -> 3
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(calendarList: List<CalendarFullDayModel>, selectShift: Shift) {
        list.clear()
        list.addAll(calendarList)
        shift = selectShift
        notifyDataSetChanged()
    }

    companion object {
        fun shift(shift: Shift): String {
            return when (shift) {
                Shift.A_SHIFT -> "А"
                Shift.B_SHIFT -> "Б"
                Shift.C_SHIFT -> "В"
                Shift.D_SHIFT -> "Г"
                Shift.NO_SHIFT -> ""
            }
        }
    }
}