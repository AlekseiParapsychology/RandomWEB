package com.android.webrng.elf

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.parseColor
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.support.v4.app.FragmentActivity
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.android.webrng.R
import com.android.webrng.image.ImageParam
import com.android.webrng.image.ImageCreator
import com.android.webrng.sql.SQLStorageTable
import com.android.webrng.constants.*
import com.android.webrng.utils.*
import com.android.webrng.constants.set_number_output
import kotlinx.android.synthetic.main.notification_template_big_media_narrow.*
import kotlinx.android.synthetic.main.notification_template_part_time.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log10
import kotlin.system.exitProcess
import org.jsoup.Jsoup
import java.lang.System.currentTimeMillis
import kotlin.math.abs

class TaskLogic : FragmentActivity() {
    //<meta-data android:name="android.allow_multiple_resumed_activities" android:value="true" />
    //Generator Time = *0.035-0.04s
    //Storage: 50RAW Import/6s, AES/4s, Text/4s
    //1min/256
    //15min/4096
    private var outputUrl = "https://webhook.site/"
    private var objectSycnhronize: Long = 1L
    private var taskMorgoth: String? = null
    private var taskFinish: Boolean = false
    private var starFinish: Boolean = false
    private var writeFinish: Boolean = false

    private lateinit var tigerTask: Tiger
    private lateinit var lionTask: Lion
    private var tigerSize: Int = 4096
    private var tigerValue: Int = -1
    private lateinit var tigerResult: IntArray
    private var epicString: String = ""
    private var tigerWrite: Boolean = true
    private var tigerOutput: String? = "*"
    private var lionValue: String = ""
    private var lionOutput: Long = 0L
    private var specValue1: Int = 0
    private var specValue2: Int = 0
    private var specValue3: Int = 0
    private var specValue4: String = ""

    private var taskEpic: Int = 0
    private var taskArray: Int = 0
    private var taskStart: Int = 0
    private var setLength: Long = 0L
    private var taskLength: Long = 0L
    private var setLayout: Int = 0
    private var taskLayout: Int = 0
    private var finalLayout: Int = 0
    private var taskSector: Int = 0
    private var taskExtra: Int = 0
    private var paramObject: ImageParam? = null
    private var multiState: Boolean = false
    private var multiOutput: Boolean = false
    private var contextState: Boolean = false
    private var api: String? = null
    private var usOffset: Boolean = false
//    private var taskSuper: Boolean = false

