package com.example.mylibrary.RxJava.lessons_9_retrofit.rx

import com.example.mylibrary.RxJava.lessons_9_retrofit.Message
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface WebApiR {

    @GET("message{page}.json")
    fun message(@Path("page") page: Int): Observable<List<Message>>
}