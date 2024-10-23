package com.example.svetlogorskchpp.__domain.en.shift_schedule

enum class RequestWorkSorted(val get: String) {
    DATE_OPEN("по дате открытия"),
    DATE_CLOSE("по дате закрытия"),
    NUMBER("по намеру заявки")
}