package com.android.webrng.utils

import android.os.Environment
import com.android.webrng.constants.ResultPath
import com.android.webrng.constants.SmilePath
import com.android.webrng.constants.getCsvSeparator
import java.io.*

class Photo(this_name: String, this_datetime: Long) {
    var name: String = this_name
    var datetime: Long = this_datetime
}
class FileLogic {
    companion object {
        fun fileList(): Array<out File>? {
            val path = Environment.getExternalStorageDirectory().toString() + SmilePath
            //Log.e("Files", "Path: $path")
            val directory = File(path)
            val output = directory.listFiles()
            //Log.e("Files", "Size: " + files.size)
            //for (i in files.indices) {
            //    Log.d("Files", "FileName:" + files[i].name)
            //}
            return output
        }
        fun writeToFile(data: String, context: File): Boolean {
            var outputStream: FileOutputStream? = null
            var outputResult: Boolean = true
            try {
                outputStream = FileOutputStream(context, true)
                outputStream.write("$data\n".toByteArray())
            }
            catch (e: IOException) {
                outputResult = false
            }

            if (outputStream != null) {
                try {
                    outputStream.close()
                }
                catch (e: IOException) {
                    outputResult = false
                }
            }
            return outputResult
        }
        fun copy(src: InputStream, dst: OutputStream): Int {
            try {
                src.use { `in` ->
                    dst.use { out ->
                        val buf = ByteArray(1024)
                        var len: Int
                        while (`in`.read(buf).also { len = it } > 0) {
                            out.write(buf, 0, len)
                        }
                    }
                }
            }
            catch (e1: Exception) {
                try {
                    src.close()
                    dst.close()
                }
                catch (e2: Exception) { }
                return -4
            }
            try {
                src.close()
                dst.close()
            }
            catch (e: Exception) {
                return -4
            }
            return 1
        }
        private fun moveFile(inputFile: String, inputPath: String, outputPath: String) {
            var `in`: InputStream? = null
            var out: OutputStream? = null
            try {

                //create output directory if it doesn't exist
                val dir = File(outputPath)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                `in` = FileInputStream(inputPath + inputFile)
                out = FileOutputStream(outputPath + inputFile)
                val buffer = ByteArray(1024)
                var read: Int
                while (`in`.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
                `in`.close()
                `in` = null

                // write the output file
                out.flush()
                out.close()
                out = null

                // delete the original file
                File(inputPath + inputFile).delete()
            } catch (fnfe1: FileNotFoundException) {
                //Log.e("tag", fnfe1.message)
            } catch (e: java.lang.Exception) {
                //Log.e("tag", e.message)
            }
        }
        fun moveAll() {
            val fileList = FileLogic.fileList()
            val srcPath = Environment.getExternalStorageDirectory().toString() + SmilePath + "/"
            val dstPath = Environment.getExternalStorageDirectory().toString() + ResultPath + "/"
            if (fileList != null) {
                fileList.forEach { moveFile(it.name, srcPath, dstPath) }
            }
        }
        fun rename(path: String, value: String) {
            var timestamp = getCsvSeparator().replace(" ", "_").replace("-", "_").replace(":", "_").replace(".", "_")
            try {
                if (timestamp.length > 0) {
                    val src = File(Environment.getExternalStorageDirectory().toString() + SmilePath + "/" + path)
                    val dst = File(Environment.getExternalStorageDirectory().toString() + SmilePath + "/_" + timestamp + "___" + value + ".jpg")
                    if (src.exists()) {
                        src.renameTo(dst)
                    }
                }
            }
            catch (e: Exception) { }
        }
        fun encrypt(src: InputStream, dst: OutputStream, inputPassword: String): Int {
            var inputByte: ByteArray? = null
            try {
                val inputBuffer = ByteArrayOutputStream()
                var nRead: Int
                val data = ByteArray(16384)
                while (src.read(data, 0, data.size).also { nRead = it } != -1) {
                    inputBuffer.write(data, 0, nRead)
                }
                inputByte = inputBuffer.toByteArray()
                inputBuffer.close()
            }
            catch (e1: Exception) {
                try {
                    src.close()
                    dst.close()
                }
                catch (e2: Exception) { }
                return -1
            }
            try {
                src.close()
            }
            catch (e: Exception) { }

            var aesByte: ByteArray? = null
            var ivStr: ByteArray? = null
            try {
                ivStr = AES256.decodeHexString(AES256.getIV())
                val keyStr: ByteArray = AES256.getRawKey(inputPassword)

                aesByte = AES256.encryptRaw(inputByte, keyStr, ivStr)
                if (aesByte == null)
                    throw Exception("AES")
            }
            catch (e1: Exception) {
                try {
                    dst.close()
                }
                catch (e2: Exception) { }
                return -2
            }

            try {
                var outputBuffer = BufferedOutputStream(dst)
                outputBuffer.write(ivStr)
                outputBuffer.write(aesByte)
                outputBuffer.flush()
                outputBuffer.close()
            }
            catch (e1: Exception) {
                try {
                    dst.close()
                }
                catch (e2: Exception) { }
                return -3
            }
            try {
                dst.close()
            }
            catch (e: Exception) {
                return -3
            }
            return 1
        }
        fun decrypt(src: InputStream, dst: OutputStream, inputPassword: String): Int {
            var inputByte: ByteArray? = null
            var ivStr = ByteArray(16)
            try {
                val inputBuffer = ByteArrayOutputStream()
                var nRead: Int
                val data = ByteArray(16384)
                src.read(ivStr, 0, 16)
                while (src.read(data, 0, data.size).also { nRead = it } != -1) {
                    inputBuffer.write(data, 0, nRead)
                }
                inputByte = inputBuffer.toByteArray()
                inputBuffer.close()
            }
            catch (e1: Exception) {
                try {
                    src.close()
                    dst.close()
                }
                catch (e2: Exception) { }
            //    Log.i("EXCEPTION2", "${e.toString()}/${e.message.toString()}")
                return -1
            }
            try {
                src.close()
            }
            catch (e: Exception) { }

            var aesByte: ByteArray? = null
            try {
                val keyStr: ByteArray = AES256.getRawKey(inputPassword)
                aesByte = AES256.decryptRaw(inputByte, keyStr, ivStr)
                if (aesByte == null)
                    throw Exception("AES")
            }
            catch (e1: Exception) {
                try {
                    dst.close()
                }
                catch (e2: Exception) { }
            //    Log.i("EXCEPTION3", "${e.toString()}/${e.message.toString()}")
                return -2
            }

            try {
                var outputBuffer = BufferedOutputStream(dst)
                outputBuffer.write(aesByte)
                outputBuffer.flush()
                outputBuffer.close()
            }
            catch (e1: Exception) {
                try {
                    dst.close()
                }
                catch (e2: Exception) { }
            //    Log.i("EXCEPTION4", "${e.toString()}/${e.message.toString()}")
                return -3
            }
            try {
                dst.close()
            }
            catch (e: Exception) {
                return -3
            }
            return 1
        }
        fun sha256(src: InputStream): String {
            var inputByte: ByteArray? = null
            try {
                val inputBuffer = ByteArrayOutputStream()
                var nRead: Int
                val data = ByteArray(16384)
                while (src.read(data, 0, data.size).also { nRead = it } != -1) {
                    inputBuffer.write(data, 0, nRead)
                }
                inputByte = inputBuffer.toByteArray()
                inputBuffer.close()
            }
            catch (e1: Exception) {
                try {
                    src.close()
                }
                catch (e2: Exception) { }
                return ""
            }
            try {
                src.close()
            }
            catch (e: Exception) { }

            var hashString: String = ""
            try {
                hashString = AES256.getSha256(inputByte)
            }
            catch (e: Exception) {
                return ""
            }
            return hashString
        }
    }
}