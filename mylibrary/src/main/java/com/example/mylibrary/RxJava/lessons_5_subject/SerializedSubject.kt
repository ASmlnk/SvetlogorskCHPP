package com.example.mylibrary.RxJava.lessons_5_subject

import android.os.Handler
import android.util.Log
import rx.functions.Action1
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject

private const val TAG = "TAG"
class SerializedSubject {

    val subject = PublishSubject.create<Long>()

    val serializedSubject = SerializedSubject(subject)
    /*добавили создание SerializedSubject из PublishSubject, и
    далее в потоках используем уже именно его. Т.е. SerializedSubject
    является оберткой для других Subject и позволяет сделать их потокобезопасными.*/

    val action = object : Action1<Long> {
        var sum: Long = 0
        override fun call(t: Long?) {
            sum += t!!
        }
        override fun toString(): String {
            return "sum = $sum"
        }
    }
    val handler = Handler()

    init {
        subject.subscribe(action)

        Thread {
            for (int in 0 until 10000) {
              //  subject.onNext(1L)
                serializedSubject.onNext(1L)
            }
            Log.d(TAG, "first thread done")
        }.start()

        Thread {
            for (int in 0 until 10000 ) {
                //  subject.onNext(1L)
                serializedSubject.onNext(1L)
            }
            Log.d(TAG, "second thread dane")
        }.start()

        handler.postDelayed(
            Runnable {
                Log.d(TAG, action.toString())
            }, 2000
        )
    }
}

/*
Результат
second thread done
first thread done
sum = 200000*/
