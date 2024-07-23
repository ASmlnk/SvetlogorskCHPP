package com.example.svetlogorskchpp.presentation.shift_schedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R

class CalendarFullAdapter(private val listener: (calendarFullDayDateModel: CalendarFullDayModel, position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<CalendarFullDayModel>()

    inner class CalendarMonthViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(calendarDateModel: CalendarFullDayModel) {
            val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
            val prevNightShift = itemView.findViewById<TextView>(R.id.text_prev_night_shift)
            val dayShift = itemView.findViewById<TextView>(R.id.text_day_shift)
            val nextNightShift = itemView.findViewById<TextView>(R.id.text_nex_night_shift)
            val imagePrev = itemView.findViewById<LinearLayout>(R.id.image_prev_night_shift)
            val imageNext = itemView.findViewById<LinearLayout>(R.id.image_nex_night_shift)
            val item = itemView.findViewById<LinearLayout>(R.id.item_layout)

            prevNightShift.text = shift(calendarDateModel.prevNightShift)
            dayShift.text = shift(calendarDateModel.dayShift)
            nextNightShift.text = shift(calendarDateModel.nextNightShift)

            calendarDate.text = calendarDateModel.calendarDate

            if (calendarDateModel.prevNightShift == Shift.A_SHIFT) {
              //  prevNightShift.setTextColor(itemView.context.getColor(R.color.red_danger))
                imagePrev.background = itemView.context.getDrawable(R.drawable.background_calendar_select_shift_night)
                imagePrev.alpha = 0.6F
            }
            if (calendarDateModel.dayShift == Shift.A_SHIFT) {
              //  prevNightShift.setTextColor(itemView.context.getColor(R.color.red_danger))
                dayShift.background = itemView.context.getDrawable(R.drawable.background_calendar_select_shift_day)
                dayShift.alpha = 0.85F
            }
            if (calendarDateModel.nextNightShift == Shift.A_SHIFT) {
              //  prevNightShift.setTextColor(itemView.context.getColor(R.color.red_danger))
                imageNext.background = itemView.context.getDrawable(R.drawable.background_calendar_select_shift_night)
                imageNext.alpha = 0.6F
            }
            if (calendarDateModel.dateDay) {
                calendarDate.background = itemView.context.getDrawable(R.drawable.zerovision_green_gradient)
            }
            if (calendarDateModel.calendarDayWeekend) {
                calendarDate.setTextColor( itemView.context.getColor(R.color.orange_zero_vision))
            }

            itemView.setOnClickListener {
                //listener.invoke(calendarDateModel, adapterPosition)
            }
        }
    }

    inner class CalendarPrevMonthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(calendarDateModel: CalendarFullDayModel) {
            val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
            calendarDate.text = calendarDateModel.calendarDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_full_calendar_month, parent, false)
                CalendarMonthViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_full_calendar_prev_month, parent, false)
                CalendarPrevMonthViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CalendarMonthViewHolder -> holder.bind(list[position])
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
    fun setData(calendarList: List<CalendarFullDayModel>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }

    fun shift(shift: Shift): String {
        return when(shift) {
           Shift.A_SHIFT -> "А"
           Shift.B_SHIFT -> "Б"
           Shift.C_SHIFT -> "В"
           Shift.D_SHIFT -> "Г"
        }
    }
}