package common.utils.base

/**
 * Author: Terry
 * Date: 2019-08-08 10:49
 * Comment:
 */
object TimeUtils {

    // input: ms, return : xxh/xxh xxmin
    fun getFormatTime(time : Long) : String {
        if (time <= 0) {
            return "__"
        }
        if (time < 60 * 60 * 1000) {
            return "${time / (60 * 1000)}min"
        }
        if (time % (60 * 60 * 1000) == 0L) {
            return "${time / (60 * 60 * 1000)}h"
        }
        return "${time / (60 * 60 * 1000)}h ${(time % (60 * 60 * 1000)) / (60 * 1000)}min"
    }
}