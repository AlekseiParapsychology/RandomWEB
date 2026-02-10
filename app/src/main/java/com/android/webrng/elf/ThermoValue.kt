package com.android.webrng.elf

import android.app.*
import android.app.PendingIntent.CanceledException
import android.content.Intent
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentActivity
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.INVALID_POSITION
import com.android.webrng.R
import com.android.webrng.constants.*
import com.android.webrng.sql.NameLogoRecord
import com.android.webrng.sql.SQLNameLogoTable
import com.android.webrng.sql.SQLTaskTable
import com.android.webrng.utils.*
import com.android.webrng.utils.CustomListAdapter
import kotlinx.android.synthetic.main.abc_list_menu_item_layout.*
import kotlinx.android.synthetic.main.abc_list_menu_item_layout.sauron_output
import java.lang.ref.WeakReference
import java.util.*


class ThermoValue : FragmentActivity() {
    private var objectSycnhronize: Long = 1L
    private var taskMorgoth: String = ""
    private var apiValue: String? = null
    private var typeSector: Boolean = true
    private var typeStockSort: Boolean = false
    private var taskSP500MOEX: Int = 0
    private var listPosition1: Int = -1
    private var listPosition2: Int = -1

    private lateinit var adapterListIndices: CustomListAdapter
    private lateinit var adapterListResource: CustomListAdapter
    private lateinit var adapterListCurrency: CustomListAdapter
    private lateinit var adapterListSector: CustomListAdapter
    private lateinit var adapterListSP500: CustomListAdapter
    private lateinit var adapterTaskStack: CustomListAdapter

    private lateinit var customListIndices: CustomList
    private lateinit var customListResource: CustomList
    private lateinit var customListCurrency: CustomList
    private lateinit var customListSector: CustomList
    private lateinit var customStockList: CustomList
    private lateinit var customListSP500: CustomList
    private lateinit var customListMOEX: CustomList
    private lateinit var customTaskStack: CustomList

    override fun onCreate(savedInstanceState: Bundle?) {
        customListIndices = CustomList()
        customListResource = CustomList()
        customListCurrency = CustomList()
        customListSector = CustomList()
        customStockList = CustomList()
        customListSP500 = CustomList()
        customListMOEX = CustomList()
        customTaskStack = CustomList()

        try {
            taskMorgoth = intent.getStringExtra(_morgoth)
        }
        catch (e: Exception) {
            taskMorgoth = "Resource"
            outputGraphMessage("Epic Morgoth")
        }

        apiValue = intent.getStringExtra(_api)
        if (apiValue == null)
            apiValue = ""

        super.onCreate(savedInstanceState)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        try {
            Locale.setDefault(Locale("en", "US"))
        }
        catch (e: Exception) { }
        registerTask(0)
        setContentView(R.layout.abc_list_menu_item_layout)
        taskPrepare(false)
    //    taskPrepare2(false)

        val catTask = InitActivity(this, taskMorgoth, apiValue!!)
        catTask.execute()
    }

