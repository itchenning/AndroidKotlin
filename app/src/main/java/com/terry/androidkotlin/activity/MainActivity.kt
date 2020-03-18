package com.terry.androidkotlin.activity

import android.os.Bundle
import android.view.View
import com.terry.androidkotlin.R
import com.terry.androidkotlin.activity.view.ViewMainActivity
import common.base.BaseActivity
import common.helper.SingleClickListener
import common.utils.base.ThemeHelper
import common.utils.utilcode.util.IntentUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Author: Terry
 * Date: 2020-01-18 12:40
 * Comment:
 */
class MainActivity : BaseActivity() , SingleClickListener {
    override fun onCreate(savedInstanceState : Bundle?) {
        ThemeHelper.setTheme(this , false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSingleClick(view : View) {
        when (view) {
            viewBtn -> IntentUtils.startActivity(this , ViewMainActivity::class.java)
        }
    }
}