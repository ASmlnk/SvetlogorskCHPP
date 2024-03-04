package com.example.daggerlessons.lessons13_Assisted_inject

import com.example.daggerlessons.lessons3.named.ServerApi
import javax.inject.Inject

/*
class MainActivityPresenter13 @Inject constructor(private val serverApi: ServerApi13) {
}*/

class MainActivityPresenter13 @Inject constructor(private val serverApiFactory: ServerApiFactory)

/*
Теперь в презентере мы используем эту фабрику, чтобы создать ServerApi:
var serverApi = serverApiFactory.create("dev1.server.com")
Мы передаем только host. А даггер сам раздобудет NetworkUtils и создаст нам объект ServerApi.
*/
