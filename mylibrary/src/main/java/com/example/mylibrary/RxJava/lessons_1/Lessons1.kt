package com.example.mylibrary.RxJava.lessons_1

import rx.Observable
import rx.Observer

class Lessons1 {
    val observable: Observable<String> = Observable.from(arrayOf("one", "two", "three"))

    val observer: Observer<String> = object : Observer<String> {
        override fun onCompleted() {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onNext(t: String?) {
            TODO("Not yet implemented")
        }
    }

    init {
        observable.subscribe(observer)
    }
}