package com.example.svetlogorskchpp.model

data class ElectricalAssemblyFirebase(
    var id: String = "",
    var nameAssembly: String = "",
    var inputAssembly: String = "",
    var listItemAssembly: Map<String, Boolean> = emptyMap(),
    var nameDepartment: String = "",
    var category: String = "",
)  {
}