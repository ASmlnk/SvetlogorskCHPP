package com.example.svetlogorskchpp.__domain.en.electrical_equipment

enum class EditAccessResult(val str: String) {
    OK("Верный пароль!"),
    ERROR_NETWORK("Ошибка сети!"),
    ERROR_KD("Неверный пароль!")
}