package com.example.mylibrary.RxJava.lessons_10_backpressure

import android.util.Log
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"

class lessons10 {
    init {
        Observable.range(1, 10)
            .subscribeOn(Schedulers.computation())
            .doOnNext { t -> Log.d(TAG, "post $t") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onCompleted() {
                    Log.d(TAG, "onCompleted")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError $e")
                }

                override fun onNext(t: Int?) {
                    Log.d(TAG, "onNext $t")
                    try {
                        TimeUnit.MILLISECONDS.sleep(300)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            })
        /*
        Результат:
        0 post 1
        0 onNext 1
        0 post 2
        0 post 3
        0 post 4
        0 post 5
        0 post 6
        0 post 7
        0 post 8
        0 post 9
        0 post 10
        300 onNext 2
        600 onNext 3
        900 onNext 4
        1200 onNext 5
        1500 onNext 6
        1800 onNext 7
        2100 onNext 8
        2400 onNext 9
        2700 onNext 10
        3000 onCompleted
        */

        //если именим на range с 10 до 20, чтобы буфер заполнился
        //Observable.range(1, 20)

        /*
        0 post 1
        0 onNext 1
        0 post 2
        0 post 3
        0 post 4
        0 post 5
        0 post 6
        0 post 7
        0 post 8
        0 post 9
        0 post 10
        0 post 11
        0 post 12
        0 post 13
        0 post 14
        0 post 15
        0 post 16
        300 onNext 2
        600 onNext 3
        900 onNext 4
        1200 onNext 5
        1500 onNext 6
        1800 onNext 7
        2100 onNext 8
        2400 onNext 9
        2700 onNext 10
        3000 onNext 11
        3300 onNext 12
        3600 onNext 13
        3600 post 17
        3600 post 18
        3600 post 19
        3600 post 20
        3900 onNext 14
        4200 onNext 15
        4500 onNext 16
        4800 onNext 17
        5100 onNext 18
        5400 onNext 19
        5700 onNext 20
        6000 onCompleted
        */

    }

}