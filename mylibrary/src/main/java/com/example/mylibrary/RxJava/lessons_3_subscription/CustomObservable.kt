package com.example.mylibrary.RxJava.lessons_3_subscription


import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CustomObservable:AppCompatActivity() {

    val onSubscribe = Observable.OnSubscribe { subscrider->
        for (i in 0 until 10) {
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            subscrider.onNext(i)
        }

        //проверка статуса подписки
        if (subscrider.isUnsubscribed) return@OnSubscribe else subscrider.onCompleted()
    }

    val observable = Observable.create(onSubscribe)
        .subscribeOn(Schedulers.io())

    val observer = object : Observer<Int> {
        override fun onCompleted() {
            Log.d("TAG", "onCompleted")
        }

        override fun onError(e: Throwable?) {
            Log.d("TAG", "onError: $e")
        }

        override fun onNext(t: Int?) {
            Log.d("TAG", "onNext: $t")
        }
    }

    val subscription = observable.subscribe(observer)

    init {
        window.decorView.postDelayed(
            Runnable {
                Log.d("TAG", "unsubscride")
                subscription.unsubscribe()
            }, 4500)
    }
}