package com.example.mylibrary.RxJava.lessons_5_subject

import android.util.Log
import rx.Observable
import rx.Observer
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

private const val TAG = "TAG"

class BehaviorSubject {

    val observer1 = object : Observer<Long> {
        override fun onCompleted() {
            Log.d(TAG, "observer1 onCompleted")
        }
        override fun onError(e: Throwable?) { }
        override fun onNext(t: Long?) {
            Log.d(TAG, "observer1 onNext value = $t")
        }
    }

    val observer2 = object : Observer<Long> {
        override fun onCompleted() {
            Log.d(TAG, "observer2 onCompleted")
        }
        override fun onError(e: Throwable?) { }
        override fun onNext(t: Long?) {
            Log.d(TAG, "observer2 onNext value = $t")
        }
    }

    val observable = Observable
        .interval(1, TimeUnit.SECONDS)
        .take(10)

    val subject = BehaviorSubject.create(-1L)
    val handler = android.os.Handler()

    init {
        Log.d(TAG, "observer1 subject")
        subject.subscribe(observer1)

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "subject subscribe")
                observable.subscribe(subject)
            }, 2000
        )

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer2 subject")
                subject.subscribe(observer2)
            }, 7500
        )
    }
}

/*
Результат
0 observer1 subscribe
0 observer1 onNext value = -1
2000 subject subscribe
3000 observer1 onNext value = 0
4000 observer1 onNext value = 1
5000 observer1 onNext value = 2
6000 observer1 onNext value = 3
7000 observer1 onNext value = 4
7500 observer2 subscribe
7500 observer2 onNext value = 4
8000 observer1 onNext value = 5
8000 observer2 onNext value = 5
9000 observer1 onNext value = 6
9000 observer2 onNext value = 6
10000 observer1 onNext value = 7
10000 observer2 onNext value = 7
11000 observer1 onNext value = 8
11000 observer2 onNext value = 8
12000 observer1 onNext value = 9
12000 observer2 onNext value = 9
12000 observer1 onCompleted
12000 observer2 onCompleted*/
