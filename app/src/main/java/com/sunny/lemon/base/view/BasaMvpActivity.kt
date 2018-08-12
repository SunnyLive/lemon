//package com.sunny.lemon.base.view
//
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import com.sunny.lemon.base.proxy.ActivityMvpProxy
//import com.sunny.lemon.p.BasePresenter
//
//
//open abstract class BasaMvpActivity<P : BasePresenter> : AppCompatActivity(),BaseView{
//
//
//    private lateinit var mMvpProxy : ActivityMvpProxy
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView()
//
//    }
//
//    abstract fun initData()
//
//    abstract fun initView()
//
//    abstract fun setContentView()
//
//    private fun createMvpProxy(): ActivityMvpProxy {
//        if (mMvpProxy == null) {null
//            mMvpProxy = ActivityMvpProxyImpl(this)
//        }
//        return mMvpProxy
//    }
//
//
//}