    fun setTask(view: View?) {
        if (!registerTask(0))
            return
        taskPrepare(false)

        var initTask: Boolean = false
        var taskRequest: Int = -1
        var taskItem: CustomItem? = null
        if (adapterListSP500.hasSelection()) {
            taskRequest = 0
            taskItem = adapterListSP500.getSelection()
        }
        else if(adapterTaskStack.hasSelection()) {
            taskRequest = 1
            taskItem = adapterTaskStack.getSelection()
        }

        if (taskRequest >= 0 && taskItem != null) {
            val index = taskItem.index
            if (index.isNotEmpty()) {
                initTask = true
                outputGraphMessage("SQL/Request")
                val mouseTask = RequestTask(this, index, taskRequest, exchangeMap)
                mouseTask.execute()
            }
        }

        if (!initTask) {
            taskPrepare(true)
            registerTask(1)
        }
    }
    fun goToInputThermo(view: View?) {
        if (!registerTask(0))
            return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Confirm <go to input>?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val intentResult = Intent()
                    intentResult.putExtra(_message, 2)
                    this.setResult(Activity.RESULT_OK, intentResult)
                    this.finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                    registerTask(1)
                }
        val alert = builder.create()
        alert.show()
    }
    fun sortSectorByName(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeSector = !typeSector
        object1008.adapter = null
        customListSector.sort(0, typeSector)
        object1008.adapter = adapterListSector
        adapterListSector.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    fun sortSectorByEarthSol(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeSector = !typeSector
        object1008.adapter = null
        customListSector.sort(1, typeSector, true)
        object1008.adapter = adapterListSector
        adapterListSector.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    fun sortSectorByWeek(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeSector = !typeSector
        object1008.adapter = null
        customListSector.sort(2, typeSector, true)
        object1008.adapter = adapterListSector
        adapterListSector.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    fun sortSectorByMonth(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeSector = !typeSector
        object1008.adapter = null
        customListSector.sort(3, typeSector, true)
        object1008.adapter = adapterListSector
        adapterListSector.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    fun sortMarketByStock(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeStockSort = !typeStockSort
        listPosition1 = -1
        adapterListSP500.clearSelection()
        object1013.adapter = null
        customStockList.sort(0, typeStockSort)
        object1013.adapter = adapterListSP500
        adapterListSP500.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    fun sortMarketByEarthSol(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeStockSort = !typeStockSort
        listPosition1 = -1
        adapterListSP500.clearSelection()
        object1013.adapter = null
        customStockList.sort(1, typeStockSort, true)
        object1013.adapter = adapterListSP500
        adapterListSP500.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    fun sortMarketByWeek(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeStockSort = !typeStockSort
        listPosition1 = -1
        adapterListSP500.clearSelection()
        object1013.adapter = null
        customStockList.sort(2, typeStockSort, true)
        object1013.adapter = adapterListSP500
        adapterListSP500.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    fun sortMarketByMonth(view: View?) {
	    if (!registerTask(0))
            return
		taskPrepare(false)
        typeStockSort = !typeStockSort
        listPosition1 = -1
        adapterListSP500.clearSelection()
        object1013.adapter = null
        customStockList.sort(3, typeStockSort, true)
        object1013.adapter = adapterListSP500
        adapterListSP500.notifyDataSetChanged()
		taskPrepare(true)
		registerTask(1)
    }
    private fun outputGraphMessage(msg: String) {
        sauron_output.text = msg
    }
    private fun taskPrepare(type: Boolean) {
        object0402.isEnabled = type
        object1004.isEnabled = type
        object1005.isEnabled = type
        object1006.isEnabled = type
        object1007.isEnabled = type
        object1015.isEnabled = type
        object1009.isEnabled = type
        object1010.isEnabled = type
        object1011.isEnabled = type
        object1012.isEnabled = type
        object1014.isEnabled = type
        try {
            (object1015.getChildAt(0) as ViewGroup).getChildAt(0).isEnabled = type
            (object1015.getChildAt(0) as ViewGroup).getChildAt(1).isEnabled = type
        }
        catch (e: Exception) { }
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

//----------------------------------------- AsyncTaskInit ----------------------------------------
    private class InitActivity(thisValue: ThermoValue, task_morgoth: String, api: String) : AsyncTask<Void, String, Void>() {
        private val reference: WeakReference<ThermoValue> = WeakReference(thisValue)
        private val MorgothValue: String
        private val taskAPI: String
        private var bigIndices: CustomList? = null
        private var bigResource: CustomList? = null
        private var bigCurrency: CustomList? = null
        private var bigSector: CustomList? = null
        private var bigSP500: CustomList? = null
        private var bigMOEX: CustomList? = null
        private var bigTaskStack: CustomList? = null
        private var taskList: ArrayList<String>? = null
        private val usaValue: Int = 166
        private val moexValue: Int = 13665

        init {
            this.MorgothValue = task_morgoth
            this.taskAPI = api
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val root = reference.get()
            if (root == null || root.isFinishing)
                return
            root.outputGraphMessage("Request 1/6")
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            val businessLogic: IBusinessLogic = RealBusinessLogic()
            bigIndices = businessLogic.GetMarketState(taskAPI, arrayOf("^GSPC", "^DJI", "^IXIC", "^GDAXI", "ERUS", "FXI", "^VIX", "RINF"), arrayOf("SP500", "Dow J", "Nasdaq", "Europe", "Russia", "China", "VIX", "Inflation"))
            publishProgress("Request 2/6")
            bigResource = businessLogic.GetMarketState(taskAPI, arrayOf("BZUSD", "NGUSD", "GCUSD", "SIUSD", "HGUSD", "PLUSD", "PAUSD", "LBUSD"), arrayOf("Brent", "Gas", "Gold", "Silver", "Copper", "Plat", "Pall", "Lumber"))
            publishProgress("Request 3/6")
            bigCurrency = businessLogic.GetMarketState(taskAPI, arrayOf("DX-Y.NYB", "BTCUSD", "ETHUSD", "USDRUB", "EURRUB", "EURUSD", "CNYUSD", "GOVT"), arrayOf("DXY", "BTC", "ETH", "US/RU", "EU/RU", "EU/US", "CN/US", "UST"))
            publishProgress("Request 4/6")
            bigSector = businessLogic.GetSectorIndustry()
            publishProgress("Request 5/6")
            bigSP500 = businessLogic.GetStockEvaluate(usaValue)
            publishProgress("Request 6/6")
            bigMOEX = businessLogic.GetStockEvaluate(moexValue)
            val taskListMOEX = businessLogic.GetStockEvaluate(moexValue+1)
            for (s in 0 until taskListMOEX.size) {
                val taskItem = taskListMOEX[s]
                if (taskItem != null) {
                    if (!bigMOEX!!.contains(0, taskItem.l1))
                        bigMOEX!!.add(taskItem)
                }
            }

            val sqlNameLogo = SQLNameLogoTable(root)
            for (s in 0 until bigIndices!!.size) {
                var logoStr: String = ""
                val taskItem = bigIndices!![s]
                if (taskItem != null) {
                    val taskSQL = sqlNameLogo.getRecord(taskItem.index)
                    if (taskSQL != null) {
                        logoStr = taskSQL.Logo
                        bigIndices!![s]!!.lm = logoStr
                    }
                }
            }

            for (s in 0 until bigResource!!.size) {
                var logoStr: String = ""
                val taskItem = bigResource!![s]
                if (taskItem != null) {
                    val taskSQL = sqlNameLogo.getRecord(taskItem.index)
                    if (taskSQL != null) {
                        logoStr = taskSQL.Logo
                        bigResource!![s]!!.lm = logoStr
                    }
                }
            }

            for (s in 0 until bigCurrency!!.size) {
                var logoStr: String = ""
                val taskItem = bigCurrency!![s]
                if (taskItem != null) {
                    val taskSQL = sqlNameLogo.getRecord(taskItem.index)
                    if (taskSQL != null) {
                        logoStr = taskSQL.Logo
                        bigCurrency!![s]!!.lm = logoStr
                    }
                }
            }

            val sqlTask = SQLTaskTable(root)
            val sqlOutput = sqlTask.allRecords
            bigTaskStack = CustomList()
            taskList = arrayListOf()
            for (s in sqlOutput) {
                val specialItem = sqlNameLogo.getRecord(s.key)
                if (specialItem != null) {
                    if (specialItem.Exchange != "OTHER") {
                        bigTaskStack!!.add(specialItem.Title, null, specialItem.Title, specialItem.Name)
                        taskList!!.add(specialItem.Meta)
                    }
                }
            }

            bigSector!!.sort(0, true)
            bigSP500!!.sort(0, true)
            bigMOEX!!.sort(0, true)
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

            root.customListIndices = bigIndices!!
            root.customListResource = bigResource!!
            root.customListCurrency = bigCurrency!!
            root.customListSector = bigSector!!
            root.customListSP500 = bigSP500!!
            root.customListMOEX = bigMOEX!!
            root.customTaskStack = bigTaskStack!!

            root.taskSP500MOEX = 0
            root.customStockList.clear()
            for (s in 0 until bigSP500!!.size) {
                val taskItem = bigSP500!![s]
                if (taskItem != null)
                    root.customStockList.add(taskItem)
            }

            val gravity1 = Gravity.START
            val gravity2 = Gravity.END
            val gravity3 = Gravity.CENTER_HORIZONTAL
            val lParam1 = CustomListParameter()
            lParam1.setWeight(floatArrayOf(31f, 10f, 10f, 11f))
            lParam1.setGravity(intArrayOf(gravity1, gravity2))
            lParam1.setStyle(intArrayOf(Typeface.BOLD))
            lParam1.setFloat(booleanArrayOf(false, true, true))
            root.adapterListIndices = CustomListAdapter(root, root.customListIndices, lParam1)
            root.adapterListResource = CustomListAdapter(root, root.customListResource, lParam1)
            root.adapterListCurrency = CustomListAdapter(root, root.customListCurrency, lParam1)

            val lParam2 = CustomListParameter()
            lParam2.setWeight(floatArrayOf(39f, 6f, 11f, 11f, 11f))
            lParam2.setFontSize(floatArrayOf(9f, 9f, 9f, 9f))
            lParam2.setGravity(intArrayOf(gravity1, gravity1, gravity3, gravity1))
        //    lParam2.setStyle(intArrayOf(Typeface.BOLD))
            lParam2.setFloat(booleanArrayOf(false, true, true, true))
            root.adapterListSector = CustomListAdapter(root, root.customListSector, lParam2)

            val lParam3 = CustomListParameter()
            lParam3.setWeight(floatArrayOf(39f, 6f, 11f, 11f, 11f))
        //    lParam3.setWeight(floatArrayOf(37f, 7f, 10f, 10f, 10f))
        //    lParam3.setWeight(floatArrayOf(41f, 11f, 10f, 10f, 10f))
            lParam3.setFontSize(floatArrayOf(11f, 9f, 9f, 9f))
            lParam3.setGravity(intArrayOf(gravity1, gravity1, gravity3, gravity1))
            lParam3.setStyle(intArrayOf(Typeface.BOLD))
            lParam3.setFloat(booleanArrayOf(false, true, true, true))
            root.adapterListSP500 = CustomListAdapter(root, root.customStockList, lParam3)
            root.adapterListSP500.setSelectionArray(taskList!!)

            val lParam4 = CustomListParameter()
            lParam4.setWeight(floatArrayOf(14f, 11f, 3f))
            lParam4.setStyle(intArrayOf(Typeface.BOLD, Typeface.BOLD))
            root.adapterTaskStack = CustomListAdapter(root, root.customTaskStack, lParam4)

            root.object1001.adapter = root.adapterListIndices
            root.object1002.adapter = root.adapterListResource
            root.object1003.adapter = root.adapterListCurrency
            root.object1008.adapter = root.adapterListSector
            root.object1013.adapter = root.adapterListSP500
            root.object1016.adapter = root.adapterTaskStack

            root.object1015.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        if (!root.registerTask(0))
                            return

                        val index: Int = tab.position
                        if (index in 0..1 && index != INVALID_POSITION) {
                            root.object1013.adapter = null
                            root.customStockList.clear()
                            root.listPosition1 = -1
                            root.adapterListSP500.clearSelection()

                            root.taskSP500MOEX = index
                            when (index) {
                                0 -> {
                                    root.customStockList.addAll(root.customListSP500.all)
                                    root.customStockList.sort(0, true, false)
                                    root.typeStockSort = false
                                }
                                1 -> {
                                    root.customStockList.addAll(root.customListMOEX.all)
                                    root.customStockList.sort(0, true, false)
                                    root.typeStockSort = true
                                }
                            }
                            root.object1013.adapter = root.adapterListSP500
                            root.adapterListSP500.notifyDataSetChanged()
                        }
                        root.registerTask(1)
                    }
                }
                override fun onTabReselected(tab: TabLayout.Tab?) { }
                override fun onTabUnselected(tab: TabLayout.Tab?) { }
            })
            root.object1013.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position == root.listPosition1 && position != INVALID_POSITION) {
                        root.listPosition1 = -1
                        root.adapterListSP500.clearSelection()
                    }
                    else {
                        root.listPosition1 = position
                        root.adapterListSP500.setSelection(position, view)
                    }
                }
            }
            root.object1016.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position == root.listPosition2 && position != INVALID_POSITION) {
                        root.listPosition2 = -1
                        root.adapterTaskStack.clearSelection()
                        root.object1008.adapter = null
                        root.adapterListSector.clearSelectionArray()
                        root.object1008.adapter = root.adapterListSector
                    }
                    else {
                        root.listPosition2 = position
                        root.adapterTaskStack.setSelection(position, view)

                        val sqlNameLogo = SQLNameLogoTable(root)
                        val taskItem = root.adapterTaskStack.getSelection()
                        if (taskItem != null) {
                            val stockTask = sqlNameLogo.getRecord(taskItem.index)
                            if (stockTask != null) {
                                if (stockTask.Sector.isNotEmpty()) {
                                    root.object1008.adapter = null
                                    root.adapterListSector.setSelectionArray(stockTask.Sector)
                                    root.object1008.adapter = root.adapterListSector
                                }
                            }
                        }
                    }
                }
            }

            root.outputGraphMessage("Request/Complete")
            root.taskPrepare(true)
            root.registerTask(1)
        }
    }
