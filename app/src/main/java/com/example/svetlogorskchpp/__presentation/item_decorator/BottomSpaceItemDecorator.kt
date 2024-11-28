package com.example.svetlogorskchpp.__presentation.item_decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BottomSpaceItemDecorator(private val bottomSpaceDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
    /*    // Преобразуем dp в пиксели
        val bottomSpacePx = view.context.resources.displayMetrics.density * bottomSpaceDp

        // Проверяем, является ли элемент последним
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == itemCount - 1) {
            // Если это последний элемент, добавляем отступ снизу
            outRect.bottom = bottomSpacePx.toInt()
        } else {
            // Для остальных элементов отступ снизу не добавляем
            outRect.bottom = 0
        }*/

        // Задаем отступы для всех элементов
        outRect.left = 0
        outRect.right = 0
        outRect.top = 0
        outRect.bottom = 0

        // Убираем нижний отступ у последнего элемента
        if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = bottomSpaceDp
        }
    }
}