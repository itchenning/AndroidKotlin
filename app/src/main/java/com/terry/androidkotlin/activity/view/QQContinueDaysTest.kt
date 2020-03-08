package com.terry.androidkotlin.activity.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.terry.androidkotlin.R
import common.base.BaseActivity
import common.helper.SingleClickListener
import common.utils.base.ThemeHelper
import kotlinx.android.synthetic.main.activity_qq_continue_days.*

/**
 * Author: Terry
 * bilibili: 码农安小辰
 * Github: https://github.com/itchenning/AndroidKotlin
 * Date: 2020-03-08 18:23
 * Comment:
 */
class QQContinueDaysTest : BaseActivity() , SingleClickListener {
    override fun onCreate(savedInstanceState : Bundle?) {
        ThemeHelper.setTheme(this , false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qq_continue_days)
    }

    override fun onSingleClick(view : View) {
        when (view) {
            startBtn -> start()
            clearBtn -> et.setText("")
        }
    }

    private fun start() {
        val num = et.text.toString()
        if (TextUtils.isEmpty(num)) {
            return
        }
        changeScoreTextView.start(num.toInt())
    }
}