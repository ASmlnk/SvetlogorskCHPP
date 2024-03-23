package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable
import rx.Observer

class take {

    val observable = Observable.from(listOf(5,6,7,8,9))
        .take(3)

    val observer = object : Observer<Int> {
        override fun onCompleted() {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onNext(t: Int?) {
            TODO("Not yet implemented")
        }

    }

    init {
        observable.subscribe(observer)
    }

    /*Результат:
    onNext: 5
    onNext: 6
    onNext: 7
    onCompleted*/

}