package com.example.svetlogorskchpp.__data.database.requestWorkTag

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarRequestWorkTag
import java.util.Date

@Entity(tableName = "request_work_tag")
data class RequestWorkTagEntity(
    @PrimaryKey
    val date: Date,    //"YYYY-MM-DD"
    val month: Date,    //"YYYY-MM"
) {

    fun toCalendarRequestWorkTag(): CalendarRequestWorkTag {
        return CalendarRequestWorkTag(
            date = this.date,
            month = this.month
        )
    }
}
