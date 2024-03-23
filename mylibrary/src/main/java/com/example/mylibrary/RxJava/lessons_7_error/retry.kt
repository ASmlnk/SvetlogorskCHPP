package com.example.mylibrary.RxJava.lessons_7_error

import android.util.Log
import rx.Observable
import rx.Observer
import rx.functions.Func2

private const val TAG = "TAG"
class retry1 {

    val stringData = Observable.just("1","2","a","4","5")

    val observable = stringData
        .map { it.toLong() }
        .retry()    //retry Будет перезапускать подписку, пока она не закончится успешно

    init {

        observable.subscribe(object : Observer<Long> {
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
onNext 1
onNext 2
onNext 1
onNext 2
onNext 1
onNext 2
onNext 1
onNext 2
…

Т.к. в этом примере ошибка в Observable будет возникать всегда,
то использование retry приведет к бесконечному количеству
попыток снова получить данные
можно указать количество попыток .retry(2)
*/

class retry2 {

    val stringData = Observable.just("1","2","a","4","5")

    val observable = stringData
        .map { it.toLong() }
        .retry (Func2<Int, Throwable, Boolean> {int, er ->
            Log.d(TAG, "retry retryCount $int, throwable = $er")
            int < 3
        })         //retry Будет перезапускать подписку, пока условие true

    init {

        observable.subscribe(object : Observer<Long> {
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

/*Результат
onNext 1
onNext 2
retry retryCount 1, throwable = java.lang.NumberFormatException: Invalid long: "a"
onNext 1
onNext 2
retry retryCount 2, throwable = java.lang.NumberFormatException: Invalid long: "a"
onNext 1
onNext 2
retry retryCount 3, throwable = java.lang.NumberFormatException: Invalid long: "a"
onError java.lang.NumberFormatException: Invalid long: "a"

После каждой попытки получения данных, вызывается функция в retry, получает данные о
количестве попыток и об ошибке и решает, продолжать ли попытки. В итоге, после трех попыток,
отказываемся от дальнейшей борьбы и получаем ошибку в onError
*/
