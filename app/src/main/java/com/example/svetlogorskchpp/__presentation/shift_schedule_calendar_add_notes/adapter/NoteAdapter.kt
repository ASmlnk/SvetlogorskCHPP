package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.Date

class NoteAdapter(
    private val date: Long,
    private val onClickDelete: (note: Note) -> Unit,
    private val onClickEdit: (note: Note) -> Unit,
) :
    ListAdapter<Note, RecyclerView.ViewHolder>(ItemNoteCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> NoteHolder.inflateFrom(parent)
            else -> NoteRequestWorkHolder.inflateFrom(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Note.NoteMy -> (holder as NoteHolder).bind(item, onClickDelete)
            is Note.NoteRequestWork -> {
                (holder as NoteRequestWorkHolder).bind(item, onClickDelete, date, onClickEdit)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        if (payloads.isNotEmpty()) {
            if (holder is NoteRequestWorkHolder) {
                // val isExpanded = position == expandedPosition
                holder.bind(
                    (getItem(position) as Note.NoteRequestWork),
                    onClickDelete,
                    date,
                    onClickEdit
                )
            }
        } else {
            onBindViewHolder(holder, position)
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
                        override fun onClose(layout: SwipeLayout?) {
                        }

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

    class NoteRequestWorkHolder(
        private val binding: ItemCalendarNoteRequestWorkBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Note.NoteRequestWork,
            onClickDelete: (noteMy: Note) -> Unit,
            date: Long,
            onClickEdit: (note: Note) -> Unit,
        ) {

            binding.apply {

                tvNumberRequest.text = item.numberRequestWork
                tvReason.text = item.reason + "\n" + item.additionally
                tvAccession.text = item.accession
                tvDateOpenRequestWork.text = calendarToString(item.dateOpen)
                tvDateCloseRequestWork.text = calendarToString(item.dateClose)
                tvExtend.text = item.contentExtend
                tvExtend.isGone = !item.isExtend
                tvPermission.text = item.permission.entity
                if (equalityDate(item.dateOpen, date)) {
                    ivOpenDate.visibility = View.VISIBLE
                    tvDateOpenRequestWork.setTextColor(itemView.resources.getColor(R.color.color_request_work_calendar))
                } else {
                    ivOpenDate.visibility = View.GONE
                    tvDateOpenRequestWork.setTextColor(itemView.resources.getColor(R.color.text_recycle_smile))
                }
                if (equalityDate(item.dateClose, date)) {
                    ivCloseDate.visibility = View.VISIBLE
                    tvDateCloseRequestWork.setTextColor(itemView.resources.getColor(R.color.red_schema))
                } else {
                    ivCloseDate.visibility = View.GONE
                    tvDateCloseRequestWork.setTextColor(itemView.resources.getColor(R.color.text_recycle_smile))
                }
                if (date > item.dateClose.timeInMillis) {
                    linearLayout.background =
                        itemView.resources.getDrawable(R.drawable.shift_schedule_note_close_background)
                } else linearLayout.background =
                    itemView.resources.getDrawable(R.drawable.shift_schedule_note_background)

                itemView.setOnClickListener {
                    val isVisible = layoutButton.visibility == View.VISIBLE
                    animationHiddenPanel(isVisible)
                }

                bDelete.setOnClickListener {
                    layoutButton.visibility = View.GONE
                    onClickDelete(item)
                }
                bEdit.setOnClickListener {
                    onClickEdit(item)
                }

                when (item.permission) {
                    PermissionRequestWork.OTHER -> tvPermission.isGone = true
                    PermissionRequestWork.DISPATCHER -> {
                        tvPermission.isGone = false
                        tvNumberRequest.setTextColor(itemView.resources.getColor(R.color.text_input_assembly))
                        tvPermission.background =
                            itemView.resources.getDrawable(R.drawable.background_request_work_text_permission_d)
                    }

                    PermissionRequestWork.CHIEF_ENGINEER -> {
                        tvPermission.isGone = false
                        tvNumberRequest.setTextColor(itemView.resources.getColor(R.color.color_request_work_calendar))
                        tvPermission.background =
                            itemView.resources.getDrawable(R.drawable.background_request_work_text_permission_c)
                    }
                }
            }
        }

        private fun animationHiddenPanel(isVisible: Boolean) {
            binding.apply {
                layoutButton.clearAnimation()
                if (isVisible) {
                    layoutButton.animate()
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction { layoutButton.visibility = View.GONE }
                        .start()
                } else {
                    layoutButton.visibility = View.VISIBLE
                    layoutButton.alpha = 0f
                    layoutButton.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .start()
                }
            }
        }

        private fun calendarToString(calendar: Calendar): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy  HH:mm")
            return sdf.format(calendar.time)
        }

        private fun equalityDate(dateTag: Calendar, date: Long): Boolean {
            val calendarString = SimpleDateFormat("dd.MM.yyyy").format(dateTag.time)
            val dateString = SimpleDateFormat("dd.MM.yyyy").format(date)
            return calendarString == dateString
        }

        companion object {
            fun inflateFrom(
                parentContext: ViewGroup,
            ): RecyclerView.ViewHolder {
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