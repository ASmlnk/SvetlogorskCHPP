package com.example.mylibrary.RxJava.lessons_10_backpressure

import android.util.Log
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"

class OnBackpressureBuffer {

    val onSubscribe = Observable.OnSubscribe<Int> {
        var i = 1
        while (i<=20) {
            if (it.isUnsubscribed) {
                return@OnSubscribe
            }
            it.onNext(i++)
        }
        it.onCompleted()
    }

    init {
        Observable.create(onSubscribe)
            .subscribeOn(Schedulers.computation())
            .doOnNext {
                //это элемент был отправлен из Observable в onBackpressureBuffer
                Log.d(TAG, "post to buffer $it")
            }
            .onBackpressureBuffer()  //это буфер-посредник между Observable и observeOn
            .doOnNext{
                //элемент был отправлен из onBackpressureBuffe в observeOn
                Log.d(TAG, "post from buffer $it")
            }
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<Int>{
                override fun onCompleted() {
                    Log.d(TAG, "onCompleted")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError $e")
                }

                override fun onNext(t: Int?) {
                    Log.d(TAG, "onNext $t")

                    try {
                        TimeUnit.MILLISECONDS.sleep(100)
                    } catch (e:InterruptedException) {
                        e.printStackTrace()
                    }
                }

            })
        /*
        Результат:

        0 post to buffer 1
        0 post from buffer 1
        0 onNext 1
        0 post to buffer 2
        0 post from buffer 2
        0 post to buffer 3
        0 post from buffer 3
        0 post to buffer 4
        0 post from buffer 4
        0 post to buffer 5
        0 post from buffer 5
        0 post to buffer 6
        0 post from buffer 6
        0 post to buffer 7
        0 post from buffer 7
        0 post to buffer 8
        0 post from buffer 8
        0 post to buffer 9
        0 post from buffer 9
        0 post to buffer 10
        0 post from buffer 10
        0 post to buffer 11
        0 post from buffer 11
        0 post to buffer 12
        0 post from buffer 12
        0 post to buffer 13
        0 post from buffer 13
        0 post to buffer 14
        0 post from buffer 14
        0 post to buffer 15
        0 post from buffer 15
        0 post to buffer 16
        0 post from buffer 16
        0 post to buffer 17
        0 post to buffer 18
        0 post to buffer 19
        0 post to buffer 20
        100 onNext 2
        200 onNext 3
        300 onNext 4
        400 onNext 5
        500 onNext 6
        600 onNext 7
        700 onNext 8
        800 onNext 9
        900 onNext 10
        1000 onNext 11
        1100 onNext 12
        1200 post from buffer 17
        1200 post from buffer 18
        1200 post from buffer 19
        1200 post from buffer 20
        1200 onNext 13
        1300 onNext 14
        1400 onNext 15
        1500 onNext 16
        1600 onNext 17
        1700 onNext 18
        1800 onNext 19
        1900 onNext 20
        2000 onCompleted
        */

        //Заменим в коде предыдущего примера onBackpressureBuffer на onBackpressureDrop
        //
        /*
        Результат

        0 post to drop 1
        0 post from drop 1
        0 onNext 1
        0 post to drop 2
        0 post from drop 2
        0 post to drop 3
        0 post from drop 3
        0 post to drop 4
        0 post from drop 4
        0 post to drop 5
        0 post from drop 5
        0 post to drop 6
        0 post from drop 6
        0 post to drop 7
        0 post from drop 7
        0 post to drop 8
        0 post from drop 8
        0 post to drop 9
        0 post from drop 9
        0 post to drop 10
        0 post from drop 10
        0 post to drop 11
        0 post from drop 11
        0 post to drop 12
        0 post from drop 12
        0 post to drop 13
        0 post from drop 13
        0 post to drop 14
        0 post from drop 14
        0 post to drop 15
        0 post from drop 15
        0 post to drop 16
        0 post from drop 16
        0 post to drop 17
        0 post to drop 18
        0 post to drop 19
        0 post to drop 20
        100 onNext 2
        200 onNext 3
        300 onNext 4
        400 onNext 5
        500 onNext 6
        600 onNext 7
        700 onNext 8
        800 onNext 9
        900 onNext 10
        1000 onNext 11
        1100 onNext 12
        1200 onNext 13
        1300 onNext 14
        1400 onNext 15
        1500 onNext 16
        1600 onCompleted
        первые 16 элементов приходят в onBackpressureDrop и
        идут дальше, а оставшиеся 4 дальше не идут,
        т.к. буфер observeOn уже заполнен
        теряете элементы, которые пришли в тот момент,
        когда они пока не могли быть обработаны
        */

        //onBackpressureLatest также будет игнорировать новые элементы, если их
        // пока нельзя отправить дальше.
        // Но при этом он всегда будет хранить последний полученный им элемент
        /*
        Результат:

        0 post to latest 1
        0 post from latest 1
        0 onNext 1
        0 post to latest 2
        0 post from latest 2
        0 post to latest 3
        0 post from latest 3
        0 post to latest 4
        0 post from latest 4
        0 post to latest 5
        0 post from latest 5
        0 post to latest 6
        0 post from latest 6
        0 post to latest 7
        0 post from latest 7
        0 post to latest 8
        0 post from latest 8
        0 post to latest 9
        0 post from latest 9
        0 post to latest 10
        0 post from latest 10
        0 post to latest 11
        0 post from latest 11
        0 post to latest 12
        0 post from latest 12
        0 post to latest 13
        0 post from latest 13
        0 post to latest 14
        0 post from latest 14
        0 post to latest 15
        0 post from latest 15
        0 post to latest 16
        0 post from latest 16
        0 post to latest 17
        0 post to latest 18
        0 post to latest 19
        0 post to latest 20
        100 onNext 2
        200 onNext 3
        300 onNext 4
        400 onNext 5
        500 onNext 6
        600 onNext 7
        700 onNext 8
        800 onNext 9
        900 onNext 10
        1000 onNext 11
        1100 onNext 12
        1200 post from latest 20
        1200 onNext 13
        1300 onNext 14
        1400 onNext 15
        1500 onNext 16
        1600 onNext 20
        1700 onCompleted
        */
    }
}