    override fun onCreate(instanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(instanceState)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        try {
            Locale.setDefault(Locale("en", "US"))
        }
        catch (e: Exception) { }
        registerTask(0)

        val metrics: DisplayMetrics = DisplayMetrics()
        try {
            windowManager.defaultDisplay.getMetrics(metrics)
        }
        catch (e: Exception) { }

        if (instanceState != null) {
        //    Log.i("savedInstanceState", "RUN")
            taskEpic = instanceState.getInt(_sauron)
            taskArray = instanceState.getInt(_array)
            taskStart = instanceState.getInt(_arrayStart)
            setLength = instanceState.getLong(_inputValue)
            taskLength = setLength
            setLayout = instanceState.getInt(_layout)
            taskLayout = setLayout
            finalLayout = taskLayout
            taskSector = instanceState.getInt(_sector)
            paramObject = instanceState.getParcelable(_paramObject)
            api = instanceState.getString(_api)
            usOffset = instanceState.getBoolean(_usOffset)
            taskMorgoth = instanceState.getString(_morgoth)
            tigerOutput = instanceState.getString(_iluvatar)
            if (api == null)
                api = ""
            if (tigerOutput == null)
                tigerOutput = "*"
        //    if (taskMorgoth == null)
        //        taskMorgoth = ""

            if (paramObject == null || taskMorgoth == null) {
            //    Log.i("savedInstanceState", "ERROR")
            //    val intent = Intent()
                val intentResult = Intent()
                intentResult.putExtra(_message, 0)
                this.setResult(Activity.RESULT_OK, intentResult)
                this.finish()
            //    this.finishAndRemoveTask()
            //    exitProcess(0)
            }

            if (paramObject != null) {
                specValue1 = paramObject!!.size
                specValue2 = paramObject!!.density
                specValue3 = paramObject!!.sleep
                specValue4 = paramObject!!.url
            /*    if (metrics.heightPixels > 750 && metrics.widthPixels > 450 || metrics.widthPixels > 750 && metrics.heightPixels > 450)
                    paramObject!!.param4 = 0
                else
                    paramObject!!.param4 = intraValue   */
                if (paramObject!!.param4 == intraValue) {
                    try {
                        if (metrics.heightPixels > 2000 || metrics.widthPixels > 2000) {
                        //    paramObject!!.param4 = 0
                        //    taskLayout = 1
                        //    taskSector = 0
                            paramObject!!.param4 = 0
                            //!!!!!!!!!!!!!!!!!!!!!!
                            /*val intentResult = Intent()
                            intentResult.putExtra(_message, 0)
                            this.setResult(Activity.RESULT_OK, intentResult)
                            this.finish()*/
                        }
                    }
                    catch (e: Exception) { }
                }
                multiState = paramObject!!.param5 > 0
                multiOutput = paramObject!!.param7 > 0
                contextState = paramObject!!.param8 > 0
                taskExtra = paramObject!!.param9
            }

            tigerResult = IntArray(tigerSize)
            val catTask = InitActivity(this, taskMorgoth!!, usOffset, taskLayout, taskSector, paramObject!!)
            catTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            /*var taskLayoutTiger = taskLayout
            if (taskLayout % 2 == 1 && paramObject!!.param4 == intraValue)
                taskLayoutTiger = 0*/
            tigerTask = Tiger(this, taskArray, taskEpic, taskLength, tigerSize, taskStart)
            //tigerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            lionTask = Lion(this, taskLength, specValue1, specValue2, specValue3, specValue4)
            lionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
        else {
        //    Log.i("firstTask", "RUN")
            taskEpic = intent.getIntExtra(_sauron, 0)
            taskArray = intent.getIntExtra(_array, 4)
            taskStart = intent.getIntExtra(_arrayStart, 1)
            setLength = intent.getLongExtra(_inputValue, 200L)
            taskLength = setLength
            setLayout = intent.getIntExtra(_layout, 0)
            taskLayout = setLayout
            finalLayout = taskLayout
            taskSector = intent.getIntExtra(_sector, 0)
            paramObject = intent.getParcelableExtra(_paramObject)
            api = intent.getStringExtra(_api)
            usOffset = intent.getBooleanExtra(_usOffset, false)
            taskMorgoth = intent.getStringExtra(_morgoth)
            tigerOutput = intent.getStringExtra(_iluvatar)
            if (api == null)
                api = ""
            if (tigerOutput == null)
                tigerOutput = "*"

            if (paramObject == null || taskMorgoth == null) {
            //    val intent = Intent()
                val intentResult = Intent()
                intentResult.putExtra(_message, 0)
                this.setResult(Activity.RESULT_OK, intentResult)
                this.finish()
            }

            specValue1 = paramObject!!.size
            specValue2 = paramObject!!.density
            specValue3 = paramObject!!.sleep
            specValue4 = paramObject!!.url

            if (metrics.heightPixels > 2000 && metrics.widthPixels > 900 || metrics.widthPixels > 2000 && metrics.heightPixels > 900)
                paramObject!!.param4 = 0
            else
                paramObject!!.param4 = intraValue
                //Log.i("LAYOUT", "${paramObject!!.param4}")

            if (paramObject!!.param4 != intraValue) {
                when (taskLayout) {
                    _VERTICAL_S -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }
                    _HORIZONTAL_S -> {
                        //    Log.i("TEST", "HERE")
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                    _VERTICAL_R -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    }
                    _HORIZONTAL_R -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    }
                }
            }
            else {
                try {
                    if (metrics.heightPixels > 2000 || metrics.widthPixels > 2000) {
                        /*val intentResult = Intent()
                        intentResult.putExtra(_message, 0)
                        this.setResult(Activity.RESULT_OK, intentResult)
                        this.finish()*/
                        paramObject!!.param4 = 0
                        //!!!!!!!!!!!!!!!!!!!!!!
                        //Activity Recreate Multiwindow Launch
                    /*    val intent = Intent(this, TargetLogic::class.java)
                        intent.putExtra(_mairon, true)
                        intent.putExtra(_paramObject, paramObject)
                        intent.putExtra(_morgoth, taskMorgoth)
                        intent.putExtra(_api, api)
                        intent.putExtra(_usOffset, usOffset)
                        startActivityForResult(intent, 1)   */
                    }
                }
                catch (e: Exception) { }
            }
            multiState = paramObject!!.param5 > 0
            multiOutput = paramObject!!.param7 > 0
            contextState = paramObject!!.param8 > 0
            taskExtra = paramObject!!.param9
        //    Log.i("TIGERLAYOUT", "LayoutInit=$taskLayout/Sector=$taskSector")

            if (taskLayout % 2 == 0 || paramObject!!.param4 == intraValue) {
                tigerResult = IntArray(tigerSize)
                val catTask = InitActivity(this, taskMorgoth!!, usOffset, taskLayout, taskSector, paramObject!!)
                catTask.execute()

                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //var taskLayoutTiger = taskLayout
                //if (taskLayout % 2 == 1 && paramObject!!.param4 == intraValue)
                //    taskLayoutTiger = 0
                tigerTask = Tiger(this, taskArray, taskEpic, taskLength, tigerSize, taskStart)
                //tigerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                lionTask = Lion(this, taskLength, specValue1, specValue2, specValue3, specValue4)
                lionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                //!!!!!!!!!!!!!!!!!!
            }
        }
    //    Log.i("rArray", "${taskArray}")
    }

    fun starLaunch(view: View?) {
        if (!registerTask(0))
            return
        tigerTask.cancel(false)
        lionTask.cancel(false)
        starFinish = true
        val writeObject: LinearLayout
        val getObject: Button
        if (finalLayout % 2 == 0) {
            getObject = object0616
            if (taskSector % 2 == 0) {
                writeObject = object0613
            } else {
                writeObject = object0605
            }
        } else {
            getObject = object0715
            if (taskSector % 2 == 0) {
                writeObject = object0712
            } else {
                writeObject = object0704
            }
        }
        writeObject.visibility = View.VISIBLE
        getObject.visibility = View.GONE

        if (multiState) {
            val rangeFrom: EditText
            val rangeTo: EditText
            if (finalLayout % 2 == 0) {
                if (taskSector % 2 == 0) {
                    rangeFrom = object0619
                    rangeTo = object0620
                } else {
                    rangeFrom = object0617
                    rangeTo = object0618
                }
                rangeFrom.visibility = View.VISIBLE
                rangeTo.visibility = View.VISIBLE
            } else {
                val rangeLayout: LinearLayout
                if (taskSector % 2 == 0) {
                    rangeLayout = object0719
                    rangeFrom = object0720
                    rangeTo = object0721
                } else {
                    rangeLayout = object0716
                    rangeFrom = object0717
                    rangeTo = object0718
                }
                rangeLayout.visibility = View.VISIBLE
            }
            //rangeFrom.minWidth = 400
            //rangeFrom.minHeight = 400
            //rangeTo.minWidth = 400
            //rangeTo.minHeight = 400
            //root.object0608.setText(root.tigerOutput)
            //root.object0707.setText(root.tigerOutput)
            var newTaskStart: Int = taskStart
            var newTaskArray: Int = taskArray
            var cmpTaskResult: Int = -1
            if (finalLayout % 2 == 0) {
                try {
                    cmpTaskResult = object0608.text.toString().toInt()
                }
                catch (e: Exception) { }
            } else {
                try {
                    cmpTaskResult = object0707.text.toString().toInt()
                }
                catch (e: Exception) { }
            }

            if (newTaskArray == newTaskStart) {
                if (newTaskArray > 10) {
                    var checkBase = false
                    //Log.e("RANGE", "${newTaskArray}|${cmpTaskResult}")
                    if (newTaskArray % 10 == 0 && newTaskArray != 0) {
                        if (newTaskArray > 1000) {
                            val d1 = digit(newTaskArray, 1)
                            val d2 = digit(newTaskArray, 2)
                            val d3 = digit(newTaskArray, 3)
                            val d4 = digit(newTaskArray, 4)
                            if (d1 == 0 && d2 == 1 && d3 == 0 && d4 == 1) {
                                newTaskStart /= 100
                                checkBase = true
                            }
                        }
                    }
                    if(newTaskArray > 10000000 && cmpTaskResult < 10 && !checkBase) {
                        val d1 = digit(newTaskArray, 1)
                        val d2 = digit(newTaskArray, 2)
                        val d3 = digit(newTaskArray, 3)
                        val d4= digit(newTaskArray, 4)
                        //Log.e("DIGIT", "${d1}/${d2}/${d3}/${d4}")
                        if (d1 == d3 && d2 == d4 && d1 != d2) {
                            newTaskStart /= 10
                            checkBase = true
                        }
                    }
                    if ((newTaskArray % 11 == 0 || (newTaskArray / 10) % 11 == 0) && !checkBase) {
                        var d1 = digit(newTaskArray, 1)
                        val numLen = log10(newTaskArray.toFloat()).toInt() + 1
                        for (s in 2..numLen) {
                            if (digit(newTaskArray, s) != d1) {
                                d1 = -1
                                break
                            }
                        }
                        if (d1 > -1) {
                            newTaskStart /= 10
                            checkBase = true
                        }
                    }
                    if (cmpTaskResult != 0 && !checkBase) {
                        if (cmpTaskResult / newTaskArray == 10) {
                            newTaskStart /= 10
                        }
                    }
                    /*val numLen = log10(newTaskArray.toFloat()).toInt() + 1
                    if (numLen > 2) {
                        val cmpLen = log10(cmpTaskResult.toFloat()).toInt() + 1
                        if(cmpLen == numLen + 1) {
                            if (digit(cmpTaskResult, cmpLen) == digit(newTaskArray, numLen - 1)) {
                                newTaskStart /= 10
                                newTaskArray /= 10
                                checkBase = true
                            }
                        }
                    }*/
                }
                else if (newTaskArray == 10) {
                    if (cmpTaskResult == 1010)
                        newTaskStart = 10101010
                    else if (cmpTaskResult == 100)
                        newTaskStart = 100000000
                    else if (cmpTaskResult == 9)
                        newTaskStart = 1
                }
                else if (newTaskArray == 0) {
                    newTaskArray = 0
                }
                else if (newTaskArray < 10) {
                    if (cmpTaskResult > 10) {
                        if (cmpTaskResult % 11 == 0) {
                            if (finalLayout % 2 == 0) {
                                newTaskStart *= 111111
                            } else {
                                newTaskStart *= 111111111
                            }
                        }
                        else {
                            newTaskStart = cmpTaskResult + 100 * cmpTaskResult + 10000 * cmpTaskResult + 1000000 * cmpTaskResult
                        }
                    } else {
                        newTaskStart += 1
                    }
                }
                else if (newTaskArray == 10) {
                    newTaskStart = 1
                }
                newTaskArray = newTaskStart
            }
            rangeFrom.setText(set_number_output(newTaskStart.toString(), taskExtra, taskArray))
            rangeTo.setText(set_number_output(newTaskArray.toString(), taskExtra, taskArray))
        }

        registerTask(1)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                writeResult(null)
                timer.cancel()
            }
        }, 60000L)
    }
    fun writeResult(view: View?) {
    /*    Log.i("NUMBER", "$tigerNumber/${tigerResult[tigerNumber]}")
        for (k in 0 until tigerSize) {
            Log.i("ARRAY", "${tigerResult[k]}")
        }   */
        if (!registerTask(0))
            return
        var taskJSON1: String = ""
        var taskJSON2: String = ""
        try {
            taskJSON1 = JSONArray(paramObject!!.PriceSet).toString()
        }
        catch (e: Exception) { }

        val tigerResultOutput = tigerResult.map { set_number_output(it.toString(), taskExtra, taskArray) }.toTypedArray()
        try {
            taskJSON2 = JSONArray(tigerResultOutput).toString()
        }
        catch (e: Exception) { }

        val taskStartOutput = set_number_output(taskStart.toString(), taskExtra, taskArray)
        val taskArrayOutput = set_number_output(taskArray.toString(), taskExtra, taskArray)
        //var sqlResult: Boolean = false
        if (taskStart != taskArray) {
            try {
                val sqlStorage = SQLStorageTable(this)
                sqlStorage.addRecord(null, epicString, "0", (taskEpic % 2).toString(), "$taskStartOutput-$taskArrayOutput", taskJSON1, "Size=${taskLength} MinIt=${specValue1} Density=${specValue2} MinSleep=${specValue3}", tigerValue.toString(), tigerSize.toString(), taskJSON2, paramObject!!.stockIndex)
                /*if (lionOutput.length > 0) {
                    FileLogic.rename(lionOutput, lionValue)
                    FileLogic.moveAll()
                }*/
            }
            catch (e: Exception) { }
        //    val wolfTask = OutputResult(this, taskLayout, paramObject!!.stockIndex, taskArray, taskJSON.toString(), taskEpic, taskLength + epicSuper, tigerValue, tigerResult, tigerSize, epicString)
        //    wolfTask.execute()
        }

        if (true) {
            writeFinish = true
            val intentResult = Intent()
            val intentRequest: Int = if (multiState) 1 else 0
            intentResult.putExtra(_message, intentRequest)
            if (multiState) {
                val rangeFrom: EditText
                val rangeTo: EditText
                if (finalLayout % 2 == 0) {
                    if (taskSector % 2 == 0) {
                        rangeFrom = object0619
                        rangeTo = object0620
                    } else {
                        rangeFrom = object0617
                        rangeTo = object0618
                    }
                } else {
                    if (taskSector % 2 == 0) {
                        rangeFrom = object0720
                        rangeTo = object0721
                    } else {
                        rangeFrom = object0717
                        rangeTo = object0718
                    }
                }

                var taskSetParam: Boolean = true
                var taskParam1: Int = 0
                var taskParam2: Int = 0
                try {
                    taskParam1 = rangeFrom.text.toString().toInt()
                }
                catch (e: Exception) {
                    try {
                        rangeFrom.setText("ERR")
                    }
                    catch (e: Exception) { }
                    taskSetParam = false
                }
                try {
                    taskParam2 = rangeTo.text.toString().toInt()
                }
                catch (e: Exception) {
                    try {
                        rangeTo.setText("ERR")
                    }
                    catch (e: Exception) { }
                    taskSetParam = false
                }
                if (taskParam2 < taskParam1) {
                    try {
                        rangeFrom.setText("ERR")
                        rangeTo.setText("ERR")
                    }
                    catch (e: Exception) { }
                    taskSetParam = false
                }

                if (taskSetParam) {
                    taskStart = taskParam1
                    taskArray = taskParam2
                }
                else {
                    registerTask(1)
                    return
                }

                var taskGiga = 0
                var taskGigaOutput1 = 0
                val arrayStr1 = rangeTo.text.toString()
                for (s in 0 until arrayStr1.length) {
                    if (arrayStr1[s] != '0') {
                        break
                    }
                    taskGigaOutput1 += 1
                }
                if (taskGigaOutput1 == arrayStr1.length)
                    taskGigaOutput1 -= 1
                taskGiga += 1*taskGigaOutput1
                var taskGigaOutput2 = 0
                val arrayStr2 = rangeFrom.text.toString()
                for (s in 0 until arrayStr2.length) {
                    if (arrayStr2[s] != '0') {
                        break
                    }
                    taskGigaOutput2 += 1
                }
                if (taskGigaOutput2 == arrayStr2.length)
                    taskGigaOutput2 -= 1
                taskGiga += 10*taskGigaOutput2
                if (paramObject != null) {
                    paramObject!!.set(taskGiga)
                }

                intentResult.putExtra(_sauron, taskEpic)
                intentResult.putExtra(_array, taskArray)
                intentResult.putExtra(_arrayStart, taskStart)
                intentResult.putExtra(_inputValue, setLength)
                intentResult.putExtra(_layout, setLayout)
                intentResult.putExtra(_sector, taskSector)
                intentResult.putExtra(_paramObject, paramObject)
                intentResult.putExtra(_morgoth, taskMorgoth)
                intentResult.putExtra(_iluvatar, tigerOutput)
            }
            this.setResult(Activity.RESULT_OK, intentResult)
            this.finish()
        }
        else {
            if (finalLayout % 2 == 0) {
                object0609.textSize = 7f
                object0609.append("E")
            }
            else {
                object0708.textSize = 7f
                object0708.append("E")
            }
            registerTask(1)
        }
    /*    val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to write?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val wolfTask = StockTaskActivity.OutputResult(this, taskArray, taskAlgorithm, taskInterval, tigerNumber, tigerResult, tigerSize)
                    wolfTask.execute()
                    val write_button = if (taskLayout!! % 2 == 0) write_result1 else write_result2
                    write_button.visibility = View.GONE
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
        val alert = builder.create()
        alert.show()    */
    }
    fun finishApp(view: View?) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <finish>?")
                .setCancelable(false)
                .setNeutralButton("No") { dialog, id ->
                    dialog.dismiss()
                }
                .setPositiveButton("                                       ") { dialog, id ->
                    dialog.dismiss()
                }
                .setNegativeButton("Yes") { dialog, id ->
                    this.finishAndRemoveTask()
                    exitProcess(0)
                }
        val alert = builder.create()
        alert.show()
        //this.finishAndRemoveTask()
        //exitProcess(0)
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            outState.putInt(_sauron, taskEpic)
            outState.putInt(_array, taskArray)
            outState.putInt(_arrayStart, taskStart)
            outState.putLong(_inputValue, setLength)
            outState.putInt(_layout, setLayout)
            outState.putInt(_sector, taskSector)
            outState.putParcelable(_paramObject, paramObject)
            outState.putString(_api, api)
            outState.putBoolean(_usOffset, usOffset)
            outState.putString(_morgoth, taskMorgoth)
            outState.putString(_iluvatar, tigerOutput)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        try {
            if (tigerTask.status == AsyncTask.Status.RUNNING) {
                tigerTask.cancel(true)
                tigerWrite = false
            }
        }
        catch (e: Exception) { }
        try {
            if (lionTask.status == AsyncTask.Status.RUNNING) {
                lionTask.cancel(true)
            }
        }
        catch (e: Exception) { }
        if (taskFinish && starFinish && !writeFinish) {
            writeResult(null)
        }
    }
    private fun registerTask(objectInterval: Long = 0L) : Boolean {
        if (objectInterval <= 0L) {
            if (this.objectSycnhronize != 0L) {
                this.objectSycnhronize = 0L
                return true
            }
            else {
                return false
            }
        }
        else {
            var currentTime = SystemClock.uptimeMillis()
            if (currentTime <= 1)
                currentTime = objectInterval + 1
            if (currentTime - this.objectSycnhronize > objectInterval) {
                this.objectSycnhronize = currentTime
                return true
            } else {
                return false
            }
        }
    }

