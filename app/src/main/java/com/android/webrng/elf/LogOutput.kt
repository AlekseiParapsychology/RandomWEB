package com.android.webrng.elf

import android.app.*
import android.app.PendingIntent.CanceledException
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.support.v4.app.FragmentActivity
import android.text.InputFilter
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.INVALID_ROW_ID
import com.android.webrng.R
import com.android.webrng.constants.*
import com.android.webrng.sql.*
import com.android.webrng.utils.*
import kotlinx.android.synthetic.main.notification_template_big_media_custom.*
import org.json.JSONArray
import java.io.File
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class LogOutput : FragmentActivity() {
    private var objectSycnhronize: Long = 1L
    private var taskMorgoth: String = ""
    private var logInit: Int = 0
    private lateinit var adapterSpinner1: ArrayAdapter<String>
    private lateinit var adapterSpinner2: ArrayAdapter<String>

    private lateinit var resourceList: HashMap<String, String>
    private lateinit var epicList: Array<String>
    private lateinit var bigList: Array<String>
    private lateinit var mairon_list: ArrayList<String>
    private lateinit var short_mairon_list: ArrayList<String>
    private lateinit var annatar_list: ArrayList<String>
    private lateinit var annatar_map: HashMap<String, MutableList<String>>

	private var annatar_graph: Boolean = false
    private var currentItem: Int = -1
    private var currentEpic1: String = ""
    private var currentEpic2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        resourceList = hashMapOf()
        epicList = arrayOf()
        bigList = arrayOf()
        mairon_list = arrayListOf()
        short_mairon_list = arrayListOf()
        annatar_list = arrayListOf()
        annatar_map = hashMapOf()

        try {
            taskMorgoth = intent.getStringExtra(_morgoth)
        }
        catch (e: Exception) {
            taskMorgoth = "Resource"
            outputGraphMessage("Epic Morgoth")
        }

        super.onCreate(savedInstanceState)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        try {
            Locale.setDefault(Locale("en", "US"))
        }
        catch (e: Exception) { }
        registerTask(0)
        setContentView(R.layout.notification_template_big_media_custom)
        taskPrepare1(false)
        taskPrepare2(false)

        val catTask = InitActivity(this, taskMorgoth)
        catTask.execute()
    }

    fun goToInputLog(view: View?) {
        if (!registerTask(0))
            return

        val intentResult = Intent()
        intentResult.putExtra(_message, 2)
        this.setResult(Activity.RESULT_OK, intentResult)
        this.finish()
    }
    fun setMairon(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare1(false)
        inputPrepare2(false)
        if (object0508.text.isNotEmpty() && object0509.text.isNotEmpty() && object0510.text.isNotEmpty() && object0511.text.isNotEmpty()) {
            taskPrepare2(false)
            val item: String = "${object0504.text} ${object0505.text}:${String.format("%02d", object0506.value)}.000"
            if (item !in mairon_list) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Confirm <1 base write>?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            outputGraphMessage("SQL1/Insert")
                            var verifyItem = true
                            val taskValue1: String = object0508.text.toString()
                            val taskValue2: Int = object0507.selectedItemPosition
                            val taskValue3 = object0525.text.toString()
                            var taskValue4: Int = 0
                            var taskValue5: Int = 0
                            var taskValue6: Int = 0
                            try {
                                taskValue4 = object0509.text.toString().toInt()
                                taskValue5 = object0510.text.toString().toInt()
                                taskValue6 = object0511.text.toString().toInt()
                            } catch (e: Exception) {
                                outputGraphMessage("List/Verify Input")
                                verifyItem = false
                                logInit = 1
                            }

                            if (verifyItem) {
                                val mouseTask = UpdateStorage(this, 1, item, taskValue1, taskValue2, annatar_map, "", taskValue3, taskValue4, taskValue5, taskValue6)
                                mouseTask.execute()
                            }
                            else {
                                taskPrepare1(true)
                                taskPrepare2(true)
                                inputPrepare2(true)
                                registerTask(1)
                            }
                        }
                        .setNegativeButton("No") { dialog, id ->
                            dialog.dismiss()
                            taskPrepare1(true)
                            taskPrepare2(true)
                            inputPrepare2(true)
                            registerTask(1)
                        }
                val alert = builder.create()
                alert.show()
            }
            else {
                outputGraphMessage("SQL/Exist")
                logInit = 1
                taskPrepare1(true)
                taskPrepare2(true)
                inputPrepare2(true)
                registerTask(1)
            }
        }
        else {
            taskPrepare1(true)
            inputPrepare2(true)
            registerTask(1)
        }
    }
    fun setAnnatar(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare1(false)
        inputPrepare2(false)
        val index = object0502.selectedItemPosition
        if (object0502.count > 0 && object0502.selectedItemId != INVALID_ROW_ID && index < mairon_list.size) {
            taskPrepare2(false)
            val item = mairon_list[index]
            if (item in annatar_map) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Confrim <2 base write>?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            outputGraphMessage("SQL2/Insert")
                            val taskValue1: String = object0517.selectedItem.toString()
                            val taskValue2: Int = object0518.selectedItemPosition
                            val taskValue3: String = if (object0514.text.isNotEmpty()) object0514.text.toString() else ""
                            val taskValue4: String = if (object0525.text.isNotEmpty()) object0525.text.toString() else ""
                            val mouseTask = UpdateStorage(this, 2, item, taskValue1, taskValue2, annatar_map, taskValue3, taskValue4)
                            mouseTask.execute()
                        }
                        .setNegativeButton("No") { dialog, id ->
                            dialog.dismiss()
                            taskPrepare1(true)
                            taskPrepare2(true)
                            inputPrepare2(true)
                            registerTask(1)
                        }
                val alert = builder.create()
                alert.show()
            }
            else {
                outputGraphMessage("List/Verify Item")
                logInit = 1
                taskPrepare1(true)
                taskPrepare2(true)
                inputPrepare2(true)
                registerTask(1)
            }
        }
        else {
            taskPrepare1(true)
            inputPrepare2(true)
            registerTask(1)
        }
    }
    fun writeAnnatar(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare1(false)
        inputPrepare1(false)
        val index = object0502.selectedItemPosition
        if (object0502.count > 0 && object0502.selectedItemId != INVALID_ROW_ID && index < mairon_list.size && object0525.text.isNotEmpty()) {
            val item = mairon_list[index]
            val taskInput: String = object0525.text.toString()
            if (item in annatar_map) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Confirm <list write>?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            annatar_map[item]!!.add(taskInput)
                            annatar_list.add(taskInput)
                            adapterSpinner2.notifyDataSetChanged()
                            if (object0503.count > 0)
                                object0503.setSelection(object0503.count - 1)
                            object0525.setText("")
                            if (logInit > 1 && taskInput.length > epicStrMax) {
                                if (!annatar_graph) {
                                    sauron_output.append("\n\n===========================")
                                    annatar_graph = true
                                }
                                sauron_output.append("\n$taskInput")
                            }
                        }
                        .setNegativeButton("No") { dialog, id ->
                            dialog.dismiss()
                        }
                val alert = builder.create()
                alert.show()
            }
            else {
                outputGraphMessage("List/Verify Item")
                logInit = 1
            }
        }
        taskPrepare1(true)
        inputPrepare1(true)
        registerTask(1)
    }
    fun copyAnnatar(view: View?) {
        if (!registerTask(0))
            return
        inputPrepare1(false)
        if (object0525.text.isNotEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <clear text>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        object0525.setText("")
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            taskPrepare1(false)
            if (object0503.count > 0 && object0503.selectedItemId != INVALID_ROW_ID) {
                val taskInput = object0503.selectedItem
                if (taskInput != null)
                    object0525.setText(taskInput.toString())
                /*    val index = spinnerEvent.selectedItem.toString()
                    Log.i("SIZE", "${comment_map[index]!!.size}")
                    for (s in 1 until comment_map[index]!!.size)
                        comment_map[index]!!.removeAt(1)
                    comment_list.clear()
                    adapterSpinnerComment.notifyDataSetChanged()
                    sqlStorage.updateRecord(index, "")  */
            }
            taskPrepare1(true)
        }
        inputPrepare1(true)
        registerTask(1)
    }
    fun flowAnnatar(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare1(false)
        val index = object0502.selectedItemPosition
        if (object0502.count > 0 && object0502.selectedItemId != INVALID_ROW_ID && index < mairon_list.size && object0503.count > 1 && object0503.selectedItemId != INVALID_ROW_ID && object0503.selectedItemPosition > 0) {
            val item1 = mairon_list[index]
            val item2 = object0503.selectedItemPosition

            if (item1 in annatar_map) {
                if (item2 < annatar_map[item1]!!.size - 1 && item2 < annatar_list.size) {
                    val taskValue = annatar_map[item1]!![item2]
                    annatar_map[item1]!![item2] = annatar_map[item1]!![item2 + 1]
                    annatar_map[item1]!![item2 + 1] = taskValue
                    annatar_list[item2 - 1] = annatar_list[item2]
                    annatar_list[item2] = taskValue
                    adapterSpinner2.notifyDataSetChanged()
                    val taskIndex = item2 - 1
                    if (taskIndex >= 0 && taskIndex < object0503.count)
                        object0503.setSelection(taskIndex)
                }
                else {
                    outputGraphMessage("Cache/Verify Item")
                    logInit = 1
                }
            }
            else {
                outputGraphMessage("List/Verify Item")
                logInit = 1
            }
        }
        taskPrepare1(true)
        registerTask(1)
    }
    fun clearList1(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare1(false)
        val index = object0502.selectedItemPosition
        if (object0502.count > 0 && object0502.selectedItemId != INVALID_ROW_ID && index < mairon_list.size) {
            val item1 = mairon_list[index]
            if (item1 in annatar_map) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Confirm <1 list remove>?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                        //    outputGraphMessage("SQL/Clear")
                            val mouseTask = UpdateStorage(this, 3, item1, "", index)
                            mouseTask.execute()
                        }
                        .setNegativeButton("No") { dialog, id ->
                            dialog.dismiss()
                            taskPrepare1(true)
                            registerTask(1)
                        }
                val alert = builder.create()
                alert.show()
            }
            else {
                outputGraphMessage("SQL/Verify Item")
                logInit = 1
                taskPrepare1(true)
                registerTask(1)
            }
        }
        else {
            taskPrepare1(true)
            registerTask(1)
        }
    }
    fun clearList2(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare1(false)
        val index = object0502.selectedItemPosition
        if (object0502.count > 0 && object0502.selectedItemId != INVALID_ROW_ID && index < mairon_list.size && object0503.count > 0 && object0503.selectedItemId != INVALID_ROW_ID) {
            val item1 = mairon_list[index]
            val item2 = object0503.selectedItemPosition
            if (item1 in annatar_map) {
                if (annatar_map[item1]!!.size > item2 + 1 && annatar_list.size > item2) {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Confirm <2 list remove>?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                annatar_map[item1]!!.removeAt(item2 + 1)
                                annatar_list.removeAt(item2)
                                adapterSpinner2.notifyDataSetChanged()
                                if (annatar_list.size > 0) {
                                    val setIndex = item2 - if (annatar_list.size > item2) 0 else 1
                                    if (setIndex < annatar_list.size)
                                        object0503.setSelection(setIndex)
                                    else
                                        object0503.setSelection(0)
                                }
								if (logInit > 1) {
                                    val taskItem = object0502.selectedItemId
                                    if (index < object0502.count && taskItem != INVALID_ROW_ID && object0502.selectedView != null) {
                                        try {
                                             object0502.onItemSelectedListener!!.onItemSelected(object0502, object0502.selectedView, index, taskItem)
                                        }
                                        catch (e: Exception) { }
                                    }
                                }
                                taskPrepare1(true)
                                registerTask(1)
                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.dismiss()
                                taskPrepare1(true)
                                registerTask(1)
                            }
                    val alert = builder.create()
                    alert.show()
                }
                else {
                    outputGraphMessage("SQL/Verify List")
                    logInit = 1
                    taskPrepare1(true)
                    registerTask(1)
                }
            }
            else {
                outputGraphMessage("SQL/Verify Item")
                logInit = 1
                taskPrepare1(true)
                registerTask(1)
            }
        }
        else {
            taskPrepare1(true)
            registerTask(1)
        }
    }
    fun writeFile(view: View?) {
        if (!registerTask(0))
            return

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val outputText = sauron_output.text.toString()
        if (outputText != "App Data") {
            //val path = getExternalFilesDir("")
            if (path != null) {
                var outputResult = false
                var fileName = ""
                try {
                    fileName = object0502.selectedItem.toString().replace(" ", "_").replace("-", "_").replace(":", "_") + ".txt"
                    val outputFile = File(path.absolutePath, fileName)
                    if (outputFile.exists()) {
                        outputFile.delete()
                    }
                    outputResult = FileLogic.writeToFile(outputText, outputFile)
                }
                catch (e: Exception) { }

                if (outputResult)
                    outputGraphMessage("Write Downloads/$fileName> complete")
                else
                    outputGraphMessage("Check settings")
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
            activity.object0504.text = "${String.format("%04d", year)}-${String.format("%02d", month+1)}-${String.format("%02d", day)}"
        }
    }
    class MaironInput : DialogFragment(), TimePickerDialog.OnTimeSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = UtcCalendar.getInstance()
            val value1 = c.get(UtcCalendar.HOUR_OF_DAY)
            val value2 = c.get(UtcCalendar.MINUTE)
            return TimePickerDialog(activity, this, value1, value2, true)
        }
        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
            activity.object0505.text = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
        }
    }
    fun showAnnatar1(v: View?) {
        if (!registerTask(0))
            return
        val newFragment = LogOutput.AnnatarInput()
        newFragment.show(fragmentManager, "datePicker")
        registerTask(1)
    }
    fun showAnnatar2(v: View?) {
        if (!registerTask(0))
            return
        val newFragment = LogOutput.MaironInput()
        newFragment.show(fragmentManager, "datePicker")
        registerTask(1)
    }
    private fun outputGraphMessage(msg: String) {
        sauron_output.text = msg
    }

    private fun taskPrepare1(type: Boolean) {
        object0502.isEnabled = type
        object0503.isEnabled = type
    }
    private fun taskPrepare2(type: Boolean) {
        object0517.isEnabled = type
        object0518.isEnabled = type
    }
    private fun inputPrepare1(type: Boolean) {
        object0525.isEnabled = type
    }
    private fun inputPrepare2(type: Boolean) {
        object0504.isEnabled = type
        object0505.isEnabled = type
        object0506.isEnabled = type
        object0507.isEnabled = type
        object0508.isEnabled = type
        object0509.isEnabled = type
        object0510.isEnabled = type
        object0511.isEnabled = type
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
    private class InitActivity(thisOutput: LogOutput, task_morgoth: String) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<LogOutput> = WeakReference(thisOutput)
        private val MorgothValue: String
        private var epicTaskList: ArrayList<String>? = null
        private var epicResult1: Array<String>? = null
        private var epicResult2: Array<String>? = null
        private var nameLogoCount: Int = 0
        private var nameLogoResult: Array<String>? = null
        private var resourceResult: HashMap<String, String>? = null
        private var specialResult: ArrayList<String>? = null
        private var taskResult: HashMap<String, TaskRecord>? = null
        private var storageResult: JSONArray? = null
        private var aesResult: JSONArray? = null
        private var taskBigList: Array<String>? = null
        private var outputValue: Int = 0

        private var hint1: String = "DAE39140A50EE6048B207A15F8EAD771^8oO9Z5gz0cvEpcPQlmeS67PS_obdrkNaZVa_k_ultAt99x-ZLqCPivqq7y550JBT"
        //Name
        private var hint2: String = "2AC721FAD5B81E183D98750D8EE54B5C^m3CgquC0I9SiZ6FLQnqExoyphrr4rh8mpJiPjVV5JgWv2mlGlxGKkLLPXtXXUuwP"
        //Interval
        private var hint3: String = "B5F7AA3A2F23D1DED21B3552E8987548^r7tDf1HIBRgBvjlv-bdXwp_s_mOwUbqYjU59aQ7kV7J-7VACo5P7fTF21eEiarVB"
        //Num
        private var hint4: String = "05EFF8B5EDEF3B7C1209F95FE7EA772C^wKQLs28wbYhX-p7nG5TvJNL-PBP_nps9JuIovubq2qui9OaR3Jmfkl9RF_b0fgs1"
        //Range
        private var hint5: String = "52D97652229480480D5EB91BD32C0247^nLNsz8zKEkal6S8HFTCGCDncDMu-Xt3kZrJMCMxpr2SJYtx4FhRg4HP57MVIQqHn"
        //Price
        private var line1: String = "08F0552C5BD495459BBB04B63D8FC162^8aGTdjdOAI4bDqu36y6uNcyuYP_yxG0VFn9YBsYQe7tGVg3XRJ2nl_YRn-MPbg3V"
        //Event
        private var line2: String = "A13B140D825F1FB19FB469EEA2EA8C1F^QwzLYbnzCYrsrIMnsfUfnr1XNAEz_OSMLmiQifMiK0exgd4Dz6QBl-5aJAEzO75l"
        //Status
        private var line3: String = "033ABD36191D75242146E0964AF6AB01^oSQtJEtOawv_LuD-G5ZiPqNI0qS-7Qic7ZFfWCdy7pA57W3cvyqaau-8MAkKFzZu"
        //Comment
        private var hint6: String = "963857FC776E367F5503F7EAAF37D572^MsXIgm-xUHXxbT83BU21d_S_ZnokWJtPI38zwNKARJw6jGpjjHXLJhn4tcO81Ndc"
        //Target/Text
        init {
            this.MorgothValue = task_morgoth
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

        //    root.mairon_list = ArrayList<String>()
        //    root.short_mairon_list = ArrayList<String>()
        //    root.annatar_list = ArrayList<String>()
        //    root.annatar_map = HashMap()

            val spinnerClass1: Spinner = root.findViewById(R.id.object0517) as Spinner
            val adapterClass1 = ArrayAdapter.createFromResource(root,
                    R.array.example1_list, R.layout.cast_tracks_chooser_dialog_row_layout)
            adapterClass1.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinnerClass1.adapter = adapterClass1

            val spinnerClass2: Spinner = root.findViewById(R.id.object0518) as Spinner
            val adapterClass2 = ArrayAdapter.createFromResource(root,
                    R.array.example2_list, R.layout.cast_tracks_chooser_dialog_row_layout)
            adapterClass2.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinnerClass2.adapter = adapterClass2

            root.object0508.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
            root.object0506.wrapSelectorWheel = false
            val taskList: ArrayList<String> = ArrayList<String>()
            for (s in 0 until 60)
                taskList.add(String.format("%02d", s))
            root.object0506.displayedValues = taskList.toTypedArray()
            root.object0506.maxValue = 59
            root.object0506.minValue = 0
            root.object0506.value = 30
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
            val sqlBig = SQLBigTable(root)
            val sqlNameLogo = SQLNameLogoTable(root)
            val sqlResource = SQLResourceTable(root)
            val sqlTaskTable = SQLTaskTable(root)
            val sqlStorage = SQLStorageTable(root)
            val sqlAES = SQLAESTable(root)
            epicResult1 = sqlBig.allRecords(999, 1, keyStr)
            epicResult2 = sqlBig.allRecords(999, 2, keyStr)
            nameLogoResult = sqlNameLogo.getSuperList()
            nameLogoCount = sqlNameLogo.countRecord()
            
            resourceResult = sqlResource.allRecords
            taskResult = sqlTaskTable.allRecords
            storageResult = sqlStorage.allRecords
            aesResult = sqlAES.allRecords

            resourceResult!!.forEach { (key, value) -> resourceResult!![key] = AES256.toText(value, keyStr) }
            val specialResultInput1 = root.resources.getStringArray(R.array.file_list1)
            val specialResultInput2 = root.resources.getStringArray(R.array.file_list3)
            specialResult = ArrayList<String>()
            for (s in 0 until specialResultInput1.size)
                specialResult!!.add(AES256.toText(specialResultInput1[s], keyStr))
            for (s in 0 until specialResultInput2.size)
                specialResult!!.add(specialResultInput2[s])

        //    Log.i("STORAGE", "Size=${storageResult.length()}")
            for (s in 0 until storageResult!!.length()) {
                var outputRow: JSONArray? = null
                try {
                    outputRow = storageResult!!.getJSONArray(s)
                }
                catch (e: Exception) {
                    outputValue++
                    continue
                }
                //    Log.i("ROW", "${s}/${outputRow[12].toString()}")
                if (outputRow == null)
                    continue
                if (outputRow.length() < 13)
                    continue
                if (outputRow[12].toString() == "false") {
                    val timestamp = outputRow[0].toString()
                    var shortTimestamp: String = "VERIFY"
                    try {
                        shortTimestamp = UtcDate.stringAdd(timestamp, "yyyy-MM-dd HH:mm:ss.SSS", true, currentGreenwichOffset)
                    //    shortTimestamp = "yyyy-MM-dd HH:mm:ss.SSS".utcFormat(UtcDate(true, "yyyy-MM-dd HH:mm:ss.SSS".utcParse(timestamp, true).time), currentGreenwichOffset)
                    }
                    catch (e: Exception) { }

                    root.mairon_list.add(timestamp)
                    root.short_mairon_list.add(shortTimestamp.substring(0, 19.coerceAtMost(shortTimestamp.length)))
                    val inputList = outputRow[11].toString().replace("[", "").replace("]", "").split(",".toRegex()).toMutableList()
                    //    Log.i("ITEM", "${timestamp}/${inputList.size}")
                    root.annatar_map[timestamp] = inputList
                }
            }

            taskBigList = root.resources.getStringArray(R.array.big_list2)
            for (s in 0 until taskBigList!!.size)
                taskBigList!![s] = AES256.toText(taskBigList!![s], keyStr)

            val epicListInput1 = root.resources.getStringArray(R.array.file_list1)
            val epicListInput2 = root.resources.getStringArray(R.array.file_list3)
            epicTaskList = ArrayList<String>()
            for (s in 0 until epicListInput1.size) {
                var taskStr = AES256.toText(epicListInput1[s], keyStr)
                if (taskStr.length > 3)
                    taskStr = taskStr.substring(0, 3.coerceAtMost(taskStr.length))
                epicTaskList!!.add(taskStr)
            }
            for (s in 0 until epicListInput2.size) {
                epicTaskList!!.add(epicListInput2[s])
			}

            hint1 = AES256.toText(hint1, keyStr)
            hint2 = AES256.toText(hint2, keyStr)
            hint3 = AES256.toText(hint3, keyStr)
            hint4 = AES256.toText(hint4, keyStr)
            hint5 = AES256.toText(hint5, keyStr)
            line1 = AES256.toText(line1, keyStr)
            line2 = AES256.toText(line2, keyStr)
            line3 = AES256.toText(line3, keyStr)
            hint6 = AES256.toText(hint6, keyStr)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

        //    if (outputValue > 0)
        //        root!!.outputGraphMessage("Storage/${outputValue}")
            val currencyValue1 = "yyyy-MM-dd".utcFormat(UtcDate(true), currentGreenwichOffset)
            root.object0504.text = currencyValue1

            val currencyValue2 = "HH:mm".utcFormat(UtcDate(true), currentGreenwichOffset)
            root.object0505.text = currencyValue2

            root.object0508.hint = hint1
            root.object0509.hint = hint2
            root.object0510.hint = hint3
            root.object0511.hint = hint4
            root.object0514.hint = hint5
            root.object0515.text = line1
            root.object0523.text = line2
            root.object0524.text = line3
            root.object0525.hint = hint6

            root.resourceList = resourceResult!!
            root.epicList = specialResult!!.toTypedArray()
            root.bigList = taskBigList!!
            val spinnerItem1 = root.object0517.selectedItem
            if (spinnerItem1 != null)
                root.currentEpic1 = spinnerItem1.toString()
            val spinnerItem2 = root.object0518.selectedItem
            if (spinnerItem2 != null)
                root.currentEpic2 = spinnerItem2.toString()

            val spinner1: Spinner = root.findViewById(R.id.object0502) as Spinner
            root.adapterSpinner1 = ArrayAdapter<String>(root,
                    R.layout.cast_tracks_chooser_dialog_row_layout, root.short_mairon_list)
            root.adapterSpinner1.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinner1.adapter = root.adapterSpinner1
        //    if (root.event_list.size > 0)
        //        spinnerEvent.setSelection(0)

            val spinner2: Spinner = root.findViewById(R.id.object0503) as Spinner
            root.adapterSpinner2 = ArrayAdapter<String>(root,
                    R.layout.cast_tracks_chooser_dialog_row_layout, root.annatar_list)
            root.adapterSpinner2.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinner2.adapter = root.adapterSpinner2

            val spinnerClass3: Spinner = root.findViewById(R.id.object0507) as Spinner
            val adapterSpinner3 = ArrayAdapter<String>(root,
                    R.layout.cast_tracks_chooser_dialog_row_layout, epicTaskList)
            adapterSpinner3.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            spinnerClass3.adapter = adapterSpinner3

            root.object0502.setOnTouchListener { v, event ->
                if (v != null && event != null) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        val textIndex = root.object0502.selectedItemId
                        val textInput = root.object0502.selectedItemPosition
                        if (textIndex != INVALID_ROW_ID && textInput < root.mairon_list.size) {
                            val timestamp = root.mairon_list[textInput]
                            var priceArrayOutput: String = ""
                            val sqlStorage = SQLStorageTable(v.context)
                            val outputRow = sqlStorage.getRecord(timestamp)
                            if (outputRow.size > 12) {
                                val priceArray = outputRow[5].replace("[", "").replace("]", "").split(",")
                                for (s in 0 until priceArray.size) {
                                    priceArrayOutput += priceArray[s]
                                    if (s < priceArray.size - 1)
                                        priceArrayOutput += " "
                                }

                                var sqlRequest: Boolean = false
                                val bigAnnatar = ArrayList<String>()
                                val taskArray = outputRow[9].replace("[", "").replace("]", "").split(",")
                                val commentList = outputRow[11].replace("[", "").replace("]", "").split(",")
                                try {
                                    val specialItem1 = outputRow[8].toInt()
                                    val specialItem2 = (specialItem1 + outputRow[7].toInt()) % specialItem1
                                    var specialItem3: String = "VERIFY"
                                    try {
                                        specialItem3 = UtcDate.stringAdd(outputRow[0], "yyyy-MM-dd HH:mm:ss.SSS", true, currentGreenwichOffset)
                                        //    specialItem3 = "yyyy-MM-dd HH:mm:ss.SSS".utcFormat(UtcDate(true, "yyyy-MM-dd HH:mm:ss.SSS".utcParse(outputRow[0], true).time), currentGreenwichOffset)
                                    }
                                    catch (e: Exception) { }

                                    root.sauron_output.text = "${specialItem3.substring(0, specialItem3.length - 4)}"
                                    root.sauron_output.append("\nNUMBER          {   ${taskArray[specialItem2].replace("\"", "")}   }          range ${outputRow[4].replace("\"", "")}")
                                    root.sauron_output.append("\n===========================\n>>> ")

                                    var dataLength = 0
                                    dataLength = outputRow[7].toInt()+1
                                    val dataArray = outputRow[9].removeSurrounding("[", "]").replace("\"", "").split(",")

                                    var outputSize: Int = 32
                                    val outputLimit: Int = outputRow[8].toInt()
                                    if (dataLength < outputSize)
                                        outputSize = dataLength
                                    for (s in 0 until outputSize) {
                                        val dataIndex = dataLength-outputSize+s
                                        if(dataIndex < outputLimit)
                                            root.sauron_output.append("${dataArray[dataIndex]}")
                                        if (s == outputSize - 1)
                                            root.sauron_output.append(".")
                                        else
                                            root.sauron_output.append(" ")
                                    }

                                    //root.sauron_output.append("\n${outputRow[9]}\n")
                                    root.sauron_output.append("\n===========================")
                                    //Algorithm Interval
                                    root.sauron_output.append("\n                     ${outputRow[6]}")
                                    for (s in 1 until commentList.size) {
                                        val itemStr1: String = commentList[s]
                                        root.sauron_output.append("\n$itemStr1")
                                        if (itemStr1.length > epicStrMax)
                                            bigAnnatar.add(itemStr1)
                                    }
                                    sqlRequest = true
                                }
                                catch (e: Exception) {
                                    root.sauron_output.text = "Graph/Verify Request"
                                }
                                root.annatar_graph = false

                                if (sqlRequest) {
                                    for (s in 0 until root.annatar_list.size) {
                                        val itemStr2: String = root.annatar_list[s]
                                        if (itemStr2.length > epicStrMax) {
                                            if (!bigAnnatar.contains(itemStr2)) {
                                                if (!root.annatar_graph) {
                                                    root.sauron_output.append("\n\n===========================")
                                                    root.annatar_graph = true
                                                }
                                                root.sauron_output.append("\n$itemStr2")
                                            }
                                        }
                                    }
                                }
                            }
                            root.logInit++
                        }
                    }
                }
                v.performClick()
            }
            root.object0502.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    if (id != INVALID_ROW_ID && position >= 0 && position < root.mairon_list.size && selectedItemView != null) {
                        if (root.currentItem >=0 && root.currentItem < root.mairon_list.size) {
                            val index = root.mairon_list[root.currentItem]
                            if (index in root.annatar_map) {
                                if (root.annatar_map[index]!!.size > 0) {
                                    var taskValue: String = root.currentEpic1
                                    if (root.currentEpic2 != "E")
                                        taskValue += root.currentEpic2
                                    root.annatar_map[index]!![0] = taskValue
                                }
                            }
                        }

                        val timestamp = root.mairon_list[position]
                        if (timestamp !in root.annatar_map) {
                            root.outputGraphMessage("Item 1/${position + 1}")
                            root.logInit = 1
                        }
                        else {
                            val inputList = root.annatar_map[timestamp]

                            root.annatar_list.clear()
                            for (s in 1 until inputList!!.size)
                                root.annatar_list.add(inputList[s])
                            root.adapterSpinner2.notifyDataSetChanged()

                            if (inputList.size > 0) {
                                var taskIndex = -1
                                var taskSpecial = -1
                                try {
                                    val inputSpecial = if (inputList[0].contains("[^!?]".toRegex())) inputList[0][inputList[0].length - 1].toString() else ""
                                    taskSpecial = if (inputSpecial.isNotEmpty()) root.resources.getStringArray(R.array.example2_list).indexOf(inputSpecial) else 0
                                    val inputType = inputList[0].replace("!", "").replace("?", "")
                                    taskIndex = root.resources.getStringArray(R.array.example1_list).indexOf(inputType)
                                }
                                catch (e: Exception) { }

                                if (taskIndex >= 0 && taskIndex < root.object0517.count)
                                    root.object0517.setSelection(taskIndex)
                                else if (root.object0517.count > 0)
                                    root.object0517.setSelection(0)
                                if (taskSpecial >= 0 && taskSpecial < root.object0518.count)
                                    root.object0518.setSelection(taskSpecial)
                                else if (root.object0518.count > 0)
                                    root.object0518.setSelection(0)

                                if (spinner2.count > 0)
                                    spinner2.setSelection(0)
                            }

                            if (root.logInit > 0) {
                                var priceArrayOutput: String = ""
                                val sqlStorage = SQLStorageTable(selectedItemView.context)
                                val outputRow = sqlStorage.getRecord(timestamp)
                                if (outputRow.size > 12) {
                                    val priceArray = outputRow[5].replace("[", "").replace("]", "").split(",")
                                    for (s in 0 until priceArray.size) {
                                        priceArrayOutput += priceArray[s]
                                        if (s < priceArray.size - 1)
                                            priceArrayOutput += " "
                                    }

                                    var sqlRequest: Boolean = false
                                    val bigAnnatar = ArrayList<String>()
                                    val taskArray = outputRow[9].replace("[", "").replace("]", "").split(",")
                                    val commentList = outputRow[11].replace("[", "").replace("]", "").split(",")
                                    try {
                                        val specialItem1 = outputRow[8].toInt()
                                        val specialItem2 = (specialItem1 + outputRow[7].toInt()) % specialItem1
                                        var specialItem3: String = "VERIFY"
                                        try {
                                            specialItem3 = UtcDate.stringAdd(outputRow[0], "yyyy-MM-dd HH:mm:ss.SSS", true, currentGreenwichOffset)
                                            //    specialItem3 = "yyyy-MM-dd HH:mm:ss.SSS".utcFormat(UtcDate(true, "yyyy-MM-dd HH:mm:ss.SSS".utcParse(outputRow[0], true).time), currentGreenwichOffset)
                                        }
                                        catch (e: Exception) { }

                                        root.sauron_output.text = "${specialItem3.substring(0, specialItem3.length - 4)}"
                                        root.sauron_output.append("\nNUMBER          {   ${taskArray[specialItem2].replace("\"", "")}   }          range ${outputRow[4].replace("\"", "")}")
                                        root.sauron_output.append("\n===========================\n>>> ")

                                        var dataLength = 0
                                        dataLength = outputRow[7].toInt()+1
                                        val dataArray = outputRow[9].removeSurrounding("[", "]").replace("\"", "").split(",")

                                        var outputSize: Int = 32
                                        val outputLimit: Int = outputRow[8].toInt()
                                        if (dataLength < outputSize)
                                            outputSize = dataLength
                                        for (s in 0 until outputSize) {
                                            val dataIndex = dataLength-outputSize+s
                                            if(dataIndex < outputLimit)
                                                root.sauron_output.append("${dataArray[dataIndex]}")
                                            if (s == outputSize - 1)
                                                root.sauron_output.append(".")
                                            else
                                                root.sauron_output.append(" ")
                                        }

                                        //root.sauron_output.append("\n${outputRow[9]}\n")
                                        root.sauron_output.append("\n===========================")
                                        //Algorithm Interval
                                        root.sauron_output.append("\n                     ${outputRow[6]}")
                                        for (s in 1 until commentList.size) {
                                            val itemStr1: String = commentList[s]
                                            root.sauron_output.append("\n$itemStr1")
                                            if (itemStr1.length > epicStrMax)
                                                bigAnnatar.add(itemStr1)
                                        }
                                        sqlRequest = true
                                    }
									catch (e: Exception) {
										root.sauron_output.text = "Graph/Verify Request"
                                    }
									root.annatar_graph = false
									
                                    if (sqlRequest) {
                                        for (s in 0 until root.annatar_list.size) {
                                            val itemStr2: String = root.annatar_list[s]
                                            if (itemStr2.length > epicStrMax) {
                                                if (!bigAnnatar.contains(itemStr2)) {
                                                    if (!root.annatar_graph) {
                                                        root.sauron_output.append("\n\n===========================")
                                                        root.annatar_graph = true
                                                    }
                                                    root.sauron_output.append("\n$itemStr2")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                root.logInit++
                            }
                        }
                        root.currentItem = position
                        //    root.graph_text.append("Crypto=${outputRow[11]} AES=${outputRow[12]} IV=${outputRow[13]}")
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }
            root.object0517.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    if (id != INVALID_ROW_ID && position >= 0 && selectedItemView != null) {
                        val epicItem1 = root.object0517.selectedItem
                        if (epicItem1 != null)
                            root.currentEpic1 = epicItem1.toString()
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }
            root.object0518.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    if (id != INVALID_ROW_ID && position >= 0 && selectedItemView != null) {
                        val epicItem2 = root.object0518.selectedItem
                        if (epicItem2 != null)
                            root.currentEpic2 = epicItem2.toString()
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

            //MOON/ECLIPSE BASE
            root.sauron_output.movementMethod = ScrollingMovementMethod()
            /*root.sauron_output.text = "${root.bigList[0]}\n===========================\n"
            root.sauron_output.append("Size: ${epicResult1!!.size}/${epicResult2!!.size}\n")

            //NAME LOGO BASE
            root.sauron_output.append("\n${root.bigList[1]}\n===========================\n")
            root.sauron_output.append("Size: ${nameLogoCount}\n")
            val nameLogoLength = nameLogoResult!!.count()
            for (s in 0 until nameLogoLength - 1) {
                root.sauron_output.append(nameLogoResult!![s] + ", ")
            }
            if (nameLogoLength > 0)
                root.sauron_output.append(nameLogoResult!![nameLogoLength - 1] + "\n")

            //RESOURCE BASE
            root.sauron_output.append("\n${root.bigList[2]}\n===========================\n")
            root.sauron_output.append("Size: ${resourceResult!!.size}\n")
            resourceResult!!.forEach { (key, _) -> root.sauron_output.append("$key=${resourceResult!![key]}\n") }

            //STOCK BASE
            root.sauron_output.append("\n${root.bigList[3]}\n===========================\n")
            root.sauron_output.append("Size: ${taskResult!!.size}\n")
            taskResult!!.forEach { (key, value) ->
                run {
                    root.sauron_output.append(key)
                    if (value.Precache)
                        root.sauron_output.append("*")
                    root.sauron_output.append(" ")
                }
            }
            if (taskResult!!.size > 0)
                root.sauron_output.append("\n")

            //STORAGE AES BASE
            root.sauron_output.append("\n${root.bigList[4]}\n===========================\n")
            var specialSize: Int = 0
            for (s in 0 until storageResult!!.length()) {
                var outputRow: JSONArray? = null
                try {
                    outputRow = storageResult!!.getJSONArray(s)
                }
                catch (e: Exception) { }
                if (outputRow == null)
                    continue
                if (outputRow.length() < 13)
                    continue
                if (outputRow[12].toString() == "true")
                    specialSize++
            }
            root.sauron_output.append("Size: ${specialSize}/${storageResult!!.length()}\tAES: ${aesResult!!.length()}\n")
            if (outputValue > 0)
                root.sauron_output.append("Verify: ${outputValue}\n")*/

            root.taskPrepare1(true)
            root.taskPrepare2(true)
            root.registerTask(1)
        }
    }
//----------------------------------------  ClearStorage -----------------------------------------
    private class UpdateStorage(thisOutput: LogOutput, task_type: Int, input1: String, input2: String, input3: Int, task_annatar: HashMap<String, MutableList<String>>? = null, input4: String = "", input5: String = "", input6: Int = 0, input7: Int = 0, input8: Int = 0) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<LogOutput> = WeakReference(thisOutput)
        private val taskType: Int
        private val index: String
        private var taskValue1: String
        private var taskValue2: Int
        private var taskValue3: String
        private var taskValue4: String
        private var taskValue5: Int
        private var taskValue6: Int
        private var taskValue7: Int
        private var taskRun: Boolean = false
        private var sqlResult: Boolean = false
        private val taskAnnatar: HashMap<String, MutableList<String>>?
        private var storageResult: List<String>? = null

        init {
            this.taskType = task_type
            this.index = input1
            this.taskValue1 = input2
            this.taskValue2 = input3
            this.taskValue3 = input4
            this.taskValue4 = input5
            this.taskValue5 = input6
            this.taskValue6 = input7
            this.taskValue7 = input8
            this.taskAnnatar = task_annatar
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            if (taskType == 2)
            {
                when (taskValue2) {
                    1 -> taskValue1 += "!"
                    2 -> taskValue1 += "?"
                }
                if (index in root.annatar_map && taskAnnatar != null) {
                    if (root.annatar_map[index]!!.size > 0) {
                        root.annatar_map[index]!![0] = taskValue1
                        taskRun = true
                    }
                }
            }
            else {
                taskRun = true
            }
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            if (taskRun) {
                val sqlStorage = SQLStorageTable(root)
                when (taskType) {
                    1 -> {
                    /*    var priceArray: String = ""
                        val priceStr = taskValue3.split(' ')
                        for (s in 0 until priceStr.size - 1) {
                            try {
                                priceArray += priceStr[s].toFloat().toString()
                                priceArray += ","
                            }
                            catch (e: Exception) { }
                        }
                        if (priceStr.size > 0) {
                            try {
                                priceArray += priceStr[priceStr.size - 1].toFloat().toString()
                            }
                            catch (e: Exception) { }
                        }   */

                        var timestamp: String = ""
                        try {
                            timestamp = UtcDate.stringAdd(index, "yyyy-MM-dd HH:mm:ss.SSS", true, currentGreenwichOffset, currentGreenwichOffset + outputGreenwichOffset)
                        //    timestamp = "yyyy-MM-dd HH:mm:ss.SSS".utcFormat(UtcDate(false, "yyyy-MM-dd HH:mm:ss.SSS".utcParse(index, true).time - currentGreenwichOffset * 3600000), currentGreenwichOffset)
                        }
                        catch (e: Exception) {
                            return null
                        }

                        if (timestamp.isNotEmpty()) {
                            sqlStorage.addRecord(timestamp, taskValue4, "0", taskValue2.toString(), taskValue7.toString(), "[]", taskValue5.toString(), "1", "1", "[$taskValue6]", taskValue1)
                            storageResult = sqlStorage.getRecord(timestamp)
                            sqlResult = true
                        }
                    }
                    2 -> {
                        var taskInput1: String = ""
                        if (taskValue3.isNotEmpty()) {
                            var taskArray: List<String>? = null
                            try {
                                taskArray = taskValue3.replace("[^\\d\\. ]".toRegex(), "").split(" ")
                            }
                            catch (e: Exception) { }
                            if (taskArray == null)
                                taskArray = listOf()

                            taskInput1 = "["
                            var inputExist: Boolean = false
                            for (s1 in 0 until taskArray.size) {
                                var taskStr: String = ""
                                val taskFloat = taskArray[s1].replace(" ", "").split(".")
                                for (s2 in 0 until taskFloat.size) {
                                    if (s2 == 1)
                                        taskStr += "."
                                    taskStr += taskFloat[s2]
                                }
                                if (taskStr.isNotEmpty()) {
                                    if (taskStr[0] == '.')
                                        taskStr = "0${taskStr}"
                                    if (taskStr[taskStr.length - 1] == '.')
                                        taskStr = "${taskStr}0"
                                    if (!inputExist)
                                        inputExist = true
                                    taskInput1 += taskStr
                                    if (s1 < taskArray.size - 1)
                                        taskInput1 += ","
                                }
                            }
                            taskInput1 += "]"

                            if (!inputExist)
                                taskInput1 = ""
                        }

                        var taskInput2: String = "["
                        for (s in 0 until taskAnnatar!![index]!!.size) {
                            taskInput2 += taskAnnatar[index]!![s]
                            if (s < taskAnnatar[index]!!.size - 1)
                                taskInput2 += ","
                        }
                        taskInput2 += "]"
                        sqlResult = sqlStorage.updateRecord(index, taskInput1, taskInput2, taskValue4)
                    }
                    3 -> {
                        sqlResult = sqlStorage.deleteRecord(index)
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            if (taskRun) {
                if (sqlResult) {
                    when (taskType) {
                        1 -> {
                            var sqlString: String = ""
                            if (sqlResult) {
                                if (storageResult!!.isNotEmpty()) {
                                    val timestamp = storageResult!![0]
                                    var shortTimestamp: String = "VERIFY"
                                    try {
                                        shortTimestamp = UtcDate.stringAdd(timestamp, "yyyy-MM-dd HH:mm:ss.SSS", true, currentGreenwichOffset)
                                    //    shortTimestamp = "yyyy-MM-dd HH:mm:ss.SSS".utcFormat(UtcDate(true, "yyyy-MM-dd HH:mm:ss.SSS".utcParse(timestamp, true).time), currentGreenwichOffset)
                                    }
                                    catch (e: Exception) { }

                                    root.mairon_list.add(timestamp)
                                    root.short_mairon_list.add(shortTimestamp.substring(0, 19.coerceAtMost(shortTimestamp.length)))
                                    root.annatar_map[timestamp] = arrayListOf()
                                    if (root.annatar_map[timestamp] != null)
                                        root.annatar_map[timestamp]!!.add("")
                                    root.adapterSpinner1.notifyDataSetChanged()

                                    if (root.mairon_list.size > 0) {
                                        root.currentItem = root.object0502.selectedItemPosition
                                        val taskIndex = root.mairon_list.size - 1
                                        if (taskIndex < root.object0502.count)
                                            root.object0502.setSelection(taskIndex)
                                    }
                                    sqlString = "SQL1/Complete"
                                }
                                else {
                                    sqlString = "SQL1/Get"
                                }
                            }
                            else {
                                sqlString = "SQL1/Verify"
                            }
                            root.outputGraphMessage(sqlString)

                            root.object0508.setText("")
                            root.object0509.setText("")
                            root.object0510.setText("")
                            root.object0511.setText("")
                            root.object0525.setText("")
							root.logInit = 1
                        }
                        2 -> {
                            var sqlString: String = ""
                            if (sqlResult)
                                sqlString = "SQL2/Complete"
                            else
                                sqlString = "SQL2/Verify"

                            root.outputGraphMessage(sqlString)
                            root.object0514.setText("")
                            root.object0525.setText("")
							root.logInit = 1
                        }
                        3 -> {
                            if (index in root.annatar_map)
                                root.annatar_map.remove(index)
                            if (taskValue2 < root.mairon_list.size)
                                root.mairon_list.removeAt(taskValue2)
                            if (taskValue2 < root.short_mairon_list.size)
                                root.short_mairon_list.removeAt(taskValue2)
                            root.annatar_list.clear()
							root.adapterSpinner1.notifyDataSetChanged()
							root.adapterSpinner2.notifyDataSetChanged()
							root.logInit = 2

							if (root.mairon_list.size > 0) {
								root.currentItem = root.object0502.selectedItemPosition
								if (root.currentItem == 0 && root.object0502.onItemSelectedListener != null && root.object0502.selectedView != null) {
									try {
										root.object0502.onItemSelectedListener!!.onItemSelected(root.object0502, root.object0502.selectedView, 0, 0L)
									}
									catch (e: Exception) { }
								}
								else {
									root.object0502.setSelection(0)
								}
							}
							else {
								root.currentItem = -1
								root.outputGraphMessage("SQL/Clear")
								root.logInit = 1
							}
                        }
                    }
                }
                else {
                    root.outputGraphMessage("SQL/Verify Request")
					root.logInit = 1
                }
            }
            else {
                root.outputGraphMessage("SQL/Verify Item")
				root.logInit = 1
            }
            root.taskPrepare1(true)
            root.taskPrepare2(true)
            root.inputPrepare2(true)
            root.registerTask(1)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event == null)
            return true
        var result: Boolean = false
        when (event.keyCode) {
            KeyEvent.KEYCODE_MENU -> result = true
            KeyEvent.KEYCODE_VOLUME_UP -> result = true
            KeyEvent.KEYCODE_VOLUME_DOWN -> result = true
            KeyEvent.KEYCODE_BACK -> result = true
            KeyEvent.KEYCODE_HOME -> result = true
        }
        return result
    }
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        try {
            val notificationIntent = Intent(this, LogOutput::class.java)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            pendingIntent.send()
        }
        catch (e: CanceledException) {
        //    e.printStackTrace()
        }
    }
}