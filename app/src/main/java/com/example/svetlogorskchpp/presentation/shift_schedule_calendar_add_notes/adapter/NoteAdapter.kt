package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.ItemSwipeCalendarNoteBinding
import com.example.svetlogorskchpp.domain.model.Note
import java.text.SimpleDateFormat
import javax.inject.Inject

class NoteAdapter @Inject constructor() : ListAdapter<Note, NoteAdapter.NoteHolder>(ItemNoteCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class NoteHolder(val binding: ItemSwipeCalendarNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(item: Note) {
            binding.apply {
                tvContet.text = item.content
                tvTime.text = SimpleDateFormat("HH:mm").format(item.dateNotes.time)
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