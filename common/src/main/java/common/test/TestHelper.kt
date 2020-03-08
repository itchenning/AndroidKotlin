package common.test

import android.content.Context
import android.provider.Settings


/**
 * Author: Terry
 * Date: 2019-01-21 21:36
 * Comment:测试用的类。
 */
object TestHelper {

    private val testDevideIDs = arrayOf( //
        "e34e8e6bd25477c9" , //华为 P9
        "d85c25496efd4c68" , //三星 G6200
        "c44aed88abc94579"  //魅族 Metal
    )

    fun isTestDevice(context : Context) : Boolean {
        val id = getAndroidId(context)
        return id in testDevideIDs
    }

    private fun getAndroidId(context : Context) : String {
        return Settings.System.getString(context.contentResolver , Settings.Secure.ANDROID_ID)
    }
}


