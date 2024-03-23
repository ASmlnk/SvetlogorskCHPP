package com.example.mylibrary.RxJava.lessons_8_operator

import android.util.Log
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"


class concat {

    val observable1 = Observable
        .interval(300, TimeUnit.MILLISECONDS)
        .take(10)

    val observable2 = Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .take(10)
        .map { it + 100 }

    init {
        /*Оператор concat - это merge c maxConcurrent = 1.
        Он всегда будет последовательно запускать переданные ему Observable*/
        observable1.concatWith(observable2)
            .subscribe(object : Observer<Long> {
                override fun onCompleted() {
                    Log.d(TAG, "onCompleted")
                }
                override fun onError(e: Throwable?) {}
                override fun onNext(t: Long?) {
                    Log.d(TAG, "onNext $t")
                }
            })
    }
}

/*
Результат
300 onNext 0
600 onNext 1
900 onNext 2
1200 onNext 3
1500 onNext 4
1800 onNext 5
2100 onNext 6
2400 onNext 7
2700 onNext 8
3000 onNext 9
3500 onNext 100
4000 onNext 101
4500 onNext 102
5000 onNext 103
5500 onNext 104
6000 onNext 105
6500 onNext 106
7000 onNext 107
7500 onNext 108
8000 onNext 109
8000 onCompleted
*/