package com.terry.androidkotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
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
    private val mScorePaint = Paint()
    private val mUnitPaint = Paint()
    private var mScore = 8888
    private var mProgress = 100
    private var mOneNumWidth = 0F

    init {
        mScorePaint.style = Paint.Style.FILL
        mScorePaint.isAntiAlias = true
        mScorePaint.textSize = DipPixelUtils.dip2px(30F).toFloat()
        mScorePaint.isFakeBoldText = true
        mScorePaint.color = Color.WHITE
        mOneNumWidth = mScorePaint.measureText("0")

        mUnitPaint.style = Paint.Style.FILL
        mUnitPaint.isAntiAlias = true
        mUnitPaint.textSize = DipPixelUtils.dip2px(14F).toFloat()
        mUnitPaint.color = Color.WHITE
    }

    override fun onDraw(canvas : Canvas) {
        canvas.drawColor(mBgColor)
        drawScore(canvas)
        drawUnit(canvas)
    }

    private fun drawUnit(canvas : Canvas) {
        val scoreValue = mScore.toString()
        val point = PaintUtils.getCenterTextPoint(mScorePaint , width , height , scoreValue)
        canvas.drawText("天" , point.x + mOneNumWidth * scoreValue.length , point.y.toFloat() , mUnitPaint)
    }

    private fun drawScore(canvas : Canvas) {
        val scoreValue = mScore.toString()
        val point = PaintUtils.getCenterTextPoint(mScorePaint , width , height , scoreValue)
        val len = scoreValue.length
        for (i in 0 until len) {
            val des = scoreValue[i].toString()
            if (des.equals("0")) {
                continue
            }
            val last = getLast(des)
            val lastPoint = getLastPoint(i , point , des)
            canvas.drawText(last.toString() , lastPoint.x , lastPoint.y , mScorePaint)
            canvas.drawText((last + 1).toString() , lastPoint.x , lastPoint.y + height , mScorePaint)
        }
    }

    private fun getLastPoint(i : Int , point : Point , des : String) : PointF {
        val oneMaxProgress = 100 / des.toFloat()
        val curProgress = mProgress % oneMaxProgress
        val y = point.y - height * curProgress / oneMaxProgress
        val x = point.x + i * mOneNumWidth
        return PointF(x , y)
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