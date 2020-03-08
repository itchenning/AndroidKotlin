package common.helper

import android.view.View

interface SingleClickListener : View.OnClickListener {


    open override fun onClick(v : View) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - ClickHelper.mLastClickTime > ClickHelper.timeInterval) {
            onSingleClick(v)
            ClickHelper.mLastClickTime = nowTime
        }
    }

    fun onSingleClick(view : View)
}