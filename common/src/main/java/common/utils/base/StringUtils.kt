package common.utils.base

/**
 * Author: Terry
 * Date: 2019-06-26 10:18
 * Comment:
 */
object StringUtils {

    //格式化为N位的字符串。
    fun formatNum(num : Int , len : Int) : String {
        val value = num.toString()
        val off = len - value.length
        val sb = StringBuilder()
        for (i in 0 until off) {
            sb.append("0")
        }
        return sb.toString() + value
    }
}
