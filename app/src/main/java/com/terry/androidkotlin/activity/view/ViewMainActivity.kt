package com.terry.androidkotlin.activity.view

import android.os.Bundle
import com.terry.androidkotlin.R
import common.base.BaseActivity
import common.utils.base.ThemeHelper

/**
 * Author: Terry
 * bilibili: 码农安小辰
 * Github: https://github.com/itchenning/AndroidKotlin
 * Date: 2020-03-08 16:29
 * Comment:
 */
class ViewMainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        ThemeHelper.setTheme(this , false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
    }
}