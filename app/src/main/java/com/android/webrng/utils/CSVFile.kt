package com.android.webrng.utils

import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.util.*


class CSVFile(file: android.app.Activity) {
    private val csvResource: android.app.Activity

    init {
        this.csvResource = file
    }

    fun getFileResource() : android.app.Activity {
        return csvResource
    }

    companion object {
        fun read(inputStream: InputStream): JSONArray {
            val resultList: ArrayList<JSONArray> = ArrayList<JSONArray>()
            try {
                inputStream.bufferedReader().use {
                    it.lines().forEach { line ->
                        val row = line.split(",".toRegex()).toTypedArray()
                        resultList.add(JSONArray(row))
                    }
                }
            }
            catch (e: IOException) {
            //    Log.i("EXCEPTION1", "${e}/${e.message}")
            }

            try {
                inputStream.close()
            }
            catch (e: IOException) {
            //    Log.i("EXCEPTION2", "${e}/${e.message}")
            }

            if (resultList.size > 0)
                return JSONArray(resultList)
            else
                return JSONArray()
        }
    }
}