package com.example.daggerlessons.lessons20_customComponent

import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope

@MyScope
@DefineComponent(parent = SingletonComponent::class)
interface MyComponent20 {

}

@InstallIn(MyComponent20::class)
@EntryPoint
interface MyEntryRepository20 {
    fun getMyRepository(): MyEntryRepository20
}


@DefineComponent.Builder
interface MyComponentBuilder20 {
    fun build(): MyComponent20
}

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
public annotation class MyScope()