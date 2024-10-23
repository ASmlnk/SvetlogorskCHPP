package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar_add_notes.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemNoteAdapterDecoration(
    private val bottomOffset: Int
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = bottomOffset
        } else {
            outRect.bottom = 0
        }
    }
}