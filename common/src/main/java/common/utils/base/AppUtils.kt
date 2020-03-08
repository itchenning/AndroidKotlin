package common.utils.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

/**
 * Author: Terry
 * Date: 2019-04-04 19:00
 * Comment:
 */
object AppUtils {

    //Intent是否存在。
    fun checkIntent(context : Context , intent : Intent) : Boolean {
        return context.packageManager.resolveActivity(intent , PackageManager.MATCH_DEFAULT_ONLY) != null
    }

    //跳转到应用市场。
    fun jumpMarket(context : Context) {
        val intentRateMe = Intent(Intent.ACTION_VIEW)
        if (ImageUtility.getAmazonMarket(context)) {
            intentRateMe.data = Uri.parse("amzn://apps/android?p=" + context.packageName.toLowerCase())
        } else {
            intentRateMe.data = Uri.parse("market://details?id=" + context.packageName.toLowerCase())
        }
        if (checkIntent(context , intentRateMe)) {
            context.startActivity(intentRateMe)
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://play.google.com/store/apps/details?id=" + context.packageName)
        if (checkIntent(context , intent)) {
            context.startActivity(intent)
        }
    }
}
