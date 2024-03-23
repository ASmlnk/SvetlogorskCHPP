package com.example.mylibrary.RxJava.lessons_7_error

import android.util.Log
import rx.Observable
import rx.Observer
import rx.functions.Action1
import rx.functions.Func1

private const val TAG = "TAG"
class onError {

    val stringData = Observable.just("1","2","a","4","5")

    val observable = stringData
        .map { it.toLong() }

    init {

        observable.subscribe(object :Observer<Long> {
            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }
            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError $e")
            }
            override fun onNext(t: Long?) {
                Log.d(TAG, "onNext $t")
            }
        })
    }
}

/*
onNext 1
onNext 2
onError java.lang.NumberFormatException: Invalid long: "a"
На этом работа завершилась*/
