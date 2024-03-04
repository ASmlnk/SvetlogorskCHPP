package com.example.daggerlessons.lessons17_module_scope

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//Application
@HiltAndroidApp
class App17: Application() {

    @Inject
    lateinit var networkUtils: NetworkUtilsApp17
}


//Activity
@AndroidEntryPoint
class OrderActivity17: AppCompatActivity() {
}


//Service
@AndroidEntryPoint
class OrderService17: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}


//ViewModel
//Доступные объекты: Application, SavedStateHandle
@HiltViewModel
class OrderViewModel @Inject constructor(val network: NetworkUtilsViewModel17): ViewModel()