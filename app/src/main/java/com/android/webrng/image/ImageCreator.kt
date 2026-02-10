package com.android.webrng.image

import android.graphics.*
import android.util.Base64
import com.android.webrng.constants.*
import com.android.webrng.utils.PricePoint
import com.android.webrng.utils.UtcCalendar
import com.android.webrng.utils.UtcDate
import com.android.webrng.utils.utcFormat
import java.io.ByteArrayInputStream
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


class ImageCreator {
    private var width: Int = 0
    private var height: Int = 0
    private val lineCount = 10
    private val lineSpecial = 3

    private val JapaneseCandleGreen = Paint().apply {
        color = Color.rgb(77, 210, 150)
        strokeWidth = 1f
        isAntiAlias = true
    }
    private val JapaneseCandleRed = Paint().apply {
        color = Color.rgb(250, 84, 84)
        strokeWidth = 1f
        isAntiAlias = true
    }
    private val axisLinePaint1 = Paint().apply {
        color = Color.rgb(254, 224, 170)
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }
    private val axisLinePaint2 = Paint().apply {
        color = Color.rgb(254, 233, 203)
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }
    private val dataValueLinePaint1 = Paint().apply {
        color = Color.rgb(250, 175, 185)
        textSize = 6f
    }
    private val graphLineTotal = Paint().apply {
        color = Color.rgb(0, 164, 234)
        strokeWidth = 1f
        isAntiAlias = true
    }
    private val graphLineSpecial = Paint().apply {
        color = Color.rgb(100, 0, 234)
        strokeWidth = 1f
        isAntiAlias = true
    }
    private val graphLineNewYear1 = Paint().apply {
        color = Color.rgb(200, 200, 200)
        strokeWidth = 1f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(2f, 2f), 0f)
    }
    private val graphLineNewYear2 = Paint().apply {
        color = Color.rgb(200, 200, 200)
        strokeWidth = 2f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(2f, 4f), 0f)
    }
    private val graphLineCurrentMoment = Paint().apply {
        color = Color.rgb(224, 224, 224)
        strokeWidth = 1f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(2f, 2f), 0f)
    }
    private val graphOrange = Paint().apply {
        color = Color.rgb(254, 235, 237)
    }
    private val graphOrangeLine1 = Paint().apply {
        color = Color.rgb(214, 214, 214)
        strokeWidth = 1f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(2f, 2f), 0f)
    }
    private val graphOrangeLine2 = Paint().apply {
        color = Color.rgb(185, 185, 185)
        strokeWidth = 2f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(2f, 2f), 0f)
    }
    private val graphOrangeLine3 = Paint().apply {
        color = Color.rgb(250, 175, 185)
        strokeWidth = 2f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(2f, 2f), 0f)
    }

    fun getImage(image: Int, inputType: Int, task: Int, objectType: Int, sector: Int, stockIndex: String, stockName: String, stockLogo: String, targetTimestamp: Long, usOffset: Boolean, taskPrice: Float, currentPrice: PricePoint?, priceArray: Array<PricePoint>?, specialTask: Array<PricePoint>?, specialLength: Float, param1: Int, param2: Int, param3: Int, param4: Int, taskMarketCycle: Int, paramList: FloatArray, MoonType: String, resourceSauron: ByteArray? = null, resourceEpic: ByteArray? = null, resourceMarket: ByteArray? = null, resourceObject: ByteArray? = null, specialResourceList: ArrayList<ByteArray?>? = null, resourceTuring: ByteArray? = null) : Pair<Bitmap?, String> {
    //    Log.i("OBJECT", "$inputType/$sector")
        var marketCycle = taskMarketCycle
        var type = inputType
        if (type > 4)
            type = 4
//<-------------------------------------------------------------------------------------------------SpecialScale
        if (objectType == _OTHER) {
            if (param4 != intraValue) {
                width = logoSizeX[task]!![type]
                if (type % 2 != 0)
                    width += 480
            }
            else {
                var taskIndex: Int = -1
                when (inputType) {
                    0,5 -> {
                        if (sector > 1)
                            taskIndex = _1LV
                        else
                            taskIndex = _1RV
                    }
                    1 -> {
                        taskIndex = _1H
                    }
                    2 -> {
                        if (sector < 2)
                            taskIndex = _2RV
                        else
                            taskIndex = _2LV
                    }
                    3 -> {
                        taskIndex = _1H
                    }
                    4 -> {
                        if (sector > 1)
                            taskIndex = _1LH
                        else
                            taskIndex = _1RH
                    }
                    6 -> {
                        if (sector % 2 == 0)
                            taskIndex = _2RV
                        else
                            taskIndex = _2LV
                    }
                }
            //    Log.i("ScaleWidth", "<${intraSizeX[taskIndex]}}>")
                width = intraSizeX[taskIndex]!!
            }
        }
        else {
            width = superSizeX[type]!!
        }
        if (objectType == _OTHER) {
            if (param4 != intraValue) {
                height = logoSizeY[task]!![type]
            }
            else {
                var taskIndex: Int = -1
                when (inputType) {
                    0,5 -> {
                        if (sector > 1)
                            taskIndex = _1LV
                        else
                            taskIndex = _1RV
                    }
                    1 -> {
                        taskIndex = _1H
                    }
                    2 -> {
                        if (sector < 2)
                            taskIndex = _2RV
                        else
                            taskIndex = _2LV
                    }
                    3 -> {
                        taskIndex = _1H
                    }
                    4 -> {
                        if (sector > 1)
                            taskIndex = _1LH
                        else
                            taskIndex = _1RH
                    }
                    6 -> {
                        if (sector % 2 == 0)
                            taskIndex = _2RV
                        else
                            taskIndex = _2LV
                    }
                }
            //    Log.i("ScaleHeight", "<${intraSizeY[taskIndex]}}>")
                height = intraSizeY[taskIndex]!!
            }
        }
        else {
            height = superSizeY[type]!!
        }

        if (objectType == _OTHER) {
            //inputType % 2 != 0 or inputValue > 4
            if (param4 == intraValue && type % 2 != 0 || inputType > 4 && image % 2 != 0) {
                val size = width
                width = height
                height = size
            }
        }
//<-------------------------------------------------------------------------------------------------SpecialLayout
        var globalPosX: Int = 0
        var globalPosY: Int = 0
        var globalSizeX: Int = 0
        var globalSizeY: Int = 0
        val specialStockLayout = inputType > 3 && objectType != _OTHER && (image % 2 == 1 || inputType == 4)
        val specialOtherLayout = inputType < 4 && objectType == _OTHER
        if (specialStockLayout) {
            width = 411
            height = 336
            //509
            when {
                inputType == 4 -> {
                    //    Log.i("SCALE", "FLOAT HORIZONTAL")
                    globalPosX = 9 + if (sector < 2) -1 else 0
                    globalPosY = 15
                    globalSizeX = 393
                    globalSizeY = 321
                    //X=393 Y=465
                }
                inputType == 5 && sector < 2 -> {
                    //    Log.i("SCALE", "TARGET RIGHT")
                    globalPosX = 7
                    globalSizeX = 396
                    globalSizeY = 336
                    //X=396 Y=480
                }
                inputType == 5 && sector > 1 -> {
                    //    Log.i("SCALE", "TARGET LEFT")
                    globalPosX = 25
                    globalSizeX = 360
                    globalSizeY = 336
                    //X=360 Y=480
                }
                inputType == 6 && sector % 2 == 0 -> {
                    //    Log.i("SCALE", "TASK RIGHT")
                    globalSizeX = 411
                    globalSizeY = 336
                    //X=411 Y=480
                }
                inputType == 6 && sector % 2 == 1 -> {
                    //    Log.i("SCALE", "TASK LEFT")
                    globalPosX = 17
                    // + if (sector > 2) -1 else 0
                    globalSizeX = 375
                    globalSizeY = 336
                    //X=375 Y=480
                }
            }
        }
        else if (specialOtherLayout) {
            if (type % 2 != 0) {
                globalPosX = 480
                globalSizeX = 800
                globalSizeY = 480
            }
            else {
                if (sector % 2 == 1) {
                    height = 800
                    globalPosY = 400
                }
                globalSizeX = 480
                globalSizeY = 800
            }
        }
        else {
            globalSizeX = width
            globalSizeY = height
         }
//<-------------------------------------------------------------------------------------------------Size
    //    Log.i("IMAGECREATOR", "${width}/${height}")
        if (width <= 0 || height <= 0)
            return Pair(null, "")

        var bitmap: Bitmap? = null
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            //    val bitmap = BitmapFactory.decodeResource(res, image).copy(Bitmap.Config.ARGB_8888, true)
        }
        catch (e: Exception) {
            return Pair(null, "")
        }
//<-------------------------------------------------------------------------------------------------White
        if (bitmap == null)
            return Pair(null, "")
        val canvas = Canvas(bitmap)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), getPointBrush(Color.rgb(160, 255, 176)))

    /*    if (specialStockLayout && false) {
            canvas.drawRect(0f, 0f, 411f, 336f, getPointBrush(Color.rgb(0, 255, 0)))
            canvas.drawRect(7f, 0f, 403f, 336f, getPointBrush(Color.rgb(255, 255, 0)))
            canvas.drawRect(9f, 15f, 402f, 336f, getPointBrush(Color.rgb(0, 255, 255)))
            canvas.drawRect(17f, 0f, 392f, 336f, getPointBrush(Color.rgb(0, 0, 255)))
            canvas.drawRect(25f, 0f, 385f, 336f, getPointBrush(Color.rgb(255, 0, 0)))
        /*    if (image > 0) {
                Log.i("ROTATE", "${image * 90f}")
                val matrix = Matrix()
                matrix.postRotate(image * 90f)
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.height, matrix, true)
            }
            Log.i("SPECIALRESULT", "${bitmap.width}/${bitmap.height}")
            return Pair(bitmap, "Elon Musk")  */
        }   */
//<-------------------------------------------------------------------------------------------------Scale
        try {
            if (resourceSauron != null) {
                val outputScale: Bitmap? = BitmapFactory.decodeByteArray(resourceSauron, 0, resourceSauron.size).copy(Bitmap.Config.ARGB_8888, true)
                if (outputScale != null)
                    canvas.drawBitmap(outputScale, scaleAxisX[type]!! - outputScale.width / 2, superPosY[task]!![type], null)
            }
        }
        catch (e: Exception) { }
