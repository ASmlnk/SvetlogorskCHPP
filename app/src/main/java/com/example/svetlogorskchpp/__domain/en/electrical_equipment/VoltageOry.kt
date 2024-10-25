package com.example.svetlogorskchpp.__domain.en.electrical_equipment

enum class VoltageOry(val str: String, val int: Int) {
    KV_220("220", 220),
    KV_110("110", 110),
    KV("U, кВ", 0)
}