package com.example.mylibrary.RxJava.lessons_9_retrofit.no_rx

import com.example.mylibrary.RxJava.lessons_9_retrofit.Message
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WebApi {
    @GET("message{page}.json")
    fun message(@Path("page") page: Int): Call<List<Message>>
}