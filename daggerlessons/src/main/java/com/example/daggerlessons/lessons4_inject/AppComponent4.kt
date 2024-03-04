package com.example.daggerlessons.lessons4_inject

import dagger.Component

@Component(modules = [Module4::class])
interface AppComponent4 {
    fun getMainPresenter():MainActivityPresenter4
    fun getMainPresenterArg4():MainActivityPresenterArg4
    fun injectMainActivity(mainActivity4: MainActivity4)

}