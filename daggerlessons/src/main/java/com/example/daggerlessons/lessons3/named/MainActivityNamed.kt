package com.example.daggerlessons.lessons3.named

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App
import javax.inject.Inject
import javax.inject.Named

class MainActivityNamed : AppCompatActivity() {

    //get
    lateinit var serverApi: ServerApi

    //inject
    @Inject
    @Named("prod")
    lateinit var serverApiInject: ServerApi

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //get
        serverApi = (application as App).appComponent3.getServerApiProd()

        //inject
        (application as App).appComponent3.injectMainActivity(this)

    }
}