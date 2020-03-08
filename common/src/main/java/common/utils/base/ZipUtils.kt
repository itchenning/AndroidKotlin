package common.utils.base

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Author: Terry
 * Date: 2018/a/26 15:04
 * Description:
 */
object ZipUtils {

    private val TAG = "ZipUtils"
    private val BUFF_SIZE = 4096

    /**
     * 解压缩一个文件
     * @param src 压缩文件
     * @param dir 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    @Throws(IOException::class)
    fun unZipFile(src: String, dir: String) {
        FileUtils.fixDir(dir)
        // 先指定压缩档的位置和档名，建立FileInputStream对象
        val fins = FileInputStream(src)
        // 将fins传入ZipInputStream中
        val zins = ZipInputStream(fins)
        var ze: ZipEntry? = zins.nextEntry
        val ch = ByteArray(BUFF_SIZE)
        while (ze != null) {
            val zfile = File(dir + "/" + ze!!.name)
            val fpath = File(zfile.parentFile.path)
            if (ze.isDirectory) {
                if (!zfile.exists())
                    zfile.mkdirs()
                zins.closeEntry()
            } else {
                if (!fpath.exists())
                    fpath.mkdirs()
                val fouts = FileOutputStream(zfile)
                var i: Int = zins.read(ch)
                while (i != -1) {
                    fouts.write(ch, 0, i)
                    i = zins.read(ch)
                }
                zins.closeEntry()
                fouts.close()
            }
            ze = zins.nextEntry
        }
        fins.close()
        zins.close()
    }

    /**
     * 把一个文件夹打包成一个zip.
     * 压缩文件,不会保留层级结构，全部回到第一层。
     * @param src
     * @param des
     * @throws IOException
     */
    @Throws(Exception::class)
    fun zipDir(src: String, des: String) {
        FileUtils.fixFile(des)
        val outZip = ZipOutputStream(FileOutputStream(des))
        //创建文件
        val file = File(src)
        zipFiles(file.parent + File.separator, file.name, outZip)
        //完成和关闭
        outZip.finish()
        outZip.close()
    }

    @Throws(Exception::class)
    private fun zipFiles(src: String, srcName: String, out: ZipOutputStream?) {
        if (out == null)
            return
        val file = File(src + srcName)
        if (file.isFile) {
            val zipEntry = ZipEntry(srcName)
            val inputStream = FileInputStream(file)
            out.putNextEntry(zipEntry)
            val buffer = ByteArray(BUFF_SIZE)
            var len: Int = inputStream.read(buffer)
            while (len != -1) {
                out.write(buffer, 0, len)
                len = inputStream.read(buffer)
            }
            out.closeEntry()
        } else {
            //文件夹
            val fileList = file.list()
            //没有子文件和压缩
            if (fileList.size <= 0) {
                val zipEntry = ZipEntry(srcName + File.separator)
                out.putNextEntry(zipEntry)
                out.closeEntry()
            }
            //子文件和递归
            for (i in fileList.indices) {
                zipFiles("$src$srcName/", fileList[i], out)
            }
        }
    }
}
