package com.example.svetlogorskchpp.__presentation.shift_schedule.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R


class ItemOffsetDecoration(private val context: Context) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildLayoutPosition(view)

            outRect.bottom = context.resources.getDimensionPixelSize(R.dimen.calendar_item_margin)
            outRect.left = context.resources.getDimensionPixelSize(R.dimen.calendar_item_margin)

    }


}