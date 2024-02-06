package com.example.svetlogorskchpp.model.inspectionSchedule

data class ChecklistInspection(
    var nameBlank: String = "",
    var numberChecklist: String = "",
    var nameCheck: Map<String, String> = emptyMap()
)
