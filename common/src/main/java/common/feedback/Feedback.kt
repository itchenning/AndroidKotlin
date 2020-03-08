package common.feedback

import android.app.Activity
import android.content.Context


object Feedback {
    internal val interval = 12
    internal val limit = 2
    private fun checkShowFeedBack(context : Context) : Boolean {
        val feedbackSharedPreference = FeedbackSharedPreference.getInstance(context)
        return (Utils.checkNetValid(context) //网络状态

                && ! feedbackSharedPreference.hasShow() //没有进过评价

                && Math.abs(System.currentTimeMillis() - feedbackSharedPreference.lastShowTime) >= interval * 3600000 //展示间隔

                && feedbackSharedPreference.showCount < limit) //总展示次数
    }

    fun shouldShowRate(activity : Activity) : Boolean {
        if (! checkShowFeedBack(activity)) {
            return false
        }
        val feedbackSharedPreference = FeedbackSharedPreference.getInstance(activity)
        feedbackSharedPreference.lastShowTime = System.currentTimeMillis()
        feedbackSharedPreference.addShowCount()
        return true
    }
}