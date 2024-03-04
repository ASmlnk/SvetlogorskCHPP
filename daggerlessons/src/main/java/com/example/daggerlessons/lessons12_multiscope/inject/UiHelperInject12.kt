package com.example.daggerlessons.lessons12_multiscope.inject

import android.app.Activity
import javax.inject.Inject

@ActivityScope12
class UiHelperInject12 @Inject constructor(activity: Activity) {
}