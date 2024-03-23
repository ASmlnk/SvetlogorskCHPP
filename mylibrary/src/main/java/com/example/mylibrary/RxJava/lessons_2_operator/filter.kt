package com.example.mylibrary.RxJava.lessons_2_operator

import rx.Observable

class filter {

    val observable = Observable.from(listOf("15","27","34","46","52","63"))
        .filter { list ->
            list.contains("5")
        }

    /*Результат:
    onNext: 15
    onNext: 52
    onCompleted*/
}