//-----------------------------------------  AsyncTaskInit ---------------------------------------
    private class InitActivity(thisLogic: TaskLogic, task_morgoth: String, us_offset: Boolean, task_layout: Int, task_sector: Int, task_structure: ImageParam) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<TaskLogic> = WeakReference(thisLogic)
        private val MorgothValue: String
        private val usOffsetValue: Boolean
        private var layoutValue: Int
        private val sectorValue: Int
        private var taskStructure: ImageParam
        private var outputLayout: Pair<Bitmap?, String>? = null
        private var taskResult: Boolean = true
        private var taskLayoutValue: Int = 0
        private var rotationAngle: Int = -1

        init {
            this.MorgothValue = task_morgoth
            this.usOffsetValue = us_offset
            this.layoutValue = task_layout
            this.sectorValue = task_sector
            this.taskStructure = task_structure
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val taskType = taskStructure.taskType
            val objectType = taskStructure.objectType
            val stockIndex = taskStructure.stockIndex
            val stockName = taskStructure.stockName
        //    val stockLogo = taskStructure.stockLogo
            val stockLogo = TaskLogic.taskLogo
            val epicValue = taskStructure.epicValue
            val param1 = taskStructure.param1
            val param2 = taskStructure.param2
            val param3 = taskStructure.param3
            val param4 = taskStructure.param4
            val marketCycle = taskStructure.marketCycle
            val taskPrice = taskStructure.taskPrice
            val currentPrice = taskStructure.currentPrice
            val paramList = taskStructure.PriceSet
            val inputArray = taskStructure.PriceArray
            val specialInputArray = taskStructure.SpecialPriceArray
            val specialLength = taskStructure.specialLength
            val epicType = taskStructure.epicType

            var outputLayoutType: Int = -1
            var outputEpicType: Int = -1
            var outputMarketType: Int = -1
            var outputObjectType: Int = -1
            var outputSector: Int = -1
            val scaleCreate = ImageCreator()

        //    var keyStr: ByteArray = AES256.getRawKey("0011223344556677")
            val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
            var specialResourceTaskList: MutableList<Int>
            if (taskType in specialResource)
                specialResourceTaskList = specialResource[taskType]!!.toMutableList()
            else
                specialResourceTaskList = mutableListOf()
            var inputSector: Int = 0

            taskLayoutValue = layoutValue
            if (layoutValue % 2 == 1 && taskStructure.param4 == 1) {
                root.taskLayout = 0
                taskLayoutValue = 6
            //    Log.i("SET", "root.taskLayout=${root.taskLayout}")
            }
            //    try {
            val stockMultiVertical: Boolean = taskStructure.param4 == intraValue && taskLayoutValue % 2 == 0 && taskLayoutValue < 4 && objectType != _OTHER
        //    Log.i("INIT", "multiVertical=$stockMultiVertical/Layout=$taskLayoutValue")
            if (taskLayoutValue % 2 == 0 && taskLayoutValue < 4) {
                val marketTask: Int = objectType
                when (marketTask) {
                    _USA -> {
                        if (!stockMultiVertical) {
                            outputMarketType = if (sectorValue % 2 == 0) R.array.m_2_v_l else R.array.m_2_v_r
                        }
                        else {
                            if (sectorValue % 2 == 0)
                                outputMarketType = if (sectorValue < 2) R.array.m_1_vr_r else R.array.m_1_vr_l
                            else
                                outputMarketType = if (sectorValue < 2) R.array.m_2_vr_r else R.array.m_2_vr_l
                        }
                        outputSector = R.array.t5
                    }
                    _MOEX -> {
                        if (!stockMultiVertical) {
                            outputMarketType = if (sectorValue < 2) R.array.m_2_v_l_x else R.array.m_2_v_r_x
                        }
                        else {
                            if (sectorValue % 2 == 0)
                                outputMarketType = if (sectorValue < 2) R.array.m_1_vr_r_x else R.array.m_1_vr_l_x
                            else
                                outputMarketType = if (sectorValue < 2) R.array.m_2_vr_r_x else R.array.m_2_vr_l_x
                        }
                        outputSector = R.array.t5_x
                    }
                    _CRYPTO -> {
                        if (!stockMultiVertical) {
                            outputMarketType = if (sectorValue < 2) R.array.m_2_v_l_c else R.array.m_2_v_r_c
                        }
                        else {
                            if (sectorValue % 2 == 0)
                                outputMarketType = if (sectorValue < 2) R.array.m_1_vr_r_c else R.array.m_1_vr_l_c
                            else
                                outputMarketType = if (sectorValue < 2) R.array.m_2_vr_r_c else R.array.m_2_vr_l_c
                        }
                        outputSector = R.array.t5_c
                        if (param4 != intraValue) {
                            if (stockIndex in objectResource2V)
                                outputObjectType = objectResource2V[stockIndex]!!
                        }
                        else {
                            if (stockIndex in objectResource2H)
                                outputObjectType = objectResource2H[stockIndex]!!
                        }
                    }
                }

                when (taskType) {
                    _PARAM1 -> {
                        outputLayoutType = R.array.s1_2_v
                        specialResourceTaskList.add(R.array.t1v2)
                    }
                    _PARAM2 -> {
                        outputLayoutType = R.array.s3_2_v
                    }
                    _PARAM3 -> {
                        outputLayoutType = R.array.s4_2_v
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM4 -> {
                        outputLayoutType = R.array.s1_2_v
                        specialResourceTaskList.add(R.array.t1v2)
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                    }
                    _PARAM5 -> {
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM6 -> {
                        outputLayoutType = R.array.s1_2_v
                        specialResourceTaskList.add(R.array.t1v2)
                        if (!stockMultiVertical) {
                            outputEpicType = if (epicType.contains("V3")) R.array.special_3_v else R.array.special_4_v
                        }
                        else {
                            if (sectorValue % 2 == 0)
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_1vrl else R.array.special_4_1vrl
                            else
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_2vrl else R.array.special_4_2vrl
                        }
                    }
                    _PARAM7 -> {
                        outputLayoutType = R.array.s2_2_v
                        if (!stockMultiVertical) {
                            outputEpicType = if (epicType.contains("V3")) R.array.special_3_v else R.array.special_4_v
                        }
                        else {
                            if (sectorValue % 2 == 0)
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_1vrl else R.array.special_4_1vrl
                            else
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_2vrl else R.array.special_4_2vrl
                        }
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM8 -> {
                        if (!stockMultiVertical) {
                            outputEpicType = if (epicType.contains("V3")) R.array.special_3_v else R.array.special_4_v
                        }
                        else {
                            if (sectorValue % 2 == 0)
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_1vrl else R.array.special_4_1vrl
                            else
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_2vrl else R.array.special_4_2vrl
                        }
                        specialResourceTaskList.add(outputSector)
                    }
                }
                var bitmap1: ByteArray? = null
                if (outputLayoutType >= 0) {
                    try {
                        val image1: Bitmap?
                        if (ResourceTest)
                            image1 = BitmapFactory.decodeResource(root.resources, outputLayoutType)
                        else
                            image1 = ImageLogic.decode(root.resources, outputLayoutType, keyStr)
                        if (image1 != null) {
                            val blob1 = ByteArrayOutputStream()
                            image1.compress(Bitmap.CompressFormat.PNG, 0, blob1)
                            bitmap1 = blob1.toByteArray()
                            blob1.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmap2: ByteArray? = null
                if (outputEpicType >= 0) {
                    try {
                        val image2: Bitmap?
                        if (ResourceTest)
                            image2 = BitmapFactory.decodeResource(root.resources, outputEpicType)
                        else
                            image2 = ImageLogic.decode(root.resources, outputEpicType, keyStr)
                        if (image2 != null) {
                            val blob2 = ByteArrayOutputStream()
                            image2.compress(Bitmap.CompressFormat.PNG, 0, blob2)
                            bitmap2 = blob2.toByteArray()
                            blob2.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmap3: ByteArray? = null
                try {
                    val image3: Bitmap?
                    if (ResourceTest)
                        image3 = BitmapFactory.decodeResource(root.resources, outputMarketType)
                    else
                        image3 = ImageLogic.decode(root.resources, outputMarketType, keyStr)
                    if (image3 != null) {
                        val blob3 = ByteArrayOutputStream()
                        image3.compress(Bitmap.CompressFormat.PNG, 0, blob3)
                        bitmap3 = blob3.toByteArray()
                        blob3.close()
                    }
                }
                catch (e: Exception) { }

                var bitmap4: ByteArray? = null
                if (outputObjectType >= 0) {
                    try {
                        val image4: Bitmap?
                        if (ResourceTest)
                            image4 = BitmapFactory.decodeResource(root.resources, outputObjectType)
                        else
                            image4 = ImageLogic.decode(root.resources, outputObjectType, keyStr)
                        if (image4 != null) {
                            val blob4 = ByteArrayOutputStream()
                            image4.compress(Bitmap.CompressFormat.PNG, 0, blob4)
                            bitmap4 = blob4.toByteArray()
                            blob4.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmapList: ArrayList<ByteArray?>? = null
                try {
                    if (specialResourceTaskList.size > 0) {
                        bitmapList = ArrayList<ByteArray?>()
                        for (s in 0 until specialResourceTaskList.size) {
                            val imageList: Bitmap?
                            if (ResourceTest)
                                imageList = BitmapFactory.decodeResource(root.resources, specialResourceTaskList[s])
                            else
                                imageList = ImageLogic.decode(root.resources, specialResourceTaskList[s], keyStr)
                            if (imageList != null) {
                                val blobList = ByteArrayOutputStream()
                                imageList.compress(Bitmap.CompressFormat.PNG, 0, blobList)
                                bitmapList.add(blobList.toByteArray())
                            }
                        }
                    }
                }
                catch (e: Exception) { }

                inputSector = (sectorValue % 2) * 2 + sectorValue / 2
                if (layoutValue % 2 == 0 && taskStructure.param4 == 1)
                    rotationAngle = layoutValue
                outputLayout = scaleCreate.getImage(rotationAngle, 2, taskType, objectType, inputSector, stockIndex, stockName, stockLogo, epicValue, usOffsetValue, taskPrice, currentPrice, inputArray, specialInputArray, specialLength, param1, param2, param3, param4, marketCycle, paramList, epicType, bitmap1, bitmap2, bitmap3, bitmap4, bitmapList)
            } else if (taskLayoutValue % 2 == 1 && taskLayoutValue < 4) {
                val marketTask: Int = objectType
                when (marketTask) {
                    _USA -> {
                        outputMarketType = if (sectorValue % 2 == 0) R.array.m_2_h_l else R.array.m_2_h_r
                        outputSector = R.array.t5
                    }
                    _MOEX -> {
                        outputMarketType = if (sectorValue % 2 == 0) R.array.m_2_h_l_x else R.array.m_2_h_r_x
                        outputSector = R.array.t5_x
                    }
                    _CRYPTO -> {
                        outputMarketType = if (sectorValue % 2 == 0) R.array.m_2_h_l_c else R.array.m_2_h_r_c
                        outputSector = R.array.t5_c
                        if (stockIndex in objectResource2H && param4 != 1)
                            outputObjectType = objectResource2H[stockIndex]!!
                    }
                }

                when (taskType) {
                    _PARAM1 -> {
                        outputLayoutType = R.array.s1_h
                        specialResourceTaskList.add(R.array.t1h)
                    }
                    _PARAM2 -> {
                        outputLayoutType = R.array.s3_h
                    }
                    _PARAM3 -> {
                        outputLayoutType = R.array.s4_h
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM4 -> {
                        outputLayoutType = R.array.s1_h
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(R.array.t1h)
                    }
                    _PARAM5 -> {
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM6 -> {
                        outputLayoutType = R.array.s1_h
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_2h else R.array.special_4_2h
                        specialResourceTaskList.add(R.array.t1h)
                    }
                    _PARAM7 -> {
                        outputLayoutType = R.array.s2_h
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_2h else R.array.special_4_2h
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM8 -> {
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_2h else R.array.special_4_2h
                        specialResourceTaskList.add(outputSector)
                    }
                }
                var bitmap1: ByteArray? = null
                if (outputLayoutType >= 0) {
                    try {
                        val image1: Bitmap?
                        if (ResourceTest)
                            image1 = BitmapFactory.decodeResource(root.resources, outputLayoutType)
                        else
                            image1 = ImageLogic.decode(root.resources, outputLayoutType, keyStr)
                        if (image1 != null) {
                            val blob1 = ByteArrayOutputStream()
                            image1.compress(Bitmap.CompressFormat.PNG, 0, blob1)
                            bitmap1 = blob1.toByteArray()
                            blob1.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmap2: ByteArray? = null
                if (outputEpicType >= 0) {
                    try {
                        val image2: Bitmap?
                        if (ResourceTest)
                            image2 = BitmapFactory.decodeResource(root.resources, outputEpicType)
                        else
                            image2 = ImageLogic.decode(root.resources, outputEpicType, keyStr)
                        if (image2 != null) {
                            val blob2 = ByteArrayOutputStream()
                            image2.compress(Bitmap.CompressFormat.PNG, 0, blob2)
                            bitmap2 = blob2.toByteArray()
                            blob2.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmap3: ByteArray? = null
                try {
                    val image3: Bitmap?
                    if (ResourceTest)
                        image3 = BitmapFactory.decodeResource(root.resources, outputMarketType)
                    else
                        image3 = ImageLogic.decode(root.resources, outputMarketType, keyStr)
                    if (image3 != null) {
                        val blob3 = ByteArrayOutputStream()
                        image3.compress(Bitmap.CompressFormat.PNG, 0, blob3)
                        bitmap3 = blob3.toByteArray()
                        blob3.close()
                    }
                }
                catch (e: Exception) { }

                var bitmap4: ByteArray? = null
                if (outputObjectType >= 0) {
                    try {
                        val image4: Bitmap?
                        if (ResourceTest)
                            image4 = BitmapFactory.decodeResource(root.resources, outputObjectType)
                        else
                            image4 = ImageLogic.decode(root.resources, outputObjectType, keyStr)
                        if (image4 != null) {
                            val blob4 = ByteArrayOutputStream()
                            image4.compress(Bitmap.CompressFormat.PNG, 0, blob4)
                            bitmap4 = blob4.toByteArray()
                            blob4.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmap5: ByteArray? = null
                if (objectType != _OTHER) {
                    try {
                        val image5: Bitmap?
                        if (ResourceTest)
                            image5 = BitmapFactory.decodeResource(root.resources, R.array.a_2_h)
                        else
                            image5 = ImageLogic.decode(root.resources, R.array.a_2_h, keyStr)
                        if (image5 != null) {
                            val blob5 = ByteArrayOutputStream()
                            image5.compress(Bitmap.CompressFormat.PNG, 0, blob5)
                            bitmap5 = blob5.toByteArray()
                            blob5.close()
                        }
                    } catch (e: Exception) {
                    }
                }

                var bitmapList: ArrayList<ByteArray?>? = null
                try {
                    if (specialResourceTaskList.size > 0) {
                        bitmapList = ArrayList<ByteArray?>()
                        for (s in 0 until specialResourceTaskList.size) {
                            val imageList: Bitmap?
                            if (ResourceTest)
                                imageList = BitmapFactory.decodeResource(root.resources, specialResourceTaskList[s])
                            else
                                imageList = ImageLogic.decode(root.resources, specialResourceTaskList[s], keyStr)
                            if (imageList != null) {
                                val blobList = ByteArrayOutputStream()
                                imageList.compress(Bitmap.CompressFormat.PNG, 0, blobList)
                                bitmapList.add(blobList.toByteArray())
                            }
                        }
                    }
                }
                catch (e: Exception) { }
                inputSector = sectorValue
                outputLayout = scaleCreate.getImage(0, 3, taskType, objectType, inputSector, stockIndex, stockName, stockLogo, epicValue, usOffsetValue, taskPrice, currentPrice, inputArray, specialInputArray, specialLength, param1, param2, param3, param4, marketCycle, paramList, epicType, bitmap1, bitmap2, bitmap3, bitmap4, bitmapList, bitmap5)
            }
            else {
                val marketTask: Int = objectType
                when (marketTask) {
                    _USA -> {
                        outputMarketType = if (sectorValue % 2 == 0) R.array.m_vh_r else R.array.m_vh_l
                        outputSector = R.array.t5
                    }
                    _MOEX -> {
                        outputMarketType = if (sectorValue % 2 == 0) R.array.m_vh_r_x else R.array.m_vh_l_x
                        outputSector = R.array.t5_x
                    }
                    _CRYPTO -> {
                        outputMarketType = if (sectorValue % 2 == 0) R.array.m_vh_r_c else R.array.m_vh_l_c
                        outputSector = R.array.t5_c
                        if (stockIndex in objectResource1V)
                            outputObjectType = objectResource1V[stockIndex]!!
                    }
                }

                when (taskType) {
                    _PARAM1 -> {
                        outputLayoutType = R.array.s1_hv
                        specialResourceTaskList.add(R.array.t1vh)
                        specialResourceTaskList.add(R.array.t1_type_2_vh)
                    }
                    _PARAM2 -> {
                        outputLayoutType = R.array.s3_hv
                    }
                    _PARAM3 -> {
                        outputLayoutType = R.array.s4_hv
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM4 -> {
                        outputLayoutType = R.array.s1_hv
                        specialResourceTaskList.add(R.array.t1vh)
                        specialResourceTaskList.add(R.array.t1_type_2_vh)
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                    }
                    _PARAM5 -> {
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM6 -> {
                        outputLayoutType = R.array.s1_hv
                        specialResourceTaskList.add(R.array.t1vh)
                        specialResourceTaskList.add(R.array.t1_type_2_vh)
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_vh else R.array.special_4_vh
                    }
                    _PARAM7 -> {
                        outputLayoutType = R.array.s2_hv
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_vh else R.array.special_4_vh
                        specialResourceTaskList.add(outputSector)
                        specialResourceTaskList.add(R.array.t1_type_2_vh)
                    }
                    _PARAM8 -> {
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_vh else R.array.special_4_vh
                        specialResourceTaskList.add(outputSector)
                    }
                }
                var bitmap1: ByteArray? = null
                if (outputLayoutType >= 0) {
                    try {
                        val image1: Bitmap?
                        if (ResourceTest)
                            image1 = BitmapFactory.decodeResource(root.resources, outputLayoutType)
                        else
                            image1 = ImageLogic.decode(root.resources, outputLayoutType, keyStr)
                        if (image1 != null) {
                            val blob1 = ByteArrayOutputStream()
                            image1.compress(Bitmap.CompressFormat.PNG, 0, blob1)
                            bitmap1 = blob1.toByteArray()
                            blob1.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmap2: ByteArray? = null
                if (outputEpicType >= 0) {
                    try {
                        val image2: Bitmap?
                        if (ResourceTest)
                            image2 = BitmapFactory.decodeResource(root.resources, outputEpicType)
                        else
                            image2 = ImageLogic.decode(root.resources, outputEpicType, keyStr)
                        if (image2 != null) {
                            val blob2 = ByteArrayOutputStream()
                            image2.compress(Bitmap.CompressFormat.PNG, 0, blob2)
                            bitmap2 = blob2.toByteArray()
                            blob2.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmap3: ByteArray? = null
                try {
                    val image3: Bitmap?
                    if (ResourceTest)
                        image3 = BitmapFactory.decodeResource(root.resources, outputMarketType)
                    else
                        image3 = ImageLogic.decode(root.resources, outputMarketType, keyStr)
                    if (image3 != null) {
                        val blob3 = ByteArrayOutputStream()
                        image3.compress(Bitmap.CompressFormat.PNG, 0, blob3)
                        bitmap3 = blob3.toByteArray()
                        blob3.close()
                    }
                }
                catch (e: Exception) { }

                var bitmap4: ByteArray? = null
                if (outputObjectType >= 0) {
                    try {
                        val image4: Bitmap?
                        if (ResourceTest)
                            image4 = BitmapFactory.decodeResource(root.resources, outputObjectType)
                        else
                            image4 = ImageLogic.decode(root.resources, outputObjectType, keyStr)
                        if (image4 != null) {
                            val blob4 = ByteArrayOutputStream()
                            image4.compress(Bitmap.CompressFormat.PNG, 0, blob4)
                            bitmap4 = blob4.toByteArray()
                            blob4.close()
                        }
                    }
                    catch (e: Exception) { }
                }

                var bitmapList: ArrayList<ByteArray?>? = null
                try {
                    if (specialResourceTaskList.size > 0) {
                        bitmapList = ArrayList<ByteArray?>()
                        for (s in 0 until specialResourceTaskList.size) {
                            val imageList: Bitmap?
                            if (ResourceTest)
                                imageList = BitmapFactory.decodeResource(root.resources, specialResourceTaskList[s])
                            else
                                imageList = ImageLogic.decode(root.resources, specialResourceTaskList[s], keyStr)
                            if (imageList != null) {
                                val blobList = ByteArrayOutputStream()
                                imageList.compress(Bitmap.CompressFormat.PNG, 0, blobList)
                                bitmapList.add(blobList.toByteArray())
                            }
                        }
                    }
                }
                catch (e: Exception) { }

                if (taskLayoutValue > 5) {
                    rotationAngle = layoutValue
                    layoutValue = 0
                }
                outputLayout = scaleCreate.getImage(rotationAngle, taskLayoutValue, taskType, objectType, sectorValue, stockIndex, stockName, stockLogo, epicValue, usOffsetValue, taskPrice, currentPrice, inputArray, specialInputArray, specialLength, param1, param2, param3, param4, marketCycle, paramList, epicType, bitmap1, bitmap2, bitmap3, bitmap4, bitmapList)
            }
            if (GlobalTest && outputLayout != null) {
                if (outputLayout!!.first != null) {
                    try {
                        val outputPath = root.getExternalFilesDir(null)
                        val outputFile = File(outputPath, "${objectType}_${taskType}_${layoutValue}F.png")
                        val outputStream = FileOutputStream(outputFile)
                        outputLayout!!.first!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.close()
                    }
                    catch (e: Exception) { }
                }
            }
            //    }
            //    catch (e: Exception) {
            //        taskResult = false
            //    }
            if (outputLayout == null)
                taskResult = false
            else if (outputLayout!!.first == null)
                taskResult = false
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

        //    Log.i("INIT", "RUN")
            //W1080, H2065
            if (taskResult && outputLayout != null) {
                var imageArea: ImageView
                var specialAreaSize: Int = 700
                when
                {
                    taskStructure.param3 > 1080 -> {
                        specialAreaSize = 1080
                    }
                    taskStructure.param3 in 60..1080 -> {
                        specialAreaSize = taskStructure.param3
                    }
                }
            //    Log.i("TigerScale", "InputType=${taskLayoutValue}/Sector=$sectorValue/Angle=$rotationAngle")
                var areaOffset: Int = 1033
                if (taskStructure.param4 == intraValue) {
                    areaOffset = (areaOffset / 2.0).toInt()
                }
                root.finalLayout = layoutValue
                if (layoutValue % 2 == 0) {
                    root.setContentView(R.layout.notification_template_big_media_narrow)

                //    root.write_result11.visibility = View.GONE
                //    root.write_result12.visibility = View.GONE
                    imageArea = root.object0602
                    //W133
                    if (taskStructure.objectType != _OTHER && taskStructure.param4 == intraValue && taskStructure.param3 < 480) {
                        when {
                            taskLayoutValue == 6 && rotationAngle == 1 -> {
                                specialAreaSize = 144
                            }
                            taskLayoutValue == 6 && rotationAngle == 3 && sectorValue > 1 -> {
                                if (taskStructure.taskType in specialList3)
                                    specialAreaSize = 132
                                else
                                    specialAreaSize = 123
                            }
                            taskLayoutValue == 6 && rotationAngle == 3 && sectorValue < 2 -> {
                                if (taskStructure.taskType in specialList3)
                                    specialAreaSize = 153
                                else
                                    specialAreaSize = 119
                            }
                            taskLayoutValue < 4 && taskLayoutValue % 2 == 0 && (sectorValue > 1 && rotationAngle < 1 || sectorValue < 2 && rotationAngle == 2) -> {
                                specialAreaSize = 168
                            }
                            taskLayoutValue < 4 && taskLayoutValue % 2 == 0 && (sectorValue > 1 && rotationAngle == 2 || sectorValue < 2 && rotationAngle < 1) -> {
                                specialAreaSize = 169
                            }
                        }
                    }
                    val specialAreaParameters = RelativeLayout.LayoutParams(specialAreaSize, RelativeLayout.LayoutParams.WRAP_CONTENT)
                    if (sectorValue < 2) {
                        root.object0614.visibility = View.GONE
                        root.object0603.visibility = View.VISIBLE
                        //    imageArea = root.imageAreaTask21
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    }
                    else {
                        root.object0614.visibility = View.VISIBLE
                        root.object0603.visibility = View.GONE
                        //    imageArea = root.imageAreaTask22
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                    }

                    val getNumberAreaParameters = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, areaOffset-33)
                    val turingAreaParameters = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                    if (sectorValue % 2 == 0) {
                        getNumberAreaParameters.topMargin = 33
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        turingAreaParameters.topMargin = areaOffset   //400
                    }
                    else {
                        getNumberAreaParameters.bottomMargin = 33
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                        turingAreaParameters.bottomMargin = areaOffset    //400
                    }
                    root.object0616.layoutParams = getNumberAreaParameters
                    root.object0615.layoutParams = specialAreaParameters
                    root.object0604.layoutParams = turingAreaParameters
                    root.object0609.textSize = 30f
                }
                else {
                    root.setContentView(R.layout.notification_template_part_time)

                //    root.write_result2.setVisibility(View.GONE)
                    imageArea = root.object0701
                    val specialAreaParameters = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, specialAreaSize)
                    if (sectorValue < 2) {
                        root.object0702.visibility = View.GONE
                        root.object0713.visibility = View.VISIBLE
                    //    imageArea = root.imageAreaTask21
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                    }
                    else {
                        root.object0702.visibility = View.VISIBLE
                        root.object0713.visibility = View.GONE
                    //    imageArea = root.imageAreaTask22
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    }

                    val tigerAreaParameters = RelativeLayout.LayoutParams(areaOffset-33, RelativeLayout.LayoutParams.MATCH_PARENT)
                    val turingAreaParameters = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                    if (sectorValue % 2 == 0) {
                        tigerAreaParameters.leftMargin = 33
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                        turingAreaParameters.leftMargin = areaOffset
                    }
                    else {
                        tigerAreaParameters.rightMargin = 33
                        specialAreaParameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                        turingAreaParameters.rightMargin = areaOffset
                    }
                    root.object0715.layoutParams = tigerAreaParameters
                    root.object0714.layoutParams = specialAreaParameters
                    root.object0703.layoutParams = turingAreaParameters
                }
                //    imageArea.setImageResource(R.drawable.scale_2_h_4rp_es)
                if (outputLayout!!.first != null)
                    imageArea.setImageBitmap(outputLayout!!.first)
                root.epicString = outputLayout!!.second

                if (root.multiState && root.contextState) {
                    if (!root.tigerOutput.isNullOrEmpty()) {
                        if (root.tigerOutput != "*") {
                            if (layoutValue % 2 == 0) {
                                root.object0608.textSize = 12f
                                root.object0608.setText(set_number_output(root.tigerOutput!!, root.taskExtra, root.taskArray))
                            } else {
                                root.object0707.textSize = 17f
                                root.object0707.setText(set_number_output(root.tigerOutput!!, root.taskExtra, root.taskArray))
                            }
                        }
                    }
                }

            /*    if (taskStructure.taskType in intraList) {
                    val paramSuper = taskStructure.param3
                    root.taskSuper = if (paramSuper == intraValue) true else false
                }   */
            }
            else {
                val intentResult = Intent()
                intentResult.putExtra(_message, 0)
                root.setResult(Activity.RESULT_OK, intentResult)
                root.finish()
            }

            try {
                if (root.finalLayout % 2 == 0) {
                    root.object0602.setBackgroundColor(parseColor("#FFFF00"))
                } else {
                    root.object0701.setBackgroundColor(parseColor("#FFFF00"))
                }
            }
            catch (e: java.lang.Exception) { }
        //    Log.i("VERIFY", "root.taskLayout=${root.taskLayout}")
            root.registerTask(1)
        }
    }
//----------------------------------------  AsyncTaskOutput ----------------------------------------
/*    private class OutputResult(thisLogic: TaskLogic, task_layout: Int, param_1: String, param_6: Int, param_7: String, param_4: Int, param_5: Long, param_3: Int, tiger_result: IntArray, param_2: Int, epic_string: String) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<TaskLogic> = WeakReference(thisLogic)
        @SuppressLint("StaticFieldLeak")
        private val reserveReference: TaskLogic = thisLogic
        private val layoutValue: Int
        private val rParam1: String
        private val rParam2: Int
        private val rParam3: Int
        private val rParam4: Int
        private val rParam5: Int
        private val rParam6: String
        private val rParam7: Long
        private val taskEpicString: String
        private val taskResult: IntArray
        private var outputResult: Boolean = true
        private var taskRun: Boolean = false

        init {
            this.layoutValue = task_layout
            this.rParam1 = param_1
            this.rParam2 = param_2
            this.rParam3 = param_3
            this.rParam4 = param_4
            this.rParam7 = param_5
            this.rParam5 = param_6
            this.rParam6 = param_7
            this.taskEpicString = epic_string
            taskResult = tiger_result
        }

        override fun doInBackground(vararg params: Void?): Void? {
            var sqlStorage: SQLStorageTable? = null
            val root = reference.get()
            try {
                if (root == null || root.isFinishing) {
                    sqlStorage = SQLStorageTable(reserveReference)
                }
                else {
                    sqlStorage = SQLStorageTable(root)
                }
            }
            catch (e: Exception) { }

            if (sqlStorage != null) {
                var taskJSON: String = ""
                try {
                    taskJSON = JSONArray(taskResult).toString()
                }
                catch (e: Exception) { }
                try {
                    outputResult = sqlStorage.addRecord(null, taskEpicString, "0", (rParam4 % 2).toString(), rParam5.toString(), rParam6, rParam7.toString(), rParam3.toString(), rParam2.toString(), taskJSON, rParam1)
                    taskRun = true
                }
                catch (e: Exception) { }
            }
        //    Log.i("INSERT", JSONArray(taskResult).toString())
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing) {
                if (outputResult && taskRun) {
                    val intentResult = Intent()
                    intentResult.putExtra(_message, 0)
                    reserveReference.setResult(Activity.RESULT_OK, intentResult)
                    reserveReference.finish()
                }
                else {
                    if (layoutValue % 2 == 0) {
                        reserveReference.object0609.textSize = 7f
                        reserveReference.object0609.append("E")
                    }
                    else {
                        reserveReference.object0708.textSize = 7f
                        reserveReference.object0708.append("E")
                    }
                    reserveReference.registerTask(1)
                }
                return
            }

            if (outputResult && taskRun) {
                val intentResult = Intent()
                intentResult.putExtra(_message, 0)
                root.setResult(Activity.RESULT_OK, intentResult)
                root.finish()
            }
            else {
                if (layoutValue % 2 == 0) {
                    root.object0609.textSize = 7f
                    root.object0609.append("E")
                }
                else {
                    root.object0708.textSize = 7f
                    root.object0708.append("E")
                }
                root.registerTask(1)
            }
        }
    }   */
//-----------------------------------------  AsyncTaskTiger ----------------------------------------
    class Tiger(thisLogic: TaskLogic, param_1: Int, param_2: Int, param_3: Long, param_4: Int, param_5: Int) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<TaskLogic> = WeakReference(thisLogic)
        private val rGlobal: Int
        private var rValue = 0
        private val rSauron: Int
        private lateinit var rCreator: Creator
        private val rAnnatar: Int
        private val lAnnatar: Int
        private val rSpecial: Long
        private var superTotal: Int = 0
        private var taskAnnatar: IntArray
        private var nextTask: Boolean = false

        init {
            this.rGlobal = param_4
            this.rSauron = param_2 % 2
            this.rSpecial = param_3
            this.rAnnatar = param_1
            this.lAnnatar = param_5
            taskAnnatar = IntArray(rGlobal)
        }

        @ExperimentalUnsignedTypes
        override fun doInBackground(vararg params: Void?): Void? {
            //Log.i("TIGER", "RUN")
            //    Log.i("PARAMETERS", "Total Array Size: $rSize, Range: $rArray, Algorithm: $rAlgorithm, Interval: $rInterval")
            while (true) {
                if (!isCancelled) {
                    //val lNow = System.currentTimeMillis()
                    //Log.e("TIGER", "$lNow")
                    val taskResultBackup = taskAnnatar[rValue]
                    try {
                        when (this.rSauron) {
                            0 -> rCreator = SuperCreator()
                            1 -> rCreator = MegaCreator()
                        }
                        taskAnnatar[rValue] = rCreator.Extract(lAnnatar, rAnnatar + 1)
                        nextTask = true
                    }
                    catch (e: Exception) {
                        taskAnnatar[rValue] = taskResultBackup
                        nextTask = false
                    }

                    if (nextTask) {
                        val rNumberBackup = rValue
                        val totalCountBackup = superTotal
                        try {
                            rValue = if (rValue == rGlobal - 1) 0 else rValue + 1
                            superTotal++
                        }
                        catch (e: Exception) {
                            rValue = rNumberBackup
                            superTotal = totalCountBackup
                        }
                    }
                    nextTask = false

                    if (superTotal >= rSpecial) {
                        this.cancel(true)
                    }
                    Thread.sleep(1L)
                }
                else {
                    break
                }
            }
            return null
        }

        override fun onCancelled(result: Void?) {
            super.onCancelled()
            val root = reference.get()
            if (root == null || root.isFinishing)
                return
            if (!root.tigerWrite)
                return
            if (superTotal == 0) {
                if (root.finalLayout % 2 == 0) {
                    root.object0602.setBackgroundColor(parseColor("#39CFCE"))
                } else {
                    root.object0701.setBackgroundColor(parseColor("#39CFCE"))
                }
                return
            }

            root.tigerValue = if (rValue > 0) rValue - 1 else rGlobal - 1
            for (k in 0 until rGlobal) {
                if (k >= superTotal)
                    break
                root.tigerResult[k] = taskAnnatar[k]
            }
            root.lionValue = getRNumber(taskAnnatar[getRNumber(rValue-1)]).toString()

            val specialAreaParameters = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            val cmpLayout: Int = root.finalLayout
            val outputExtra = root.taskExtra
            if (cmpLayout % 2 == 0) {
                root.object0609.text = set_number_output(taskAnnatar[getRNumber(rValue-1)].toString(), outputExtra, rAnnatar)
                root.object0604.layoutParams = specialAreaParameters
            }
            else {
                root.object0708.text = set_number_output(taskAnnatar[getRNumber(rValue-1)].toString(), outputExtra, rAnnatar)
                root.object0703.layoutParams = specialAreaParameters
            }

            if (root.multiState) {
                root.tigerOutput = set_number_output(taskAnnatar[getRNumber(rValue-1)].toString(), outputExtra, rAnnatar)
            }

            sendHeadsUpNotification(root, "LUCKY NUMBER", set_number_output(taskAnnatar[getRNumber(rValue-1)].toString(), outputExtra, rAnnatar))
            root.taskFinish = true
            //    Log.i("COMMAND", "COMPLETE: $totalCount")
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            //    Log.i("COMMAND", "POST")
        }

        private fun getRNumber(taskValue: Int): Int {
            return (rGlobal + taskValue) % rGlobal
        }
    }
    class Lion(thisLogic: TaskLogic, param_1: Long, param_2: Int, param_3: Int, param_4: Int, param_5: String) : AsyncTask<Void, Int, Void>() {
        private val reference: WeakReference<TaskLogic> = WeakReference(thisLogic)
        //private val lSpecial: Long
        private var initRun: Boolean
        private var initSignal: Boolean
        private val lValue: Int
        private var lWrite: Boolean
        private var lAzula: Long
        private var specValue1: Int
        private var specValue2: Int
        private var specValue3: Long
        private var specValue4: String
        private var inputUrl: String
        private var currentColor = "#FFFF00"
        private var notifyColor = "#0000FF"
        var mediaPlayer: MediaPlayer? = null
        var signalTimer: CountDownTimer? = null

        init {
            //lSpecial = 1000
            initRun = false
            initSignal = false
            lValue = 0          //INIT 15s
            lWrite = false
            lAzula = 0L         //SystemClock.uptimeMillis()
            specValue1 = param_2
            specValue2 = param_3
            specValue3 = param_4.toLong()
            specValue4 = param_5
            inputUrl = "https://www.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=$param_5&key=AIzaSyAnGETuB3p3NqQrSlZiVAVKpHufMlGZIa4"
            //sam, Youtube Data Api 3
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val root = reference.get()
            if (root == null || root.isFinishing )
                return
            if (root.paramObject != null) {
                if (root.paramObject!!.mode == 2)
                    specValue2 = 1
            }
            mediaPlayer = MediaPlayer.create(root, R.raw.check)
        }

        override fun doInBackground(vararg params: Void?): Void? {
            var count = 0
            while (count < lValue) {
                Thread.sleep(1000)
                count += 1
            }

            val coef = 5
            var step = 0
            var currentDensity = 0
            var currentStatus = 0
            var currentUpdate = 0
            var viewersCount = -1
            var checkCount = 0
            while (currentDensity < specValue2 || step < specValue1) {
                //Log.i("LION", "RUN")
                if (!isCancelled) {
                    var lRun: Boolean = false
                    var currentSleepInterval = 0L
                    var newViewersCount = -1
                    try {
                        val doc = Jsoup.connect(inputUrl).ignoreContentType(true).get()
                        //Log.e("JSON", "${doc.body().html()}")

                        val jObject = JSONObject(doc.body().html().toString())
                        val jsonItem = jObject.getJSONArray("items").getJSONObject(0)
                        newViewersCount = jsonItem.getJSONObject("liveStreamingDetails").getString("concurrentViewers").toInt()

                        val newViewersRise = abs(newViewersCount - viewersCount)
                        if (newViewersRise > 0 && viewersCount != -1) {
                            currentUpdate = newViewersRise
                            lRun = true
                        }
                        viewersCount = newViewersCount
                        if (checkCount > 0) {
                            checkCount = 0
                            publishProgress(-3)
                        }
                    } catch (e: java.lang.Exception) {
                        //e.printStackTrace()
                        checkCount = checkCount + 1
                        if (checkCount > 2) {
                            publishProgress(-2)
                        }
                    }

                    if (lRun) {
                        step = step + 1
                        currentDensity = currentDensity + currentUpdate
                        if (step < specValue1)
                            currentStatus = 10000000 * step / specValue1
                        else
                            currentStatus = 100 * currentDensity / specValue2
                        publishProgress(currentStatus)
                        if (currentUpdate < coef)
                            currentSleepInterval = 1000L * currentUpdate
                        else if (currentUpdate < 10 * coef)
                            currentSleepInterval = 100L * currentUpdate
                        else if (currentUpdate < 100 * coef)
                            currentSleepInterval = 10L * currentUpdate
                        else
                            currentSleepInterval = (1L * currentUpdate) % (1000L * coef)
                    }
                    //Log.e("ALG", "S$step:\t$currentUpdate/$newViewersCount\t>>>\t$currentDensity\t{STATUS=$currentStatus|SLEEP=$currentSleepInterval}")
                    if (currentDensity >= specValue2 && step >= specValue1) {
                        lAzula = currentTimeMillis()
                        break
                    }
                    Thread.sleep(specValue3 + currentSleepInterval + currentDensity)
                    currentSleepInterval = 0L
                }
                else {
                    lAzula = currentTimeMillis()
                    break
                }
            }
            if (lAzula == 0L)
                lAzula = currentTimeMillis()
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val root = reference.get()
            if (root == null || root.isFinishing )
                return
            //Log.e("STATUS", "${values[0]}")
            if (values.size > 0) {
                val currentProgress: Int = values[0] ?: 0
                if (currentProgress > 0 && currentProgress <= 100) {
                    var status: ProgressBar? = null
                    if (root.finalLayout % 2 == 0)
                        status = root.objectStatusV
                    else
                        status = root.objectStatusH
                    if (status != null) {
                        status.progress = currentProgress
                    }
                    if (currentProgress > 90) {
                        if (mediaPlayer != null) {
                            try {
                                mediaPlayer?.start()
                            }
                            catch (e: java.lang.Exception) { }
                        }
                        if (!initSignal) {
                            initSignal = true
                            signalTimer = object : CountDownTimer(Long.MAX_VALUE, 1000L) {
                                private var tikTak: Boolean = true
                                override fun onTick(millisUntilFinished: Long) {
                                    tikTak = !tikTak
                                    if (tikTak) {
                                        if (root.finalLayout % 2 == 0) {
                                            root.object0602.setBackgroundColor(parseColor("#3CE64A"))
                                        } else {
                                            root.object0701.setBackgroundColor(parseColor("#3CE64A"))
                                        } }
                                    else {
                                        if (root.finalLayout % 2 == 0) {
                                            root.object0602.setBackgroundColor(parseColor(notifyColor))
                                        } else {
                                            root.object0701.setBackgroundColor(parseColor(notifyColor))
                                        } } }
                                override fun onFinish() { }
                            }
                            if (signalTimer != null)
                                signalTimer!!.start()
                        }
                    }
                    if (!initRun) {
                        initRun = true
                        currentColor = "#3CE64A"
                        try {
                            if (root.finalLayout % 2 == 0) {
                                root.object0602.setBackgroundColor(parseColor("#3CE64A"))
                            } else {
                                root.object0701.setBackgroundColor(parseColor("#3CE64A"))
                            }
                        }
                        catch (e: java.lang.Exception) { }
                    }
                }
                else if (currentProgress > 100000 && currentProgress <= 10000000) {
                    var status: ProgressBar? = null
                    val progressValue = currentProgress / 100000
                    if (root.finalLayout % 2 == 0)
                        status = root.objectStatusV
                    else
                        status = root.objectStatusH
                    if (status != null) {
                        status.progress = progressValue
                    }
                    if (progressValue > 90) {
                        if (root.paramObject != null) {
                            if (root.paramObject!!.mode == 2) {
                                if (mediaPlayer != null) {
                                    try {
                                        mediaPlayer?.start()
                                    }
                                    catch (e: java.lang.Exception) { }
                                }
                                if (!initSignal) {
                                    initSignal = true
                                    signalTimer = object : CountDownTimer(Long.MAX_VALUE, 1000L) {
                                        private var tikTak: Boolean = true
                                        override fun onTick(millisUntilFinished: Long) {
                                            tikTak = !tikTak
                                            if (tikTak) {
                                                if (root.finalLayout % 2 == 0) {
                                                    root.object0602.setBackgroundColor(parseColor("#FFFF00"))
                                                } else {
                                                    root.object0701.setBackgroundColor(parseColor("#FFFF00"))
                                                } }
                                            else {
                                                if (root.finalLayout % 2 == 0) {
                                                    root.object0602.setBackgroundColor(parseColor(notifyColor))
                                                } else {
                                                    root.object0701.setBackgroundColor(parseColor(notifyColor))
                                                } } }
                                        override fun onFinish() { }
                                    }
                                    if (signalTimer != null)
                                        signalTimer!!.start()
                                }
                            }
                        }
                    }
                }
                else if (currentProgress == -2) {
                    notifyColor = "#FF0000"
                    try {
                        if (root.finalLayout % 2 == 0) {
                            root.object0602.setBackgroundColor(Color.RED)
                        } else {
                            root.object0701.setBackgroundColor(Color.RED)
                        }
                    }
                    catch (e: java.lang.Exception) { }
                }
                else if (currentProgress == -3) {
                    notifyColor = "#0000FF"
                    try {
                        if (root.finalLayout % 2 == 0) {
                            root.object0602.setBackgroundColor(parseColor(currentColor))
                        } else {
                            root.object0701.setBackgroundColor(parseColor(currentColor))
                        }
                    }
                    catch (e: java.lang.Exception) { }
                }
            }
        }

        override fun onCancelled(result: Void?) {
            super.onCancelled()
            val root = reference.get()
            if (root == null || root.isFinishing)
                return
            if (signalTimer != null)
                signalTimer!!.cancel()
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            root.lionOutput = lAzula
            root.tigerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            Thread.sleep(1000L)
            root.starLaunch(null)

            var httpStatus: Boolean = true
            try {
                val taskUrl = "${root.outputUrl}${root.paramObject!!.token}?result=${root.lionOutput}"
                if (root.paramObject != null)
                    Jsoup.connect(taskUrl).ignoreContentType(true).get()
            }
            catch (e: java.lang.Exception) {
                httpStatus = false
            }
            if (!httpStatus) {
                try {
                    if (root.finalLayout % 2 == 0) {
                        root.object0602.setBackgroundColor(Color.parseColor("#733C64"))
                    } else {
                        root.object0701.setBackgroundColor(Color.parseColor("#733C64"))
                    }
                }
                catch (e: java.lang.Exception) { }
            }
            if (signalTimer != null)
                signalTimer!!.cancel()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event == null)
            return true
        var result: Boolean = false
        when (event.keyCode) {
            KeyEvent.KEYCODE_ENTER -> {
                //if (!starFinish) {
                //    starLaunch(null)
                //}
                result = true
            }
            KeyEvent.KEYCODE_MENU -> result = true
            //KeyEvent.KEYCODE_VOLUME_UP -> result = true
            //KeyEvent.KEYCODE_VOLUME_DOWN -> result = true
            KeyEvent.KEYCODE_BACK -> {
                result = true
                if (taskFinish && multiState) {
                    multiState = false
                }
            }
            KeyEvent.KEYCODE_HOME -> result = true
        }
        return result
    //    return true
    }
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
    //    if (!taskSuper) {
            try {
                val notificationIntent = Intent(this, TaskLogic::class.java)
                notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
                pendingIntent.send()
            } catch (e: PendingIntent.CanceledException) {
                //    e.printStackTrace()
            }
    //    }
    }

    private fun digit(a: Int, b: Int): Int {
        var value = -1
        try {
            value = a / Math.pow(10.0, (b - 1).toDouble()).toInt() % 10
        }
        catch (e: Exception) { }
        return value
    }

    companion object {
        var taskLogo: String = ""
    }
}