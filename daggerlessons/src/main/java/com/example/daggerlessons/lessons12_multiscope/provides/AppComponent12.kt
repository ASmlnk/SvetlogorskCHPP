package com.example.daggerlessons.lessons12_multiscope.provides

import dagger.Component
import dagger.Subcomponent
import javax.inject.Scope

@Component
interface AppComponent12 {

}
@OrderScope12
@Subcomponent(modules = [OrderModule12::class])
interface OrderComponent12 {

}

@UserScope12
@Subcomponent(modules = [UserModule12::class])
interface UserComponent12 {

}



@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class OrderScope12

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class UserScope12
