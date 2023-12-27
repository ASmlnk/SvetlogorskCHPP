package com.example.svetlogorskchpp.model

data class ElectricalAssembly(
    var id: String = "",
    var nameAssembly: String = "",
    var inputAssembly: String = "",
    var listItemAssembly: List<ItemElectricalAssembly> = emptyList(),
    var nameDepartment: String = "",
    var category: String = "",
) {
}