package com.example.svetlogorskchpp.__domain.en

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Shift(val nameBD: String, val nameApp: String) : Parcelable{
    A_SHIFT("A", "А"),
    B_SHIFT("B", "Б"),
    C_SHIFT("C", "В"),
    D_SHIFT("D", "Г"),
    E_SHIFT("E", "Д"),
    NO_SHIFT("", "")
}