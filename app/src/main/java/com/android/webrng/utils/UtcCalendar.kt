package com.android.webrng.utils

import android.annotation.SuppressLint
import com.android.webrng.constants.outputGreenwichOffset
import java.text.SimpleDateFormat
import java.util.*


class UtcCalendar {
    private var cal: Calendar
    val timeInMillis: Long
        get() {
            return cal.timeInMillis
        }
    var time: UtcDate
        get() {
            return UtcDate(false, cal.timeInMillis)
        }
        set(loc: UtcDate) {
            cal.time = loc.loc
        }

    init {
        cal = Calendar.getInstance()
        cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.HOUR_OF_DAY, outputGreenwichOffset)
    }

    fun get(type: Int) : Int {
        val output = Calendar.getInstance()
        output.time = cal.time
        output.add(Calendar.HOUR_OF_DAY, -outputGreenwichOffset)
        var result: Int = 1
		if (type in 0 until Calendar.FIELD_COUNT) {
			try {
				result = output.get(type)
			}
			catch (e: Exception) { }
		}
        return result
    /*    var result = output.get(type)
        if (type == Calendar.DAY_OF_WEEK) {
            Log.i("WEEK", "${output.time}")
            result = (result + weekUniversalOffset - 1) % 7 + 1
        }
        return result   */
    }
    fun add(type: Int, count: Int) {
        if (type in 0 until Calendar.FIELD_COUNT) {
            try {
                cal.add(type, count)
            }
            catch (e: Exception) { }
        }
    }
    fun set(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int) {
        cal.set(year, month, day, hour, minute, second)
        cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.HOUR_OF_DAY, outputGreenwichOffset)
    }
    fun set(type: Int, value: Int) {
        cal.set(type, value)
        cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.HOUR_OF_DAY, outputGreenwichOffset)
    }
    fun max(type: Int): Int {
        return cal.getActualMaximum(type)
    }

    companion object {
        val YEAR: Int
            get() {
                return Calendar.YEAR
            }
        val MONTH: Int
            get() {
                return Calendar.MONTH
            }
        val DAY_OF_YEAR: Int
            get() {
                return Calendar.DAY_OF_YEAR
            }
        val DAY_OF_MONTH: Int
            get() {
                return Calendar.DAY_OF_MONTH
            }
        val DAY_OF_WEEK: Int
            get() {
                return Calendar.DAY_OF_WEEK
            }
        val HOUR_OF_DAY: Int
            get() {
                return Calendar.HOUR_OF_DAY
            }
        val MINUTE: Int
            get() {
                return Calendar.MINUTE
            }
        val SECOND: Int
            get() {
                return Calendar.SECOND
            }
        val WeekRange = arrayOf(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY)

        fun getInstance() : UtcCalendar {
            return UtcCalendar()
        }
        fun checkInRange(point: Int, value: Long): Boolean {
            val cal1 = UtcCalendar.getInstance()
            val cal2 = UtcCalendar.getInstance()
            cal1.set(point, 0, 1, 0, 0, 0)
            cal2.set(point+1, 0, 1, 0, 0, 0)
            val cmp1 = cal1.timeInMillis / 1000L
            val cmp2 = cal2.timeInMillis / 1000L
            return value in cmp1 until cmp2
        }
        fun calculateWeekDays(point: Int): Int {
            val cal = UtcCalendar.getInstance()
            cal.set(UtcCalendar.YEAR, point)
            cal.set(UtcCalendar.DAY_OF_YEAR, 1)

            var totalCount = 0
            while(cal.get(UtcCalendar.YEAR) == point && totalCount <= 366) {
                val weekDay = cal.get(UtcCalendar.DAY_OF_WEEK)
                if (weekDay in WeekRange)
                    totalCount++
                cal.add(UtcCalendar.DAY_OF_YEAR, 1)
            }
            return totalCount
        }
    }
}

class UtcDate {
    var loc: Date
    val time: Long
        get() {
            return loc.time
        }

    constructor() {
        val currentMoment = Date().time
        loc = Date(currentMoment - currentMoment % 1000L + outputGreenwichOffset * 3600000L)
    }
    constructor(milli: Boolean) {
        val currentMoment = Date().time
        if (milli) {
            loc = Date(currentMoment + outputGreenwichOffset * 3600000L)
        }
        else {
            loc = Date(currentMoment)
        }
    }
    constructor(milli: Boolean, count: Long) {
        if (milli) {
            loc = Date(count + outputGreenwichOffset * 3600000L)
        }
        else {
            loc = Date(count)
        }
        //    Log.i("CREATE", "${count}/${loc.time}")
    }

    override fun toString(): String {
        val output = Date(loc.time - outputGreenwichOffset * 3600000L)
        return output.toString()
    }

    companion object {
        fun stringAdd(source: String, format: String, thr: Boolean, utc: Int, offset: Int = 0): String {
            var result: String = ""
            val srcDate = format.utcParse(source, thr)
            val resDate = UtcDate(true, srcDate.time - offset * 3600000L)
            result = format.utcFormat(resDate, utc)
            return result
        }
    }
}

/*fun SimpleDateFormat.utcFormat(loc: UtcDate) : String {
    return this.format(loc.loc)
}   */
@SuppressLint("SimpleDateFormat")
fun String.utcParse(row: String, thr: Boolean = false) : UtcDate {
    var result = UtcDate()
    try {
        result = UtcDate(true, SimpleDateFormat(this).parse(row).time)
    }
    catch (e: Exception) {
        if (thr)
            throw Exception("UtcParse")
    }
    return result
}
@SuppressLint("SimpleDateFormat")
fun String.utcFormat(loc: UtcDate, utc: Int) : String {
    val utcOffset = if (utc == Int.MAX_VALUE) 0L else (utc - outputGreenwichOffset) * 3600000L
    val taskDate = UtcDate(false, loc.loc.time)
    taskDate.loc.time -= outputGreenwichOffset * 3600000L + utcOffset
    var result: String = ""
    try {
        result = SimpleDateFormat(this).format(taskDate.loc)
    }
    catch (e: Exception) { }
    return result
}