package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable

class all {

    val observable = Observable.from(listOf(1,2,3,4,5,6,7,8))
        .all {it < 10}

    /*Результат:
    onNext: true
    onCompleted*/
}