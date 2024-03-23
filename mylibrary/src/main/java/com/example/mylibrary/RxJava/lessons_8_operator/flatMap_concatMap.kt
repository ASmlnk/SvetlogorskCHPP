package com.example.mylibrary.RxJava.lessons_8_operator

import rx.Observable

class flatMap {

    val userGroupObservable: Observable<UserGroup> = Observable.just(UserGroup(listOf("dd", "dkjf")))

    val observable1: Observable<List<String>> = userGroupObservable
        .map { it.getUsers() }
    /*
    Результат:
    onNext [User 1, User 2, User 3, User 4, User 5]
    onNext [User 6, User 7, User 8, User 9, User 10]
    onCompleted
    */

    val observable2 = userGroupObservable
        .map { Observable.from(it.getUsers()) }
    /*
    Результат:
    onNext rx.Observable@4f18411
    onNext rx.Observable@2e44368
    onCompleted
    т.е. Observable<Observable<String>>
    * */

    val observable3 = userGroupObservable
        .flatMap { Observable.from(it.getUsers())}

    /*
    * Результат
    onNext User 1
    onNext User 2
    onNext User 3
    onNext User 4
    onNext User 5
    onNext User 6
    onNext User 7
    onNext User 8
    onNext User 9
    onNext User 10
    onCompleted
    * */

    /*Если групп несколько то flatMap запустит их паралельно
    Т.е. flatMap получил первую группу и запустил в работу ее Observable,
    полученный из функции. Затем, получил вторую группу и запустил ее Observable.
    И оба Observable работали параллельно
    у flatMap есть параметр maxConcurrent, который позволяет указать, сколько Observable могут работать одновременно*/

    val observable4 = userGroupObservable
        .flatMap ({ Observable.from(it.getUsers())}, 1)
    /*еперь одновременно может работать только один Observable. И сначала работает первая группа, затем вторая*/

    /*concatMap - это flatMap c maxConcurrent = 1*/


}

class UserGroup(private val users: List<String>) {
    fun getUsers(): List<String> {
        return users
    }
}