//---------------------------------------- AsyncTaskInsert ---------------------------------------
    private class RequestTask(thisValue: ThermoValue, stock_name: String, task_type: Int, exchange_map: HashMap<String, Int>) : AsyncTask<Void, Void, Void>() {
        private val reference: WeakReference<ThermoValue> = WeakReference(thisValue)
        private val taskName: String
        private val taskType: Int
        private val taskExchangeMap: HashMap<String, Int>
        private var outputResult: NameLogoRecord? = null
        private var taskResult: Int = 0

        init {
            this.taskName = stock_name
            this.taskType = task_type
            this.taskExchangeMap = exchange_map
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val root = reference.get()
            if (root == null || root.isFinishing)
                return null

            when (taskType) {
                0 -> {
                    val sqlNameLogo = SQLNameLogoTable(root)
                    val stockTask = sqlNameLogo.getRecord(taskName, true)
                    if (stockTask != null) {
                        if (stockTask.Exchange in taskExchangeMap) {
                            val sqlTask = SQLTaskTable(root)
                            val sqlResult = sqlTask.addRecord(stockTask.Title, stockTask.Name, taskExchangeMap[stockTask.Exchange]!!)
                            if (sqlResult) {
                                taskResult = 4
                                outputResult = stockTask
                            } else {
                                if (sqlTask.checkRecord(stockTask.Title))
                                    taskResult = 3
                                else
                                    taskResult = 2
                            }
                        }
                        else {
                            taskResult = 1
                        }
                    }
                }
                1 -> {
                    val sqlNameLogo = SQLNameLogoTable(root)
                    val stockTask = sqlNameLogo.getRecord(taskName)
                    if (stockTask != null) {
                        val sqlTask = SQLTaskTable(root)
                        val sqlResult = sqlTask.deleteRecord(stockTask.Title)
                        if (sqlResult) {
                            taskResult = 2
                            outputResult = stockTask
                        } else {
                            taskResult = 1
                        }
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

            when (taskType) {
                0 -> {
                    when (taskResult) {
                        0 -> {
                            root.outputGraphMessage("SQL1/Verify")
                        }
                        1 -> {
                            root.outputGraphMessage("SQL1/Exchange")
                        }
                        2 -> {
                            root.outputGraphMessage("SQL2/Verify")
                        }
                        3 -> {
                            root.outputGraphMessage("SQL/Exist")
                            root.adapterListSP500.clearSelection()
                        }
                        4 -> {
                            root.outputGraphMessage("Insert/${outputResult!!.Title}")
                            root.object1016.adapter = null
                            root.customTaskStack.add(outputResult!!.Title, null, outputResult!!.Title, outputResult!!.Name)
                            root.object1016.adapter = root.adapterTaskStack
                            root.adapterTaskStack.notifyDataSetChanged()
                            root.adapterListSP500.setSelectionArray(taskName)
                            root.adapterListSP500.clearSelection()
                        }
                    }
                }
                1 -> {
                    if (taskResult < 2) {
                        when (taskResult) {
                            0 -> {
                                root.outputGraphMessage("SQL1/Verify")
                            }
                            1 -> {
                                root.outputGraphMessage("SQL2/Verify")
                            }
                        }
                    }
                    else if (taskResult == 2) {
                        root.object1016.adapter = null
                        root.customTaskStack.clear(-1, outputResult!!.Title)
                        root.listPosition2 = -1
                        root.adapterTaskStack.clearSelection()
                        root.object1016.adapter = root.adapterTaskStack
                        root.adapterTaskStack.notifyDataSetChanged()

                        root.listPosition1 = -1
                        root.adapterListSP500.clearSelectionArray(outputResult!!.Meta)
                        root.object1008.adapter = null
                        root.adapterListSector.clearSelectionArray()
                        root.object1008.adapter = root.adapterListSector
                        root.outputGraphMessage("Clear/${outputResult!!.Title}")
                    }
                }
            }

            root.taskPrepare(true)
            root.registerTask(1)
        }
    }
//------------------------------------------------------------------------------------------------

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
            val notificationIntent = Intent(this, ThermoValue::class.java)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            pendingIntent.send()
        }
        catch (e: CanceledException) {
        //    e.printStackTrace()
        }
    }
}