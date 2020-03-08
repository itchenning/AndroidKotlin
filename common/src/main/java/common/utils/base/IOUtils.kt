package common.utils.base

import java.io.InputStream
import java.io.OutputStream

/**
 * Author: Terry
 * Date: 2020-02-21 11:19
 * Comment:
 */
object IOUtils {

    fun close(os : OutputStream?) {
        try {
            os?.close()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun close(inputStream : InputStream?) {
        try {
            inputStream?.close()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}