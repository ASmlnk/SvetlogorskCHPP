package com.example.mylibrary.RxJava.lessons_9_retrofit.no_rx

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.RxJava.lessons_9_retrofit.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityRetrofit: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        //создает обьект ретрофит
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://rawgit.com/startandroid/data/master/message")
            .build()

        //просим ретрофит реализовать интерфейс
        val webApi = retrofit.create(WebApi::class.java)

        //В результате вызова messages мы получаем объект Call.
        // Он содержит в себе всю информацию: что и откуда грузить,
        // и в каком виде возвращать.
        val call = webApi.message(1)


        //запустить Call и, тем самым, отправить наконец-то запрос на сервер
        call.enqueue(object : Callback<List<Message>>{
            override fun onResponse(
                call: Call<List<Message>>?,
                response: Response<List<Message>>?
            ) {
                if(response?.isSuccessful == true) {
                    val mes =  response.body().size
                    Log.d("TAG", "onResponse, message count $mes")
                } else {
                    Log.d("TAG", "onResponse error ${response?.code()}")
                }
            }

            override fun onFailure(call: Call<List<Message>>?, t: Throwable?) {
                Log.d("TAG", "onFailure $t")
            }

        })
        //Сам запрос будет выполнен в отдельном потоке, а результат придет нам в UI-потоке
        //Чтобы добраться до данных, надо вызвать метод response.body() (List<Messages>)
        //Результат: onResponse, messages count 50

    }
}