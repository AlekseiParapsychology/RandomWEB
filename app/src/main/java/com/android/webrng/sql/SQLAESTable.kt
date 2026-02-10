package com.android.webrng.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.webrng.utils.UtcDate
import com.android.webrng.utils.utcFormat
import org.json.JSONArray
import kotlin.collections.ArrayList


class SQLAESTable(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val CREATE_AES_TABLE = "CREATE TABLE aes_hash ( " +
                    "sha256 TEXT PRIMARY KEY, " +
                    "name TEXT )"
            db.execSQL(CREATE_AES_TABLE)
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
            db.execSQL("DROP TABLE IF EXISTS aes_hash")
            verifyTask = true
        }
        catch (e: Exception) { }
        if (verifyTask)
            onCreate(db)
        db.closeSafe()
    }

    fun addRecord(hash: String, name: String?): String {
        val rowExist = getRecord(hash).isNotEmpty()
        if (rowExist)
            return "Exist"

        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return "CATCHGetSqlBase"
        }
        try {
            //    Log.i("addRecord", "")
            val timedate = getCsvSeparator()
            val values = ContentValues()
            values.put(KEY_HASH, hash)
            values.put(KEY_NAME, if (name == null) timedate else name)
            db.insertWithOnConflict(TABLE_AES_HASH, null, values, SQLiteDatabase.CONFLICT_ROLLBACK)
            db.closeSafe()
            return timedate
        }
        catch (e: Exception) {
            db.closeSafe()
            //    Log.i("EXCEPTION", "${e.toString()}/${e.message.toString()}")
            return "CATCH${e}/${e.message}"
        }
    }

    fun getRecord(hash: String): List<String> {
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
            val query = "SELECT  * FROM $TABLE_AES_HASH WHERE sha256=?"
            cursor = db.rawQuery(query, arrayOf(hash))
            if (cursor.moveToFirst()) {
                outputList.add(cursor.getString(0))
                outputList.add(cursor.getString(1))
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

            var cursor: Cursor? = null
            try {
                val query = "SELECT  * FROM $TABLE_AES_HASH"
                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    do {
                        var inputList = ArrayList<String>()
                        inputList.add(cursor.getString(0).toString())
                        inputList.add(cursor.getString(1).toString())

                        outputList.add(JSONArray(inputList))
                        inputList.clear()
                    } while (cursor.moveToNext())
                }
                //    Log.i("getAllRecords()", "")
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
            return JSONArray(outputList)
        }

    fun hasPrecache(hash: String): Boolean {
        var precache: Boolean = false
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT  * FROM ${SQLAESTable.TABLE_AES_HASH} WHERE sha256=?"
            cursor = db.rawQuery(query, arrayOf(java.lang.String.valueOf(hash)))
            if (cursor.moveToFirst()) {
                precache = true
                //    Log.i("hasPrecache()", hash)
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
                val query1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='${SQLAESTable.TABLE_AES_HASH}';"
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
                        val query2 = "SELECT  * FROM ${SQLAESTable.TABLE_AES_HASH} LIMIT 1"
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

    fun createExist() {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return
        }
        try {
            val CREATE_AES_TABLE = "CREATE TABLE IF NOT EXISTS aes_hash ( " +
                    "sha256 TEXT PRIMARY KEY, " +
                    "name TEXT )"
            db.execSQL(CREATE_AES_TABLE)
        }
        catch (e: Exception) { }
        db.closeSafe()
    }

    fun deleteRecord(hash: String) : Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var taskResult: Int = 0
        try {
            taskResult = db.delete(TABLE_AES_HASH, "$KEY_HASH = ?", arrayOf(hash))
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
    private fun getCsvSeparator(): String {
        val date = UtcDate(false)
        return "yyyy-MM-dd HH:mm:ss.SSS".utcFormat(date, Int.MAX_VALUE)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SpecialBase"
        private const val TABLE_AES_HASH = "aes_hash"
        private const val KEY_HASH = "sha256"
        private const val KEY_NAME = "name"
    }
}