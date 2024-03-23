package com.example.mylibrary.RxJava.lessons_8_operator

import android.util.Log
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"


class zip1 {

    val observable1 = Observable
        .interval(300, TimeUnit.MILLISECONDS)
        .take(10)

    val observable2 = Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .take(10)
        .map { it + 100 }

    init {
        /*Оператор zip позволяет соединять друг с другом элементы из разных Observable*/
        observable1.zipWith(observable2) { aLong1, aLong2 ->
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
В результате работы оператора zip, мы из двух Observable<Long> получили один Observable<String>.

500 onNext 0 and 100
1000 onNext 1 and 101
1500 onNext 2 and 102
2000 onNext 3 and 103
2500 onNext 4 and 104
3000 onNext 5 and 105
3500 onNext 6 and 106
4000 onNext 7 and 107
4500 onNext 8 and 108
5000 onNext 9 and 109
5000 onCompleted
*/

class zip2 {

    val observable1 = Observable
        .interval(300, TimeUnit.MILLISECONDS)
        .take(10)

    val list = listOf("a", "b", "c", "d", "e", "f", "g")

    init {
        /*zipWith, который позволяет соединить ваш Observable с коллекцией Iterable*/
        observable1.zipWith(list) { aLong1, aLong2 ->
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
В результате работы оператора zip, мы из двух Observable<Long> получили один Observable<String>.

Результат:
300 onNext 0 and a
600 onNext 1 and b
900 onNext 2 and c
1200 onNext 3 and d
1500 onNext 4 and e
1800 onNext 5 and f
2100 onNext 6 and g
2100 onCompleted
*/

class zip3 {

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
        /*Статический метод Observable.zip дает возможность объединять более, чем два Observable*/
        Observable.zip(observable1, observable2, observable3) { aLong1, aLong2, aLong3 ->
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
Результат:
800 onNext 0 and 100 and 1000
1600 onNext 1 and 101 and 1001
2400 onNext 2 and 102 and 1002
3200 onNext 3 and 103 and 1003
4000 onNext 4 and 104 and 1004
4800 onNext 5 and 105 and 1005
5600 onNext 6 and 106 and 1006
6400 onNext 7 and 107 and 1007
7200 onNext 8 and 108 and 1008
8000 onNext 9 and 109 and 1009
8000 onCompleted
zip ждет самый медленный Observable, чтобы собрать элементы из трех Observable в один.

С помощью zip можно искусственно сделать паузу между элементами вашего основного Observable
Когда есть observer1 который выдает строки мгновено, то спомощью observer2 у которого есть интервал
можно замедлить получение данных
Т.к. zip работает со скоростью самого медленного из Observable, то мы получим паузу 100 мсек в работе observable1
*/
