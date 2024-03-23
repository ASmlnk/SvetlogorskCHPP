package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable
import rx.Observer


class from {

    val observable: Observable<String> = Observable.from(listOf("one", "two", "three"))

    val observer = object : Observer<String> {
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

    /*onNext: one
    onNext: two
    onNext: three
    onCompleted*/

}