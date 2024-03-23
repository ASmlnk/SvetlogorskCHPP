package com.example.mylibrary.RxJava.lessons_4.Hot_Observable

import android.util.Log
import com.example.mylibrary.RxJava.lessons_3_subscription.Subscription
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

class refCount {

    lateinit var subscription1: rx.Subscription
    lateinit var subscription2: rx.Subscription

    val observer1 = object : Observer<Long> {
        override fun onCompleted() {
            Log.d("TAG","observer1 onCompleted")
        }
        override fun onError(e: Throwable?) {
        }
        override fun onNext(t: Long?) {
            Log.d("TAG", "observer1 onNext value = $t")
        }
    }

    val observer2 = object : Observer<Long> {
        override fun onCompleted() {
            Log.d("TAG","observer2 onCompleted")
        }
        override fun onError(e: Throwable?) {
        }
        override fun onNext(t: Long?) {
            Log.d("TAG", "observer2 onNext value = $t")
        }
    }

    val observable = Observable
        .interval(1, TimeUnit.MILLISECONDS)
        .take(6)
        .publish()              // publish делаем из него Hot Observable
        .refCount()             //Он из ConnectableObservable сделает Observable, который будет генерировать элементы только пока есть подписчики


    private val handler = android.os.Handler()

    init {
        Log.d("TAG", "observable create")

      //  observable.connect()  //метод connect, тут не нужен

        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable1 subscride")
                subscription1 = observable.subscribe(observer1)
            }, 1500
        )

        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable2 subscride")
                subscription2 =observable.subscribe(observer2)
            }, 3000
        )
        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable1 unsubscribe")
                subscription1.unsubscribe()
            }, 5000
        )

        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable2 unsubscribe")
                subscription2.unsubscribe()
            }, 6000
        )
        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable1 subscride")
                observable.subscribe(observer1)
            }, 6500
        )
    }
}

/*
Результат:
1500 observer1 subscribe
2500 observer1 onNext value = 0
3000 observer2 subscribe
3500 observer1 onNext value = 1
3500 observer2 onNext value = 1
4500 observer1 onNext value = 2
4500 observer2 onNext value = 2
5000 observer1 unsubscribe
5500 observer2 onNext value = 3
6000 observer2 unsubscribe
6500 observer1 subscribe
7500 observer1 onNext value = 0
8500 observer1 onNext value = 1
9500 observer1 onNext value = 2
10500 observer1 onNext value = 3
11500 observer1 onNext value = 4
12500 observer1 onNext value = 5
12500 observer1 onCompleted*/
