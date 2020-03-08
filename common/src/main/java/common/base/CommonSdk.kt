package common.base

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import com.baidu.crabsdk.CrabSDK
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

/**
 * Author: Terry
 * Date: 2019-07-27 21:50
 * Comment:
 */
object CommonSdk {
    private lateinit var sApp : Application
    private lateinit var splashClass : Class<out Activity>
    fun init(app : Application , splashClass : Class<out Activity>) {
        sApp = app
        CrabSDK.init(app , BuildConfig.CRAB_APP_ID)
        initAutoSize()
        this.splashClass = splashClass
    }

    private fun initAutoSize() {
        AutoSizeConfig.getInstance().setBaseOnWidth(true).unitsManager.setSupportDP(true).setSupportSP(true).supportSubunits = Subunits.MM
    }

    fun getApp() : Application {
        return sApp
    }

    fun getSplashClass() : Class<out Activity> {
        return splashClass
    }

    fun fixOnCreate(activity : Activity , savedInstanceState : Bundle?) {
        checkStatus(activity , savedInstanceState)
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && activityIsTrans(activity)) {
            fixOrientation()
        }
    }

    private fun checkStatus(activity : Activity , savedInstanceState : Bundle?) {
        if (savedInstanceState != null) {
            savedInstanceState.clear()
            startClearLastTask(activity)
        }
    }

    private fun startClearLastTask(restoreActivity : Activity) {
        val intent = Intent(restoreActivity , getSplashClass())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        restoreActivity.startActivity(intent)
        restoreActivity.finish()
    }

    private fun fixOrientation() {
        try {
            val field = Activity::class.java.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            val o = field.get(this) as ActivityInfo
            o.screenOrientation = - 1;
            field.setAccessible(false);
        } catch (e : Exception) {
            e.printStackTrace();
        }
    }

    private fun activityIsTrans(activity : Activity) : Boolean {
        var isTranslucentOrFloating = false
        try {
            val styleableRes = Class.forName("com.android.internal.R\$styleable").getField("Window").get(null) as IntArray
            val ta = activity.obtainStyledAttributes(styleableRes)
            val m = ActivityInfo::class.java.getMethod("isTranslucentOrFloating" , TypedArray::class.java)
            m.setAccessible(true);
            isTranslucentOrFloating = m.invoke(null , ta) as Boolean
            m.setAccessible(false);
        } catch (e : Exception) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating
    }
}
