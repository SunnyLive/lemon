/*
 * Created by sunny on 18-1-12.
 *----------Dragon be here!----------/
 *      ┏┓       ┏┓
 *     ┏┛┻━━━━━━━┛┻┓
 *     ┃　　　　    ┃
 *     ┃     ━     ┃
 *     ┃   ┳┛ ┗┳   ┃
 *     ┃　　　　    ┃
 *     ┃     ┻     ┃
 *     ┃　　　　    ┃
 *     ┗━━━┓   ┏━━━┛
 *         ┃   ┃神兽保佑
 *         ┃   ┃代码无BUG！
 *         ┃   ┗━━━━━━━━━━┓
 *         ┃   　　　 　   ┣┓
 *         ┃              ┏┛
 *         ┗┓┓┏━━━━━━━━┳┓┏┛
 *          ┃┫┫        ┃┫┫
 *          ┗┻┛        ┗┻┛
 * ━━━━━━神兽出没━━━━━━by:coder-yang
 * <p>
 * 权限处理的Activity
 */

package com.example.commonlibrary.per

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi


class PermissionActivity : Activity() {

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent

        val mPermissions = i.getStringArrayExtra(PERMISSIONS)
        if (mPermissions == null || mPermissions.size <= 0) {
            mPermissionListener = null
            mRationaleListener = null
        }

        //查询是否被用户拒绝过这个权限
        if (mRationaleListener != null) {
            var rationale = false
            for (permission in mPermissions!!) {
                rationale = shouldShowRequestPermissionRationale(permission)
                if (rationale) break
            }
            mRationaleListener!!.onRationaleResult(rationale)
            mRationaleListener = null
            finish()
            return
        }

        if (mPermissionListener != null)
            requestPermissions(mPermissions!!, 10086)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (mPermissionListener != null)
            mPermissionListener!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionListener = null
        finish()
    }

    interface RationaleListener {
        fun onRationaleResult(showRationale: Boolean)
    }


    interface PermissionListener {
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    }

    companion object {

        val PERMISSIONS = "key_input_permissions"

        private var mPermissionListener: PermissionListener? = null//执行权限申请
        private var mRationaleListener: RationaleListener? = null //查询申请权限是否被拒绝过


        fun setmPermissionListener(mPermissionListener: PermissionListener) {
            PermissionActivity.mPermissionListener = mPermissionListener
        }

        fun setmRationaleListener(mRationaleListener: RationaleListener) {
            PermissionActivity.mRationaleListener = mRationaleListener
        }
    }

}
