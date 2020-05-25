package com.lmy.permissionx

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/25 9:59
 * @Compony 永远相信美好的事物即将发生
 */

//typealias给函数设置别名
typealias PermissionCallBack = ((Boolean, List<String>) -> Unit)

class InvisibleFragment : Fragment() {
    //定义一个callback来为运行时权限请求结果的回调通知方式
    private var callback: PermissionCallBack? = null

    fun requestNow(cb: PermissionCallBack, vararg permission: String) {
        callback = cb
        requestPermissions(permission, 1)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            //deiedList用于记录未被用户授予的权限 然后遍历grantResults数组如果发现未被授予的权限则添加到deiedList中
            val deiedList = ArrayList<String>()
            for ((index, resust) in grantResults.withIndex()) {
                if (resust != PackageManager.PERMISSION_GRANTED) {
                    deiedList.add(permissions[index])
                }
            }
            val allGrated = deiedList.isEmpty()
            //判断deiedList时候为空如果未空则证明权限全部授予
            // 如果不为空权限未授予然后把拒绝的权限通过callback回调传递出去
            //let函数可以将原始对象作为参数传递到lambada表达式中
            callback?.let {
                it(allGrated, deiedList)
                //?.表示对象未空的时候什么都不做 不为空的时候执行let函数
            }
            /**
             * 完整的写法是这样的
             */
//            callback?.let { callback ->
//                callback(allGrated, deiedList)
//            }
        }
    }
}