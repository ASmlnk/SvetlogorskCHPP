package com.example.daggerlessons.lessons15_Hilt

import com.example.daggerlessons.App
import dagger.Component
import dagger.Subcomponent

@Component
interface HiltAppCompat15 {

    fun injectApp (app: App)
    fun getActivityComponent(): HiltActivityComponent15
}

@Subcomponent
interface HiltActivityComponent15 {
    fun injectOrderActivity(activity: OrderActivity15)
    fun injectUserActivity(activity: UserActivity15)

     fun getFragmentComponent(): HiltFragmentComponent15
}

@Subcomponent
interface HiltFragmentComponent15 {
    fun injectOrderFragment(fragment: OrderFragment15)
}

