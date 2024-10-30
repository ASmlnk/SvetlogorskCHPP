package com.example.svetlogorskchpp.__data.model

enum class SuccessResultFirebase(val result: String) {
    UPDATE_OK("Изменения добавлены!"),
    UPDATE_ERROR("Ошибка! Данные не изменены!")
}