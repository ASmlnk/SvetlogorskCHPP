package com.example.mylibrary.RxJava.lessons_9_retrofit.rx

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.RxJava.lessons_9_retrofit.Message
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ActivityRetrofitR: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl("http://rawgit.com/startandroid/data/master/message")
            .build()

        val webApi = retrofit.create(WebApiR::class.java)

        val observable = webApi.message(1)

        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Message>> {
                override fun onCompleted() {
                    Log.d("TAG", "onCompleted")
                }

                override fun onError(e: Throwable?) {
                    Log.d("TAG", "onError $e")
                }

                override fun onNext(t: List<Message>?) {
                    Log.d("TAG", "onNext ${t?.size}")
                }

            })
    }
}