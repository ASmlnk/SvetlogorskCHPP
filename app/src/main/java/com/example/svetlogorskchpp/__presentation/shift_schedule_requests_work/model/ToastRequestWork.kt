package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model

enum class ToastRequestWork(val textToast: String) {
    NUMBER("Введите номер заявки!"),
    DATE("Введите дату заявки!"),
    NUMBER_EXTEND("Введите номер продленной заявки!"),
    DATE_EXTEND("Введите дату продленной заявки!"),
    ACCESSION("Введите присоединение!"),
    REASON("Введите причину заявки!"),
    SAVE("Заяка добавлена!")
}