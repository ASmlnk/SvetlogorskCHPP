package com.example.daggerlessons.lessons2.module

import com.example.daggerlessons.lessons2.DatabaseHelper2
import com.example.daggerlessons.lessons2.Repository2
import dagger.Module
import dagger.Provides

@Module
class StorageModule2 {
    @Provides
    fun provideDatabaseHelper(repository: Repository2): DatabaseHelper2 {
        return DatabaseHelper2(repository)
    }

    @Provides
    fun provideRepository(): Repository2 {
        return Repository2()
    }

}