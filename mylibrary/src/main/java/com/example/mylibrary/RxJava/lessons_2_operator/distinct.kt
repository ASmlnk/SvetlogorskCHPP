package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable

class distinct {

    val observable = Observable.from(listOf(5,9,7,5,9,6,7,8,9))
        .distinct()

    /*Результат:
    onNext: 5
    onNext: 9
    onNext: 7
    onNext: 8
    onNext: 6
    onCompleted*/
}