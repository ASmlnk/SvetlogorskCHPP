package com.example.svetlogorskchpp.domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class CalendarNoteTagUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
) : CalendarNoteTagUseCases {

    override fun calendarNoteTagStream(month: Calendar) =
        calendarNoteTagRepository.getTagsByMonth(
            calendarDateUseCases.calendarToDateYM(month)
        )
            .map { calendarNoteTagEntitys ->
                calendarNoteTagEntitys.map {
                    CalendarNoteTag(
                        id = it.id,
                        date = it.date,
                        month = it.month,
                        isTechnical = it.isTechnical,
                        isNotes = it.isNotes
                    )
                }
            }


}