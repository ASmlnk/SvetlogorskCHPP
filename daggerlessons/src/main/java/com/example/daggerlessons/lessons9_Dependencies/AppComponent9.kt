package com.example.daggerlessons.lessons9_Dependencies

import com.example.daggerlessons.lessons2.DatabaseHelper2
import com.example.daggerlessons.lessons2.MainActivityPresenter2
import com.example.daggerlessons.lessons2.NetworkUtils2
import com.example.daggerlessons.lessons2.module.MainModule2
import com.example.daggerlessons.lessons2.module.NetworkModule2
import com.example.daggerlessons.lessons2.module.StorageModule2
import dagger.Component

@Component(modules = [StorageModule2::class, NetworkModule2::class])
interface AppComponent9 {
    fun getDatabaseHelper9(): DatabaseHelper2
    fun getNetworkUtils9(): NetworkUtils2

}

//параметр dependencies, в котором указываем класс AppComponent.
// Этим мы сообщаем даггеру, что MainComponent должен иметь доступ к объектам компонента AppComponent.
//Даггер учитывает это, когда создает класс компонента - DaggerMainComponent.
// В билдер этого компонента он зашивает необходимость передачи объекта AppComponent.
@Component(modules = [MainModule2::class], dependencies = [AppComponent9::class])
interface MainComponent9 {
    fun getMainActivityPresenter(): MainActivityPresenter2
}