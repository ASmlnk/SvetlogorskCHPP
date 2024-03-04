package com.example.daggerlessons.lessons17_module_scope

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

//Если нужен синглтон то вешаем анатацию, которая будет соответствовать времени жизни компонента

@Singleton
class NetworkUtilsApp17(val app: Application) {
}

@ActivityScoped
class NetworkUtilsActivity17 (val app: Application, val activity: Activity)

@FragmentScoped
class NetworkUtilsFragment17 (val app: Application, val activity: Activity, val fragment: Fragment)


@ServiceScoped
class NetworkUtilsService17 (val app: Application, val service: Service)

@ViewModelScoped
class NetworkUtilsViewModel17
