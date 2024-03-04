package com.example.daggerlessons.lessons1.module

import com.example.daggerlessons.lessons1.DatabaseHelper
import dagger.Module
import dagger.Provides

//Модуль для DatabaseHelper:
@Module
class StorageModule {

    @Provides
    fun provideDatabaseHelper(): com.example.daggerlessons.lessons1.DatabaseHelper {
        return com.example.daggerlessons.lessons1.DatabaseHelper()
    }
}