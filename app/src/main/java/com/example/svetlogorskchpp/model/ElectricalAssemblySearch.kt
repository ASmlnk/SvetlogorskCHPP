package com.example.svetlogorskchpp.model

data class ElectricalAssemblySearch(
    var name: String = "",
    var index: Int = 0,
    var electricalAssembly: ElectricalAssemblyFirebase = ElectricalAssemblyFirebase()
) {
}