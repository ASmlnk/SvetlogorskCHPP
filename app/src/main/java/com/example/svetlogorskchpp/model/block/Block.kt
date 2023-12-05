package com.example.svetlogorskchpp.model.block

data class Block(
    var nameBlock: String = "",
    var nameTransformer: String = "",
    var nameGenerator: String = "",
    var nameTurbin: String = "",
    var detailTransformer: String = "",
    var detailGenerator: String = "",
    var detailTurbin: String = "",
    var expanded: Boolean = false,
    var decodingTransformer: String = "",
    var decodingGenerator: String = "",
    var decodingTurbin: String = "",

)
