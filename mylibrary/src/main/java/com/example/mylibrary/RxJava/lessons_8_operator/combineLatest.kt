package com.example.mylibrary.RxJava.lessons_8_operator

import android.util.Log
import rx.Observable
import rx.Observer
import java.util.concurrent.TimeUnit

private const val TAG = "TAG"


class combineLatest {

    val observable1 = Observable
        .interval(300, TimeUnit.MILLISECONDS)
        .take(10)

    val observable2 = Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .take(10)
        .map { it + 100 }

    init {
        /*Похож на zip - также берет элементы из нескольких Observable и собирает из них один.
        Но отличается тем, что не ждет, когда придет самый медленный элемент пары, а просто берет
        последние полученные элементы с каждого Observable каждый раз когда придет новый элемент из любого Observable*/
        Observable.combineLatest(observable1,observable2) { aLong1, aLong2 ->
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
Результат:
500 onNext 0 and 100
600 onNext 1 and 100
900 onNext 2 and 100
1000 onNext 2 and 101
1200 onNext 3 and 101
1500 onNext 4 and 101
1500 onNext 4 and 102
1800 onNext 5 and 102
2000 onNext 5 and 103
2100 onNext 6 and 103
2400 onNext 7 and 103
2500 onNext 7 and 104
2700 onNext 8 and 104
3000 onNext 9 and 104
3000 onNext 9 and 105
3500 onNext 9 and 106
4000 onNext 9 and 107
4500 onNext 9 and 108
5000 onNext 9 and 109
5000 onCompleted
*/