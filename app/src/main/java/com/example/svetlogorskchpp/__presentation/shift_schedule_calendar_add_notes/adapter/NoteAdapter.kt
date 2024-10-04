package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.databinding.ItemCalendarNoteRequestWorkBinding
import com.example.svetlogorskchpp.databinding.ItemSwipeCalendarNoteBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class NoteAdapter(
    private val onClickDelete: (noteMy: Note) -> Unit,
) :
    ListAdapter<Note, RecyclerView.ViewHolder>(ItemNoteCallback()) {

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> NoteHolder.inflateFrom(parent)
            else -> NoteRequestWorkHolder.inflateFrom(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Note.NoteMy -> (holder as NoteHolder).bind(item, onClickDelete)
            is Note.NoteRequestWork -> (holder as NoteRequestWorkHolder).bind(expandedPositions,item, onClickDelete)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Note.NoteMy -> 0
            is Note.NoteRequestWork -> 1
        }
    }

    class NoteHolder(val binding: ItemSwipeCalendarNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(item: Note.NoteMy, onClickDelete: (noteMy: Note.NoteMy) -> Unit) {
            binding.apply {
                swipeItemNote.apply {
                    showMode = SwipeLayout.ShowMode.LayDown
                    addDrag(SwipeLayout.DragEdge.Right, binding.swipeRightNote)
                    addSwipeListener(object : SwipeLayout.SwipeListener {
                        override fun onStartOpen(layout: SwipeLayout?) {}
                        override fun onOpen(layout: SwipeLayout?) {}
                        override fun onStartClose(layout: SwipeLayout?) {}
                        override fun onClose(layout: SwipeLayout?) {}
                        override fun onUpdate(
                            layout: SwipeLayout?,
                            leftOffset: Int,
                            topOffset: Int,
                        ) {
                        }

                        override fun onHandRelease(
                            layout: SwipeLayout?,
                            xvel: Float,
                            yvel: Float,
                        ) {
                        }
                    })
                }
                deleteNote.setOnClickListener {
                    onClickDelete(item)
                }
                tvContet.text = item.content
                tvTime.text = SimpleDateFormat("HH:mm").format(item.dateNotes.time)
                tvTime.isGone = !item.isTimeNotes
            }
        }

        companion object {
            fun inflateFrom(parentContext: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflate = LayoutInflater.from(parentContext.context)
                val binding =
                    ItemSwipeCalendarNoteBinding.inflate(
                        layoutInflate,
                        parentContext,
                        false
                    )
                return NoteHolder(binding)
            }
        }
    }

    class NoteRequestWorkHolder(val binding: ItemCalendarNoteRequestWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expandedPosition:  Int ,item: Note.NoteRequestWork, onClickDelete: (noteMy: Note.NoteMy) -> Unit) {
            val isExpanded = position == expandedPosition

            binding.apply {

                layoutButton.visibility = if (isExpanded) View.VISIBLE else View.GONE

                tvNumberRequest.text = item.numberRequestWork
                tvReason.text = item.reason + "\n" + item.additionally
                tvAccession.text = item.accession
                tvDateRequestWork.text =
                    calendarToString(item.dateOpen) + "\n" + calendarToString(item.dateClose)
                tvExtend.text = item.contentExtend
                tvExtend.isGone = !item.isExtend
                tvPermission.text = item.permission.entity

                itemView.setOnClickListener {
                    val previousExpandedPosition = expandedPosition
                    expandedPosition = if (isExpanded) -1 else position
                    this@NoteRequestWorkHolder.notifyItemChanged(previousExpandedPosition)
                    notifyItemChanged(position)
                }

                when (item.permission) {
                    PermissionRequestWork.OTHER -> tvPermission.isGone = true
                    PermissionRequestWork.DISPATCHER -> {
                        tvNumberRequest.setTextColor(itemView.resources.getColor(R.color.text_input_assembly))
                        tvPermission.background =
                            itemView.resources.getDrawable(R.drawable.background_request_work_text_permission_d)
                    }

                    PermissionRequestWork.CHIEF_ENGINEER -> {
                        tvNumberRequest.setTextColor(itemView.resources.getColor(R.color.color_request_work_calendar))
                        tvPermission.background =
                            itemView.resources.getDrawable(R.drawable.background_request_work_text_permission_c)
                    }
                }
            }
        }

        private fun calendarToString(calendar: Calendar): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy  HH:mm")
            return sdf.format(calendar.time)
        }


        companion object {
            fun inflateFrom(parentContext: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflate = LayoutInflater.from(parentContext.context)
                val binding =
                    ItemCalendarNoteRequestWorkBinding.inflate(
                        layoutInflate,
                        parentContext,
                        false
                    )
                return NoteRequestWorkHolder(binding)
            }
        }
    }
}

fun View.expand() {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = measuredHeight

    layoutParams.height = 0
    visibility = View.VISIBLE

    val animator = ValueAnimator.ofInt(0, targetHeight).apply {
        addUpdateListener { valueAnimator ->
            layoutParams.height = valueAnimator.animatedValue as Int
            requestLayout()
        }
        duration = 300
    }
    animator.start()
}

fun View.collapse() {
    val initialHeight = measuredHeight

    val animator = ValueAnimator.ofInt(initialHeight, 0).apply {
        addUpdateListener { valueAnimator ->
            layoutParams.height = valueAnimator.animatedValue as Int
            requestLayout()
        }
        duration = 300
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
        })
    }
    animator.start()
}
