package com.example.daggerlessons.lessons20_customComponent

import javax.inject.Inject
import javax.inject.Singleton

@MyScope
class MyRepository @Inject constructor() {
}

@Singleton
class MyComponentManager @Inject constructor(
    val myComponentBuilder: MyComponentBuilder20
) {
    private var myComponent: MyComponent20? = null

    fun create() {
        myComponent = myComponentBuilder.build()
    }

    fun get() = myComponent

    fun destroy() {
        myComponent = null
    }
}