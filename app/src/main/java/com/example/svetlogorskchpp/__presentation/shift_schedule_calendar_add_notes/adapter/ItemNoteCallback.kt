package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.__domain.model.Note

class ItemNoteCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return when {
            oldItem is Note.NoteMy && newItem is Note.NoteMy -> oldItem.id == newItem.id
            oldItem is Note.NoteRequestWork && newItem is Note.NoteRequestWork -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}