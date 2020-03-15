package common.utils.base

import android.content.Context
import android.graphics.*
import java.io.File
import java.io.FileOutputStream

/**
 * Author: Terry
 * Date: 2019-08-02 11:01
 * Comment:
 */
object BitmapUtils {

    fun scaleImage(org : Bitmap , desWidth : Int , desHeight : Int) : Bitmap {
        return scaleImage(org , desWidth / org.width.toFloat() , desHeight / org.height.toFloat())
    }

    fun scaleImage(context : Context , res : Int , desWidth : Int , desHeight : Int) : Bitmap {
        val org = BitmapFactory.decodeResource(context.resources , res)
        return scaleImage(org , desWidth / org.width.toFloat() , desHeight / org.height.toFloat())
    }

    private fun scaleImage(org : Bitmap , scaleWitdh : Float , scaleHeight : Float) : Bitmap {
        val matrix = Matrix()
        matrix.postScale(scaleWitdh , scaleHeight)
        return Bitmap.createBitmap(org , 0 , 0 , org.width * scaleWitdh.toInt() , org.height * scaleHeight.toInt() , matrix , false)
    }

    fun bitmap2File(bitmap : Bitmap , desFile : File , quality : Int) : File {
        var out : FileOutputStream? = null
        try {
            val parent = desFile.parentFile
            if (! parent.exists()) {
                parent.mkdirs()
            }
            if (desFile.exists()) {
                desFile.delete()
            }
            desFile.createNewFile()
            out = FileOutputStream(desFile)
            bitmap.compress(Bitmap.CompressFormat.PNG , quality , out)
            out.flush()
            out.close()
        } catch (e : Exception) {
            e.printStackTrace()
        } finally {
            IOUtils.close(out)
        }
        return desFile
    }

    // Bitmap变色
    fun changeColor(srcBitmap : Bitmap , color : Int) : Bitmap {
        val outBitmap = Bitmap.createBitmap(srcBitmap.width , srcBitmap.height , srcBitmap.config)
        val canvas = Canvas(outBitmap)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(color , PorterDuff.Mode.SRC_IN)
        val rect = Rect(0 , 0 , srcBitmap.width , srcBitmap.height)
        canvas.drawBitmap(srcBitmap , rect , rect , paint)
        return outBitmap
    }
}