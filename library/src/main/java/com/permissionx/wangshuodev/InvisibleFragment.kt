package com.permissionx.wangshuodev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment



// 给任意类型参数指定一个别名
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment: Fragment() {

    // 运行时权限申请结果的回调通知方式
    private var callback: PermissionCallback? = null

    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}