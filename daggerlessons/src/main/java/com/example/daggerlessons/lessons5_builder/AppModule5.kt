package com.example.daggerlessons.lessons5_builder

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources

import dagger.Module
import dagger.Provides

@Module
class AppModule5(private val context: Context) {

    @Provides
    fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    fun getResources(): Resources {
        return context.resources
    }

    @Provides
    fun getAppContext(): Context {
        return context
    }
    //getAppContext() позволяет дагеру использовать контекст в других модулях без передачи его в конструктор
}