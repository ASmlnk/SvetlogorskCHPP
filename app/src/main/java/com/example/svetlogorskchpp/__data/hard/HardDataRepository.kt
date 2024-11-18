package com.example.svetlogorskchpp.__data.hard

interface HardDataRepository <out T> {
    fun data(): T
}