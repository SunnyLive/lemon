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
 */

package com.example.commonlibrary.per


interface RequestRationaleListener {
    fun showRequestPermissionRationale(mRationale: Rationale, requestCode: Int, vararg permissions: String)
}
