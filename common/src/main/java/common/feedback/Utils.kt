package common.feedback

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.LayoutDirection.RTL
import android.view.WindowManager
import java.util.*

object Utils {

    val f3545a = arrayOf("MB525" , "ME525" , "MB526" , "ME526" , "ME525+" , "ME811")
    val f3546b = arrayOf("GT-I9103")

    fun checkNetValid(context : Context) : Boolean {
        val activeNetworkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun checkRTL(context : Context) : Boolean {
        var z = false
        if (Build.VERSION.SDK_INT < 17) {
            return Locale.getDefault().language != null && Locale.getDefault().language.trim { it <= ' ' } == "ar"
        } else {
            val z2 = context.applicationInfo.flags and 4194304 == 4194304
            val z3 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == RTL
            if (z2 && z3) {
                z = true
            }
            return z
        }
    }

    fun getWidth(context : Context) : Int {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun dp2Pix(context : Context , f : Float) : Int {
        return (context.resources.displayMetrics.density * f + 0.5f).toInt()
    }

}
