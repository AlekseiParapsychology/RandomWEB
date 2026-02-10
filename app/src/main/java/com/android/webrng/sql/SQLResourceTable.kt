package com.android.webrng.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TableLayout
import com.android.webrng.R
import com.android.webrng.utils.CSVFile
import com.android.webrng.utils.UtcCalendar
import java.util.*
import kotlin.math.roundToInt


class SQLResourceTable(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            val CREATE_RESOURCE_TABLE = "CREATE TABLE resource ( " +
                    "name TEXT PRIMARY KEY, " +
                    "value TEXT )"
            db.execSQL(CREATE_RESOURCE_TABLE)
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
            db.execSQL("DROP TABLE IF EXISTS resource")
            verifyTask = true
        }
        catch (e: Exception) { }
        if (verifyTask)
            onCreate(db)
        db.closeSafe()
    }

    fun addRecord(name: String, value: String): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        try {
            val values = ContentValues()
            values.put(KEY_NAME, name)
            values.put(KEY_VALUE, value)
            db.insertWithOnConflict(TABLE_RESOURCE, null, values, SQLiteDatabase.CONFLICT_REPLACE)
            db.closeSafe()
            //   Log.i("addResource", name)
            return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    fun updateRecord(name: String, value: String): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }
        try {
            val values = ContentValues()
            values.put(KEY_VALUE, value)
            db.update(TABLE_RESOURCE, values, "$KEY_NAME = ?", arrayOf(name))
            db.closeSafe()
            //    Log.i("updateResource", name)
            return true
        }
        catch (e: Exception) {
            db.closeSafe()
            return false
        }
    }

    val allRecords: HashMap<String, String>
        get() {
            val outputList: HashMap<String, String> = HashMap<String, String>()
            //    val calTimestamp: Long = UtcCalendar.getInstance().timeInMillis
            var db: SQLiteDatabase
            try {
                db = this.readableDatabase
            }
            catch (e: Exception) {
                return hashMapOf()
            }

            var cursor: Cursor? = null
            try {
                val query = "SELECT * FROM $TABLE_RESOURCE"
                cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    do {
                        outputList[cursor.getString(0)] = cursor.getString(1)
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
            return outputList
        }

    fun getRecord(name: String): String {
        var outputValue: String = ""
        val calTimestamp: Long = UtcCalendar.getInstance().timeInMillis
        var db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: Exception) {
            return outputValue
        }

        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM $TABLE_RESOURCE WHERE name = \"$name\""
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    outputValue = cursor.getString(1)
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
        return outputValue
    }

    fun inputRecord(task: CSVFile, name: String) {
        //    Log.i("getSettings", name)
        //    try {
        val setContent = android.app.Activity::class.java.getMethod(name, View::class.java)
        val rlMain = RelativeLayout(task.getFileResource())
        rlMain.setBackgroundColor(Color.parseColor("#A0FFB0"))

        val tableInput = RelativeLayout(task.getFileResource())
        tableInput.id = View.generateViewId()
        tableInput.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, parseString(task, 570))
        tableInput.gravity = Gravity.CENTER
        tableInput.setBackgroundColor(Color.parseColor("#A0FFB0"))
        task.getFileResource().layoutInflater.inflate(R.layout.place_autocomplete_progress, tableInput)
        rlMain.addView(tableInput)

        val buttonArea = RelativeLayout(task.getFileResource())
        buttonArea.id = View.generateViewId()
        val buttonAreaParameters = RelativeLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, parseString(task, 25))
        buttonAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        buttonArea.layoutParams = buttonAreaParameters
        buttonArea.setBackgroundColor(Color.parseColor("#A0FFB0"))
        task.getFileResource().layoutInflater.inflate(R.layout.select_dialog_item_material, buttonArea)
        rlMain.addView(buttonArea)

        val numberPicker = RelativeLayout(task.getFileResource())
        numberPicker.id = View.generateViewId()
        val numberPickerParameters = RelativeLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, parseString(task, 35))
        numberPickerParameters.bottomMargin = 5
        numberPickerParameters.addRule(RelativeLayout.ABOVE, buttonArea.id)
        numberPicker.layoutParams = numberPickerParameters
        numberPicker.setBackgroundColor(Color.parseColor("#A0FFB0"))
        task.getFileResource().layoutInflater.inflate(R.layout.abc_activity_chooser_view_list_item, numberPicker)
        rlMain.addView(numberPicker)

        val imageArea2 = RelativeLayout(task.getFileResource())
        imageArea2.id = View.generateViewId()
        val imageArea2Parameters = RelativeLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, parseString(task, 35))
        imageArea2Parameters.bottomMargin = 5
        imageArea2Parameters.addRule(RelativeLayout.ABOVE, numberPicker.id)
        imageArea2Parameters.addRule(RelativeLayout.BELOW, tableInput.id)
        imageArea2.layoutParams = imageArea2Parameters
        task.getFileResource().layoutInflater.inflate(R.layout.abc_search_view, imageArea2)
        rlMain.addView(imageArea2)

        setContent(task.getFileResource(), rlMain)
        //    Log.i("INFLATE", "OK")
        //    }
        //    catch (e: Exception) {
        //    Log.i("ERROR", "${e.toString()}/${e.message.toString()}")
        //    }
    }

    private fun parseString(task: CSVFile, value: Int) : Int {
        return (value * task.getFileResource().resources.displayMetrics.density).roundToInt()
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
                val query1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='${SQLResourceTable.TABLE_RESOURCE}';"
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
                        val query2 = "SELECT  * FROM ${SQLResourceTable.TABLE_RESOURCE} LIMIT 1"
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

    fun deleteRecord(name: String): Boolean {
        var db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch (e: Exception) {
            return false
        }

        var taskResult: Int = 0
        try {
            taskResult = db.delete(TABLE_RESOURCE, "$KEY_NAME = ?", arrayOf(name))
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
        private const val TABLE_RESOURCE = "resource"
        private const val KEY_NAME = "name"
        private const val KEY_VALUE = "value"
    }
}