package com.example.svetlogorskchpp.__domain.en.electrical_equipment

enum class Voltage(val str: String, val int: Int) {
    KV_220("220", 220),
    KV_110("110", 110),
    KV_35("35", 35),
    KV("U, кВ", 0)
}