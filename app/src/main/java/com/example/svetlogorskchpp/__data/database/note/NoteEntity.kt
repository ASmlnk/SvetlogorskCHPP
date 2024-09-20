package com.example.svetlogorskchpp.__data.database.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.__domain.model.Note
import java.util.Calendar
import java.util.Date

@Entity(
    tableName = "notes",
   /* foreignKeys = [ForeignKey(
        entity = CalendarNoteTagEntity::class,
        parentColumns = ["date"],
        childColumns = ["tagDate"],
        onDelete = ForeignKey.CASCADE
    )]*/
)

data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tagDate: Date,        //авязь с CalendarTag
    val dateNotes: Date,        //"YYYY-MM-DD HH:mm"
    val isTimeNotes: Boolean = false,
    val content: String,
    @ColumnInfo(name = "is_request_work") val isRequestsWork: Boolean = false
) {
    fun toNote(): Note {
        return Note(
            id = this.id,
            tagDate = this.tagDate,
            dateNotes = dateToCalendar(this.dateNotes),
            isTimeNotes = this.isTimeNotes,
            content = this.content,
            isRequestsWork = this.isRequestsWork
        )
    }

    private fun dateToCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time =date
        return calendar
    }
}