//<-------------------------------------------------------------------------------------------------SpecialResource
        val stockMultiVertical = param4 == intraValue && type % 2 == 0 && type < 4 && objectType != _OTHER
        if (task in specialResourceSizeX) {
        //    Log.i("SpecialResource", "Size=${specialResourceList!!.size}")
            if (specialResourceList != null) {
                    for (s in 0 until specialResourceList.size) {
                        try {
                        //    Log.i("SpecialResource", "${specialResourcePosX[task]!![s][type]}/${specialResourcePosY[task]!![s][type]}/${specialResourceSizeX[task]!![s][type]}/${specialResourceSizeY[task]!![s][type]}")
                            if (specialResourceSizeX[task]!![s][type] != -1) {
                                if (specialResourceList[s] != null) {
                                    var outputScale: Bitmap? = BitmapFactory.decodeByteArray(specialResourceList[s]!!, 0, specialResourceList[s]!!.size).copy(Bitmap.Config.ARGB_8888, true)
                                    if (outputScale != null) {
                                        if (specialResourceSizeY[task]!![s][type] > 0)
                                            outputScale = Bitmap.createScaledBitmap(outputScale, specialResourceSizeX[task]!![s][type], specialResourceSizeY[task]!![s][type], false)
                                        else if (specialResourceSizeY[task]!![s][type] < 0) {
                                            var specialResourceTaskSizeY: Int = (bitmap.height - specialResourcePosY[task]!![s][type]).roundToInt()
                                            if (stockMultiVertical) {
                                                when (task) {
                                                    _PARAM3, _PARAM7 -> specialResourceTaskSizeY = 19
                                                    _PARAM5, _PARAM8 -> specialResourceTaskSizeY = 33
                                                }
                                            }
                                            outputScale = Bitmap.createScaledBitmap(outputScale, specialResourceSizeX[task]!![s][type], specialResourceTaskSizeY, false)
                                        }
                                        canvas.drawBitmap(outputScale!!, specialResourcePosX[task]!![s][type], specialResourcePosY[task]!![s][type], null)
                                    }
                                }
                            }
                        }
                        catch (e: Exception) { }
                    }
            }
        }
//<-------------------------------------------------------------------------------------------------Logo
        var taskLogoImage: ByteArrayInputStream? = null
        try {
            //    Log.i("LOGO", "${stockLogo}")
            val byteStr: ByteArray = Base64.decode(stockLogo, Base64.URL_SAFE)
            taskLogoImage = ByteArrayInputStream(byteStr)
        }
        catch (e: Exception) { }
        if (taskLogoImage != null) {
            try {
                val logoImage: Bitmap? = BitmapFactory.decodeStream(taskLogoImage)
                if (logoImage != null) {
                    var taskSizeX: Int = logoImage.width
                    var taskSizeY: Int = logoImage.height
                    var taskPosX: Float = 0f
                    var taskPosY: Float = 0f
                    if (param4 == intraValue && objectType == _OTHER) {
                        when {
                            //(inputType == 5 && sector < 2 || inputType == 6 && sector % 2 == 0) && image == 2
                            inputType < 4 && (type % 2 == 0 && sector > 1 || image == 2) || (inputType == 5 && sector > 1 || inputType == 6 && sector % 2 == 1) && image % 2 == 0 -> {
                                taskPosY = -29f
                            }
                            inputType == 4 -> {
                                taskPosX = -44f
                            }
                            inputType == 5 && sector < 2 && image % 2 != 0 -> {
                                taskPosX = -42f
                                taskPosY = -29f
                            }
                            inputType == 5 && sector > 1 && image % 2 != 0 -> {
                                taskPosX = -60f
                                taskPosY = -29f
                            }
                            inputType == 6 && sector % 2 == 0 && image % 2 != 0 -> {
                                taskPosX = -35f
                                taskPosY = -29f
                            }
                            inputType == 6 && sector % 2 == 1 && image % 2 != 0 -> {
                                taskPosX = -53f
                                taskPosY = -29f
                            }
                        }
                    //    Log.i("SCALE", "$type/$inputType/$sector/$taskPosX/$taskPosY")
                    }
                    else if (objectType != _OTHER) {
                        if (inputType < 4) {
                            taskSizeX = logoSizeX[task]!![type]
                            taskSizeY = logoSizeY[task]!![type]
                            taskPosX = logoPosX[task]!![type] + taskSizeX / 2
                            taskPosY = logoPosY[task]!![type] + taskSizeY
                        }
                        else {
                            var logoType: Int = 0
                            when (inputType) {
                                5 -> logoType = 1 + if (sector > 1) 1 else 0
                                6 -> logoType = 3 + if (sector % 2 == 1) 1 else 0
                            }
                            taskSizeX = floatLogoSizeX[task]!![logoType]
                            taskSizeY = floatLogoSizeY[task]!![logoType]
                            taskPosX = floatLogoPosX[task]!![logoType] + taskSizeX / 2
                            taskPosY = floatLogoPosY[task]!![logoType] + taskSizeY
                        }
                    }
                //    Log.i("LOGO", "Pos$taskPosX/$taskPosY/Size${taskSizeX}/${taskSizeY}")
                    //    val logoImage = BitmapFactory.decodeByteArray(byteStr, 0, byteStr.size)
                    if (objectType != _OTHER) {
                        var coef1: Float = 0f
                        var coef2: Float = 0f
                        val sourceWidth = logoImage.width.toFloat()
                        val sourceHeight = logoImage.height.toFloat()
                        val targetWidth = taskSizeX.toFloat()
                        val targetHeight = taskSizeY.toFloat()
                        if (logoImage.width > logoImage.height) {
                            coef1 = sourceWidth / sourceHeight
                            coef2 = targetWidth / targetHeight
                            if (coef1 < coef2) {
                                taskSizeX = ((targetHeight / sourceHeight) * sourceWidth).roundToInt()
                            }
                            else {
                                taskSizeY = ((targetWidth / sourceWidth) * sourceHeight).roundToInt()
                            }
                        }
                        else {
                            coef1 = sourceHeight / sourceWidth
                            coef2 = targetHeight / targetWidth
                            if (coef1 < coef2) {
                                taskSizeY = ((targetWidth / sourceWidth) * sourceHeight).roundToInt()
                            }
                            else {
                                taskSizeX = ((targetHeight / sourceHeight) * sourceWidth).roundToInt()
                            }
                        }
                        canvas.drawBitmap(Bitmap.createScaledBitmap(logoImage, taskSizeX, taskSizeY, false), taskPosX - taskSizeX / 2, taskPosY - taskSizeY, null)
                    }
                    else {
                        canvas.drawBitmap(logoImage, taskPosX, taskPosY, null)
                    }
                }
                else {
                    throw Exception("Logo")
                }
            }
            catch (e: Exception) {
                val taskSizeX: Float = if (objectType == _OTHER) width.toFloat() else logoSizeX[type]!![task].toFloat()
                val taskSizeY: Float = if (objectType == _OTHER) height.toFloat() else logoSizeY[type]!![task].toFloat()
                try {
                    canvas.drawRect(0f, 0f, taskSizeX, taskSizeY, getPointBrush(Color.rgb(160, 255, 176)))
                }
                catch (e: Exception) { }
            }
            try {
                taskLogoImage.close()
            }
            catch (e: Exception) { }
        }
//<-------------------------------------------------------------------------------------------------Target Date
        var dateText: String = ""
        var targetDate = UtcCalendar.getInstance()
        var targetDateString: String = ""
        var taskDateOffsetX: Int = -1
        var utcOffsetX: Int = 2
        var stockNameOffsetX: Int = type % 2 + 1
        if (objectType != _OTHER) {
            when (task) {
                _PARAM0 -> {
                    targetDate.time = UtcDate(false, targetTimestamp)
                    targetDateString = "dd.MM.yyyy".utcFormat(targetDate.time, currentGreenwichOffset)
                    dateText = targetDateString
                }
                _PARAM1 -> {
                    targetDate.time = UtcDate(false, targetTimestamp)
                    targetDate.set(targetDate.get(UtcCalendar.YEAR), targetDate.get(UtcCalendar.MONTH), targetDate.get(UtcCalendar.DAY_OF_MONTH), epicSauron[objectType]!!, epicMorgoth[objectType]!!, 0)
                    if (objectType == _USA && usOffset) {
                        targetDate.add(UtcCalendar.HOUR_OF_DAY, 1)
                    }
                    var targetDateHour = targetDate.get(UtcCalendar.HOUR_OF_DAY)
                    val targetDateMinute = targetDate.get(UtcCalendar.MINUTE)
                    if (objectType == _MOEX) {
                        taskDateOffsetX += 2
                        utcOffsetX += 2
                    }

                    if (targetDateHour == 0 && targetDateMinute == 0) {
                        targetDate.add(UtcCalendar.HOUR_OF_DAY, -24)
                        targetDateHour = 24
                    }
                    targetDateString = "dd.MM.yyyy".utcFormat(targetDate.time, currentGreenwichOffset)
                    dateText = "$targetDateHour:${"%02d".format(targetDateMinute)} $targetDateString"
                }
                _PARAM2, _PARAM3 -> {
                    targetDate.time = UtcDate()
                    //    Log.i("DATETIME", "${targetDate.time.loc.time}")
                    var targetDateMinute = targetDate.get(UtcCalendar.MINUTE)
                    //    targetDateMinute = 30 - targetDateMinute % 30 + 30
                    targetDateMinute = 30 - targetDateMinute % 30 + (param3 % 60) * 15
                    targetDate.add(UtcCalendar.MINUTE, targetDateMinute)
                    //    targetDate.add(UtcCalendar.HOUR_OF_DAY, outputGreenwichOffset)
                    var targetDateHour = targetDate.get(UtcCalendar.HOUR_OF_DAY)
                    targetDateMinute = targetDate.get(UtcCalendar.MINUTE)
                    if (targetDateHour > 9 && targetDateHour < 20) {
                        taskDateOffsetX += 2
                        utcOffsetX += 2
                    }

                    if (targetDateHour == 0 && targetDateMinute == 0) {
                        targetDate.add(UtcCalendar.HOUR_OF_DAY, -24)
                        targetDateHour = 24
                    }
                    targetDateString = "dd.MM.yyyy".utcFormat(targetDate.time, currentGreenwichOffset)
                    dateText = "${"%02d".format(targetDateHour)}:${"%02d".format(targetDateMinute)} $targetDateString"
                }
                _PARAM4, _PARAM5, _PARAM6, _PARAM7, _PARAM8 -> {
                    targetDate.time = UtcDate(false, targetTimestamp)
                    targetDateString = "dd.MM.yyyy".utcFormat(targetDate.time, Int.MAX_VALUE)
                    if (MoonType.length > 2)
                        dateText = "${MoonType.substring(2, MoonType.length)} $targetDateString"
                    taskDateOffsetX += 1
                    //    utcOffsetX += 1
                    stockNameOffsetX = 1 - type % 2
                }
            }
        }
