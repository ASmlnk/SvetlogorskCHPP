package com.example.daggerlessons.lessons11_scope

import dagger.Component
import dagger.Subcomponent
import javax.inject.Scope

@Component
interface AppComponent11 {
    fun getOrderComponent11(): OrderComponent11
    fun getOrderComponentProv(): OrderComponentProv11

}

@OrderScope11
@Subcomponent
interface OrderComponent11 {
    fun getOrderRepository(): OrderRepository11
}

@OrderScope11
@Subcomponent(modules = [OrderModule11::class])
interface OrderComponentProv11 {
    fun getOrderComponentProv(): OrderRepositoryProv11
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class OrderScope11
