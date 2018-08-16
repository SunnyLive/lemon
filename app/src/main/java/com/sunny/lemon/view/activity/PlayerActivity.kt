package com.sunny.lemon.view.activity

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.example.commonlibrary.per.PermissionListener
import com.example.commonlibrary.per.PermissionUtils
import com.sunny.lemon.R
import com.sunny.lemon.utils.DO_MAIN
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_player.*
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

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

                            var response = Jsoup
                                    .connect(videoLink)
                                    .ignoreContentType(true)
                                    .timeout(10 * 1000).execute()

                            it.onNext(
                                    response
                            )
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {

                                    PermissionUtils.with(this).addPermission(
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE
                                    ).requestCode(10086)
                                            .callback(object : PermissionListener(){
                                                override fun onSuccess(requestCode: Int, vararg permissions: String) {
                                                    val bytes = it.bodyAsBytes()
                                                    var path = Environment.getExternalStorageDirectory().path + File.separator
                                                    var fileName = System.nanoTime().toString() + ".mp4"
                                                    val file = File(path,fileName)

                                                    if (file.exists()) {
                                                        file.delete()
                                                    }

                                                    var fileOutputStream: FileOutputStream? = null
                                                    var bufferedOutputStream: BufferedOutputStream? = null
                                                    try {
                                                        file.createNewFile()
                                                        fileOutputStream = FileOutputStream(file)
                                                        bufferedOutputStream = BufferedOutputStream(fileOutputStream)
                                                        bufferedOutputStream.write(bytes)
                                                        video_play.setVideoPath(file.path)
                                                    } finally {
                                                        fileOutputStream?.close()
                                                        bufferedOutputStream?.close()
                                                    }
                                                }
                                            })

                                }

                    }
                }
    }


}