//<-------------------------------------------------------------------------------------------------------------Stock Market
        if (objectType != _OTHER && priceArray != null) {
//<-------------------------------------------------------------------------------------------------Stock Index
            val outputStockIndex = stockIndex.replace("_", "")
            var textBrush = getTextBrush(stockIndexColor[objectType]!!, stockIndexSize[type]!!.pxTo(), fontName, Typeface.BOLD)
            var outputLength = getTextWidth(outputStockIndex, textBrush)
            canvas.drawText(outputStockIndex, stockAxisX[type]!! - outputLength / 2, stockIndexPosY[task]!![type] + stockIndexSize[type]!!, textBrush)
//<-------------------------------------------------------------------------------------------------Currency Target
            textBrush = getTextBrush(currencyValueColor[objectType]!!, currencyValueSize[type]!!.pxTo(), fontName, Typeface.BOLD)
            outputLength = getTextWidth(typeCurrencyValue[objectType]!!, textBrush)
            canvas.drawText(typeCurrencyValue[objectType]!!, currencyAxisX[type]!! - outputLength / 2, currencyValuePosY[task]!![type] + currencyValueSize[type]!!, textBrush)
//<-------------------------------------------------------------------------------------------------UTC
            val utcText = "UTC +$outputGreenwichOffset"
            textBrush = getTextBrush(utcColor[objectType]!!, utcSize[task]!!.pxTo(), fontName, Typeface.BOLD)
            outputLength = getTextWidth(utcText, textBrush)
            canvas.drawText(utcText, stockAxisX[type]!! - outputLength / 2 + utcOffsetX, utcPosY[task]!![type] + utcSize[task]!!, textBrush)
//<-------------------------------------------------------------------------------------------------Sector
            var sOffset = 0
            val sectorOffset = scaleAxisX[type]!! - sectorArray[sectorArray.size - 1] / 2
            while (sOffset < sectorArray.size - 1) {
                canvas.drawRect(sectorOffset + sectorArray[sOffset], sectorPosY[task]!![type], sectorOffset + sectorArray[sOffset + 1], sectorPosY[task]!![type] + sectorSize[type]!!, getPointBrush(sectorColor[objectType]!!))
                sOffset += 2
            }
//<-------------------------------------------------------------------------------------------------Graph
            try {
                sOffset = 0
                while (sOffset < matrixArray[task]!![type].size - 1) {
                    canvas.drawRect(graphPosX[task]!![type] + matrixArray[task]!![type][sOffset] - 1f, graphPosY[task]!![type].toFloat(), graphPosX[task]!![type] + matrixArray[task]!![type][sOffset + 1] - 1f, graphPosY[task]!![type] + graphSizeY[task]!![type], getPointBrush(graphColor[objectType]!!))
                    sOffset += 2
                }
            } catch (e: Exception) {
            }

            canvas.drawRect(graphPosX[task]!![type] + graphSizeX[task]!![type], graphPosY[task]!![type].toFloat() - 2f, arraySpecialGraphText[task]!![type] - 1f, graphPosY[task]!![type] + graphSizeY[task]!![type] + 2f, getPointBrush(epicValueAreaColor[objectType]!!))
            when (task) {
                _PARAM6, _PARAM7, _PARAM8 -> {
                    canvas.drawLine(graphPosX[task]!![type] + graphSizeX[task]!![type] - finishGraphSize[task]!! / 2, graphPosY[task]!![type].toFloat(), graphPosX[task]!![type] + graphSizeX[task]!![type] - finishGraphSize[task]!! / 2, graphPosY[task]!![type] + graphSizeY[task]!![type], getPointBrush(graphOrange.color, finishGraphSize[task]!!))
                }
            }
            canvas.drawLine(graphPosX[task]!![type] + graphSizeX[task]!![type] - finishGraphSize[task]!! / 2, graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], graphPosX[task]!![type] + graphSizeX[task]!![type] - finishGraphSize[task]!! / 2, graphPosY[task]!![type] + graphSizeY[task]!![type], getPointBrush(finishGraph1Color[task]!!, finishGraphSize[task]!!, 2))
            canvas.drawLine(arraySpecialGraphText[task]!![type] - 1f, graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], arraySpecialGraphText[task]!![type] - 1f, graphPosY[task]!![type] + graphSizeY[task]!![type], getPointBrush(finishGraph2Color[objectType]!!))
//<-------------------------------------------------------------------------------------------------Target Date
            //    Log.i("OFFSET", "${taskDateOffsetX}")
            textBrush = getTextBrush(epicValueColor[task]!!, epicValueSize[type]!!.pxTo(), fontName, Typeface.BOLD)
            outputLength = getTextWidth(dateText, textBrush)
            canvas.drawText(dateText, stockAxisX[type]!! - outputLength / 2 - taskDateOffsetX, epicValuePosY[task]!![type] + epicValueSize[type]!!, textBrush)
//<-------------------------------------------------------------------------------------------------Stock Name
            textBrush = getTextBrush(stockNameColor[task]!!, stockNameSize[type]!!.pxTo(), fontName, Typeface.BOLD)
            outputLength = getTextWidth(stockName, textBrush)
            if (outputLength <= stockNameMaxLength[type]!!) {
                canvas.drawText(stockName, stockAxisX[type]!! - outputLength / 2 + stockNameOffsetX, stockNamePosY[task]!![type] + stockNameSize[type]!!, textBrush)
            }
            else {
                textBrush.textScaleX = stockNameMaxLength[type]!! / outputLength
                val scaleLength = getTextWidth(stockName, textBrush)
                canvas.drawText(stockName, stockAxisX[type]!! - scaleLength / 2 + stockNameOffsetX, stockNamePosY[task]!![type] + stockNameSize[type]!!, textBrush)
            }
//<-------------------------------------------------------------------------------------------------Price Array
            //    if (currentPrice != null)
            //    Log.i("CURRENTPRICE", "${currentPrice.open}/${currentPrice.high}/${currentPrice.low}/${currentPrice.close}/${currentPrice.date}")

            if (marketCycle < 0) {
                marketCycle = 0
                //    priceArrayLength = 0
            }
            var currentPriceOutput = taskPrice
            var priceArrayLength: Int = 0
            var currentPriceLength: Int = 0
            var specialPriceArray: Array<PricePoint?>? = null
            try {
                when (task) {
                    _PARAM1 -> {
                        priceArrayLength = priceArray.size
                        if (currentPrice != null) {
                            currentPriceLength++
                        }
                    }
                    _PARAM2, _PARAM3 -> {
                        priceArrayLength = priceArray.size
                        if (specialTask != null) {
                            //    var specialCurrentPriceLength: Int = 0
                            //    if (currentPrice != null)
                            //        specialCurrentPriceLength++

                            specialPriceArray = Array<PricePoint?>(specialTask.size) { index -> null }
                            //Verification!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            for (s in 0 until specialTask.size) {
                                specialPriceArray[s] = specialTask[s]
                                //    Log.i("COPY", "${s}/${specialTask[s]}")
                            }
                            /*    if (specialCurrentPriceLength > 0) {
                        var taskPriceOpen: Float
                        var taskHighPrice: Float
                        var taskLowPrice: Float
                        if (specialTask.size > 0) {
                            val comparePrice = specialPriceArray[1]
                            taskPriceOpen = comparePrice!!.close
                            taskHighPrice = if (currentPrice!!.close > comparePrice.close) currentPrice.close else comparePrice.close
                            taskLowPrice = if (currentPrice.close < comparePrice.close) currentPrice.close else comparePrice.close
                        }
                        else {
                            taskPriceOpen = currentPrice!!.open
                            taskHighPrice = currentPrice.high
                            taskLowPrice = currentPrice.low
                        }
                        specialPriceArray[0] = PricePoint(Date().time, taskPriceOpen, taskHighPrice, taskLowPrice, currentPrice.close, 0)
                    }   */
                        }
                    }
                    _PARAM4, _PARAM5 -> {
                        var specialTaskLength: Int = 0
                        var weekLength = specialLength.roundToInt() % epicValue1
                        if (weekLength != (epicValue1 - 1)) {
                            if (currentPrice != null)
                                specialTaskLength++
                        } else {
                            weekLength = 0
                        }
                    //    Log.i("WEEK", "${weekLength}/${epicValue1}")

                        priceArrayLength = priceArray.size - weekLength
                        specialPriceArray = Array<PricePoint?>(weekLength + specialTaskLength) { index -> null }
                        for (s1 in 0 until weekLength) {
                            specialPriceArray[s1 + specialTaskLength] = PricePoint(priceArray[s1].date, priceArray[s1].open, priceArray[s1].low, priceArray[s1].high, priceArray[s1].close, 0)
                        }
                        if (specialTaskLength > 0)
                            specialPriceArray[0] = PricePoint(currentPrice!!.date, currentPrice.open, currentPrice.low, currentPrice.high, currentPrice.close, 0)
                    }
                    _PARAM6, _PARAM7, _PARAM8 -> {
                        var epicSpecialLength: Int = 0
                        if (marketCycle > 0) {
                            priceArrayLength = marketCycle
                            epicSpecialLength = priceArray.size - priceArrayLength
                        }
                        else {
                            epicSpecialLength = marketCycle
                            priceArrayLength = priceArray.size - epicSpecialLength
                        }
                        var specialTaskLength: Int = 0
                        if (currentPrice != null) {
                            specialTaskLength++
                        }
                    //    Log.i("EPIC", "<$epicSpecialLength/$priceArrayLength>")

                        specialPriceArray = Array<PricePoint?>(epicSpecialLength + specialTaskLength) { index -> null }
                        for (s in 0 until epicSpecialLength) {
                            specialPriceArray[s + specialTaskLength] = PricePoint(priceArray[s].date, priceArray[s].open, priceArray[s].low, priceArray[s].high, priceArray[s].close, 0)
                        }
                        if (specialTaskLength > 0)
                            specialPriceArray[0] = PricePoint(currentPrice!!.date, currentPrice.open, currentPrice.low, currentPrice.high, currentPrice.close, 0)
                    }
                }
            } catch (e: Exception) {
                when (task) {
                    _PARAM1 -> {
                        priceArrayLength = priceArray.size
                        currentPriceLength = 0
                    }
                    _PARAM2, _PARAM3, _PARAM4, _PARAM5 -> {
                        priceArrayLength = priceArray.size
                        specialPriceArray = null
                    }
                }
            }
