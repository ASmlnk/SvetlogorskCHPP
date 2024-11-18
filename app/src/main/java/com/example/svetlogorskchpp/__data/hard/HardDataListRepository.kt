package com.example.svetlogorskchpp.__data.hard

interface  HardDataListRepository <out E> {
    fun data(): List<E>
}