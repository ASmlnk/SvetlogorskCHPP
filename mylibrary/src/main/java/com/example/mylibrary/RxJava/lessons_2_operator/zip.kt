package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable

class zip {

    val observable = Observable
        .from(listOf(1,2,3))
        .zipWith(Observable.from(listOf("One", "Two", "Three"))) { it, string ->
             "$string:$it"
        }

    /*Результат:
    onNext: One: 1
    onNext: Two: 2
    onNext: Three: 3
    onCompleted*/
}