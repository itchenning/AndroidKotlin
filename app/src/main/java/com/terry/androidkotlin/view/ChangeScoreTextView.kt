package com.terry.androidkotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import com.terry.androidkotlin.utils.PaintUtils
import common.utils.base.DipPixelUtils

/**
 * Author: Terry
 * bilibili: 码农安小辰
 * Github: https://github.com/itchenning/AndroidKotlin
 * Date: 2020-03-08 18:32
 * Comment:
 */
class ChangeScoreTextView(context : Context , attrs : AttributeSet) : View(context , attrs) {

    private var mBgColor = Color.RED
    private val mScorePaint : Paint
    private val mUnitPaint : Paint
    private val mHeight : Int
    private var mScore = 1278
    private var mProgress = 0
    private var mOneTextWidth = 0F
    private val DELAY = 3000L

    init {
        mHeight = DipPixelUtils.dip2px(50F)

        mScorePaint = Paint()
        mScorePaint.style = Paint.Style.FILL
        mScorePaint.color = Color.WHITE
        mScorePaint.textSize = DipPixelUtils.dip2px(25F).toFloat()
        mOneTextWidth = mScorePaint.measureText("0")

        mUnitPaint = Paint()
        mUnitPaint.style = Paint.Style.FILL
        mUnitPaint.color = Color.WHITE
        mUnitPaint.textSize = DipPixelUtils.dip2px(25F).toFloat()
    }

    override fun onDraw(canvas : Canvas) {
        canvas.drawColor(mBgColor)
        drawText(canvas)
        drawUnit(canvas)
    }

    private fun drawUnit(canvas : Canvas) {


    }

    private fun drawText(canvas : Canvas) {
        val text = mScore.toString()
        val len = text.length
        val point = PaintUtils.getCenterVerticalTextPoint(mScorePaint , mHeight , text)
        val wholeTextWidth = mScorePaint.measureText(mScore.toString())
        for (i in 0 until len) {
            val des = text[i].toString()
            val cur = getCurString(des)
            val x = point.x.toFloat() + i * mOneTextWidth + width / 2 - wholeTextWidth / 2
            val y = getCurY(des , point)
            if ("0".equals(des)) {
                canvas.drawText(des , x , point.y.toFloat() , mScorePaint)
                continue
            }
            canvas.drawText(cur , x , y - mHeight , mScorePaint)
            canvas.drawText((cur.toInt() + 1).toString() , x , y , mScorePaint)
        }
    }

    private fun getCurString(des : String) : String {
        val max = des.toInt()
        return (mProgress / 100F * max).toInt().toString()
    }

    private fun getCurY(des : String , point : Point) : Float {
        val between = des.toInt()
        val oneMaxProgress = 100 / between.toFloat()
        val curProgess = oneMaxProgress - mProgress % oneMaxProgress
        return point.y + mHeight * curProgess / oneMaxProgress
    }

    fun start(score : Int) {
        this.mScore = score
        val anim = ValueAnimator.ofInt(0 , 100)
        anim.setDuration(2000L)
        anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation : ValueAnimator?) {
                mProgress = animation?.getAnimatedValue().toString().toInt()
                postInvalidate()
            }
        })
        anim.start()
    }
}