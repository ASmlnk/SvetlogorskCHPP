package com.example.mylibrary.RxJava.lessons_3_subscription

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import rx.Observable
import rx.functions.Action1
import java.util.concurrent.TimeUnit

class Subscription: AppCompatActivity() {

    val observable: Observable<Long> = Observable.interval(1, TimeUnit.SECONDS)

    private val action = Action1<Long> {
        Log.d("TAG", "onNext:" + 1)
    }

    private val subscription = observable.subscribe(action)

    private val run = Runnable { subscription.unsubscribe() }
    init {
        window.decorView.postDelayed(run, 4500)
    }

    /*Результат:
    onNext: 0
    onNext: 1
    onNext: 2
    onNext: 3
    unsubscribe*/


}