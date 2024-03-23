package com.example.mylibrary.RxJava.lessons_7_error

import android.util.Log
import rx.Observable
import rx.Observer

private const val TAG = "TAG"
class onErrorReturn {

    val stringData = Observable.just("1","2","a","4","5")

    val observable = stringData
        .map { it.toLong() }
        .onErrorReturn {
            Log.d(TAG, "onErrorReturn $it")
            0L
        }    //onErrorReturn позволит перехватить ошибку и вместо нее передать значение

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
Результат:
onNext 1
onNext 2
onErrorReturn java.lang.NumberFormatException: Invalid long: "a"
onNext 0
onCompleted

 onErrorReturn ловит ошибку и передает какое-то значение в onNext,
 в итоге это все равно приводит к тому, что поток данных завершается (onCompleted)
*/
