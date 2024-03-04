package com.example.daggerlessons.lessons13_Assisted_inject

import com.example.daggerlessons.lessons2.NetworkUtils2
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ServerApi13 @AssistedInject constructor(
    val networkUtils2: NetworkUtils2,
    @Assisted val host: String) {
}

//два String параметра и даггер сам не разберется,
// какой String из фабрики в какой аргумент конструктора ServerApi передавать. Нам надо их явно отличать.
class ServerApi13v2 @AssistedInject constructor(
    val networkUtils2: NetworkUtils2,
    @Assisted("host") val host: String,
    @Assisted("port") val port: String
)
//Теперь мы в презентере используем фабрику, которое передаем два String значения:
//var serverApi = serverApiFactory.create("dev1.server.com", "80")