//<-------------------------------------------------------------------------------------------------SpecialArray
            var maxPrice: Float = 0f
            var minPrice: Float = 0f
            try {
                maxPrice = priceArray.maxByOrNull { it.close }!!.close
                minPrice = priceArray.minByOrNull { it.close }!!.close
            } catch (e: Exception) {
                maxPrice = 0f
                minPrice = 0f
            }

            var maxSpecialPrice: Float = 0f
            var minSpecialPrice: Float = 0f
            try {
                if (!specialPriceArray.isNullOrEmpty() && task in specialList1) {
                    maxSpecialPrice = specialPriceArray.maxByOrNull { it!!.close }!!.close
                    minSpecialPrice = specialPriceArray.minByOrNull { it!!.close }!!.close
                }
            } catch (e: Exception) {
                maxSpecialPrice = 0f
                minSpecialPrice = 0f
            }

            var totalOffset: Int = 0
            var totalMoonLength: Int = 0
            when (task) {
                _PARAM4, _PARAM5 -> {
                    totalMoonLength = (specialLength.roundToInt() % (epicValue1 * epicValue1)) / epicValue1
                    val weekState1 = specialLength.roundToInt() % (epicValue1 * epicValue1 * epicValue1) / epicValue1 / epicValue1
                    val weekState2 = specialLength.roundToInt() % (epicValue1 * epicValue1 * epicValue1 * epicValue1) / epicValue1 / epicValue1 / epicValue1
                    totalOffset = (arraySpecialGraph[task]!![type] * totalMoonLength).roundToInt() + (if (weekState1 > 0) 1 else 0) + (if (weekState2 > 0) 1 else 0) + 1
                    //    Log.i("TOTAL MOON LENGTH", totalMoonLength.toString())
                }
                _PARAM6, _PARAM7, _PARAM8 -> {
                    try {
                        maxPrice = if (maxSpecialPrice < maxPrice) maxPrice else maxSpecialPrice
                        minPrice = if (minSpecialPrice > minPrice) minPrice else minSpecialPrice
                    } catch (e: Exception) {
                        maxSpecialPrice = 0f
                        minSpecialPrice = 0f
                    }
                    totalOffset = specialLength.roundToInt() % epicValue2
                    totalMoonLength = (specialLength.roundToInt() % (epicValue2 * epicValue2)) / epicValue2
                    //    Log.i("TOTAL ECLIPSE LENGTH", "${totalOffset}/${totalMoonLength}")
                }
            }

            var graphArray = arrayOfNulls<Point>(priceArrayLength)
            var pixelInterval: Float = 0f
            var currentPixel = 0f
//<-------------------------------------------------------------------------------------------------GraphNewYearLine
            val taskMarket = if ((priceArrayLength > marketCycle || task in specialList1) && marketCycle > 0) true else false                 //priceArrayLength + currentPriceLength >= marketCycle && marketCycle > 0
        //    Log.i("MARKET", "${marketCycle}/${priceArrayLength}/${currentPriceLength}")
            if (taskMarket) {
                var lineOffset: Float = 0f
                var lineType: Paint? = null
                when (task) {
                    _PARAM1, _PARAM2, _PARAM3 -> {
                        if (priceArrayLength + currentPriceLength != 0) {
                            lineOffset = graphSizeX[task]!![type] * marketCycle / (priceArrayLength + currentPriceLength)
                            lineType = graphLineNewYear1
                        }
                    }
                    _PARAM4, _PARAM5 -> {
                        if (priceArrayLength + currentPriceLength != 0) {
                            lineOffset = (graphSizeX[task]!![type] - totalOffset) * marketCycle / (priceArrayLength + currentPriceLength)
                            lineType = graphLineNewYear1
                        }
                    }
                    _PARAM6, _PARAM7, _PARAM8 -> {
                        lineOffset = arraySpecialGraph[task]!![type]
                        lineType = graphLineNewYear2
                    }
                }
                if (lineType != null)
                    canvas.drawLine(graphPosX[task]!![type] + lineOffset, graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], graphPosX[task]!![type] + lineOffset, graphPosY[task]!![type] + graphSizeY[task]!![type], lineType)
            }
//<-------------------------------------------------------------------------------------------------GraphTotal
            when (task) {
                _PARAM1, _PARAM2, _PARAM3 -> {
                    val taskValue = priceArrayLength + currentPriceLength - 1
                    if (taskValue != 0)
                        pixelInterval = (graphSizeX[task]!![type] - 1) / taskValue
                    else
                        pixelInterval = 0f
                }
                _PARAM4, _PARAM5 -> {
                    val weekState1 = specialLength.roundToInt() % (epicValue1 * epicValue1 * epicValue1) / epicValue1 / epicValue1 - 1
                    val weekState2 = specialLength.roundToInt() % (epicValue1 * epicValue1 * epicValue1 * epicValue1) / epicValue1 / epicValue1 / epicValue1 - 1
                    //    Log.i("WEEKSTATE", "$weekState1/$weekState2")
                    var offsetSpecialX: Int = 0
                    val specialGraphX = graphPosX[task]!![type] + graphSizeX[task]!![type] - totalOffset
                    val specialGraphY = graphPosY[task]!![type] + graphSizeY[task]!![type]
                    for (s in 0 until totalMoonLength) {
                        val taskOffset = if (s == weekState1 || s == weekState2) 1 else 0
                        canvas.drawRect(specialGraphX + offsetSpecialX + arraySpecialGraph[task]!![type] * s, graphPosY[task]!![type].toFloat(), specialGraphX + offsetSpecialX + taskOffset + arraySpecialGraph[task]!![type] * (s + 1), specialGraphY, graphOrange)
                        canvas.drawLine(specialGraphX + offsetSpecialX + taskOffset + arraySpecialGraph[task]!![type] * (s + 1) - taskOffset, graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], specialGraphX + offsetSpecialX + taskOffset + arraySpecialGraph[task]!![type] * (s + 1) - 1, specialGraphY, if (taskOffset == 1) graphOrangeLine2 else graphOrangeLine1)
                        if (taskOffset == 1)
                            offsetSpecialX += 1
                    }

                    canvas.drawLine(specialGraphX - 1, graphPosY[task]!![type].toFloat(), specialGraphX - 1, specialGraphY, getPointBrush(graphOrange.color, 2f))
                    canvas.drawLine(specialGraphX - 1, graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], specialGraphX - 1, specialGraphY, graphOrangeLine2)
                    canvas.drawLine(specialGraphX + offsetSpecialX + arraySpecialGraph[task]!![type] * totalMoonLength, graphPosY[task]!![type].toFloat(), specialGraphX + offsetSpecialX + arraySpecialGraph[task]!![type] * totalMoonLength, specialGraphY, getPointBrush(graphOrange.color, 2f))
                    canvas.drawLine(specialGraphX + offsetSpecialX + arraySpecialGraph[task]!![type] * totalMoonLength, graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], specialGraphX + offsetSpecialX + arraySpecialGraph[task]!![type] * totalMoonLength, specialGraphY, graphOrangeLine3)
                    if (priceArrayLength != 1)
                        pixelInterval = (graphSizeX[task]!![type] - totalOffset) / (priceArrayLength - 1)
                    else
                        pixelInterval = 0f
                }
                _PARAM6, _PARAM7, _PARAM8 -> {
                    val offsetX = if (taskMarket) arraySpecialGraph[task]!![type].roundToInt() else 0
                    val sizeX = if (taskMarket) graphSizeX[task]!![type] - offsetX else graphSizeX[task]!![type]
                    val specialPriceArrayStart = if (specialPriceArray != null) specialPriceArray.size else 0
                    var startPoint: Float = 0f
                    if (totalMoonLength != 1)
                        startPoint = offsetX + sizeX * specialPriceArrayStart / (totalMoonLength - 1)

                    var k = 1
                    try {
                        while (k < matrixArray[task]!![type].size) {
                            if (matrixArray[task]!![type][k] > startPoint)
                                break
                            k++
                        }
                        if (k < matrixArray[task]!![type].size) {
                            var firstPoint: Float
                            if (k % 2 == 1) {
                                firstPoint = startPoint
                            } else {
                                firstPoint = matrixArray[task]!![type][k]
                                k++
                            }
                            canvas.drawRect(graphPosX[task]!![type] + firstPoint, graphPosY[task]!![type].toFloat(), graphPosX[task]!![type] + matrixArray[task]!![type][k++], graphPosY[task]!![type].toFloat() + graphSizeY[task]!![type], graphOrange)
                            while (k < matrixArray[task]!![type].size - 1) {
                                canvas.drawRect(graphPosX[task]!![type] + matrixArray[task]!![type][k], graphPosY[task]!![type].toFloat(), graphPosX[task]!![type] + matrixArray[task]!![type][k + 1], graphPosY[task]!![type].toFloat() + graphSizeY[task]!![type], graphOrange)
                                k += 2
                            }
                        }
                    } catch (e: Exception) {
                    }
                    //    Log.i("MATRIX", "${taskMarket}/${offsetX}/${graphSizeX[task]!![type]}/${startPoint}")
                    pixelInterval = if (taskMarket && marketCycle > 1) arraySpecialGraph[task]!![type] / (marketCycle - 1) else 0f
                }
            }
            var k1 = priceArrayLength - 1
            var k2 = priceArray.size - 1
            try {
                while (k1 >= 0) {
                //    Log.i("ARRAY", "${priceArray.size}/${priceArrayLength}/${k1}")
                    graphArray[k1] = Point(graphPosX[task]!![type] + currentPixel.roundToInt(), graphPosY[task]!![type] + (graphSizeY[task]!![type] * (maxPrice - priceArray[k2].close) / (maxPrice - minPrice)).roundToInt())
                    currentPixel += pixelInterval
                    k1--
                    k2--
                }
                for (s in 0 until priceArrayLength - 1) {
                    //    Log.i("POINT", "X=${graphArray[s]!!.x}")
                    canvas.drawLine(graphArray[s]!!.x.toFloat(), graphArray[s]!!.y.toFloat(), graphArray[s + 1]!!.x.toFloat(), graphArray[s + 1]!!.y.toFloat(), graphLineTotal)
                }
            }
            catch (e: Exception) { }
//<-------------------------------------------------------------------------------------------------Current Price
            var currentPricePoint: Point? = null
            if (currentPriceLength > 0 && graphArray.isNotEmpty()) {
                if (graphArray[0] != null && (maxPrice - minPrice) != 0f) {
                    currentPricePoint = Point(graphPosX[task]!![type] + currentPixel.roundToInt(), graphPosY[task]!![type] + (graphSizeY[task]!![type] * (maxPrice - currentPrice!!.close) / (maxPrice - minPrice)).roundToInt())
                    canvas.drawLine(graphArray[0]!!.x.toFloat(), graphArray[0]!!.y.toFloat(), currentPricePoint.x.toFloat(), currentPricePoint.y.toFloat(), graphLineTotal)
                    currentPixel += pixelInterval
                }
            }
