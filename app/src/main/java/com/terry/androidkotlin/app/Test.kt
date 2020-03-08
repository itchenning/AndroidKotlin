package com.terry.androidkotlin.app

import android.Manifest
import android.app.Activity
import com.terry.androidkotlin.BuildConfig
import common.base.OneCrashCatcher
import common.test.TestHelper
import common.utils.base.PermissionsUtils


/**
 * Author: Terry
 * Date: 2019-01-21 21:36
 * Comment:测试用的类。
 */
object Test {

    private val TAG = Test::class.java.simpleName
    private val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE)

    fun test(context : Activity) {
        if (! BuildConfig.LOG_DEBUG && ! TestHelper.isTestDevice(context)) {
            return
        }
        catchLastCrash(context)
    }

    private fun catchLastCrash(context : Activity) {
        requestPermissions(context)
        Thread.setDefaultUncaughtExceptionHandler(OneCrashCatcher())
    }

    private fun requestPermissions(context : Activity) {
        PermissionsUtils.requestPermissions(context , 0 , *PERMISSIONS)
    }
}
