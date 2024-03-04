package com.example.daggerlessons.lessons12_multiscope.provides

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class OrderModule12 {

    @OrderScope12
    @Provides
    fun provideUiHelper(activity: Activity): UiHelper12 {
        return UiHelper12(activity)
    }
}