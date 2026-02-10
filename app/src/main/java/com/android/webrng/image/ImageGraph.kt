package com.android.webrng.image

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.android.webrng.constants.*
import com.android.webrng.constants.toNonZero
import com.android.webrng.utils.UtcCalendar
import com.android.webrng.utils.UtcDate
import com.android.webrng.utils.utcFormat
import kotlin.math.roundToInt


class ImageGraph(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var objectType: Int = -1
    private var usOffset: Boolean = false
    private val dataSet = mutableListOf<DataPoint>()
    private var xMin = 0
    private var xMax = 0
    private var yMin = 0f
    private var yMax = 0f
    private val offsetX1 = 30f
    private val offsetY1 = 15f
    private val offsetX2 = 30f
    private val offsetY2 = 10f
    private val lineCount1 = 8
    private val lineCount2 = 20
    private var cal1: UtcCalendar? = null
	private var cal2: UtcCalendar? = null
    private var cal3: UtcCalendar? = null
    private var currentMoment: Long = 0L

    private val dataPointLinePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 2f
        isAntiAlias = true
    }

    private val dataValueLinePaint1 = Paint().apply {
        color = Color.RED
        strokeWidth = 7f
        isAntiAlias = true
        textSize = 10f
    }

    private val dataValueLinePaint2 = Paint().apply {
        color = Color.MAGENTA
        strokeWidth = 7f
        isAntiAlias = true
        textSize = 10f
    }

    private val dataValueLinePaint3 = Paint().apply {
        color = Color.RED
        strokeWidth = 7f
        isAntiAlias = true
        textSize = 9f
    }

    private val dataValueLinePaint4 = Paint().apply {
        color = Color.rgb(45, 205, 200)
        strokeWidth = 1f
        isAntiAlias = true
        textSize = 10f
        style = Paint.Style.STROKE
    }

    private val dataValueLinePaint5 = Paint().apply {
        color = Color.rgb(45, 214, 110)
        strokeWidth = 1f
        isAntiAlias = true
        textSize = 10f
        style = Paint.Style.STROKE
    }

    private val axisLinePaint1 = Paint().apply {
        color = Color.DKGRAY
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }

    private val axisLinePaint2 = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }

    private val axisLinePaint3 = Paint().apply {
        color = Color.GREEN
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val intervalY = (yMax - yMin) / lineCount2
        var yValue = yMin
        var yColor = 0
        var formatLength: Int = 0
        when {
            yMax < 1f -> formatLength = 4
            yMax >= 1f && yMax < 25f -> formatLength = 3
            yMax >= 25f && yMax < 100f -> formatLength = 2
            yMax >= 100f && yMax < 1000f -> formatLength = 1
        }
        while (yValue < yMax) {
            canvas.drawLine(offsetX1, yValue.toRealY(), width.toFloat()-offsetX2, yValue.toRealY(), if (yColor % 4 == 0) axisLinePaint1 else axisLinePaint2)
            canvas.drawText("%.${formatLength}f".format(yValue), 0f, yValue.toRealY()+3f, if (yColor % 4 == 0) dataValueLinePaint1 else dataValueLinePaint2)
            canvas.drawText("%.${formatLength}f".format(yValue), 2f+width.toFloat()-offsetX2, yValue.toRealY()+3f, if (yColor % 4 == 0) dataValueLinePaint1 else dataValueLinePaint2)
            yValue += intervalY
            yColor += 1
        }
        canvas.drawLine(offsetX1, yMax.toRealY(), width.toFloat()-offsetX2, yMax.toRealY(), axisLinePaint1)
        canvas.drawText("%.${formatLength}f".format(yMax), 0f, yMax.toRealY()+3f, dataValueLinePaint1)
        canvas.drawText("%.${formatLength}f".format(yMax), 2f+width.toFloat()-offsetX2, yMax.toRealY()+3f, dataValueLinePaint1)

    /*    var xValue = xMin
        while (xValue < xMax) {
            canvas.drawLine(xValue.toRealX(), offsetY2, xValue.toRealX(), height.toFloat() - offsetY1, axisLinePaint1)
            canvas.drawText(SimpleDateFormat("HH:mm").format(Date(dataSet[xValue].zTime)), xValue.toRealX()-11f, height.toFloat()-4f, dataValueLinePaint3)
            xValue += 7
        }   */

        if (dataSet.isEmpty() || xMax !in 0 until dataSet.size)
            return
        var xInterval: Float = dataSet.size.toFloat() / lineCount1
        var xCurrentPixel: Float = xMin.toFloat()
        var xValue = xMin
        while (xValue < xMax) {
        //    Log.i("GRAPH", "${xValue}/${xValue.toRealX()}")
        //    Log.i("TIME", "${dataSet[xValue].zTime}/${UtcDate(false, dataSet[xValue].zTime)}")
            canvas.drawLine(xValue.toRealX(), offsetY2, xValue.toRealX(), height.toFloat() - offsetY1, axisLinePaint1)
            canvas.drawText("HH:mm".utcFormat(UtcDate(false, dataSet[xValue].zTime), currentGreenwichOffset), xValue.toRealX()-11f, height.toFloat()-4f, dataValueLinePaint3)
            xCurrentPixel += xInterval
            xValue = xCurrentPixel.roundToInt()
        }
        canvas.drawLine(xMax.toRealX(), offsetY2, xMax.toRealX(), height.toFloat() - offsetY1, axisLinePaint1)
        canvas.drawText("HH:mm".utcFormat(UtcDate(false, dataSet[xMax].zTime), currentGreenwichOffset), xMax.toRealX()-24f, height.toFloat()-15f, dataValueLinePaint3)
    //    Log.i("DATE", "${dataSet[xMax].zTime}/${UtcDate(dataSet[xMax].zTime).time}")

        if (cal1 == null)
            cal1 = UtcCalendar.getInstance()
        if (cal2 == null)
            cal2 = UtcCalendar.getInstance()
        if (cal3 == null)
            cal3 = UtcCalendar.getInstance()
        cal1!!.set(cal1!!.get(UtcCalendar.YEAR), cal1!!.get(UtcCalendar.MONTH), cal1!!.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
        cal2!!.set(cal2!!.get(UtcCalendar.YEAR), cal2!!.get(UtcCalendar.MONTH), cal2!!.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
        cal3!!.set(cal3!!.get(UtcCalendar.YEAR), cal3!!.get(UtcCalendar.MONTH), cal3!!.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
        var changeTime1: Long = 0
        var changeTime2: Long = 0
        var changeTime3: Long = Long.MAX_VALUE

        val taskWeek = cal1!!.get(UtcCalendar.DAY_OF_WEEK)
        when (objectType) {
            _USA -> {
                cal1!!.add(UtcCalendar.HOUR_OF_DAY, 17)
                cal1!!.add(UtcCalendar.MINUTE, 30)
                if (!usOffset) {
                    cal1!!.add(UtcCalendar.HOUR_OF_DAY, -1)
                    cal2!!.add(UtcCalendar.HOUR_OF_DAY, -1)
                }

                if (taskWeek in 3..6) {
                    if (currentMoment <= cal1!!.timeInMillis) {
                        cal1!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                    }
                    if (currentMoment <= cal2!!.timeInMillis) {
                        cal2!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                    }
                }

                when (taskWeek) {
                    2 -> {
                        if (currentMoment <= cal1!!.timeInMillis)
                            cal1!!.add(UtcCalendar.HOUR_OF_DAY, -72)
                        cal2!!.add(UtcCalendar.HOUR_OF_DAY, -48)
                    }
                    1 -> {
                        cal1!!.add(UtcCalendar.HOUR_OF_DAY, -48)
                        cal2!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                    }
                    7 -> {
                        cal1!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                    }
                }

                val checkPoint1 = (cal1!!.timeInMillis - cal1!!.timeInMillis % 86400000L) / 1000L
                //    Log.i("checkPoint1", "$checkPoint1")
                if (checkPoint1 in usHolidays)
                    cal1!!.add(UtcCalendar.HOUR_OF_DAY, -24 - (cal1!!.get(UtcCalendar.DAY_OF_WEEK) == 2).toInt() * 48)
                val checkPoint2 = (cal2!!.timeInMillis - (cal2!!.timeInMillis - 1L) % 86400000L - 1L) / 1000L
                //    Log.i("checkPoint2", "$checkPoint2")
                if (checkPoint2 in usHolidays)
                    cal2!!.add(UtcCalendar.HOUR_OF_DAY, -24 - (cal2!!.get(UtcCalendar.DAY_OF_WEEK) == (2 + usOffset.toInt())).toInt() * 48)
                //    Log.i("cal1", "${cal1!!.timeInMillis / 1000L}")
                //    Log.i("cal2", "${cal2!!.timeInMillis / 1000L}")
            }
            _MOEX -> {
                cal1!!.add(UtcCalendar.HOUR_OF_DAY, 7)
                cal2!!.add(UtcCalendar.HOUR_OF_DAY, 19)
                cal3!!.add(UtcCalendar.HOUR_OF_DAY, 10)

            //    Log.i("CAL1", "${cal1!!.time}")
            //    Log.i("CAL2", "${cal2!!.time}")
                if (taskWeek in 3..6) {
                    if (currentMoment <= cal1!!.timeInMillis)
                        cal1!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                    if (currentMoment <= cal2!!.timeInMillis)
                        cal2!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                    if (currentMoment <= cal3!!.timeInMillis)
                        cal3!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                }

                when (taskWeek) {
                    2 -> {
                        if (currentMoment <= cal1!!.timeInMillis)
                            cal1!!.add(UtcCalendar.HOUR_OF_DAY, -72)
                        if (currentMoment <= cal2!!.timeInMillis)
                            cal2!!.add(UtcCalendar.HOUR_OF_DAY, -72)
                        if (currentMoment <= cal3!!.timeInMillis)
                            cal3!!.add(UtcCalendar.HOUR_OF_DAY, -72)
                    }
                    1 -> {
                        cal1!!.add(UtcCalendar.HOUR_OF_DAY, -48)
                        cal2!!.add(UtcCalendar.HOUR_OF_DAY, -48)
                        cal3!!.add(UtcCalendar.HOUR_OF_DAY, -48)
                    }
                    7 -> {
                        cal1!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                        cal2!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                        cal3!!.add(UtcCalendar.HOUR_OF_DAY, -24)
                    }
                }

                val checkPoint1 = (cal1!!.timeInMillis - cal1!!.timeInMillis % 86400000L) / 1000L
                //    Log.i("checkPoint1", "$checkPoint1")
                if (checkPoint1 in moexHolidays)
                    cal1!!.add(UtcCalendar.HOUR_OF_DAY, -24 - (cal1!!.get(UtcCalendar.DAY_OF_WEEK) == 2).toInt() * 48)
                val checkPoint2 = (cal2!!.timeInMillis - cal2!!.timeInMillis % 86400000L) / 1000L
                //    Log.i("checkPoint2", "$checkPoint2")
                if (checkPoint2 in moexHolidays)
                    cal2!!.add(UtcCalendar.HOUR_OF_DAY, -24 - (cal2!!.get(UtcCalendar.DAY_OF_WEEK) == 2).toInt() * 48)
                val checkPoint3 = (cal3!!.timeInMillis - cal3!!.timeInMillis % 86400000L) / 1000L
                //    Log.i("checkPoint3", "$checkPoint3")
                if (checkPoint3 in moexHolidays)
                    cal3!!.add(UtcCalendar.HOUR_OF_DAY, -24 - (cal3!!.get(UtcCalendar.DAY_OF_WEEK) == 2).toInt() * 48)
                //    Log.i("cal1", "${cal1!!.timeInMillis / 1000L}")
                //    Log.i("cal2", "${cal2!!.timeInMillis / 1000L}")
                //    Log.i("cal3", "${cal3!!.timeInMillis / 1000L}")
                changeTime3 = cal3!!.timeInMillis
            }
        }
        changeTime1 = cal1!!.timeInMillis
        changeTime2 = cal2!!.timeInMillis
    //    Log.i("SOL", "${objectType}/${changeTime}/${cal.time}")

        if (objectType != _CRYPTO) {
            xValue = xMin
            while (xValue < xMax) {
                if (dataSet[xValue].zTime > changeTime1) {
                    canvas.drawLine(xValue.toRealX(), offsetY2, xValue.toRealX(), height.toFloat() - offsetY1, axisLinePaint3)
                    break
                }
                xValue += 1
            }
        }

    //    canvas.drawLine(xMax.toFloat(), 0f, xMax.toFloat(), height.toFloat(), axisLinePaint)
        canvas.drawLine(offsetX1, offsetY2, offsetX1, height.toFloat() - offsetY1, axisLinePaint1)
        canvas.drawLine(offsetX1, height.toFloat() - offsetY1, width.toFloat() - offsetX2, height.toFloat() - offsetY1, axisLinePaint1)

        dataSet.forEachIndexed { index, currentDataPoint ->
            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                val startY = currentDataPoint.yVal.toRealY()
                val endX = nextDataPoint.xVal.toRealX()
                val endY = nextDataPoint.yVal.toRealY()
                canvas.drawLine(startX, startY, endX, endY, dataPointLinePaint)
            }

            //    canvas.drawCircle(realX, realY, 7f, dataPointFillPaint)
            //    canvas.drawCircle(realX, realY, 7f, dataPointPaint)
        }

        xValue = xMin
        while (xValue <= xMax) {
            //    Log.i("COMPARE", "<${dataSet[xValue].zTime}/${changeTime2}>")
            if (dataSet[xValue].zTime >= changeTime2) {
                canvas.drawLine(xValue.toRealX(), offsetY2, xValue.toRealX(), height.toFloat() - offsetY1, axisLinePaint3)
                if (xValue > 0) {
                    val currentPriceText = "${dataSet[xValue-1].yVal}"
                    canvas.drawText(currentPriceText, xMax.toRealX() - 6f * currentPriceText.length, height.toFloat() - 34f, dataValueLinePaint5)
                }
                break
            }
            xValue += 1
        }
        if (changeTime3 != Long.MAX_VALUE) {
            xValue = xMin
            while (xValue <= xMax) {
                if (dataSet[xValue].zTime >= changeTime3) {
                    canvas.drawLine(xValue.toRealX(), offsetY2, xValue.toRealX(), height.toFloat() - offsetY1, axisLinePaint3)
                    break
                }
                xValue += 1
            }
        }
        val currentPriceText = "${dataSet[xMax].yVal}"
        canvas.drawText(currentPriceText, xMax.toRealX()-6f*currentPriceText.length, height.toFloat()-24f, dataValueLinePaint4)
    }

    fun setData(newDataSet: List<DataPoint>, taskInput: Int, taskOffset: Boolean, taskValue: Long) {
        objectType = taskInput
        usOffset = taskOffset
        try {
            xMin = newDataSet.minByOrNull { it.xVal }?.xVal ?: 0
            xMax = newDataSet.maxByOrNull { it.xVal }?.xVal ?: 0
            yMin = newDataSet.minByOrNull { it.yVal }?.yVal ?: 0f
            yMax = newDataSet.maxByOrNull { it.yVal }?.yVal ?: 0f
        }
        catch (e: Exception) {
            xMin = 0
            xMax = 0
            yMin = 0f
            yMax = 0f
        }
        currentMoment = taskValue
        cal1 = UtcCalendar.getInstance()
        cal1!!.time = UtcDate(false, taskValue)
        cal2 = UtcCalendar.getInstance()
        cal2!!.time = UtcDate(false, taskValue)
        cal3 = UtcCalendar.getInstance()
        cal3!!.time = UtcDate(false, taskValue)
    //    Log.i("VALUE", "xMax=${xMax}, xMin=${xMin}, yMax=${yMax}, yMin=${yMin}")
        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }

    private fun Int.toRealX() = offsetX1 + toFloat() / xMax.toNonZero() * (width - offsetX1 - offsetX2)
    private fun Float.toRealY() = height - offsetY1 - (toFloat() - yMin) / (yMax - yMin).toNonZero() * (height - offsetY1 - offsetY2)
}

data class DataPoint(
        val xVal: Int,
        val yVal: Float,
        val zTime: Long
)