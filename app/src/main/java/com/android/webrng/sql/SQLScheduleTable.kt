package com.android.webrng.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*


class SQLScheduleTable(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val CREATE_SCHEDULE_TABLE = "CREATE TABLE $TABLE_SCHEDULE ( " +
                    "$KEY_FIELD1 INTEGER, " +
                    //timestamp
                    "$KEY_FIELD2 INTEGER, " +
                    //exchange
                    "$KEY_FIELD3 BOOLEAN, " +
                    //type
                    "PRIMARY KEY ($KEY_FIELD1, $KEY_FIELD2) )"
            db.execSQL(CREATE_SCHEDULE_TABLE)
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
            db.execSQL("DROP TABLE IF EXISTS $TABLE_SCHEDULE")
            verifyTask = true
        }
        catch (e: Exception) { }
        if (verifyTask)
            onCreate(db)
        db.closeSafe()
    }

    fun addRecord(input1: Long, input2: Int, input3: Boolean): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        try {
            val values = ContentValues()
            values.put(KEY_FIELD1, input1)
            values.put(KEY_FIELD2, input2)
            values.put(KEY_FIELD3, input3)
            db.insertWithOnConflict(TABLE_SCHEDULE, null, values, SQLiteDatabase.CONFLICT_REPLACE)
            db.closeSafe()
            return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    val allRecords: Array<ScheduleRecord>
        get() {
            val outputList: ArrayList<ScheduleRecord> = arrayListOf()
            var db: SQLiteDatabase
            try {
                db = this.readableDatabase
            }
            catch (e: Exception) {
                return arrayOf()
            }

            var cursor: Cursor? = null
            try {
                val query = "SELECT * FROM $TABLE_SCHEDULE ORDER BY $KEY_FIELD1 DESC"
                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    do {
                        outputList.add(ScheduleRecord(cursor.getLong(0), cursor.getInt(1), cursor.getInt(2) > 0))
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
            return outputList.toTypedArray()
        }

    fun getRecord(input1: Int, input2: Boolean): Array<Long> {
        val outputList: ArrayList<Long> = ArrayList<Long>()
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return arrayOf()
        }

        var objectType = if (input2) 1 else 0
        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM $TABLE_SCHEDULE WHERE $KEY_FIELD2 = \"$input1\" AND $KEY_FIELD3 = \"${objectType}\""
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    outputList.add(cursor.getLong(0))
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
        return outputList.toTypedArray()
    }

    fun deleteRecord(input1: Long): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var taskResult: Int = 0
        try {
            taskResult = db.delete(TABLE_SCHEDULE, "$KEY_FIELD1 = ?", arrayOf("$input1"))
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
        private const val TABLE_SCHEDULE = "schedule"
        private const val KEY_FIELD1 = "field1"
        private const val KEY_FIELD2 = "field2"
        private const val KEY_FIELD3 = "field3"
    }
}

class ScheduleRecord(x: Long, y: Int, z: Boolean) {
    val Index: Long = x
    val Exchange: Int = y
    var Type: Boolean = z
}