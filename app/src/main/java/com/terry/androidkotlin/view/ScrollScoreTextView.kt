package com.terry.androidkotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import common.utils.base.DipPixelUtils
import common.utils.base.PaintUtils

/**
 * Author: Terry
 * bilibili: 码农安小辰
 * Github: https://github.com/itchenning/AndroidKotlin
 * Date: 2020-03-22 16:39
 * Comment:
 */
class ScrollScoreTextView(context : Context , attrs : AttributeSet) : View(context , attrs) {

    private val mBgColor = Color.RED
    private val mSocrePaint = Paint()
    private val mUnitPaint = Paint()
    private var mScore = 1234
    private var mProgress = 0
    private var mOneWidth = 0F

    init {
        mSocrePaint.style = Paint.Style.FILL
        mSocrePaint.isAntiAlias = true
        mSocrePaint.textSize = DipPixelUtils.dip2px(20F).toFloat()
        mSocrePaint.isFakeBoldText = true
        mSocrePaint.color = Color.WHITE
        mOneWidth = mSocrePaint.measureText("0")

        mUnitPaint.style = Paint.Style.FILL
        mUnitPaint.isAntiAlias = true
        mUnitPaint.textSize = DipPixelUtils.dip2px(12F).toFloat()
        mUnitPaint.color = Color.WHITE
    }

    override fun onDraw(canvas : Canvas) {
        canvas.drawColor(mBgColor)
        drawScore(canvas)
        drawUnit(canvas)
    }

    private fun drawUnit(canvas : Canvas) {
        val scoreValue = mScore.toString()
        val point = PaintUtils.getCenterTextPoint(mSocrePaint , width , height , scoreValue)
        canvas.drawText("天" , point.x + mOneWidth * scoreValue.length.toFloat() , point.y.toFloat() , mUnitPaint)
    }

    private fun drawScore(canvas : Canvas) {
        val scoreValue = mScore.toString()
        val len = scoreValue.length
        val point = PaintUtils.getCenterTextPoint(mSocrePaint , width , height , scoreValue)
        for (i in 0 until len) {
            val des = scoreValue[i].toString()
            val last = getLast(des)
            val lastPoint = getLastPoint(i , point , des)
            canvas.drawText(last.toString() , lastPoint.x.toFloat() , lastPoint.y.toFloat() , mSocrePaint)
            canvas.drawText((last + 1).toString() , lastPoint.x.toFloat() , lastPoint.y.toFloat() + height , mSocrePaint)
        }
    }

    private fun getLastPoint(i : Int , point : Point , des : String) : Point {
        val oneProgress = 100 / des.toFloat()
        val curProgess = mProgress % oneProgress
        val y = point.y - height * (curProgess) / oneProgress
        val x = point.x + i * mOneWidth
        return Point(x.toInt() , y.toInt())
    }

    private fun getLast(des : String) : Int {
        return (mProgress / 100F * des.toInt()).toInt()
    }

    fun start(score : Int) {
        this.mScore = score
        val anim = ValueAnimator.ofInt(0 , 100)
        anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation : ValueAnimator?) {
                mProgress = animation?.getAnimatedValue().toString().toInt()
                postInvalidate()
            }
        })
        anim.duration = 2000L
        anim.start()
    }
}