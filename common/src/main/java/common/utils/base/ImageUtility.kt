package common.utils.base

import android.annotation.SuppressLint
import android.content.Context
import android.renderscript.Allocation


object ImageUtility {
    var SPLASH_TIME_OUT_LONG = 0
    internal var SPLASH_TIME_OUT_MAX = 0
    var SPLASH_TIME_OUT_SHORT = 0
    private val TAG = "SaveImage Utils"

    @SuppressLint("WrongConstant")
    fun getAmazonMarket(context : Context) : Boolean {
        var AMAZON_MARKET = 0
        try {
            AMAZON_MARKET = context.packageManager.getApplicationInfo(context.packageName , Allocation.USAGE_SHARED).metaData.getInt("amazon_market")
            if (AMAZON_MARKET < 0) {
                AMAZON_MARKET = 0
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }

        return if (AMAZON_MARKET == 1) {
            true
        } else false
    }
}
