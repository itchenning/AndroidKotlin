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
    private val mScorePaint : Paint = Paint()
    private val mUnitPaint : Paint = Paint()
    private var mScore = 1278
    private var mProgress = 0
    private var mOneTextWidth = 0F
    private val DELAY = 2000L

    init {
        mScorePaint.style = Paint.Style.FILL
        mScorePaint.color = Color.WHITE
        mScorePaint.textSize = DipPixelUtils.dip2px(25F).toFloat()
        mOneTextWidth = mScorePaint.measureText("0")

        mUnitPaint.style = Paint.Style.FILL
        mUnitPaint.color = Color.WHITE
        mUnitPaint.textSize = DipPixelUtils.dip2px(12F).toFloat()
    }

    override fun onDraw(canvas : Canvas) {
        canvas.drawColor(mBgColor)
        drawScore(canvas)
        drawUnit(canvas)
    }

    private fun drawUnit(canvas : Canvas) {
        val text = mScore.toString()
        val wholeTextWidth = mScorePaint.measureText(text)
        val uinitPoint = PaintUtils.getCenterVerticalTextPoint(mUnitPaint , height , "天")
        val scoreBaseY = (height / 2 - mScorePaint.fontMetrics.top / 2 - mScorePaint.fontMetrics.bottom / 2)
        canvas.drawText("天" , uinitPoint.x + width / 2F + wholeTextWidth / 2 , scoreBaseY , mUnitPaint)
    }

    private fun drawScore(canvas : Canvas) {
        val text = mScore.toString()
        val len = text.length
        val point = PaintUtils.getCenterVerticalTextPoint(mScorePaint , height , text)
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
            canvas.drawText(cur , x , y - height , mScorePaint)
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
        return point.y + height * curProgess / oneMaxProgress
    }

    fun start(score : Int) {
        this.mScore = score
        val anim = ValueAnimator.ofInt(0 , 100)
        anim.setDuration(DELAY)
        anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation : ValueAnimator?) {
                mProgress = animation?.getAnimatedValue().toString().toInt()
                postInvalidate()
            }
        })
        anim.start()
    }
}