package com.permissionx.wangshuodev

import androidx.fragment.app.FragmentActivity



// 单例类——为了让PermissionX中的接口能够更加方便地被调用

object PermissionX {

    private const val TAG = "InvisibleFragment"

    fun request(activity: FragmentActivity, vararg permissions: String, callback: PermissionCallback) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            // 在添加结束后一定要调用commitNow()方法，而不能调用commit()方法
            // 因为commit()方法并不会立即执行添加操作，因而无法保证下一行代码执行时InvisibleFragment已被添加到Activity中
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        // 这里的“*”并不是指针的意思，而是表示将一个数组转换成可变长度参数传递过去
        // 有了invisibleFragment的实例之后，接下来只需要调用它的requestNow()方法就能去申请运行时权限了，
        // 申请结果会自动回调到 callback参数中
        fragment.requestNow(callback, *permissions)
    }
}