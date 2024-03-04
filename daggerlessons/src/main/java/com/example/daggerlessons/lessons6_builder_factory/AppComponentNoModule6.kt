package com.example.daggerlessons.lessons6_builder_factory

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponentNoModule6 {

    @Component.Builder
    interface AppCompBuilderBindsInstance6 {
        fun buildAppCompBindsInstance6(): AppComponentNoModule6
        @BindsInstance
        fun context(context: Context): AppCompBuilderBindsInstance6
        //На вход он должен принимать объект, который мы хотим передать компоненту. А на выходе должен возвращать билдер.
        /*Теперь Context можно убрать из конструктора AppModule. У компонента будет доступ к этому объекту напрямую,
        и он сможет передать его в Provides методы для получения SharedPreferences и Resources:*/
    }
}