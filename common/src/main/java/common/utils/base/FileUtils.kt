package common.utils.base

import android.content.Context
import java.io.*

/**
 * Author: Terry
 * Date: 2017/10/17 14:41
 * Description: 文件操作工具类。
 */
object FileUtils {

    @Throws(IOException::class)
    fun getFileLength(path : String) : Long {
        var size : Long = 0
        val f = File(path)
        if (f.exists()) {
            var fis : FileInputStream? = null
            fis = FileInputStream(f)
            size = fis.available().toLong()
        }
        return size
    }

    /**
     * 把文件转换成byte[]
     * @return
     */
    @Throws(IOException::class)
    fun getAssetsFileBytes(context : Context , fileName : String) : ByteArray {
        val `is` = context.assets.open(fileName)
        val bytes = ByteArray(`is`.available())
        `is`.read(bytes)
        return bytes
    }

    @Throws(IOException::class)
    fun copyAssets(context : Context , assetsName : String , des : String) {
        fixFile(des)
        val inStream = context.assets.open(assetsName)
        val fs = FileOutputStream(des)
        val buffer = ByteArray(4096)
        var len = inStream.read(buffer)
        while (len != - 1) {
            fs.write(buffer , 0 , len)
            len = inStream.read(buffer)
        }
        fs.flush()
        inStream.close()
        fs.close()
    }

