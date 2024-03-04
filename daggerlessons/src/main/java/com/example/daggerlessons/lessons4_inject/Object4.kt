package com.example.daggerlessons.lessons4_inject

import com.example.daggerlessons.lessons3.named.ServerApi
import com.example.daggerlessons.lessons3.qualifier.NetworkModule3Qualifier
import com.example.daggerlessons.lessons3.qualifier.NetworkModule3Qualifier.Dev
import javax.inject.Inject

class MainActivityPresenter4 @Inject constructor() {}

//или
class MainActivityPresenterArg4 @Inject constructor(
    private val databaseHelper4: DatabaseHelper4,
    private val networkUtils4: NetworkUtils4
) {
     /*Когда даггер будет создавать этот объект для нас, то сразу после создания он вызовет метод postInit.
    Причем он может передать в этот метод объект, который ему доступен для создания:*/
    @Inject
    fun postInit() { }
    @Inject
    fun postInit(networkUtils4: NetworkUtils4) {
    }
    /*Если даггер создает объект с помощью Provides метода из модуля, а не создает
    сам Inject конструктором, то Inject методы вызваны не будут.*/
}


class MainActivityPresenterQualifier4 @Inject constructor(
    private val databaseHelper4: DatabaseHelper4,
    private val networkUtils4: NetworkUtils4,
    @Dev private val serverApi: ServerApi
)


class NetworkUtils4


class DatabaseHelper4 @Inject constructor()