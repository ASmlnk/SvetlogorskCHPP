package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable
import rx.functions.Action1

class Action {

    val observable = Observable.from(listOf("one", "two", "three"))

    val action = object : Action1<String> {
        override fun call(t: String?) {
            TODO("Not yet implemented")
        }
    }

    init {
        observable.subscribe(action)
    }
}