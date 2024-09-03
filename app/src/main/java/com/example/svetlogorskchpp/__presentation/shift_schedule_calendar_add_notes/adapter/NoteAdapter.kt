package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.databinding.ItemSwipeCalendarNoteBinding
import com.example.svetlogorskchpp.__domain.model.Note
import java.text.SimpleDateFormat

class NoteAdapter(private val onClickDelete: (note: Note) -> Unit) : ListAdapter<Note, NoteAdapter.NoteHolder>(ItemNoteCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickDelete)
    }

    class NoteHolder(val binding: ItemSwipeCalendarNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(item: Note, onClickDelete: (note: Note) -> Unit) {
            binding.apply {
                swipeItemNote.apply {
                    showMode = SwipeLayout.ShowMode.LayDown
                    addDrag(SwipeLayout.DragEdge.Right, binding.swipeRightNote)
                    addSwipeListener(object : SwipeLayout.SwipeListener {
                        override fun onStartOpen(layout: SwipeLayout?) {
                        }
                        override fun onOpen(layout: SwipeLayout?) {
                        }
                        override fun onStartClose(layout: SwipeLayout?) {
                        }
                        override fun onClose(layout: SwipeLayout?) {
                        }
                        override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
                        }
                        override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
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
            fun inflateFrom(parentContext: ViewGroup): NoteHolder {
                val layoutInflate = LayoutInflater.from(parentContext.context)
                val binding =
                    ItemSwipeCalendarNoteBinding.inflate(layoutInflate, parentContext, false)
                return NoteHolder(binding)
            }
        }
    }
}