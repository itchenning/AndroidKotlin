package common.utils.base

import common.base.CommonSdk

/**
 * Author: Terry
 * Date: 2017/10/17 14:39
 * Description: 适配相关的工具类。
 */
object DipPixelUtils {

    fun dip2px(dpValue : Float) : Int {
        val context = CommonSdk.getApp()
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(pxValue : Float) : Int {
        val context = CommonSdk.getApp()
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}
