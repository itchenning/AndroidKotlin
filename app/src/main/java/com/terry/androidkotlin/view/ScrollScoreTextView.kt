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
 * Date: 2020-03-22 14:56
 * Comment:
 */
class ScrollScoreTextView(context : Context , attrs : AttributeSet) : View(context , attrs) {

    private val mBgColror = Color.RED
    private val mScorePaint = Paint()
    private val mUnitPaint = Paint()
    private var mScore = 0
    private var mProgess = 0
    private var mOneWidth = 0

    init {
        mScorePaint.style = Paint.Style.FILL
        mScorePaint.isAntiAlias = true
        mScorePaint.textSize = DipPixelUtils.dip2px(20F).toFloat()
        mScorePaint.color = Color.WHITE
        mScorePaint.isFakeBoldText = true
        mOneWidth = mScorePaint.measureText("0").toInt()

        mUnitPaint.style = Paint.Style.FILL
        mUnitPaint.isAntiAlias = true
        mUnitPaint.textSize = DipPixelUtils.dip2px(8F).toFloat()
        mUnitPaint.color = Color.WHITE
    }

    override fun onDraw(canvas : Canvas) {
        canvas.drawColor(mBgColror)
        drawScore(canvas)
        drawUnit(canvas)
    }

    private fun drawScore(canvas : Canvas) {
        val scoreValue = mScore.toString()
        val len = scoreValue.length
        for (i in 0 until len) {
            val des = scoreValue[i].toString()
            if (des.equals("0")) {
                continue
            }
            val cur = get_last(des)
            val point = PaintUtils.getCenterTextPoint(mScorePaint , width , height , scoreValue)
            val realPoint = getCurPoint(i , point , des)
            canvas.drawText(cur , realPoint.x.toFloat() , realPoint.y.toFloat() - height , mScorePaint)
            canvas.drawText((cur.toInt() + 1).toString() , realPoint.x.toFloat() , realPoint.y.toFloat() , mScorePaint)
        }
    }

    private fun getCurPoint(index : Int , point : Point , des : String) : Point {
        val oneProgress = 100 / des.toFloat()
        val curProgress = mProgess % oneProgress
        val y = point.y + height * (oneProgress - curProgress) / oneProgress
        val x = point.x + index * mOneWidth
        return Point(x , y.toInt())
    }

    private fun get_last(des : String) : String {
        return (mProgess / 100F * (des.toInt())).toInt().toString()
    }

    private fun drawUnit(canvas : Canvas) {
        val scoreValue = mScore.toString()
        val point = PaintUtils.getCenterTextPoint(mScorePaint , width , height , scoreValue)
        canvas.drawText("天" , point.x + mOneWidth * scoreValue.length.toFloat() , point.y.toFloat() , mUnitPaint)
    }

    fun start(score : Int) {
        this.mScore = score
        val anim = ValueAnimator.ofInt(0 , 100)
        anim.duration = 2000L
        anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation : ValueAnimator?) {
                mProgess = animation?.getAnimatedValue().toString().toInt()
                postInvalidate()
            }
        })
        anim.start()
    }
}