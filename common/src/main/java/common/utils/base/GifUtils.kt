package common.utils.base

import android.graphics.Movie
import common.base.VLog
import java.io.File
import java.io.FileInputStream

/**
 * Author: Terry
 * Date: 2020-02-11 15:32
 * Comment:
 */
object GifUtils {
    private val TAG = "GifUtils"

    fun getDuration(path : String) : Long {
        try {
            val inputStream = FileInputStream(path)
            val bytes = inputStream.readBytes()
            val movie = Movie.decodeByteArray(bytes , 0 , bytes.size)
            return movie.duration().toLong()
        } catch (e : Exception) {
            e.printStackTrace()
            VLog.i(TAG , e.message.toString())
        }
        return 0
    }

    fun getDuration(file : File) : Long {
        try {
            val inputStream = FileInputStream(file)
            val bytes = inputStream.readBytes()
            val movie = Movie.decodeByteArray(bytes , 0 , bytes.size)
            return movie.duration().toLong()
        } catch (e : Exception) {
            e.printStackTrace()
            VLog.i(TAG , e.message.toString())
        }
        return 0
    }
}