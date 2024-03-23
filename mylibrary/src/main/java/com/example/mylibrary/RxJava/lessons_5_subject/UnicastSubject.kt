package com.example.mylibrary.RxJava.lessons_5_subject

import android.util.Log
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject
import rx.subjects.UnicastSubject
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

private const val TAG = "TAG"

class UnicastSubject {

    private lateinit var subscription1: Subscription
    private lateinit var subscription2: Subscription

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
        .take(20)

    val subject = UnicastSubject.create<Long>()
    val handler = android.os.Handler()

    init {
        Log.d(TAG, "subject subscribe")
        observable.subscribe(subject)

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer1 subscribe")
                subscription1 = subject.subscribe(observer1)
            }, 2500
        )

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer2 subject")
                subject.subscribe(observer2)
            }, 4500
        )

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer1 unsubscribe")
                subscription1.unsubscribe()
            }, 6500
        )

        handler.postDelayed(
            Runnable {
                Log.d(TAG, "observer2 subject")
                subject.subscribe(observer2)
            }, 8500
        )
    }
}

/*
Результат:
0 subject subscribe
2500 observer1 subscribe
2500 observer1 onNext value = 0
2500 observer1 onNext value = 1
3000 observer1 onNext value = 2
4000 observer1 onNext value = 3
4500 observer2 subscribe
4500 observer2 onError Only a single subscriber is allowed
5000 observer1 onNext value = 4
6000 observer1 onNext value = 5
6500 observer1 unsubscribe
8500 observer2 subscribe
8500 observer2 onError Only a single subscriber is allowed

Observer2 пытается подписаться и пока Observer1 подписан, и после того, как Observer1 отписался, но получает ошибку.*/
