package com.sunny.lemon.base.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open abstract class BaseFragment() : Fragment(){


    lateinit var mView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = setContentView(inflater,container)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
        initData()
        super.onActivityCreated(savedInstanceState)
    }


    abstract fun setContentView(inflater: LayoutInflater,container: ViewGroup?):View

    abstract fun initView()

    abstract fun initData()

}