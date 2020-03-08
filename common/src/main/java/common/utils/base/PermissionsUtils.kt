package common.utils.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Proxy
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import common.base.CommonSdk.getApp
import java.io.BufferedReader
import java.io.FileReader
import java.net.NetworkInterface
import java.util.*

/**
 * Author: Terry
 * Date: 2017/12/28 20:25
 * Description:
 */
object PermissionsUtils {
    private val TAG = PermissionsUtils::class.java.simpleName

    fun checkAlertWindowsPermission(context : Context) : Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            return true
        }
        return Settings.canDrawOverlays(context)
    }

    /**
     * 安卓6.0以上权限主动请求。
     *
     * @param activity
     * @param requestCode 请求码
     * @param permisions
     */
    fun requestPermissions(activity : Activity? , requestCode : Int , vararg permisions : String) {
        val permissionStrs : MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT < 23) {
            return
        }
        var state : Int
        for (permission in permisions) {
            state = ContextCompat.checkSelfPermission(activity !! , permission)
            if (state != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(permission)
            }
        }
        val stringArray = permissionStrs.toTypedArray()
        if (stringArray.size > 0) {
            ActivityCompat.requestPermissions(activity !! , stringArray , requestCode)
        }
    }

    fun hasNotificationPermission() : Boolean {
        return NotificationManagerCompat.from(getApp()).areNotificationsEnabled()
    }

    /**
     * 权限检查。
     *
     * @param activity
     * @param permisions
     * @return true : 检查通过，false 没通过。
     */
    fun checkPermissions(activity : Activity? , vararg permisions : String) : Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            return true
        }
        val permissionStrs : MutableList<String> = ArrayList()
        var state : Int
        for (permission in permisions) {
            state = ContextCompat.checkSelfPermission(activity !! , permission)
            if (state != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(permission)
            }
        }
        val stringArray = permissionStrs.toTypedArray()
        return if (stringArray.size > 0) {
            false
        } else true
    }

    /**
     * 是否使用代理(WiFi状态下的,避免被抓包)
     *
     * @param context
     * @return true:正在使用wifi代理,false:没有使用wifi代理.
     */
    fun checkUseWifiProxy(context : Context?) : Boolean {
        val IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
        val proxyAddress : String
        val proxyPort : Int
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost")
            val portStr = System.getProperty("http.proxyPort")
            proxyPort = (portStr ?: "-1").toInt()
        } else {
            proxyAddress = Proxy.getHost(context)
            proxyPort = Proxy.getPort(context)
        }
        return ! TextUtils.isEmpty(proxyAddress) && proxyPort != - 1
    }

    /**
     * 检测是否正在使用VPN代理.
     *
     * @return true:正在使用vpn代理,false:没有使用vpn代理.
     */
    fun checkUseVpnProxy() : Boolean {
        try {
            val niList = NetworkInterface.getNetworkInterfaces()
            if (niList != null) {
                for (intf in Collections.list(niList)) {
                    if (! intf.isUp || intf.interfaceAddresses.size == 0) {
                        continue
                    }
                    if ("tun0" == intf.name || "ppp0" == intf.name) {
                        return true
                    }
                }
            }
        } catch (e : Throwable) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 检测手机是否正在进行USB调试:打开USB调试并且当前连接了USB
     *
     * @param context
     * @return true->正在进行USB调试，false->没有进行USB调试，有可能USB调试打开了，但是没有连接USB.
     * 注意：如果用户用wifi debug的方式调试的话，没有办法判断。
     */
    fun checkUsbDebug(context : Context) : Boolean {
        val enableAdb = Settings.Secure.getInt(context.contentResolver , Settings.Secure.ADB_ENABLED , 0) > 0
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatusIntent = context.registerReceiver(null , ifilter)
        val chargePlug = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED , - 1)
        val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        return enableAdb && usbCharge
    }

    /**
     * 检测是否在容器中运行。
     *
     * @param context
     * @return true->在容器中运行，false->没有在容器中运行。
     */
    @SuppressLint("SdCardPath")
    fun checkInVA(context : Context) : Boolean {
        val priDir = context.filesDir.absolutePath
        return ! (priDir.startsWith("/data/data/" + context.packageName) || priDir.startsWith("/data/user/0/" + context.packageName))
    }

    /**
     * 检测是否在Xposed中运行。
     *
     * @param context
     * @return true->在Xposed中运行，false->没有在Xposed中运行。
     * 主要通过检测包名等固定的字符串信息，如果别人修改过的Xposed框架，有可能检测不到。
     */
    fun checkInXposed(context : Context) : Boolean { //1.检测包名。
        val packageManager = context.applicationContext.packageManager
        val appliacationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (item in appliacationInfoList) {
            if (item.packageName == "de.robv.android.xposed.installer") {
                return true
            }
            if (item.packageName == "com.saurik.substrate") {
                return true
            }
        }
        //2.检查调用栈。
        try {
            throw Exception("check xposed")
        } catch (e : Exception) {
            var zygoteInitCallCount = 0
            for (item in e.stackTrace) { // 检测"com.android.internal.os.ZygoteInit"是否出现两次，如果出现两次，则表明Substrate框架已经安装
                if (item.className == "com.android.internal.os.ZygoteInit") {
                    zygoteInitCallCount ++
                    if (zygoteInitCallCount == 2) {
                        return true
                    }
                }
                if (item.className == "com.saurik.substrate.MS$2" && item.methodName == "invoke") {
                    return true
                }
                if (item.className == "de.robv.android.xposed.XposedBridge" && item.methodName == "main") {
                    return true
                }
                if (item.className == "de.robv.android.xposed.XposedBridge" && item.methodName == "handleHookedMethod") {
                    return true
                }
            }
        }
        //3.检测内存。
        val libraries : MutableSet<String> = HashSet()
        val mapsFilename = "/proc/" + Process.myPid() + "/maps"
        try {
            val reader = BufferedReader(FileReader(mapsFilename))
            var line : String
            while (reader.readLine().also { line = it } != null) {
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    val n = line.lastIndexOf(" ")
                    libraries.add(line.substring(n + 1))
                }
            }
            for (library in libraries) {
                if (library.contains("com.saurik.substrate")) {
                    return true
                }
                if (library.contains("XposedBridge.jar")) {
                    return true
                }
            }
            reader.close()
        } catch (e : Exception) { //
        }
        return false
    }

    fun openNotificationPermission(context : Context) {
        try {
            val intent = Intent()
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(Settings.EXTRA_APP_PACKAGE , context.packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID , context.applicationInfo.uid)
            intent.putExtra("app_package" , context.packageName)
            intent.putExtra("app_uid" , context.applicationInfo.uid)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e : Exception) {
            e.printStackTrace()
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package" , context.packageName , null)
            intent.data = uri
            context.startActivity(intent)
        }
    }
}