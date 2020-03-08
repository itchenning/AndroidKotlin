package com.terry.androidkotlin.app

import android.app.Application
import com.terry.androidkotlin.activity.MainActivity
import common.base.CommonSdk

/**
 * Author: Terry
 * Date: 2020-01-18 12:43
 * Comment:
 */
class VApp : Application() {

    override fun onCreate() {
        app = this
        super.onCreate()
        CommonSdk.init(this , MainActivity::class.java)
    }

    companion object {
        private lateinit var app : Application

        fun get() : Application {
            return app
        }
    }
}