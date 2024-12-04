package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__data.hard.HardDataListRepository
import com.example.svetlogorskchpp.__data.hard.HardDataRepository
import com.example.svetlogorskchpp.__data.hard.HomePageInfoHardDataImpl
import com.example.svetlogorskchpp.__data.hard.RequestWorkHardDataListImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class HardDataModule {

    @Provides
    @RequestWorkAccession
    fun provideRequestWorkAccessionHardData (): HardDataListRepository<String> {
        return RequestWorkHardDataListImpl.Accession
    }

    @Provides
    @RequestWorkReason
    fun provideRequestWorkReasonHardData (): HardDataListRepository<String> {
        return RequestWorkHardDataListImpl.Reason
    }

    @Provides
    @InfoORY
    fun provideInfoOryHardData (): HardDataListRepository<String> {
        return HomePageInfoHardDataImpl.ORY
    }

    @Provides
    @InfoTSN
    fun provideInfoTSNHardData (): HardDataListRepository<String> {
        return HomePageInfoHardDataImpl.TSN
    }

    @Provides
    @InfoSwitchgear
    fun provideInfoSwitchgearNHardData (): HardDataListRepository<String> {
        return HomePageInfoHardDataImpl.Switchgear
    }

    @Provides
    @InfoElMotor
    fun provideInfoElMotorHardData (): HardDataListRepository<String> {
        return HomePageInfoHardDataImpl.ElMotor
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RequestWorkReason

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RequestWorkAccession

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InfoORY

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InfoTSN

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InfoSwitchgear

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InfoElMotor