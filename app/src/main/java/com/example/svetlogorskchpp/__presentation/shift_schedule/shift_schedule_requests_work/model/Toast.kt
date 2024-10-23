package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.model

enum class Toast (val textToast: String) {
    NUMBER("Введите номер заявки!"),
    DATE("Введите дату заявки!"),
    NUMBER_EXTEND("Введите номер продленной заявки!"),
    DATE_EXTEND("Введите дату продленной заявки!"),
    ACCESSION("Введите присоединение!"),
    REASON("Введите причину заявки!"),
    ERROR_REQUEST_WORK("Ошибка! Заявка не добавлена!"),
    INSERT_REQUEST_WORK("Заяка добавлена!"),
    DELETE_REQUEST_WORK("Заяка удалена!"),
    INSERT_NOTE("Заметка добавлена!"),
    DELETE_NOTE("Заметка удалена!"),
}