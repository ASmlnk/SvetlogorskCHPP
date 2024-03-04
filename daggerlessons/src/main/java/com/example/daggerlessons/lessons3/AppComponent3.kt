package com.example.daggerlessons.lessons3

import com.example.daggerlessons.lessons3.intoMap.EventHandlerType
import com.example.daggerlessons.lessons3.intoSet.EventHandler
import com.example.daggerlessons.lessons3.intoSet.NetworkModuleIntoSet
import com.example.daggerlessons.lessons3.named.MainActivityNamed
import com.example.daggerlessons.lessons3.named.ServerApi
import com.example.daggerlessons.lessons3.named.module.NetworkModule3
import com.example.daggerlessons.lessons3.qualifier.NetworkModule3Qualifier
import com.example.daggerlessons.lessons3.qualifier.NetworkModule3Qualifier.Prod
import com.example.daggerlessons.lessons3.qualifier.NetworkModule3Qualifier.ProdString
import dagger.Component
import javax.inject.Named

@Component(modules = [NetworkModule3::class, NetworkModule3Qualifier::class, NetworkModuleIntoSet::class])
interface AppComponent3 {

    //get
    @Named("prod")
    fun getServerApiProd(): ServerApi

    //inject
    fun injectMainActivity(mainActivity: MainActivityNamed)

    //Qualifier
    @Prod
    fun getServerApiProdQualifier(): ServerApi

    @ProdString("2")
    fun getServerApiProdQualifierString(): ServerApi

    //IntoSet
    fun getEventHandlers(): Set<EventHandler>

  //  fun getEventHandlersIntoMap(): Map<String, EventHandler>

   // fun getEventHandlersIntoMap2(): Map<EventHandlerType, EventHandler>

}