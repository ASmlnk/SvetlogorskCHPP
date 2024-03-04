package com.example.daggerlessons.lessons6_builder_factory

import android.content.Context
import com.example.daggerlessons.lessons4_inject.Module4
import com.example.daggerlessons.lessons4_inject.NetworkUtils4
import com.example.daggerlessons.lessons5_builder.AppModule5
import dagger.BindsInstance
import dagger.Component

@Component(modules = [Module4::class])
interface AppComponentFactory6 {
    //фабрика
    @Component.Factory
    interface AppCompFactory6 {
        fun create(@BindsInstance context: Context, networkModule4: Module4): AppComponentFactory6
    }
    //если не через фабрику то можно так
 /*   @Component.Builder
    interface AppCompBuilderF6 {
        @BindsInstance fun context(context: Context): AppCompBuilderF6
        fun networkModule(networkModule: Module4): AppCompBuilderF6
        fun buildAppComp(): AppComponentFactory6
    }*/
}