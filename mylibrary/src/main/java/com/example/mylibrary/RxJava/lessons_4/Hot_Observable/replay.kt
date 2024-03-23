package com.example.mylibrary.RxJava.lessons_4.Hot_Observable

import android.util.Log
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

class replay {

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
        .replay()              // replay делаем из него Hot Observable
    private val handler = android.os.Handler()

    init {
        Log.d("TAG", "observable create")

        observable.connect()  //метод connect, который начнет работу Observable, независимо от того, подписан кто-то или нет

        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable1 subscride")
                observable.subscribe(observer1)
            }, 2500
        )

        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable2 subscride")
                observable.subscribe(observer2)
            }, 4500
        )
    }
}

/*
Результат
0 observable connect
2500 observer1 subscribe
2500 observer1 onNext value = 0
2500 observer1 onNext value = 1
3000 observer1 onNext value = 2
4000 observer1 onNext value = 3
4500 observer2 subscribe
4500 observer2 onNext value = 0
4500 observer2 onNext value = 1
4500 observer2 onNext value = 2
4500 observer2 onNext value = 3
5000 observer2 onNext value = 4
5000 observer1 onNext value = 4
6000 observer2 onNext value = 5
6000 observer1 onNext value = 5
6000 observer2 onCompleted
6000 observer1 onCompleted*/
