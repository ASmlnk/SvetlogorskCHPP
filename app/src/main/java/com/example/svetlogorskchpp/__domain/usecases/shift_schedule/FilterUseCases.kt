package com.example.svetlogorskchpp.__domain.usecases.shift_schedule

import com.example.svetlogorskchpp.__domain.en.shift_schedule.JobTitle
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_edit_composition.model.ShiftPersonal
import javax.inject.Inject

class FilterUseCases @Inject constructor() {

    fun filterPersonalShiftForJobTitlePersonal(
        listPersonal: List<ShiftPersonal>,
        shift: Shift,
        jobTitle: JobTitle,
    ): String =
        listPersonal.filter { it.jobTitle == jobTitle }
            .firstOrNull { it.shift == shift }?.namePersonal ?: ""
}