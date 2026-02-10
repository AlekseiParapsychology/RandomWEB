package com.android.webrng.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.webrng.constants.getCsvSeparator
import com.android.webrng.utils.AES256
import org.json.JSONArray
import kotlin.collections.ArrayList


class SQLStorageTable(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val CREATE_STORAGE_TABLE = "CREATE TABLE secret_table ( " +
                    "field1 TEXT PRIMARY KEY, " +
                    //timedate
                    "field2 TEXT, " +
                    //target
                    "field3 TEXT, " +
                    //experiment
                    "field4 TEXT, " +
                    //algorithm
                    "field5 TEXT, " +
                    //range
                    "field6 TEXT, " +
                    //price
                    "field7 TEXT, " +
                    //interval
                    "field8 TEXT, " +
                    //state
                    "field9 TEXT, " +
                    //size
                    "field10 TEXT, " +
                    //array
                    "field11 TEXT, " +
                    //stock
                    "field12 TEXT, " +
                    //comment
                    "crypto BOOLEAN, " +
                    //crypto
                    "aes TEXT, " +
                    //aes
                    "iv TEXT )"
            //iv
            db.execSQL(CREATE_STORAGE_TABLE)
        }
        catch (e: Exception) { }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { }

    fun recreate() {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return
        }
        var verifyTask: Boolean = false
        try {
            db.execSQL("DROP TABLE IF EXISTS secret_table")
            verifyTask = true
        }
        catch (e: Exception) { }
        if (verifyTask)
            onCreate(db)
        db.closeSafe()
    }

    fun addRecord(input1: String?, input2: String, input3: String, input4: String, input5: String, input6: String, input7: String, input8: String, input9: String, input10: String, input11: String, input12: String = "", input13: Int? = null, input14: String? = null, input15: String? = null): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        //    createExist()

        try {
            //    Log.i("addRecord", "")
            val values = ContentValues()
            values.put(KEY_FIELD1, if (input1 == null) getCsvSeparator() else input1)
            values.put(KEY_FIELD2, input2)
            values.put(KEY_FIELD3, input3)
            values.put(KEY_FIELD4, input4)
            values.put(KEY_FIELD5, input5)
            values.put(KEY_FIELD6, input6)
            values.put(KEY_FIELD7, input7)
            values.put(KEY_FIELD8, input8)
            values.put(KEY_FIELD9, input9)
            values.put(KEY_FIELD10, input10)
            values.put(KEY_FIELD11, input11)
            values.put(KEY_FIELD12, input12)
            var cryptoInsert: Boolean
            if (input13 == null) {
                cryptoInsert = false
            }
            else {
                cryptoInsert = if (input13 == 1) true else false
            }
            values.put(KEY_CRYPTO, cryptoInsert)
            values.put(KEY_AES, if (input14 == null) "0" else input14)
            values.put(KEY_IV, if (input15 == null) "0" else input15)
            db.insertWithOnConflict(TABLE_SECRET, null, values, SQLiteDatabase.CONFLICT_REPLACE)
            db.closeSafe()
            return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    fun updateRecord(input1: String, input2: String, input3: String, input4: String = ""): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        try {
            val values = ContentValues()
            if (input2.isNotEmpty())
                values.put(SQLStorageTable.KEY_FIELD6, input2)
            values.put(SQLStorageTable.KEY_FIELD12, input3)
            if (input4.isNotEmpty())
                values.put(SQLStorageTable.KEY_FIELD2, input4)
            db.update(SQLStorageTable.TABLE_SECRET, values, "${SQLStorageTable.KEY_FIELD1} = ?", arrayOf(input1))
            db.closeSafe()
            //    Log.i("updateStorage", timestamp)
            return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    fun getRecord(input1: String): List<String> {
        var outputList = ArrayList<String>()
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return listOf()
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT  * FROM $TABLE_SECRET WHERE $KEY_FIELD1=?"
            cursor = db.rawQuery(query, arrayOf(input1))
            if (cursor.moveToFirst()) {
                outputList.add(cursor.getString(0))
                outputList.add(cursor.getString(1))
                outputList.add(cursor.getString(2))
                outputList.add(cursor.getString(3))
                outputList.add(cursor.getString(4))
                outputList.add(cursor.getString(5))
                outputList.add(cursor.getString(6))
                outputList.add(cursor.getString(7))
                outputList.add(cursor.getString(8))
                outputList.add(cursor.getString(9))
                outputList.add(cursor.getString(10))
                outputList.add(cursor.getString(11))
                outputList.add((cursor.getInt(12) > 0).toString())
                outputList.add(cursor.getString(13))
                outputList.add(cursor.getString(14))
                //    Log.i("getRecord()", "")
            }
        }
        catch (e: Exception) { }
        finally {
            if (cursor != null) {
                try {
                    cursor.close()
                }
                catch (e: Exception) { }
            }
        }
        db.closeSafe()
        return outputList.toList()
    }

    val allRecords: JSONArray
        get() {
            val outputList: ArrayList<JSONArray> = ArrayList<JSONArray>()
            var db: SQLiteDatabase
            try {
                db = this.readableDatabase
            }
            catch (e: Exception) {
                return JSONArray()
            }

            var taskResult: Boolean = false
            var cursor: Cursor? = null
            try {
                val query = "SELECT  * FROM $TABLE_SECRET ORDER BY field1 DESC"
                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    do {
                        var inputList = ArrayList<String>()
                        inputList.add(cursor.getString(0))
                        inputList.add(cursor.getString(1))
                        inputList.add(cursor.getString(2))
                        inputList.add(cursor.getString(3))
                        inputList.add(cursor.getString(4))
                        inputList.add(cursor.getString(5))
                        inputList.add(cursor.getString(6))
                        inputList.add(cursor.getString(7))
                        inputList.add(cursor.getString(8))
                        inputList.add(cursor.getString(9))
                        inputList.add(cursor.getString(10))
                        inputList.add(cursor.getString(11))
                        inputList.add((cursor.getInt(12) > 0).toString())
                        inputList.add(cursor.getString(13))
                        inputList.add(cursor.getString(14))

                        outputList.add(JSONArray(inputList))
                        inputList.clear()
                    } while (cursor.moveToNext())
                }
                taskResult = true
            }
            catch (e: Exception) { }
            finally {
                if (cursor != null) {
                    try {
                        cursor.close()
                    }
                    catch (e: Exception) { }
                }
            }
            db.closeSafe()

            if (taskResult)
                return JSONArray(outputList)
            else
                return JSONArray()
            //    Log.i("getAllRecords()", "")
        }

    val checkExist: Boolean
        get() {
            var result: Boolean = true
            var db: SQLiteDatabase
            try {
                db = this.readableDatabase
            }
            catch (e: Exception) {
                return true
            }

            var cursor1: Cursor? = null
            var cursor2: Cursor? = null
            try {
                val query1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='$TABLE_SECRET';"
                cursor1 = db.rawQuery(query1, null)
                if (cursor1 == null) {
                    result = false
                }
                else {
                    var taskRun: Boolean = false
                    if (cursor1.count < 1)
                        result = false
                    else
                        taskRun = true
                    cursor1.close()
                    cursor1 = null

                    if (taskRun) {
                        val query2 = "SELECT  * FROM $TABLE_SECRET LIMIT 1"
                        cursor2 = db.rawQuery(query2, null)
                        if (cursor2 == null) {
                            result = false
                        }
                        else {
                            if (cursor2.count < 1)
                                result = false
                        }
                        cursor2.close()
                        cursor2 = null
                    }
                }
                //    Log.i("checkExist()", "")
            }
            catch (e: Exception) {
                result = false
            }
            finally {
                if (cursor1 != null) {
                    try {
                        cursor1.close()
                    }
                    catch (e: Exception) { }
                }
                if (cursor2 != null) {
                    try {
                        cursor2.close()
                    }
                    catch (e: Exception) { }
                }
            }
            db.closeSafe()
            return result
        }

    fun createExist() {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return
        }
        try {
            val CREATE_STORAGE_TABLE = "CREATE TABLE IF NOT EXISTS secret_table ( field1 TEXT PRIMARY KEY, field2 TEXT, field3 TEXT, field4 TEXT, field5 TEXT, field6 TEXT, field7 TEXT, field8 TEXT, field9 TEXT, field10 TEXT, field11 TEXT, field12 TEXT, crypto BOOLEAN, aes TEXT, iv TEXT )"
            db.execSQL(CREATE_STORAGE_TABLE)
        }
        catch (e: Exception) { }
        db.closeSafe()
    }

    fun cryptoRecord(input1: String, input2: String, input3: Int): Int {
        var count: Int = 0
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return -1
        }

        var taskResult: Boolean = false
        var cursor: Cursor? = null
        try {
            val outputList: ArrayList<JSONArray> = ArrayList<JSONArray>()
            val query = if (input3 == 0) "SELECT  * FROM $TABLE_SECRET WHERE $KEY_AES = \"$input2\"" else "SELECT  * FROM $TABLE_SECRET WHERE $KEY_CRYPTO = 0"
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    var inputList = ArrayList<String>()
                    inputList.add(cursor.getString(0))
                    inputList.add(cursor.getString(1))
                    inputList.add(cursor.getString(2))
                    inputList.add(cursor.getString(3))
                    inputList.add(cursor.getString(4))
                    inputList.add(cursor.getString(5))
                    inputList.add(cursor.getString(6))
                    inputList.add(cursor.getString(7))
                    inputList.add(cursor.getString(8))
                    inputList.add(cursor.getString(9))
                    inputList.add(cursor.getString(10))
                    inputList.add(cursor.getString(11))
                    inputList.add((cursor.getInt(12) > 0).toString())
                    inputList.add(cursor.getString(13))
                    inputList.add(cursor.getString(14))
                    //    Log.i("CRYPTO", "${outputList[0]}/${outputList[12]}/${outputList[14]}")

                    outputList.add(JSONArray(inputList))
                    inputList.clear()
                } while (cursor.moveToNext())
            }
            cursor.close()
            cursor = null

            val keyStr: ByteArray = AES256.getRawKey(input1, 1)
            for (s in 0 until outputList.size) {
                try {
                    if (input3 == 0) {
                        val iv = outputList[s][14].toString()
                        val ivStr = AES256.decodeHexString(iv)
                        val field1 = outputList[s][0].toString()
                        val field2 = AES256.clearSalt(AES256.decryptString(outputList[s][1].toString(), keyStr, ivStr)!!)
                        val field3 = AES256.clearSalt(AES256.decryptString(outputList[s][2].toString(), keyStr, ivStr)!!)
                        val field4 = AES256.clearSalt(AES256.decryptString(outputList[s][3].toString(), keyStr, ivStr)!!)
                        val field5 = AES256.clearSalt(AES256.decryptString(outputList[s][4].toString(), keyStr, ivStr)!!)
                        val field6 = AES256.clearSalt(AES256.decryptString(outputList[s][5].toString(), keyStr, ivStr)!!)
                        val field7 = AES256.clearSalt(AES256.decryptString(outputList[s][6].toString(), keyStr, ivStr)!!)
                        val field8 = AES256.clearSalt(AES256.decryptString(outputList[s][7].toString(), keyStr, ivStr)!!)
                        val field9 = AES256.clearSalt(AES256.decryptString(outputList[s][8].toString(), keyStr, ivStr)!!)
                        val field10 = AES256.clearSalt(AES256.decryptString(outputList[s][9].toString(), keyStr, ivStr)!!)
                        val field11 = AES256.clearSalt(AES256.decryptString(outputList[s][10].toString(), keyStr, ivStr)!!)
                        val field12 = AES256.clearSalt(AES256.decryptString(outputList[s][11].toString(), keyStr, ivStr)!!)
                        val crypto = 0
                        val aes = "0"
                        addRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, crypto, aes)
                    } else {
                        val iv = AES256.getIV()
                        val ivStr = AES256.decodeHexString(iv)
                        val field1 = outputList[s][0].toString()
                        val field2 = AES256.encryptString(AES256.getSalt(outputList[s][1].toString()), keyStr, ivStr)
                        val field3 = AES256.encryptString(AES256.getSalt(outputList[s][2].toString()), keyStr, ivStr)
                        val field4 = AES256.encryptString(AES256.getSalt(outputList[s][3].toString()), keyStr, ivStr)
                        val field5 = AES256.encryptString(AES256.getSalt(outputList[s][4].toString()), keyStr, ivStr)
                        val field6 = AES256.encryptString(AES256.getSalt(outputList[s][5].toString()), keyStr, ivStr)
                        val field7 = AES256.encryptString(AES256.getSalt(outputList[s][6].toString()), keyStr, ivStr)
                        val field8 = AES256.encryptString(AES256.getSalt(outputList[s][7].toString()), keyStr, ivStr)
                        val field9 = AES256.encryptString(AES256.getSalt(outputList[s][8].toString()), keyStr, ivStr)
                        val field10 = AES256.encryptString(AES256.getSalt(outputList[s][9].toString()), keyStr, ivStr)
                        val field11 = AES256.encryptString(AES256.getSalt(outputList[s][10].toString()), keyStr, ivStr)
                        val field12 = AES256.encryptString(AES256.getSalt(outputList[s][11].toString()), keyStr, ivStr)
                        val crypto = 1
                        val aes = input2
                        addRecord(field1, field2!!, field3!!, field4!!, field5!!, field6!!, field7!!, field8!!, field9!!, field10!!, field11!!, field12!!, crypto, aes, iv)
                    }
                    count++
                }
                catch (e: Exception) {
                    //    Log.i("EXCEPTION", "${e.toString()}/${e.message.toString()}")
                }
            }
            taskResult = true
            /*    val values = ContentValues()
                values.put(SQLStorageTable.KEY_AES, hash)
                db.update(SQLStorageTable.TABLE_EXPERIMENT_EVENT, values, "${SQLStorageTable.KEY_TIMEDATE} = ?", arrayOf(getDateTime()))
                db.closeSafe()  */
        }
        catch (e: Exception) { }
        finally {
            if (cursor != null) {
                try {
                    cursor.close()
                }
                catch (e: Exception) { }
            }
        }
        db.closeSafe()

        if (taskResult)
            return count
        else
            return -1
    }

    fun countHash(): HashMap<String, Int> {
        val outputResult: HashMap<String, Int> = HashMap<String, Int>()
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return outputResult
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT aes, COUNT(*) FROM $TABLE_SECRET GROUP BY aes"
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    outputResult[cursor.getString(0)] = cursor.getString(1).toInt()
                } while (cursor.moveToNext())
            }
        }
        catch (e: Exception) { }
        finally {
            if (cursor != null) {
                try {
                    cursor.close()
                }
                catch (e: Exception) { }
            }
        }
        db.closeSafe()
        return outputResult
    }

    fun deleteRecord(input1: String): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var taskResult: Int = 0
        try {
            taskResult = db.delete(TABLE_SECRET, "$KEY_FIELD1 = ?", arrayOf(input1))
        }
        catch (e: Exception) { }
        db.closeSafe()
        return taskResult > 0
        //    Log.i("deleteList", input1.toString())
    }

    private fun SQLiteDatabase.closeSafe() {
        try {
            this.close()
        }
        catch (e: Exception) { }
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SpecialBase"
        private const val TABLE_SECRET = "secret_table"
        private const val KEY_FIELD1 = "field1"
        private const val KEY_FIELD2 = "field2"
        private const val KEY_FIELD3 = "field3"
        private const val KEY_FIELD4 = "field4"
        private const val KEY_FIELD5 = "field5"
        private const val KEY_FIELD6 = "field6"
        private const val KEY_FIELD7 = "field7"
        private const val KEY_FIELD8 = "field8"
        private const val KEY_FIELD9 = "field9"
        private const val KEY_FIELD10 = "field10"
        private const val KEY_FIELD11 = "field11"
        private const val KEY_FIELD12 = "field12"
        private const val KEY_CRYPTO = "crypto"
        private const val KEY_AES = "aes"
        private const val KEY_IV = "iv"
    }
}