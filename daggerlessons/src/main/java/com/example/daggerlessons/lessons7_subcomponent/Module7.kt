package com.example.daggerlessons.lessons7_subcomponent

import com.example.daggerlessons.lessons2.DatabaseHelper2
import com.example.daggerlessons.lessons2.MainActivityPresenter2
import com.example.daggerlessons.lessons2.NetworkUtils2
import dagger.Module
import dagger.Provides


@Module
class MainModule7 {
    @Provides
    fun provideMainActivityPresenter(databaseHelper: DatabaseHelper2, networkUtils: NetworkUtils2):MainActivityPresenter2 {
        return MainActivityPresenter2(databaseHelper, networkUtils)
    }
}