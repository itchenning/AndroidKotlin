package common.utils.base

import android.util.Base64

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import kotlin.experimental.xor

/**
 * 使用AES对文件进行加密和解密
 */
object EncodeDecodeUtils {

    @Throws(IOException::class)
    fun encrypt(src: String, des: String, key: String) {
        val fis = FileInputStream(src)
        val fos = FileOutputStream(mkdirFiles(des))
        val buffer = ByteArray(1024)
        var content: ByteArray
        var len: Int = fis.read(buffer)
        while (len != -1) {
            content = crypt(buffer, len, key)
            fos.write(content)
            len = fis.read(buffer)
        }
        fis.close()
        fos.flush()
        fos.close()
    }

    @Throws(IOException::class)
    fun decrypt(src: String, des: String, key: String) {
        val fis = FileInputStream(src)
        val fos = FileOutputStream(mkdirFiles(des))
        val buffer = ByteArray(1024)
        var content: ByteArray
        var len: Int = fis.read(buffer);
        while (len != -1) {
            content = crypt(buffer, len, key)
            fos.write(content)
            len = fis.read(buffer);
        }
        fis.close()
        fos.flush()
        fos.close()
    }

    /**
     * 加密字符串
     * @param buffer 需要被加密的字符串
     * @param key    加密需要的密码
     * @return 密文
     */
    private fun crypt(buffer: ByteArray, len: Int, key: String): ByteArray {
        val result = ByteArray(len)
        val pwdBytes = key.toByteArray()
        for (i in 0 until len) {
            if (i > pwdBytes.size - 1) {
                result[i] = (buffer[i] xor pwdBytes[0]).toByte()
            } else {
                result[i] = (buffer[i] xor pwdBytes[i]).toByte()
            }
        }
        return result
    }

    /**
     * 获取key.
     * @param key
     * @return
     */
    fun getPrivacyKey(key: String): String {
        val result = Base64.encodeToString(key.toByteArray(), 0)
        val bytes = result.toByteArray()
        var index = 0
        var k: Byte
        for (b in bytes) {
            k = b xor (Math.pow(key.length.toDouble(), (3 shl 2).toDouble()).toInt().toByte())
            k = (k * 3).toByte()
            bytes[index] = k
            index++
        }
        return String(bytes)
    }

    @Throws(IOException::class)
    private fun mkdirFiles(filePath: String): File {
        val file = File(filePath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        file.createNewFile()
        return file
    }
}