//<-------------------------------------------------------------------------------------------------Graph Target
            canvas.save()
            canvas.rotate(-90f, 0f, 0f)
            textBrush = getTextBrush(epicValueGraphColor[task]!!, epicValueGraphSize[task]!![type].pxTo(), fontName, Typeface.BOLD, epicValueGraphScale[task]!![type])
            outputLength = getTextWidth(targetDateString, textBrush)
            canvas.drawText(targetDateString, -epicValueGraphPosY[task]!![type] - outputLength / 2, epicValueGraphPosX[task]!![type] + epicValueGraphSize[task]!![type], textBrush)
            canvas.restore()
//<-------------------------------------------------------------------------------------------------Graph Japanese
            var specialPoint: Point? = null
            if (task in JapaneseCandleList) {
                if (!specialPriceArray.isNullOrEmpty()) {
                    //    Log.i("JAPANESE", "<${specialPriceArray.size}>")
                    var offsetX: Int = 0
                    var specialPixelInterval: Float = 0f
                    var JapaneseCandleSize: Float = 0f
                    var taskMaxPrice: Float = 0f
                    var taskMinPrice: Float = 0f
                    var taskResize: Boolean = false
                    var graphPrice: Boolean = false
                    when (task) {
                        _PARAM2, _PARAM3 -> {
                            //    Log.i("SET", "VALUE")
                            taskResize = true
                            graphPrice = true
                            val taskCount = if (type % 2 == 1) specialPriceArray.size else (specialPriceArray.size / 2 + specialPriceArray.size % 2)
                            if (taskCount > 0)
                                specialPixelInterval = (arraySpecialGraph[task]!![type]) / taskCount
                            else
                                specialPixelInterval = 4f
                            JapaneseCandleSize = specialPixelInterval - 1f
                            offsetX = graphPosX[task]!![type] + (graphSizeX[task]!![type] - totalOffset).toInt()
                            currentPixel = JapaneseCandleSize / 2 + 0.5f + (taskCount - 1) * specialPixelInterval
                            try {
                                taskMaxPrice = specialPriceArray.maxByOrNull { it!!.high }!!.high
                                taskMinPrice = specialPriceArray.minByOrNull { it!!.low }!!.low
                            } catch (e: Exception) {
                                taskMaxPrice = 0f
                                taskMinPrice = 0f
                            }
                            currentPriceOutput = specialPriceArray[0]!!.close
                        }
                        _PARAM4, _PARAM5 -> {
                            if (totalMoonLength != 0)
                                specialPixelInterval = totalOffset.toFloat() / totalMoonLength
                            else
                                specialPixelInterval = 4f
                            JapaneseCandleSize = specialPixelInterval - 1f
                            offsetX = graphPosX[task]!![type] + (graphSizeX[task]!![type] - totalOffset).toInt()
                            currentPixel = JapaneseCandleSize / 2 + (specialPriceArray.size - 1) * specialPixelInterval
                            try {
                                taskMaxPrice = specialPriceArray.maxByOrNull { it!!.high }!!.high
                                taskMinPrice = specialPriceArray.minByOrNull { it!!.low }!!.low
                            } catch (e: Exception) {
                                taskMaxPrice = 0f
                                taskMinPrice = 0f
                            }
                            currentPriceOutput = taskPrice
                        }
                    }

                    val graphOffsetX = (graphPosX[task]!![type] + graphSizeX[task]!![type] + epicValueGraphSize[task]!![type] + 2f).toInt()
                    var graphCurrentPixel = graphPosY[task]!![type].toFloat()

                    var intervalY: Float
                    var graphRange: Float
                    if (lineCount > 1) {
                        intervalY = (taskMaxPrice - taskMinPrice) / (lineCount - 1)
                        graphRange = (graphSizeY[task]!![type] - 1f) / (lineCount - 1)
                    } else {
                        intervalY = 0f
                        graphRange = 0f
                    }

                    if (graphPrice) {
                        var yValue = taskMaxPrice
                        var yColor = 0
                        var formatLength: Int = 0
                        when {
                            taskMaxPrice < 1f -> formatLength = 4
                            taskMaxPrice >= 1f && taskMaxPrice < 25f -> formatLength = 3
                            taskMaxPrice >= 25f && taskMaxPrice < 100f -> formatLength = 2
                            taskMaxPrice >= 100f && taskMaxPrice < 1000f -> formatLength = 1
                        }

                        for (s in 0 until lineCount) {
                            canvas.drawLine(graphOffsetX.toFloat(), graphCurrentPixel, arraySpecialGraphText[task]!![type] - 1f, graphCurrentPixel, if (yColor % lineSpecial == 0) axisLinePaint1 else axisLinePaint2)
                            if (yColor % lineSpecial == 0)
                                canvas.drawText("%.${formatLength}f".format(yValue), arraySpecialGraphText[task]!![type], graphCurrentPixel + dataValueLinePaint1.textSize / 2 - 1f, dataValueLinePaint1)
                            graphCurrentPixel += graphRange
                            yValue -= intervalY
                            yColor += 1
                        }
                    }

                    try {
                        var k = 0
                        while (k < specialPriceArray.size) {
                            val taskCandle = if (taskResize && !(k == 0 && specialPriceArray.size % 2 == 1) && type % 2 == 0 && k + 1 < specialPriceArray.size) JapaneseCandleMerge(specialPriceArray[k]!!, specialPriceArray[k + 1]!!) else specialPriceArray[k]!!
                            canvas.JapaneseCandle(taskCandle, offsetX + currentPixel.roundToInt(), graphPosY[task]!![type], JapaneseCandleSize, graphSizeY[task]!![type], taskMinPrice, taskMaxPrice)
                            currentPixel -= specialPixelInterval
                            k += 2 - (if (taskResize && !(k == 0 && specialPriceArray.size % 2 == 1)) type % 2 else 1)
                        }
                    } catch (e: Exception) { }
                }
                else {
                    when (task) {
                        _PARAM2, _PARAM3 -> {
                            if (!priceArray.isNullOrEmpty())
                                currentPriceOutput = priceArray[0].close
                        }
                        _PARAM4, _PARAM5 -> {
                            currentPriceOutput = taskPrice
                        }
                    }
                }
            }
//<-------------------------------------------------------------------------------------------------Graph Special
            if (task in specialList1) {
                var specialPointInit: Boolean = false
                if (!graphArray.isNullOrEmpty()) {
                    if (graphArray[0] != null) {
                        specialPoint = Point(graphArray[0]!!.x, graphArray[0]!!.y)
                        specialPointInit = true
                    }
                }
                if (!specialPointInit) {
                    specialPoint = Point(graphPosX[task]!![type], graphPosY[task]!![type])
                }

                if (!specialPriceArray.isNullOrEmpty()) {
                    //    Log.i("SPECIALPRICEARRAY", "${specialPriceArray.size}/${specialPriceArray[0]!!.close}")
                    var graphSpecialArray: Array<Point?>? = null
                    var offsetX: Int = 0
                    var specialPixelInterval: Float = 0f
                    var specialArrayLine: Paint? = null
                    var taskMaxPrice: Float = 0f
                    var taskMinPrice: Float = 0f
                    when (task) {
                    /*    _PARAM2, _PARAM3 -> {
                            graphSpecialArray = arrayOfNulls<Point>(specialPriceArray.size)
                            specialPixelInterval = specialLength * arraySpecialGraph[task]!![type] / specialPriceArray.size / tradeRange[objectType]!!
                            offsetX = graphPosX[task]!![type] + graphSizeX[task]!![type].toInt()
                            currentPixel = 0f
                            if (specialPriceArray.size > 1) {
                                taskMaxPrice = maxSpecialPrice
                                taskMinPrice = minSpecialPrice
                            } else {
                                if (specialPriceArray[0] != null) {
                                    taskMaxPrice = specialPriceArray[0]!!.high
                                    taskMinPrice = specialPriceArray[0]!!.low
                                } else {
                                    taskMaxPrice = 65536f
                                    taskMinPrice = 0f
                                }
                            }
                            specialArrayLine = graphLineSpecial
                        }
                        _PARAM4, _PARAM5 -> {
                            specialPixelInterval = totalOffset.toFloat() / totalMoonLength
                            offsetX = graphPosX[task]!![type] + (graphSizeX[task]!![type] - totalOffset).toInt() - 1
                            graphSpecialArray = arrayOfNulls<Point>(specialPriceArray.size)
                            currentPixel = 0f
                            if (specialPriceArray.size > 2) {
                                taskMaxPrice = maxSpecialPrice
                                taskMinPrice = minSpecialPrice
                            } else if (specialPriceArray.size == 2) {
                                if (specialPriceArray[0] != null && specialPriceArray[1] != null) {
                                    val taskItem = specialPriceArray[1]!!.close
                                    taskMaxPrice = if (taskItem > specialPriceArray[0]!!.high) taskItem else specialPriceArray[0]!!.high
                                    taskMinPrice = if (taskItem < specialPriceArray[0]!!.low) taskItem else specialPriceArray[0]!!.low
                                } else {
                                    taskMaxPrice = 65536f
                                    taskMinPrice = 0f
                                }
                                //    Log.i("PRICE", "${specialPriceArray[1]!!.close}/${taskMinPrice}/${taskMaxPrice}/${specialPriceArray[0]!!.close}")
                            }
                            specialArrayLine = graphLineSpecial
                        }   */
                        _PARAM6, _PARAM7, _PARAM8 -> {
                            val specialTaskMarket = if (priceArrayLength >= marketCycle && marketCycle > 0) true else false
                            //    Log.i("LENGTH", "${graphSizeX[task]!![type]}/${specialPriceArray.size}/${totalMoonLength}")
                            val sizeX = if (taskMarket) graphSizeX[task]!![type] - arraySpecialGraph[task]!![type].roundToInt() else graphSizeX[task]!![type]
                            if (totalMoonLength != 1)
                                specialPixelInterval = sizeX / (totalMoonLength - 1)
                            else
                                specialPixelInterval = 0f
                            graphSpecialArray = arrayOfNulls<Point>(specialPriceArray.size + if (specialTaskMarket) 1 else 0)
                            //    Log.i("PIXEL", "${specialPixelInterval}/${specialPixelInterval * specialPriceArray.size}")
                            if (taskMarket && graphSpecialArray.isNotEmpty()) {
                                offsetX = graphPosX[task]!![type] + arraySpecialGraph[task]!![type].roundToInt()
                                graphSpecialArray[graphSpecialArray.size - 1] = Point(offsetX, specialPoint!!.y)
                                currentPixel = specialPixelInterval
                            } else {
                                offsetX = graphPosX[task]!![type]
                                currentPixel = 0f
                            }
                            taskMaxPrice = maxPrice
                            taskMinPrice = minPrice
                            specialArrayLine = graphLineTotal
                        }
                    }

                    specialPoint = null
                    if (!graphSpecialArray.isNullOrEmpty() && (taskMaxPrice - taskMinPrice) != 0f) {
                        var k = specialPriceArray.size - 1
                        if (k < graphSpecialArray.size) {
                            try {
                                while (k >= 0) {
                                //    Log.i("POINT", "${specialPriceArray[k]!!.date}/${specialPriceArray[k]!!.close}")
                                    graphSpecialArray[k] = Point(offsetX + currentPixel.roundToInt(), graphPosY[task]!![type] + (graphSizeY[task]!![type] * (taskMaxPrice - specialPriceArray[k]!!.close) / (taskMaxPrice - taskMinPrice)).roundToInt())
                                    currentPixel += specialPixelInterval
                                    k--
                                }
                                specialPoint = Point(graphSpecialArray[0]!!.x, graphSpecialArray[0]!!.y)
                            } catch (e: Exception) {
                                specialPoint = Point(graphPosX[task]!![type], graphPosY[task]!![type])
                            }

                            if (specialArrayLine != null) {
                                try {
                                    for (s in k + 1 until graphSpecialArray.size - 1) {
                                        canvas.drawLine(graphSpecialArray[s]!!.x.toFloat(), graphSpecialArray[s]!!.y.toFloat(), graphSpecialArray[s + 1]!!.x.toFloat(), graphSpecialArray[s + 1]!!.y.toFloat(), specialArrayLine)
                                    }
                                } catch (e: Exception) {
                                }
                            }
                        }
                    }
                }
                //    Log.i("SPECIAL", "${specialPoint.x}/${specialPoint.y}")
            }
