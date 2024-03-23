package com.example.mylibrary.RxJava.lessons_8_operator

import android.util.Log
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"
class withLatestFrom1 {

    val observable1 = Observable
        .interval(300, TimeUnit.MILLISECONDS)
        .take(10)

    val observable2 = Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .take(10)
        .map { it + 100 }

    init {
        /*Также аналог zip, но ориентируется не на самый медленный Observable,
        а на основной Observable, к которому этот оператор и был применен*/
        observable1.withLatestFrom(observable2) { aLong1, aLong2 ->
            "$aLong1 and $aLong2"
        }
            .subscribe(object : Observer<String> {
                override fun onCompleted() {
                    Log.d(TAG, "onCompleted")
                }
                override fun onError(e: Throwable?) {}
                override fun onNext(t: String?) {
                    Log.d(TAG, "onNext $t")
                }
            })
    }
}

/*
Мы используем оператор withLatestFrom для observable1. В оператор передаем observable2 и функцию.
Функция будет срабатывать каждый раз, когда придет новый элемент из observable1.
 А из observable2 просто будет забираться последний полученный от него элемент.

Результат:
600 onNext 1 and 100
900 onNext 2 and 100
1200 onNext 3 and 101
1500 onNext 4 and 102
1800 onNext 5 and 102
2100 onNext 6 and 103
2400 onNext 7 and 103
2700 onNext 8 and 104
3000 onNext 9 and 105
3000 onCompleted
*/

class withLatestFrom2 {

    val observable1 = Observable
        .interval(300, TimeUnit.MILLISECONDS)
        .take(10)

    val observable2 = Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .take(10)
        .map { it + 100 }

    val observable3 = Observable
        .interval(800, TimeUnit.MILLISECONDS)
        .take(10)
        .map { it + 1000 }

    init {
        /*Оператор withLatestFrom можно использовать и более, чем для двух Observable*/
        observable1.withLatestFrom(observable2, observable3) { aLong1, aLong2, aLong3 ->
            "$aLong1 and $aLong2 and $aLong3"
        }
            .subscribe(object : Observer<String> {
                override fun onCompleted() {
                    Log.d(TAG, "onCompleted")
                }
                override fun onError(e: Throwable?) {}
                override fun onNext(t: String?) {
                    Log.d(TAG, "onNext $t")
                }
            })
    }
}

/*
Используем withLatestFrom для observable1 и передаем туда observable2, observable3 и функцию.
Функция будет срабатывать каждый раз, когда придет новый элемент из observable1.
А из observable2 и observable3 просто будут забираться последние полученные от них элементы.

Результат
900 onNext 2 and 100 and 1000
1200 onNext 3 and 101 and 1000
1500 onNext 4 and 102 and 1000
1800 onNext 5 and 102 and 1001
2100 onNext 6 and 103 and 1001
2400 onNext 7 and 103 and 1002
2700 onNext 8 and 104 and 1002
3000 onNext 9 and 105 and 1002
3000 onCompleted

Функция срабатывает каждые 300 мсек, т.е. при каждом новом элементе из observable1.
А из observable2 и observable3 берутся их последние на тот момент полученные элементы.

В первые 300 и 600 мсек функция не сработала, т.к. не было последнего значения observable2 или observable3,
они появились только после 800 мсек
*/