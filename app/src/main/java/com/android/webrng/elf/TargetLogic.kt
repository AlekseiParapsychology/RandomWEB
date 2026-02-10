package com.android.webrng.elf

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.support.v4.app.FragmentActivity
import android.util.DisplayMetrics
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.android.webrng.R
import com.android.webrng.constants.*
import com.android.webrng.image.ImageCreator
import com.android.webrng.image.ImageParam
import com.android.webrng.utils.*
import kotlinx.android.synthetic.main.abc_activity_chooser_view.*
import org.jsoup.Jsoup
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.util.*


class TargetLogic : FragmentActivity() {
    private var objectSycnhronize: Long = 1L
    private var taskMorgoth: String? = null
    private var taskSpecial: String? = null
    private var epicMairon: Boolean = false
    private var sectorInit: Boolean = false
    private var layoutType: Int = 0
    private var sectorType: Int = 0
    private var sectorList: ArrayList<String>? = null
    private lateinit var sectorSpinner: Spinner
    private lateinit var sectorAdapter: ArrayAdapter<String>

    private var section1: Boolean = false
    private var section2: Boolean = false
    private var objectType: Int = -1
    private var paramObject: ImageParam? = null
    private var api: String? = null
    private var usOffset: Boolean = false
    private var runLayout: Boolean = false
    private var setLayout: Boolean = false
    private var winParam: Int = 0
    private var greenState: Boolean = false
    private var layoutTransfer: Boolean = false
    private var sauronRange: HashMap<Int, Int> = hashMapOf(_PARAM1 to 4, _PARAM2 to 4, _PARAM3 to 8, _PARAM4 to 4, _PARAM5 to 2, _PARAM6 to 4, _PARAM7 to 8, _PARAM8 to 2)

