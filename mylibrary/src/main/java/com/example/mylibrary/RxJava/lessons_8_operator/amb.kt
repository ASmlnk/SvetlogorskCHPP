package com.example.mylibrary.RxJava.lessons_8_operator

import android.util.Log
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"


class amb {

    val observable1 = Observable
        .interval(1000, 300, TimeUnit.MILLISECONDS)
        .take(10)

    val observable2 = Observable
        .interval(700,500, TimeUnit.MILLISECONDS)
        .take(10)
        .map { it + 100 }

    init {
        /*amb берет несколько Observable и ждет, кто из них первый пришлет данные.
        Далее, оператор будет возвращать элементы только из этого первого Observable*/
        Observable.amb(observable1,observable2)
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
700 onNext 100
1200 onNext 101
1700 onNext 102
2200 onNext 103
2700 onNext 104
3200 onNext 105
3700 onNext 106
4200 onNext 107
4700 onNext 108
5200 onNext 109
5200 onCompleted
*/