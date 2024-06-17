package com.example.mylibrary.RxJava.lessons_9_retrofit

import com.google.gson.annotations.SerializedName

class Message {
    @SerializedName("id")
    private val id: Int = 0
    @SerializedName("time")
    private val time: Long = 0
    @SerializedName("text")
    private val text:String? = null
    @SerializedName("image")
    private val image: String? = null

    fun getId(): Int {
        return id
    }
    fun getTime(): Long {
        return time
    }
    fun getText(): String? {
        return text
    }
    fun getImage(): String? {
        return image
    }
}