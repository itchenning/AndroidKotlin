package common.utils.base

/**
 * Author: Terry
 * Date: 2019-07-29 15:21
 * Comment:
 */
object ArrayUtils {

    fun toString(array: Array<String>): String {
        val builder = StringBuilder()
        for (a in array) {
            builder.append(a)
            builder.append(",")
        }
        return builder.toString()
    }

    fun toString(array: Array<Int>): String {
        val builder = StringBuilder()
        for (a in array) {
            builder.append(a)
            builder.append(",")
        }
        return builder.toString()
    }
}