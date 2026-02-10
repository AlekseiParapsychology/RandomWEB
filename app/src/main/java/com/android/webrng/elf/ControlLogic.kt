package com.android.webrng.elf

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.FragmentActivity
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.INVALID_POSITION
import android.widget.AdapterView.INVALID_ROW_ID
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.android.webrng.R
import com.android.webrng.constants.*
import com.android.webrng.sql.*
import com.android.webrng.utils.*
import kotlinx.android.synthetic.main.mr_chooser_list_item.*
import org.json.JSONArray
import java.io.*
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ControlLogic : FragmentActivity() {
    private var objectSycnhronize: Long = 1L
    private var taskMorgoth: String = ""
    private var initComplete: Boolean = false
    private var msgMairon: Int = 2
    private var maironAnnatar: Boolean = false
    private var aesRequest: Boolean = false
    private val resourceInit: String = "res/STOCK/Spec/Mkt/Clear/Output=VALUE{,ARG}*"

    private lateinit var totalResource: HashMap<String, String>
    private lateinit var adapterSpinnerAES: ArrayAdapter<String>
    private lateinit var aes_list: ArrayList<String>
    private lateinit var short_aes_list: ArrayList<String>
    private lateinit var hash_list: ArrayList<String>
    private lateinit var bigList: Array<String>
    private var uri1: Uri? = null
    private var uri2: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            val clipData = intent.clipData
            if (clipData != null) {
                uri1 = clipData.getItemAt(0).uri
                uri2 = clipData.getItemAt(1).uri
            }
            //    Log.i("ARRAY", "COMPLETE")
        }
        catch (e: Exception) {
            outputGraphMessage("File/Resource")
            //    Log.i("ARRAY", "${e.toString()}/${e.message.toString()}")
        }

        totalResource = hashMapOf()
        aes_list = arrayListOf()
        short_aes_list = arrayListOf()
        hash_list = arrayListOf()
        bigList = arrayOf()

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
        setContentView(R.layout.mr_chooser_list_item)
        taskPrepare(false)
        inputPrepare(false)

        val catTask = InitActivity(this, taskMorgoth)
        catTask.execute()
    }

    fun shortcutIcon(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare(false)
        val taskType = object0418.selectedItemPosition
        if (taskType == INVALID_POSITION || taskType !in 0..1) {
            taskPrepare(true)
            registerTask(1)
            return
        }

        val builder = AlertDialog.Builder(this)
        if (taskType == 1) {
            builder.setMessage("Confirm <total recreate>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        outputGraphMessage("Base/Total Creation")
                        val mouseTask = ClearStorage(this, taskMorgoth, 1)
                        mouseTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        taskPrepare(true)
                        registerTask(1)
                    }
        }
        else {
            if (msgMairon == 0) {
                taskPrepare(true)
                registerTask(1)
                return
            }
            builder.setMessage("Confirm <scale erase>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        outputGraphMessage("Base/Scale Erase")
                        val mouseTask = ClearStorage(this, taskMorgoth, 2)
                        mouseTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        taskPrepare(true)
                        registerTask(1)
                    }
        }
        val alert = builder.create()
        alert.show()
    }
    fun setSqlVerification(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <rewrite archive>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    outputGraphMessage("File/Backup Create")
                    val mouseTask = BaseOperation(this, taskMorgoth, 0)
                    mouseTask.execute()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    taskPrepare(true)
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun clearReserveBase(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare(false)
        val requestType = object0418.selectedItemPosition
        if (requestType == INVALID_POSITION || requestType !in 0..1) {
            taskPrepare(true)
            registerTask(1)
            return
        }

        if (requestType == 1) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <clear base>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        outputGraphMessage("File/Clear Cache")
                        val mouseTask = BaseOperation(this, taskMorgoth, 3)
                        mouseTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        taskPrepare(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            if (msgMairon == 0) {
                taskPrepare(true)
                registerTask(1)
                return
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <base reserve>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        outputGraphMessage("Base/Export WiFi")
                        val mouseTask = SetBase(this, 0)
                        mouseTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        taskPrepare(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
    }
    fun restoreBase1(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare(false)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <restore base primary>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    outputGraphMessage("File/Restore 1")
                    val mouseTask = BaseOperation(this, taskMorgoth, 1)
                    mouseTask.execute()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    taskPrepare(true)
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun restoreBase2(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare(false)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <restore base reserve>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    outputGraphMessage("File/Restore 2")
                    val mouseTask = BaseOperation(this, taskMorgoth, 2)
                    mouseTask.execute()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    taskPrepare(true)
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun setStorageImport(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <import storage>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    outputGraphMessage("Storage/Import")
                    val mouseTask = GetBase(this, taskMorgoth, 2, "Export")
                    mouseTask.execute()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    taskPrepare(true)
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun setClearStorage(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        val taskType = object0418.selectedItemPosition
        if (taskType == INVALID_POSITION || taskType !in 0..1) {
            taskPrepare(true)
            registerTask(1)
            return
        }

        if (taskType == 1) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Confirm <erase storage>?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        outputGraphMessage("Storage/Erase")
                        val mouseTask = ClearStorage(this, taskMorgoth, 0)
                        mouseTask.execute()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                        taskPrepare(true)
                        registerTask(1)
                    }
            val alert = builder.create()
            alert.show()
        }
        else {
            outputGraphMessage("Storage/Print")
            val mouseTask = PrintEvent(this)
            mouseTask.execute()
        }
    }
    fun decryptAES(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        val taskType = object0418.selectedItemPosition
        if (taskType == INVALID_POSITION || taskType !in 0..1) {
            taskPrepare(true)
            registerTask(1)
            return
        }

        if (taskType == 0) {
            inputPrepare(false)
            val taskAES = object0420.text.toString()
            if (taskAES.isNotEmpty()) {
                outputGraphMessage("AES/Source")
                val mouseTask = GetAES(this, taskMorgoth, 0, taskAES.toUpperCase(Locale.ENGLISH))
                mouseTask.execute()
            } else {
                taskPrepare(true)
                inputPrepare(true)
                registerTask(1)
            }
        }
        else {
            taskPrepare(true)
            registerTask(1)
        }
    }
    fun encryptAES(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        val taskType = object0418.selectedItemPosition
        if (taskType == INVALID_POSITION || taskType !in 0..1) {
            taskPrepare(true)
            registerTask(1)
            return
        }

        if (taskType == 0) {
            inputPrepare(false)
            val taskAES = object0420.text.toString()
            if (taskAES.isNotEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Confirm <encrypt storage>?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            outputGraphMessage("AES/Crypto")
                            val mouseTask = GetAES(this, taskMorgoth, 1, taskAES.toUpperCase(Locale.ENGLISH))
                            mouseTask.execute()
                        }
                        .setNegativeButton("No") { dialog, id ->
                            dialog.dismiss()
                            taskPrepare(true)
                            inputPrepare(true)
                            registerTask(1)
                        }
                val alert = builder.create()
                alert.show()
            } else {
                taskPrepare(true)
                inputPrepare(true)
                registerTask(1)
            }
        }
        else {
            taskPrepare(true)
            registerTask(1)
        }
    }
    fun generateAES(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        val requestType = object0418.selectedItemPosition
        if (requestType == INVALID_POSITION || requestType !in 0..1) {
            taskPrepare(true)
            registerTask(1)
            return
        }

        if (requestType == 1) {
            inputPrepare(false)
            val taskStr = object0420.text.toString()
            if (taskStr.isNotEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Confirm <resource run>?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            outputGraphMessage("Sql/Resource")
                            val mouseTask = GetAES(this, taskMorgoth, 3, taskStr, exchangeMap)
                            mouseTask.execute()
                        }
                        .setNegativeButton("No") { dialog, id ->
                            taskPrepare(true)
                            inputPrepare(true)
                            registerTask(1)
                        }
                val alert = builder.create()
                alert.show()
            }
            else {
                taskPrepare(true)
                inputPrepare(true)
                registerTask(1)
            }
        }
        else {
            val taskIndex = object0417.selectedItemPosition
            if (object0417.selectedItemId != INVALID_ROW_ID && taskIndex > 0 && taskIndex < hash_list.size) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Confirm <aes hash erase>?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            outputGraphMessage("AES/Clear")
                            val mouseTask = GetAES(this, taskMorgoth, 4, hash_list[taskIndex])
                            mouseTask.execute()
                        }
                        .setNegativeButton("No") { dialog, id ->
                            dialog.dismiss()
                            taskPrepare(true)
                            registerTask(1)
                        }
                val alert = builder.create()
                alert.show()
            }
            else {
                inputPrepare(false)
                val taskStr = object0420.text.toString()
                if (taskStr.isNotEmpty()) {
                    outputGraphMessage("AES/Insert")
                    val mouseTask = GetAES(this, taskMorgoth, 2, taskStr.toUpperCase(Locale.ENGLISH))
                    mouseTask.execute()
                }
                else {
                    taskPrepare(true)
                    inputPrepare(true)
                    registerTask(1)
                }
            }
        }
    }
    fun setStorageExport(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <export storage>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    outputGraphMessage("Storage/Export")
                    val mouseTask = SetBase(this, 1)
                    mouseTask.execute()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    taskPrepare(true)
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun getBase(view: View?) {
        if (msgMairon == 0)
            return
        if (!registerTask(0))
            return
        taskPrepare(false)
        inputPrepare(false)
        val requestType: Int = object0418.selectedItemPosition
        if (requestType == INVALID_POSITION || requestType !in 0..1) {
            taskPrepare(true)
            inputPrepare(true)
            registerTask(1)
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <import base>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    if (requestType == 1) {
                        if ("request" in totalResource) {
                            val taskUrl = totalResource["request"]
                            if (taskUrl!!.isNotEmpty()) {
                                val urlTask = "https://docs.google.com/spreadsheets/d/e/${taskUrl}/pub?output=csv"
                                //    Log.i("URL", urlTask)
                                val mouseTask = GetBase(this, taskMorgoth, requestType, urlTask, exchangeMap)
                                mouseTask.execute()
                                outputGraphMessage("Base/Import Online")
                            } else {
                                taskPrepare(true)
                                inputPrepare(true)
                                outputGraphMessage("Base/Verify Length")
                                registerTask(1)
                            }
                        }
                        else {
                            taskPrepare(true)
                            inputPrepare(true)
                            outputGraphMessage("Base/Verify Site")
                            registerTask(1)
                        }
                    }
                    else {
                        val mouseTask = GetBase(this, taskMorgoth, requestType, "", exchangeMap)
                        mouseTask.execute()
                        outputGraphMessage("Base/Import Wifi")
                    }
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    taskPrepare(true)
                    inputPrepare(true)
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun goToInputControl(view: View?) {
        if (!registerTask(0))
            return
        val intentResult = Intent()
        intentResult.putExtra(_message, msgMairon)
        this.setResult(Activity.RESULT_OK, intentResult)
        this.finish()
    }
    private fun outputGraphMessage(msg: String) {
        sauron_output.text = msg
    }
    private fun taskPrepare(type: Boolean) {
        object0417.isEnabled = type
        object0418.isEnabled = type
    }
    private fun inputPrepare(type: Boolean) {
        object0420.isEnabled = type
    }
    /*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //    Log.i("MESSAGE", "$requestCode/$resultCode/$data")
            if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
                val msg = data.getStringExtra(_message)
            //    Log.i("RESULT", msg)
            }
        }   */
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
    private class InitActivity(thisLogic: ControlLogic, task_morgoth: String) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<ControlLogic> = WeakReference(thisLogic)
        private val MorgothValue: String
        private var maironSauron: Boolean = false
        //    private var taskTotal: HashMap<String, String>? = null
        private var taskTotal: String? = null
        private var taskBigList: Array<String>? = null
        private var taskAES: JSONArray? = null

        init {
            this.MorgothValue = task_morgoth
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
            val sqlResource = SQLResourceTable(root)
            val sqlAES = SQLAESTable(root)
            try {
                taskTotal = sqlResource.getRecord("request")
                //    taskTotal = sqlResource.allRecords
                taskAES = sqlAES.allRecords
                taskTotal = AES256.toText(taskTotal!!, keyStr)
            }
            catch (e: Exception) {
                maironSauron = true
            //    taskTotal = hashMapOf()
                taskTotal = ""
                taskAES = null
            }

            if (taskTotal.isNullOrEmpty()) {
                maironSauron = true
                if (taskTotal == null)
                //    taskTotal = hashMapOf()
                    taskTotal = ""
            }
            /*    if (!("api1" in taskTotal!!))
                    taskTotal!!["api1"] = ""
                if (!("api2" in taskTotal!!))
                    taskTotal!!["api2"] = ""
                if (!("usoffset" in taskTotal!!))
                    taskTotal!!["usoffset"] = "false"   */

            taskBigList = root.resources.getStringArray(R.array.big_list2)
            for (s in 0 until taskBigList!!.size)
                taskBigList!![s] = AES256.toText(taskBigList!![s], keyStr)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            root.bigList = taskBigList!!
            root.maironAnnatar = maironSauron
            //    root.totalResource = taskTotal!!
            root.totalResource["request"] = taskTotal!!
            if (maironSauron) {
                root.outputGraphMessage("Epic Mairon")
                root.object0404.setBackgroundColor(Color.rgb(255, 64, 128))
                root.object0405.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0409.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0410.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0411.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0412.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0413.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0414.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0415.setBackgroundColor(Color.rgb(255, 0, 0))
                root.object0420.setText("")
            }

            root.sauron_output.movementMethod = ScrollingMovementMethod()
            val request_source: Spinner = root.findViewById(R.id.object0418) as Spinner
            val adapterSpinner1 = ArrayAdapter.createFromResource(root,
                    R.array.request_source, R.layout.cast_tracks_chooser_dialog_row_layout)
            adapterSpinner1.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            request_source.adapter = adapterSpinner1
            request_source.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> {
                            root.object0420.filters = arrayOf<InputFilter>(AllCaps())
                            root.object0420.setText("")
                            root.object0411.setText("KEY")
                            root.object0410.setText("PRINT")
                            root.object0404.setText("SCALE")
                            root.object0408.setText("RSV")
                        }
                        1 -> {
                            root.object0420.filters = arrayOf<InputFilter>()
                            if (!root.maironAnnatar)
                                root.object0420.setText(root.resourceInit)
                            else
                                root.object0420.setText("")
                            root.object0411.setText("RES")
                            root.object0410.setText("ERASE")
                            root.object0404.setText("HOME")
                            root.object0408.setText("CLR")
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

            root.aes_list = ArrayList<String>()
            root.aes_list.add("AES List")
            root.short_aes_list = ArrayList<String>()
            root.short_aes_list.add("AES List")
            root.hash_list = ArrayList<String>()
            root.hash_list.add("Empty")
            if (taskAES != null) {
                for (s in 0 until taskAES!!.length()) {
                    var outputRow: JSONArray? = null
                    try {
                        outputRow = taskAES!!.getJSONArray(s)
                    }
                    catch (e: Exception) { }
                    if (outputRow == null)
                        continue
                    if (outputRow.length() < 2)
                        continue

                    root.aes_list.add(outputRow[1].toString())
                    val short_aes_name = outputRow[1].toString()
                    root.short_aes_list.add(short_aes_name.substring(0, 10.coerceAtMost(short_aes_name.length)))
                    root.hash_list.add(outputRow[0].toString())
                }
                val taskLength = taskAES!!.length()
                if (taskLength < root.object0417.count)
                    root.object0417.setSelection(taskLength)
                else if (root.object0417.count > 0)
                    root.object0417.setSelection(0)
            }
            root.adapterSpinnerAES = ArrayAdapter<String>(root,
                    R.layout.cast_tracks_chooser_dialog_row_layout, root.short_aes_list)
            root.adapterSpinnerAES.setDropDownViewResource(R.layout.cast_tracks_chooser_dialog_layout)
            root.object0417.adapter = root.adapterSpinnerAES
            //    root.adapterSpinnerAES.notifyDataSetChanged()

            root.object0417.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    if (root.initComplete) {
                        if (position > 0) {
                            //    Log.i("WRITE", "AES")
                            val taskIndex = root.object0417.selectedItemPosition
                            if (!root.aesRequest && taskIndex < root.hash_list.size)
                                root.outputGraphMessage("Hash/Request\n${root.hash_list[taskIndex]}")
                            else
                                root.aesRequest = false
                        }
                    }
                    else {
                        root.initComplete = true
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

            //    Log.i("REQUEST", "value=${taskTotal.toString()}")
            root.taskPrepare(true)
            root.inputPrepare(true)
            root.registerTask(1)
        }
    }
    //----------------------------------------  BaseOperation ----------------------------------------
    private class BaseOperation(thisLogic: ControlLogic, morgoth_value: String, request_type: Int) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<ControlLogic> = WeakReference(thisLogic)
        private val MorgothValue: String
        private val baseName = "SpecialBase"
        private var requestType: Int = 0
        private var baseFile: File? = null
        private var baseHash: File? = null
        private var baseJournal: File? = null
        private var outputResult: Int = 0
        private var taskRun: Boolean = false

        init {
            this.MorgothValue = morgoth_value
            val root = reference.get()
            if (root != null && !root.isFinishing) {
                this.requestType = request_type
                this.baseFile = File(root.filesDir.parent + "/databases", baseName)
                this.baseHash = File(root.filesDir.parent + "/databases", baseName + "Hash")
                this.baseJournal = File(root.filesDir.parent + "/databases", baseName + "-journal")
                taskRun = true
            }
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing || !taskRun)
                return null

            outputResult = 0
            when (requestType) {
                0 -> {
                    if (root.uri1 == null || root.uri2 == null) {
                        outputResult = -10
                        return null
                    }
                    //    Log.i("COPY", "${root!!.uri1!!.path}/${root.uri2!!.path!!}")
                    var inputStream: InputStream? = null
                    try {
                        inputStream = root.contentResolver.openInputStream(root.uri1!!)
                    }
                    catch (e: Exception) {
                        if (inputStream != null) {
                            inputStream.close()
                            inputStream = null
                        }
                        outputResult = 2
                    }

                    if (outputResult != 2 && inputStream != null) {
                        try {
                            val outputStream: OutputStream? = root.contentResolver.openOutputStream(root.uri2!!)
                            if (outputStream == null) {
                                outputResult = -5
                                return null
                            }
                            outputResult = FileLogic.copy(inputStream, outputStream)

                            //    Log.i("OUTPUTRESULT1", outputResult.toString())
                            if (outputResult == 1) {
                                val hashStream1: InputStream? = root.contentResolver.openInputStream(root.uri1!!)
                                val hashStream2: InputStream? = root.contentResolver.openInputStream(root.uri2!!)
                                if (hashStream1 != null && hashStream2 != null) {
                                    val hash1 = FileLogic.sha256(hashStream1)
                                    val hash2 = FileLogic.sha256(hashStream2)
                                    if (hash1 != hash2 || hash1.isEmpty() || hash2.isEmpty())
                                        outputResult = -6
                                }
                                else {
                                    outputResult = -6
                                }
                                //    Log.i("HASH1", "$hash1/$hash2")
                            }
                        } catch (e: Exception) {
                            outputResult = -5
                            //    Log.i("EXCEPTION", "${e.toString()}/${e.message.toString()}")
                            return null
                        }
                        if (outputResult != 1)
                            return null
                    }

                    //    Log.i("WRITE", "${baseFile.absolutePath}/${root.uri1!!.path!!}")
                    try {
                        val writeStream: OutputStream? = root.contentResolver.openOutputStream(root.uri1!!)
                        if (writeStream == null) {
                            outputResult = -5
                            return null
                        }
                        val readStream: InputStream = FileInputStream(baseFile!!)
                        outputResult = FileLogic.encrypt(readStream, writeStream, MorgothValue)
                    } catch (e: Exception) {
                        outputResult = -5
                        return null
                    }
                    if (outputResult < 0)
                        return null


                    //    Log.i("DECRYPT", "${root.uri1!!.path!!}/${baseHash.absolutePath}")
                    try {
                        val checkReadStream: InputStream? = root.contentResolver.openInputStream(root.uri1!!)
                        if (checkReadStream == null) {
                            outputResult = -7
                            return null
                        }
                        val checkWriteStream: OutputStream = FileOutputStream(baseHash!!)
                        outputResult = FileLogic.decrypt(checkReadStream, checkWriteStream, MorgothValue)

                        //    Log.i("OUTPUTRESULT2", outputResult.toString())
                        if (outputResult == 1) {
                            val hashStream1: InputStream = FileInputStream(baseFile!!)
                            val hashStream2: InputStream = FileInputStream(baseHash!!)
                            val hash1 = FileLogic.sha256(hashStream1)
                            val hash2 = FileLogic.sha256(hashStream2)
                            if (hash1 != hash2 || hash1.isEmpty() || hash2.isEmpty()) {
                                //    baseHash.delete()
                                outputResult = -8
                            }
                            //    Log.i("HASH2", "$hash1/$hash2")
                        }
                    }
                    catch (e: Exception) {
                        try {
                            if (baseHash!!.exists())
                                baseHash!!.delete()
                        }
                        catch (e: Exception) { }
                        outputResult = -7
                        return null
                    }
                    if (outputResult < 1) {
                        try {
                            if (baseHash!!.exists())
                                baseHash!!.delete()
                        }
                        catch (e: Exception) { }
                        return null
                    }

                    //    Log.i("CLEAR", "${baseHash.absolutePath}")
                    try {
                        //    baseJournal.delete()
                        //    baseFile.delete()
                        if (baseHash!!.exists())
                            baseHash!!.delete()
                    }
                    catch (e: Exception) {
                        outputResult = -9
                        return null
                    }
                }
                1 -> {
                    if (root.uri1 == null) {
                        outputResult = -5
                        return null
                    }
                    //    Log.i("READ", "${root!!.uri1!!.path}/${baseFile.absolutePath}")
                    try {
                        val inputStream: InputStream? = root.contentResolver.openInputStream(root.uri1!!)
                        if (inputStream == null) {
                            outputResult = -4
                            return null
                        }
                        val outputStream: OutputStream = FileOutputStream(baseFile!!)
                        outputResult = FileLogic.decrypt(inputStream, outputStream, MorgothValue)
                    }
                    catch (e: Exception) {
                        outputResult = -4
                        return null
                    }
                }
                2 -> {
                    if (root.uri2 == null) {
                        outputResult = -5
                        return null
                    }
                    //    Log.i("READ", "${root!!.uri2!!.path}/${baseFile.absolutePath}")
                    try {
                        val inputStream: InputStream? = root.contentResolver.openInputStream(root.uri2!!)
                        if (inputStream == null) {
                            outputResult = -4
                            return null
                        }
                        val outputStream: OutputStream = FileOutputStream(baseFile!!)
                        outputResult = FileLogic.decrypt(inputStream, outputStream, MorgothValue)
                    }
                    catch (e: Exception) {
                        outputResult = -4
                        return null
                    }
                }
                3 -> {
                    //    Log.i("CLEAR", "${baseJournal.absolutePath}/${baseFile.absolutePath}")
                    try {
                        if (baseJournal!!.exists())
                            baseJournal!!.delete()
                        if (baseHash!!.exists())
                            baseHash!!.delete()
                        if (baseFile!!.exists())
                            baseFile!!.delete()
                        outputResult = 1
                    }
                    catch (e: Exception) {
                        outputResult = -1
                        return null
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing || !taskRun)
                return
            if (outputResult == 0) {
                root.outputGraphMessage("Rewrite/Verify Request")
                root.taskPrepare(true)
                root.registerTask(1)
                return
            }

            var outputMessage: String = ""
            when (requestType) {
                0 -> {
                    when (outputResult) {
                        -10 -> outputMessage = "Rewrite/File Access"
                        -9 -> outputMessage = "Error/Clear"
                        -8 -> outputMessage = "Error/Hash 2"
                        -7 -> outputMessage = "Error/Decrypt"
                        -6 -> outputMessage = "Error/Hash 1"
                        -5 -> outputMessage = "Error/Stream"
                        -4 -> outputMessage = "Error/Copy"
                        -3 -> outputMessage = "Error/Write"
                        -2 -> outputMessage = "Error/AES"
                        -1 -> outputMessage = "Error/Read"
                        1, 2 -> outputMessage = "Rewrite/Complete"
                    }
                    root.taskPrepare(true)
                    root.registerTask(1)
                }
                1 -> {
                    when (outputResult) {
                        -5 -> outputMessage = "Restore 1/File Access"
                        -4 -> outputMessage = "Error/Stream"
                        -3 -> outputMessage = "Error/Write"
                        -2 -> outputMessage = "Error/AES"
                        -1 -> outputMessage = "Error/Read"
                        1 -> outputMessage = "Restore 1/Create"
                    }
                    if (outputResult == 1) {
                        root.object0404.setBackgroundColor(Color.rgb(255, 228, 213))
                        root.object0405.setBackgroundColor(Color.rgb(211, 255, 213))
                        root.object0409.setBackgroundColor(Color.rgb(226, 170, 255))
                        root.object0410.setBackgroundColor(Color.rgb(255, 228, 213))
                        root.object0411.setBackgroundColor(Color.rgb(211, 255, 213))
                        root.object0412.setBackgroundColor(Color.rgb(128, 141, 255))
                        root.object0413.setBackgroundColor(Color.rgb(128, 141, 255))
                        root.object0414.setBackgroundColor(Color.rgb(128, 141, 255))
                        root.object0415.setBackgroundColor(Color.rgb(128, 141, 255))
                        val catTask = InitActivity(root, MorgothValue)
                        catTask.execute()
                        root.msgMairon = 2
                    }
                    else {
                        root.taskPrepare(true)
                        root.registerTask(1)
                    }
                }
                2 -> {
                    when (outputResult) {
                        -5 -> outputMessage = "Restore 2/File Access"
                        -4 -> outputMessage = "Error/Stream"
                        -3 -> outputMessage = "Error/Write"
                        -2 -> outputMessage = "Error/AES"
                        -1 -> outputMessage = "Error/Read"
                        1 -> outputMessage = "Restore 2/Create"
                    }
                    if (outputResult == 1) {
                        root.object0404.setBackgroundColor(Color.rgb(255, 228, 113))
                        root.object0405.setBackgroundColor(Color.rgb(211, 255, 213))
                        root.object0409.setBackgroundColor(Color.rgb(226, 170, 255))
                        root.object0410.setBackgroundColor(Color.rgb(255, 228, 113))
                        root.object0411.setBackgroundColor(Color.rgb(211, 255, 213))
                        root.object0412.setBackgroundColor(Color.rgb(128, 141, 255))
                        root.object0413.setBackgroundColor(Color.rgb(128, 141, 255))
                        root.object0414.setBackgroundColor(Color.rgb(128, 141, 255))
                        root.object0415.setBackgroundColor(Color.rgb(128, 141, 255))
                        val catTask = InitActivity(root, MorgothValue)
                        catTask.execute()
                        root.msgMairon = 2
                    }
                    else {
                        root.taskPrepare(true)
                        root.registerTask(1)
                    }
                }
                3 -> {
                    when (outputResult) {
                        -1 -> outputMessage = "Error Clear"
                        1 -> outputMessage = "Base/Clear"
                    }
                    if (outputResult == 1) {
                        root.object0404.setBackgroundColor(Color.rgb(255, 64, 128))
                        root.object0405.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0409.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0410.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0411.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0412.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0413.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0414.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0415.setBackgroundColor(Color.rgb(255, 0, 0))
                        root.object0420.setText("")
                        root.aes_list.clear()
                        root.short_aes_list.clear()
                        root.hash_list.clear()
                        root.totalResource.clear()
                        root.msgMairon = 0
                    }
                    root.taskPrepare(true)
                    root.registerTask(1)
                }
            }
            root.outputGraphMessage(outputMessage)
        }
    }
    //----------------------------------------  ClearStorage -----------------------------------------
    private class ClearStorage(thisLogic: ControlLogic, task_morgoth: String, request_type: Int) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<ControlLogic> = WeakReference(thisLogic)
        private val MorgothValue: String
        private val requestType: Int
        //    private var taskTotal: HashMap<String, String>? = null
        private var taskTotal: String? = null
        private var taskResult: Boolean = false

        init {
            this.MorgothValue = task_morgoth
            this.requestType = request_type
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            when (requestType) {
                0 -> {
                    val sqlStorage = SQLStorageTable(root)
                    sqlStorage.recreate()
                    taskResult = !sqlStorage.checkExist
                    //    root.sqlAES.recreate()
                    //    Log.i("STORAGE", "RECREATE")
                }
                1 -> {
                    val sqlNameLogo = SQLNameLogoTable(root)
                    sqlNameLogo.recreate()
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

                    taskTotal = ""
                    val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
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

                    val sqlBig = SQLBigTable(root)
                    sqlBig.recreate()
                    //    SQLBigBase().Insert(sqlBig)

                    val sqlSchedule = SQLScheduleTable(root)
                    sqlSchedule.recreate()

                    taskResult = true
                    //    taskResult = sqlResource.checkExist
                }
                2 -> {
                    taskResult = SQLNameLogoTable(root).deleteRecord("OTHER", false)
                    SQLTaskTable(root).deleteRecord(_OTHER)
                }
                3 -> {
                    val sqlNameLogo = SQLNameLogoTable(root)
                    sqlNameLogo.recreate()
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

                    taskTotal = ""
                    val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
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

                    val sqlBig = SQLBigTable(root)
                    sqlBig.recreate()
                    //    SQLBigBase().Insert(sqlBig)

                    val sqlSchedule = SQLScheduleTable(root)
                    sqlSchedule.recreate()

                    val sqlStorage = SQLStorageTable(root)
                    sqlStorage.recreate()

                    taskResult = true
                    //    taskResult = sqlResource.checkExist
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return
            if (!taskResult) {
                root.outputGraphMessage("Base/Verify Request")
                root.taskPrepare(true)
                root.registerTask(1)
                return
            }

            when (requestType) {
                0 -> {
                    root.outputGraphMessage("Storage Erase/Complete")
                }
                1 -> {
                    root.outputGraphMessage("Base Recreate/Complete")
                    //    if ("request" in taskTotal)
                    //        root.object0420.setText(taskTotal["request"])

                    root.object0404.setBackgroundColor(Color.rgb(255, 228, 213))
                    root.object0405.setBackgroundColor(Color.rgb(211, 255, 213))
                    root.object0409.setBackgroundColor(Color.rgb(226, 170, 255))
                    root.object0410.setBackgroundColor(Color.rgb(255, 228, 213))
                    root.object0411.setBackgroundColor(Color.rgb(211, 255, 213))
                    root.object0412.setBackgroundColor(Color.rgb(128, 141, 255))
                    root.object0413.setBackgroundColor(Color.rgb(128, 141, 255))
                    root.object0414.setBackgroundColor(Color.rgb(128, 141, 255))
                    root.object0415.setBackgroundColor(Color.rgb(128, 141, 255))
                    //    root.totalResource = taskTotal!!
                    root.totalResource["request"] = taskTotal!!
                    val catTask = InitActivity(root, MorgothValue)
                    catTask.execute()
                    root.msgMairon = 2
                }
                2 -> {
                    root.outputGraphMessage("Scale Erase/Complete")
                }
            }
            root.taskPrepare(true)
            root.registerTask(1)
        }
    }
    //---------------------------------------  AsyncPrintEvent ---------------------------------------
    private class PrintEvent(thisLogic: ControlLogic) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<ControlLogic> = WeakReference(thisLogic)
        private var outputResult1: JSONArray? = null
        private var outputResult2: JSONArray? = null
        private var usageTask: HashMap<String, Int>? = null
        //    private var taskRun: Boolean = false

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val sqlAES = SQLAESTable(root)
            val sqlStorage = SQLStorageTable(root)
            //    Log.i("AES", "${sqlAES.checkExist}/${sqlStorage.checkExist}")
            //    if (sqlAES.checkExist && sqlStorage.checkExist) {
            outputResult1 = sqlAES.allRecords
            outputResult2 = sqlStorage.allRecords
            usageTask = sqlStorage.countHash()
            //        taskRun = true
            //    }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            var outputString: String = ""
            //    if (taskRun) {
            if (outputResult1!!.length() == 0 && outputResult2!!.length() == 0) {
                outputString = "Empty"
            }
            else {
                if (outputResult1!!.length() > 0)
                    outputString += "AES\n==================================\n"
                for (s in 0 until outputResult1!!.length()) {
                    var outputRow: JSONArray? = null
                    try {
                        outputRow = outputResult1!!.getJSONArray(s)
                    }
                    catch (e: Exception) { }
                    if (outputRow == null)
                        continue
                    if (outputRow.length() < 2)
                        continue

                    val aes = outputRow[0]
                    val usageCount = if (aes in usageTask!!) usageTask!![aes] else 0
                    val taskStr = outputRow[1].toString()
                    outputString += "${aes}\n<${taskStr.substring(0, 10.coerceAtMost(taskStr.length))}/${usageCount}>\n\n"
                }
                if (outputResult2!!.length() > 0)
                    outputString += "Storage\n==================================\n"
                for (s in 0 until outputResult2!!.length()) {
                    var outputRow: JSONArray? = null
                    try {
                        outputRow = outputResult2!!.getJSONArray(s)
                    }
                    catch (e: Exception) { }
                    if (outputRow == null)
                        continue
                    if (outputRow.length() < 15)
                        continue

                    val priceArray = outputRow[5].toString()
                    //    var taskArray = outputRow[9].toString()
                    //    outputString += "${outputRow[0]}> " + (if (outputRow[12] == "true") "CRYPTO\n${outputRow[13]}" else "RAW") + "\n\n"
                    outputString += "E${outputRow[2]}/${outputRow[0]}> ${outputRow[8]}/${outputRow[4]} ${root.bigList[5]} ${outputRow[10]} <${outputRow[1]}> ${root.bigList[6]} {$priceArray} ${root.bigList[7]}=${outputRow[3]} ${root.bigList[8]}=${outputRow[6]} ${root.bigList[9]} {${outputRow[11]}} Crypto=${outputRow[12]} AES=${outputRow[13]} IV=${outputRow[14]}\n\n"
                }
            }
            //    }
            //    else {
            //        outputString = "SQL/Exception"
            //    }
            root.taskPrepare(true)
            root.outputGraphMessage(outputString)
            root.registerTask(1)
        }
    }
    //-----------------------------------------  GenerateAES -----------------------------------------
    private class GetAES(thisLogic: ControlLogic, task_morgoth: String, request_type: Int, parameter: String, exchange_map: HashMap<String, Int>? = null) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<ControlLogic> = WeakReference(thisLogic)
        private val MorgothValue: String
        private val requestType: Int
        private val taskParameter: String
        private val taskExchangeMap: HashMap<String, Int>?
        private var outputResult: String = ""
        private var outputName: String = ""
        private var hash: String = ""
        private var taskResult: Boolean = false
        private var sqlResult: Int = 0
        private var sqlList1: HashMap<String, String>? = null
        private var sqlList2: ArrayList<String>? = null

        init {
            this.MorgothValue = task_morgoth
            this.requestType = request_type
            this.taskParameter = parameter
            this.taskExchangeMap = exchange_map
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            when (requestType) {
                0, 1 -> {
                    val sqlAES = SQLAESTable(root)
                    val sqlStorage = SQLStorageTable(root)
                    hash = AES256.getSha256(taskParameter.toByteArray())
                    //    Log.i("HASH", "${taskParameter}/${hash}")
                    if (hash.isNotEmpty()) {
                        if (sqlAES.hasPrecache(hash)) {
                            //    Log.i("PRECACHE","EXIST")
                            outputResult = sqlStorage.cryptoRecord(taskParameter, hash, requestType).toString()
                        }
                    }
                }
                2 -> {
                    val sqlAES = SQLAESTable(root)
                    outputResult = AES256.getSha256(taskParameter.toByteArray())
                    if (outputResult.isNotEmpty()) {
                        outputName = sqlAES.addRecord(outputResult, null)
                    }
                    else {
                        outputName = "Hash"
                    }
                }
                3 -> {
                    var sqlRequest = taskParameter.split("=")
                    if (sqlRequest.size > 1) {
                        if (sqlRequest[0].isNotEmpty() && sqlRequest[1].isNotEmpty()) {
                            val sqlType = sqlRequest[0]
                            val iv = AES256.getIV()
                            val ivStr = AES256.decodeHexString(iv)
                            val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
                            var taskSrc: String = sqlRequest[1]
                            var requestSearch = sqlType.toUpperCase(Locale.ROOT)

                            when {
                                requestSearch == "SPEC" -> {
                                    var taskSpecial: String = ""
                                    if (taskSrc.length > 11) {
                                        val typeList = arrayOf("V1", "V2", "V3", "V4")
                                        taskSpecial = taskSrc.substring(taskSrc.length - 2, taskSrc.length).toUpperCase(Locale.ROOT)
                                        if (taskSpecial !in typeList) {
                                            sqlResult = 3
                                        }
                                        else {
                                            val inputSrc = taskSrc.substring(0, taskSrc.length - 2)
                                            try {
                                                taskSrc = "dd/MM/yyyy".utcParse(inputSrc, true).time.toString()
                                            } catch (e: Exception) {
                                                sqlResult = 3
                                            }

                                            if (sqlResult == 0) {
                                                val outputText: String? = AES256.encryptString(AES256.getSalt(taskSrc), keyStr, ivStr)
                                                if (outputText != null) {
                                                    val taskValue = "$iv^$outputText"
                                                    val sqlBig = SQLBigTable(root)
                                                //    Log.i("MOON", "<${sqlType}/${taskSrc}/${taskSpecial}/${taskValue}>")
                                                    val requestOutput = sqlBig.addRecord(taskValue, taskSpecial)
                                                    if (!requestOutput) {
                                                        sqlResult = 6
                                                    }
                                                    else {
                                                        val sortResult = sqlBig.sortRecord(keyStr)
                                                        if (sortResult > 0) {
                                                            sqlResult = 7
                                                        }
                                                        else {
                                                            var totalOutput: Array<String>? = null
                                                            val taskSpecialCompare = taskSpecial.toUpperCase(Locale.ROOT)
                                                            if (taskSpecialCompare == "V1" || taskSpecialCompare == "V2")
                                                                totalOutput = sqlBig.allRecords(100, 1, keyStr)
                                                            else
                                                                totalOutput = sqlBig.allRecords(100, 2, keyStr)
                                                            if (!totalOutput.isNullOrEmpty()) {
                                                                sqlList2 = arrayListOf()
                                                                for (s in 0 until totalOutput.size)
                                                                    sqlList2!!.add(totalOutput[s])
                                                            }
                                                        }
                                                    }
                                                }
                                                else {
                                                    sqlResult = 5
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        sqlResult = 4
                                    }
                                }
                                requestSearch == "MKT" -> {
                                    val paramList = taskSrc.split(",")
                                    var taskValue1: Long = 0L
                                    var taskValue2: Int = 0
                                    var exchangeType: String = ""
                                    var commandValue: Int = 0
                                    if (paramList.size == 1) {
                                        commandValue = -1
                                    }

                                    try {
                                        taskValue1 = "dd/MM/yyyy".utcParse(paramList[0]).time / 1000L
                                        if (commandValue != -1) {
                                            exchangeType = paramList[1].toUpperCase(Locale.ROOT)
                                            val mapValue = exchangeMap[exchangeType]
                                            mapValue?.let { taskValue2 = mapValue } ?: throw Exception("MAP")
                                            commandValue = paramList[2].toInt()
                                        }
                                    }
                                    catch (e: Exception) {
                                        sqlResult = 3
                                    }

                                    if (sqlResult == 0) {
                                        val sqlScheduleTable = SQLScheduleTable(root)
                                        var requestOutput: Boolean = false
                                        if (commandValue >= 0)
                                            requestOutput = sqlScheduleTable.addRecord(taskValue1, taskValue2, commandValue >= 1)
                                        else
                                            requestOutput = sqlScheduleTable.deleteRecord(taskValue1)
                                        if (!requestOutput) {
                                            sqlResult = 6
                                        }
                                        else {
                                            var commandText: String = ""
                                            if (commandValue > 0)
                                                commandText = "HOLIDAY"
                                            else if (commandValue == 0)
                                                commandText = "WORK"
                                            else
                                                commandText = "ERASE"
                                            sqlList1 = hashMapOf()
                                            sqlList1!!["$taskValue1"] = "$exchangeType/$taskValue2\n$commandText/$commandValue"
                                        }
                                    }
                                }
                                requestSearch == "CLEAR" -> {
                                    if (taskSrc.contains("/")) {
                                        var searchValue: Long = 0L
                                        try {
                                            searchValue = "dd/MM/yyyy".utcParse(taskSrc).time
                                        }
                                        catch (e: Exception) {
                                            sqlResult = 4
                                        }

                                        if (sqlResult == 0) {
                                            val sqlBig = SQLBigTable(root)
                                            val requestOutput = sqlBig.deleteRecord(searchValue, keyStr)
                                            if (!requestOutput) {
                                                sqlResult = 6
                                            }
                                            else {
                                                sqlList2 = arrayListOf()
                                                var totalOutput = sqlBig.allRecords(100, 1, keyStr)
                                                for (s in 0 until totalOutput.size)
                                                    sqlList2!!.add(totalOutput[s])
                                                totalOutput = sqlBig.allRecords(100, 2, keyStr)
                                                if (totalOutput.isNotEmpty())
                                                    sqlList2!!.add("==================================")
                                                for (s in 0 until totalOutput.size)
                                                    sqlList2!!.add(totalOutput[s])
                                                if (sqlList2!!.isEmpty())
                                                    sqlList2 = null
                                            }
                                        }
                                    }
                                    else if (taskSrc.matches("^[_A-Z]+\$".toRegex())) {
                                        val sqlName = SQLNameLogoTable(root)
                                        val requestOutput = sqlName.deleteRecord(taskSrc, true)
                                        if (!requestOutput) {
                                            sqlResult = 6
                                        }
                                        else {
                                            val sqlTask = SQLTaskTable(root)
                                            val sqlSearch = sqlTask.checkRecord(taskSrc)
                                            if (sqlSearch) {
                                                val sqlSpecialOutput = sqlTask.deleteRecord(taskSrc)
                                                if (!sqlSpecialOutput)
                                                    outputResult = "\nTask Verify"
                                            }
                                            sqlList1 = hashMapOf()
                                            sqlList1!![taskSrc] = "CLEAR"
                                        }
                                    }
                                    else {
                                        val sqlResource = SQLResourceTable(root)
                                        val requestOutput = sqlResource.deleteRecord(taskSrc)
                                        if (!requestOutput) {
                                            sqlResult = 6
                                        }
                                        else {
                                            sqlList1 = hashMapOf()
                                            sqlList1!![taskSrc] = "CLEAR"
                                            /*    sqlList1 = sqlResource.allRecords
                                                for (s in sqlList1!!.keys)
                                                    sqlList1!![s] = AES256.toText(sqlList1!![s]!!, keyStr)  */
                                        }
                                    }
                                }
                                requestSearch == "OUTPUT" -> {
                                    val typeSearch = taskSrc.toUpperCase(Locale.ROOT)
                                    when (typeSearch) {
                                        "SPEC" -> {
                                            val sqlBig = SQLBigTable(root)
                                            sqlList2 = arrayListOf()
                                            var totalOutput = sqlBig.allRecords(100, 1, keyStr)
                                            for (s in 0 until totalOutput.size)
                                                sqlList2!!.add(totalOutput[s])
                                            totalOutput = sqlBig.allRecords(100, 2, keyStr)
                                            if (totalOutput.isNotEmpty())
                                                sqlList2!!.add("==================================")
                                            for (s in 0 until totalOutput.size)
                                                sqlList2!!.add(totalOutput[s])
                                            if (sqlList2!!.isEmpty())
                                                sqlList2 = null
                                        }
                                        "MKT" -> {
                                            val sqlSchedule = SQLScheduleTable(root)
                                            sqlList2 = arrayListOf()
                                            sqlList2!!.add("USA/MOEX/CRYPTO\t${marketES[_USA]}/${marketES[_MOEX]}/${marketES[_CRYPTO]}")
                                            sqlList2!!.add("Calendar{USA/MOEX,1/0}|ERASE/-1")
                                            sqlList2!!.add("==================================")
                                            val totalOutput = sqlSchedule.allRecords
                                            for (s in 0 until totalOutput.size) {
                                                try {
                                                    val textDate = "dd/MM/yyyy".utcFormat(UtcDate(false, totalOutput[s].Index * 1000L), currentGreenwichOffset)
                                                    val textExchange = exchangeMap.filterValues { it == totalOutput[s].Exchange }.keys.elementAt(0)
                                                    val textType = if (totalOutput[s].Type) "HOLIDAY" else "WORK"
                                                    sqlList2!!.add("$textDate\t$textExchange/$textType")
                                                }
                                                catch (e: Exception) {
                                                    sqlList2!!.add("${totalOutput[s].Index}/${totalOutput[s].Exchange}/${totalOutput[s].Type}")
                                                }
                                            }
                                        }
                                        "STOCK" -> {
                                            val sqlNameLogo = SQLNameLogoTable(root)
                                            sqlList1 = hashMapOf()
                                            val totalOutput = sqlNameLogo.allRecords
                                            for (s in 0 until totalOutput.size)
                                                sqlList1!![totalOutput[s].Title] = "${totalOutput[s].Exchange}\t${totalOutput[s].Meta}\t${totalOutput[s].Logo.isNotEmpty()}\n${totalOutput[s].Name}"
                                        }
                                        else -> {
                                            sqlResult = 9
                                        }
                                    }
                                }
                                else -> {
                                    if (sqlType.matches("^[_A-Z]+$".toRegex())) {
                                        val sqlNameLogo = SQLNameLogoTable(root)
                                        var taskExchange: String = ""
                                        if (taskSrc.contains("/")) {
                                            val inputValue = taskSrc.split("/")
                                            if (inputValue.size > 1) {
                                                taskSrc = inputValue[0]
                                                taskExchange = inputValue[1]
                                            }
                                        }

                                        var taskExchangeValue = -1
                                        if (taskExchange in taskExchangeMap!!)
                                            taskExchangeValue = taskExchangeMap[taskExchange]!!
                                        else if (taskExchange.isNotEmpty())
                                            taskExchangeValue = -2

                                        //    Log.i("NAMELOGO", "<$sqlType/$taskSrc/$taskExchange>")
                                        if (taskExchangeValue >= -1) {
                                            val requestOutput = sqlNameLogo.addUpdateRecord(sqlType, taskExchange, taskSrc, "", "", "")
                                            if (!requestOutput) {
                                                sqlResult = 6
                                            }
                                            else {
                                                if (taskExchangeValue >= -1) {
                                                    val sqlTask = SQLTaskTable(root)
                                                    val sqlSpecialOutput = sqlTask.checkUpdate(sqlType, taskSrc, taskExchangeValue)
                                                    if (sqlSpecialOutput < 0)
                                                        outputResult = "\nTask Verify"
                                                } else {
                                                    outputResult = "\nExchange Verify"
                                                }

                                                sqlList1 = hashMapOf()
                                                sqlList1!![sqlType] = taskSrc + if (taskExchange.isNotEmpty()) "/$taskExchange" else ""
                                            }
                                        }
                                        else {
                                            sqlResult = 8
                                        }
                                    }
                                    else if (!sqlType.hasUpperCase()) {
                                        val outputText: String? = AES256.encryptString(AES256.getSalt(taskSrc), keyStr, ivStr)
                                        if (outputText != null) {
                                            val taskValue = "$iv^$outputText"
                                            val sqlResource = SQLResourceTable(root)
                                            //    Log.i("RESOURCE", "<${sqlType}/${taskSrc}/${taskValue}>")
                                            val requestOutput = sqlResource.addRecord(sqlType, taskValue)
                                            if (!requestOutput) {
                                                sqlResult = 6
                                            }
                                            else {
                                                sqlList1 = hashMapOf()
                                                sqlList1!![sqlType] = taskSrc
                                            }
                                        }
                                        else {
                                            sqlResult = 5
                                        }
                                    }
                                    else {
                                        sqlResult = 9
                                    }
                                }
                            }
                        }
                        else {
                            sqlResult = 2
                        }
                    }
                    else {
                        sqlResult = 1
                    }
                }
                4 -> {
                    val sqlAES = SQLAESTable(root)
                    hash = taskParameter
                    outputResult = sqlAES.deleteRecord(hash).toString()
                }
            }
            taskResult = true
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return
            if (!taskResult) {
                root.outputGraphMessage("Crypto/Verify Request")
                root.taskPrepare(true)
                root.registerTask(1)
                return
            }

            when (requestType) {
                0 -> {
                    if (outputResult.isNotEmpty())
                        root.outputGraphMessage("Crypto Mairon $outputResult/Size")
                    else
                        root.outputGraphMessage("Crypto Hash/Verify\n$hash")
                    root.object0420.setText("")
                    root.inputPrepare(true)
                }
                1 -> {
                    if (outputResult.isNotEmpty())
                        root.outputGraphMessage("Crypto Text $outputResult/Size")
                    else
                        root.outputGraphMessage("Crypto Hash/Verify\n$hash")
                    root.object0420.setText("")
                    root.inputPrepare(true)
                }
                2 -> {
                    if (outputName.isNotEmpty()) {
                        if (outputName == "Exist") {
                            root.outputGraphMessage("AES/Exist\n$outputResult")
                            //    var ivStr: ByteArray = AES256.decodeHexString("74F8B4181137F0F24655D4B30A1C2623")
                            //    var keyStr: ByteArray = AES256.getRawKey(taskParameter)
                            //    Log.i("PASSWORD", Base64.encodeToString(keyStr, Base64.DEFAULT))
                        }
                        else if (outputName == "Hash") {
                            root.outputGraphMessage("AES/Verify Hash")
                        }
                        else if (!outputName.contains("CATCH")) {
                            root.aes_list.add(outputName)
                            root.short_aes_list.add(outputName.substring(0, 10.coerceAtMost(outputName.length)))
                            root.hash_list.add(outputResult)
                            root.adapterSpinnerAES.notifyDataSetChanged()
                            root.outputGraphMessage("AES Insert/Complete\n$outputResult")
                            root.object0420.setText("")
                        }
                        else {
                            val taskValue = outputName.length
                            if (taskValue > 4 && outputName.length > 5)
                                root.outputGraphMessage(outputName.substring(5, taskValue.coerceAtMost(outputName.length)))
                            else
                                root.outputGraphMessage(outputName)
                        }
                    }
                    //    else {
                    //        root.outputGraphMessage("AES\n$outputName")
                    //    }
                    root.inputPrepare(true)
                }
                3 -> {
                    var outputText: String = ""
                    when (sqlResult) {
                        0 -> outputText = "Resource/Complete"
                        1 -> outputText = "Resource/Global Format"
                        2 -> outputText = "Resource/Length"
                        3 -> outputText = "Resource/Convert"
                        4 -> outputText = "Resource/Special Format"
                        5 -> outputText = "Resource/Crypto"
                        6 -> outputText = "Resource/SQL"
                        7 -> outputText = "Resource/Sort"
                        8 -> outputText = "Resource/Exchange"
                        9 -> outputText = "Resource/Type"
                    }

                    root.outputGraphMessage(outputText + outputResult)
                    if (sqlList1 != null) {
                        root.sauron_output.append("\n==================================")
                        for (s in sqlList1!!.keys)
                            root.sauron_output.append("\n${s}\t${sqlList1!![s]}")
                    }
                    if (sqlList2 != null) {
                        root.sauron_output.append("\n==================================")
                        for (s in sqlList2!!)
                            root.sauron_output.append("\n${s}")
                    }

                    root.object0420.setText(root.resourceInit)
                    root.inputPrepare(true)
                }
                4 -> {
                    if (outputResult == "true") {
                        val taskIndex = root.hash_list.indexOf(taskParameter)
                        if (taskIndex > 0 && taskIndex < root.aes_list.size && taskIndex < root.short_aes_list.size && taskParameter in root.hash_list) {
                            //    root.aesRequest = true
                            root.outputGraphMessage("AES Clear/Complete\n$hash")
                            root.aes_list.removeAt(taskIndex)
                            root.short_aes_list.removeAt(taskIndex)
                            root.hash_list.remove(taskParameter)
                            root.adapterSpinnerAES.notifyDataSetChanged()
                            if (root.object0417.count > 0)
                                root.object0417.setSelection(0)
                        }
                        else {
                            root.outputGraphMessage("AES Clear/Verify Item")
                        }
                    }
                    else {
                        root.outputGraphMessage("AES Clear/Verify SQL")
                    }
                }
            }
            //    root.aes_list.add(outputResult)
            //    root.adapterSpinnerAES.notifyDataSetChanged()
            root.taskPrepare(true)
            root.registerTask(1)
        }

        private fun String.isUpperCase(): Boolean {
            for (s in 0 until this.length) {
                if (!Character.isUpperCase(this[s]))
                    return false
            }
            return true
        }
        private fun String.hasUpperCase(): Boolean {
            for (s in 0 until this.length) {
                if (Character.isUpperCase(this[s]))
                    return true
            }
            return false
        }
    }
    //-----------------------------------------  AsyncGetBase ----------------------------------------
    private class GetBase(thisLogic: ControlLogic, morgoth_value: String, request_type: Int, request_string: String, exchange_map: HashMap<String, Int>? = null) : AsyncTask<Void, String, Void>() {
        private val reference: WeakReference<ControlLogic> = WeakReference(thisLogic)
        private val MorgothValue: String
        private val requestType: Int
        private val requestString: String
        private val taskExchangeMap: java.util.HashMap<String, Int>?
        private var outputResult: Pair<JSONArray?, String>? = null
        private var outputStr: String = ""
        private var count1: Int = 0
        private var count2: Int = 0
        private var stockList: ArrayList<NameLogoRecord>? = null

        init {
            this.MorgothValue = morgoth_value
            this.requestType = request_type
            this.requestString = request_string
            this.taskExchangeMap = exchange_map
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null
            //    Log.i("REQUEST", "${requestType}")

            val businessLogic: IBusinessLogic = RealBusinessLogic()
            if (requestType == 0 || requestType == 2) {
                //    Log.d("WIFI", "Let's sniff the network")

                var macCount = 0
                var br: BufferedReader? = null
                try {
                    br = BufferedReader(FileReader("/proc/net/arp"))
                    var line: String
                    //    var ipList = ArrayList<String>()
                    while (br.readLine().also { line = it } != null) {
                        val splitted = line.split(" +".toRegex()).toTypedArray()
                        if (splitted.size > 3) {
                            // Basic sanity check
                            val mac = splitted[3]
                            //    println("Mac : Outside If ${mac.toString()}")
                            if (mac.matches("..:..:..:..:..:..".toRegex())) {
                                /*    macCount++
                                    ClientList.add("Client(" + macCount + ")");
                                    IpAddr.add(splitted[0]);
                                    HWAddr.add(splitted[3]);
                                    Device.add(splitted[5]);    */
                                //    println("Mac : " + mac + " IP Address : " + splitted[0])
                                val ipResult = splitted[0]
                                outputResult = businessLogic.GetBase(ipResult, requestType)
                                //    businessDAO.SendWifi(ipResult, root.sqlStorage.getAllRecords.toString())
                                //    ipList.add(ipResult)
                                //    println("Mac_Count  $macCount MAC_ADDRESS  $mac")
                                break
                            }
                            /* for (int i = 0; i < splitted.length; i++)
                        System.out.println("Addressssssss     "+ splitted[i]);*/
                        }
                    }
                    /*    Log.i("IPLIST", "${ipList.size.toString()}/${ipList[0].toString()}")
                        if (ipList.isNotEmpty()) {
                            val businessDAO: IBusinessDAO = RealBusinessDAO()
                            businessDAO.SendWifi(ipList[0], "Hello World")
                        }   */
                }
                catch (e: Exception) {
                    //    Log.i("EXCEPTION2", "${e.toString()}/${e.message.toString()}")
                    outputResult = Pair(null, "${e}/${e.message}")
                }
            }
            else {
                outputResult = businessLogic.GetBase(requestString, 1)
            }
            //    Log.i("outputResult", outputResult.toString())

            if (outputResult == null)
                return null
            if (outputResult!!.first != null) {
                val keyStr: ByteArray = AES256.getRawKey(MorgothValue)
                //    Log.i("BASE", "${outputResult.second}/${outputResult.first!!.length()}")
                val sqlNameLogo = SQLNameLogoTable(root)
                val sqlBig = SQLBigTable(root)
                val sqlResource = SQLResourceTable(root)
                val sqlStorage = SQLStorageTable(root)
                val taskTotal: HashMap<String, String> = HashMap<String, String>()
                when (outputResult!!.second) {
                    "STOCK" -> {
                        stockList = arrayListOf()
                    }
                    "SPECIAL" -> {
                        sqlBig.recreate()
                    }
                }
                if (requestType == 1 && outputResult!!.first!!.length() > 0) {
                    stockList = arrayListOf()
                }

                for (s in 0 until outputResult!!.first!!.length()) {
                    var stock: JSONArray? = null
                    try {
                        stock = outputResult!!.first!!.getJSONArray(s)
                    }
                    catch (e: Exception) { }
                    if (stock == null)
                        continue
                    if (stock.length() < 2)
                        continue

                    val objectIndex = stock[0].toString()
                    val objectTask = stock[1].toString()
                    when (requestType) {
                        0 -> {
                            when (outputResult!!.second) {
                                "STOCK" -> {
                                    if (stock.length() < 6)
                                        continue
                                //    if (stock[1].toString() == "MOEX")
                                //    Log.i("AddNameLogoWiFi", "${objectIndex}/${objectTask}/${stock[2]}/${stock[3]}")
                                    val requestOutput = sqlNameLogo.addUpdateRecord(objectIndex, objectTask, stock[2].toString(), stock[3].toString(), stock[4].toString(), stock[5].toString())
                                    if (requestOutput) {
                                        stockList!!.add(NameLogoRecord(objectIndex, objectTask, stock[2].toString(), stock[3].toString(), stock[4].toString(), stock[5].toString()))
                                        count1++
                                    }
                                    count2++
                                }
                                "SPECIAL" -> {
                                    val ivStr: String = AES256.getIV()
                                    val inputValue = "dd/MM/yyyy".utcParse(objectIndex).time.toString()
                                    val outputValue = AES256.encryptString(AES256.getSalt(inputValue), keyStr, AES256.decodeHexString(ivStr))
                                    if (outputValue != null) {
                                        val taskValue = "$ivStr^$outputValue"
                                        //    Log.i("AddMoonWiFi", "${taskValue}/${stock[1]}")
                                        val requestOutput = sqlBig.addRecord(taskValue, objectTask)
                                        count1 += if (requestOutput) 1 else 0
                                    }
                                    count2++
                                }
                                "RESOURCE" -> {
                                    val ivStr: String = AES256.getIV()
                                    val outputValue = AES256.encryptString(AES256.getSalt(objectTask), keyStr, AES256.decodeHexString(ivStr))
                                    if (outputValue != null) {
                                        val taskValue = "$ivStr^$outputValue"
                                        //    Log.i("AddResourceWiFi", "${stock[0].toString()}/${taskValue}")
                                        val requestOutput = sqlResource.addRecord(objectIndex, taskValue)
                                        taskTotal[objectIndex] = taskValue
                                        count1 += if (requestOutput) 1 else 0
                                    }
                                    count2++
                                }
                            }
                            if (objectTask == "OTHER") {
                                if (outputStr.isNotEmpty())
                                    outputStr += ", "
                                outputStr += objectIndex
                            }
                            if (s % 100 == 0)
                                publishProgress(objectIndex)
                        }
                        1 -> {
                            if (stock.length() < 6)
                                continue
                        //    Log.i("AddNameLogoSite", "${objectIndex}/${objectTask}/${stock[2]}/${stock[3]}")
                            val requestOutput = sqlNameLogo.addUpdateRecord(objectIndex, objectTask, stock[2].toString(), stock[3].toString(), stock[4].toString(), stock[5].toString())
                            if (requestOutput) {
                                stockList!!.add(NameLogoRecord(objectIndex, objectTask, stock[2].toString(), stock[3].toString(), stock[4].toString(), stock[5].toString()))
                                count1++
                            }
                            count2++

                            if (objectTask == "OTHER") {
                                if (outputStr.isNotEmpty())
                                    outputStr += ", "
                                outputStr += objectIndex
                            }
                            if (s % 100 == 0)
                                publishProgress(objectIndex)
                        }
                        2 -> {
                            //    Log.i("AddStorageWiFi", "${stock[1]}")
                            if (stock.length() < 15)
                                continue
                            val requestOutput = sqlStorage.addRecord(objectIndex, objectTask, stock[2].toString(), stock[3].toString(), stock[4].toString(), stock[5].toString(), stock[6].toString(), stock[7].toString(), stock[8].toString(), stock[9].toString(), stock[10].toString(), stock[11].toString(), if (stock[12].toString() == "true") 1 else 0, stock[13].toString(), stock[14].toString())
                            count1 += if (requestOutput) 1 else 0
                            count2++
                            if (s % 100 == 0)
                                publishProgress(stock[2].toString())
                        }
                    }
                }

                when(outputResult!!.second) {
                    "STOCK" -> {
                        val sqlTask = SQLTaskTable(root)
                        val totalOutput = sqlTask.allRecords
                        when (requestType) {
                            0, 1 -> {
                                for (s in 0 until stockList!!.size) {
                                    if (stockList!![s].Title in totalOutput.keys && stockList!![s].Exchange in taskExchangeMap!!)
                                        sqlTask.checkUpdate(stockList!![s].Title, stockList!![s].Name, taskExchangeMap[stockList!![s].Exchange]!!)
                                }
                            }
                        }
                    }
                    "SPECIAL" -> {
                        val sortResult = sqlBig.sortRecord(keyStr)
                        if (sortResult > 0)
                            outputStr = "Verify Sort"
                    }
                    "RESOURCE" -> {
                        if ("request" in taskTotal) {
                            try {
                                taskTotal["request"] = AES256.toText(taskTotal["request"]!!, keyStr)
                                //    taskTotal.forEach { (key, value) -> taskTotal[key] = AES256.toText(value, keyStr) }
                            } catch (e: Exception) {
                                taskTotal["request"] = ""
                            }
                            root.totalResource["request"] = taskTotal["request"]!!
                        }
                        /*    try {
                                taskTotal.forEach { (key, value) -> taskTotal[key] = AES256.toText(value, keyStr) }
                            }
                            catch (e: Exception) {
                                if (!("api1" in taskTotal))
                                    taskTotal["api1"] = ""
                                if (!("api2" in taskTotal))
                                    taskTotal["api2"] = ""
                                if (!("usoffset" in taskTotal))
                                    taskTotal["usoffset"] = "false"
                            }
                            root.totalResource = taskTotal  */
                    }
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate()
            val root = reference.get()
            if (root == null || root.isFinishing || values.isNullOrEmpty())
                return
            if (values[0] != null)
                root.outputGraphMessage(values[0]!!)
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            if (outputResult != null) {
                if (outputResult!!.first != null) {
                    when (requestType) {
                        0, 1 -> {
                            var outputGraphStr: String = "Base Import> $count1/$count2"
                            if (outputStr.isNotEmpty())
                                outputGraphStr += "\n$outputStr"
                            root.outputGraphMessage(outputGraphStr)
                        }
                        2 -> {
                            root.outputGraphMessage("Storage Import> $count1/$count2")
                        }
                    }
                } else {
                    root.outputGraphMessage(outputResult!!.second)
                }
            }
            else {
                root.outputGraphMessage("Request/Init")
            }

            root.taskPrepare(true)
            root.inputPrepare(true)
            root.registerTask(1)
        }
    }
    //------------------------------------------  AsyncWIFI ------------------------------------------
    private class SetBase(thisLogic: ControlLogic, request_type: Int) : AsyncTask<Void?, Void?, Void?>() {
        private val reference: WeakReference<ControlLogic> = WeakReference(thisLogic)
        private val requestType: Int
        private var outputResult: String = ""
        //    private lateinit var taskTotal: HashMap<String, String>

        init {
            this.requestType = request_type
        }

        override fun doInBackground(vararg voids: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            //    Log.d("WIFI", "Let's sniff the network")
            var macCount = 0
            var br: BufferedReader? = null
            var taskStorage: JSONArray? = null
            try {
                br = BufferedReader(FileReader("/proc/net/arp"))
                var line: String
                while (br.readLine().also { line = it } != null) {
                    val splitted = line.split(" +".toRegex()).toTypedArray()
                    if (splitted.size > 3) {
                        val mac = splitted[3]
                        //    println("Mac : Outside If ${mac.toString()}")
                        if (mac.matches("..:..:..:..:..:..".toRegex())) {
                            //    macCount++
                            //    println("Mac : " + mac + " IP Address : " + splitted[0])
                            val ipResult = splitted[0]
                            val businessLogic: IBusinessLogic = RealBusinessLogic()
                            when (requestType) {
                                0 -> {
                                    val sqlNameLogo = SQLNameLogoTable(root)
                                    taskStorage = sqlNameLogo.allRecordsJSON
                                }
                                1 -> {
                                    val sqlStorage = SQLStorageTable(root)
                                    taskStorage = sqlStorage.allRecords
                                }
                            }

                            if (taskStorage == null)
                                break
                            if (taskStorage.length() > 0) {
                                outputResult = businessLogic.SetBase(ipResult, requestType, taskStorage.toString())
                            }
                            else {
                                when (requestType) {
                                    0 -> outputResult = "Base Export/Verify"
                                    1 -> outputResult = "Storage Export/Verify"
                                }
                            }
                            //    println("Mac_Count  $macCount MAC_ADDRESS  $mac")
                            break
                        }
                    }
                }
                /*    val subnet = "192.168.43"
                    for (i in 0..255) { //VERY INNEFICENT! Takes about 13 minutes to go through all hosts on ONE subnet(at 3000ms timeout)
                        val host = "$subnet.$i"
                        try {
                            if (InetAddress.getByName(host).isReachable(3000)) println("$host is reachable") else println("$host is not reachable")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }   */
            }
            catch (e: Exception) {
                outputResult = "${e}/${e.message}"
                //    Log.i("EXCEPTION2", "${e.toString()}/${e.message.toString()}")
            }
            if (outputResult.isEmpty()) {
                when(requestType) {
                    0 -> outputResult = "Base Export/Init"
                    1 -> outputResult = "Storage Export/Init"
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val root = reference.get()
            if (root == null || root.isFinishing)
                return

            if (outputResult == "OK") {
                when (requestType) {
                    0 -> root.outputGraphMessage("Base Export/Complete")
                    1 -> root.outputGraphMessage("Storage Export/Complete")
                }
            }
            else {
                root.outputGraphMessage(outputResult)
            }
            root.taskPrepare(true)
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
            //    else -> result = super.dispatchKeyEvent(event)
        }
        return result
    }
    /*    override fun onPause()
        {
            super.onPause()
            val am = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            am.moveTaskToFront(taskId, 0)
        }   */
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        try {
            val notificationIntent = Intent(this, ControlLogic::class.java)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            pendingIntent.send()
        }
        catch (e: CanceledException) {
            //    e.printStackTrace()
        }
    }
/*    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.i("TEST", "HERE")
        if(!hasFocus) {
            Log.i("FOCUS", "TASK")
        /*    val am = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val cn = am.getRunningTasks(1)[0].topActivity
            intent=packageManager.getLaunchIntentForPackage(cn.className)
            startActivity(intent)  */
        }
    }   */
    /*    private val windowCloseHandler: Handler = Handler()
    private val windowCloserRunnable = Runnable {
        val am = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn = am.getRunningTasks(1)[0].topActivity
        Log.i("PACKAGE", cn.className)
        if (cn != null && cn.className == "com.android.launcher3.Launcher") {
            toggleRecents()
        }
    }
    private fun toggleRecents() {
        val closeRecents = Intent("com.android.systemui.recents.action.TOGGLE_RECENTS")
        closeRecents.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        val recents = ComponentName("com.android.systemui", "com.android.systemui.recents.RecentsActivity")
        closeRecents.component = recents
        this.startActivity(closeRecents)
    }   */
}