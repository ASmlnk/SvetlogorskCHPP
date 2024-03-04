package com.example.daggerlessons.lessons6_builder_factory

import android.content.Context
import com.example.daggerlessons.lessons2.module.NetworkModule2
import com.example.daggerlessons.lessons3.named.module.NetworkModule3
import com.example.daggerlessons.lessons4_inject.MainActivity4
import com.example.daggerlessons.lessons4_inject.Module4
import com.example.daggerlessons.lessons4_inject.NetworkUtils4
import com.example.daggerlessons.lessons5_builder.AppModule5
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule5::class])
interface AppComponent6 {


    //кастомный билдер
    /*@Component.Builder
    interface AppCompBuilder6 {
        fun buildAppComp6(): AppComponent6
        //Имя метода значения не имеет (например, buildAppComp), но он должен быть без аргументов и возвращать AppComponent
    }*/

    @Component.Builder
    interface AppCompBuilderMod62 {
        fun buildAppComp6(): AppComponent6
        fun appModule(appModule5: AppModule5): AppCompBuilderMod62
    }

}