//<-------------------------------------------------------------------------------------------------GraphCurrentMomentLine
            when (task) {
                _PARAM2, _PARAM3 -> {
                }
                _PARAM4, _PARAM5 -> {
                    if (specialPoint != null)
                        canvas.DrawPricePoint(specialPoint.x.toFloat(), specialPoint.y.toFloat(), getPointBrush(graphPointColor[task]!!))
                }
                _PARAM6, _PARAM7, _PARAM8 -> {
                    if (specialPoint != null) {
                        canvas.drawLine(specialPoint.x.toFloat(), graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], specialPoint.x.toFloat(), graphPosY[task]!![type] + graphSizeY[task]!![type].toFloat(), graphLineCurrentMoment)
                        canvas.DrawPricePoint(specialPoint.x.toFloat(), specialPoint.y.toFloat(), getPointBrush(graphPointColor[task]!!))
                    }
                }
                else -> {
                    if (currentPricePoint != null) {
                        canvas.DrawPricePoint(currentPricePoint.x.toFloat(), currentPricePoint.y.toFloat(), getPointBrush(graphPointColor[task]!!))
                    }
                    else if (!graphArray.isNullOrEmpty()) {
                        if (graphArray[0] != null)
                            canvas.DrawPricePoint(graphArray[0]!!.x.toFloat(), graphArray[0]!!.y.toFloat(), getPointBrush(graphPointColor[task]!!))
                    }
                }
            }
//<-------------------------------------------------------------------------------------------------GraphSpecialNewYearLine
            when (task) {
                _PARAM6, _PARAM7, _PARAM8 -> {
                    if (specialPriceArray != null) {
                        //    val compareLength = (if (taskMarket) -1 * marketCycle else 0) + priceArrayLength + (if (specialPriceArray != null) specialPriceArray.size else 0) + totalMoonLength
                        val compareLength = totalMoonLength
                        if (compareLength >= marketES[objectType]!!) {
                            //    Log.i("COMPARELENGTH", "<${marketES}/${specialPriceArray.size}/${compareLength}>")
                            val marketOffset = if (taskMarket) arraySpecialGraph[task]!![type] else 0f
                            var lineOffset: Float = 0f
                            if (totalMoonLength > 0)
                                lineOffset = marketOffset + (graphSizeX[task]!![type] - marketOffset) * marketES[objectType]!! / totalMoonLength
                            val lineType = graphLineNewYear2
                            canvas.drawLine(graphPosX[task]!![type] + lineOffset, graphPosY[task]!![type].toFloat() + arrayGraphOffsetY[task]!![type], graphPosX[task]!![type] + lineOffset, graphPosY[task]!![type] + graphSizeY[task]!![type], lineType)
                        }
                    }
                }
            }
//<-------------------------------------------------------------------------------------------------Scale Value
            var maxFontLength: Int = 0
            when (task) {
                _PARAM5, _PARAM8 -> {
                    textBrush = getTextBrush(priceValueColor[task]!![0], priceValueSizeFont[task]!![type].pxTo(), fontName, Typeface.BOLD)
                    for (s in 0 until epicRange[task]!!) {
                        val outputValue = "%.${0}f".format(paramList[s])
                        outputLength = getTextWidth(outputValue, textBrush)
                        if (outputLength > maxFontLength)
                            maxFontLength = outputLength
                    }
                }
            }
            if (task in priceValuePosX) {
                var priceTaskFont: Float = priceValueSizeFont[task]!![type]
                if (!(task in specialList3)) {
                    for (s in 0 until epicRange[task]!!) {
                        val taskStr = "%.${param1}f".format(paramList[s])
                        val hasPoint = if (taskStr.contains(".")) 1 else 0
                        if (taskStr.length - hasPoint > 4) {
                            priceTaskFont *= 0.9f - 0.075f * hasPoint
                            break
                        }
                    }
                }

                var currentPixelX: Float = 0f
                var currentPixelY: Float = 0f
                var pixelIntervalX: Float = 0f
                var pixelIntervalY: Float = 0f
                if (epicRange[task]!! > 1) {
                    pixelIntervalX = priceValueSizeX[task]!![type] / (epicRange[task]!! - 1)
                    pixelIntervalY = priceValueSizeY[task]!![type] / (epicRange[task]!! - 1)
                }

                for (s in 0 until epicRange[task]!!) {
                    var outputValue: String
                    textBrush = getTextBrush(priceValueColor[task]!![s], priceTaskFont.pxTo(), fontName, Typeface.BOLD)
                    when (task) {
                        _PARAM5, _PARAM8 -> {
                            outputValue = paramList[s].roundToInt().toString()
                            outputLength = getTextWidth(outputValue, textBrush) + maxFontLength
                        }
                        else -> {
                            outputValue = "%.${param1}f${typeCurrency[objectType]}".format(paramList[s])
                            outputLength = getTextWidth(outputValue, textBrush)
                        }
                    }
                    canvas.drawText(outputValue, priceValuePosX[task]!![type] + currentPixelX - outputLength / 2, priceValuePosY[task]!![type] + priceTaskFont / 2 + currentPixelY, textBrush)
                    currentPixelX += pixelIntervalX
                    currentPixelY += pixelIntervalY
                }
            }
//<-------------------------------------------------------------------------------------------------Current Price
            var outputPrice = "%.${param2}f${typeCurrency[objectType]}".format(currentPriceOutput)
            textBrush = getTextBrush(currentPriceColor[task]!!, currentPriceSize[task]!![type].pxTo(), fontName, Typeface.BOLD)
            outputLength = getTextWidth(outputPrice, textBrush)
            when (task) {
                _PARAM5 -> {
                    if (!graphArray.isNullOrEmpty()) {
                        if (graphArray[0] != null)
                            canvas.drawText(outputPrice, currentPricePosY[task]!![type], graphArray[0]!!.y.toFloat() + currentPriceSize[task]!![type] / 2, textBrush)
                    }
                }
                else -> {
                    canvas.drawText(outputPrice, stockAxisX[type]!! - outputLength / 2 + 1, currentPricePosY[task]!![type] + currentPriceSize[task]!![type], textBrush)
                }
            }
        }
//<-------------------------------------------------------------------------------------------------------------Global
        var TuringHeight: Float = 0f
        var outputTuring: Bitmap? = null
        try {
            if (resourceTuring != null) {
                outputTuring = BitmapFactory.decodeByteArray(resourceTuring, 0, resourceTuring.size).copy(Bitmap.Config.ARGB_8888, true)
                if (outputTuring != null)
                    TuringHeight = outputTuring.height.toFloat()
            }
        }
        catch (e: Exception) { }

        var outputMarket: Bitmap? = null
        var outputMarketWidth: Float = 0f
        var outputMarketHeight: Float = 0f
        var outputIntraHeight: Float = 0f
        var outputIntraOffset: Float = 0f
//<-------------------------------------------------------------------------------------------------------------Intra
    //    Log.i("PARAM", "$param4/$type/$objectType")
    //    else {
            if (resourceMarket != null) {
                try {
                    outputMarket = BitmapFactory.decodeByteArray(resourceMarket, 0, resourceMarket.size).copy(Bitmap.Config.ARGB_8888, true)
                    if (outputMarket != null) {
                        outputMarketWidth = outputMarket.width.toFloat()
                        outputMarketHeight = outputMarket.height.toFloat()
                    }
                    else {
                        throw Exception("Market")
                    }
                }
                catch (e: Exception) {
                    outputMarketWidth = bitmap.width.toFloat()
                }
            }
            else {
                outputMarketWidth = bitmap.width.toFloat()
            }
    //    }
        val stockMultiHorizontal1 = param4 == intraValue && (inputType == 4 || inputType == 5) && objectType != _OTHER
        val stockMultiHorizontal2 = param4 == intraValue && inputType == 6 && objectType != _OTHER
        if (stockMultiVertical) {
            outputMarketWidth = 480f
            if (type < 2) {
                if (sector > 1) {
                    outputMarketHeight = 0f
                    outputIntraHeight = 360f - bitmap.height.toFloat()
                }
                else {
                    outputMarketHeight = 396f - bitmap.height.toFloat()
                }
            } else {
                if (sector > 1) {
                    outputMarketHeight = 0f
                    outputIntraHeight = 375f - bitmap.height.toFloat()
                    outputIntraOffset = 375f - bitmap.height.toFloat()
                } else {
                    outputMarketHeight = 411f - bitmap.height.toFloat()
                }
            }
        }
        if (specialStockLayout && globalPosY > 0) {
        //    Log.i("STOCKSPECIAL1", "PosX=0/PosY=$globalPosY/SizeX=${width}/SizeY=$globalSizeY/SourceY=${bitmap.height}")
            if (globalPosY + globalSizeY > bitmap.height)
                globalSizeY = bitmap.height - globalPosY
            bitmap = Bitmap.createBitmap(bitmap, 0, globalPosY, bitmap.width, globalSizeY)
            if (bitmap == null)
                return Pair(null, "")
        }
        if (specialOtherLayout && globalPosY > 0 && type == 0) {
            outputMarketHeight -= 15f
        }
    //    Log.i("WIDTH", "${bitmap.width}/${outputMarketWidth}")
    //    Log.i("HEIGHT", "${bitmap.height}/${TuringHeight}/${outputMarketHeight}/${outputIntraHeight}")
