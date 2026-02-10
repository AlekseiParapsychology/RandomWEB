package com.android.webrng.utils

import android.util.Base64
import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.inv
import kotlin.experimental.or


class AES256 {
    companion object {
        private val sectorSize = 4
        private val saltSize = 4
        private val sauronList = listOf("3378e79abe85a8b4c0a1fc081c7ac083a71842adff87643516c998dabdbb8303", "9560c48e07a96bbfba38b65d1e2935ec776eb0395b4b4459e4663219ea13b230", "7ae6faa480efdfa4e1c42fca44b59684afaecc1a0ec54fd6c8354129665dacfa", "4f1081119bcc8ac5caff209a4a9fcb86c4920989f2057e555cb8f4c948615c46")
        val morgothList = listOf("74F8B4181137F0F24655D4B30A1C2623", "ACF806355A3F71A2179654410AE52A43")

        fun encryptRaw(taskString: ByteArray, keyString: ByteArray, ivString: ByteArray): ByteArray? {
            try {
            //    Log.i("ENCRYPT", "${Base64.encodeToString(keyString, Base64.URL_SAFE)}")
                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                val keySpec = SecretKeySpec(keyString, "AES")
                val ivSpec = IvParameterSpec(ivString)
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
                val encrypt = cipher.doFinal(taskString)
                return encrypt
            }
            catch (e: Exception) {
            //    Log.i("EXCEPTION0", "${e.toString()}/${e.message.toString()}")
                return null
            }
        }
        fun decryptRaw(taskString: ByteArray, keyString: ByteArray, ivString: ByteArray): ByteArray? {
            try {
            //    Log.i("DECRYPT", "${Base64.encodeToString(keyString, Base64.URL_SAFE)}")
                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                val keySpec = SecretKeySpec(keyString, "AES")
                val ivSpec = IvParameterSpec(ivString)
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
                val decrypt = cipher.doFinal(taskString)
                return decrypt
            }
            catch (e: Exception) {
            //    Log.i("EXCEPTION1", "${e.toString()}/${e.message.toString()}")
                return null
            }
        }
        fun encryptString(taskString: String, keyString: ByteArray, ivString: ByteArray): String? {
            try {
                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                val keySpec = SecretKeySpec(keyString, "AES")
                val ivSpec = IvParameterSpec(ivString)
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
                val encrypt = cipher.doFinal(taskString.toByteArray(Charsets.UTF_8))
            //    val test = Base64.encodeToString(encrypt, Base64.NO_WRAP)
            //    Log.i("ENCODE", "${test}/" + test.toString().indexOf("\n").toString())
                return Base64.encodeToString(encrypt, Base64.NO_WRAP or Base64.URL_SAFE)
            }
            catch (e: Exception) {
                return null
            }
        }
        fun decryptString(taskString: String, keyString: ByteArray, ivString: ByteArray): String? {
            try {
                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                val keySpec = SecretKeySpec(keyString, "AES")
                val ivSpec = IvParameterSpec(ivString)
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
                val decrypt = cipher.doFinal(Base64.decode(taskString, Base64.URL_SAFE))
                return decrypt.decodeToString()
            }
            catch (e: Exception) {
                return null
            }
        }

        fun getRawKey(password: String, type: Int = 0): ByteArray {
            if (password.isNotEmpty()) {
                val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec: KeySpec = PBEKeySpec(password.toCharArray(), decodeHexString(sauronList[type]), 4096, 256)
                val tmp: SecretKey = factory.generateSecret(spec)
                val secret: SecretKey = SecretKeySpec(tmp.encoded, "AES")
                return secret.encoded
            }
            else {
                return ByteArray(0)
            }
        }
        fun getSha256(taskString: ByteArray): String {
            if (taskString.isNotEmpty()) {
                try {
                    val digest = MessageDigest.getInstance("SHA-256").apply { reset() }
                    val byteData: ByteArray = digest.digest(taskString)
                    return StringBuffer().apply {
                        byteData.forEach {
                            append(((it.toInt() and 0xff) + 0x100).toString(16).substring(1))
                        }
                    }.toString()
                }
                catch (e: Exception) { }
            }
            return ""
        }
        fun getIV(): String {
            return getHexString(32)
        }
        fun getSalt(taskString: String): String {
        //    val length = taskString.length % 32
        //    Log.i("STRING", getString(32))
            return taskString + '^' + getString(32)
        }
        fun clearSalt(taskString: String, type: Int = 1): String {
            if (taskString.isEmpty())
                return ""
        //    var rawStr: String = ""
            var shortStr: String = ""
            if (type == 1) {
                val taskValue = taskString.indexOf('^')
                if (taskValue > 0 && taskString.length > 1)
                    shortStr = taskString.substring(0, taskValue.coerceAtMost(taskString.length))
                else
                    shortStr = ""
            }
            else {
            //    Log.i("CLEAR", "SALT")
                if (taskString.length % (sectorSize + saltSize) > 0)
                    return ""
                val sectorCount = taskString.length / (sectorSize + saltSize)
                val parseString = taskString.toByteArray(Charsets.UTF_8)
                var taskByte: Byte = 0xFF.toByte()
                if (parseString.size < (sectorCount - 1) * (sectorSize + saltSize))
                    return ""
                for (s in 0 until sectorCount - 1) {
                //    val input = taskString.substring(s * (sectorSize + saltSize), s * (sectorSize + saltSize) + sectorSize)
                    for (k in 0 until saltSize) {
                        parseString[s * (sectorSize + saltSize) + sectorSize + k] = parseString[s * (sectorSize + saltSize) + sectorSize + k].or(taskByte).inv()
                    }
                //    rawStr += input
                }

                try {
                    shortStr = String(parseString).replace(String(ByteArray(saltSize)), "").slice(0 until (sectorCount - 1) * sectorSize)
                }
                catch (e: Exception) {
                    return ""
                }
            //    Log.i("LENGTH", "${rawStr.length}/${shortStr.length}")
            //    Log.i("SHORT", "SECTOR/${shortStr.slice(shortStr.length - 64 until shortStr.length)}")
            //    Log.i("RAW", "${rawStr.slice(rawStr.length - 64 until rawStr.length)}")

                var taskValue = (sectorCount - 1) * (sectorSize + saltSize)
                if (taskValue < 0 || taskValue >= taskString.length)
                    return ""
                val input = taskString.substring(taskValue, taskString.length)

                taskValue = input.indexOf('^')
                if (taskValue > 0 && taskValue < input.length) {
                //    rawStr += input.substring(0, taskValue)
                    shortStr += input.substring(0, taskValue)
                }
            //    Log.i("CLEAR", "COMPLETE")
            }
            return shortStr
        }
        private fun getString(length: Int): String {
            val generator = Random()
            val randomStringBuilder = StringBuilder()
            var tempChar: Char
            for (i in 0 until length) {
                tempChar = ((generator.nextInt(96) + 32).toChar())
                randomStringBuilder.append(tempChar)
            }
            return randomStringBuilder.toString()
        }
        private fun getHexString(length: Int): String {
            val r = Random()
            val sb = StringBuffer()
            while (sb.length < length) {
                sb.append(Integer.toHexString(r.nextInt()))
            }
            val resultStr = sb.toString()
            return resultStr.substring(0, length.coerceAtMost(resultStr.length))
        }

        fun decodeHexString(hexString: String): ByteArray {
            try {
                require(hexString.length % 2 != 1) { "Invalid hexadecimal String supplied." }
                val bytes = ByteArray(hexString.length / 2)
                var i = 0
                while (i < hexString.length) {
                    bytes[i / 2] = hexToByte(hexString.substring(i, i + 2))
                    i += 2
                }
                return bytes
            }
            catch (e: Exception) {
                return ByteArray(0)
            }
        }
        private fun hexToByte(hexString: String): Byte {
            val firstDigit: Int = toDigit(hexString[0])
            val secondDigit: Int = toDigit(hexString[1])
            return ((firstDigit shl 4) + secondDigit).toByte()
        }
        private fun toDigit(hexChar: Char): Int {
            val digit = Character.digit(hexChar, 16)
            require(digit != -1) { "Invalid Hexadecimal Character: $hexChar" }
            return digit
        }

        fun toText(cipher: String, keyStr: ByteArray) : String {
            val taskValue1 = cipher.indexOf('^')
            if (taskValue1 > 0) {
                var ivStr: ByteArray = decodeHexString(cipher.substring(0, taskValue1.coerceAtMost(cipher.length)))
                if (cipher.length > taskValue1 + 1) {
                    val rawString = decryptString(cipher.substring(taskValue1 + 1, cipher.length), keyStr, ivStr)
                    if (rawString != null) {
                        val taskValue2 = rawString.indexOf('^')
                        if (taskValue2 > 0 && taskValue2 < rawString.length)
                            return rawString.substring(0, taskValue2)
                        else if (taskValue2 != 0)
                            return rawString
                    }
                }
            }
            return ""
        }
    }
}