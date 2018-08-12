package com.sunny.lemon.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sunny.lemon.R
import com.sunny.lemon.utils.DO_MAIN
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class PlayerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        var link = intent.getStringExtra("link")

        Observable.create(ObservableOnSubscribe<Document> {
            it.onNext(Jsoup.connect(DO_MAIN + link)
                    .timeout(10 * 1000)
                    .get())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    for (element in it.select("li")) {

                        val videoLink = DO_MAIN + element.select("a[href]").attr("href").toString()


                        Observable.create(ObservableOnSubscribe<Connection.Response> {

                            var response = Jsoup.connect(videoLink).ignoreContentType(true).timeout(8000).execute()

                            it.onNext(
                                    response
                            )
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {

                                    val bytes = it.bodyAsBytes()




                                }

                    }
                }
    }






}