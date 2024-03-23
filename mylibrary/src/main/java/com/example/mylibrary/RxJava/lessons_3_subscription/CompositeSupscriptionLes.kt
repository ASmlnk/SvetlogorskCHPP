package com.example.mylibrary.RxJava.lessons_3_subscription

import androidx.appcompat.app.AppCompatActivity
import rx.Observable
import rx.Observer
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class CompositeSubscriptionLes : AppCompatActivity() {

    val observable = Observable.interval(1, TimeUnit.SECONDS)

    val observer1 = object : Observer<Long> {
        override fun onCompleted() {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onNext(t: Long?) {
            TODO("Not yet implemented")
        }

    }

    val observer2 = object : Observer<Long> {
        override fun onCompleted() {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onNext(t: Long?) {
            TODO("Not yet implemented")
        }

    }

    val subscription1 = observable.subscribe(observer1)
    val subscription2 = observable.subscribe(observer2)

    val compositeSubscription = CompositeSubscription()

    init {
        compositeSubscription.apply {
            add(subscription1)
            add(subscription2)
        }

        compositeSubscription.unsubscribe()
    }
}