//<-------------------------------------------------------------------------------------------------------------Canvas
        val bitmapResultWidth = outputMarketWidth.toInt()
        val bitmapResultHeight = (TuringHeight + outputMarketHeight + outputIntraHeight).toInt() + bitmap.height
        if (bitmapResultWidth == 0 || bitmapResultHeight == 0)
            return Pair(null, "")

        var bitmapResult: Bitmap? = null
        try {
            bitmapResult = Bitmap.createBitmap(bitmapResultWidth, bitmapResultHeight, Bitmap.Config.ARGB_8888)
        }
        catch (e: Exception) {
            return Pair(null, "")
        }

        if (bitmapResult == null)
            return Pair(null, "")
        val canvasResult = Canvas(bitmapResult)
        var bitmapOffsetX: Float = 0f
        if (type == 2 && sector % 2 != 0) {
            bitmapOffsetX = (bitmapResult.width - bitmap.width).toFloat()
        }
        if (stockMultiVertical && image == 2) {
            bitmapOffsetX = (bitmapResult.width - bitmap.width).toFloat() - bitmapOffsetX
        }
        var bitmapOffsetY: Float = outputIntraOffset
        var marketOffsetY: Float = bitmap.height.toFloat()
        var TuringOffsetY: Float = bitmap.height.toFloat() + outputMarketHeight
        if (sector < 2 && (!stockMultiHorizontal1 || stockMultiHorizontal2) || sector % 2 == 0 && stockMultiHorizontal1) {
            bitmapOffsetY = TuringHeight + outputMarketHeight
            marketOffsetY = TuringHeight
            TuringOffsetY = 0f
        }
        if (stockMultiVertical && outputMarket != null) {
            if (type < 2) {
                if (sector > 1) {
                    bitmapOffsetY -= 8f
                    marketOffsetY = intraSizeY[_1LV]!! - outputMarket.height.toFloat() + 15f
                }
                else {
                    bitmapOffsetY += 26f
                    marketOffsetY = -15f
                }
            }
            else {
                if (sector > 1) {
                    bitmapOffsetY -= 23f
                    marketOffsetY = intraSizeY[_1LV]!! - outputMarket.height.toFloat() + 15f
                }
                else {
                    bitmapOffsetY += 31f
                    marketOffsetY = 0f
                }
            }
        }
        if (specialOtherLayout && globalPosY > 0) {
            bitmapOffsetY = -400f
        }
    //    Log.i("BITMAPRESULT", "${bitmapResult.width}/${bitmapResult.height}")
    //    Log.i("SECTOR", "${sector}")

        var specialMarketOffset: Float = 0f
        canvasResult.drawBitmap(bitmap, bitmapOffsetX, bitmapOffsetY, null)
        if (outputMarket != null) {
            when {
                inputType == 4 -> {
                    if (sector == 1 || sector == 2)
                        specialMarketOffset = -1f
                }
                inputType == 5 && image % 2 == 1 && (sector == 1 || sector == 2) -> {
                    specialMarketOffset = -1f
                }
                inputType == 6 && image % 2 == 1 && sector % 2 == 1 -> {
                    specialMarketOffset = -1f
                }
            }
        //    Log.i("MARKET", "$specialMarketOffset")
            canvasResult.drawBitmap(outputMarket, specialMarketOffset, marketOffsetY, null)
        }
        if (outputTuring != null)
            canvasResult.drawBitmap(outputTuring, 0f, TuringOffsetY, null)
    /*    if (stockMultiVertical) {
            var taskPos1: Float
            var taskPos2: Float
            if (type < 2) {
                if (sector < 2) {
                //    taskPos1
                //    taskPos2 = outputMarketHeight
                } else {
                //    taskPos1 = 0f
                //    taskPos2 = bitmapResult.height.toFloat()
                }
            //    canvasResult.drawRect(0f, taskPos1, outputMarketWidth, taskPos2, getPointBrush(scaleColor[objectType]!!))
            } else {
                if (sector < 2) {
                    taskPos1 = 0f
                    taskPos2 = outputMarketHeight
                    canvasResult.drawRect(0f, taskPos1, outputMarketWidth, taskPos2, getPointBrush(superColor[objectType]!!))
                }

                var taskPos3: Float
                var taskPos4: Float
                if (sector % 2 != 0) {
                    taskPos3 = 0f
                    taskPos4 = bitmapResult.width.toFloat() - bitmap.width.toFloat()
                } else {
                    taskPos3 = bitmap.width.toFloat()
                    taskPos4 = bitmapResult.width.toFloat()
                }
                canvasResult.drawRect(taskPos3, 0f, taskPos4, 400f, getPointBrush(turingColor))
            }
        }   */
//<-------------------------------------------------------------------------------------------------Object
        if (resourceObject != null) {
            try {
                when (objectType) {
                    _CRYPTO -> {
                        var outputObject: Bitmap? = BitmapFactory.decodeByteArray(resourceObject, 0, resourceObject.size).copy(Bitmap.Config.ARGB_8888, true)
                        if (outputObject != null) {
                            var taskSizeX: Int = 0
                            var taskSizeY: Int = 0
                            if (!stockMultiVertical) {
                                taskSizeX = ObjectSizeX[objectType]!![type]
                                taskSizeY = ObjectSizeY[objectType]!![type]
                            }
                            else {
                                if (outputMarket != null) {
                                    taskSizeX = bitmapResult.width / 2
                                    taskSizeY = outputMarket.height - 15
                                }
                            }
                            outputObject = Bitmap.createScaledBitmap(outputObject, taskSizeX, taskSizeY, false)

                            var ObjectOffsetPosX: Float = 0f
                            var ObjectOffsetPosY: Float = 0f
                            if (!stockMultiVertical && !stockMultiHorizontal1 && !stockMultiHorizontal2) {
                                ObjectOffsetPosX = if (sector % 2 == 0) ObjectPosX[objectType]!![type] else (bitmapResult.width - ObjectPosX[objectType]!![type] - ObjectSizeX[objectType]!![type])
                                if (type < 3) {
                                    ObjectOffsetPosY = (if (sector > 1) bitmap.height.toFloat() else 0f) + ObjectPosY[objectType]!![type]
                                } else {
                                    ObjectOffsetPosY = if (sector > 1) (bitmap.height.toFloat() + ObjectPosY[objectType]!![type]) else ((TuringHeight + outputMarketHeight - ObjectSizeY[objectType]!![type] - ObjectPosY[objectType]!![type]))
                                }
                            }
                            else {
                                when {
                                    inputType < 4 -> {
                                        ObjectOffsetPosX = if (sector % 2 == 0 || type < 2) bitmapResult.width / 2f else (bitmapResult.width - bitmapResult.width / 2f - taskSizeX)
                                        ObjectOffsetPosY = marketOffsetY + if (sector < 2) 15f else 0f
                                    }
                                    inputType == 4 && sector % 2 == 0 -> {
                                    //    Log.i("CRYPTO", "FLOAT SL")
                                        if (sector == 0)
                                            ObjectOffsetPosX = bitmapResult.width / 2f - 1f
                                        else
                                            ObjectOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                    }
                                    inputType == 4 && sector % 2 == 1 -> {
                                    //    Log.i("CRYPTO", "FLOAT SR")
                                        if (sector == 1)
                                            ObjectOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                        else
                                            ObjectOffsetPosX = bitmapResult.width / 2f - 1f
                                        ObjectOffsetPosY = marketOffsetY
                                    }
                                    inputType == 5 && sector == 0 -> {
                                    //    Log.i("CRYPTO", "TARGET SL")
                                        ObjectOffsetPosX = bitmapResult.width / 2f - 1f
                                    }
                                    inputType == 5 && sector == 1 -> {
                                    //    Log.i("CRYPTO", "TARGET SR")
                                        ObjectOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                        ObjectOffsetPosY = marketOffsetY
                                    }
                                    inputType == 5 && sector == 2 -> {
                                    //    Log.i("CRYPTO", "TARGET SL")
                                        ObjectOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                    }
                                    inputType == 5 && sector == 3 -> {
                                    //    Log.i("CRYPTO", "TARGET SR")
                                        ObjectOffsetPosX = bitmapResult.width / 2f - 1f
                                        ObjectOffsetPosY = marketOffsetY
                                    }
                                    inputType == 6 && sector == 0 -> {
                                    //    Log.i("CRYPTO", "TASK LEFT SL")
                                        ObjectOffsetPosX = bitmapResult.width / 2f - 1f
                                    }
                                    inputType == 6 && sector == 2 -> {
                                    //    Log.i("CRYPTO", "TASK LEFT SR")
                                        ObjectOffsetPosX = bitmapResult.width / 2f - 1f
                                        ObjectOffsetPosY = marketOffsetY
                                    }
                                    inputType == 6 && sector == 1 -> {
                                    //    Log.i("CRYPTO", "TASK RIGHT SL")
                                        ObjectOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                    }
                                    inputType == 6 && sector == 3 -> {
                                    //    Log.i("CRYPTO", "TASK RIGHT SR")
                                        ObjectOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                        ObjectOffsetPosY = marketOffsetY
                                    }
                                }
                            }
                            canvasResult.drawBitmap(outputObject!!, ObjectOffsetPosX, ObjectOffsetPosY, null)
                        }
                    }
                }
            }
            catch (e: Exception) { }
        }
