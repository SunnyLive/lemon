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
 * 6.0+ android系统动态权限的处理
 */


package com.example.commonlibrary.per

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat

import java.util.ArrayList

class PermissionUtils private constructor(private val mContext: Context) {

    private var mPermissionListener: PermissionListener? = null
    private var mRequestRationaleListener: RequestRationaleListener? = null
    private var mRequestCode: Int = 0
    private var mPermission: Array<String>? = null

    fun requestCode(requestCode: Int): PermissionUtils {
        this.mRequestCode = requestCode
        return this
    }

    fun addPermission(vararg mPermission: String): PermissionUtils {
        this.mPermission = mPermission
        return this
    }

    fun callback(mPermissionListener: PermissionListener): PermissionUtils {
        this.mPermissionListener = mPermissionListener
        return this
    }

    fun requestRationale(mRequestRationaleListener: RequestRationaleListener): PermissionUtils {
        this.mRequestRationaleListener = mRequestRationaleListener
        return this
    }


    fun builder() {
        val b = Builder(mContext, mPermission, mPermissionListener, mRequestRationaleListener, mRequestCode)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            b.onRequestSuccess()
        } else {
            val permissions = checkSelfPermission(*mPermission!!)
            if (permissions.size > 0) {
                b.checkSelfPermission()
            } else {
                b.onRequestSuccess()
            }
        }
    }


    //验证是否受理注册此权限
    private fun checkSelfPermission(vararg permissions: String): Array<String> {
        val checkPermissions = ArrayList<String>()
        for (p in permissions) {
            if (ContextCompat.checkSelfPermission(this.mContext, p) != PackageManager.PERMISSION_GRANTED)
                checkPermissions.add(p)
        }
        return checkPermissions.toTypedArray<String>()
    }


    internal class Builder(private val mContext: Context, private val mPermission: Array<String>, private val mPermissionListener: PermissionListener?, private val mRequestRationaleListener: RequestRationaleListener?, private var mRequestCode: Int) : Rationale, PermissionActivity.PermissionListener, PermissionActivity.RationaleListener {

        override fun onRationaleResult(showRationale: Boolean) {
            if (showRationale && mRequestRationaleListener != null) {
                mRequestRationaleListener.showRequestPermissionRationale(this, mRequestCode, *mPermission)
            } else {
                resume()
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            this.mRequestCode = requestCode
            val deniedList = ArrayList<String>()
            for (i in permissions.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                    deniedList.add(permissions[i])
            }

            if (deniedList.isEmpty())
                onRequestSuccess()
            else if (shouldShowRequestPermissionRationale())
                onRequestFailed()
        }


        //查询是否受理
        private fun checkSelfPermission() {
            PermissionActivity.setmRationaleListener(this)
            val intent = Intent(mContext, PermissionActivity::class.java)
            intent.putExtra(PermissionActivity.PERMISSIONS, this.mPermission)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)
        }


        //用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        @RequiresApi(api = Build.VERSION_CODES.M)
        private fun shouldShowRequestPermissionRationale(): Boolean {

            var rationale = true
            for (permission in this.mPermission) {
                rationale = (mContext as? Activity)?.shouldShowRequestPermissionRationale(permission) ?: true
                if (!rationale) break
            }

            if (!rationale) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", mContext.packageName, null)
                intent.data = uri
                (mContext as Activity).startActivityForResult(intent, mRequestCode)
            }
            return rationale
        }


        override fun resume() {
            PermissionActivity.setmPermissionListener(this)
            val intent = Intent(mContext, PermissionActivity::class.java)
            intent.putExtra(PermissionActivity.PERMISSIONS, this.mPermission)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        override fun cacenl() {
            val results = IntArray(this.mPermission.size)
            for (i in mPermission.indices)
                results[i] = ContextCompat.checkSelfPermission(mContext, mPermission[i])
            onRequestPermissionsResult(this.mRequestCode, mPermission, results)
        }


        //成功
        private fun onRequestSuccess() {
            if (mPermissionListener == null) return
            mPermissionListener.onSuccess(this.mRequestCode, *this.mPermission)
        }

        //失败
        @RequiresApi(api = Build.VERSION_CODES.M)
        private fun onRequestFailed() {
            if (mPermissionListener == null) return
            mPermissionListener.onFailed(this.mRequestCode, *this.mPermission)
        }

    }

    companion object {

        fun with(mContext: Context): PermissionUtils {
            return PermissionUtils(mContext)
        }
    }

}
