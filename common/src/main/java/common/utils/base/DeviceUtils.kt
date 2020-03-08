package common.utils.base

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


/**
 * Author: Terry
 * Date: 2017/10/17 11:46
 * Description: 设备相关工具类。
 */
object DeviceUtils {

    fun getActivityWidth(activity : Activity) : Int {
        val metric = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric)
        return metric.widthPixels
    }


    fun getActivityHeight(activity : Activity) : Int {
        val metric = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric)
        return metric.heightPixels
    }

    /**
     * 获得设备操作系统的名字，如：KTU84P
     * @return
     */
    val osName : String
        get() = Build.DISPLAY.replace("\"" , "")

    /**
     * 获得设备的安卓版本号：如4.1.2
     * @return
     */
    val androidVersion : String
        get() = Build.VERSION.RELEASE

    /**
     * 获得设备的厂商。如：LGE
     * @return
     */
    val vendor : String
        get() = Build.MANUFACTURER.replace("\"" , "")

    /**
     * 获得设备型号。如：Nexus 5
     * @return
     */
    val model : String
        get() = Build.MODEL.replace("\"" , "")

    /**
     * 获得设备名称。如:LGE-Nexus 5
     * @return
     */
    val deviceName : String
        get() = Build.MANUFACTURER + "-" + Build.MODEL

    /**
     * 获取设备的安卓API版本号。如19
     * @return
     */
    val apiLevel : String
        get() = Build.VERSION.SDK_INT.toString() + ""

    /**
     * 获取设备的IP地址。
     * @param context
     * @return
     */
    fun getIPAddress(context : Context) : String? {
        val info = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (info != null && info.isConnected) {
            if (info.type == ConnectivityManager.TYPE_MOBILE) { //当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    val en = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf = en.nextElement()
                        val enumIpAddr = intf.inetAddresses
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress = enumIpAddr.nextElement()
                            if (! inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e : SocketException) {
                    e.printStackTrace()
                }

            } else if (info.type == ConnectivityManager.TYPE_WIFI) { //当前使用无线网络
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                return formatIP(wifiInfo.ipAddress)
            }
        }
        return null
    }

    /**
     * 格式化IP地址。
     * @param ip
     * @return
     */
    private fun formatIP(ip : Int) : String {
        return (ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "." + (ip shr 24 and 0xFF)
    }

    /**
     * 获取设备ID.
     * @param ctx
     * @return
     */
    fun getIMEI(ctx : Context) : String? {
        val tm = ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager ?: return null
        return tm.deviceId
    }

    /**
     * 获取设备的mac地址。
     * @param ctx
     * @return
     */
    fun getMAC(ctx : Context) : String? {
        val wifiManager = ctx.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return if (wifiManager == null || wifiManager.connectionInfo == null) {
            null
        } else wifiManager.connectionInfo.macAddress
    }

    /**
     * 获取屏幕的宽。
     * @param context
     * @return
     */
    fun getScreenWidth(context : Context) : Int {
        val wm = getWindonManager(context)
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    /**
     * 获取屏幕的高。
     * @param context
     * @return
     */
    fun getScreenHeight(context : Context) : Int {
        val wm = getWindonManager(context)
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    /**
     * 获取窗口管理器。
     * @param context
     * @return
     */
    private fun getWindonManager(context : Context) : WindowManager {
        return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    /**
     * 设备的语言。
     * @param context
     * @return
     */
    fun getLanguage(context : Context) : String? {
        val requestLanguage : String
        val language = Locale.getDefault().language
        val country = getCountry(context)
        if ("zh".equals(language , ignoreCase = true)) {
            if ("CN".equals(country , ignoreCase = true)) {
                requestLanguage = language
            } else {
                requestLanguage = "$language-$country"
            }
        } else {
            requestLanguage = language
        }
        return requestLanguage
    }

    /**
     * 设备的国家。
     * @param context
     * @return
     */
    fun getCountry(context : Context) : String {
        val tm : TelephonyManager
        try {
            tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var id : String? = tm.simCountryIso
            if (TextUtils.isEmpty(id)) {
                id = Locale.getDefault().country
            }
            if (id == null) {
                id = "d"
            }
            id = id.toLowerCase()
            return id
        } catch (e : Exception) {
            return "d"
        }

    }

    /**
     * 获取设备的状态栏的高度。
     * @param activity
     * @return
     */
    fun getDeviceStateBarHeight(activity : Activity) : Int {
        //获取绘制应用的屏幕区域的高度。
        val outRect = Rect()
        activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect)
        val drawAppHeight = outRect.height()
        return getScreenHeight(activity) - drawAppHeight
    }

    /**
     * 获取导航栏的高度。
     * @param context
     * @return
     */
    fun getNavigationBarHeight(context : Context) : Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height" , "dimen" , "android")
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 获取手机内存的总大小。
     * @param context
     * @return ：类型：byte
     */
    fun getTotalMemery(context : Context) : Long {
        val str1 = "/proc/meminfo" // 系统内存信息文件
        val str2 : String
        val arrayOfString : Array<String>
        var initial_memory : Long = 0

        try {
            val localFileReader = FileReader(str1)
            val localBufferedReader = BufferedReader(localFileReader , 8192)
            str2 = localBufferedReader.readLine() // 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            initial_memory = java.lang.Long.valueOf(arrayOfString[1]) * 1024 // 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close()

        } catch (e : IOException) {
            return Integer.MAX_VALUE.toLong()
        }

        return if (initial_memory <= 0) {
            Integer.MAX_VALUE.toLong()
        } else initial_memory
    }

    /**
     * 手机当前是否亮屏。
     * @param context
     * @return
     */
    fun isScreenOn(context : Context) : Boolean {
        var isScreenOn = false
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        isScreenOn = powerManager.isScreenOn
        return isScreenOn
    }

}
