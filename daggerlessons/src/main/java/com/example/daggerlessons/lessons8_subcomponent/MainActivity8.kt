package com.example.daggerlessons.lessons8_subcomponent

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerlessons.App
import com.example.daggerlessons.lessons2.module.MainModule2
import javax.inject.Inject

class MainActivity8: AppCompatActivity() {

    @Inject
    lateinit var mainComponent8BuilderIn: MainComponent8Builder.Builder8

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val appComponent =(application as App).appComponent8

        //Стандартный способ передачи обьекта в сабкомпонент в MainModule теперь можно передаь наши объекты
        //В MainModule мы можем передать те объекты, которые мы хотели бы использовать при создании презентера.
        // Все аналогично передаче объектов в обычный компонент
        val mainComponent8st = appComponent.getMainComponent8(MainModule2())

        //Мы получаем от компонента билдер и сами создаем сабкомпонент MainComponent.
        // При этом мы передаем туда все, что нужно сабкомпоненту для создания объектов
        val mainComponent8Builder = appComponent.getMainComponent8Builder()
            .activity(this)
            .build()

        //Получаем от компонента фабрику и создаем сабкомпонент:
        val mainComponent8Factory = appComponent.getMainComponent8Factory()
            .create(this)

        //Вызываем injectMainActivity метод у компонента, он поместит билдер в переменную mainComponentBuilder,
        // и мы сможем его этот билдер использовать для построения сабкомпонента.
        (application as App).appComponent8.injectMainActivity(this)
        val mainComponent = mainComponent8BuilderIn.activity(this).build()

    }
}