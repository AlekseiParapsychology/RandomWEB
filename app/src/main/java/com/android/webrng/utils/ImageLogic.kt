package com.android.webrng.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


class ImageLogic {
    companion object {
        fun decode(res: Resources, taskImage: Int, keyStr: ByteArray): Bitmap? {
            var outputResult: Bitmap? = null
            val dataArray = res.getStringArray(taskImage)
            val ivStr: ByteArray = AES256.decodeHexString(dataArray[0])
            var dataStr: String = ""
            try {
                dataStr = dataArray.drop(1).joinToString("", "", "") { it -> "${it}" }
            }
            catch (e: Exception) {
                return null
            }

        //    Log.i("IMAGELOGIC", "IV=${dataArray[0]}")
        //    Log.i("IMAGELOGIC", "STR=${dataStr}")
            val data = AES256.decryptRaw(Base64.decode(dataStr, Base64.DEFAULT), keyStr, ivStr)
            if (data != null) {
                try {
                    outputResult = BitmapFactory.decodeByteArray(data, 0, data.size).copy(Bitmap.Config.ARGB_8888, true)
                }
                catch (e: Exception) {
                    return null
                }
                if (outputResult != null)
                    return outputResult
            }
            return null
        }
    }
}