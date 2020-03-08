package common.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import common.utils.base.FileUtils
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat

/**
 * Author: chenning
 * Date:2019/7/15
 * Comment: 异常捕获，只保留最新的crash， 同时复制到剪切板
 */
class OneCrashCatcher : Thread.UncaughtExceptionHandler {
    private val TAG = OneCrashCatcher::class.java.simpleName


    override fun uncaughtException(t : Thread , e : Throwable) {
        VLog.i(TAG , "uncaughtException")
        val path = Environment.getExternalStorageDirectory().toString() + "/data/" + CommonSdk.getApp().packageName + "/crash.txt"
        saveExceptionToFile(path , e)
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    private fun saveExceptionToFile(path : String , e : Throwable) {
        val currentTime = System.currentTimeMillis()
        val crashTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime)
        val file = File(path)
        try {
            FileUtils.fixFile(file)
            val fw = FileWriter(file , false)
            val pw = PrintWriter(fw)
            pw.println(crashTime)
            printPhoneInfo(pw)
            pw.println()
            pw.flush()
            e.printStackTrace(pw)
            e.printStackTrace()
            pw.close()
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
        copyToclip(path)
    }

    private fun copyToclip(path : String) {
        val file = File(path)
        if (! file.exists()) {
            return
        }
        val fileContent = FileUtils.getFileContent(file)
        val cm = CommonSdk.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("crash" , fileContent)
        cm.primaryClip = mClipData
    }

    /**
     * 输出手机信息
     */
    @Throws(PackageManager.NameNotFoundException::class)
    private fun printPhoneInfo(pw : PrintWriter) {
        val pm = CommonSdk.getApp().packageManager
        val pi = pm?.getPackageInfo(CommonSdk.getApp().packageName , PackageManager.GET_ACTIVITIES)
        pw.print("App version: ")
        pw.print(pi?.versionName)
        pw.print("_")
        pw.println(pi?.versionCode)

        //android版本号
        pw.print("OS Version: ")
        pw.print(Build.VERSION.RELEASE)
        pw.print("_")
        pw.println(Build.VERSION.SDK_INT)

        //制造商
        pw.print("Vendor: ")
        pw.println(Build.MANUFACTURER)

        //手机型号
        pw.print("Model: ")
        pw.println(Build.MODEL)

        //cpu架构
        pw.print("CPU ABI: ")
        pw.println(Build.CPU_ABI)
    }
}
