package common.utils.base

import android.graphics.Point

/**
 * Author: Terry
 * Date: 2019-09-03 15:06
 * Comment:
 */
object PositionUtils {
    //获取两点的距离。
    fun getBetween(start : Point , end : Point) : Float {
        return Math.sqrt(Math.pow((end.x - start.x).toDouble() , 2.0) + Math.pow((start.y - end.y).toDouble() , 2.0)).toFloat()
    }

    // 获取两点的夹角，角度从第一象限开始，顺时针，0-359
    fun getAngle(start : Point , end : Point) : Int {
        val radian = Math.atan2((end.y - start.y).toDouble() , (end.x - start.x).toDouble())
        val angle = (radian * (180.0 / Math.PI)).toInt() - 90
        return if (angle >= 0) angle else angle + 360
    }
}