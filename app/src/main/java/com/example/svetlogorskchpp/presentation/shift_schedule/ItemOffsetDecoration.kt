package com.example.svetlogorskchpp.presentation.shift_schedule

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.compose.ui.unit.dp

import androidx.core.content.ContextCompat
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

    }


}