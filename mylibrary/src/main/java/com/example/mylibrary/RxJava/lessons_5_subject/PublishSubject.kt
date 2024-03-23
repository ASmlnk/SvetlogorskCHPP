package com.example.mylibrary.RxJava.lessons_5_subject

import android.util.Log
import rx.Observable
import rx.Observer
import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

private const val TAG = "TAG"

class PublishSubject {

    val observer1 = object : Observer<Long> {
        override fun onCompleted() {
            Log.d(TAG, "observer1 onCompleted")
        }

        override fun onError(e: Throwable?) {

        }

        override fun onNext(t: Long?) {
            Log.d(TAG, "observer1 onNext value $t")
        }
    }

    val observer2 = object : Observer<Long> {
        override fun onCompleted() {
            Log.d(TAG, "observer2 onCompleted")
        }

        override fun onError(e: Throwable?) {

        }

        override fun onNext(t: Long?) {
            Log.d(TAG, "observer2 onNext value $t")
        }
    }

    val observable = Observable
        .interval(1, TimeUnit.MILLISECONDS)
        .take(10)

    val subject = PublishSubject.create<Long>()
    val handler = android.os.Handler()

    init {
        Log.d(TAG, "subject subscribe")
        observable.subscribe(subject)

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer1 subject")
                subject.subscribe(observer1)
            }, 3500
        )

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer2 subject")
                subject.subscribe(observer2)
            }, 5500
        )

        handler.postDelayed(
            Runnable {
                subject.onNext(100L)  //отправляем элемент в Subject методом onNext
            }, 7500
        )
    }
}

/*
Результат
0 subject subscribe
3500 observer1 subscribe
4000 observer1 onNext value = 3
5000 observer1 onNext value = 4
5500 observer2 subscribe
6000 observer1 onNext value = 5
6000 observer2 onNext value = 5
7000 observer1 onNext value = 6
7000 observer2 onNext value = 6
7500 observer1 onNext value = 100
7500 observer2 onNext value = 100
8000 observer1 onNext value = 7
8000 observer2 onNext value = 7
9000 observer1 onNext value = 8
9000 observer2 onNext value = 8
10000 observer1 onNext value = 9
10000 observer2 onNext value = 9
10000 observer1 onCompleted
10000 observer2 onCompleted*/
