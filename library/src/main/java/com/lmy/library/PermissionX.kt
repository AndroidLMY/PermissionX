package com.lmy.permissionx

import android.app.Activity
import androidx.fragment.app.FragmentActivity

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/25 10:13
 * @Compony 永远相信美好的事物即将发生
 */
object PermissionX {
    private const val TAG = "InvisibleFragment"
    fun requst(
        activity: FragmentActivity,
        vararg permissing: String,
        callBack: PermissionCallBack
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        //这个地方其实是高阶函数的应用 把这个callBack传递下去
        // 到请求回调的时候在调用callback的callback方法就可以回调到请求权限的lambda表达式中了
        fragment.requestNow(callBack, *permissing)
    }
}