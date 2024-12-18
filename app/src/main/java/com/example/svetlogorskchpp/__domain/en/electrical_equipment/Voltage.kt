package com.example.svetlogorskchpp.__domain.en.electrical_equipment

enum class Voltage(val str: String, val int: Int) {
    KV_220("220", 220),
    KV_110("110", 110),
    KV_35("35", 35),
    KV_3("3,15 кВ", 3),
    KV_6("6,3 кВ", 6),
    V_660("660 В", 1),
    KV_380("380 В", 1),
    V_220("220 В", 1),
    KV("U, кВ", 0),
    KV10_KV6("10,5кВ/6,3кВ", 0),
    KV10_KV3("10,5кВ/3,15кВ", 0),
    KV6_KV04("6,3кВ/0,4кВ", 0),
    KV3_KV04("3,15кВ/0,4кВ", 0),
}