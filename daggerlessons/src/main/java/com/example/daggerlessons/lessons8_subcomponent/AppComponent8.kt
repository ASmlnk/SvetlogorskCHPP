package com.example.daggerlessons.lessons8_subcomponent

import android.app.Activity
import com.example.daggerlessons.lessons2.MainActivityPresenter2
import com.example.daggerlessons.lessons2.module.MainModule2
import com.example.daggerlessons.lessons2.module.NetworkModule2
import com.example.daggerlessons.lessons2.module.StorageModule2
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

//AppModule должен быть в списке модулей компонента, который инджектит билдер сабкомпонента MainComponent
@Component(modules = [StorageModule2::class, NetworkModule2::class, AppModule8::class])
interface AppComponent8 {

    //В MainModule мы можем передать те объекты, которые мы хотели бы использовать при создании презентера.
    // Все аналогично передаче объектов в обычный компонент
    fun getMainComponent8(mainModule: MainModule2): MainComponent8

    //Создаем интерфейс с аннотацией @Subcomponent.Builder, и в нем описываем,
    // какие объекты хотим передать в сабкомпонент.
    fun getMainComponent8Builder(): MainComponent8Builder.Builder8

    //В родительском компоненте описываем метод, который возвращает эту фабрику:
    fun getMainComponent8Factory(): MainComponent8Factory.Factory

    //инжект нужен AppModule8
    fun injectMainActivity(activity: Activity)

}

@Subcomponent(modules = [MainModule2::class])
interface MainComponent8 {
    fun getMainActivityPresenter8(): MainActivityPresenter2
}

@Subcomponent(modules = [MainModule2::class])
interface MainComponent8Builder {
    @Subcomponent.Builder
    interface Builder8 {
        @BindsInstance fun activity(activity: Activity): Builder8
        fun build(): MainComponent8Builder
    }
    fun getMainActivityPresenter(): MainActivityPresenter2
}

@Subcomponent(modules = [MainModule2::class])
interface MainComponent8Factory {

    @Subcomponent.Factory
    interface Factory {
        //В аргументах метода описываем объекты, которые хотим передавать в сабкомпонент
        fun create(@BindsInstance activity: Activity): MainComponent8Factory
    }
    fun getMainActivityPresenter(): MainActivityPresenter2
}