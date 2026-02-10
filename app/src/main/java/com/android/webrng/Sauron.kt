package com.android.webrng

import android.annotation.SuppressLint
import android.app.*
import android.content.ClipData
import android.content.Intent
import android.database.CursorWindow
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.support.v4.app.FragmentActivity
import android.text.InputFilter
import android.text.InputFilter.*
import android.util.Base64
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.INVALID_ROW_ID
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.NumberPicker.OnValueChangeListener
import com.android.webrng.constants.*
import com.android.webrng.elf.*
import com.android.webrng.image.DataPoint
import com.android.webrng.image.ImageParam
import com.android.webrng.sql.*
import com.android.webrng.utils.*
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item1
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item2
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item3
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item4
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item5
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item6
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item7
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_item8
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_row
import kotlinx.android.synthetic.main.abc_activity_chooser_view_list_item.sauron_type
import kotlinx.android.synthetic.main.abc_search_view.*
import kotlinx.android.synthetic.main.abc_search_view.sauron_output
import kotlinx.android.synthetic.main.activity_sauron.sauron_input
import kotlinx.android.synthetic.main.activity_sauron.sauron_list
import kotlinx.android.synthetic.main.activity_sauron.sauron_task
import kotlinx.android.synthetic.main.cast_help_text.*
import kotlinx.android.synthetic.main.place_autocomplete_progress.*
import org.json.JSONObject
import java.io.*
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.system.exitProcess

//VERIFY: SAURON, CONTROL, TARGET MEMORY
//SAFETY: TARGET LARGE, Task, ImageCreator

class Sauron : FragmentActivity() {
    private var objectSycnhronize: Long = 1L
    private var taskMorgoth: String = ""
    private var sauronInit = true
    private var sauronGlobal: Boolean = true
    private var graphStart: Boolean = false
    private var maironAnnatar: Boolean = false
    private var activitySleep: Boolean = false
    private var activityTransfer: Boolean = false
    private var activityStart: Long = 0L

    private val checkBase64: String = "Oqm4Ir3oxaMhI5EV66cHbXc9PR6Cj1Q6UeR3kHAQNuWHg5g7EW6wbv_cNHf_3nqE"
    private val stockResource: String = "f7d16fb46b0d948671e7b59e43779a5bda0640f1234ec66616c7c9f4aaeae684"
    private val checkHash: String = "c3b887a5f1a0187fba91a293cf5db5a9a2571884843c51d75a31142ad0089a2f"
    private val epicMap: HashMap<Int, Int> = hashMapOf(0 to _PARAM1, 1 to _PARAM2, 2 to _PARAM3, 3 to _PARAM4, 4 to _PARAM5, 5 to _PARAM6, 6 to _PARAM7, 7 to _PARAM8)
    //AngmarMorgoth

    private lateinit var totalResource: HashMap<String, String>
    private lateinit var adapterSpinner2: ArrayAdapter<String>
    private lateinit var adapterSpinner3: ArrayAdapter<String>
    private lateinit var stock_list: ArrayList<String>
    private lateinit var hash_list: HashMap<String, TaskRecord>
    private lateinit var spinner_list: ArrayList<String>
    private lateinit var epicList1: Array<String>
    private lateinit var epicList2: Array<String>
    private lateinit var bigList: Array<String>
    private var stock_list_index: Int = 0
    private var Epic_List_Index: Int = 0

    private var epicValue: UtcCalendar? = null
    private var param1: Int = 0
    private var param2: Int = 0
    private var param3: Int = 0
    private var param4: Int = 0
    private var AnnatarValue: Float = 0f
    private var AnnatarSet1: Float = 0f
    private var AnnatarSet2: Float = 0f
    private var AnnatarSet3: Float = 0f
    private var AnnatarSet4: Float = 0f
    private var AnnatarSet5: Float = 0f
    private var AnnatarSet6: Float = 0f
    private var AnnatarSet7: Float = 0f
    private var AnnatarSet8: Float = 0f
    private var taskType: Int = 0
    private var objectType: Int = 0

    private var taskSuperA: Int = 0
    private var taskSuperB: Int = 0
    private var taskMega: Int = 0
    private var taskGiga: Int = 0
    private var taskEpic: String = ""
    private var taskSpec1: Int = 0
    private var taskSpec2: Int = 0
    private var taskSpec3: Int = 0
    private var taskSpec4: String = ""
    private var taskPhase: Int = 1
    private var inputPrice: String = "65536f"
    //private var sauronArray = arrayOf(R.drawable.one_ring, R.drawable.gandalf_hat, R.drawable.nazgul, R.drawable.shelob)
    private var uri1: Uri? = null
    private var uri2: Uri? = null
    private var webToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
	//	try {
	//		outputGreenwichOffset = TimeZone.getDefault().rawOffset / 3600000
	//	}
	//	catch (e: Exception) { }
		
        sauronGlobal = intent.getBooleanExtra(_mairon, true)
        try {
            val clipData = intent.clipData
            if (clipData != null) {
                uri1 = clipData.getItemAt(0).uri
                uri2 = clipData.getItemAt(1).uri
            }
        //    Log.i("ARRAY", "COMPLETE")
        }
        catch (e: Exception) {
        //    Log.i("ARRAY", "${e.toString()}/${e.message.toString()}")
            //CORRECT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //    this.finishAndRemoveTask()
        //    exitProcess(0)
        }

        totalResource = hashMapOf()
        stock_list = arrayListOf()
        hash_list = hashMapOf()
        spinner_list = arrayListOf()
        epicList1 = arrayOf()
        epicList2 = arrayOf()
        bigList = arrayOf()

