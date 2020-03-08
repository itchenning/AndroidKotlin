package common.utils.base

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import common.base.CommonSdk
import common.utils.utilcode.util.ThreadUtils


/**
 * Author: Terry
 * Date: 2017/10/17 19:21
 * Description: Toast 工具类。
 */
object ToastUtils {

    fun showToast(context : Context , content : String) {
        ThreadUtils.uiExcude(Runnable {
            val toast = Toast.makeText(context , content , Toast.LENGTH_LONG)
            val height = DeviceUtils.getScreenHeight(context)
            toast.setGravity(Gravity.TOP , 0 , (height / 3 * 1.8F).toInt());
            toast.show()
        })
    }

    fun showToast(context : Context , res : Int) {
        ThreadUtils.uiExcude(Runnable {
            val content = CommonSdk.getApp().getResources()?.getString(res)
            val toast = Toast.makeText(context , content , Toast.LENGTH_LONG)
            val height = DeviceUtils.getScreenHeight(context)
            toast.setGravity(Gravity.TOP , 0 , (height / 3 * 1.8F).toInt());
            toast.show()
        })
    }
}
