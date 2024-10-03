package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter

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
        if (position == state.itemCount -1) {
            outRect.bottom = bottomOffset
        }
    }
}