package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable
import rx.Observer

class buffer {

    val observable = Observable
        .from(listOf(1,2,3,4,5,6,7,8,))
        .buffer(3)

    val observer = object : Observer<List<Int>> {
        override fun onCompleted() {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onNext(t: List<Int>?) {
            TODO("Not yet implemented")
        }
    }

    init {
        observable.subscribe(observer)
    }

    /*Результат:
    onNext: [1, 2, 3]
    onNext: [4, 5, 6]
    onNext: [7, 8]
    onCompleted*/
}