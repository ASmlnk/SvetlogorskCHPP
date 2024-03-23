package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable

class merge {

    val observable = Observable
        .from(listOf(1,2,3))
        .mergeWith(Observable.from(listOf(6,7,8,9)))

    /*Результат:
    onNext: 1
    onNext: 2
    onNext: 3
    onNext: 6
    onNext: 7
    onNext: 8
    onNext: 9
    onCompleted*/
}