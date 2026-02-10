package com.android.webrng.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.webrng.utils.AES256
import org.json.JSONArray


class SQLNameLogoTable(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val CREATE_NAME_LOGO_TABLE = "CREATE TABLE task_name ( " +
                    "field1 TEXT PRIMARY KEY, " +
                    //title
                    "field2 TEXT, " +
                    //exchange
                    "field3 TEXT, " +
                    //name
                    "field4 TEXT, " +
                    //sector
                    "field5 TEXT, " +
                    //logo
                    "field6 TEXT )"
                    //meta
            db.execSQL(CREATE_NAME_LOGO_TABLE)
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
            db.execSQL("DROP TABLE IF EXISTS task_name")
            verifyTask = true
        }
        catch (e: Exception) { }
        if (verifyTask)
            onCreate(db)
        db.closeSafe()
    }

    fun addUpdateRecord(input1: String, input2: String, input3: String, input4: String, input5: String, input6: String): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        try {
            //    Log.i("addNameLogo", name)
            var insertRun: Boolean = true
            val values1 = ContentValues()
            values1.put(KEY_FIELD1, input1)
            values1.put(KEY_FIELD2, input2)
            if (input2.isEmpty())
                insertRun = false
            values1.put(KEY_FIELD3, input3)
            values1.put(KEY_FIELD4, input4)
            values1.put(KEY_FIELD5, input5)
            values1.put(KEY_FIELD6, input6)

            var sqlResult1: Long = -1L
            if (insertRun)
                sqlResult1 = db.insertWithOnConflict(TABLE_TASK_NAME, null, values1, SQLiteDatabase.CONFLICT_IGNORE)

            var sqlResult2: Int = 1
            if (sqlResult1 == -1L) {
                val values2 = ContentValues()
                if (input2.isNotEmpty())
                    values2.put(KEY_FIELD2, input2)
                values2.put(KEY_FIELD3, input3)
                if (input4.isNotEmpty())
                    values2.put(KEY_FIELD4, input4)
                if (input5.isNotEmpty())
                    values2.put(KEY_FIELD5, input5)
                if (input6.isNotEmpty())
                    values2.put(KEY_FIELD6, input6)
                sqlResult2 = db.update(TABLE_TASK_NAME,  values2, "$KEY_FIELD1=?", arrayOf(input1))
            }
            db.closeSafe()
            if (sqlResult2 <= 0)
                return false
            else
                return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    val allRecords: Array<NameLogoRecord>
        get() {
            val outputList: ArrayList<NameLogoRecord> = ArrayList<NameLogoRecord>()
            var db: SQLiteDatabase
            try {
                db = this.readableDatabase
            }
            catch (e: Exception) {
                return arrayOf()
            }

            var cursor: Cursor? = null
            try {
                val query = "SELECT * FROM ${SQLNameLogoTable.TABLE_TASK_NAME}"
                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    do {
                        outputList.add(NameLogoRecord(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)))
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
    val allRecordsJSON: JSONArray
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
                val query = "SELECT * FROM ${SQLNameLogoTable.TABLE_TASK_NAME}"
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
        }

    fun getRecord(input1: String, input2: Boolean = false, input3: String = ""): NameLogoRecord? {
        var outputValue: NameLogoRecord? = null
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return null
        }

        var cursor: Cursor? = null
        try {
            var query: String = ""
            if (!input2)
                query = "SELECT  * FROM $TABLE_TASK_NAME WHERE $KEY_FIELD1=?"
            else
                query = "SELECT  * FROM $TABLE_TASK_NAME WHERE $KEY_FIELD6=?"
            cursor = db.rawQuery(query, arrayOf(java.lang.String.valueOf(input1)))
            if (cursor.moveToFirst()) {
                outputValue = NameLogoRecord(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5))
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

        if (input3.isNotEmpty() && outputValue != null) {
            //    Log.i("CONVERT", "<${input3}>/${outputValue.Name}/${outputValue.Logo}")
            val keyStr: ByteArray = AES256.getRawKey(input3, 2)
            val taskValue = outputValue.Name.indexOf('^')
            //    Log.i("TASKVALUE", "${outputValue.Logo.length}")
            if (taskValue > 0 && outputValue.Name.length > taskValue + 1) {
                val iv = outputValue.Name.substring(0, taskValue)
                val taskStr1 = outputValue.Name.substring(taskValue + 1, outputValue.Name.length)
                val taskStr2 = outputValue.Logo
                val rawStr1 = AES256.decryptString(taskStr1, keyStr, AES256.decodeHexString(iv))
                val rawStr2 = AES256.decryptString(taskStr2, keyStr, AES256.decodeHexString(iv))
                //    Log.i("VALUE", "${taskStr1}/${taskStr2}")
                //    Log.i("RAW", "${rawStr1}/${rawStr2}")
                if (rawStr1 != null && rawStr2 != null) {
                    //    Log.i("RESULT", "${rawStr1}/${rawStr2}")
                    outputValue.Name = AES256.clearSalt(rawStr1, 1)
                    outputValue.Logo = AES256.clearSalt(rawStr2, 2)
                    //    Log.i("SALT", "${outputValue.Logo}")
                }
            }
        }
        //    Log.i("LOGO", "<${outputValue!!.Name}>/${outputValue.Logo}")
        return outputValue
    }

/*    fun updateRecord(input1: String, input2: String, input3: String, input4: String, input5: String): Int {
        var task: Int = 0
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return task
        }
        try {
            val values = ContentValues()
            values.put(KEY_FIELD2, input2)
            values.put(KEY_FIELD3, input3)
            values.put(KEY_FIELD4, input4)
            values.put(KEY_FIELD6, input5)
            task = db.update(TABLE_TASK_NAME, values, "$KEY_FIELD1 = ?", arrayOf(input1))
        }
        catch (e: Exception) { }
        db.closeSafe()
        return task
    }   */

    fun getSuperList(): Array<String> {
        var outputArray: ArrayList<String> = ArrayList<String>()
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return arrayOf()
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT  * FROM $TABLE_TASK_NAME WHERE $KEY_FIELD2=\"OTHER\""
            cursor = db.rawQuery(query, arrayOf())
            if (cursor.moveToFirst()) {
                do {
                    outputArray.add(cursor.getString(0))
                    //    Log.i("getNameLogo()", outputValue.toString())
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
        return outputArray.toTypedArray()
    }

    fun countRecord(): Int {
        var value: Int = 0
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return value
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM ${SQLNameLogoTable.TABLE_TASK_NAME}"
            cursor = db.rawQuery(query, null)
            value = cursor.count
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
        //    Log.i("getCount()", value.toString())
        return value
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
                val query1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='${SQLNameLogoTable.TABLE_TASK_NAME}';"
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
                        val query2 = "SELECT  * FROM ${SQLNameLogoTable.TABLE_TASK_NAME} LIMIT 1"
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

    fun deleteRecord(input1: String, type: Boolean): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var taskResult: Int = 0
        var query: String = ""
        if (type)
            query = "$KEY_FIELD1=?"
        else
            query = "$KEY_FIELD2=?"
        try {
            taskResult = db.delete(TABLE_TASK_NAME, query, arrayOf(input1))
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
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
        private const val TABLE_TASK_NAME = "task_name"
        private const val KEY_FIELD1 = "field1"
        private const val KEY_FIELD2 = "field2"
        private const val KEY_FIELD3 = "field3"
        private const val KEY_FIELD4 = "field4"
        private const val KEY_FIELD5 = "field5"
        private const val KEY_FIELD6 = "field6"
    }
}

class NameLogoRecord(u: String, v: String, w: String, x: String, y: String, z: String) {
    val Title: String = u
    val Exchange: String = v
    var Name: String = w
    var Sector: String = x
    var Logo: String = y
    var Meta: String = z
}