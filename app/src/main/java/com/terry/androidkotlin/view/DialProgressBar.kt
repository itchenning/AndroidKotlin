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
 * Date: 2020-03-15 23:51
 * Comment:
 */
class DialProgressBar(context : Context , attrs : AttributeSet) : View(context , attrs) {

    private val mBgPaint = Paint()
    private val mProgressPaint = Paint()
    private val mTotalPaint = Paint()
    private val mAvailableTipPaint = Paint()
    private val mAvailableAmountPaint = Paint()
    private val mMarkPaint = Paint()
    private val START_ANGLE = 135F
    private val ROTATION_MAX = 270F
    private var mTotal = 21000F
    private var mCurAmount = 10500F
    private val PADDING = DipPixelUtils.dip2px(20F)

    init {
        mBgPaint.style = Paint.Style.STROKE
        mBgPaint.isAntiAlias = true
        mBgPaint.strokeWidth = DipPixelUtils.dip2px(6F).toFloat()
        mBgPaint.color = Color.parseColor("#E0E0E0")
        mBgPaint.strokeCap = Paint.Cap.ROUND

        mProgressPaint.style = Paint.Style.STROKE
        mProgressPaint.isAntiAlias = true
        mProgressPaint.strokeWidth = DipPixelUtils.dip2px(2F).toFloat()
        mProgressPaint.color = Color.parseColor("#18B4ED")

        mTotalPaint.style = Paint.Style.FILL
        mTotalPaint.isAntiAlias = true
        mTotalPaint.color = Color.parseColor("#808080")
        mTotalPaint.textSize = DipPixelUtils.dip2px(12F).toFloat()

        mAvailableTipPaint.style = Paint.Style.FILL
        mAvailableTipPaint.isAntiAlias = true
        mAvailableTipPaint.color = Color.parseColor("#18B4ED")
        mAvailableTipPaint.textSize = DipPixelUtils.dip2px(14F).toFloat()

        mAvailableAmountPaint.style = Paint.Style.FILL
        mAvailableAmountPaint.isAntiAlias = true
        mAvailableAmountPaint.color = Color.parseColor("#18B4ED")
        mAvailableAmountPaint.textSize = DipPixelUtils.dip2px(18F).toFloat()
        mAvailableAmountPaint.isFakeBoldText = true

        mMarkPaint.style = Paint.Style.FILL
        mMarkPaint.isAntiAlias = true
        mMarkPaint.color = Color.parseColor("#E0E0E0")
        mMarkPaint.textSize = DipPixelUtils.dip2px(12F).toFloat()
    }

    override fun onDraw(canvas : Canvas) {
        val rectf = RectF()
        rectf.left = mBgPaint.strokeWidth / 2 + PADDING
        rectf.top = mBgPaint.strokeWidth / 2 + PADDING
        rectf.right = width - mBgPaint.strokeWidth / 2 - PADDING
        rectf.bottom = height - mBgPaint.strokeWidth / 2 - PADDING
        drawBg(canvas , rectf)
        drawProgress(canvas , rectf)
        drawTotal(canvas)
        drawAvailable(canvas)
        drawMark(canvas)
    }

    private fun drawMark(canvas : Canvas) {
        val oneAngle = ROTATION_MAX / 4
        val r = DipPixelUtils.dip2px(1.5F)
        val rectf = RectF()
        rectf.left = width / 2F - r
        rectf.top = PADDING - 2F * r
        rectf.right = rectf.left + 2 * r
        rectf.bottom = rectf.top + 2 * r
        val centerAngle = START_ANGLE + ROTATION_MAX / 2
        val oneValue = mTotal / 4F
        for (i in 1 .. 3) {
            canvas.save()
            val text = (oneValue * i).toInt().toString()
            canvas.rotate(START_ANGLE + i * oneAngle - centerAngle , width / 2F , height / 2F)
            canvas.drawOval(rectf , mMarkPaint)
            val point = PaintUtils.getCenterHorizontalTextPoint(mMarkPaint , width , text)
            val bounds = Rect()
            mMarkPaint.getTextBounds(text , 0 , text.length , bounds)
            canvas.drawText(text , point.x.toFloat() , PADDING - DipPixelUtils.dip2px(5F).toFloat() , mMarkPaint)
            canvas.restore()
        }
    }

    private fun drawAvailable(canvas : Canvas) {
        var text = getFormatString(mCurAmount)
        var point = PaintUtils.getCenterTextPoint(mAvailableAmountPaint , width , height , text)
        canvas.drawText(text , point.x.toFloat() , point.y.toFloat() , mAvailableAmountPaint)
        val bounds = Rect()
        mAvailableAmountPaint.getTextBounds(text , 0 , text.length , bounds)
        val textHeight = bounds.height()
        val lastY = point.y

        text = "可借额度"
        point = PaintUtils.getCenterHorizontalTextPoint(mAvailableTipPaint , width , text)
        point.y = lastY - textHeight - DipPixelUtils.dip2px(10F)
        canvas.drawText(text , point.x.toFloat() , point.y.toFloat() , mAvailableTipPaint)
    }

    private fun drawTotal(canvas : Canvas) {
        var text = getFormatString(mTotal)
        var point = PaintUtils.getCenterHorizontalTextPoint(mTotalPaint , width , text)
        point.y = height - DipPixelUtils.dip2px(35F)
        canvas.drawText(text , point.x.toFloat() , point.y.toFloat() , mTotalPaint)

        val bounds = Rect()
        mTotalPaint.getTextBounds(text , 0 , text.length , bounds)
        val textHeight = bounds.height()
        text = "总额度"
        point = PaintUtils.getCenterHorizontalTextPoint(mTotalPaint , width , text)
        point.y = height - DipPixelUtils.dip2px(42F) - textHeight
        canvas.drawText(text , point.x.toFloat() , point.y.toFloat() , mTotalPaint)
    }

    private fun getFormatString(num : Float) : String {
        val value = num.toString()
        val index = value.indexOf(".")
        if (index < 0) {
            return "￥${num}.00"
        }
        if (index >= value.length - 2) {
            return "￥${num}0"
        }
        return "￥${value.substring(0 , index + 3)}"
    }

    private fun drawProgress(canvas : Canvas , rectf : RectF) {
        val progress = mCurAmount / mTotal
        val curRotation = progress * ROTATION_MAX
        canvas.drawArc(rectf , START_ANGLE , curRotation , false , mProgressPaint)
    }

    private fun drawBg(canvas : Canvas , rectf : RectF) {
        canvas.drawArc(rectf , START_ANGLE , ROTATION_MAX , false , mBgPaint)
    }

    fun borrow(value : Float) {
        val temp = mCurAmount
        val anim = ValueAnimator.ofFloat(0F , value)
        anim.setDuration(2000L)
        anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation : ValueAnimator?) {
                val value = animation?.getAnimatedValue().toString().toFloat()
                mCurAmount = temp - value
                mCurAmount = Math.max(0F , mCurAmount)
                postInvalidate()
            }
        })
        anim.start()
    }

    fun reset() {
        val anim = ValueAnimator.ofFloat(mCurAmount , mTotal)
        anim.setDuration(2000L)
        anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation : ValueAnimator?) {
                val value = animation?.getAnimatedValue().toString().toFloat()
                mCurAmount = value
                postInvalidate()
            }
        })
        anim.start()
    }
}