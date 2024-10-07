package com.example.svetlogorskchpp.__domain.usecases.hardData

import com.example.svetlogorskchpp.__domain.en.HardData

interface HardDataUseCases <out T> {
    fun data(hardData: HardData):List<T>
}