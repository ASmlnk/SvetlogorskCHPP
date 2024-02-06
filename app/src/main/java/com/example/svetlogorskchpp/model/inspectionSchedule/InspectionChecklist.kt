package com.example.svetlogorskchpp.model.inspectionSchedule

data class InspectionChecklist(
    var workingShift: String = "",
    var executor: String = "",
    var content: String = "",
    var withoutNumber: Boolean = false,
    var timeSpending: String = ""
) {
}
