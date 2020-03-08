package com.terry.androidkotlin.helper

import com.terry.androidkotlin.activity.MainActivity
import com.terry.androidkotlin.model.Function

/**
 * Author: Terry
 * bilibili: 码农安小辰
 * Github: https://github.com/itchenning/AndroidKotlin
 * Date: 2020-03-08 16:43
 * Comment:
 */
object ViewHelper {
    private val datas = mutableListOf<Function>()

    init {
        datas.add(Function("哈哈哈哈哈" , MainActivity::class.java))
    }

    fun getAll() : MutableList<Function> {
        return datas
    }
}