package com.sunny.lemon.utils

import android.support.v4.app.FragmentManager
import com.sunny.lemon.base.view.BaseFragment

class FragmentHelper(mFragmentManager: FragmentManager, mFlag: Int) {

    var mFragmentManager = mFragmentManager
    var mFlag = mFlag


    fun addFragment(fmg: BaseFragment) {
        val t = mFragmentManager.beginTransaction()
        t.add(mFlag, fmg)
        t.commitAllowingStateLoss()
    }


    fun switchFragment(fmg: BaseFragment) {

        val t = mFragmentManager.beginTransaction()

        for (fragment in mFragmentManager.fragments) {
            t.hide(fragment)
        }

        if (mFragmentManager.fragments.contains(fmg)) {
            t.show(fmg)
        } else {
            t.add(mFlag, fmg)
        }

        t.commitAllowingStateLoss()
    }


}