package com.android.webrng.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.webrng.utils.UtcDate
import java.util.*


class SQLTaskTable(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val CREATE_TASK_TABLE = "CREATE TABLE stock_list ( " +
                    "field1 TEXT PRIMARY KEY, " +
                    //title
                    "field2 TEXT, " +
                    //name
                    "field3 TEXT, " +
                    //date
                    "field4 BOOLEAN, " +
                    //precache
                    "field5 TEXT, " +
                    //array
                    "field6 INTEGER )"
                    //type
            db.execSQL(CREATE_TASK_TABLE)
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
            db.execSQL("DROP TABLE IF EXISTS stock_list")
            verifyTask = true
        }
        catch (e: Exception) { }
        if (verifyTask)
            onCreate(db)
        db.closeSafe()
    }

    fun addRecord(stock: String, name: String, type: Int): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        try {
            val values = ContentValues()
            values.put(KEY_FIELD1, stock)
            values.put(KEY_FIELD2, name)
            values.put(KEY_FIELD3, (UtcDate(true).time).toString())
            values.put(KEY_FIELD4, false)
            values.put(KEY_FIELD5, "")
            values.put(KEY_FIELD6, type)
            db.insertWithOnConflict(TABLE_STOCK_LIST, null, values, SQLiteDatabase.CONFLICT_ROLLBACK)
            db.closeSafe()
            return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    val allRecords: HashMap<String, TaskRecord>
        get() {
            val outputResult: HashMap<String, TaskRecord> = HashMap<String, TaskRecord>()
            var db: SQLiteDatabase
            try {
                db = this.readableDatabase
            }
            catch (e: Exception) {
                return hashMapOf()
            }

            var cursor: Cursor? = null
            try {
                val query = "SELECT * FROM $TABLE_STOCK_LIST ORDER BY $KEY_FIELD3"
                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    do {
                        outputResult[cursor.getString(0)] = TaskRecord(cursor.getString(1), cursor.getString(2).toLong(), cursor.getInt(3) > 0, cursor.getString(4), cursor.getInt(5))
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

    fun hasPrecache(stock: String): String {
        var precache: String = ""
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return precache
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM $TABLE_STOCK_LIST WHERE $KEY_FIELD1=?"
            cursor = db.rawQuery(query, arrayOf(java.lang.String.valueOf(stock)))
            if (cursor.moveToFirst()) {
                precache = cursor.getString(4)
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
        return precache
    }

    fun updateRecord(stock: String, precache: Boolean, array: String): Int {
        var task: Int = 0
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return task
        }

        //    Log.i("updateRecord", "$stock/$precache/$array")
        val setValue: Long = UtcDate(true).time / 1000L
        var taskDate: Long = setValue
        if (!precache) {
            var cursor: Cursor? = null
            try {
                val query = "SELECT * FROM $TABLE_STOCK_LIST ORDER BY $KEY_FIELD3 ASC"
                cursor = db.rawQuery(query, arrayOf())
                if (cursor.moveToFirst()) {
                    taskDate = UtcDate(false, (cursor.getString(2).toLong() - 60L) * 1000L).time / 1000L
                    //    Log.i("hasPrecache()", precache)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                taskDate = setValue
            }
            finally {
                if (cursor != null) {
                    try {
                        cursor.close()
                    }
                    catch (e: Exception) { }
                }
            }
        }

        try {
            val values = ContentValues()
            values.put("field3", taskDate.toString())
            values.put("field4", precache)
            values.put("field5", array)
            task = db.update(TABLE_STOCK_LIST, values, "$KEY_FIELD1 = ?", arrayOf(stock))
        }
        catch (e: Exception) {
            e.printStackTrace()
            //    Log.i("Exception", "${e}/${e.message}")
        }
        db.closeSafe()
        return task
    }

    fun checkUpdate(stock: String, name: String, exchange: Int = -1): Int {
        var task: Int = 0
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return -1
        }

        try {
            val values = ContentValues()
            values.put("field2", name)
            if (exchange >= 0)
                values.put("field6", exchange)
            task = db.update(TABLE_STOCK_LIST, values, "$KEY_FIELD1 = ?", arrayOf(stock))
        }
        catch (e: Exception) {
            task = -2
            //    Log.i("Exception", "${e}/${e.message}")
        }
        db.closeSafe()
        return task
    }

    fun checkRecord(stock: String): Boolean {
        var outputResult: Boolean = false
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM $TABLE_STOCK_LIST WHERE $KEY_FIELD1=?"
            cursor = db.rawQuery(query, arrayOf(java.lang.String.valueOf(stock)))
            if (cursor.count > 0) {
                outputResult = true
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
                val query1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='${SQLTaskTable.TABLE_STOCK_LIST}';"
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
                        val query2 = "SELECT  * FROM ${SQLTaskTable.TABLE_STOCK_LIST} LIMIT 1"
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

    fun deleteRecord(stock: String): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var taskResult: Int = 0
        try {
            taskResult = db.delete(TABLE_STOCK_LIST, "$KEY_FIELD1 = ?", arrayOf(stock))
        }
        catch (e: Exception) { }
        db.closeSafe()
        return taskResult > 0
    }

    fun deleteRecord(type: Int): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var taskResult: Int = 0
        try {
            taskResult = db.delete(TABLE_STOCK_LIST, "$KEY_FIELD6 = ?", arrayOf(type.toString()))
        }
        catch (e: Exception) { }
        db.closeSafe()
        return taskResult > 0
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
        private const val TABLE_STOCK_LIST = "stock_list"
        private const val KEY_FIELD1 = "field1"
        private const val KEY_FIELD2 = "field2"
        private const val KEY_FIELD3 = "field3"
        private const val KEY_FIELD4 = "field4"
        private const val KEY_FIELD5 = "field5"
        private const val KEY_FIELD6 = "field6"
    }
}

class TaskRecord(v: String, w: Long, x: Boolean, y: String, z: Int) {
    var Name: String = v
    var Date: Long = w
    var Precache: Boolean = x
    var Array: String = y
    val Type: Int = z
}