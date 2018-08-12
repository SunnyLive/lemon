package com.sunny.lemon.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.lemon.base.view.BaseFragment

class PerFragment : BaseFragment() {

    companion object {
        fun instance() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE: PerFragment
            get() = PerFragment()
    }

    override fun setContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}