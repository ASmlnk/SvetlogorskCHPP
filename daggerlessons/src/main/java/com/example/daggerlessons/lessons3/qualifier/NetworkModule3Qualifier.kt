package com.example.daggerlessons.lessons3.qualifier

import com.example.daggerlessons.lessons3.named.ServerApi
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
class NetworkModule3Qualifier {

    //создание своих анотации, аналог Named
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Prod

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Dev

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ProdString(val value:String = "")


    @Prod
    @Provides
    fun provideServerApiProd(): ServerApi {
        return ServerApi("prod.server.com")
    }

    @Dev
    @Provides
    fun provideServerApiDev(): ServerApi {
        return ServerApi("dev.server.com")
    }

    @ProdString("1")
    @Provides
    fun provideServerApiProd1(): ServerApi {
        return ServerApi("prod1.server.com")
    }

    @ProdString("2")
    @Provides
    fun provideServerApiProd2(): ServerApi {
        return ServerApi("prod2.server.com")
    }
}