package com.example.svetlogorskchpp.model.inspectionSchedule

data class Inspection(
    var id: String = "",
    var workingShift: String = "",
    var executor: String = "",
    var month: String = "all",
    var dayMonth: String = "all",
    var dayOfTheWeek: String = "all",
    var week: String = "all",
    var interval: Int = 0,
    var content: String = "",
    var withoutNumber: Boolean = false,
    var timeSpending: String = "") {
}
