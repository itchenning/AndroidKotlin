package common.utils.base

import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

/**
 * Author: Terry
 * Date: 2020-03-05 18:19
 * Comment:
 */
object PaintUtils {

    //获取垂直居中绘制的文本的绘制坐标
    fun getCenterVerticalTextPoint(paint : Paint , rootHeight : Int , text : String) : Point {
        val fontMetrics = paint.getFontMetrics()
        val y = (rootHeight / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom).toInt()
        val bounds = Rect()
        paint.getTextBounds(text , 0 , text.length , bounds)
        val x : Int
        if (paint.textAlign == Paint.Align.LEFT) {
            x = 0
        } else if (paint.textAlign == Paint.Align.RIGHT) {
            x = bounds.width()
        } else {
            x = bounds.width() / 2
        }
        return Point(x , y)
    }

    //获取水平居中绘制的文本的绘制坐标
    fun getCenterHorizontalTextPoint(paint : Paint , rootWidth : Int , text : String) : Point {
        val bounds = Rect()
        paint.getTextBounds(text , 0 , text.length , bounds)
        val x : Int
        if (paint.textAlign == Paint.Align.LEFT) {
            x = rootWidth / 2 - bounds.width() / 2
        } else if (paint.textAlign == Paint.Align.RIGHT) {
            x = bounds.width() + rootWidth / 2 - bounds.width() / 2
        } else {
            x = bounds.width() / 2 + rootWidth / 2 - bounds.width() / 2
        }
        val fontMetrics = paint.getFontMetrics()
        val y = ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom).toInt() * 2
        return Point(x , y)
    }

    //获取居中绘制的文本的绘制坐标
    fun getCenterTextPoint(paint : Paint , rootWidth : Int , rootHeight : Int , text : String) : Point {
        val bounds = Rect()
        paint.getTextBounds(text , 0 , text.length , bounds)
        val x : Int
        if (paint.textAlign == Paint.Align.LEFT) {
            x = rootWidth / 2 - bounds.width() / 2
        } else if (paint.textAlign == Paint.Align.RIGHT) {
            x = bounds.width() + rootWidth / 2 - bounds.width() / 2
        } else {
            x = bounds.width() / 2 + rootWidth / 2 - bounds.width() / 2
        }
        val fontMetrics = paint.getFontMetrics()
        val y = (rootHeight / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom).toInt()
        return Point(x , y)
    }

    //获取文字的baseline的y坐标
    fun getTextBaselineY(paint : Paint) : Int {
        val fontMetrics = paint.getFontMetrics()
        return ((fontMetrics.bottom - fontMetrics.top) / 2F - fontMetrics.bottom).toInt()
    }
}