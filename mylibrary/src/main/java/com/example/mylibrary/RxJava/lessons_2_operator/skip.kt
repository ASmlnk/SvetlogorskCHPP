package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable

class skip {

    val observable = Observable.from(listOf(5,6,7,8,9))
        .skip(2)

    /*Результат:
    onNext: 7
    onNext: 8
    onNext: 9
    onCompleted*/

}