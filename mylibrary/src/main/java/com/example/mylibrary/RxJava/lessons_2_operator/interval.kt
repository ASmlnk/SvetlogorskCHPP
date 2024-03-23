package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

class interval {
    val observable: Observable<Long> = Observable.interval(500, TimeUnit.MICROSECONDS)

    val observer = object : Observer<Long> {
        override fun onCompleted() {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onNext(t: Long?) {
            TODO("Not yet implemented")
        }
    }

    init {
        observable.subscribe(observer)
    }

/*    Теперь каждые 500 мсек в Observer будет приходить все увеличивающееся значение, начиная с 0.

    Результат:
    onNext: 0
    onNext: 1
    onNext: 2
    onNext: 3
    ...*/
}