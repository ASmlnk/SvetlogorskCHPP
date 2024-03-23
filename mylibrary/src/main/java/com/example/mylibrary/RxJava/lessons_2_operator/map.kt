package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable
import rx.Observer

class map {

    val observable: Observable<Int> =
        Observable.from(listOf("1", "2", "3", "4", "5", "6"))
            .map { it.toInt() }

    val observer = object :Observer<Int> {
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

    /*Результат
    onNext: 1
    onNext: 2
    onNext: 3
    onNext: 4
    onNext: 5
    onNext: 6
    onCompleted*/

    //если
    val observableEr: Observable<Int> =
        Observable.from(listOf("1", "2", "3", "4", "5", "6"))
            .map { it.toInt() }

/*    Результат:
    onNext: 1
    onNext: 2
    onNext: 3
    onError: java.lang.NumberFormatException: Invalid int: "a"*/

}