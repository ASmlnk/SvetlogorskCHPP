package com.example.svetlogorskchpp.__domain

sealed class OperationResult<out T> {
    data class Success <out T> (val data: T): OperationResult<T>()
    data class Error(val massage: String): OperationResult<Nothing>()
}