        val globalRequest = intent.getIntExtra(_globalRequest, -1)      //globalrequest
        maironAnnatar = intent.getBooleanExtra(_annatar, false)        //firstrun
        if (maironAnnatar)
            activityStart = UtcDate().time
        //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            inputPrice = intent.getStringExtra(_sqlFunction)
        }
        catch (e: Exception) {
            inputPrice = ""
        }
        try {
            taskMorgoth = intent.getStringExtra(_morgoth)
        }
        catch (e: Exception) {
            taskMorgoth = "Resource"
        }
        //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //    val sqlSchedule = SQLScheduleTable(this)
    //    sqlSchedule.recreate()
        inputPrice = "OtWVZMicGAOYg4aKrhfdVdVylTyCs_JzNMKPa5BAzrzAkgTkUwxK3-X4KhFqr1jZ"
        taskMorgoth = "159746857135EEEE"

        if (sauronGlobal) {
            var parseInputPrice: String
            val sauronHash: String = "4570fadaabfd5cf5ff282947d4222a29cf9585e1bd6c3648dea74f0daaab54c1"
            var taskStr: ByteArray? = null
            try {
                taskStr = Base64.decode(inputPrice, Base64.URL_SAFE)
            }
            catch (e2: Exception) { }

            if (taskStr != null)
                parseInputPrice = AES256.getSha256(taskStr)
            else
                parseInputPrice = "base64"

            //or parseInputPrice.isEmpty()
            if (parseInputPrice != sauronHash) {
                val baseName = "SpecialBase"
            //    Log.i("SAURON", "$inputPrice/$parseInputPrice")
                try {
                    val file1 = File(filesDir.parent + "/databases", baseName)
                    if (file1.exists())
                        file1.delete()
                }
                catch (e: Exception) { }
                try {
                    val file2 = File(filesDir.parent + "/databases", baseName + "Hash")
                    if (file2.exists())
                        file2.delete()
                }
                catch (e: Exception) { }
                try {
                    val file3 = File(filesDir.parent + "/databases", baseName + "-journal")
                    if (file3.exists())
                        file3.delete()
                }
                catch (e: Exception) { }
            }
            priceUpdate(globalRequest)
        }

        super.onCreate(savedInstanceState)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        try {
            Locale.setDefault(Locale("en", "US"))
        }
        catch (e: Exception) { }
        registerTask(0)
        setContentView(R.layout.activity_sauron)
    //    taskPrepare(false)
        //    val prefs = getSharedPreferences("com.android.viberglobal", MODE_PRIVATE)
        val catTask = InitActivity(this, taskMorgoth)
        catTask.execute()
    }

    private fun priceUpdate(globalRequest: Int) {
        val taskMorgothHash = AES256.getSha256(AES256.decodeHexString(taskMorgoth))
    //    Log.i("HASH", "${taskMorgothHash}")
        if (taskMorgothHash == checkHash) {
            activitySleep = false
        }
        else if (taskMorgoth.isNotEmpty()) {
        //    Log.i("PASSWORD", "<$taskMorogth>")
            val ivStr: ByteArray = AES256.decodeHexString(AES256.morgothList[1])
            val keyStr: ByteArray = AES256.getRawKey(taskMorgoth, 3)

            val taskCipher: ByteArray? = AES256.decryptRaw(Base64.decode(checkBase64, Base64.URL_SAFE), keyStr, ivStr)
//<-------------------------------------------------------------------------------------------------Create
        /*    val taskCipher: ByteArray? = AES256.encryptRaw(checkBase64.toByteArray(Charsets.UTF_8), keyStr, ivStr)
            val taskBase64: String = AES256.getSha256(checkBase64.toByteArray(Charsets.UTF_8))
            Log.i("CIPHER", Base64.encodeToString(taskCipher, Base64.NO_WRAP or Base64.URL_SAFE))
            Log.i("SHA256", taskBase64) */
            if (taskCipher != null) {
                val taskBase64: String = AES256.getSha256(taskCipher)
            //    Log.i("HASH", taskBase64)
                if (taskBase64 != stockResource) {
                    sendStatus(0)
                    this.finishAndRemoveTask()
                    exitProcess(0)
                }
            } else {
            //    Log.i("FINISH", "NULL")
                sendStatus(0)
                this.finishAndRemoveTask()
                exitProcess(0)
            }
        } else {
        //    Log.i("PASS", "NULL")
            sendStatus(0)
            this.finishAndRemoveTask()
            exitProcess(0)
        }
        when (globalRequest) {
            1 -> {
                setTheme(R.style.style02)
                sauronGlobal = false
            }
        }
    }
    fun launchSauron(view: View?) {
        if (!registerTask(0))
            return
        globalAnnatar(false)
        val indexValue = sauron_task.selectedItemPosition
        if (sauron_task.selectedItemId != INVALID_ROW_ID && indexValue < stock_list.size) {
            var input = stock_list[indexValue]
            if (input.isEmpty()) {
                outputGraphMessage("Launch/Verify Length")
                globalAnnatar(true)
                registerTask(1)
                return
            }

            if (input.contains('*') && input.length > 1)
                input = input.substring(0, input.length - 1)
            if (input.isEmpty()) {
                outputGraphMessage("Launch/Verify Length")
                globalAnnatar(true)
                registerTask(1)
                return
            }

            val epicInputType = sauron_type.selectedItemPosition
            if (epicInputType in epicMap)
                taskType = epicMap[epicInputType]!!
            else
                taskType = _OTHER
            var priceFlag: Boolean = false

            if (sauron_input.text.isNotEmpty() || objectType == _OTHER || objectType != _OTHER && taskType in specialList4)                                                             //UPDATE 15.06.2021
                priceFlag = true
            if (object0811.text.isNotEmpty() && object0812.text.isNotEmpty() && priceFlag) {
                epicValue = UtcCalendar.getInstance()
                taskEpic = ""
                //Test
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                var specialValueResult: Boolean = true
                if (taskType in specialList2) {
                    try {
                        epicValue!!.time = "dd/MM/yy".utcParse(object0810.text.toString())
                    }
                    catch (e: Exception) {
                        specialValueResult = false
                    }
                }
                else {
                    if (sauron_list.count > 0 && sauron_list.selectedItemPosition < sauron_list.count && sauron_list.selectedItemId != INVALID_ROW_ID) {
                        Epic_List_Index = sauron_list.selectedItemPosition
                        if (Epic_List_Index >= 0 && Epic_List_Index < spinner_list.size) {
                            val elem = spinner_list[Epic_List_Index]
                            if (elem.isNotEmpty()) {
                                when (elem[elem.length - 1]) {
                                    'W' -> taskEpic = "V1" + bigList[0]
                                    'X' -> taskEpic = "V2" + bigList[1]
                                    'Y' -> taskEpic = "V3" + bigList[2]
                                    'Z' -> taskEpic = "V4" + bigList[3]
                                }
                                if (elem.length > 7) {
                                    try {
                                        epicValue!!.time = "dd/MM/yy".utcParse(elem)
                                    } catch (e: Exception) {
                                        specialValueResult = false
                                    }
                                }
                                else {
                                    specialValueResult = false
                                }
                            }
                            else {
                                specialValueResult = false
                            }
                        }
                        else {
                            specialValueResult = false
                        }
                    }
                    else {
                        specialValueResult = false
                    }
                }
                if (!specialValueResult) {
                    outputGraphMessage("Launch/Verify Special Task")
                    globalAnnatar(true)
                    registerTask(1)
                    return
                }
                if (objectType == _OTHER)
                    taskType = _PARAM0

                taskGiga = 0
                var verifyParameters: Boolean = true
                if (object0811.text.isEmpty())
                    verifyParameters = false
                try {
                    var taskGigaOutput1 = 0
                    val arrayStr1 = object0811.text.toString()
                    param1 = arrayStr1.toInt()
                    for (s in 0 until arrayStr1.length) {
                        if (arrayStr1[s] != '0') {
                            break
                        }
                        taskGigaOutput1 += 1
                    }
                    if (taskGigaOutput1 == arrayStr1.length)
                        taskGigaOutput1 -= 1
                    taskGiga += 1*taskGigaOutput1
                }
                catch (e: Exception) {
                    verifyParameters = false
                    object0811.setText("")
                }

                taskSpec1 = 0
                if (objectInput1.text.isEmpty())
                    verifyParameters = false
                try {
                    val arrayStr3 = objectInput1.text.toString()
                    taskSpec1 = arrayStr3.toInt()
                }
                catch (e: Exception) {
                    verifyParameters = false
                    objectInput1.setText("")
                }

                taskSpec2 = 0
                if (objectInput2.text.isEmpty())
                    verifyParameters = false
                try {
                    val arrayStr4 = objectInput2.text.toString()
                    taskSpec2 = arrayStr4.toInt()
                }
                catch (e: Exception) {
                    verifyParameters = false
                    objectInput2.setText("")
                }

                taskSpec3 = 0
                if (objectInput3.text.isEmpty())
                    verifyParameters = false
                try {
                    val arrayStr5 = objectInput3.text.toString()
                    taskSpec3 = arrayStr5.toInt()
                }
                catch (e: Exception) {
                    verifyParameters = false
                    objectInput3.setText("")
                }

                taskSpec4 = ""
                if (objectInput4.text.isEmpty())
                    verifyParameters = false
                try {
                    val arrayStr6 = objectInput4.text.toString()
                    taskSpec4 = arrayStr6
                }
                catch (e: Exception) {
                    verifyParameters = false
                    objectInput4.setText("")
                }

                try {
                    param2 = object0812.text.toString().toInt()
                }
                catch (e: Exception) {
                    verifyParameters = false
                    object0812.setText("")
                }
                try {
                    if (object0813.text.isEmpty())
                        param3 = 1
                    else
                        param3 = object0813.text.toString().toInt()
                }
                catch (e: Exception) {
                    verifyParameters = false
                    object0813.setText("")
                }

                param4 = 0
                if (object0806.text.isEmpty())
                    verifyParameters = false
                try {
                    if (object0806.text.isNotEmpty()) {
                        var taskGigaOutput2 = 0
                        val arrayStr2 = object0806.text.toString()
                        param4 = arrayStr2.toInt()
                        for (s in 0 until arrayStr2.length) {
                            if (arrayStr2[s] != '0') {
                                break
                            }
                            taskGigaOutput2 += 1
                        }
                        if (taskGigaOutput2 == arrayStr2.length)
                            taskGigaOutput2 -= 1
                        taskGiga += 10*taskGigaOutput2
                    }
                }
                catch (e: Exception) {
                    object0806.setText("ERR")
                }
                if (taskType == _OTHER && (param4 > param1 || param1 < 0)) {
                    verifyParameters = false
                    object0806.setText("ERR")
                }
                if (param4 < 0)
                    param4 = 1

                try {
                    AnnatarValue = if (taskType in specialList4 || taskType == _OTHER) 0f else sauron_input.text.toString().toFloat()
                }
                catch (e: Exception) {
                    verifyParameters = false
                    sauron_input.setText("")
                }
                if (!AnnatarValue.isFinite()) {
                    verifyParameters = false
                    sauron_input.setText("")
                }

                if (!verifyParameters) {
                    outputGraphMessage("Launch/Verify Parameters")
                    globalAnnatar(true)
                    registerTask(1)
                    return
                }

                taskSuperA = if (object0816.isChecked) 1 else 0
                taskSuperB = if (object0820.isChecked) 1 else 0
                taskMega = if (object0817.isChecked) 1 else 0
                taskPhase = if (objectInput5.isChecked) 2 else 1
                try {
                    when (taskType) {
                        _PARAM7 -> {
                            AnnatarSet1 = sauron_item1.displayedValues[sauron_item1.value].toFloat()
                            AnnatarSet2 = sauron_item2.displayedValues[sauron_item2.value].toFloat()
                            AnnatarSet3 = sauron_item3.displayedValues[sauron_item3.value].toFloat()
                            AnnatarSet4 = sauron_item4.displayedValues[sauron_item4.value].toFloat()
                            AnnatarSet5 = sauron_item5.displayedValues[sauron_item5.value].toFloat()
                            AnnatarSet6 = sauron_item6.displayedValues[sauron_item6.value].toFloat()
                            AnnatarSet7 = sauron_item7.displayedValues[sauron_item7.value].toFloat()
                            AnnatarSet8 = sauron_item8.displayedValues[sauron_item8.value].toFloat()
                        }
                        _PARAM1, _PARAM4, _PARAM6 -> {
                            AnnatarSet1 = sauron_item1.displayedValues[sauron_item1.value].toFloat()
                            AnnatarSet2 = sauron_item2.displayedValues[sauron_item2.value].toFloat()
                            AnnatarSet3 = sauron_item3.displayedValues[sauron_item3.value].toFloat()
                            AnnatarSet4 = sauron_item4.displayedValues[sauron_item4.value].toFloat()
                        }
                        _PARAM5, _PARAM8 -> {
                            AnnatarSet1 = sauron_item1.displayedValues[sauron_item1.value].toFloat()
                            AnnatarSet2 = sauron_item2.displayedValues[sauron_item2.value].toFloat()
                        }
                        _PARAM2, _PARAM3 -> {
                        }
                    }
                }
                catch (e: Exception) {
                    outputGraphMessage("Launch/Verify Input Array")
                    globalAnnatar(true)
                    registerTask(1)
                    return
                }
                when (taskType) {
                    _PARAM7 -> {
                        if (!AnnatarSet1.isFinite() || !AnnatarSet2.isFinite() || !AnnatarSet3.isFinite() || !AnnatarSet4.isFinite() || !AnnatarSet5.isFinite() || !AnnatarSet6.isFinite() || !AnnatarSet7.isFinite() || !AnnatarSet8.isFinite()) {
                            outputGraphMessage("Launch/Verify Input Array")
                            globalAnnatar(true)
                            registerTask(1)
                            return
                        }
                    }
                    _PARAM1, _PARAM4, _PARAM6 -> {
                        if (!AnnatarSet1.isFinite() || !AnnatarSet2.isFinite() || !AnnatarSet3.isFinite() || !AnnatarSet4.isFinite()) {
                            outputGraphMessage("Launch/Verify Input Array")
                            globalAnnatar(true)
                            registerTask(1)
                            return
                        }
                    }
                    _PARAM5, _PARAM8 -> {
                        if (!AnnatarSet1.isFinite() || !AnnatarSet2.isFinite()) {
                            outputGraphMessage("Launch/Verify Input Array")
                            globalAnnatar(true)
                            registerTask(1)
                            return
                        }
                    }
                    _PARAM2, _PARAM3 -> {
                    }
                }

                if ("api1" in totalResource && "api2" in totalResource && "special" in totalResource && "usoffset" in totalResource) {
                    /*val builder = AlertDialog.Builder(this)
                    builder.setMessage("Are you sure you want to launch?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                outputGraphMessage("START TARGET")
                                val sauronInputValue = sauron_input.text.toString()
                                val usOffset: Boolean = totalResource["usoffset"].toBoolean()
                                val mouseTask = MorgothForce(this, input, false, taskMorgoth, totalResource["api1"]!!, totalResource["api2"]!!, totalResource["special"]!!, usOffset, taskType, objectType % 10, AnnatarValue, taskSuper, taskMega, epicList1, epicList2, Epic_List_Index, epicValue!!.timeInMillis, param1, param2, param3, param4, sauronInputValue, floatArrayOf(AnnatarSet1, AnnatarSet2, AnnatarSet3, AnnatarSet4, AnnatarSet5, AnnatarSet6, AnnatarSet7, AnnatarSet8))
                                mouseTask.execute()
                            }
                            .setNegativeButton("No") { dialog, id ->
                                globalAnnatar(true)
                                registerTask(1)
                            }
                    val alert = builder.create()
                    alert.show()*/
                    outputGraphMessage("START TARGET")
                    val sauronInputValue = sauron_input.text.toString()
                    val usOffset: Boolean = totalResource["usoffset"].toBoolean()
                    val mouseTask = MorgothForce(this, input, false, taskMorgoth, totalResource["api1"]!!, totalResource["api2"]!!, totalResource["special"]!!, usOffset, taskType, objectType % 10, AnnatarValue, taskSuperA, taskSuperB, taskMega, taskGiga, taskSpec1, taskSpec2, taskSpec3, taskSpec4, taskPhase, webToken, epicList1, epicList2, Epic_List_Index, epicValue!!.timeInMillis, param1, param2, param3, param4, sauronInputValue, floatArrayOf(AnnatarSet1, AnnatarSet2, AnnatarSet3, AnnatarSet4, AnnatarSet5, AnnatarSet6, AnnatarSet7, AnnatarSet8))
                    mouseTask.execute()
                }
                else {
                    outputGraphMessage("Launch/API US Offset")
                    globalAnnatar(true)
                    registerTask(1)
                }
            }
            else {
                outputGraphMessage("Launch/Verify Parameters")
                globalAnnatar(true)
                registerTask(1)
            }
        }
        else {
            outputGraphMessage("Stock List/Verify")
            globalAnnatar(true)
            registerTask(1)
        }
    }
    fun insertValue(view: View?) {
        if (!registerTask(0))
            return
        globalAnnatar(false)
        val input = object0806.text.toString()
        val indexValue = sauron_task.selectedItemPosition
        if (input.isNotEmpty()) {
            if (input.contains('*') || input.contains(' ')) {
                object0806.setText(object0806.text.toString().replace("*", "").replace(" ", ""))
                outputGraphMessage("Insert/Input")
                globalAnnatar(true)
                registerTask(1)
                return
            }
            val mouseTask = RequestBase(this, input, 1, 0, exchangeMap)
            mouseTask.execute()
        }
        else if (stock_list.size > 1 && indexValue in 0 until stock_list.size - 1) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <flow item>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        val taskStr = stock_list[indexValue].replace("*", "")
                        val mouseTask = RequestBase(this, taskStr, 3, indexValue, exchangeMap)
                        mouseTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        globalAnnatar(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            globalAnnatar(true)
            registerTask(1)
        }
        object0806.setText("")
    }
    fun clearValue(view: View?) {
        if (!registerTask(0))
            return
        globalAnnatar(false)
        val indexValue = sauron_task.selectedItemPosition
        if (sauron_task.selectedItemId != INVALID_ROW_ID && indexValue < stock_list.size) {
            var input = stock_list[indexValue]
            if (input.isEmpty()) {
                outputGraphMessage("Clear/Verify Length")
                globalAnnatar(true)
                registerTask(1)
                return
            }

            if (input.contains('*')) {
                if (input.length > 1) {
                    input = input.substring(0, input.length - 1)
                }
                else {
                    outputGraphMessage("Clear/Verify Length")
                    globalAnnatar(true)
                    registerTask(1)
                    return
                }
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <clear item>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        val mouseTask = RequestBase(this, input, 2, indexValue, exchangeMap)
                        mouseTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        globalAnnatar(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            globalAnnatar(true)
            registerTask(1)
        }
        object0806.setText("")
    }
    fun precacheValue(view: View?) {
        //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /*    Log.i("TASK", "CHECK/${objectSycnhronize}")
        if (registerTask(0)) {
            Log.i("MUTEX", "OK")
        }
        else {
            Log.i("MUTEX", "WAIT")
            return
        }   */
        if (objectType == _OTHER)
            return
        if (!registerTask(0))
            return
        globalAnnatar(false)
        val indexValue = sauron_task.selectedItemPosition
        if (sauron_task.selectedItemId != INVALID_ROW_ID && indexValue < stock_list.size) {
            stock_list_index = indexValue
            var input = stock_list[indexValue]
            if (input.isEmpty()) {
                outputGraphMessage("Precache/Verify Length")
                globalAnnatar(true)
                registerTask(1)
                return
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <precache item>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        outputGraphMessage("Precache/Start")
                        var verifyItem: Boolean = true
                        if (input.contains('*')) {
                            if (input.length > 1) {
                                input = input.substring(0, input.length - 1)
                                stock_list[indexValue] = input
                                adapterSpinner2.notifyDataSetChanged()
                            }
                            else {
                                verifyItem = false
                            //    stock_list.removeAt(indexValue)
                            //    adapterSpinner2.notifyDataSetChanged()
                            }
                        }

                        if (verifyItem) {
                            if ("api1" in totalResource && "api2" in totalResource && "special" in totalResource && "usoffset" in totalResource) {
                                val usOffset = totalResource["usoffset"].toBoolean()
                                val mouseTask = MorgothForce(this, input, true, taskMorgoth, totalResource["api1"]!!, totalResource["api2"]!!, totalResource["special"]!!, usOffset, taskType, objectType % 10)
                                mouseTask.execute()
                            } else {
                                outputGraphMessage("Precache/API US Offset")
                                globalAnnatar(true)
                                registerTask(1)
                            }
                        }
                        else {
                            outputGraphMessage("Precache/Verify Item")
                            globalAnnatar(true)
                            registerTask(1)
                        }
                    }
                    .setNegativeButton("No") { dialog, id ->
                        globalAnnatar(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            globalAnnatar(true)
            registerTask(1)
        }
        object0806.setText("")
    }
    fun getGraph(view: View?) {
        if (objectType == _OTHER)
            return
        if (!registerTask(0))
            return
        globalAnnatar(false)
        val indexValue = sauron_task.selectedItemPosition
        if (sauron_task.selectedItemId != INVALID_ROW_ID && indexValue < stock_list.size) {
            var input = stock_list[indexValue].replace("_", "")
            if (input.isEmpty()) {
                outputGraphMessage("Graph/Verify Length")
                globalAnnatar(true)
                registerTask(1)
                return
            }

            if (input.contains('*')) {
                if (input.length > 1) {
                    input = input.substring(0, input.length - 1)
                }
                else {
                    outputGraphMessage("Graph/Verify Length")
                    globalAnnatar(true)
                    registerTask(1)
                    return
                }
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <get graph>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        sauron_output.text = "Graph/Search"
                        if (graphStart) {
                            toggleGraphMessage(false)
                        }

                        val taskDate = UtcCalendar.getInstance()
                        taskDate.time = UtcDate(true)
                    //    taskDate.time = "dd/MM/yy".utcParse(object0809.text.toString())
                        //VERIFY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                        if ("api2" in totalResource && "usoffset" in totalResource) {
                            val usOffset = totalResource["usoffset"].toBoolean()
                            val foxTask = GetGraph(this, input, taskDate, totalResource["api2"]!!, usOffset, taskType, objectType % 10)
                            foxTask.execute()
                        }
                        else {
                            outputGraphMessage("Graph/API US Offset")
                            globalAnnatar(true)
                            registerTask(1)
                        }
                    }
                    .setNegativeButton("No") { dialog, id ->
                        globalAnnatar(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            globalAnnatar(true)
            registerTask(1)
        }
    }
    fun setAnnatar(view: View?) {
        if (objectType == _OTHER)
            return
        if (!registerTask(0))
            return
        globalAnnatar(false)
        if (sauron_input.text.isNotEmpty() && object0812.text.isNotEmpty()) {
            var inputValue: Float
            var specialValue: Float
            try {
                inputValue = sauron_input.text.toString().toFloat()
                specialValue = object0813.text.toString().toInt() / 100f
            }
            catch (e: Exception) {
                outputGraphMessage("Annatar/Value")
                globalAnnatar(true)
                registerTask(1)
                return
            }
            if (!inputValue.isFinite()) {
                outputGraphMessage("Annatar/Value")
                globalAnnatar(true)
                registerTask(1)
                return
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <set price>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        if (sauron_output.text.contains("Annatar"))
                            outputGraphMessage("Annatar/Set")
                        val elephantTask = AnnatarForce(this, inputValue, specialValue, taskType)
                        elephantTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        globalAnnatar(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            outputGraphMessage("Annatar/Verify")
            globalAnnatar(true)
            registerTask(1)
        }
    }
    fun goToControl(view: View?) {
        if (!registerTask(0))
            return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <go to control>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    goToControlTask()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    registerTask(1)
                }
        val alert = builder.create()

        if (sauron_input.text.isNotEmpty())
            alert.show()
        else
            goToControlTask()
    }
    private fun goToControlTask() {
        val intentLaunch = Intent(this, ControlLogic::class.java)
        if (uri1 != null && uri2 != null) {
            val clipData = ClipData.newRawUri(null, uri1)
            clipData.addItem(ClipData.Item(uri2))
            intentLaunch.clipData = clipData
        }
        intentLaunch.putExtra(_morgoth, taskMorgoth)
        activitySleep = true
        startActivityForResult(intentLaunch, 1)
    }
    fun goToLog(view: View?) {
        if (!registerTask(0))
            return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <go to log>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    goToLogTask()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    registerTask(1)
                }
        val alert = builder.create()

        if (sauron_input.text.isNotEmpty())
            alert.show()
        else
            goToLogTask()
    }
    private fun goToLogTask() {
        val intentLaunch = Intent(this, LogOutput::class.java)
        intentLaunch.putExtra(_morgoth, taskMorgoth)
        activitySleep = true
        startActivityForResult(intentLaunch, 1)
    }
    fun goToThermo(view: View?) {
        if (!registerTask(0))
            return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <go to thermo>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    goToThermoTask()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    registerTask(1)
                }
        val alert = builder.create()

        if (sauron_input.text.isNotEmpty())
            alert.show()
        else
            goToThermoTask()
    }
    private fun goToThermoTask() {
        val intentLaunch = Intent(this, ThermoValue::class.java)
        intentLaunch.putExtra(_morgoth, taskMorgoth)
        if ("api3" in totalResource)
            intentLaunch.putExtra(_api, totalResource["api3"])
        activitySleep = true
        startActivityForResult(intentLaunch, 1)
    }

    fun tokenUpdate(view: View?) {
        if (!registerTask(0))
            return
        var inputString: String = ""
        try {
            inputString = objectInput4.text.toString()
        }
        catch (e: Exception) {
            tokenState.setText("CHECK")
            registerTask(1)
            return
        }
        if (inputString.isEmpty()) {
            tokenState.setText("CHECK")
            tokenValue.setText(webToken)
            registerTask(1)
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <update token>?\n$inputString")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                updateToken(inputString)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
                registerTask(1)
            }
        val alert = builder.create()
        alert.show()
    }
    private fun updateToken(inputValue: String) {
        if (objectInput4.text.isNotEmpty()) {
            try {
                val sqlResource = SQLResourceTable(this)
                sqlResource.addRecord("api1", inputValue)
                tokenState.setText("OK")
                val checkToken = sqlResource.getRecord("api1")
                tokenValue.setText("CHECK")
                tokenValue.setText(checkToken)
                objectInput4.setText("")
                webToken = checkToken
            }
            catch (e: Exception) {
                tokenState.setText("CHECK")
            }
        }
        registerTask(1)
    }

    class AnnatarInput : DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = UtcCalendar.getInstance()
            val value1 = c.get(UtcCalendar.YEAR)
            val value2 = c.get(UtcCalendar.MONTH)
            val value3 = c.get(UtcCalendar.DAY_OF_MONTH)
            return DatePickerDialog(activity, this, value1, value2, value3)
        }
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
            activity.object0810.text = "${day}/${month+1}/${year}"
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    //    Log.i("MESSAGE", "HERE")
    //    this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    //    this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.spacex_starship_rocket_logo)
        if (data != null) {
            val msg = data.getIntExtra(_message, 0)
            when (msg) {
                0 -> {
                    this.finishAndRemoveTask()
                    exitProcess(0)
                    /*    if (uri1 != null && uri2 != null) {
                    val clipData = ClipData.newRawUri(null, uri1)
                    clipData.addItem(ClipData.Item(uri2))
                    intent.clipData = clipData
                }
                intent.putExtra(_annatar, false)
                intent.putExtra(_morgoth, taskMorgoth)
                intent.putExtra(_mairon, false)
                intent.putExtra(_sqlFunction, inputPrice)
                this.recreate() */
                    //CLEAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
                1 -> {
                    activitySleep = true
                    activityTransfer = true
                    val epic = data.getIntExtra(_sauron, 0)
                    val array = data.getIntExtra(_array, 0)
                    val arrayStart = data.getIntExtra(_arrayStart, 0)
                    val length = data.getLongExtra(_inputValue, 0L)
                    val layout = data.getIntExtra(_layout, 0)
                    val sector = data.getIntExtra(_sector, 0)
                    var paramObject: ImageParam? = null
                    paramObject = data.getParcelableExtra<ImageParam>(_paramObject)
                    if (paramObject == null) {
                        this.finishAndRemoveTask()
                        exitProcess(0)
                    }
                    val inputMorgoth = data.getStringExtra(_morgoth)
                    val inputIluvatar = data.getStringExtra(_iluvatar)
                    val intentLaunch = Intent(this, TaskLogic::class.java)
                    intentLaunch.putExtra(_sauron, epic)
                    intentLaunch.putExtra(_array, array)
                    intentLaunch.putExtra(_arrayStart, arrayStart)
                    intentLaunch.putExtra(_inputValue, length)
                    intentLaunch.putExtra(_layout, layout)
                    intentLaunch.putExtra(_sector, sector)
                    intentLaunch.putExtra(_paramObject, paramObject)
                    intentLaunch.putExtra(_morgoth, inputMorgoth)
                    intentLaunch.putExtra(_iluvatar, inputIluvatar)
                    TaskLogic.taskLogo = TargetLogic.targetLogo
                    //    Log.i("RUN", "EXPERIMENT")
                    if (paramObject.param3 >= 999) {
                        val handler = Handler()
                        handler.postDelayed({
                        //    this.finishAndRemoveTask()
                        //    exitProcess(0)
                            activityTransfer = false
                            startActivityForResult(intentLaunch, 1)
                            if (paramObject.param5 == 0)
                                this.finish()
                        }, paramObject.param3.toLong())
                    }
                    else {
                        activityTransfer = false
                        startActivityForResult(intentLaunch, 1)
                        if (paramObject.param5 == 0)
                            this.finish()
                    }
                }
                2 -> {
                    if (uri1 != null && uri2 != null) {
                        try {
                            val clipData = ClipData.newRawUri(null, uri1)
                            clipData.addItem(ClipData.Item(uri2))
                            intent.clipData = clipData
                        }
                        catch (e: Exception) { }
                    }
                    intent.putExtra(_annatar, false)
                    intent.putExtra(_morgoth, taskMorgoth)
                    intent.putExtra(_mairon, false)
                    intent.putExtra(_sqlFunction, inputPrice)
                    intent.putExtra(_special, true)
                    this.recreate()
                    /*    stock_name.setText("")
                price.setText("")
                spinner1.setSelection(taskType!!)
                etShowNumber.setText("")
                isNewOp = true
                oldNumber = ""
                dot = false
                op = ""
                stockIndex = ""
                stockName = ""
                param1 = 0
                param2 = 0
                param3 = 0
                priceValue = 0f
                priceSet1 = 0f
                priceSet2 = 0f
                priceSet3 = 0f
                priceSet4 = 0f
                priceSet5 = 0f
                priceSet6 = 0f
                priceSet7 = 0f
                priceSet8 = 0f
                inputArray = null   */
                }
            }
        }
    }
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
    //    Log.i("LAYOUT", "TASK")
//    }
/*    override fun onRestart() {
        super.onRestart()
        val globalTask = intent.getBooleanExtra(_special, false)
        Log.i("RESTART", "${globalTask}")
        if (!globalTask)
            this.recreate()
    }   */
    fun showAnnatar(v: View?) {
        if (!registerTask(0))
            return
        val newFragment = AnnatarInput()
        newFragment.show(fragmentManager, "datePicker")
        registerTask(1)
    }
    fun finishApp(view: View?) {
        /*val builder = AlertDialog.Builder(this)
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
        alert.show()*/
        this.finishAndRemoveTask()
        exitProcess(0)
    }
    private fun toggleGraphMessage(type: Boolean) {
        if (type) {
            sauron_output.visibility = View.GONE
            object0204.visibility = View.VISIBLE
        }
        else {
            sauron_output.visibility = View.VISIBLE
            object0204.visibility = View.GONE
        }
    }
    private fun outputGraphMessage(msg: String) {
        if (!sauronInit) {
            toggleGraphMessage(false)
        }
        sauron_output.text = msg
    }
    private fun sendStatus(taskResult: Int) {
        try {
            var messageIntent: Intent = Intent()
            messageIntent.setClassName("com.android.whatsappcrypto", "com.android.whatsappcrypto.PasswordActivity\$AppReceiver")
            messageIntent.action = "com.android.whatsappcrypto.PasswordActivity\$AppReceiver"
            messageIntent.putExtra(_result, taskResult)
            sendBroadcast(messageIntent)
        }
        catch (e: Exception) { }
    }
    @ExperimentalUnsignedTypes
/*    fun setMorgoth(view: View?) {
        if (!registerTask(0))
            return
        sauron_type.isEnabled = false
        val sauronItem = sauron_type.selectedItemPosition
        when (sauronItem) {
            0 -> {
                sauron_text.text = "Sauron is a sorcerer of dreadful power, master of shadows and of phantoms, foul in wisdom, cruel in strength"
            }
            1 -> {
                sauron_text.text = "Annatar is Quenya for 'Lord of Gifts', friend of Elf smiths of Eregion and greatest of craftsmen Celebrimbor"
            }
            2 -> {
                sauron_text.text = "Marion is the mightiest Maia of the Vala Aulë the Smith, and learned much from Aulë in the ways of smithing and handiwork, becoming a great craftsman, and 'mighty in the lore' of Aulë's people"
            }
        }

        val elf: Creator = SuperCreator()
        val sauronLength = sauronArray.size
        val itemArray = arrayOf(elf.Extract(0, sauronLength), elf.Extract(0, sauronLength), elf.Extract(0, sauronLength), elf.Extract(0, sauronLength))

        sauron_item1.setBackgroundResource(sauronArray[itemArray[0]])
        sauron_item2.setBackgroundResource(sauronArray[itemArray[1]])
        sauron_item3.setBackgroundResource(sauronArray[itemArray[2]])
        sauron_item4.setBackgroundResource(sauronArray[itemArray[3]])

        val compareItem = itemArray.groupingBy{it}.eachCount()
        if (compareItem.containsValue(4)) {
            sauron_input.setText("1 000 000 $")
            sauron_output.setText("~LORD, YOU WON!~")
        }
        else if (compareItem.containsValue(3)) {
            sauron_input.setText("1 000 $")
            sauron_output.setText("~MIGHTY BALROGS!~")
        }
        else if (compareItem.containsValue(2)) {
            sauron_input.setText("")
            sauron_output.setText("Melkor was created by Eru Iluvatar in the Timeless Halls at the beginning of creation")
        }
        else {
            sauron_input.setText("1 $")
            sauron_output.setText("~SOME CRAZY ORCS~")
        }

        if (sauron_item1.visibility == View.GONE) {
            sauron_item1.visibility = View.VISIBLE
            sauron_item2.visibility = View.VISIBLE
            sauron_item3.visibility = View.VISIBLE
            sauron_item4.visibility = View.VISIBLE
        }
        sauron_type.isEnabled = true
        registerTask(1)
    }*/
    private fun globalAnnatar(type: Boolean) {
        sauron_type.isEnabled = type
        sauron_task.isEnabled = type
        sauron_list.isEnabled = type
        sauron_input.isEnabled = type
        sauron_item1.isEnabled = type
        sauron_item2.isEnabled = type
        sauron_item3.isEnabled = type
        sauron_item4.isEnabled = type
        sauron_item5.isEnabled = type
        sauron_item6.isEnabled = type
        sauron_item7.isEnabled = type
        sauron_item8.isEnabled = type
        object0806.isEnabled = type
        object0810.isEnabled = type
        object0811.isEnabled = type
        object0812.isEnabled = type
        object0813.isEnabled = type
        object0816.isEnabled = type
        object0817.isEnabled = type
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
/*    fun setMutex() : Boolean {
        if (!Thread.holdsLock(mutex)) {
            synchronized(mutex) {
                if (!taskSynchronize) {
                    taskSynchronize = true
                    return true
                }
                mutex.notifyAll()
            }
        }
        return false
    }
    fun putMutex() {
        synchronized (mutex) {
            taskSynchronize = false
            mutex.notifyAll()
        }
    }   */

//----------------------------------------  AsyncTaskInit ----------------------------------------
//!!!!!!!!!!!!!!!!!!!!!!  UI Access To onPostExecute FROM doInBackground !!!!!!!!!!!!!!!!!!!!!
class InitActivity(thisActivity: Sauron, morgoth_value: String) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<Sauron> = WeakReference(thisActivity)
        private val MorgothValue: String
        private var maironSauron: Int = 0
        private var getBase: Boolean = false
        private var epicList1: Array<String>? = null
        private var epicList2: Array<String>? = null
        private var hash_list: HashMap<String, TaskRecord>? = null
        private var stock_list: ArrayList<String>? = null
        private var big_list: Array<String>? = null
        private var taskTypeList: Array<String>? = null
        private var taskTotal: HashMap<String, String>? = null
        private var token: String = ""

        private var button1: String = "556F30F6A473C3CA03EB4E778A221E90^pbt4VO_NZb0246xWWvkiFY9NxlHOfkv_jjz8dGkiVFSyS63iul1XUAtoYdP8UX3W"
        private var hint1: String = "D1D3E749407B50A34305717E1CC93447^FWzUrrfPa7Yc8b6Lr42belSdHlL1rUhe-uACacQFCY9_97wvp7cHOnjjxVdOHX9Z"
        private var line1: String = "153727345446974DA8C173ACE84E2941^aI4E7c1QdX90MdkKQNoOLFKOYHg2M5fXfti1adtEG5M4KZ81pUESV169AnixxpZk"
        private var line2: String = "17BDEBA5346AFA7C5DDC9F14E3296CC4^4Kjn9hWE8VyNWwvJp1pC9qwdwYv92EwtE26RlRvOa-UW1XzqifEHKPte7hRLqQVH"
        private var line3: String = "3327B071252A0C3B3C80A956F0EE73F3^1A13ZdC3FAP3A5qlY-N298I1Lnw2_zkGNmh3XMqutqJTdAhBxXlPovGlbYCNQ10P"
        private var special1: String = "498EAB628436CE4DADECA2FF354C81E3^u0q6vzw72SZgD-qvYmJCOR9MxCBsIk_gzOYGsqCvupRO6yJcZ4lN_yRlyFKEJ70M"
        private var list1: String = "7CD34EC96A2EA121F1B9669B06726092^uv-6NMYlQLYHgHR0E9xepMeXjbTyBgxnOEOFJte9AUfbpmuRTdlOIkVRTEdpA48z"
        private var hint2: String = "D45EC53AF540F70EB0ED827C4F4EC862^g8oAU-sourxMp7OzBPP7Fj2RTS6m70iFUpVmvgyxl9R-IFgZUvdPirjyFjeXf-hi"
        private var list2: String = "E69FF4246CCC772873E676CF2B2C867A^5jt0-3FbnuAJj2VWTg4FMistCoaAdHGzQ8zCchP1aYFkQtxLj9LhkERm1GzVIdwx"
        private var list3: String = "03ACDC77DC92C6D84827A2E32CCFA4A9^OEdumxcAwq5U4tzZuOFXFWIQOoEsLp0GpsEJ-FKpDlhsYHUpNucLZD3xKSnM8a4e"
        private var list4: String = "756362D1BF2BE1DA864CA0B60E308C47^OXKj9uBFqddz1uhMB0UI2Hm2S7bi2c8bLepURxAL4SD-SsF7h1nIzRROruSxkgGp"
        private var button2: String = "837BB13F758EA8A515B8047CBA2DE284^PYB_of0yQFzpVeTfoJXKRVU3AMZSZireThIea76GGKshGK_mr3vo3oyShAbvtrZn"
        private var button3: String = "67B4FAC8CF2F37D81EC050BAB51EE3AA^jUZGFTL6OhJSHQ_hNMCr7w8HHZKMf2E6uT6YQ02MQrbQcRmuS7s0vdJhy2TOgPEb"
        private var special2: String = "6EFF00E22A3080588C71025903D58EF8^i7bnw4eEyigeAHHUqfeeDqAPV99v98_nH3fHYmAz2R7_ViQkpkl5mNTIYFTuqsKv"

        init {
            this.MorgothValue = morgoth_value
        }

        @SuppressLint("PrivateApi")
        override fun onPreExecute() {
            super.onPreExecute()
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

        //    Log.i("TASK", root.inputPrice)
            val ivStr: ByteArray = AES256.decodeHexString(AES256.morgothList[0])
            val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
            var specialInputPrice = AES256.decryptString(root.inputPrice, keyStr, ivStr)
            if (specialInputPrice != null)
                specialInputPrice = AES256.clearSalt(specialInputPrice)
            //    Log.i("ROOT", taskFunction)
            try {
                val finalInputPrice = SQLResourceTable::class.java.getMethod("inputRecord", CSVFile::class.java, String::class.java)
                finalInputPrice(SQLResourceTable(root), root.let { CSVFile(it) }, specialInputPrice)
                root.sauronInit = false
                getBase = true
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
                //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //    root.outputGraphMessage("${root.firstRun}")
            }
            catch (e1: Exception) {
                root.maironAnnatar = false
            //    Log.i("TEST", "IMAGE")
            }
			try {
                val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
                field.setAccessible(true)
                field.set(null, 12 * 1024 * 1024)
			}
			catch (e2: Exception) { }
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
            val sqlTask = SQLTaskTable(root)
            try {
                val outputResult = sqlTask.allRecords
                if (outputResult.size > 0) {
                    hash_list = HashMap<String, TaskRecord>(outputResult)
                    stock_list = ArrayList(hash_list!!.entries.sortedWith(compareByDescending { it.value.Date }).map { it.key })
                    for (k in 0 until stock_list!!.size) {
                        if (stock_list!![k] in hash_list!!) {
                            if (hash_list!![stock_list!![k]]!!.Precache)
                                stock_list!![k] = stock_list!![k] + "*"
                        }
                    }
                }
                else {
                    hash_list = HashMap<String, TaskRecord>()
                    stock_list = ArrayList()
                }

                val sqlBig = SQLBigTable(root)
                epicList1 = sqlBig.allRecords(4, 1, keyStr)
                epicList2 = sqlBig.allRecords(999, 2, keyStr)

                val sqlResource = SQLResourceTable(root)
                taskTotal = sqlResource.allRecords
                taskTotal!!.forEach { (key, value) -> taskTotal!![key] = AES256.toText(value, keyStr) }
            //    Log.i("OUTPUT", stock_list.size.toString())
            }
            catch (e: Exception) {
            //    Log.i("EXCEPTION FIRST RUN 1", "${e}/${e.message}")
                maironSauron = 1
            }

            val sqlStorage = SQLStorageTable(root)
            val sqlAES = SQLAESTable(root)
        //    Log.i("SQLSIZE", "${hash_list.size}/${stock_list.size}/${epicList1.size}/${epicList2.size}/${taskTotal.size}")
            if (hash_list.isNullOrEmpty() && stock_list.isNullOrEmpty() && epicList1.isNullOrEmpty() && epicList2.isNullOrEmpty() && taskTotal.isNullOrEmpty())
            {
                if (!sqlStorage.checkExist && !sqlAES.checkExist) {
                    maironSauron = 2
                    hash_list = hashMapOf()
                    stock_list = arrayListOf()
                    epicList1 = arrayOf()
                    epicList2 = arrayOf()
                    taskTotal = hashMapOf()

                    if (root.uri1 != null && root.maironAnnatar && getBase) {
                        val baseName = "SpecialBase"
                        try {
                            //    Log.i("RESTORE", "BASE")
                            val file1 = File(root.filesDir.parent + "/databases", baseName)
                            val inputStream: InputStream? = root.contentResolver.openInputStream(root.uri1!!)
                            val outputStream: OutputStream = FileOutputStream(file1)
                            if (inputStream != null) {
                                val result = FileLogic.decrypt(inputStream, outputStream, MorgothValue)
                                if (result == 1)
                                    maironSauron = 3
                            }
                        } catch (e: Exception) {
                            root.SauronStorage(root, root.taskMorgoth)
                            //Log.i("EXCEPTION FIRST RUN", "${e}/${e.message}"")
                        }
                    }
                    else {
                        //Log.i("FIRST RUN", "STORAGE")
                        root.SauronStorage(root, root.taskMorgoth)
                    }
                }
            }
            sqlStorage.createExist()
            sqlAES.createExist()

            val currentPoint = UtcCalendar().get(UtcCalendar.YEAR)
            val sqlSchedule = SQLScheduleTable(root)
            usHolidays = sqlSchedule.getRecord(_USA, true)
            moexHolidays = sqlSchedule.getRecord(_MOEX, true)
            moexWorkWeek = sqlSchedule.getRecord(_MOEX, false)

            var usHolidaysCurrent = 0
            var moexHolidaysCurrent = 0
            var moexWorkWeekCurrent = 0
            for (s in 0 until usHolidays.size) {
           //     Log.i("usHolidays", "${usHolidays[s]}")
                if (UtcCalendar.checkInRange(currentPoint, usHolidays[s]))
                    usHolidaysCurrent++
            }
            for (s in 0 until moexHolidays.size) {
           //     Log.i("moexHolidays", "${moexHolidays[s]}")
                if (UtcCalendar.checkInRange(currentPoint, moexHolidays[s]))
                    moexHolidaysCurrent++
            }
            for (s in 0 until moexWorkWeek.size) {
            //    Log.i("moexWorkWeek", "${moexWorkWeek[s]}")
                if (UtcCalendar.checkInRange(currentPoint, moexWorkWeek[s]))
                    moexWorkWeekCurrent++
            }
            marketES[_USA] = UtcCalendar.calculateWeekDays(currentPoint)-usHolidays.size
            marketES[_MOEX] = UtcCalendar.calculateWeekDays(currentPoint)-moexHolidays.size+moexWorkWeek.size
            marketES[_CRYPTO] = UtcCalendar().max(UtcCalendar.DAY_OF_YEAR)
        //    Log.i("COUNT", "${marketES[_USA]}/${marketES[_MOEX]}/${marketES[_CRYPTO]}")

            if (hash_list == null)
                hash_list = hashMapOf()
            if (stock_list == null)
                stock_list = arrayListOf()
            if (epicList1 == null)
                epicList1 = arrayOf()
            if (epicList2 == null)
                epicList2 = arrayOf()
            if (taskTotal == null)
                taskTotal = hashMapOf()
            //    if (taskTotal.isEmpty()) {
                if (!("api1" in taskTotal!!))
                    taskTotal!!["api1"] = ""
                if (!("api2" in taskTotal!!))
                    taskTotal!!["api2"] = ""
                if (!("special" in taskTotal!!))
                    taskTotal!!["special"] = "794C8241052B1DBD7CDD28DF495FDFE3^d6XIJMdQEQ1-Mpf29_FavZndZTr_Aq-0QA6tmna1lY-qFYztKxZCXYBpNUy3RcU4"
                if (!("usoffset" in taskTotal!!))
                    taskTotal!!["usoffset"] = "false"
        //    }
            if("scale" in taskTotal!!)
                GlobalTest = taskTotal!!["scale"].toBoolean()

            button1 = AES256.toText(button1, keyStr)
            hint1 = AES256.toText(hint1, keyStr)
            line1 = AES256.toText(line1, keyStr)
            line2 = AES256.toText(line2, keyStr)
            line3 = AES256.toText(line3, keyStr)
            special1 = AES256.toText(special1, keyStr)
            list1 = AES256.toText(list1, keyStr)
            hint2 = AES256.toText(hint2, keyStr)
            list2 = AES256.toText(list2, keyStr)
            list3 = AES256.toText(list3, keyStr)
            list4 = AES256.toText(list4, keyStr)
            button2 = AES256.toText(button2, keyStr)
            button3 = AES256.toText(button3, keyStr)
            special2 = AES256.toText(special2, keyStr)
            taskTypeList = root.resources.getStringArray(R.array.special_type)
            for (s in 0 until taskTypeList!!.size)
                taskTypeList!![s] = AES256.toText(taskTypeList!![s], keyStr)
            big_list = root.resources.getStringArray(R.array.big_list1)
            for (s in 0 until big_list!!.size)
                big_list!![s] = AES256.toText(big_list!![s], keyStr)

            try {
                val sqlResource = SQLResourceTable(root)
                token = sqlResource.getRecord("api1")
            }
            catch (e: java.lang.Exception) { }
        //    eclipseList = root.resources.getStringArray(R.array.eclipse_list)
        //    for (s in 0 until eclipseList.size)
        //        eclipseList[s] = AES256.toText(eclipseList[s], keyStr)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            root.maironAnnatar = if (maironSauron > 0) true else false
        //    Log.i("MAIRON", "${maironSauron}/${root.sauronInit}")
            when (maironSauron) {
            /*    0 -> {
                    val sqlStorage = SQLStorageTable(root)
                    val sqlAES = SQLAESTable(root)
                    root.outputGraphMessage("${hash_list.size}/${stock_list.size}/${epicList1.size}/${epicList2.size}/${taskTotal.size}/${sqlStorage.checkExist}/${sqlAES.checkExist}")
                }   */
                1 -> {
                    root.outputGraphMessage("Arda Creation")
                }
                2 -> {
                    root.outputGraphMessage("Epic Mairon")
                }
                3 -> {
                    //    Log.i("RECREATE", "FIRSTRUN")
                    val clipData = ClipData.newRawUri(null, root.uri1)
                    if (root.uri2 != null) {
                        clipData.addItem(ClipData.Item(root.uri2))
                    }
                    root.intent.clipData = clipData
                    root.intent.putExtra(_morgoth, root.taskMorgoth)
                    root.intent.putExtra(_mairon, false)
                    root.intent.putExtra(_sqlFunction, root.inputPrice)
                    root.intent.putExtra(_special, true)
                    root.recreate()
                }
            }
            root.hash_list = hash_list!!
            root.stock_list = stock_list!!
            root.totalResource = taskTotal!!
            root.bigList = big_list!!
            root.webToken = token

            if (!root.sauronInit) {
                val currencyValue = "dd/MM/YY".utcFormat(UtcDate(true), currentGreenwichOffset)
                root.object0810.text = currencyValue
                //root.object0806.filters = arrayOf<InputFilter>(AllCaps(), LengthFilter(9))
                //root.object0811.filters = arrayOf<InputFilter>(AllCaps(), LengthFilter(9))
                root.sauron_input.filters = arrayOf<InputFilter>(AllCaps(), LengthFilter(32))

                root.object0801.text = button1
                root.object0806.hint = "From"
                root.object0807.text = line1
                root.object0808.text = line2
                root.object0809.text = line3
                root.object0814.text = "DATA"
                root.object0815.text = "OUTPUT"
                root.sauron_input.hint = hint2
                root.object0803.text = list2
                root.object0804.text = list3
                root.object0803.text = list2
                root.object0805.text = list4
                root.object0818.text = button2
                root.object0819.text = button3
            }

            val spinner1: Spinner = root.findViewById(R.id.sauron_type) as Spinner
            val adapterSpinner1 = ArrayAdapter<String>(root,
                    R.layout.cast_tracks_chooser_dialog_row_layout, if (root.sauronInit) root.resources.getStringArray(R.array.sauron_list) else taskTypeList)
            adapterSpinner1.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinner1.adapter = adapterSpinner1

            val spinner2: Spinner = root.findViewById(R.id.sauron_task) as Spinner
            root.adapterSpinner2 = ArrayAdapter<String>(root,
                    R.layout.cast_tracks_chooser_dialog_row_layout, root.stock_list)
            root.adapterSpinner2.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinner2.adapter = root.adapterSpinner2
            spinner2.prompt = special2

            root.epicList1 = epicList1!!
            root.epicList2 = epicList2!!
            root.spinner_list = ArrayList<String>()

            val spinner3: Spinner = root.findViewById(R.id.sauron_list) as Spinner
            root.adapterSpinner3 = ArrayAdapter<String>(root,
                    R.layout.cast_tracks_chooser_dialog_row_layout, root.spinner_list)
            root.adapterSpinner3.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinner3.adapter = root.adapterSpinner3

            root.sauron_type.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    var setTaskType = _PARAM0
                    if (position in root.epicMap)
                        setTaskType = root.epicMap[position]!!
                    root.taskType = setTaskType
                    if (!root.sauronInit) {
                        when (setTaskType) {
                            _PARAM1 -> {
                                root.object0810.visibility = View.VISIBLE
                                root.sauron_list.visibility = View.GONE
                                root.sauron_row.weightSum = 4f
                                root.sauron_item1.visibility = View.VISIBLE
                                root.sauron_item2.visibility = View.VISIBLE
                                root.sauron_item3.visibility = View.VISIBLE
                                root.sauron_item4.visibility = View.VISIBLE
                                root.sauron_item5.visibility = View.GONE
                                root.sauron_item6.visibility = View.GONE
                                root.sauron_item7.visibility = View.GONE
                                root.sauron_item8.visibility = View.GONE
                            }
                            _PARAM2, _PARAM3 -> {
                                root.object0810.visibility = View.VISIBLE
                                root.sauron_list.visibility = View.GONE
                                root.sauron_row.weightSum = 0f
                                root.sauron_item1.visibility = View.GONE
                                root.sauron_item2.visibility = View.GONE
                                root.sauron_item3.visibility = View.GONE
                                root.sauron_item4.visibility = View.GONE
                                root.sauron_item3.visibility = View.GONE
                                root.sauron_item4.visibility = View.GONE
                                root.sauron_item5.visibility = View.GONE
                                root.sauron_item6.visibility = View.GONE
                                root.sauron_item7.visibility = View.GONE
                                root.sauron_item8.visibility = View.GONE
                            }
                            _PARAM4 -> {
                                root.object0810.visibility = View.GONE
                                root.sauron_list.visibility = View.VISIBLE
                                root.sauron_row.weightSum = 4f
                                root.sauron_item1.visibility = View.VISIBLE
                                root.sauron_item2.visibility = View.VISIBLE
                                root.sauron_item3.visibility = View.VISIBLE
                                root.sauron_item4.visibility = View.VISIBLE
                                root.sauron_item5.visibility = View.GONE
                                root.sauron_item6.visibility = View.GONE
                                root.sauron_item7.visibility = View.GONE
                                root.sauron_item8.visibility = View.GONE

                                root.spinner_list.clear()
                                for (k in 0 until root.epicList1.size) {
                                    root.spinner_list.add(root.epicList1[k])
                                }
                                root.adapterSpinner3.notifyDataSetChanged()
                                if (root.sauron_list.count > 1)
                                    root.sauron_list.setSelection(1)
                            }
                            _PARAM5 -> {
                                root.object0810.visibility = View.GONE
                                root.sauron_list.visibility = View.VISIBLE
                                root.sauron_row.weightSum = 2f
                                root.sauron_item1.visibility = View.VISIBLE
                                root.sauron_item2.visibility = View.VISIBLE
                                root.sauron_item3.visibility = View.GONE
                                root.sauron_item4.visibility = View.GONE
                                root.sauron_item5.visibility = View.GONE
                                root.sauron_item6.visibility = View.GONE
                                root.sauron_item7.visibility = View.GONE
                                root.sauron_item8.visibility = View.GONE

                                root.spinner_list.clear()
                                for (k in 0 until root.epicList1.size) {
                                    root.spinner_list.add(root.epicList1[k])
                                }
                                root.adapterSpinner3.notifyDataSetChanged()
                                if (root.sauron_list.count > 1)
                                    root.sauron_list.setSelection(1)
                            }
                            _PARAM6 -> {
                                root.object0810.visibility = View.GONE
                                root.sauron_list.visibility = View.VISIBLE
                                root.sauron_row.weightSum = 4f
                                root.sauron_item1.visibility = View.VISIBLE
                                root.sauron_item2.visibility = View.VISIBLE
                                root.sauron_item3.visibility = View.VISIBLE
                                root.sauron_item4.visibility = View.VISIBLE
                                root.sauron_item5.visibility = View.GONE
                                root.sauron_item6.visibility = View.GONE
                                root.sauron_item7.visibility = View.GONE
                                root.sauron_item8.visibility = View.GONE

                                root.spinner_list.clear()
                                for (k in 0 until root.epicList2.size) {
                                    root.spinner_list.add(root.epicList2[k])
                                }
                                root.adapterSpinner3.notifyDataSetChanged()
                                if (root.sauron_list.count > 0)
                                    root.sauron_list.setSelection(0)
                            }
                            _PARAM7 -> {
                                root.object0810.visibility = View.GONE
                                root.sauron_list.visibility = View.VISIBLE
                                root.sauron_row.weightSum = 8f
                                root.sauron_item1.visibility = View.VISIBLE
                                root.sauron_item2.visibility = View.VISIBLE
                                root.sauron_item3.visibility = View.VISIBLE
                                root.sauron_item4.visibility = View.VISIBLE
                                root.sauron_item5.visibility = View.VISIBLE
                                root.sauron_item6.visibility = View.VISIBLE
                                root.sauron_item7.visibility = View.VISIBLE
                                root.sauron_item8.visibility = View.VISIBLE

                                root.spinner_list.clear()
                                for (k in 0 until root.epicList2.size) {
                                    root.spinner_list.add(root.epicList2[k])
                                }
                                root.adapterSpinner3.notifyDataSetChanged()
                                if (root.sauron_list.count > 0)
                                    root.sauron_list.setSelection(0)
                            }
                            _PARAM8 -> {
                                root.object0810.visibility = View.GONE
                                root.sauron_list.visibility = View.VISIBLE
                                root.sauron_row.weightSum = 2f
                                root.sauron_item1.visibility = View.VISIBLE
                                root.sauron_item2.visibility = View.VISIBLE
                                root.sauron_item3.visibility = View.GONE
                                root.sauron_item4.visibility = View.GONE
                                root.sauron_item5.visibility = View.GONE
                                root.sauron_item6.visibility = View.GONE
                                root.sauron_item7.visibility = View.GONE
                                root.sauron_item8.visibility = View.GONE

                                root.spinner_list.clear()
                                for (k in 0 until root.epicList2.size) {
                                    root.spinner_list.add(root.epicList2[k])
                                }
                                root.adapterSpinner3.notifyDataSetChanged()
                                if (root.sauron_list.count > 0)
                                    root.sauron_list.setSelection(0)
                            }
                        }
                    }
                    else {
                    /*    when (position) {
                            0 -> {
                                root.sauron_input.setText("")
                                root.sauron_launch.text = "SAURON"
                                root.sauron_output.text = ""
                                root.sauron_image.setImageResource(R.drawable.sauron)
                                root.sauron_launch.setTextColor(Color.rgb(255, 0, 128))
                            }
                            1 -> {
                                root.sauron_input.setText("")
                                root.sauron_launch.text = "ANNATAR"
                                root.sauron_output.text = ""
                                root.sauron_image.setImageResource(R.drawable.annatar)
                                root.sauron_launch.setTextColor(Color.rgb(255, 100, 0))
                            }
                            2 -> {
                                root.sauron_input.setText("")
                                root.sauron_launch.text = "MAIRON"
                                root.sauron_output.text = ""
                                root.sauron_image.setImageResource(R.drawable.mairon)
                                root.sauron_launch.setTextColor(Color.rgb(0, 255, 255))
                            }
                        }
                        root.sauron_text.text = ""
                        root.sauron_item1.visibility = View.GONE
                        root.sauron_item2.visibility = View.GONE
                        root.sauron_item3.visibility = View.GONE
                        root.sauron_item4.visibility = View.GONE*/
                    }

                    root.sauron_item1.maxValue = 0
                    root.sauron_item1.minValue = 0
                    root.sauron_item1.displayedValues = arrayOf("0")
                    root.sauron_item2.maxValue = 0
                    root.sauron_item2.minValue = 0
                    root.sauron_item2.displayedValues = arrayOf("0")
                    root.sauron_item3.maxValue = 0
                    root.sauron_item3.minValue = 0
                    root.sauron_item3.displayedValues = arrayOf("0")
                    root.sauron_item4.maxValue = 0
                    root.sauron_item4.minValue = 0
                    root.sauron_item4.displayedValues = arrayOf("0")
                    root.sauron_item5.maxValue = 0
                    root.sauron_item5.minValue = 0
                    root.sauron_item5.displayedValues = arrayOf("0")
                    root.sauron_item6.maxValue = 0
                    root.sauron_item6.minValue = 0
                    root.sauron_item6.displayedValues = arrayOf("0")
                    root.sauron_item7.maxValue = 0
                    root.sauron_item7.minValue = 0
                    root.sauron_item7.displayedValues = arrayOf("0")
                    root.sauron_item8.maxValue = 0
                    root.sauron_item8.minValue = 0
                    root.sauron_item8.displayedValues = arrayOf("0")
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

            root.sauron_task.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    if (root.hash_list.size > 0 && !root.maironAnnatar) {
                        if (root.stock_list.size > position) {
                            var inputName = root.stock_list[position]
                            //Log.e("INPUT", "${inputName}")
                            if (inputName.isEmpty()) {
                                root.objectType = _OTHER
                                root.object0808.text = "VERIFY"
                                root.object0809.text = ""
                                return
                            }

                            if (inputName.contains('R')) {
                                if (inputName.length > 1) {
                                    var inputArray: List<Int>? = null
                                    if (inputName[0] == 'R')
                                        inputName = "1"+inputName
                                    try {
                                        inputArray = inputName.split("R").map { it.toInt() }
                                    }
                                    catch (e: Exception) { }
                                    //Log.e("ARRAY", "${inputArray?.get(0)}")
                                    if (inputArray != null) {
                                        if (inputArray.size == 2) {
                                            root.object0806.setText(inputArray[0].toString())
                                            root.object0811.setText(inputArray[1].toString())
                                        }
                                    }
                                }
                                else {
                                    root.objectType = _OTHER
                                    root.object0808.text = "VERIFY"
                                    root.object0809.text = ""
                                    return
                                }
                            }
                            else {
                                //root.object0806.setText("")
                                //root.object0811.setText("")
                                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            }

                            if (inputName in root.hash_list) {
                                val setObjectType = root.hash_list[inputName]!!.Type
                                root.objectType = setObjectType
                                //    Log.i("STOCK", "${inputName}/${setObjectType}")
                                var filterMap: Set<String>
                                try {
                                    filterMap = exchangeMap.filterValues { it == setObjectType }.keys
                                }
                                catch (e: Exception) {
                                    root.objectType = _OTHER
                                    root.object0808.text = "VERIFY"
                                    root.object0809.text = ""
                                    return
                                }

                                if (filterMap.size > 0) {
                                    root.object0807.text = "MMMM dd HH:mm".utcFormat(UtcDate(false, root.hash_list[inputName]!!.Date * 1000), currentGreenwichOffset)
                                    root.object0808.text = filterMap.elementAt(0)
                                    val outputStockName = root.hash_list[inputName]!!.Name
                                    if (root.hash_list[inputName]!!.Type == _OTHER)
                                        root.object0809.text = "IMAGE"
                                    else
                                        root.object0809.text = HtmlParse.compressString(outputStockName)
                                }
                            }
                            else {
                                root.objectType = _OTHER
                                root.object0808.text = "VERIFY"
                                root.object0809.text = ""
                            }
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

            //Verification!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            root.sauron_item1.displayedValues = arrayOf("0")
            root.sauron_item2.displayedValues = arrayOf("0")
            root.sauron_item3.displayedValues = arrayOf("0")
            root.sauron_item4.displayedValues = arrayOf("0")
            root.sauron_item5.displayedValues = arrayOf("0")
            root.sauron_item6.displayedValues = arrayOf("0")
            root.sauron_item7.displayedValues = arrayOf("0")
            root.sauron_item8.displayedValues = arrayOf("0")
            root.sendStatus(1)
        //    root.taskPrepare(true)
            root.registerTask(1)
        }
    }
//--------------------------------------  AsyncRequestBase ---------------------------------------
    private class RequestBase(thisActivity: Sauron, stock_index: String, task_type: Int, row: Int, exchange_map: HashMap<String, Int>) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<Sauron> = WeakReference(thisActivity)
        private val taskIndex: String
        private val requestType: Int
        private val rowIndex: Int
        private val taskExchangeMap: HashMap<String, Int>
        private var sqlResult: Boolean = false
        private var outputMessage: String = ""
        private var stockType: NameLogoRecord? = null

        init {
            this.taskIndex = stock_index
            this.requestType = task_type
            this.rowIndex = row
            this.taskExchangeMap = exchange_map
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val sqlTask = SQLTaskTable(root)
            when(requestType) {
                1 -> {
                    val sqlNameLogo = SQLNameLogoTable(root)
                    stockType = sqlNameLogo.getRecord(taskIndex)
                    if (stockType != null) {
                        val taskExchange = stockType!!.Exchange
                        if (taskExchange in taskExchangeMap)
                            sqlResult = sqlTask.addRecord(stockType!!.Title, stockType!!.Name, taskExchangeMap[taskExchange]!!)
                        else
                            outputMessage = "Insert/Stock Exchange"
                    } else {
                        outputMessage = "Insert/Global Base"
                    }

                }
                2 -> {
                    sqlResult = sqlTask.deleteRecord(taskIndex)
                }
                3 -> {
                    val taskOutput = sqlTask.updateRecord(taskIndex, false, "")
                    sqlResult = taskOutput > 0
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            if (sqlResult) {
                when (requestType) {
                    1 -> {
                        if (stockType != null) {
                            val taskExchange = stockType!!.Exchange
                            if (taskExchange in taskExchangeMap) {
                                root.hash_list[taskIndex] = TaskRecord(stockType!!.Name, UtcDate().time / 1000, false, "", taskExchangeMap[taskExchange]!!)
                                root.objectType = taskExchangeMap[taskExchange]!!

                                if (root.sauron_task.selectedItemPosition == 0) {
                                    root.object0808.text = taskExchange
                                    if (root.objectType == _OTHER)
                                        root.object0809.text = "IMAGE"
                                    else
                                        root.object0809.text = HtmlParse.compressString(stockType!!.Name)
                                }
                            }
                            else {
                                root.objectType = _OTHER
                                root.object0808.text = "VERIFY"
                                root.object0809.text = ""
                            }
                        }
                        else {
                            root.objectType = _OTHER
                            root.object0808.text = "VERIFY"
                            root.object0809.text = ""
                        }

                        root.stock_list.add(0, taskIndex)
                        root.adapterSpinner2.notifyDataSetChanged()                                                                                     //UPDATE 15.06.2021
                        root.stock_list_index = root.sauron_task.count - 1
                        if (root.sauron_task.count > 0)
                            root.sauron_task.setSelection(0)
                        //    root.adapterSpinner2.notifyDataSetChanged()
                        root.outputGraphMessage("Insert/$taskIndex")
                    }
                    2 -> {
                        if (rowIndex < root.stock_list.size)
                            root.stock_list.removeAt(rowIndex)
                        if (taskIndex in root.hash_list)
                            root.hash_list.remove(taskIndex)
                        root.adapterSpinner2.notifyDataSetChanged()

                        if (root.sauron_task.count > 0) {
                            root.sauron_task.setSelection(0)
                        } else {
                            root.object0807.text = ""
                            root.object0808.text = ""
                            root.object0809.text = ""
                        }

                        if (root.stock_list.size > 0) {
                            var input = root.stock_list[0]
                            var verifyItem: Boolean = true
                            if (input.contains('*')) {
                                if (input.length > 1) {
                                    input = input.substring(0, input.length - 1)
                                } else {
                                    verifyItem = false
                                }
                            }

                            if (input.isNotEmpty() && verifyItem) {
                                if (input in root.hash_list)
                                    root.objectType = root.hash_list[input]!!.Type
                                else
                                    root.objectType = _OTHER

                                if (root.sauron_task.count == 1 || root.sauron_task.selectedItemPosition == 0) {
                                    var filterMap: Set<String>
                                    try {
                                        filterMap = exchangeMap.filterValues { it == root.objectType }.keys
                                    } catch (e: Exception) {
                                        filterMap = emptySet()
                                    }
                                    if (filterMap.isNotEmpty() && input in root.hash_list) {
                                        root.object0807.text = "MMMM dd HH:mm".utcFormat(UtcDate(false, root.hash_list[input]!!.Date * 1000), currentGreenwichOffset)
                                        root.object0808.text = filterMap.elementAt(0)
                                        val outputStockName = root.hash_list[input]!!.Name
                                        if (root.hash_list[input]!!.Type == _OTHER)
                                            root.object0809.text = "IMAGE"
                                        else
                                            root.object0809.text = HtmlParse.compressString(outputStockName)
                                    }
                                }
                            } else {
                                root.objectType = _OTHER
                                root.object0808.text = "VERIFY"
                                root.object0809.text = ""
                            }
                        } else {
                            root.objectType = _OTHER
                            root.object0808.text = ""
                            root.object0809.text = ""
                            //Value -1
                        }
                    }
                    3 -> {
                        root.stock_list_index = rowIndex
                        var output_stock_name = taskIndex
                        val task_stock_name = taskIndex.indexOf("*")
                        if (task_stock_name >= 0 && task_stock_name < taskIndex.length)
                            output_stock_name = output_stock_name.substring(0, task_stock_name)

                        if (rowIndex < root.stock_list.size) {
                            root.stock_list.removeAt(rowIndex)
                            root.stock_list.add(output_stock_name)
                        }
                        root.adapterSpinner2.notifyDataSetChanged()
                        val sauron_task_item = root.sauron_task.count
                        if (sauron_task_item > 0)
                            root.sauron_task.setSelection(sauron_task_item - 1)

                    //    if (taskIndex in root.hash_list)
                    //        root.hash_list[taskIndex]!!.Date = UtcDate(true).time / 1000
                    //    root.object0807.text = "MMMM dd HH:mm".utcFormat(UtcDate(true), currentGreenwichOffset)
                    //    root.outputGraphMessage("Flow/$taskIndex")
                    }
                }
            }
            else {
                var outputStr = ""
                when (requestType) {
                    1 -> outputStr = "Insert/Verify Request"
                    2 -> outputStr = "Clear/Verify Request"
                    3 -> outputStr = "Flow/Verify Request"
                }
                root.outputGraphMessage(outputStr)
            }
            if (outputMessage.isNotEmpty()) {
                root.outputGraphMessage(outputMessage)
            }
            root.globalAnnatar(true)
            root.registerTask(1)
        }
    }
//---------------------------------------  AsyncTaskGraph ----------------------------------------
    private class GetGraph(thisActivity: Sauron, stockIndex: String, taskCalendar: UtcCalendar, api_value: String, us_offset: Boolean, task_type: Int, object_type: Int) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<Sauron> = WeakReference(thisActivity)
        private val taskIndex: String
        private val taskDate: UtcCalendar
        private val taskAPI: String
        private val taskUsOffset: Boolean
        private val taskTypeA: Int
        private val ObjectValue: Int
        private var pointList: List<DataPoint>? = null

        init {
            this.taskIndex = stockIndex
            this.taskDate = taskCalendar
            this.taskAPI = api_value
            this.taskUsOffset = us_offset
            this.taskTypeA = task_type
            this.ObjectValue = object_type
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null
            val businessLogic: IBusinessLogic = RealBusinessLogic()
            pointList = businessLogic.GetGraphArray(taskIndex, taskDate, taskAPI, taskUsOffset, taskTypeA, ObjectValue)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            if (!pointList.isNullOrEmpty()) {
                var taskRun: Boolean = true
                try {
                    val taskList = pointList!!.toList()
                    root.object0204.setData(taskList, ObjectValue, taskUsOffset, UtcDate().time)
                }
                catch (e: Exception) {
                    taskRun = false
                    root.sauron_output.text = "${e}/${e.message}"
                }

                if (taskRun) {
                    root.sauron_output.text = "Graph/Complete"
                    root.toggleGraphMessage(true)
                    if (!root.graphStart) {
                        root.graphStart = true
                    }
                //  calculatorFunction5
                }
            }
            else {
                root.outputGraphMessage("Graph/Price Array Length")
            }
            root.globalAnnatar(true)
            root.registerTask(1)
        }
    }
//------------------------------------  AsyncTaskInputArray --------------------------------------
    private class MorgothForce(thisActivity: Sauron, stock_name: String, precache: Boolean, morgoth_value: String, api1: String, api2: String, task_special: String, us_offset: Boolean, task_type: Int, object_type: Int, task_annatar: Float = 0f, task_super_1: Int = 0, task_super_2: Int = 0, task_mega: Int = 0, task_giga: Int = 0, task_spec_1: Int = 0, task_spec_2: Int = 0, task_spec_3: Int = 0, task_spec_4: String = "", task_phase: Int = 1, task_token: String = "", epic1: Array<String>? = null, epic2: Array<String>? = null, epic_type: Int = 0, epic_value: Long = 0L, param1: Int = 0, param2: Int = 0, param3: Int = 0, param4: Int = 0, annatar_value: String = "", annatar_array: FloatArray? = null) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<Sauron> = WeakReference(thisActivity)
        private val MorgothValue: String
        private val taskName: String
        private val inputType: Boolean
        private val taskAPI1: String
        private val taskAPI2: String
        private val epicSpecialValue: String
        private val taskUsOffset: Boolean
        private val taskTypeA: Int
        private val objectTypeA: Int
        private val taskAnnatar: Float
        private val AnnatarArray: FloatArray?
        private val taskSuperA: Int
        private val taskSuperB: Int
        private val taskMegaA: Int
        private val taskGiga: Int
        private val taskEpicList1: Array<String>?
        private val taskEpicList2: Array<String>?
        private val taskEpicItem: Int
        private val taskEpicValue: Long
        private val taskParam1: Int
        private val taskParam2: Int
        private val taskParam3: Int
        private val taskParam4: Int
        private val taskSpec1: Int
        private val taskSpec2: Int
        private val taskSpec3: Int
        private val taskSpec4: String
        private val taskPhase: Int
        private var token: String = ""

        private var stockName: String = ""
        private var stockLogo: String = "'"
        private var taskArray: Array<PricePoint>? = null
        private val taskAnnatarValue: String
        private var taskSpecialArray: Array<PricePoint>? = null
        private var taskMarket: Int = 0
        private var taskSpecialLength: Float = 0f
        private var taskCurrentPrice: PricePoint? = null
        private var outputResult: String = "INIT"

        init {
            this.taskName = stock_name
            this.inputType = precache
            this.MorgothValue = morgoth_value
            this.taskAPI1 = api1
            this.taskAPI2 = api2
            this.epicSpecialValue = task_special
            this.taskUsOffset = us_offset
            this.taskTypeA = task_type
            this.objectTypeA = object_type
            this.taskAnnatar = task_annatar
            this.AnnatarArray = annatar_array
            this.taskSuperA = task_super_1
            this.taskSuperB = task_super_2
            this.taskMegaA = task_mega
            this.taskGiga = task_giga
            this.taskEpicList1 = epic1
            this.taskEpicList2 = epic2
            this.taskEpicItem = epic_type
            this.taskEpicValue = epic_value
            this.taskParam1 = param1
            this.taskParam2 = param2
            this.taskParam3 = param3
            this.taskParam4 = param4
            this.taskSpec1 = task_spec_1
            this.taskSpec2 = task_spec_2
            this.taskSpec3 = task_spec_3
            this.taskSpec4 = task_spec_4
            this.taskPhase = task_phase
            this.token = task_token
            this.taskAnnatarValue = annatar_value
        }

        override fun doInBackground(vararg params: Void?): Void? {
        //    Log.i("CHECK", taskName)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val sqlTask = SQLTaskTable(root)
            val sqlNameLogo = SQLNameLogoTable(root)
            val taskPrecache = sqlTask.hasPrecache(taskName)
        //    try {
                val businessLogic: IBusinessLogic = RealBusinessLogic()
                if (taskTypeA == _PARAM0) {
                    val stockNameLogo = sqlNameLogo.getRecord(taskName, false, taskAnnatarValue)
                    if (stockNameLogo != null) {
                        stockName = stockNameLogo.Name
                        stockLogo = stockNameLogo.Logo
                    }
                    else {
                        stockName = "CATCH"
                        stockLogo = ""
                    }
                //    taskArray = ArrayList<PricePoint>().toTypedArray()
                }
            //    else if (taskPrecache.isEmpty() || inputType) {
                else if (taskPrecache.isEmpty() || inputType) {
                //    Log.i("OBJECT", "<${objectTypeA}>")
                    val taskObject = businessLogic.GetGlobalArray(taskName.replace("_", ""), taskAPI1, objectTypeA)
                    if (taskObject != null) {
                        if (inputType) {
                        //    Log.i("PRECACHE", "${taskObject.length()}")
                            if (taskObject.length() > 0) {
                                val sqlResult = sqlTask.updateRecord(taskName, true, taskObject.toString())
                                if (sqlResult <= 0)
                                    outputResult = "Precache/SQL"
                            }
                            else {
                                outputResult = "Precache/Length"
                            }
                        } else {
                            //    stockName = root.sqlName.getName(taskName)
                            val outputResult = businessLogic.ConvertJSON(taskObject, objectTypeA)
                            taskArray = outputResult.first
                            taskMarket = outputResult.second
                        }
                    }
                    else {
                        if (inputType)
                            outputResult = "Precache/Internet"
                    //    taskArray = ArrayList<PricePoint>().toTypedArray()
                        taskMarket = 0
                    }
                }
                else {
                    //    stockName = root.sqlName.getName(taskName)
                    var taskJSON: JSONObject? = null
                    try {
                        taskJSON = JSONObject(taskPrecache)
                    }
                    catch(e: Exception) { }

                    if (taskJSON != null) {
                        val outputResult = businessLogic.ConvertJSON(JSONObject(taskPrecache), objectTypeA)
                        taskArray = outputResult.first
                        taskMarket = outputResult.second
                    }
                }

                if (!inputType) {
                    val stockNameLogo = sqlNameLogo.getRecord(taskName, false, taskAnnatarValue)
                    if (stockNameLogo != null) {
                        stockName = stockNameLogo.Name
                        stockLogo = stockNameLogo.Logo
                    } else {
                        stockName = "CATCH"
                        stockLogo = ""
                    }

                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    if (taskEpicList1 != null && taskEpicList2 != null) {
                        when (taskTypeA) {
                            _PARAM2, _PARAM3 -> {
                                val outputSpecial = businessLogic.GetSpecialArray(taskName.replace("_", ""), taskAPI2, taskTypeA, objectTypeA, taskUsOffset, taskSuperA == 1)
                                taskSpecialArray = outputSpecial.first
                                taskSpecialLength = outputSpecial.second
                            }
                            _PARAM4, _PARAM5 -> {
                                val outputSpecial = businessLogic.GetSpecialArray(taskName.replace("_", ""), taskAPI2, taskTypeA, objectTypeA, taskUsOffset, taskSuperA == 1, taskEpicList1, taskEpicItem)
                                taskSpecialArray = outputSpecial.first
                                taskSpecialLength = outputSpecial.second
                            }
                            _PARAM6, _PARAM7, _PARAM8 -> {
                                val outputSpecial = businessLogic.GetSpecialArray(taskName.replace("_", ""), taskAPI2, taskTypeA, objectTypeA, taskUsOffset, taskSuperA == 1, taskEpicList2, taskEpicItem)
                                taskSpecialArray = outputSpecial.first
                                taskSpecialLength = outputSpecial.second
                            }
                        }
                        /*    if (taskTypeA in intArrayOf(_PARAM2, _PARAM3)) {
                                if (objectType == _USA)
                                    taskCurrentPrice = businessLogic.GetCurrentPrice(taskName, taskAPI2, objectType!!, taskUsOffset)
                            }   */
                        if (taskTypeA in intArrayOf(_PARAM1, _PARAM4, _PARAM5, _PARAM6, _PARAM7, _PARAM8)) {
                            taskCurrentPrice = businessLogic.GetCurrentPrice(taskName.replace("_", ""), taskAPI2, objectTypeA, taskUsOffset)
                        }
                    }
                }
        //    }
        //    catch (e: Exception) {
        //        outputResult = "${e.toString()}/${e.message.toString()}"
        //    }
            if (outputResult == "INIT")
                outputResult = ""
            if (taskArray == null)
                taskArray = ArrayList<PricePoint>().toTypedArray()
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            var verifyItem: Boolean = true
            if (outputResult.isNotEmpty()) {
                if (inputType && taskName in root.hash_list) {
                    if (root.hash_list[taskName]!!.Precache) {
                        root.stock_list[root.stock_list_index] = root.stock_list[root.stock_list_index] + "*"
                        root.adapterSpinner2.notifyDataSetChanged()
                    }
                }
                if (outputResult == "INIT")
                    outputResult = "Morgoth/Verify Request"
                root.outputGraphMessage(outputResult)
                root.globalAnnatar(true)
            }
            else {
                if (inputType) {
                    if (taskName in root.hash_list) {
                    //    Log.i("PRECACHE", "SET/${root.stock_list_index}/${root.stock_list[root.stock_list_index]}")
                        root.hash_list[taskName]!!.Precache = true
                        root.hash_list[taskName]!!.Date = UtcDate(true).time / 1000
                        if (root.stock_list_index < root.stock_list.size)
                            root.stock_list[root.stock_list_index] = root.stock_list[root.stock_list_index] + "*"
                        root.adapterSpinner2.notifyDataSetChanged()
                    }

                    if (root.stock_list_index < root.stock_list.size) {
                        root.stock_list.removeAt(root.stock_list_index)
                        root.stock_list.add(0, taskName + "*")
                    }
                    root.adapterSpinner2.notifyDataSetChanged()
                    if (root.sauron_task.count > 0)
                        root.sauron_task.setSelection(0)

                    root.object0807.text = "MMMM dd HH:mm".utcFormat(UtcDate(true), currentGreenwichOffset)
                    root.outputGraphMessage("Precache/Complete")
                    root.globalAnnatar(true)
                }
                else if (AnnatarArray != null) {
                    if (AnnatarArray.size > 7) {
                        //    Log.i("NAME", stockName)
                        //    Log.i("LOGO", stockLogo)
                        var AnnatarSet: FloatArray
                        when (taskTypeA) {
                            _PARAM5, _PARAM8 -> AnnatarSet = floatArrayOf(AnnatarArray[0], AnnatarArray[1])
                            _PARAM1, _PARAM4, _PARAM6 -> AnnatarSet = floatArrayOf(AnnatarArray[0], AnnatarArray[1], AnnatarArray[2], AnnatarArray[3])
                            _PARAM7 -> AnnatarSet = floatArrayOf(AnnatarArray[0], AnnatarArray[1], AnnatarArray[2], AnnatarArray[3], AnnatarArray[4], AnnatarArray[5], AnnatarArray[6], AnnatarArray[7])
                            _PARAM2 -> AnnatarSet = floatArrayOf(1f, 2f, 3f, 4f)
                            _PARAM3 -> AnnatarSet = floatArrayOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f)
                            else -> AnnatarSet = floatArrayOf()
                        }

                        val logoInsert: String = ""
                    //    if (taskTypeA == _OTHER)
                            TargetLogic.targetLogo = stockLogo
                    //    else
                    //        logoInsert = stockLogo
                        val paramObject = ImageParam(taskTypeA, objectTypeA, taskName, stockName, logoInsert, taskEpicValue, taskParam1, taskParam2, taskParam3, 0, taskMegaA, taskMarket, taskAnnatar, taskCurrentPrice, AnnatarSet, taskArray, taskSpecialArray, taskSpecialLength, root.taskEpic, taskParam4, taskSuperA, taskSuperB, taskGiga, taskSpec1, taskSpec2, taskSpec3, taskSpec4, token, taskPhase)
                        val intentLaunch = Intent(root, TargetLogic::class.java)
                        intentLaunch.putExtra(_mairon, true)
                        intentLaunch.putExtra(_paramObject, paramObject)
                        intentLaunch.putExtra(_morgoth, MorgothValue)
                        intentLaunch.putExtra(_inputValue, epicSpecialValue)
                        intentLaunch.putExtra(_api, taskAPI2)
                        intentLaunch.putExtra(_usOffset, taskUsOffset)

                        root.sauron_item1.maxValue = 0
                        root.sauron_item1.minValue = 0
                        root.sauron_item1.displayedValues = arrayOf("0")
                        root.sauron_item2.maxValue = 0
                        root.sauron_item2.minValue = 0
                        root.sauron_item2.displayedValues = arrayOf("0")
                        root.sauron_item3.maxValue = 0
                        root.sauron_item3.minValue = 0
                        root.sauron_item3.displayedValues = arrayOf("0")
                        root.sauron_item4.maxValue = 0
                        root.sauron_item4.minValue = 0
                        root.sauron_item4.displayedValues = arrayOf("0")
                        root.sauron_item5.maxValue = 0
                        root.sauron_item5.minValue = 0
                        root.sauron_item5.displayedValues = arrayOf("0")
                        root.sauron_item6.maxValue = 0
                        root.sauron_item6.minValue = 0
                        root.sauron_item6.displayedValues = arrayOf("0")
                        root.sauron_item7.maxValue = 0
                        root.sauron_item7.minValue = 0
                        root.sauron_item7.displayedValues = arrayOf("0")
                        root.sauron_item8.maxValue = 0
                        root.sauron_item8.minValue = 0
                        root.sauron_item8.displayedValues = arrayOf("0")

                        root.activitySleep = true
                        root.startActivityForResult(intentLaunch, 1)
                    }
                    else {
                        verifyItem = false
                    }
                }
                else {
                    verifyItem = false
                }
                if (!verifyItem) {
                    root.outputGraphMessage("Morgoth/Annatar Length")
                    root.globalAnnatar(true)
                }
            }
            root.registerTask(1)
        }
    }
//--------------------------------------  AsyncTaskSetPrice --------------------------------------
    private class AnnatarForce(thisActivity: Sauron, input1: Float, input2: Float, input3: Int) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<Sauron> = WeakReference(thisActivity)
        private val taskValue: Float
        private val taskEpic: Float
        private val taskType: Int
        private var taskSize: Int = 0
        private var AnnatarValue1: Float = 0f
        private var AnnatarValue2: Float = 0f
        private var AnnatarValue3: Float = 0f
        private var AnnatarValue4: Float = 0f
        private var AnnatarValue5: Float = 0f
        private var AnnatarValue6: Float = 0f
        private var AnnatarValue7: Float = 0f
        private var AnnatarValue8: Float = 0f
        private var AnnatarSpecial: Float = 1f
        private var AnnatarLength: Int = 0

        init {
            this.taskValue = input1
            this.taskEpic = input2
            this.taskType = input3
        }

        override fun doInBackground(vararg params: Void?): Void? {
        /*    when {
                taskValue > 150 || taskType in specialList3 -> {
                    AnnatarSpecial = 1f
                    AnnatarLength = 0
                }
                taskValue <= 150 && taskValue > 35 -> {
                    AnnatarSpecial = 0.1f
                    AnnatarLength = 1
                }
                taskValue <= 35 && taskValue > 5 -> {
                    AnnatarSpecial = 0.01f
                    AnnatarLength = 2
                }
            }   */
            val AnnatarInterval = 20f * taskValue * taskEpic
            when {
                AnnatarInterval > 240000f -> {
                    AnnatarSpecial = 100f
                    AnnatarLength = -100
                }
                AnnatarInterval <= 240000f && AnnatarInterval > 120000f -> {
                    AnnatarSpecial = 50f
                    AnnatarLength = -50
                }
                AnnatarInterval <= 120000f && AnnatarInterval > 60000f -> {
                    AnnatarSpecial = 25f
                    AnnatarLength = -25
                }
                AnnatarInterval <= 60000f && AnnatarInterval > 20000f -> {
                    AnnatarSpecial = 15f
                    AnnatarLength = -15
                }
                AnnatarInterval <= 20000f && AnnatarInterval > 1500f -> {
                    AnnatarSpecial = 5f
                    AnnatarLength = -5
                }
                AnnatarInterval <= 1500f && AnnatarInterval > 150f -> {
                    AnnatarSpecial = 1f
                    AnnatarLength = 0
                }
                AnnatarInterval <= 150f && AnnatarInterval > 30f -> {
                    AnnatarSpecial = 0.1f
                    AnnatarLength = 1
                }
                AnnatarInterval <= 30f && AnnatarInterval > 5f -> {
                    AnnatarSpecial = 0.01f
                    AnnatarLength = 2
                }
                AnnatarInterval <= 5f && AnnatarInterval > 0.5f -> {
                    AnnatarSpecial = 0.001f
                    AnnatarLength = 3
                }
                AnnatarInterval <= 0.5f -> {
                    AnnatarSpecial = 0.0001f
                    AnnatarLength = 4
                }
            }
            try {
                taskSize = (0.4f * (taskValue * taskEpic) / AnnatarSpecial).roundToInt().coerceAtLeast(11)
            }
            catch (e: Exception) { }

        //    Log.i("AnnatarLength", "taskSize=$taskSize Parameters=$AnnatarInterval/$AnnatarSpecial/$AnnatarLength")
            when (taskType) {
                _PARAM7 -> {
                    AnnatarValue1 = ((1 + 7 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue2 = ((1 + 5 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue3 = ((1 + 3 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue4 = ((1 + 1 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue5 = ((1 - 1 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue6 = ((1 - 3 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue7 = ((1 - 5 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue8 = ((1 - 7 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                }
                _PARAM1, _PARAM4, _PARAM6 -> {
                    AnnatarValue1 = ((1 + 3 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue2 = ((1 + 1 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue3 = ((1 - 1 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                    AnnatarValue4 = ((1 - 3 * taskEpic / 2f) * taskValue).round(AnnatarLength)
                }
                _PARAM8, _PARAM5 -> {
                    AnnatarLength = 1
                    AnnatarValue1 = (2 * taskValue.roundToInt()).toFloat()
                    if (!AnnatarValue1.isFinite())
                        AnnatarValue1 = 1f
                    AnnatarValue2 = 1f
                }
                _PARAM2, _PARAM3 -> { }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            when (taskType) {
                _PARAM7 -> {
                    root.sauron_item1.maxValue = 0
                    root.sauron_item1.minValue = 0
                    root.sauron_item1.displayedValues = arrayOf("0")
                    root.sauron_item2.maxValue = 0
                    root.sauron_item2.minValue = 0
                    root.sauron_item2.displayedValues = arrayOf("0")
                    root.sauron_item3.maxValue = 0
                    root.sauron_item3.minValue = 0
                    root.sauron_item3.displayedValues = arrayOf("0")
                    root.sauron_item4.maxValue = 0
                    root.sauron_item4.minValue = 0
                    root.sauron_item4.displayedValues = arrayOf("0")
                    root.sauron_item5.maxValue = 0
                    root.sauron_item5.minValue = 0
                    root.sauron_item5.displayedValues = arrayOf("0")
                    root.sauron_item6.maxValue = 0
                    root.sauron_item6.minValue = 0
                    root.sauron_item6.displayedValues = arrayOf("0")
                    root.sauron_item7.maxValue = 0
                    root.sauron_item7.minValue = 0
                    root.sauron_item7.displayedValues = arrayOf("0")
                    root.sauron_item8.maxValue = 0
                    root.sauron_item8.minValue = 0
                    root.sauron_item8.displayedValues = arrayOf("0")
                    getAnnatar(root.sauron_item1, root.sauron_item8, AnnatarValue1, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item2, root.sauron_item7, AnnatarValue2, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item3, root.sauron_item6, AnnatarValue3, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item4, root.sauron_item5, AnnatarValue4, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item5, null, AnnatarValue5, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item6, null, AnnatarValue6, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item7, null, AnnatarValue7, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item8, null, AnnatarValue8, AnnatarSpecial, AnnatarLength, taskSize)
                }
                _PARAM1, _PARAM4, _PARAM6 -> {
                    root.sauron_item1.maxValue = 0
                    root.sauron_item1.minValue = 0
                    root.sauron_item1.displayedValues = arrayOf("0")
                    root.sauron_item2.maxValue = 0
                    root.sauron_item2.minValue = 0
                    root.sauron_item2.displayedValues = arrayOf("0")
                    root.sauron_item3.maxValue = 0
                    root.sauron_item3.minValue = 0
                    root.sauron_item3.displayedValues = arrayOf("0")
                    root.sauron_item4.maxValue = 0
                    root.sauron_item4.minValue = 0
                    root.sauron_item4.displayedValues = arrayOf("0")
                    getAnnatar(root.sauron_item1, root.sauron_item4, AnnatarValue1, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item2, root.sauron_item3, AnnatarValue2, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item3, null, AnnatarValue3, AnnatarSpecial, AnnatarLength, taskSize)
                    getAnnatar(root.sauron_item4, null, AnnatarValue4, AnnatarSpecial, AnnatarLength, taskSize)
                }
                _PARAM5, _PARAM8 -> {
                    root.sauron_item1.maxValue = 0
                    root.sauron_item1.minValue = 0
                    root.sauron_item1.displayedValues = arrayOf("0")
                    root.sauron_item2.maxValue = 0
                    root.sauron_item2.minValue = 0
                    root.sauron_item2.displayedValues = arrayOf("0")
                    //    Log.i("POW1", 2.0.pow(log2(price1.toInt() - 0.5f).roundToInt() + 1).toString())
                    //    Log.i("POW2", (truncate(log2(price1.toInt() - 0.5f)).toInt() + 1).toString())
                    //    Log.i("POW3", (price1.toInt() - 0.5f).toString())
                    //    val listLength = 2.0.pow(truncate(log2(AnnatarValue1.toInt() - 0.5f)).toInt()).toInt()
                    val listLength = 11
                    getAnnatar(root.sauron_item1, null, AnnatarValue1, 1f, AnnatarLength, listLength)
                    getAnnatar(root.sauron_item2, null, AnnatarValue2, 0f, AnnatarLength, 1)
                }
                _PARAM2, _PARAM3 -> { }
            }
            root.globalAnnatar(true)
            root.registerTask(1)
        }

        fun getAnnatar(param1: NumberPicker, param2: NumberPicker?, param3: Float, param4: Float, param5: Int, param6: Int) {
            var specialArray = ArrayList<String>()
            for (k in 0 until param6) {
                specialArray.add(((param6 / 2 + 1 - k) * param4 + param3).round(param5).toString())
            }

            param1.wrapSelectorWheel = false
            param1.displayedValues = specialArray.toTypedArray()
            param1.maxValue = param6 - 1
            param1.minValue = 0
            param1.value = param6 / 2

            if (param2 != null) {
                param1.setOnValueChangedListener(OnValueChangeListener { numberPicker, i, i1 ->
                    val taskObject: Int = param1.value
                    if (param2.value != taskObject)
                        param2.value = param6 - taskObject
                })
            }
        }
        fun Float.round(decimals: Int = 0): Float = if (decimals >= 0) "%.${decimals}f".format(this).toFloat().toFinite() else this - this % (-1 * decimals)
    }
//------------------------------------------ Special --------------------------------------------
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event == null)
            return true
        var result: Boolean = false
        when (event.keyCode) {
            KeyEvent.KEYCODE_MENU -> result = true
            KeyEvent.KEYCODE_VOLUME_UP -> result = true
            KeyEvent.KEYCODE_VOLUME_DOWN -> result = true
            KeyEvent.KEYCODE_BACK -> {
                result = true
                if (!activityTransfer) {
                    this.finishAndRemoveTask()
                    exitProcess(0)
                }
            }
            KeyEvent.KEYCODE_HOME -> result = true
        }
        return result
    }
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        /*if (!activitySleep) {
            val currentMoment = UtcDate().time
            if (currentMoment - activityStart > 15000) {
                this.finishAndRemoveTask()
                exitProcess(0)
            }
        }*/
        if (activityTransfer) {
            try {
                val notificationIntent = Intent(this, Sauron::class.java)
                notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
                pendingIntent.send()
            } catch (e: PendingIntent.CanceledException) {
                //    e.printStackTrace()
            }
        }
    }
//------------------------------------------ SauronStorage ------------------------------------------
    public fun SauronStorage(root: Sauron, task_morgoth: String) {
        val sqlNameLogo = SQLNameLogoTable(root)
        sqlNameLogo.recreate()
        sqlNameLogo.addUpdateRecord("TEST", "OTHER", "TEST", "", "", "")
        sqlNameLogo.addUpdateRecord("R100", "OTHER", "1-100", "", "", "")
        //sqlNameLogo.addUpdateRecord("1000R10000", "OTHER", "1000-10000", "", "", "")
        //    SQLNameBase().Insert(root.sqlNameLogo)

        val sqlResource = SQLResourceTable(root)
        sqlResource.recreate()
        //IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        sqlResource.addRecord("special", "100")
        sqlResource.addRecord("usoffset", "81242780AFD269A71B2D2F7C0FB9ACF3^iTXIiMQyz0DAzaPliKO6seudSZyh0Xgth7WtiG_SXoK4fXDXk3K_5H7RcWZ4WvzU")
        sqlResource.addRecord("request", "B177938F26DFB167C4656AC56C06FC7F^Ygs1ZmU8oXCD-qdoi3kArTA1qCnD1Wy4RwToM2ayj8Ljn8RYz2i8gq-pb4VDdRqZZq7wE3aiFMN-1zx_xF6X9nPnV-__NMEPATE-2aBisAOhUk4-Dhk3EixD_Jpc24xfG8pOrEcKE9tMQtIshKdmsYzKBmN-OfDOVxXHZEhqhhw=")
        sqlResource.addRecord("api1", "ACC6B771321E59B15CF287FACD900F20^JjuOcgFFl95TF5qxGmxR66BjV7LNzeiW5rFTpL-WPpkiNYgjKt1_VFbBQM84XMA_DOaq91YPOnFTOfqJpw6jJA==")
        //MLUTPROB84XKDMCW
        sqlResource.addRecord("api2", "1B393484147B282F075C2E4DD388BBCA^rRQRQfHuaH6yBBgGsEfjTfnJwYc7-MY9AKXOmr9W5lub0fXDwKmYmLlosT93_4CKl2L29a56cFC9UdBG6B1cNwKf6XcwljnBsjLUOVmau4o=")
        //d1076cc401bd45a9ee41b87de4c35259
        sqlResource.addRecord("api3", "1833524C31FD86E0522DA6134708DFCA^Yt_-_0PbZMkf8GVAb3fYYJzvBIxoq7zhIH_oUXcjH7gb9mbQNPz5Hw4y3EHVF13kgUkk7dA03je3UqY2uR4e9x5RG5yoeh_PaWP3wME7XHY=")
        //5135e17cb38b9d4b95db2ec0d97c6811

        var taskTotal = ""
        val keyStr: ByteArray = AES256.getRawKey(task_morgoth)
        try {
            taskTotal = sqlResource.getRecord("request")
            //    taskTotal = sqlResource.allRecords
            //    taskTotal!!.forEach { (key, value) -> taskTotal!![key] = AES256.toText(value, keyStr) }
        }
        catch (e: Exception) {
            /*    taskTotal = hashMapOf()
                taskTotal!!["api1"] = ""
                taskTotal!!["api2"] = ""
                taskTotal!!["usoffset"] = "false"   */
        }

        //    val sqlStorage = SQLStorageTable(root)
        //    sqlStorage.recreate()
        //    sqlStorage.addRecord(null, "23:00 14.05.2021","0", "0", "4", "[1.0,2,4.2,9.4]", "200", "5", "10", "[0,1,2,3,0,1,2,3,0,1]", "BMY", "[AA,ABSOLUTELY PERFECT,super bright targeting,TRUST THIS]")
        //    sqlStorage.addRecord(null, "Full Moon 14.05.2021","0", "1", "4", "[1.0,2,4.2,9.4]", "200", "0", "10", "[9,8,7,6,5,4,3,2,1,0]", "GMKN", "[HH!,really perfect task understanding,EPIC MEGA MORGOTH POWERFUL EVENT]")
        //    sqlStorage.addRecord(null, "Solar Eclipse 14.05.2021","0", "1", "8", "[1.0,2,4.2,9.4,15,19,24.2,30.6]", "400", "14", "15", "[0,1,2,3,4,5,6,7,0,1,2,3,4,5,6]", "BTC", "[M?,some slow event,Heartily Sauron Force]")

        //VERIFY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        val sqlAES = SQLAESTable(root)
        sqlAES.createExist()
        //    SQLStorageTable(root).createExist()

        //Request!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        val sqlTask = SQLTaskTable(root)
        sqlTask.recreate()
        //sqlTask.addRecord("1000R10000", "1000-10000", _OTHER)
        sqlTask.addRecord("R100", "1-100", _OTHER)
        sqlTask.addRecord("TEST", "TEST", _OTHER)

        val sqlBig = SQLBigTable(root)
        sqlBig.recreate()
        //    SQLBigBase().Insert(sqlBig)

        val sqlSchedule = SQLScheduleTable(root)
        sqlSchedule.recreate()

        val sqlStorage = SQLStorageTable(root)
        sqlStorage.recreate()
    }
//------------------------------------------ Calculator ------------------------------------------
    var isNewOp=true
    var oldNumber=""
    var dot=false
    var op=""
    fun calculatorFunction1(view: View?)
    {
        if (activitySleep)
            return
        if(isNewOp)
            object0301.setText("")
        isNewOp=false
        val buSelect = view as Button
        var buClickValue: String = object0301.text.toString()
    //    Log.i("CALC", "${buClickValue.length}/${buSelect.id}/${object0314.id}")
        if (buClickValue.length == 1) {
            if (buClickValue[0] == '0' && buSelect.id != object0314.id)
                return
        }
        if (buClickValue.isNotEmpty()) {
            if (buClickValue == "Infinity" || buClickValue == "Number" || buClickValue == "Size" || buClickValue == "Exception")
                buClickValue = ""
        }
        when(buSelect.id)
        {
            object0315.id -> {
                buClickValue += "0"
            }
            object0312.id -> {
                buClickValue += "1"
            }
            bu2.id -> {
                buClickValue += "2"
            }
            object0313.id -> {
                buClickValue += "3"
            }
            bu4.id -> {
                buClickValue += "4"
            }
            object0311.id -> {
                buClickValue += "5"
            }
            bu6.id -> {
                buClickValue += "6"
            }
            object0308.id -> {
                buClickValue += "7"
            }
            object0309.id -> {
                buClickValue += "8"
            }
            object0310.id -> {
                buClickValue += "9"
            }
            object0314.id -> {
                if (dot == false) {
                    buClickValue += "."
                }
                dot = true
            }
            /*    buPlusMinus.id->
                {
                    val length = buClickValue.length
                    if (length > 0) {
                        if (!buClickValue.contains('-')) {
                            buClickValue = "-" + buClickValue
                        } else {
                            buClickValue = buClickValue.substring(1, length)
                        }
                    }
                }   */
        }
        object0301.setText(buClickValue)
    }
    fun calculatorFunction2(view: View?)
    {
        if (activitySleep)
            return
        val taskNumber = object0301.text.toString()
        if (taskNumber.length > 0) {
            if (taskNumber.contains('.') && taskNumber.length == 1)
                return
            val buSelect = view as Button
            when (buSelect.id) {
                object0306.id -> {
                    op = "X"
                }
                object0302.id -> {
                    op = "÷"
                }
                object0303.id -> {
                    op = "-"
                }
                object0304.id -> {
                    op = "+"
                }
            }
            setColor()
            buSelect.setBackgroundColor(Color.rgb(165, 244, 110))
            if (oldNumber.isEmpty()) {
                oldNumber = taskNumber
                isNewOp = true
                dot = false
            }
        }
    }
    fun calculatorFunction3(view: View?)
    {
        if (activitySleep)
            return
        val newNumber = object0301.text.toString()
        if (oldNumber.isNotEmpty() && newNumber.isNotEmpty()) {
            if (oldNumber.contains('.') && oldNumber.length == 1)
                return
            if (newNumber.contains('.') && newNumber.length == 1)
                return
            var taskNumber1 = 0.0
            var taskNumber2 = 0.0
            try {
                taskNumber1 = oldNumber.toDouble()
                taskNumber2 = newNumber.toDouble()
            }
            catch (e: Exception) {
                clearCalculator()
                object0301.setText("Number")
                return
            }
            if (!taskNumber1.isFinite() || !taskNumber2.isFinite()) {
                clearCalculator()
                object0301.setText("Finite")
                return
            }

            var finalNumber: Double? = null
            when (op) {
                "X" -> {
                    finalNumber = taskNumber1 * taskNumber2
                }
                "÷" -> {
                    finalNumber = taskNumber1 / taskNumber2
                }
                "-" -> {
                    finalNumber = taskNumber1 - taskNumber2
                }
                "+" -> {
                    finalNumber = taskNumber1 + taskNumber2
                }
            }

            clearCalculator()
            if (finalNumber != null) {
                var outputResult = finalNumber.toString()
                if (outputResult.length < 10) {
                    object0301.setText(outputResult)
                }
                else {
                    val zeroPos = outputResult.indexOf('.')
                    if (zeroPos > -1 && zeroPos < 9) {
                        try {
                            var exp = outputResult.indexOf('E')
                            var expStr: String = ""
                            //    Log.i("Calculator", "$outputResult")
                            if (exp > -1 && exp < outputResult.length - 1) {
                                expStr = outputResult.substring(exp, outputResult.length)
                                if (expStr.length >= 9)
                                    expStr.substring(0, 8)
                            }
                            outputResult = outputResult.substring(0, if (exp > -1 && exp < outputResult.length - 1) 9 - expStr.length else 9) + expStr
                            object0301.setText(outputResult)
                        }
                        catch (e: Exception) {
                            object0301.setText("Exception")
                        }
                    }
                    else {
                        object0301.setText("Size")
                    }
                }
            }
            else {
                object0301.setText("Null")
            }
        }
    }
    fun calculatorFunction4(view: View?)
    {
        if (activitySleep)
            return
        val length = object0301.text.length
        if (length > 0 && !isNewOp) {
            if (object0301.text[length - 1] == '.')
                dot = false
            var outputText: String = ""
            if (length > 1)
                outputText = object0301.text.substring(0, length - 1)
            object0301.setText(outputText)
        //    if (length == 1)
        //        setColor()
        }
        //    val number=(etShowNumber.text.toString().toDouble())/100
        //    etShowNumber.setText(number.toString())
        //    isNewOp=true
    }
    fun calculatorFunction5(view: View?)
    {
        if (activitySleep)
            return
        clearCalculator()
    }
    private fun clearCalculator() {
        object0301.setText("")
        setColor()
        isNewOp=true
        oldNumber = ""
        dot=false
        op=""
    }
    private fun setColor() {
        object0302.setBackgroundColor(resources.getColor(R.color.color06, this.theme))
        object0303.setBackgroundColor(resources.getColor(R.color.color06, this.theme))
        object0304.setBackgroundColor(resources.getColor(R.color.color06, this.theme))
        object0306.setBackgroundColor(resources.getColor(R.color.color06, this.theme))
    }
    private fun getPrecision(task: Double): Double {
        try {
            val result = BigDecimal(task).toDouble()
            return if (result.isFinite()) result else 0.0
        }
        catch (e: Exception) {
            return 0.0
        }
    }
}