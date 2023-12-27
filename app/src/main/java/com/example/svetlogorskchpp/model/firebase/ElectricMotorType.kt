package com.example.svetlogorskchpp.model.firebase

enum class ElectricMotorType(val nameCategoryFirebase: String) {
    BOILESRS("Котлоагрегаты"),
    TURBOGENERATORS("Турбогенераторы"),
    ANOTHER("Остальное"),
    UPDATE_BD("ОбновлениеБД"),
    ASSEMBLY("Сборки")
}