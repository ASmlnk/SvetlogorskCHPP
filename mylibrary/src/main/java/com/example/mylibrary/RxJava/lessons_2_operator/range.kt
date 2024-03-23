package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable
import rx.Observer

class range {
    val observable: Observable<Int> = Observable.range(40, 4)

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

/*    Результат:
    onNext: 10
    onNext: 11
    onNext: 12
    onNext: 13
    onCompleted*/
}