package com.example.mylibrary.RxJava.lessons_7_error

import android.util.Log
import rx.Observable
import rx.Observer

private const val TAG = "TAG"
class onErrorResumeNext {

    val stringData = Observable.just("1","2","a","4","5")

    val observable = stringData
        .map { it.toLong() }
        .onErrorResumeNext(
            Observable.just(8L, 9L, 10L)
        )    //onErrorReturn позволяет вместо ошибки отправить в Observer не одно значение, а несколько - в виде Observable

    init {

        observable.subscribe(object :Observer<Long> {
            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }
            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError $e")
            }
            override fun onNext(t: Long?) {
                Log.d(TAG, "onNext $t")
            }
        })
    }
}

/*
Результат:
onNext 1
onNext 2
onNext 8
onNext 9
onNext 10
onCompleted

Получаем первые два значения, а после ошибки - значения из последовательности, указанной в onErrorResumeNext
*/

class onErrorResumeNext2 {

    val stringData = Observable.just("1","2","a","4","5")

    val observable = stringData
        .map { it.toLong() }
        .onErrorResumeNext {
            Log.d(TAG, "onErrorResumeNext $it")
            Observable.just(8L, 9L, 10L)
        }   //В onErrorResumeNext используем функцию, на вход которой получаем Throwable.
             // В зависимости от типа Throwable можем создать необходимую последовательность значений.
             // В данном примере я в любом случае возвращаю последовательность Observable.just(8L, 9L, 10L).

    init {

        observable.subscribe(object :Observer<Long> {
            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }
            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError $e")
            }
            override fun onNext(t: Long?) {
                Log.d(TAG, "onNext $t")
            }
        })
    }
}

/*
Результат
onNext 1
onNext 2
onErrorResumeNext java.lang.NumberFormatException: Invalid long: "a"
onNext 8
onNext 9
onNext 10
onCompleted

Получаем первые два значения, а после ошибки - значения из последовательности, указанной в onErrorResumeNext
*/