    override fun onCreate(instanceState: Bundle?) {
        sectorList = arrayListOf()
        super.onCreate(instanceState)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        try {
            Locale.setDefault(Locale("en", "US"))
        }
        catch (e: Exception) { }
        registerTask(0)
        setContentView(R.layout.abc_activity_chooser_view)
        //    Log.i("START", "ACTIVITY")
        /*    val mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val orientationEventListener: OrientationEventListener = object : OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
                override fun onOrientationChanged(orientation: Int) {
                    val display: Display = mWindowManager.defaultDisplay
                    val rotation: Int = display.rotation
                    if (rotation == Surface.ROTATION_0) {
                        Log.i("ROTATION", "0")
                    }
                    if (rotation == Surface.ROTATION_90) {
                        Log.i("ROTATION", "90")
                    }
                    if (rotation == Surface.ROTATION_180) {
                        Log.i("ROTATION", "180")
                    }
                    if (rotation == Surface.ROTATION_270) {
                        Log.i("ROTATION", "270")
                    }
                }
            }
            if (orientationEventListener.canDetectOrientation())
                orientationEventListener.enable()   */

        if (instanceState != null) {
            //    Log.i("INPUT", "CHANGE")
            epicMairon = instanceState.getBoolean(_mairon)
            //    layoutType = instanceState.getInt(_layoutType)
            sectorType = instanceState.getInt(_sectorType)
            paramObject = instanceState.getParcelable(_paramObject)
            taskMorgoth = instanceState.getString(_morgoth)
            taskSpecial = instanceState.getString(_inputValue)
            api = instanceState.getString(_api)
            usOffset = instanceState.getBoolean(_usOffset)
            runLayout = instanceState.getBoolean(_annatar)
            if (taskMorgoth == null)
                taskMorgoth = ""
            if (taskSpecial != null) {
                if (taskSpecial!!.isEmpty()) {
                    taskSpecial = "1"
                }
            }
            else {
                taskSpecial = "1"
            }
            if (api == null)
                api = ""

            if (paramObject == null) {
                val intentResult = Intent()
                intentResult.putExtra(_message, 3)
                this.setResult(Activity.RESULT_OK, intentResult)
                this.finish()
            }
            objectType = paramObject!!.objectType
            object0009.visibility = View.GONE
        //    object0008.setSelection(layoutType)
        }
        else {
            //    Log.i("INPUT", "CREATE")
            epicMairon = intent.getBooleanExtra(_mairon, false)
            //    layoutType = intent.getIntExtra(_layoutType, 0)
            sectorType = intent.getIntExtra(_sectorType, 0)
            paramObject = intent.getParcelableExtra(_paramObject)
            taskMorgoth = intent.getStringExtra(_morgoth)
            taskSpecial = intent.getStringExtra(_inputValue)
            api = intent.getStringExtra(_api)
            usOffset = intent.getBooleanExtra(_usOffset, false)
            runLayout = intent.getBooleanExtra(_annatar, false)
            if (taskMorgoth == null)
                taskMorgoth = ""
            if (taskSpecial != null) {
                if (taskSpecial!!.isEmpty()) {
                    taskSpecial = "1"
                }
            }
            else {
                taskSpecial = "1"
            }
            if (api == null)
                api = ""

            if (paramObject == null) {
                val intentResult = Intent()
                intentResult.putExtra(_message, 3)
                this.setResult(Activity.RESULT_OK, intentResult)
                this.finish()
            }
            objectType = paramObject!!.objectType
        }
//--------------------------------------------------------------------------------------------------IMPORTANT
    //    Log.i("INIT1", "paramObject=${paramObject!!.param4}/runLayout=$runLayout/type=$layoutType/sector=$sectorType")

        var display: Display? = null
        try {
            display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        }
        catch (e: Exception) { }

        if (display != null) {
            val rotation = display.rotation
            if (rotation == Surface.ROTATION_0) {
                //    Log.i("ROTATION", "0")
                layoutType = 0
            }
            if (rotation == Surface.ROTATION_90) {
                //    Log.i("ROTATION", "90")
                layoutType = 1
            }
            if (rotation == Surface.ROTATION_180) {
                //    Log.i("ROTATION", "180")
                layoutType = 2
            }
            if (rotation == Surface.ROTATION_270) {
                //    Log.i("ROTATION", "270")
                layoutType = 3
            }
        }
    //    Log.i("RECREATE", "Layout=$layoutType")

        if (paramObject!!.param4 > 0) {
            /*    when (resources.configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> layoutType = 0
                    Configuration.ORIENTATION_LANDSCAPE -> layoutType = 1
                }   */
            val metrics: DisplayMetrics = DisplayMetrics()
        //    Log.i("TEST", "a=${metrics.widthPixels}/b=${metrics.heightPixels}")
            if (display != null) {
                try {
                    display.getMetrics(metrics)
                }
                catch (e: Exception) { }
            }
            if (getRotate == 1)
                object0016.visibility = View.GONE

        //    Log.i("Receive", "WinParamObject ========== <$winParam/${paramObject!!.param4}>")
            if (metrics.heightPixels == 0 && metrics.widthPixels == 0 || display == null) {
                winParam = intraValue
                paramObject!!.param4 = 0
                sectorType = 0
            }
            else {
                if (paramObject!!.param4 == intraValue) {
                    winParam = intraValue
                } else if (metrics.heightPixels < 420 || metrics.widthPixels < 420) {
                    paramObject!!.param4 = intraValue
                    winParam = intraValue
                } else {
                    //    Log.i("SECTOR", "Height=${metrics.heightPixels}/Width=${metrics.widthPixels}")
                    winParam = specialSectorValue
                    paramObject!!.param4 = intraValue
                    sectorType = 0
                }
                try {
                    //    Log.i("WIN", "ParamObject=${paramObject!!.param4}/epicMairon=$epicMairon")
                    if ((metrics.heightPixels > metrics.widthPixels && metrics.heightPixels > 750 || metrics.widthPixels > metrics.heightPixels && metrics.widthPixels > 750) && !epicMairon) {
                        paramObject!!.param4 = 0
                        epicMairon = false
                        if (winParam == specialSectorValue) {
                            //    Log.i("Sector", "INIT ========== Full Screen")
                            winParam = intraValue
                            //    sectorType = 0
                        }
                        object0016.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                }
            }

        //    if (paramObject!!.param4 == 1 && layoutType % 2 == 1 && sectorType > 1) {
        //        Log.i("HorizontalLayout", "Sector=${sectorType}")
        //        sectorType = 3
        //    }
        //    Log.i("Update", "WinParam/Object ========== <$winParam/${paramObject!!.param4}>")
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //    layoutType = 1
        //    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

//--------------------------------------------------------------------------------------------------IMPORTANT
    //    Log.i("INIT2", "paramObject=${paramObject!!.param4}/runLayout=$runLayout/type=$layoutType/sector=$sectorType")
        //!(paramObject!!.param4 == intraValue && !runLayout)
        if (!(paramObject!!.param4 == intraValue && !runLayout)) {
            val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
            catTask.execute()
        }
        else {
            greenState = true
        //    Log.i("SET1", "greenState")
        }
    }

    fun launchTiger(view: View?) {
        if (!registerTask(0))
            return
        object0008.isEnabled = false
        object0005.isEnabled = false
        object0006.isEnabled = false
        object0007.isEnabled = false

        if (paramObject!!.param4 == intraValue && getRotate == 1) {
            layoutType = 0
            object0002.visibility = View.GONE
            object0016.visibility = View.GONE
            object0008.isEnabled = true
            object0005.isEnabled = true
            object0006.isEnabled = true
            object0007.isEnabled = true
            val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
            catTask.execute()
            return
        }

        val superStr: String = object0007.text.toString()
    //    if (!superStr.matches(".*[.* ].*".toRegex())) {
        if (superStr.matches("^[0-9]+$".toRegex())) {
        //!!!!!!!!!!!!!!!!!
        /*    val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to launch?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->*/
                        if (paramObject!!.param4 == intraValue && getRotate == 1) {
                            //dialog.dismiss()
                            //!!!!!!!!!!!!!!!!!
                            verifyState()
                            //    Log.i("DEVICE", "Auto Rotate is ON")
                            //    Toast.makeText(applicationContext, "Check <auto rotate>", Toast.LENGTH_SHORT).show()
                            object0002.visibility = View.GONE
                            object0016.visibility = View.GONE
                            object0008.isEnabled = true
                            object0005.isEnabled = true
                            object0006.isEnabled = true
                            object0007.isEnabled = true
                            val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
                            catTask.execute()
                        } else {
                            epicTask()
                        }
                    /*}
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        object0002.visibility = View.GONE
                        section1 = false
                        object0008.isEnabled = true
                        object0005.isEnabled = true
                        object0006.isEnabled = true
                        object0007.isEnabled = true
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()*/
            //!!!!!!!!!!!!!!!!!
        }
        else {
            object0002.visibility = View.GONE
            object0008.isEnabled = true
            object0005.isEnabled = true
            object0006.isEnabled = true
            object0007.isEnabled = true
            object0007.setText("")
            registerTask(1)
        }
        //    epicTask()
    }
    private fun epicTask() {
        var taskLayout = object0008.selectedItemPosition.coerceIn(0..3)
        if (taskLayout == 2)
            taskLayout = 3
        val specialType = object0005.selectedItemPosition.coerceIn(0..1)
        var layoutOffset = taskLayout
        if (objectType == _OTHER && setLayout) {
            layoutOffset *= 2
            if (layoutType % 2 != 0)
                layoutOffset += 1
        }
        //    Log.i("LAYOUT", "${paramObject!!.param4}/$layoutType/${object0008.selectedItemPosition}/$layoutOffset")

        var specialSector = object0006.selectedItemPosition.coerceIn(0..3)
        if (paramObject!!.param4 == 1)
            specialSector = (2 * specialSector + sectorType / 2 + 2) % 4
        var specialInputValue: Long = Long.MAX_VALUE
        try {
            val taskString = object0007.text.toString()
            specialInputValue = taskString.toLong()
        }
        catch (e: Exception) {
            object0002.visibility = View.GONE
            object0008.isEnabled = true
            object0005.isEnabled = true
            object0006.isEnabled = true
            object0007.isEnabled = true
            object0007.setText("")
            registerTask(1)
            return
        }

        val intentLaunch = Intent()
        intentLaunch.putExtra(_message, 1)
        intentLaunch.putExtra(_sauron, specialType)

        var taskArray: Int = 0
        var taskStart: Int = 0
        taskArray = paramObject!!.param1
        taskStart = paramObject!!.param6
        if (taskArray < 0) {
            taskArray = 0
        }
        if (taskStart < 0) {
            taskStart = 0
        }
        if (taskArray < taskStart) {
            taskArray = taskStart
        }

        intentLaunch.putExtra(_array, taskArray)
        intentLaunch.putExtra(_arrayStart, taskStart)
        intentLaunch.putExtra(_inputValue, specialInputValue)
        intentLaunch.putExtra(_layout, layoutOffset)
        intentLaunch.putExtra(_sector, specialSector)
        intentLaunch.putExtra(_paramObject, paramObject)
        intentLaunch.putExtra(_usOffset, usOffset)
        intentLaunch.putExtra(_morgoth, taskMorgoth)
        this.setResult(Activity.RESULT_OK, intentLaunch)
        this.finish()
    }
    fun changeLayout(view: View?) {
        if (!registerTask(4000))
            return
        verifyState()
        object0012.isEnabled = false
        object0013.isEnabled = false
        val taskLayout = object0012.selectedItemPosition
        var taskSector = object0013.selectedItemPosition

        var display: Display? = null
        try {
            display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        }
        catch (e: Exception) { }
        val metrics: DisplayMetrics = DisplayMetrics()
        if (display != null) {
            try {
                display.getMetrics(metrics)
            }
            catch (e: Exception) { }
        }
        if (metrics.heightPixels == 0 && metrics.widthPixels == 0 || display == null) {
            greenState = false
            runLayout = true
            object0012.isEnabled = true
            object0013.isEnabled = true
            object0016.visibility = View.GONE
            return
        }

    //    val stockV = paramObject!!.param4 == intraValue && Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 0
        val stockV = paramObject!!.param4 == intraValue && metrics.widthPixels > metrics.heightPixels
        val stockHM = paramObject!!.param4 == intraValue && getRotate == 1 && metrics.heightPixels > metrics.widthPixels
        val stockOther = paramObject!!.param4 != intraValue && objectType == _OTHER
        if (stockV || stockHM)
            taskSector = (if (sectorType < 2) 0 else 2) + taskSector
        else if (!stockOther)
            taskSector = 2 * taskSector
        var layoutOffset = taskLayout
        if (objectType == _OTHER && setLayout) {
            layoutOffset *= 2
            if (layoutType % 2 != 0)
                layoutOffset += 1
        }
    //    Log.i("SPECIAL", "stockV=$stockV/stockHM=$stockHM")

        val winFull = metrics.heightPixels > metrics.widthPixels && metrics.heightPixels > 750 || metrics.widthPixels > metrics.heightPixels && metrics.widthPixels > 750
//--------------------------------------------------------------------------------------------------IMPORTANT
    //    Log.i("CHECK", "PObject=${paramObject!!.param4}/Win=$winParam/Layout=$layoutType/TaskL=$layoutOffset/Sector=$sectorType/TaskS=$taskSector")
        if (winParam > 0) {
            if (paramObject!!.param4 == intraValue && layoutType > 0) {
                //    val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                if (getRotate == 1) {
                    if (!(metrics.heightPixels > metrics.widthPixels)) {
                        //    Log.i("RECREATE", "SCALE1")
                        greenState = false
                    //    Log.i("CLEAR1", "greenState")
                        layoutType = 0
                        sectorType -= sectorType % 2
                        object0012.isEnabled = true
                        object0013.isEnabled = true
                        object0016.visibility = View.GONE
                        val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
                        catTask.execute()
                        return
                    }
                    else {
                        layoutOffset = layoutType
                    }
                }
            }
            /*    if (paramObject!!.param4 == intraValue && layoutOffset != layoutType && Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
                    val catTask = InitActivity(this, taskMorgoth!!, usOffset, 0, sectorType, paramObject!!)
                    catTask.execute()
                    object0012.isEnabled = true
                    object0013.isEnabled = true
                    object0016.visibility = View.GONE
                    return
                }   */

            runLayout = false
            //UPDATE03062021!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //IF layout...
            if (layoutOffset == layoutType && taskSector == sectorType || paramObject!!.param4 == intraValue && getRotate == 0) {
                greenState = true
            //    Log.i("SET2", "greenState")
                object0001.setImageResource(0)
                object0002.visibility = View.GONE
                object0009.visibility = View.GONE
                sauron_layout2.visibility = View.GONE
            }
            /*    else if (paramObject!!.param4 == intraValue && Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 0) {
                    Log.i("METRICS", "${metrics.heightPixels}")
                    if (metrics.heightPixels < 400)
                        sectorType = 2
                    layoutType = layoutOffset
                    object0012.isEnabled = true
                    object0013.isEnabled = true
                    val catTask = InitActivity(this, taskMorgoth!!, usOffset, layoutType, sectorType, paramObject!!)
                    catTask.execute()
                    return
                }   */
            //    Log.i("MULTI", "WINDOW")
        }

        if (winParam > 0 && !winFull && getRotate == 1)
            object0016.visibility = View.GONE
        if (winParam > 0 && (winFull || getRotate == 0))
            object0016.visibility = View.VISIBLE

        val srcLayout = layoutType
        val srcSector = sectorType
        layoutType = layoutOffset
        sectorType = taskSector
//--------------------------------------------------------------------------------------------------IMPORTANT
    //    Log.i("LAYOUTSECTOR", "TaskL=$layoutType/SrcL=$srcLayout/TaskR=$sectorType/SrcR=$srcSector")

        val winEqualFull = srcLayout != layoutType && srcLayout % 2 == layoutType % 2 && winFull
        val winEqualHV = srcLayout != layoutType && srcLayout % 2 == layoutType % 2 && getRotate == 1
        val winState = paramObject!!.param4 == intraValue
        if (winParam > 0 && paramObject != null && !(winEqualFull || winEqualHV)) {
            //    Log.i("SET", "ParamObject")
            paramObject!!.param4 = intraValue
        }
        if (winEqualFull || winEqualHV)
            object0009.visibility = View.GONE
//--------------------------------------------------------------------------------------------------IMPORTANT
    //    Log.i("ParamOjbect", "${paramObject!!.param4}")
        //!(paramObject!!.param4 == intraValue && Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 0)

        val rotation = display.rotation
        if (paramObject!!.param4 == intraValue && (layoutType > 0 || sectorType % 2 > 0) && !winFull && rotation == Surface.ROTATION_0 && getRotate == 1) {
            //    Log.i("RECREATE", "SCALE2")
            greenState = false
        //    Log.i("CLEAR2", "greenState")
            runLayout = true
            layoutType = 0
            sectorType -= sectorType % 2
            object0016.visibility = View.GONE
            val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
            catTask.execute()
            return
        }

        val stockHV = winState && rotation == Surface.ROTATION_0 && getRotate == 0
        if ((srcLayout != layoutType || srcSector != sectorType) && !winFull && (stockHV || stockHM)) {
            greenState = false
        //    Log.i("CLEAR3", "greenState")
            runLayout = true
            val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
            catTask.execute()
            return
        }
        else if (srcLayout != layoutType) {
//--------------------------------------------------------------------------------------------------IMPORTANT
        //    Log.i("CHANGE","LAYOUT")
            //    if (winParam)
            //        runLayout = false
            if (srcLayout % 2 != layoutType % 2)
                layoutTransfer = true
            when (layoutType) {
                _VERTICAL_S -> {
                    //    Log.i("LAYOUT", "VERTICAL_1, " + layoutType.toString())
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
                _HORIZONTAL_S -> {
                    //    Log.i("LAYOUT", "HORIZONTAL_1, " + layoutType.toString())
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
                _VERTICAL_R -> {
                    //    Log.i("LAYOUT", "VERTICAL_2, " + layoutType.toString())
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                }
                _HORIZONTAL_R -> {
                    //    Log.i("LAYOUT", "HORIZONTAL_2, " + layoutType.toString())
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                }
            }
            if (winEqualFull && srcSector != sectorType) {
                greenState = false
//--------------------------------------------------------------------------------------------------IMPORTANT
            //    Log.i("CLEAR4", "greenState")
                runLayout = true
                paramObject!!.param4 = 0
                val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
                catTask.execute()
                return
            }
        }
        else if (srcSector != sectorType) {
            //    Log.i("TaskSector", "TaskR=$sectorType/SrcR=$srcSector")
            /*    if (paramObject!!.param4 == intraValue && !winFull && rotation == Surface.ROTATION_0 && Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 0) {
                    if (Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
                        Log.i("RECREATE", "SCALE2")
                        layoutType = 0
                        object0012.isEnabled = true
                        object0013.isEnabled = true
                        object0016.visibility = View.GONE
                        val catTask = InitActivity(this, taskMorgoth!!, usOffset, layoutType, sectorType, paramObject!!)
                        catTask.execute()
                        return
                    }
                        runLayout = true
                        object0012.isEnabled = true
                        object0013.isEnabled = true
                        val catTask = InitActivity(this, taskMorgoth!!, usOffset, layoutType, sectorType, paramObject!!)
                        catTask.execute()
                        return
                //    }
                }   */
            //    else {
            //    sectorType = taskSector

            greenState = false
//--------------------------------------------------------------------------------------------------IMPORTANT
        //    Log.i("RECREATE", "ACTIVITY TaskS=$taskSector/TypeS=$sectorType")
            runLayout = true
            paramObject!!.param4 = 0
            val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
            catTask.execute()
        //    this.recreate()
        }
        object0012.isEnabled = true
        object0013.isEnabled = true
        //    if (winParam)
        //        runLayout = false
    }
    fun goToInput(view: View?) {
        if (!registerTask(0))
            return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <exit>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val intentResult = Intent()
                    intentResult.putExtra(_message, 0)
                    this.setResult(Activity.RESULT_OK, intentResult)
                    this.finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    object0009.visibility = View.GONE
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun showSection1(view: View?) {
        //    if (!registerTask(1000))
        //        return
        /*    if (paramObject!!.param4 == intraValue && layoutType > 0){
                Toast.makeText(applicationContext, "Check <orientation>", Toast.LENGTH_SHORT).show()
                return
            }   */

        if (section1) {
            object0002.visibility = View.GONE
            section1 = false
        }
        else {
            object0002.visibility = View.VISIBLE
            if (section2) {
                object0009.visibility = View.GONE
            }
            section1 = true
        }
    }
    fun showSection2(view: View?) {
        //    if (!registerTask(1000))
        //        return
        if (section2) {
            object0009.visibility = View.GONE
            section2 = false
        }
        else {
            object0009.visibility = View.VISIBLE
            if (section1) {
                object0002.visibility = View.GONE
            }
            section2 = true
        }
    }
    /*    fun checkStack() : Boolean {
            val am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val runningAppProcessInfo = am.runningAppProcesses
            for (s in runningAppProcessInfo.indices) {
                Log.i("APP", "${runningAppProcessInfo[s].processName}/${runningAppProcessInfo[s].importance}/${runningAppProcessInfo[s]}")
            //    if (runningAppProcessInfo[s].processName == "com.the.app.you.are.looking.for") {
            //    }
            }
            return false
        /*    val mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val taskList = mngr.getRunningTasks(10)
            Log.i("STACK", "${taskList[0].numActivities}/${taskList[0].topActivity.className}")
            if (taskList[0].numActivities == 1 && taskList[0].topActivity.className == this.javaClass.name) {
                return true
            }
            else {
                return false
            }   */
        }   */
    private val getRotate: Int
        get() {
            return Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0)
        }
    private fun verifyState() {
        if (paramObject != null) {
            val metrics: DisplayMetrics = DisplayMetrics()
            try {
                windowManager.defaultDisplay.getMetrics(metrics)
            }
            catch (e: Exception) { }

            //winParam
            if (!(metrics.heightPixels == 0 && metrics.widthPixels == 0)) {
                if (metrics.heightPixels < 420 || metrics.widthPixels < 420) {
                    paramObject!!.param4 = intraValue
                }
                else {
                    paramObject!!.param4 = 0
                }
            }
            if (winParam > 0)
                winParam = intraValue
        //    Log.i("VERIFY", "ParamObject=${paramObject!!.param4}/H=${metrics.heightPixels}/W=${metrics.widthPixels}")
        }
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    //    verifyState()
        if (outState != null) {
            val metrics: DisplayMetrics = DisplayMetrics()
            try {
                windowManager.defaultDisplay.getMetrics(metrics)
            }
            catch (e: Exception) { }

            var verifyResult: Boolean = true
            if (metrics.heightPixels == 0 && metrics.widthPixels == 0) {
                verifyResult = false
            }

            if (winParam > 0 && sectorType > 0 && !layoutTransfer && verifyResult && (metrics.heightPixels < 420 || metrics.widthPixels < 420))
                winParam = specialSectorValue
            if (winParam > 0 && paramObject != null)
                paramObject!!.param4 = winParam
//--------------------------------------------------------------------------------------------------IMPORTANT
            //    Log.i("ActivityInstance", "LAYOUT ========== <$layoutType/$sectorType>")
            //    Log.i("ActivityInstance", "STATE =========== <$winParam/${paramObject!!.param4}>")
            //    outState.putBoolean(_mairon, epicMairon)
            outState.putInt(_layoutType, layoutType)
            outState.putInt(_sectorType, sectorType)
            outState.putParcelable(_paramObject, paramObject)
            outState.putString(_morgoth, taskMorgoth)
            outState.putString(_api, api)
            outState.putBoolean(_usOffset, usOffset)
            outState.putBoolean(_annatar, runLayout)
            if (greenState)
                outState.putBoolean(_mairon, true)
            //    Log.i("epicMairon", "GREEN/$greenState\t$epicMairon")
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

//----------------------------------------  AsyncTaskInit ----------------------------------------
    private class InitActivity(thisLogic: TargetLogic, task_morgoth: String, task_special: String, us_offset: Boolean, task_layout: Int, task_sector: Int, task_structure: ImageParam) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<TargetLogic> = WeakReference(thisLogic)
        private val MorgothValue: String
        private var epicSpecialValue: String
        private val usOffsetValue: Boolean
        private var layoutValue: Int
        private val sectorValue: Int
        private val taskStructure: ImageParam
        private var outputLayout: Pair<Bitmap?, String>? = null
        private var epicList: Array<String>? = null
        private var taskResult: Boolean = true
        private var taskRun: Boolean = true
        private var rotateImage: Int = -1
        private var httpState: Boolean = true

        init {
            this.MorgothValue = task_morgoth
            this.usOffsetValue = us_offset
            this.layoutValue = task_layout
            this.sectorValue = task_sector
            this.taskStructure = task_structure
            this.epicSpecialValue = task_special
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val root = reference.get()
            if (root == null || root.isFinishing)
                return
            root.sectorInit = false
            rotateImage = 1 - root.getRotate

        //    Log.i("OBJECT", "${taskStructure.objectType}/${taskStructure.taskType}/${layoutValue}/${sectorValue}")
            var initList: Boolean = false
            var taskLayoutList: Array<String> = arrayOf()
            if (taskStructure.param4 == intraValue) {
                try {
                    if (root.getRotate == 0)
                        taskLayoutList = root.resources.getStringArray(R.array.layout_list)
                    else if (root.layoutType in 0..3)
                        taskLayoutList = root.resources.getStringArray(R.array.layout_list).slice(root.layoutType until root.layoutType + 1).toTypedArray()
                }
                catch (e: Exception) { }
                //    Log.i("INTRA", "SIZE=${taskLayoutList.size}")
                initList = true
            }
            else {
                val taskStr = taskStructure.stockIndex
                if (taskStructure.objectType == _OTHER && root.winParam == 0 && taskStr.isNotEmpty()) {
                    if (taskStr[taskStr.length - 1] == 'H') {
                        if (root.layoutType % 2 == 0) {
                            root.layoutType = 1
                            root.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            taskRun = false
                            return
                        }
                        else {
                            //    Log.i("LAYOUT", "HORIZONTAL")
                            val outputList = root.resources.getStringArray(R.array.layout_list)
                            taskLayoutList = arrayOf(outputList[1], outputList[3])
                            initList = true
                            root.setLayout = true
                        }
                    }
                    else if (taskStr[taskStr.length - 1] == 'V') {
                        //    Log.i("LAYOUT", "VERTICAL")
                        val outputList = root.resources.getStringArray(R.array.layout_list)
                        taskLayoutList = arrayOf(outputList[0], outputList[2])
                        initList = true
                        root.setLayout = true
                    }
                }
            }
            //!initList
            if (!initList) {
                taskLayoutList = root.resources.getStringArray(R.array.layout_list)
            }
            val spinner4: Spinner = root.findViewById(R.id.object0008) as Spinner
            val adapterSpinner4 = ArrayAdapter<String>(root,
                    R.layout.abc_popup_menu_item_layout, taskLayoutList)
            adapterSpinner4.setDropDownViewResource(R.layout.abc_popup_menu_header_item_layout)
            spinner4.adapter = adapterSpinner4
            //spinner4.setSelection(1)

            val spinner6: Spinner = root.findViewById(R.id.object0012) as Spinner
            val adapterSpinner6 = ArrayAdapter<String>(root,
                    R.layout.abc_popup_menu_item_layout, taskLayoutList)
            adapterSpinner6.setDropDownViewResource(R.layout.abc_popup_menu_header_item_layout)
            spinner6.adapter = adapterSpinner6
            //spinner6.setSelection(1)

            var taskTypeList1: Array<String>
            if (taskStructure.param4 == 1) {
                try {
                    var outputList: MutableList<String>
                    //    Log.i("SectorValue", "Sector=$sectorValue")
                    if (sectorValue < 2)
                        outputList = root.resources.getStringArray(R.array.sector_list1).slice(0..1).toMutableList()
                    else
                        outputList = root.resources.getStringArray(R.array.sector_list1).slice(2..3).toMutableList()
                    taskTypeList1 = outputList.toTypedArray()
                }
                catch (e: Exception) {
                    taskTypeList1 = root.resources.getStringArray(R.array.sector_list1)
                }
                /*    val inputList = root.resources.getStringArray(R.array.sector_list1)
    var outputList = ArrayList<String>()
    if (root.sectorType == 0) {
        outputList.add(inputList[1])
        outputList.add(inputList[3])
    } else {
        outputList.add(inputList[0])
        outputList.add(inputList[2])
    }
    taskTypeList1 = outputList.toTypedArray()   */
            }
            else {
                taskTypeList1 = root.resources.getStringArray(R.array.sector_list1)
            }
            val spinner7: Spinner = root.findViewById(R.id.object0006) as Spinner
            val adapterSpinner7 = ArrayAdapter<String>(root,
                    R.layout.abc_popup_menu_item_layout, taskTypeList1)
            adapterSpinner7.setDropDownViewResource(R.layout.abc_popup_menu_header_item_layout)
            spinner7.adapter = adapterSpinner7

            var taskTypeList2: Array<String>
        //    val stockMultiHorizontal = taskStructure.param4 == intraValue && rotateImage == 1 && root.layoutType % 2 == 1 && taskStructure.objectType != _OTHER
            //    if stockMultiHorizontal
            if (taskStructure.param4 == 1 || taskStructure.objectType == _OTHER && root.layoutType % 2 != 0) {
                // || taskStructure.objectType == _OTHER
                //    Log.i("CHANGE", "LIST")
                if (sectorValue == 0)
                    taskTypeList2 = root.resources.getStringArray(R.array.sector_list2).slice(0 until 1).toTypedArray()
                else
                    taskTypeList2 = root.resources.getStringArray(R.array.sector_list2).slice(1 until 2).toTypedArray()
            }
            else {
                taskTypeList2 = root.resources.getStringArray(R.array.sector_list2)
            }

            if (root.sectorList == null)
                root.sectorList = ArrayList<String>()
            else
                root.sectorList!!.clear()
            for (item in taskTypeList2)
                root.sectorList!!.add(item)
            //    root.sectorList = taskTypeList2
            root.sectorSpinner = root.findViewById(R.id.object0013) as Spinner
            root.sectorAdapter = ArrayAdapter<String>(root,
                    R.layout.abc_popup_menu_item_layout, root.sectorList)
            root.sectorAdapter.setDropDownViewResource(R.layout.abc_popup_menu_header_item_layout)
            root.sectorSpinner.adapter = root.sectorAdapter

            root.object0012.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                //    Log.i("ItemChange", "$position")
                    if (root.paramObject != null) {
                        root.verifyState()
                        val metrics: DisplayMetrics = DisplayMetrics()
                        try {
                            root.windowManager.defaultDisplay.getMetrics(metrics)
                        }
                        catch (e: Exception) { }

                        val stockHV = root.paramObject!!.param4 == intraValue && root.paramObject!!.objectType != _OTHER && root.getRotate == 0
                        //    Log.i("Metrics", "Width=${metrics.widthPixels}/Height=${metrics.heightPixels}")
                        val stockHM = root.paramObject!!.param4 == intraValue && root.paramObject!!.objectType != _OTHER && root.getRotate == 1 && (metrics.heightPixels > metrics.widthPixels || metrics.heightPixels == 0 && metrics.widthPixels == 0)
                        val stockOther = root.paramObject!!.param4 != intraValue && root.paramObject!!.objectType == _OTHER
                        if (stockHV || stockHM || stockOther) {
                            var taskSectorList: Array<String>? = null
                            if (stockOther) {
                                if (position % 2 == 0)
                                    taskSectorList = root.resources.getStringArray(R.array.sector_list2).slice(0..1).toTypedArray()
                                else
                                    taskSectorList = root.resources.getStringArray(R.array.sector_list2).slice(0 until 1).toTypedArray()
                            }
                            else if (stockHV && position % 2 == 1 || stockHM) {
                                if (root.sectorType < 2)
                                    taskSectorList = root.resources.getStringArray(R.array.sector_list1).slice(0..1).toTypedArray()
                                else
                                    taskSectorList = root.resources.getStringArray(R.array.sector_list1).slice(2..3).toTypedArray()
                            }
                            else {
                                if (root.sectorType < 2)
                                    taskSectorList = root.resources.getStringArray(R.array.sector_list2).slice(0 until 1).toTypedArray()
                                else
                                    taskSectorList = root.resources.getStringArray(R.array.sector_list2).slice(1 until 2).toTypedArray()
                            }

                            if (root.sectorList == null)
                                root.sectorList = ArrayList<String>()
                            else
                                root.sectorList!!.clear()
                            for (item in taskSectorList)
                                root.sectorList!!.add(item)
                            root.sectorAdapter.notifyDataSetChanged()

                            if (!root.sectorInit) {
                                root.sectorInit = true
                                val taskIndex = root.sectorType % 2
                                if (taskIndex < root.sectorSpinner.count)
                                    root.sectorSpinner.setSelection(taskIndex)
                            }
                            //    Log.i("ItemChange", "Root=${root.sectorList!!.size}/Task=${taskSectorList.size}/Spinner=${root.sectorAdapter.count}")
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

            val taskSectorValue = sectorValue / 2
            if (root.sectorList!!.count() > 1 && taskSectorValue < root.sectorSpinner.count)
                root.sectorSpinner.setSelection(taskSectorValue)
            //    Log.i("List", "Size=${root.sectorList!!.count()}/Sector=$sectorValue")
            root.sauron_layout2.visibility = View.VISIBLE
            root.object0009.visibility = View.GONE

            root.object0002.visibility = View.VISIBLE
            root.section1 = true
            if (root.greenState) {
                root.object0002.visibility = View.GONE
                root.section1 = false
            }
            /*    if (root.paramObject!!.taskType in intArrayOf(_PARAM2, _PARAM3)) {
                    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)
                }   */
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing || !taskRun)
                return null

            val taskType = taskStructure.taskType
            val objectType = taskStructure.objectType
            val stockIndex = taskStructure.stockIndex
            val stockName = taskStructure.stockName
        //    val stockLogo = taskStructure.stockLogo
            val stockLogo = TargetLogic.targetLogo
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

            var taskLayoutValue: Int = layoutValue
            if (layoutValue % 2 == 1 && taskStructure.param4 == 1)
                taskLayoutValue = 4 + rotateImage
            val stockMultiVertical: Boolean = taskStructure.param4 == intraValue && taskLayoutValue % 2 == 0 && taskLayoutValue < 4 && objectType != _OTHER
        //    Log.i("Init", "multiVertical=$stockMultiVertical/Layout=$taskLayoutValue")
            //    try {
            if (taskLayoutValue % 2 == 0 && taskLayoutValue < 4) {
                val marketTask: Int = objectType
                when (marketTask) {
                    _USA -> {
                        if (!stockMultiVertical) {
                            outputMarketType = R.array.m_1_v
                            //RESOURECE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            //    outputMarketType = R.array.special_4_vh
                        }
                        else {
                            if (sectorValue < 2)
                                outputMarketType = R.array.m_1_vr_r
                            else
                                outputMarketType = R.array.m_2_vr_r
                        }
                        outputSector = R.array.t5
                    }
                    _MOEX -> {
                        if (!stockMultiVertical) {
                            outputMarketType = R.array.m_1_v_x
                        }
                        else {
                            if (sectorValue < 2)
                                outputMarketType = R.array.m_1_vr_r_x
                            else
                                outputMarketType = R.array.m_2_vr_r_x
                        }
                        outputSector = R.array.t5_x
                    }
                    _CRYPTO -> {
                        if (!stockMultiVertical) {
                            outputMarketType = R.array.m_1_v_c
                        }
                        else {
                            if (sectorValue < 2)
                                outputMarketType = R.array.m_1_vr_r_c
                            else
                                outputMarketType = R.array.m_2_vr_r_c
                        }
                        if (stockIndex in objectResource1V) {
                            //    Log.i("INPUT", "IMAGE")
                            outputSector = R.array.t5_c
                            if (param4 != intraValue)
                                outputObjectType = objectResource1V[stockIndex]!!
                            else
                                outputObjectType = objectResource2H[stockIndex]!!
                        }
                    }
                }

                when (taskType) {
                    _PARAM1 -> {
                        //    outputLayoutType = R.drawable.scale_1_v_4rp_es
                        outputLayoutType = R.array.s1_1_v
                        specialResourceTaskList.add(R.array.t1v1)
                    }
                    _PARAM2 -> {
                        outputLayoutType = R.array.s3_1_v
                    }
                    _PARAM3 -> {
                        outputLayoutType = R.array.s4_1_v
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM4 -> {
                        outputLayoutType = R.array.s1_1_v
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(R.array.t1v1)
                    }
                    _PARAM5 -> {
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM6 -> {
                        outputLayoutType = R.array.s1_1_v
                        if (!stockMultiVertical) {
                            outputEpicType = if (epicType.contains("V3")) R.array.special_3_v else R.array.special_4_v
                        }
                        else {
                            if (sectorValue < 2)
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_1vrl else R.array.special_4_1vrl
                            else
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_2vrl else R.array.special_4_2vrl
                        }
                        specialResourceTaskList.add(R.array.t1v1)
                    }
                    _PARAM7 -> {
                        outputLayoutType = R.array.s2_1_v
                        if (!stockMultiVertical) {
                            outputEpicType = if (epicType.contains("V3")) R.array.special_3_v else R.array.special_4_v
                        }
                        else {
                            if (sectorValue < 2)
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
                            if (sectorValue < 2)
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_1vrl else R.array.special_4_1vrl
                            else
                                outputEpicType = if (epicType.contains("V3")) R.array.special_3_2vrl else R.array.special_4_2vrl
                        }
                        specialResourceTaskList.add(outputSector)
                    }
                }

                //    if (stockMultiVertical) {
                //        specialResourceTaskList
                //    }
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
                            image1.compress(CompressFormat.PNG, 0, blob1)
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
                            image2.compress(CompressFormat.PNG, 0, blob2)
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
                        image3.compress(CompressFormat.PNG, 0, blob3)
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
                            image4.compress(CompressFormat.PNG, 0, blob4)
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
                                imageList.compress(CompressFormat.PNG, 0, blobList)
                                bitmapList.add(blobList.toByteArray())
                            }
                        }
                    }
                }
                catch (e: Exception) { }

                var rotationAngle: Int = -1
                if (layoutValue % 2 == 0 && taskStructure.param4 == 1)
                    rotationAngle = layoutValue
                outputLayout = scaleCreate.getImage(rotationAngle, 0, taskType, objectType, sectorValue, stockIndex, stockName, stockLogo, epicValue, usOffsetValue, taskPrice, currentPrice, inputArray, specialInputArray, specialLength, param1, param2, param3, param4, marketCycle, paramList, epicType, bitmap1, bitmap2, bitmap3, bitmap4, bitmapList)
            } else if (taskLayoutValue % 2 == 1 && taskLayoutValue < 4) {
                val marketTask: Int = objectType
                when (marketTask) {
                    _USA -> {
                        outputMarketType = R.array.m_1_h
                        outputSector = R.array.t5
                    }
                    _MOEX -> {
                        outputMarketType = R.array.m_1_h_x
                        outputSector = R.array.t5_x
                    }
                    _CRYPTO -> {
                        outputMarketType = R.array.m_1_h_c
                        outputSector = R.array.t5_c
                        if (stockIndex in objectResource1H && param4 != 1)
                            outputObjectType = objectResource1H[stockIndex]!!
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
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_1h else R.array.special_4_1h
                        specialResourceTaskList.add(R.array.t1h)
                    }
                    _PARAM7 -> {
                        outputLayoutType = R.array.s2_h
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_1h else R.array.special_4_1h
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM8 -> {
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_1h else R.array.special_4_1h
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
                            image1.compress(CompressFormat.PNG, 0, blob1)
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
                            image2.compress(CompressFormat.PNG, 0, blob2)
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
                        image3.compress(CompressFormat.PNG, 0, blob3)
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
                            image4.compress(CompressFormat.PNG, 0, blob4)
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
                                imageList.compress(CompressFormat.PNG, 0, blobList)
                                bitmapList.add(blobList.toByteArray())
                            }
                        }
                    }
                }
                catch (e: Exception) { }
                outputLayout = scaleCreate.getImage(0, 1, taskType, objectType, sectorValue, stockIndex, stockName, stockLogo, epicValue, usOffsetValue, taskPrice, currentPrice, inputArray, specialInputArray, specialLength, param1, param2, param3, param4, marketCycle, paramList, epicType, bitmap1, bitmap2, bitmap3, bitmap4, bitmapList)
            }
            else {
                val marketTask: Int = objectType
                when (marketTask) {
                    _USA -> {
                        outputMarketType = if (sectorValue == 0 || sectorValue == 3) R.array.m_vh_r else R.array.m_vh_l
                        outputSector = R.array.t5
                    }
                    _MOEX -> {
                        outputMarketType = if (sectorValue == 0 || sectorValue == 3) R.array.m_vh_r_x else R.array.m_vh_l_x
                        outputSector = R.array.t5_x
                    }
                    _CRYPTO -> {
                        outputMarketType = if (sectorValue == 0 || sectorValue == 3) R.array.m_vh_r_c else R.array.m_vh_l_c
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
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(R.array.t1vh)
                        specialResourceTaskList.add(R.array.t1_type_2_vh)
                    }
                    _PARAM5 -> {
                        outputEpicType = if (epicType.contains("V1")) R.array.special_1 else R.array.special_2
                        specialResourceTaskList.add(outputSector)
                    }
                    _PARAM6 -> {
                        outputLayoutType = R.array.s1_hv
                        outputEpicType = if (epicType.contains("V3")) R.array.special_3_vh else R.array.special_4_vh
                        specialResourceTaskList.add(R.array.t1vh)
                        specialResourceTaskList.add(R.array.t1_type_2_vh)
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

                var rotationAngle: Int = -1
                if (taskLayoutValue > 4)
                    rotationAngle = layoutValue
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
            if (epicSpecialValue.contains('^'))
                epicSpecialValue = AES256.toText(epicSpecialValue, keyStr)
            epicList = root.resources.getStringArray(R.array.file_list1)
            for (s in 0 until epicList!!.size)
                epicList!![s] = AES256.toText(epicList!![s], keyStr)

            val inputUrl = "https://www.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=${taskStructure.url}&key=AIzaSyAnGETuB3p3NqQrSlZiVAVKpHufMlGZIa4"
            try {
                val doc = Jsoup.connect(inputUrl).ignoreContentType(true).get()
                if (doc.body().toString().contains("\"totalResults\": 0")) {
                    httpState = false
                }
            } catch (e: java.lang.Exception) {
                httpState = false
            }

            if (outputLayout == null)
                taskResult = false
            else if (outputLayout!!.first == null)
                taskResult = false
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing || !taskRun)
                return

            root.object0007.setText(epicSpecialValue)
            val spinner5: Spinner = root.findViewById(R.id.object0005) as Spinner
            val adapterSpinner5 = ArrayAdapter<String>(root,
                    R.layout.abc_popup_menu_item_layout, epicList)
            adapterSpinner5.setDropDownViewResource(R.layout.abc_popup_menu_header_item_layout)
            spinner5.adapter = adapterSpinner5

            if (taskResult && outputLayout != null) {
                if (layoutValue % 2 == 0) {
                    //    root.imageArea1.setImageResource(R.drawable.task1v)
                    root.object0001.setImageBitmap(outputLayout!!.first)
                } else {
                    //    root.imageArea1.setImageResource(R.drawable.task1h)
                    root.object0001.setImageBitmap(outputLayout!!.first)
                }
            }
            else {
                val intentResult = Intent()
                intentResult.putExtra(_message, 0)
                root.setResult(Activity.RESULT_OK, intentResult)
                root.finish()
            }

            /*    if (taskStructure.taskType in intraList) {
                    val paramSuper = taskStructure.param3
                    root.taskSuper = if (paramSuper == intraValue) true else false
                }   */
            val taskLayoutValue = root.layoutType / 2
            if (root.setLayout && taskLayoutValue < root.object0012.count)
                root.object0012.setSelection(taskLayoutValue)
            else if (root.layoutType < root.object0012.count)
                root.object0012.setSelection(root.layoutType)
            else if (root.object0012.count > 0)
                root.object0012.setSelection(0)
            root.object0012.isEnabled = true
            root.object0013.isEnabled = true
            root.sauron_layout2.visibility = View.VISIBLE
            //if (root.object0008.count > 1 && taskStructure.param4 != 1 && !root.setLayout)
            //    root.object0008.setSelection(1)
            //else if (root.object0008.count > 0)
            //    root.object0008.setSelection(0)
            root.object0006.setSelection(1)
            root.object0008.setSelection(0)

            val rangeFrom = set_number_output(taskStructure.param6.toString(), taskStructure.param9, taskStructure.param6)
            val rangeTo = set_number_output(taskStructure.param1.toString(), taskStructure.param9, taskStructure.param6)
            var token: String = ""
            /*if (taskStructure.token.length > 10) {
                try {
                    token = taskStructure.token.substring(0, 3)
                }
                catch(e: java.lang.Exception) { }
                token = "$token..."
            }*/
            val outputString = "Range\n${rangeFrom}\n${rangeTo}\nContext Size"
            root.inputRangeValue.setText(outputString)
            if (!httpState) {
                root.object0001.setBackgroundColor(Color.RED)
            }

            root.registerTask(1)
        }
        /*    private fun Bitmap.convertToByteArray(): ByteArray {
                val size = this.byteCount
                val buffer = ByteBuffer.allocate(size)
                val bytes = ByteArray(size)
                this.copyPixelsToBuffer(buffer)
                buffer.rewind()
                buffer.get(bytes)
                return bytes
            }   */
    }
//------------------------------------------------------------------------------------------------
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event == null)
            return true
        var result: Boolean = false
        when (event.keyCode) {
            KeyEvent.KEYCODE_MENU -> result = true
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (paramObject != null && taskSpecial != null && taskMorgoth != null) {
                //    Log.i("KEY", "Param4=${paramObject!!.param4}/RunLayout=$runLayout")
                    if (!runLayout && paramObject!!.param4 > 0) {
                        runLayout = true
                        verifyState()
                        try {
                            val metrics: DisplayMetrics = DisplayMetrics()
                            windowManager.defaultDisplay.getMetrics(metrics)

                            if (metrics.heightPixels > metrics.widthPixels && metrics.heightPixels > 750 || metrics.widthPixels > metrics.heightPixels && metrics.widthPixels > 750) {
                                paramObject!!.param4 = 0
                                object0016.visibility = View.VISIBLE
                            }
                            else {
                                if (paramObject!!.param4 == intraValue && sectorType % 2 == 1 && (getRotate == 0 || metrics.heightPixels > metrics.widthPixels))
                                    sectorType = 1
                                else
                                    sectorType = 0
                                if (getRotate == 1) {
                                    object0016.visibility = View.GONE
                                    layoutType = layoutType % 2
                                }
                                //    Log.i("RequestA", "SET=$layoutType")
                            }
                        } catch (e: Exception) {
                        }
                        greenState = false
                        epicMairon = false
                        winParam = intraValue
                    //    Log.i("CLEAR1A", "greenState")
                        //    Log.i("RequestA", "Layout=$layoutType/Sector=$sectorType")
                        val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
                        catTask.execute()
                    }
                }
                result = true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (paramObject != null && taskSpecial != null && taskMorgoth != null) {
                //    Log.i("KEY", "Param4=${paramObject!!.param4}/RunLayout=$runLayout")
                    if (!runLayout && paramObject!!.param4 > 0) {
                        runLayout = true
                        verifyState()
                        try {
                            val metrics: DisplayMetrics = DisplayMetrics()
                            windowManager.defaultDisplay.getMetrics(metrics)

                            if (metrics.heightPixels > metrics.widthPixels && metrics.heightPixels > 750 || metrics.widthPixels > metrics.heightPixels && metrics.widthPixels > 750) {
                                paramObject!!.param4 = 0
                                object0016.visibility = View.VISIBLE
                            }
                            else {
                                if (paramObject!!.param4 == intraValue && sectorType % 2 == 1 && (getRotate == 0 || metrics.heightPixels > metrics.widthPixels))
                                    sectorType = 3
                                else
                                    sectorType = 2
                                if (getRotate == 1) {
                                    object0016.visibility = View.GONE
                                    layoutType = layoutType % 2
                                }
                                //    Log.i("RequestB", "SET=$layoutType")
                            }
                        } catch (e: Exception) {
                        }
                        greenState = false
                        epicMairon = false
                        winParam = intraValue
                    //    Log.i("CLEAR1B", "greenState")
                        //    Log.i("RequestB", "Layout=$layoutType/Sector=$sectorType")
                        val catTask = InitActivity(this, taskMorgoth!!, taskSpecial!!, usOffset, layoutType, sectorType, paramObject!!)
                        catTask.execute()
                    }
                }
                result = true
            }
            KeyEvent.KEYCODE_BACK -> result = true
            KeyEvent.KEYCODE_HOME -> result = true
        }
        return result
    }
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        //    Log.i("KEY", "USER")
        /*    if (paramObject != null) {
                if (!runLayout && paramObject!!.param4 == intraValue) {
                    //    Log.i("HOME", "PRESS")
                    runLayout = true
                    sectorType = 2
                    val catTask = InitActivity(this, layoutType, paramObject!!)
                    catTask.execute()
                }
            }   */
        //    if (!taskSuper) {
        if (paramObject == null)
            return
        if (paramObject!!.param4 > 0 && runLayout)
            verifyState()
    //    Log.i("UserLeaveHint", "Param4=${paramObject!!.param4}/RunLayout=$runLayout")
        if (!(paramObject!!.param4 > 0 && !runLayout)) {
            try {
                val notificationIntent = Intent(this, TargetLogic::class.java)
                notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
                pendingIntent.send()
            } catch (e: PendingIntent.CanceledException) {
                //    e.printStackTrace()
            }
        }
        //    }
    }

    companion object {
        var targetLogo: String = ""
    }
}