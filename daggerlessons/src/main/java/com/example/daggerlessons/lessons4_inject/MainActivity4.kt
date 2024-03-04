package com.example.daggerlessons.lessons4_inject

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App
import javax.inject.Inject

class MainActivity4: AppCompatActivity() {

    @Inject
    lateinit var mainActivityPresenter4: MainActivityPresenter4

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        (application as App).appComponent4.injectMainActivity(this)
    }

    @Inject
    fun postInit(networkUtils4: NetworkUtils4) {
        //Когда компонент заинджектит объекты (MainActivityPresenter) в Activity, он вызовет метод postInit.
    }
}