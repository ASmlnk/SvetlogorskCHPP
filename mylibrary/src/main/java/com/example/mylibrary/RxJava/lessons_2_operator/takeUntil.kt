package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable

class takeUntil {

    val observable = Observable.from(listOf(1,2,3,4,5,6,7,8))
        .takeUntil{ it == 5 }

    /*Результат:
    onNext: 1
    onNext: 2
    onNext: 3
    onNext: 4
    onNext: 5
    onCompleted*/
}