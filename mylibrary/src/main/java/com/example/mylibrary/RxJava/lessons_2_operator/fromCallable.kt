package com.example.mylibrary.RxJava.lessons_2_operator

import android.util.Log
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action
import rx.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.math.log

class fromCallable {

    fun longAction(text: String): Int {
        try {
            TimeUnit.SECONDS.sleep(1)
        } catch (e:InterruptedException) {
            e.printStackTrace()
        }
        return text.toInt()
    }

    //Необходимо обернуть его в Callable
    class CallableLongAction(val data: String): Callable<Int> {
        override fun call(): Int {
            return fromCallable().longAction(data)
        }
    }

    //создавать Observable из Callable
init {
        Observable.fromCallable { CallableLongAction("5")}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{integer ->
                Log.d("k","onNext $integer")
            }

}




}