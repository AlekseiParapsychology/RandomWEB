package com.android.webrng.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.webrng.R
import com.android.webrng.constants.toFinite
import java.io.ByteArrayInputStream
import java.util.*


class CustomItem(item1: String, item2: String?, item3: String = "", item4: String = "", item5: String = "", item6: String = "") {
    var index: String = ""
    var lm: String? = null
    var l1: String = ""
    var l2: String = ""
    var l3: String = ""
    var l4: String = ""

    init {
        this.index = item1
        this.lm = item2
        this.l1 = item3
        this.l2 = item4
        this.l3 = item5
        this.l4 = item6
    }
}
class CustomList {
    private var items: ArrayList<CustomItem>
    val size: Int
        get() {
            return items.size
        }
    val all: ArrayList<CustomItem>
        get() {
            return items
        }

    init {
        items = ArrayList<CustomItem>()
    }
    fun add(item: CustomItem) {
        try {
            items.add(CustomItem(item.index, item.lm, item.l1, item.l2, item.l3, item.l4))
        }
        catch (e: Exception) { }
    }
    fun add(item1: String, item2: String?, item3: String = "", item4: String = "", item5: String = "", item6: String = "") {
        try {
            items.add(CustomItem(item1, item2, item3, item4, item5, item6))
        }
        catch (e: Exception) { }
    }
    fun addAll(item: ArrayList<CustomItem>) {
        try {
            items.addAll(item)
        }
        catch (e: Exception) { }
    }
    fun sort(value: Int, type: Boolean, format: Boolean = false) {
        try {
            if (type) {
                if (format) {
                    when (value) {
                        0 -> items.sortBy { it.l1.replace("+", "").replace("%", "").toFloat().toFinite() }
                        1 -> items.sortBy { it.l2.replace("+", "").replace("%", "").toFloat().toFinite() }
                        2 -> items.sortBy { it.l3.replace("+", "").replace("%", "").toFloat().toFinite() }
                        3 -> items.sortBy { it.l4.replace("+", "").replace("%", "").toFloat().toFinite() }
                    }
                } else {
                    when (value) {
                        0 -> items.sortBy { it.l1.toUpperCase(Locale.ROOT) }
                        1 -> items.sortBy { it.l2.toUpperCase(Locale.ROOT) }
                        2 -> items.sortBy { it.l3.toUpperCase(Locale.ROOT) }
                        3 -> items.sortBy { it.l4.toUpperCase(Locale.ROOT) }
                    }
                }
            } else {
                if (format) {
                    when (value) {
                        0 -> items.sortByDescending { it.l1.replace("+", "").replace("%", "").toFloat().toFinite() }
                        1 -> items.sortByDescending { it.l2.replace("+", "").replace("%", "").toFloat().toFinite() }
                        2 -> items.sortByDescending { it.l3.replace("+", "").replace("%", "").toFloat().toFinite() }
                        3 -> items.sortByDescending { it.l4.replace("+", "").replace("%", "").toFloat().toFinite() }
                    }
                } else {
                    when (value) {
                        0 -> items.sortByDescending { it.l1.toUpperCase(Locale.ROOT) }
                        1 -> items.sortByDescending { it.l2.toUpperCase(Locale.ROOT) }
                        2 -> items.sortByDescending { it.l3.toUpperCase(Locale.ROOT) }
                        3 -> items.sortByDescending { it.l4.toUpperCase(Locale.ROOT) }
                    }
                }
            }
        }
        catch (e: Exception) { }
    }
    fun contains(index: Int, value: String): Boolean {
        when (index) {
            0 -> return items.any { v -> v.l1 == value }
            1 -> return items.any { v -> v.l2 == value }
            2 -> return items.any { v -> v.l3 == value }
            3 -> return items.any { v -> v.l4 == value }
        }
        return false
    }
    fun clear(index: Int, value: String) {
        try {
            when (index) {
                -1 -> items.removeIf { v -> v.index == value }
                0 -> items.removeIf { v -> v.l1 == value }
                1 -> items.removeIf { v -> v.l2 == value }
                2 -> items.removeIf { v -> v.l3 == value }
                3 -> items.removeIf { v -> v.l4 == value }
            }
        }
        catch (e: Exception) { }
    }
    fun clear() {
        try {
            items.clear()
        }
        catch (e: Exception) { }
    }
    operator fun get(index: Int): CustomItem? {
        if (index in 0 until items.size)
            return items[index]
        else
            return null
    }
    operator fun set(index: Int, item: CustomItem) {
        if (index in 0 until items.size)
            items[index] = CustomItem(item.index, item.lm, item.l1, item.l2, item.l3, item.l4)
    }
}
class CustomListParameter {
    var weightArray: FloatArray? = null
    var fontSizeArray: FloatArray? = null
    var gravityArray: IntArray? = null
    var styleArray: IntArray? = null
    var floatType: BooleanArray? = null

