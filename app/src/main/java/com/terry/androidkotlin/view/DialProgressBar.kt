package com.terry.androidkotlin.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import common.utils.base.DipPixelUtils

/**
 * Author: Terry
 * bilibili: 码农安小辰
 * Github: https://github.com/itchenning/AndroidKotlin
 * Date: 2020-03-15 23:51
 * Comment:
 */
class DialProgressBar(context : Context , attrs : AttributeSet) : View(context , attrs) {

    private val mBgPaint = Paint()
    private val mProgressPaint = Paint()
    private val START_ANGLE = 135F
    private val ROTATION_MAX = 270F
    private var mProgress = 50

    init {
        mBgPaint.style = Paint.Style.STROKE
        mBgPaint.strokeWidth = DipPixelUtils.dip2px(6F).toFloat()
        mBgPaint.color = Color.parseColor("#E0E0E0")
        mBgPaint.strokeCap = Paint.Cap.ROUND

        mProgressPaint.style = Paint.Style.STROKE
        mProgressPaint.strokeWidth = DipPixelUtils.dip2px(2F).toFloat()
        mProgressPaint.color = Color.parseColor("#18B4ED")
        mProgressPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas : Canvas) {
        val rectf = RectF()
        rectf.left = mBgPaint.strokeWidth / 2
        rectf.top = mBgPaint.strokeWidth / 2
        rectf.right = width - mBgPaint.strokeWidth / 2
        rectf.bottom = height - mBgPaint.strokeWidth / 2
        drawBg(canvas , rectf)
        drawProgress(canvas , rectf)
    }

    private fun drawProgress(canvas : Canvas , rectf : RectF) {
        val curRotation = mProgress / 100F * ROTATION_MAX
        canvas.drawArc(rectf , START_ANGLE , curRotation , false , mProgressPaint)
    }

    private fun drawBg(canvas : Canvas , rectf : RectF) {
        canvas.drawArc(rectf , START_ANGLE , ROTATION_MAX , false , mBgPaint)
    }
}