package com.example.mylibrary.RxJava.lessons_6_subscribeOn_observeOn

import android.util.Log
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"

class lessons6 {

    val observer = object : Observer<Int> {
        override fun onCompleted() {
            Log.d(TAG, "Observer onCompleted")
        }
        override fun onError(e: Throwable?) {}
        override fun onNext(t: Int?) {
            Log.d(TAG, "observer onNext value = $t")
        }
    }

    //своя реализацию Observable
    val onSubscribe = Observable.OnSubscribe<Int> { subscribe ->
        Log.d(TAG, "call")
        for (i in 0 until 3) {
            try {
                TimeUnit.MILLISECONDS.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            subscribe.onNext(i)
        }
        subscribe.onCompleted()
    }

    val func = Func1<Int, Int> {
        Log.d(TAG, "func $it")
        it * 10
    }

    val observable = Observable
        .create(onSubscribe)
        .subscribeOn(Schedulers.io())
        .map(func)
        .observeOn(AndroidSchedulers.mainThread())
}

/*
Результат:
100 subscribe [main]
200 done [main]
200 call [RxIoScheduler-2]
300 func 0 [RxIoScheduler-2]
300 onNext: 0 [main]
400 func 1 [RxIoScheduler-2]
400 onNext: 10 [main]
500 func 2 [RxIoScheduler-2]
500 onNext: 20 [main]
500 onCompleted [main]*/