    fun setWeight(list: FloatArray) {
        weightArray = list
    }
    fun getWeight(index: Int): Float {
        if (weightArray != null) {
            if (index < weightArray!!.size)
                return weightArray!![index]
        }
        return 0f
    }

    fun setFontSize(list: FloatArray) {
        fontSizeArray = list
    }
    fun getFontSize(index: Int): Float {
        if (fontSizeArray != null) {
            if (index < fontSizeArray!!.size)
                return fontSizeArray!![index]
        }
        return 9f
    }

    fun setGravity(list: IntArray) {
        gravityArray = list
    }
    fun getGravity(index: Int): Int {
        if (gravityArray != null) {
            if (index < gravityArray!!.size)
                return gravityArray!![index]
        }
        return Gravity.CENTER
    }

    fun setStyle(list: IntArray) {
        styleArray = list
    }
    fun getStyle(index: Int): Int {
        if (styleArray != null) {
            if (index < styleArray!!.size)
                return styleArray!![index]
        }
        return Typeface.NORMAL
    }

    fun setFloat(list: BooleanArray) {
        floatType = list
    }
    fun getFloat(index: Int): Boolean {
        if (floatType != null) {
            if (index < floatType!!.size)
                return floatType!![index]
        }
        return false
    }
}
internal class CustomListAdapter(var mContext: Context, var mList: CustomList, var lParam: CustomListParameter = CustomListParameter()) : BaseAdapter() {
    private val greenColor = Color.parseColor("#17EA6F")
    private val redColor = Color.parseColor("#EA4B17")
    private var mSelection: Int = -1
    private var mSelectionArray: ArrayList<String> = arrayListOf()
    private var lastSelectedRow: View? = null
    private var lastSelectedRowPosition: Int = -1