//<-------------------------------------------------------------------------------------------------Moon
        if (resourceEpic != null) {
            when (task) {
                _PARAM4, _PARAM5 -> {
                    try {
                        var outputEpic: Bitmap? = BitmapFactory.decodeByteArray(resourceEpic, 0, resourceEpic.size).copy(Bitmap.Config.ARGB_8888, true)
                        if (outputEpic != null) {
                            var taskEpicOffsetX: Float = 0f
                            if (param4 == 0 && inputType == 2 && sector % 2 == 1 || param4 == 1 && inputType == 2 && (image < 1 && sector % 2 == 1 || image == 2 && sector % 2 == 0))
                                taskEpicOffsetX = 60f
                        //    Log.i("MOON", "Param4=$param4/InputType=$inputType/Sector=$sector")

                            var taskEpicSizeX: Int = 0
                            var taskEpicSizeY: Int = 0
                            var taskEpicPosX: Float = 0f
                            var taskEpicPosY: Float = 0f
                            if (inputType < 4) {
                                taskEpicSizeX = epicSizeX[task]!![type]
                                taskEpicSizeY = epicSizeY[task]!![type]
                                taskEpicPosX = epicPosX[task]!![type] + taskEpicOffsetX
                                taskEpicPosY = epicPosY[task]!![type]
                            }
                            else {
                                var logoType: Int = 0
                                when (inputType) {
                                    5 -> logoType = 1 + if (sector > 1) 1 else 0
                                    6 -> logoType = 3 + if (sector % 2 == 1) 1 else 0
                                }
                                taskEpicSizeX = floatEpicSizeX[task]!![logoType]
                                taskEpicSizeY = floatEpicSizeY[task]!![logoType]
                                taskEpicPosX = floatEpicPosX[task]!![logoType]
                                taskEpicPosY = floatEpicPosY[task]!![logoType]
                            }
                            outputEpic = Bitmap.createScaledBitmap(outputEpic, taskEpicSizeX, taskEpicSizeY, false)
                            val epicOffsetPosY = bitmapOffsetY + taskEpicPosY
                            canvasResult.drawBitmap(outputEpic!!, taskEpicPosX, epicOffsetPosY, null)
                        }
                    } catch (e: Exception) {
                    }
                }
                _PARAM6, _PARAM7, _PARAM8 -> {
                    try {
                        var outputEpic: Bitmap? = BitmapFactory.decodeByteArray(resourceEpic, 0, resourceEpic.size).copy(Bitmap.Config.ARGB_8888, true)
                        if (outputEpic != null) {
                            var taskSizeX: Int = 0
                            var taskSizeY: Int = 0
                            if (!stockMultiVertical && !stockMultiHorizontal1 && !stockMultiHorizontal2) {
                                taskSizeX = epicSizeX[task]!![type]
                                taskSizeY = epicSizeY[task]!![type]
                            }
                            else {
                            //    taskSizeX = bitmapResult.width / 2
                                taskSizeX = outputEpic.width
                                taskSizeY = outputEpic.height
                            //    if (inputType < 4)
                            //        taskSizeY -= 15
                            }
                            outputEpic = Bitmap.createScaledBitmap(outputEpic, taskSizeX, taskSizeY, false)

                            var epicOffsetPosX: Float = 0f
                            var epicOffsetPosY: Float = 0f
                            if (!stockMultiVertical && !stockMultiHorizontal1 && !stockMultiHorizontal2) {
                                epicOffsetPosX = if (sector % 2 == 0) epicPosX[task]!![type] else (bitmapResult.width - epicPosX[task]!![type] - epicSizeX[task]!![type])
                                if (type < 3) {
                                    epicOffsetPosY = (if (sector > 1) bitmap.height.toFloat() else 0f) + epicPosY[task]!![type]
                                } else {
                                    epicOffsetPosY = if (sector > 1) (bitmap.height.toFloat() + epicPosY[task]!![type]) else ((TuringHeight + outputMarketHeight - epicSizeY[task]!![type] - epicPosY[task]!![type]))
                                }
                            }
                            else {
                                when {
                                    inputType < 4 -> {
                                        epicOffsetPosX = if (sector % 2 == 0 || type < 2) bitmapResult.width / 2f else (bitmapResult.width - bitmapResult.width / 2f - taskSizeX)
                                        epicOffsetPosY = marketOffsetY + if (sector < 2) 15f else 0f
                                    }
                                    inputType == 4 && sector % 2 == 0 -> {
                                        if (sector == 0)
                                            epicOffsetPosX = bitmapResult.width / 2f - 1f
                                        else
                                            epicOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                    }
                                    inputType == 4 && sector % 2 == 1 -> {
                                        if (sector == 1)
                                            epicOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                        else
                                            epicOffsetPosX = bitmapResult.width / 2f - 1f
                                        epicOffsetPosY = marketOffsetY
                                    }
                                    inputType == 5 && sector == 0 -> {
                                        epicOffsetPosX = bitmapResult.width / 2f - 1f
                                    }
                                    inputType == 5 && sector == 1 -> {
                                        epicOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                        epicOffsetPosY = marketOffsetY
                                    }
                                    inputType == 5 && sector == 2 -> {
                                        epicOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                    }
                                    inputType == 5 && sector == 3 -> {
                                        epicOffsetPosX = bitmapResult.width / 2f - 1f
                                        epicOffsetPosY = marketOffsetY
                                    }
                                    inputType == 6 && sector == 0 -> {
                                        epicOffsetPosX = bitmapResult.width / 2f - 1f
                                    }
                                    inputType == 6 && sector == 2 -> {
                                        epicOffsetPosX = bitmapResult.width / 2f - 1f
                                        epicOffsetPosY = marketOffsetY
                                    }
                                    inputType == 6 && sector == 1 -> {
                                        epicOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                    }
                                    inputType == 6 && sector == 3 -> {
                                        epicOffsetPosX = bitmapResult.width - bitmapResult.width / 2f - taskSizeX - 1f
                                        epicOffsetPosY = marketOffsetY
                                    }
                                }
                            }
                            //    Log.i("OFFSET", "${MoonOffsetPosX}/${MoonOffsetPosY}/TASK=$type/MOON=${MoonPosY[task]!![type]}")
                            canvasResult.drawBitmap(outputEpic!!, epicOffsetPosX, epicOffsetPosY, null)
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }
        if ((specialStockLayout || specialOtherLayout) && globalPosX > 0) {
        //    Log.i("STOCKSPECIAL2", "PosX=$globalPosX/PosY=0/SizeX=$globalSizeX/SizeY=${bitmapResult.height}")
            if (globalPosX + globalSizeX > bitmap.width)
                globalSizeX = bitmap.width - globalPosX
            bitmapResult = Bitmap.createBitmap(bitmapResult, globalPosX, 0, globalSizeX, bitmapResult.height)
        }
        if (specialOtherLayout && globalPosY > 0) {
            canvasResult.drawBitmap(bitmap, 0f, 400f, null)
        }
        if (image > 0 && bitmapResult != null) {
        //    Log.i("ROTATE", "${image * 90f}")
            val matrix = Matrix()
            matrix.postRotate(image * 90f)
            bitmapResult = Bitmap.createBitmap(bitmapResult, 0, 0, bitmapResult.width, bitmapResult.height, matrix, true)
        }
//<-------------------------------------------------------------------------------------------------
        //    Log.i("IMAGE", "COMPLETE")
    //    Log.i("BITMAPRESULT", "${bitmapResult.width}/${bitmapResult.height}")
        return Pair(bitmapResult, dateText)
    }

    private fun getTextBrush(taskColor: Int, sizeStr: Float, fontStyle1: String, fontStyle2: Int, scaleStr: Float = 1.0f) : Paint {
        val taskScale = if (scaleStr > 0f && scaleStr <= 1f) scaleStr else 1.0f
        return Paint().apply {
            color = taskColor
            strokeWidth = 1f
            isAntiAlias = true
            textSize = sizeStr
            textScaleX = taskScale
            typeface = Typeface.create(fontStyle1, fontStyle2)
        }
    }
    private fun getPointBrush(taskColor: Int, width: Float = 1f, special: Int = 0) : Paint {
        val taskPaint: Paint
        if (special == 0) {
            taskPaint = Paint().apply {
                color = taskColor
                strokeWidth = width
            }
        }
        else {
            taskPaint = Paint().apply {
                color = taskColor
                strokeWidth = width
                pathEffect = DashPathEffect(floatArrayOf(special.toFloat(), special.toFloat()), 0f)
            }
        }
        return taskPaint
    }
    private fun getTextWidth(taskStr: String, textBrush: Paint) : Int {
        val result = Rect()
        textBrush.getTextBounds(taskStr, 0, taskStr.length, result)
        return result.width().absoluteValue
    }
    private fun Float.pxTo() : Float {
        return this * 1.35f
    }
    private fun Canvas.JapaneseCandle(taskValue: PricePoint, offsetX: Int, offsetY: Int, sizeX: Float, sizeY: Float, minValue: Float, maxValue: Float) {
        if (maxValue - minValue == 0f)
            return

        val taskColor: Paint
        val onePrice: Float
        val twoPrice: Float
        when {
            taskValue.close > taskValue.open -> {
                taskColor = JapaneseCandleGreen
                onePrice = taskValue.close
                twoPrice = taskValue.open
            }
            else -> {
                taskColor = JapaneseCandleRed
                onePrice = taskValue.open
                twoPrice = taskValue.close
            }
        }
        val pixelOffset: Float = (sizeX - 1) / 2

        val taskPointHigh = Point(offsetX, offsetY + (sizeY * (maxValue - taskValue.high) / (maxValue - minValue)).roundToInt())
        val taskPointLow = Point(offsetX, offsetY + (sizeY * (maxValue - taskValue.low) / (maxValue - minValue)).roundToInt())
        this.drawLine(taskPointHigh.x.toFloat(), taskPointHigh.y.toFloat(), taskPointLow.x.toFloat(), taskPointLow.y.toFloat(), taskColor)

        val taskPointOne = Point(offsetX, offsetY + (sizeY * (maxValue - onePrice) / (maxValue - minValue)).roundToInt())
        val taskPointTwo = Point(offsetX, offsetY + (sizeY * (maxValue - twoPrice) / (maxValue - minValue)).roundToInt())
        this.drawRect(taskPointOne.x.toFloat() - pixelOffset, taskPointOne.y.toFloat(), taskPointTwo.x.toFloat() + pixelOffset, taskPointTwo.y.toFloat() + 1f, taskColor)
    }
    private fun Canvas.DrawPricePoint(posX: Float, posY: Float, taskBrush: Paint) {
        this.drawPoint(posX - 1, posY, taskBrush)
        this.drawPoint(posX, posY + 1, taskBrush)
        this.drawPoint(posX + 1, posY, taskBrush)
        this.drawPoint(posX, posY - 1, taskBrush)
        this.drawPoint(posX, posY, taskBrush)
    }

    companion object {
        fun JapaneseCandleMerge(value1: PricePoint, value2: PricePoint): PricePoint {
            val taskPriceOpen = value2.open
            val taskPriceLow = if (value1.low < value2.low) value1.low else value2.low
            val taskPriceHigh = if (value1.high > value2.high) value1.high else value2.high
            val taskPriceClose = value1.close
            return PricePoint(value1.date, taskPriceOpen, taskPriceLow, taskPriceHigh, taskPriceClose, 0)
        }
    }
}