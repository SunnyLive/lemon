package com.sunny.lemon.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.RadioGroup
import android.widget.Toast
import com.sunny.lemon.R
import com.sunny.lemon.R.id.fl_fragment_content
import com.sunny.lemon.base.view.BaseFragment
import com.sunny.lemon.utils.DO_MAIN
import com.sunny.lemon.utils.FragmentHelper
import com.sunny.lemon.view.fragment.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {


    lateinit var mFragmentHelper: FragmentHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rg_navigation_bar.setOnCheckedChangeListener(this)
        mFragmentHelper = FragmentHelper(supportFragmentManager, R.id.fl_fragment_content)
        indicator(0)
    }


    fun test() {


        Observable.create(ObservableOnSubscribe<Document> {

            it.onNext(Jsoup.connect(DO_MAIN)
                    .timeout(10 * 1000)
                    .get())
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }

    }




    fun indicator(index:Int){
        rg_navigation_bar.getChildAt(index).performClick()
    }



    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        val mFragment: BaseFragment
        when (checkedId) {
            R.id.rb_home_bar -> mFragment = HomeFragment.instance()
            R.id.rb_gallery_bar -> mFragment = GalleryFragment.instance()
            R.id.rb_video_bar -> mFragment = VideoFragment.instance()
            R.id.rb_book_bar -> mFragment = BookFragment.instance()
            R.id.rb_per_bar -> mFragment = PerFragment.instance()
            else -> {
                mFragment = HomeFragment.instance()
            }
        }
        mFragmentHelper.switchFragment(mFragment)


    }


}

fun Context.toast(charSequence: CharSequence) =
        Toast.makeText(
                this.applicationContext
                , charSequence
                , Toast.LENGTH_SHORT)
                .show()