package com.example.svetlogorskchpp.model.powerLines

data class PowerLines(
    var name: String = "",
    var nameORY: String = "",
    var numberCells: Int = 0,
    var numberSH: Int = 0,
    var numberGHY: String = "",
    var apv: String = "",
    var key1SR: Int = 0,
    var key2SR: Int = 0,
    var keyLR: Int = 0,
    var keyOR: Int = 0,
    var madeApv: String = "",
    var earthProtection: String = "",
    var phaseProtection: String = ""
)
