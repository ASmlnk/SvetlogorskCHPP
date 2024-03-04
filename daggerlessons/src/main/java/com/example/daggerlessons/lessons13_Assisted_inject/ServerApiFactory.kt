package com.example.daggerlessons.lessons13_Assisted_inject

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ServerApiFactory {
    fun create(host: String): ServerApi13
}

//два String параметра и даггер сам не разберется,
// какой String из фабрики в какой аргумент конструктора ServerApi передавать. Нам надо их явно отличать.
@AssistedFactory
interface ServerApiFactory2 {
    fun create(
        @Assisted("host") host: String,
        @Assisted("port")port: String): ServerApi13v2
}
//Теперь мы в презентере используем фабрику, которое передаем два String значения:
//var serverApi = serverApiFactory.create("dev1.server.com", "80")

//Если нам в фабрике нужны значения по умолчанию, мы можем их использовать.
// Например, для порта можно поставить значение по умолчанию 80:
 @AssistedFactory
interface ServerApiFactory3 {
    fun create(
        @Assisted("host") host: String,
        @Assisted("port") port: String = "80"
    ): ServerApi13v2
}