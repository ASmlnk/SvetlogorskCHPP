package com.example.svetlogorskchpp.model.transformerNeeds

data class Tsn(
    var nameTsn: String = "",
    var typeTsn: String = "",
    var powerTsn: String = "",
    var lowVoltage: String = "",
    var lowVoltageCell: String = "",
    var highVoltage: String = "",
    var highVoltageCell: String = "",
    var controlPanel: String = "",
    var controlPanelCell: String = "",
    var shutdownProtection: String = "",
    var signalProtection: String = ""
)
