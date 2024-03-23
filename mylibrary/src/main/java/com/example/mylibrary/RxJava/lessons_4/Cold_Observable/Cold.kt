package com.example.mylibrary.RxJava.lessons_4.Cold_Observable

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit


class Cold: AppCompatActivity() {

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

    private val handler = android.os.Handler()

    init {
        Log.d("TAG", "observable create")

        val observable = Observable
            .interval(1, TimeUnit.MICROSECONDS)
            .take(5)

        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable1 subscride")
                observable.subscribe(observer1)
            }, 3000
        )

        handler.postDelayed(
            Runnable {
                Log.d("TAG", "observable2 subscride")
                observable.subscribe(observer2)
            }, 5500
        )
    }



}

/*
Результат:
0 observable create
3000 observer1 subscribe
4000 observer1 onNext value = 0
5000 observer1 onNext value = 1
5500 observer2 subscribe
6000 observer1 onNext value = 2
6500 observer2 onNext value = 0
7000 observer1 onNext value = 3
7500 observer2 onNext value = 1
8000 observer1 onNext value = 4
8000 observer1 onCompleted
8500 observer2 onNext value = 2
9500 observer2 onNext value = 3
10500 observer2 onNext value = 4
10500 observer2 onCompleted*/
