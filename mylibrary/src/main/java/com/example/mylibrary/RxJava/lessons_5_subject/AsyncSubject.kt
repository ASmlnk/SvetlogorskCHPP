package com.example.mylibrary.RxJava.lessons_5_subject

import android.util.Log
import rx.Observable
import rx.Observer
import rx.subjects.AsyncSubject
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

private const val TAG = "TAG"

class AsyncSubject {

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
        .take(4)

    val subject = AsyncSubject.create<Long>()
    val handler = android.os.Handler()

    init {
        Log.d(TAG, "subject subscribe")
        observable.subscribe(subject)

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer1 subject")
                subject.subscribe(observer1)
            }, 1500
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
0 subject subscribe
1500 observer1 subscribe
4000 observer1 onNext value = 3
4000 observer1 onCompleted
7500 observer2 subscribe
7500 observer2 onNext value = 3
7500 observer2 onCompleted

Observer1 подписался во время работы Subject, но получил только последнее значение в
момент когда последовательность завершилась (onCompleted). Observer2, который подписался
уже после завершения, также получил последнее значение.
*/
