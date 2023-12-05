package com.example.svetlogorskchpp.model.electricMotor

data class ElectricMotor(
    var id: String = "",
    var name: String = "",
    var category: String = "",
    var keyCheckBox: String = "",
    var schemaState: Boolean = true,
    var characteristics: String = "",
    var rep: Boolean = false,
    var responsibly: Boolean = false,
    var voltage: String = "",
    var generalCategory: String = "",
    var indexSection: String = "",
    var schema: String = "",
    var p: String = "",
    var i: String = "",
    var pump: String = ""
)
