package com.example.daggerlessons.lessons9_Dependencies

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App


class MainActivity9: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val appComponent = (application as App).appComponent9

        //Мы должны передать ему экземпляр компонента AppComponent.
        // Только так MainComponent сможет получить доступ к объектам AppComponent. Он просто будет просить их у этого компонента.
        val mainComponent9 = DaggerMainComponent9.builder()
            .appComponent9(appComponent)
            .build()
    }
}