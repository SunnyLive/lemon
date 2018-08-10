package com.sunny.lemon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        test()

    }


    fun test() {


        Observable.create(ObservableOnSubscribe<Document> {

            it.onNext(Jsoup.connect("https://www.avicks.com/")
                    .timeout(10 * 1000)
                    .get())
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e("123456",it.toString())

                }


    }


}
