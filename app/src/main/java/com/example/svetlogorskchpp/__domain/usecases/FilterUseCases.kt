package com.example.svetlogorskchpp.__domain.usecases

import com.example.svetlogorskchpp.__domain.en.JobTitle
import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model.ShiftPersonal
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