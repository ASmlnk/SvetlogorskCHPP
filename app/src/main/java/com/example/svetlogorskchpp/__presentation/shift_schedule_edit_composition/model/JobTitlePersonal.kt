package com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model


import com.example.svetlogorskchpp.__domain.en.JobTitle

data class JobTitlePersonal(
    val jobTitle: JobTitle = JobTitle.TITLE_NO,
    val namePersonalShiftA: String = "",
    val namePersonalShiftB: String = "",
    val namePersonalShiftC: String = "",
    val namePersonalShiftD: String = "",
    val namePersonalShiftE: String = "",
) {
}