    fun getAssetsFile(context : Context , path : String) : File {
        var fos : FileOutputStream? = null
        val f = File(context.cacheDir.toString() + File.separator + System.currentTimeMillis() + ".test")
        try {
            val input = context.assets.open(path)
            val data = ByteArray(2048)
            fos = FileOutputStream(f)
            var nbread = input.read(data);
            while (nbread > - 1) {
                fos.write(data , 0 , nbread)
                nbread = input.read(data)
            }
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e : IOException) {
                    e.printStackTrace()
                }

            }
        }
        return f
    }

    /**
     * 复制文件，目标文件存在则覆盖
     * @param oldPath
     * @param newPath
     * @return
     */
    fun renameTo(oldPath : String , newPath : String) : Boolean {
        val oldFile = File(oldPath)
        if (! oldFile.exists()) {
            return false
        }
        val newFile = File(newPath)
        if (newFile.exists()) {
            newFile.delete()
        }
        return oldFile.renameTo(newFile)
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径
     * @param newPath String 复制后路径
     * @return boolean
     */
    @Throws(IOException::class)
    fun copyFile(oldPath : String , newPath : String) {
        val oldfile = File(oldPath)
        if (! oldfile.exists()) {
            return
        }
        val newFile = File(newPath)
        val parent = newFile.parentFile
        if (! parent.exists()) {
            parent.mkdirs()
        }
        if (! newFile.exists()) {
            newFile.createNewFile()
        }
        val inStream = FileInputStream(oldPath)
        val fs = FileOutputStream(newPath)
        val buffer = ByteArray(1024 * 256)
        var byteread = inStream.read(buffer)
        while (byteread != - 1) {
            fs.write(buffer , 0 , byteread)
            byteread = inStream.read(buffer)
        }
        fs.flush()
        inStream.close()
        fs.close()
    }

    /**
     * 把一个byte数组写入到一个文件中。
     * @param path
     * @param bytes
     * @throws Exception
     */
    @Throws(Exception::class)
    fun writeFile(path : String , bytes : ByteArray) {
        val fs = FileOutputStream(path)
        fs.write(bytes)
        fs.close()
    }

    /**
     * 复制文件。
     * @param srcPath
     * @param desPath
     * @param bytes
     * @return
     */
    @Throws(IOException::class)
    private fun copy(srcPath : String , desPath : String , bytes : ByteArray) : Int {
        var input : InputStream? = null
        var out : OutputStream? = null
        try {
            input = FileInputStream(srcPath)
            out = FileOutputStream(desPath)
            var c : Int = input.read(bytes);
            while (c > 0) {
                out.write(bytes , 0 , c)
                c = input.read(bytes)
            }
        } catch (e : FileNotFoundException) {
            e.printStackTrace()
        } catch (e : IOException) {
            e.printStackTrace()
            throw IOException(e.message)
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e : IOException) {
                    e.printStackTrace()
                }

            }
            if (out != null) {
                try {
                    out.flush()
                    out.close()
                } catch (e : IOException) {
                    e.printStackTrace()
                }

            }
        }
        return 0
    }

    fun fixDir(path : String) {
        val file = File(path)
        fixDir(file)
    }

    fun fixDir(file : File) {
        if (! file.exists()) {
            file.mkdirs()
        }
    }


    fun fixFile(path : String) {
        val file = File(path)
        fixFile(file)
    }

    fun fixFile(file : File) {
        if (! file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
    }

    /**
     * 把文件读成字符串。
     * @param path
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getFileContent(path : String) : String {
        return getFileContent(File(path))
    }

    /**
     * 把文件读成字符串。
     * @param file
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getFileContent(file : File) : String {
        var content = ""
        if (! file.isDirectory) {
            val instream = FileInputStream(file)
            val inputreader = InputStreamReader(instream , "UTF-8")
            val buffreader = BufferedReader(inputreader)
            var line = buffreader.readLine()
            while (line != null) {
                content += line + "\n"
                line = buffreader.readLine()
            }
            instream.close()
        }
        return content
    }

    /**
     * 获得指定文件的byte数组
     */
    fun getBytes(file : File) : ByteArray? {
        var buffer : ByteArray? = null
        try {
            val fis = FileInputStream(file)
            val bos = ByteArrayOutputStream(1024 * 128)
            val b = ByteArray(1024 * 128)
            var n : Int = fis.read(b)
            while (n != - 1) {
                bos.write(b , 0 , n)
                n = fis.read(b)
            }
            fis.close()
            bos.close()
            buffer = bos.toByteArray()
        } catch (e : FileNotFoundException) {
            e.printStackTrace()
        } catch (e : IOException) {
            e.printStackTrace()
        }

        return buffer
    }

    /**
     * 创建一个标识文件，系统不去扫描当前目录的媒体文件。
     * @param path
     */
    fun createNomedia(path : String) {
        try {
            val file = File(path , ".nomedia")
            val parent = file.parentFile
            if (! parent.exists()) {
                parent.mkdirs()
            }
            if (! file.exists()) {
                file.createNewFile()
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 复制SD卡中的文件。
     * @param srcFile
     * @param desFile
     */
    @Throws(IOException::class)
    fun copyFromSD(srcFile : String , desFile : String) {
        var input : InputStream? = null
        var output : OutputStream? = null
        try {
            output = FileOutputStream(desFile)
            input = FileInputStream(File(srcFile))
            val buffer = ByteArray(1024 * 1024)
            var length = input.read(buffer)
            while (length > 0) {
                output.write(buffer , 0 , length)
                length = input.read(buffer)
            }
            output.flush()
        } catch (e : IOException) {
            e.printStackTrace()
            throw IOException(e.message)
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e : IOException) {
                    e.printStackTrace()
                }

            }
            if (input != null) {
                try {
                    output !!.close()
                } catch (e : IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 获取文件列表。
     * @param dir
     * @param files
     * @return
     */
    fun getFiles(dir : String , files : MutableList<File>?) : List<File> {
        var files = files
        if (files == null) {
            files = ArrayList()
        }
        val root = File(dir)
        val array = root.listFiles()
        if (array != null && array.size > 0) {
            for (file in array) {
                if (file.isFile) {
                    files.add(file)
                } else {
                    getFiles(file.absolutePath , files)
                }
            }
        }
        return files
    }

    fun deleteFiles(vararg files : File) {
        for (f in files) {
            deleteDir(f)
        }
    }

    fun deleteFiles(vararg paths : String) {
        for (path in paths) {
            deleteDir(File(path))
        }
    }

    fun deleteFiles(files : List<File>) {
        for (file in files) {
            deleteDir(file)
        }
    }

    private fun deleteDir(dir : File) : Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            //递归删除目录中的子目录下
            for (i in children.indices) {
                val success = deleteDir(File(dir , children[i]))
                if (! success) {
                    return false
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete()
    }

    fun listDir(dir : String , list : ArrayList<String>) {
        val path = File(dir)
        if (path.isFile) {
            list.add(path.absolutePath)
            return
        }
        val fileList = path.listFiles()
        for (file in fileList) {
            if (file.isFile) {
                list.add(path.absolutePath)
            } else {
                listDir(file.absolutePath , list)
            }
        }
    }
}
