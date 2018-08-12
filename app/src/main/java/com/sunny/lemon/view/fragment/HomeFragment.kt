package com.sunny.lemon.view.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.lemon.R
import com.sunny.lemon.base.view.BaseFragment
import com.sunny.lemon.bean.HomeBean
import com.sunny.lemon.utils.DO_MAIN
import com.sunny.lemon.view.adpater.HomeAdapter
import com.sunny.lemon.view.view.GridDecoration
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HomeFragment : BaseFragment() {


    companion object {
        fun instance() = Holder.INSTANCE
    }


    private object Holder{
        val INSTANCE: HomeFragment
            get() = HomeFragment()
    }


    lateinit var mAdapter: HomeAdapter

    override fun setContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun initView() {
        val mRecyclerView = this.mView.findViewById<RecyclerView>(R.id.home_recycler)
        mAdapter = context?.let { HomeAdapter(it) }!!
        mRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mRecyclerView.addItemDecoration(GridDecoration(20))
        mRecyclerView.adapter = mAdapter
    }

    override fun initData() {


        Observable.create(ObservableOnSubscribe<Document> {
            it.onNext(Jsoup.connect(DO_MAIN)
                    .timeout(10 * 1000)
                    .get())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{

                    var mHomeDatas = ArrayList<HomeBean>()
                    for (element in it.select("li")) {

                        var data = HomeBean()
                        data.mPicture = element.select("img[src]").attr("src").toString()
                        data.mLink = element.select("a[href]").attr("href").toString()
                        data.mDescription = element.select("img[title]").attr("title").toString()
                        data.mTimer = element.select("span").text()
                        mHomeDatas.add(data)



                    }
                    mAdapter.setDatas(mHomeDatas)
                }
    }


}