    fun getPosition(): Int {
        return mSelection
    }
    fun getSelection(): CustomItem? {
        return mList[mSelection]
    }
    fun setSelection(position: Int, selectedItemView: View?) {
        mSelection = position
        val taskItem = mList[lastSelectedRowPosition]
        if (lastSelectedRow != null && taskItem != null) {
            if (taskItem.index !in mSelectionArray) {
                lastSelectedRowPosition = -1
                lastSelectedRow!!.setBackgroundResource(R.drawable.item_bg)
                lastSelectedRow = null
            }
        }

        if (selectedItemView != null && selectedItemView != lastSelectedRow) {
            selectedItemView.setBackgroundResource(R.drawable.item_sel)
        }
        this.lastSelectedRow = selectedItemView
        lastSelectedRowPosition = position
    }
    fun setSelectionArray(input: String) {
        mSelectionArray.add(input)
    //    val taskItem = mList[mSelection]
    //    if (taskItem != null)
    //        mSelectionArray.add(taskItem.l5)
    }
    fun setSelectionArray(input: ArrayList<String>) {
        mSelectionArray.clear()
        try {
            mSelectionArray.addAll(input)
        }
        catch (e: Exception) { }
    }
    fun clearSelectionArray(input: String): Boolean {
        try {
            mSelectionArray.remove(input)
        }
        catch (e: Exception) {
            return false
        }

        mSelection = -1
        val taskItem = mList[lastSelectedRowPosition]
        if (lastSelectedRow != null && taskItem != null) {
            if (taskItem.index !in mSelectionArray) {
                lastSelectedRowPosition = -1
                lastSelectedRow!!.setBackgroundResource(R.drawable.item_bg)
                lastSelectedRow = null
            }
        }
        return true
    }
    fun clearSelectionArray() {
        mSelectionArray.clear()
    }
    fun clearSelection() {
        mSelection = -1
        val taskItem = mList[lastSelectedRowPosition]
        if (lastSelectedRow != null && taskItem != null) {
            if (taskItem.index !in mSelectionArray) {
                lastSelectedRowPosition = -1
                lastSelectedRow!!.setBackgroundResource(R.drawable.item_bg)
                lastSelectedRow = null
            }
        }
    }
    fun hasSelection(): Boolean {
        return mSelection != -1
    /*    val taskItem = mList[mSelection]
        if (taskItem != null) {
            return taskItem.index in mSelectionArray
        }
        return false    */
    }
    override fun getCount(): Int {
        return mList.size
    }
    override fun getItem(position: Int): CustomItem? {
        return mList[position]
    }
    override fun getItemId(position: Int): Long {
        return 0L
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        val taskItem = mList[position]
        if (view == null) {
            view = View.inflate(mContext, R.layout.abc_list_menu_item_icon, null)
        }
        if (view != null) {
            var taskResult: Boolean = false
            if (position == mSelection) {
                lastSelectedRow = view
                view.setBackgroundResource(R.drawable.item_sel)
                taskResult = true
            }
            else if (taskItem != null) {
                if (taskItem.index in mSelectionArray) {
                    view.setBackgroundResource(R.drawable.item_sel)
                    taskResult = true
                }
            }

            if (!taskResult)
                view.setBackgroundResource(R.drawable.item_bg)
        }

        if (position < mList.size && view != null) {
            val image = view.findViewById(R.id.icon_1) as ImageView?
            val value1 = view.findViewById(R.id.value_1) as TextView?
            val value2 = view.findViewById(R.id.value_2) as TextView?
            val value3 = view.findViewById(R.id.value_3) as TextView?
            val value4 = view.findViewById(R.id.value_4) as TextView?
            val value5 = view.findViewById(R.id.value_5) as TextView?

            var itemCount: Int = 5
            var taskValue: String? = if (taskItem != null) taskItem.lm else null
            if (taskValue != null) {
                var imageTask: Boolean = false
                if (taskValue.isNotEmpty()) {
                    var taskLogoImage: ByteArrayInputStream? = null
                    try {
                    //    Log.i("LOGO", "${taskValue}")
                        val byteStr: ByteArray = Base64.decode(taskValue, Base64.URL_SAFE)
                        taskLogoImage = ByteArrayInputStream(byteStr)
                    } catch (e: Exception) {
                    }

                    if (taskLogoImage != null) {
                        try {
                            val logoImage: Bitmap? = BitmapFactory.decodeStream(taskLogoImage)
                            if (logoImage != null) {
                                image?.setImageBitmap(logoImage)
                                imageTask = true
                            }
                        } catch (e: Exception) {
                        }
                    }
                }
                if (!imageTask) {
                    //image?.setImageResource(R.drawable.shelob)
                }
            }
            else {
                image?.visibility = View.GONE
            }

            var taskStr: String = if (taskItem != null) taskItem.l1 else ""
            if (taskStr.isNotEmpty()) {
                value1?.text = taskStr
                if (lParam.getFloat(0)) {
                    if (taskStr.contains('+'))
                        value1?.setTextColor(greenColor)
                    else if (taskStr.contains('-'))
                        value1?.setTextColor(redColor)
                }
                value1?.textSize = lParam.getFontSize(0)
                value1?.gravity = lParam.getGravity(0)
                value1?.setTypeface(null, lParam.getStyle(0))
            }
            else {
                value1?.visibility = View.GONE
                itemCount--
            }

            taskStr = if (taskItem != null) taskItem.l2 else ""
            if (taskStr.isNotEmpty()) {
                value2?.text = taskStr
                if (lParam.getFloat(1)) {
                    if (taskStr.contains('+'))
                        value2?.setTextColor(greenColor)
                    else if (taskStr.contains('-'))
                        value2?.setTextColor(redColor)
                }
                value2?.textSize = lParam.getFontSize(1)
                value2?.gravity = lParam.getGravity(1)
                value2?.setTypeface(null, lParam.getStyle(1))
            }
            else {
                value2?.visibility = View.GONE
                itemCount--
            }

            taskStr = if (taskItem != null) taskItem.l3 else ""
            if (taskStr.isNotEmpty()) {
                value3?.text = taskStr
                if (lParam.getFloat(2)) {
                    if (taskStr.contains('+'))
                        value3?.setTextColor(greenColor)
                    else if (taskStr.contains('-'))
                        value3?.setTextColor(redColor)
                }
                value3?.textSize = lParam.getFontSize(2)
                value3?.gravity = lParam.getGravity(2)
                value3?.setTypeface(null, lParam.getStyle(2))
            }
            else {
                value3?.visibility = View.GONE
                itemCount--
            }

            taskStr = if (taskItem != null) taskItem.l4 else ""
            if (taskStr.isNotEmpty()) {
                value4?.text = taskStr
                if (lParam.getFloat(3)) {
                    if (taskStr.contains('+'))
                        value4?.setTextColor(greenColor)
                    else if (taskStr.contains('-'))
                        value4?.setTextColor(redColor)
                }
                value4?.textSize = lParam.getFontSize(3)
                value4?.gravity = lParam.getGravity(3)
                value4?.setTypeface(null, lParam.getStyle(3))
            }
            else {
                value4?.visibility = View.GONE
                itemCount--
            }

            taskStr = if (taskItem != null) taskItem.index else ""
            if (taskStr.isNotEmpty()) {
                value5?.text = taskStr
                if (lParam.getFloat(4)) {
                    if (taskStr.contains('+'))
                        value5?.setTextColor(greenColor)
                    else if (taskStr.contains('-'))
                        value5?.setTextColor(redColor)
                }
                value5?.textSize = lParam.getFontSize(4)
                value5?.gravity = lParam.getGravity(4)
                value5?.setTypeface(null, lParam.getStyle(4))
            }
            else {
                value5?.visibility = View.GONE
                itemCount--
            }

            if (lParam.weightArray != null) {
                val linearLayoutItem = view.findViewById(R.id.linear_layout) as LinearLayout
                linearLayoutItem.weightSum = lParam.getWeight(0)


                val param1 = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, lParam.getWeight(1))
                if (param1.weight > 0f)
                    value1?.layoutParams = param1
                else
                    value1?.visibility = View.GONE

                val param2 = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, lParam.getWeight(2))
                if (param2.weight > 0f)
                    value2?.layoutParams = param2
                else
                    value2?.visibility = View.GONE

                val param3 = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, lParam.getWeight(3))
                if (param3.weight > 0f)
                    value3?.layoutParams = param3
                else
                    value3?.visibility = View.GONE

                val param4 = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, lParam.getWeight(4))
                if (param4.weight > 0f)
                    value4?.layoutParams = param4
                else
                    value4?.visibility = View.GONE

                val param5 = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, lParam.getWeight(5))
                if (param5.weight > 0f)
                    value5?.layoutParams = param5
                else
                    value5?.visibility = View.GONE
                itemCount = 5
            }

            if (itemCount in 0..4) {
                val linearLayoutItem = view.findViewById(R.id.linear_layout) as LinearLayout
                linearLayoutItem.weightSum = itemCount.toFloat()
            }
        }

        if (view != null)
            return view
        else
            return View(null)
    }
}