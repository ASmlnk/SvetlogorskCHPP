package com.example.daggerlessons

import android.app.Application
import com.example.daggerlessons.lessons1.component.AppComponent
import com.example.daggerlessons.lessons1.component.DaggerAppComponent
import com.example.daggerlessons.lessons11_scope.AppComponent11
import com.example.daggerlessons.lessons11_scope.DaggerAppComponent11
import com.example.daggerlessons.lessons2.component.AppComponent2
import com.example.daggerlessons.lessons2.component.DaggerAppComponent2
import com.example.daggerlessons.lessons3.AppComponent3
import com.example.daggerlessons.lessons3.DaggerAppComponent3
import com.example.daggerlessons.lessons4_inject.AppComponent4
import com.example.daggerlessons.lessons4_inject.DaggerAppComponent4
import com.example.daggerlessons.lessons4_inject.Module4
import com.example.daggerlessons.lessons5_builder.AppComponent5
import com.example.daggerlessons.lessons5_builder.AppModule5
import com.example.daggerlessons.lessons5_builder.DaggerAppComponent5
import com.example.daggerlessons.lessons6_builder_factory.AppComponent6
import com.example.daggerlessons.lessons6_builder_factory.AppComponentFactory6
import com.example.daggerlessons.lessons6_builder_factory.AppComponentNoModule6
import com.example.daggerlessons.lessons6_builder_factory.DaggerAppComponent6
import com.example.daggerlessons.lessons6_builder_factory.DaggerAppComponentFactory6

import com.example.daggerlessons.lessons6_builder_factory.DaggerAppComponentNoModule6
import com.example.daggerlessons.lessons7_subcomponent.AppComponent7
import com.example.daggerlessons.lessons7_subcomponent.DaggerAppComponent7
import com.example.daggerlessons.lessons8_subcomponent.AppComponent8
import com.example.daggerlessons.lessons8_subcomponent.DaggerAppComponent8
import com.example.daggerlessons.lessons9_Dependencies.AppComponent9
import com.example.daggerlessons.lessons9_Dependencies.DaggerAppComponent9

class App: Application() {

    lateinit var appComponent: AppComponent
    lateinit var appComponent2: AppComponent2
    lateinit var appComponent3: AppComponent3
    lateinit var appComponent4: AppComponent4
    lateinit var appComponent5: AppComponent5
    lateinit var appComponent6: AppComponent6
    lateinit var appComponentNoModule6: AppComponentNoModule6
    lateinit var appComponentFactory6: AppComponentFactory6
    lateinit var appComponent7: AppComponent7
    lateinit var appComponent8: AppComponent8
    lateinit var appComponent9: AppComponent9
    lateinit var appComponent11: AppComponent11

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        appComponent2 = DaggerAppComponent2.create()
        appComponent3 = DaggerAppComponent3.create()
        appComponent4 = DaggerAppComponent4.create()
        appComponent5 = DaggerAppComponent5
            .builder()
            .appModule5(AppModule5(this))
            .build()
        appComponent6 = DaggerAppComponent6.builder()
            .appModule(AppModule5(this))
            .buildAppComp6()
        appComponentNoModule6 = DaggerAppComponentNoModule6
            .builder()
            .context(this)
            .buildAppCompBindsInstance6()
        appComponentFactory6 = DaggerAppComponentFactory6
            .factory()
            .create(this, Module4())
        appComponent7 = DaggerAppComponent7.create()
        appComponent8 = DaggerAppComponent8.create()
        appComponent9 = DaggerAppComponent9.create()
        appComponent11 = DaggerAppComponent11.create()


    }
}