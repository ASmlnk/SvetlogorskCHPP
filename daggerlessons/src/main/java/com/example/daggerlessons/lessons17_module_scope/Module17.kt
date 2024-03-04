package com.example.daggerlessons.lessons17_module_scope

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModuleApp17 {
    @Provides
    fun provideNetworkUtils(app: Application): NetworkUtilsApp17 {
        return NetworkUtilsApp17(app)
    }
}

@Module
@InstallIn(ActivityComponent::class)
class NetworkModuleActivity17 {
    @Provides
    fun provideNetworkUtils(app: Application, activity: Activity): NetworkUtilsActivity17 {
        return NetworkUtilsActivity17(app, activity)
    }
}

@Module
@InstallIn(FragmentComponent::class)
class NetworkModuleFragment17 {
    @Provides
    fun provideNetworkUtils(app: Application, activity: Activity, fragment: Fragment): NetworkUtilsFragment17 {
        return NetworkUtilsFragment17(app, activity, fragment)
    }
}