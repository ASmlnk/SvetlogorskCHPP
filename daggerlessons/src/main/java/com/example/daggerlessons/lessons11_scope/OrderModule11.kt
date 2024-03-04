package com.example.daggerlessons.lessons11_scope

import dagger.Module
import dagger.Provides

@Module
class OrderModule11 {
//Этот модуль теперь может быть использован только в компоненте (или сабкомпоненте) с такой же scope аннотацией
    @OrderScope11
    @Provides
    fun provideOrderRepository(): OrderRepositoryProv11 {
        return OrderRepositoryProv11()
    }
}