//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package common.base

import android.content.ContentValues
import android.util.Log


object VLog {
    private val OPEN_LOG = BuildConfig.DEBUG_ABLE
    private const val LOG_I = 0;
    private const val LOG_V = 1;
    private const val LOG_D = 2;
    private const val LOG_W = 3;
    private const val LOG_E = 4;

    fun i(tag : String , msg : String) {
        if (OPEN_LOG) {
            show(LOG_I , tag , msg)
        }
    }

    fun i(tag : String , values : ContentValues) {
        if (OPEN_LOG) {
            val sb = StringBuilder()
            for (key in values.keySet()) {
                sb.append(key + ": " + values.get(key) + ",")
            }
            val content = sb.toString()
            show(LOG_I , tag , content.substring(0 , content.length - 1) + ".")
        }
    }

    fun d(tag : String , msg : String) {
        if (OPEN_LOG) {
            show(LOG_D , tag , msg)
        }
    }

    fun w(tag : String , msg : String) {
        if (OPEN_LOG) {
            show(LOG_W , tag , msg)
        }
    }

    fun w(tag : String , e : Exception) {
        if (OPEN_LOG) {
            show(LOG_W , tag , e.message.toString())
        }
    }

    fun e(tag : String , msg : String) {
        if (OPEN_LOG) {
            show(LOG_E , tag , msg)
        }
    }

    fun e(msg : String) {
        if (OPEN_LOG) {
            show(LOG_E , "error" , msg)
        }
    }

    fun v(tag : String , msg : String) {
        if (OPEN_LOG) {
            show(LOG_V , tag , msg)
        }
    }

    fun debugException(e : Exception) {
        if (OPEN_LOG) {
            e.printStackTrace()
        }
    }

    fun printException(e : Exception) {
        e.printStackTrace()
    }

    private fun show(type : Int , tag : String , msg : String) {
        var content : String? = msg
        if (tag.length == 0 || content == null || content.length == 0) return
        content = content.trim { it <= ' ' }
        var index = 0
        val segmentSize = 3 * 1024
        var logContent : String
        while (index < content.length) {
            logContent = if (content.length <= index + segmentSize) {
                content.substring(index)
            } else {
                content.substring(index , segmentSize + index)
            }
            index += segmentSize
            when (type) {
                LOG_I -> Log.i(tag , logContent.trim { it <= ' ' })
                LOG_V -> Log.v(tag , content)
                LOG_D -> Log.d(tag , logContent.trim { it <= ' ' })
                LOG_W -> Log.w(tag , content)
                LOG_E -> Log.w(tag , content)
                else -> {
                }
            }
        }
    }
}
