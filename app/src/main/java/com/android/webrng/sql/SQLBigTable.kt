package com.android.webrng.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.webrng.utils.*
import kotlin.collections.ArrayList


class SQLBigTable(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val CREATE_BIG_LIST_TABLE = "CREATE TABLE big_list ( " +
                    "field1 INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    //id
                    "field2 TEXT, " +
                    //array
                    "field3 TEXT )"
                    //type
            db.execSQL(CREATE_BIG_LIST_TABLE)
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
            db.execSQL("DROP TABLE IF EXISTS big_list")
            verifyTask = true
        }
        catch (e: Exception) { }
        if (verifyTask)
            onCreate(db)
        db.closeSafe()
    }

    fun addRecord(input1: String, input2: String): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        try {
        //    Log.i("Special", "<$input1/$input2>")
            val values = ContentValues()
            values.put(KEY_FIELD2, input1)
            values.put(KEY_FIELD3, input2)
            //    db.insert(TABLE_BIG_LIST, null, values)
            db.insertWithOnConflict(TABLE_BIG_LIST, null, values, SQLiteDatabase.CONFLICT_REPLACE)
            db.closeSafe()
            return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    fun allRecords(input1: Int, input2: Int, input3: ByteArray): Array<String> {
        val outputList: ArrayList<String> = ArrayList<String>()
        val cal = UtcCalendar.getInstance()
        cal.set(cal.get(UtcCalendar.YEAR), cal.get(UtcCalendar.MONTH), cal.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
        val calTimestamp = cal.timeInMillis

        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return arrayOf()
        }

        var cursor: Cursor? = null
        try {
            var inputCount = 0
            var flag: Boolean = if (input2 == 1) false else true
            var reserveValue: Long = -1
            var reserveObject: String = ""
            var query: String
            if (input2 == 1)
                query = "SELECT * FROM $TABLE_BIG_LIST WHERE (field3 = 'V1' OR field3 = 'V2')"
            else
                query = "SELECT * FROM $TABLE_BIG_LIST WHERE (field3 = 'V3' OR field3 = 'V4')"
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    var SpecialValue: String = ""
                    when (cursor.getString(2)) {
                        "V1" -> SpecialValue = "W"
                        "V2" -> SpecialValue = "X"
                        "V3" -> SpecialValue = "Y"
                        "V4" -> SpecialValue = "Z"
                    }

                    val taskValue = AES256.toText(cursor.getString(1), input3).toLong()
                    if (taskValue > calTimestamp) {
                        if (!flag) {
                            outputList.add("dd/MM/yyyy".utcFormat(UtcDate(false, reserveValue), Int.MAX_VALUE) + reserveObject)
                            flag = true
                        }
                    //    Log.i("DATE", "<${taskValue}/${"dd/MM/yyyy HH:mm:ss".utcFormat(UtcDate(false, taskValue), Int.MAX_VALUE)}>")
                        outputList.add("dd/MM/yyyy".utcFormat(UtcDate(false, taskValue), Int.MAX_VALUE) + SpecialValue)
                        inputCount++

                        if (inputCount >= input1)
                            break
                    } else if (!flag) {
                        reserveValue = taskValue
                        reserveObject = SpecialValue
                    }
                } while (cursor.moveToNext())
            }
        }
        catch (e: Exception) {
            //    Log.i("ERROR", "${e}/${e.message}")
        }
        finally {
            if (cursor != null) {
                try {
                    cursor.close()
                }
                catch (e: Exception) { }
            }
        }
        db.closeSafe()
        return outputList.toTypedArray()
    }

    fun sortRecord(input1: ByteArray): Int {
        var requestResult: Int = 0
        val sortList: ArrayList<BigRecord> = ArrayList<BigRecord>()
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return 1
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM $TABLE_BIG_LIST"
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val taskSpecial = cursor.getString(2)
                    val taskValue = AES256.toText(cursor.getString(1), input1).toLong()
                    sortList.add(BigRecord(taskValue, taskSpecial, ""))
                } while (cursor.moveToNext())
            }
        }
        catch (e: Exception) {
            return 2
        }
        finally {
            if (cursor != null) {
                try {
                    cursor.close()
                }
                catch (e: Exception) { }
            }
        }

        if (sortList.isEmpty())
            return 3

        try {
            sortList.sortBy { s -> s.Field1 }
        }
        catch (e: Exception) {
            return 3
        }

        try {
            db.delete(TABLE_BIG_LIST, null, null)
        }
        catch (e: Exception) {
            return 4
        }

        for (s in 0 until sortList.size) {
            val iv = AES256.getIV()
            val ivStr = AES256.decodeHexString(iv)
            val outputText: String? = AES256.encryptString(AES256.getSalt(sortList[s].Field1.toString()), input1, ivStr)
            if (outputText != null) {
                val taskValue = "$iv^$outputText"
                this.addRecord(taskValue, sortList[s].Field2)
            }
        }

        db.closeSafe()
        return requestResult
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
                val query1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='${SQLBigTable.TABLE_BIG_LIST}';"
                cursor1 = db.rawQuery(query1, null)
                if (cursor1 == null) {
                    result = false
                }
                else {
                    //    Log.i("COUNT", "${cursor.count}")
                    var taskRun: Boolean = false
                    if (cursor1.count < 1)
                        result = false
                    else
                        taskRun = true
                    cursor1.close()
                    cursor1 = null

                    if (taskRun) {
                        val query2 = "SELECT  * FROM ${SQLBigTable.TABLE_BIG_LIST} LIMIT 1"
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
                //    Log.i("EXCEPTION", "${e}/${e.message}")
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

    fun deleteRecord(input1: Long, input2: ByteArray): Boolean {
        var requestResult: Boolean = false
        val searchList: ArrayList<BigRecord> = ArrayList<BigRecord>()
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return requestResult
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM $TABLE_BIG_LIST"
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val taskSpecial = cursor.getString(2)
                    val inputvalue = cursor.getString(1)
                    val taskValue = AES256.toText(inputvalue, input2).toLong()
                    searchList.add(BigRecord(taskValue, taskSpecial, inputvalue))
                } while (cursor.moveToNext())
            }
        }
        catch (e: Exception) {
            return requestResult
        }
        finally {
            if (cursor != null) {
                try {
                    cursor.close()
                }
                catch (e: Exception) { }
            }
        }

        if (searchList.isEmpty())
            return requestResult
        for (s in 0 until searchList.size) {
            if (searchList[s].Field1 == input1) {
                try {
                    val taskResult = db.delete(SQLBigTable.TABLE_BIG_LIST, "${SQLBigTable.KEY_FIELD2} = ?", arrayOf(searchList[s].Field3))
                    if (taskResult > 0)
                        requestResult = true
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
                break
            }
        }

        db.closeSafe()
        return requestResult
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
        private const val TABLE_BIG_LIST = "big_list"
        private const val KEY_FIELD1 = "field1"
        private const val KEY_FIELD2 = "field2"
        private const val KEY_FIELD3 = "field3"
    }
}

class BigRecord(x: Long, y: String, z: String) {
    var Field1: Long = x
    var Field2: String = y
    var Field3: String = z
}