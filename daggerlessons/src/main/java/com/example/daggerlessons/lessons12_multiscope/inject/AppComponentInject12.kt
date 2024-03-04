package com.example.daggerlessons.lessons12_multiscope.inject

import com.example.daggerlessons.lessons12_multiscope.provides.OrderScope12
import com.example.daggerlessons.lessons12_multiscope.provides.UserScope12
import dagger.Component
import dagger.Subcomponent
import javax.inject.Scope

@Component
interface AppComponentInject12 {
}

@ActivityScope12
@OrderScope12
@Subcomponent
interface OrderComponent12

@ActivityScope12
@UserScope12
@Subcomponent
interface UserComponent12


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope12