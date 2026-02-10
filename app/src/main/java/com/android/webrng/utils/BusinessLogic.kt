package com.android.webrng.utils

import com.android.webrng.constants.*
import com.android.webrng.image.DataPoint
import com.android.webrng.image.ImageCreator
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Math.abs
import java.net.HttpURLConnection
import java.net.Socket
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.HashMap


interface IBusinessLogic {
    fun GetGraphArray(stockIndex: String, targetUtcDate: UtcCalendar, api: String, usOffset: Boolean, taskType: Int, objectType: Int): List<DataPoint>
    fun GetGlobalArray(stock_name: String, api: String, objectType: Int): JSONObject?
    fun GetSpecialArray(stock_name: String, api: String, task_type: Int, object_type: Int, usOffset: Boolean, multiTask: Boolean, epicList: Array<String>? = null, task_index: Int? = null): Pair<Array<PricePoint>?, Float>
    fun GetIntraArray(stock_name: String, api: String, object_type: Int, usOffset: Boolean, multiTask: Boolean, taskTimeframe: String, taskSize: Int = 0, reserve: Int = 1, startTime: Long = 0, tradeTime: Long = 0): Pair<Array<PricePoint>?, Float>
    fun GetCurrentPrice(stock_name: String, api: String, object_type: Int, usOffset: Boolean): PricePoint?
    fun ConvertJSON(inputJson: JSONObject, object_type: Int): Pair<Array<PricePoint>, Int>

    fun GetMarketState(api: String, taskList: Array<String>, nameList: Array<String>): CustomList
    fun GetSectorIndustry(): CustomList
    fun GetStockEvaluate(type: Int): CustomList

    fun GetBase(ip: String, requestType: Int): Pair<JSONArray?, String>
    fun SetBase(ip: String, requestType: Int, input: String): String
}

class RealBusinessLogic: IBusinessLogic {
    override fun GetGraphArray(stockIndex: String, targetUtcDate: UtcCalendar, api: String, usOffset: Boolean, taskType: Int, objectType: Int): ArrayList<DataPoint> {
        var timeStampArray: LongArray? = null
        var priceArray: FloatArray? = null
        var startTime: Long = 0
        var tradeTime: Long = 0

        when (objectType) {
            _USA -> {
                when (taskType) {
                    _PARAM1 -> {
                        val postmarketTime = UtcCalendar.getInstance()
                        postmarketTime.set(targetUtcDate.get(UtcCalendar.YEAR), targetUtcDate.get(UtcCalendar.MONTH), targetUtcDate.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
                        postmarketTime.add(UtcCalendar.HOUR_OF_DAY, -5 + if (usOffset) 1 else 0)

                        val weekDay = postmarketTime.get(UtcCalendar.DAY_OF_WEEK)
                        when (weekDay) {
                            1 -> postmarketTime.add(UtcCalendar.DAY_OF_MONTH, -2)
                            7 -> postmarketTime.add(UtcCalendar.DAY_OF_MONTH, -1)
                        }
                        startTime = postmarketTime.timeInMillis / 1000L
                        postmarketTime.add(UtcCalendar.HOUR_OF_DAY, 9)
                        tradeTime = postmarketTime.timeInMillis / 1000L

                        //    Log.i("UNIX", "${startTime.toString()}/${currentMoment.toString()}")
                        //    Log.i("MARKETSTART", "${weekDay}/${UtcDate(startTime * 1000)}")
                        //    Log.i("CURRENTMOMENT", "$tradeTime")
                        var intraPriceArray = GetIntraArray(stockIndex, api, objectType, usOffset, true, "5", 150, 2, startTime, tradeTime).first
                        if (intraPriceArray.isNullOrEmpty()) {
                            intraPriceArray = GetIntraArray(stockIndex, api, objectType, usOffset, true, "5", 150, 1, startTime, tradeTime).first
                        }

                        try {
                            if (!intraPriceArray.isNullOrEmpty()) {
                                val taskLength = intraPriceArray.size
                                timeStampArray = LongArray(taskLength)
                                priceArray = FloatArray(taskLength)
                                var k1: Int = taskLength - 1
                                var k2: Int = 0

                                while (k1 >= 0) {
                                    //unviersalMarketTime * 3600
                                    timeStampArray[k2] = intraPriceArray[k1].date
                                    priceArray[k2] = intraPriceArray[k1].close
                                    k1--
                                    k2++
                                }
                            }
                        } catch (e: Exception) {
                            //    Log.i("Error", e.printStackTrace().toString())
                            timeStampArray = null
                        }
                    }
                    _PARAM2, _PARAM3, _PARAM4, _PARAM5, _PARAM6, _PARAM7, _PARAM8 -> {
                        var marketStart = UtcCalendar.getInstance()
                        marketStart.time = UtcDate()
                        //    marketStart.add(UtcCalendar.HOUR_OF_DAY, universalMarketTime)
                        tradeTime = marketStart.timeInMillis / 1000L

                        marketStart.add(UtcCalendar.HOUR_OF_DAY, -24)
                        val taskWeek = marketStart.get(UtcCalendar.DAY_OF_WEEK)
                        when (taskWeek) {
                            1 -> {
                                marketStart.add(UtcCalendar.HOUR_OF_DAY, -48)
                            }
                            7 -> {
                                marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), marketStartHour[_USA]!! + if (usOffset) 1 else 0, marketStartMinute[_USA]!!, 0)
                                marketStart.add(UtcCalendar.HOUR_OF_DAY, -24)
                            }
                            6 -> {
                                marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), marketStartHour[_USA]!! + if (usOffset) 1 else 0, marketStartMinute[_USA]!!, 0)
                            }
                        }
                        startTime = marketStart.timeInMillis / 1000L
                        val currentMoment = System.currentTimeMillis()

                        //    Log.i("MARKETSTART", "${marketStart.time}")
                        //    Log.i("CURRENTMOMENT", "${UtcDate(currentMoment)}")
                        var intraPriceArray = GetIntraArray(stockIndex, api, objectType, usOffset, true, "5", 300, 1, startTime, currentMoment).first
                        if (intraPriceArray.isNullOrEmpty()) {
                            intraPriceArray = GetIntraArray(stockIndex, api, objectType, usOffset, true, "5", 300, 2, startTime, currentMoment).first
                        }

                        try {
                            if (!intraPriceArray.isNullOrEmpty()) {
                                val taskLength = intraPriceArray.size
                                timeStampArray = LongArray(taskLength)
                                priceArray = FloatArray(taskLength)
                                var k1: Int = taskLength - 1
                                var k2: Int = 0

                                while (k1 >= 0) {
                                    timeStampArray[k2] = intraPriceArray[k1].date
                                    priceArray[k2] = intraPriceArray[k1].close
                                    k1--
                                    k2++
                                }
                            }
                        } catch (e: Exception) {
                            //    Log.i("Error", e.printStackTrace().toString())
                            timeStampArray = null
                        }
                    }
                }
            }
            _MOEX, _CRYPTO -> {
                var marketInterval: String = ""
                when (objectType) {
                    _MOEX -> marketInterval = "10"
                    _CRYPTO -> marketInterval = "5"
                }

                var marketStart = UtcCalendar.getInstance()
                marketStart.time = UtcDate()
                //    marketStart.add(UtcCalendar.HOUR_OF_DAY, universalMarketTime)
                tradeTime = marketStart.timeInMillis / 1000L

                marketStart.add(UtcCalendar.HOUR_OF_DAY, -24)
                val taskWeek = marketStart.get(UtcCalendar.DAY_OF_WEEK)
                when (objectType) {
                    _MOEX -> {
                        when (taskWeek) {
                            1 -> marketStart.add(UtcCalendar.HOUR_OF_DAY, -48)
                            6 -> marketStart.add(UtcCalendar.HOUR_OF_DAY, -1 * marketStart.get(UtcCalendar.HOUR_OF_DAY))
                            7 -> marketStart.add(UtcCalendar.HOUR_OF_DAY, -24 - marketStart.get(UtcCalendar.HOUR_OF_DAY))
                        }
                    }
                    _CRYPTO -> {
                    }
                }
                startTime = marketStart.timeInMillis / 1000L

                //    Log.i("MARKETSTART", "${startTime}")
                //    Log.i("CURRENTMOMENT", "${tradeTime}")
                var intraPriceArray = GetIntraArray(stockIndex, api, objectType, usOffset, true, marketInterval, 300, 1, startTime, tradeTime).first
                if (intraPriceArray.isNullOrEmpty()) {
                    when (objectType) {
                        _MOEX -> marketInterval = "5"
                    }
                    intraPriceArray = GetIntraArray(stockIndex, api, objectType, usOffset, true, marketInterval, 300, 2, startTime, tradeTime).first
                }

                try {
                    if (!intraPriceArray.isNullOrEmpty()) {
                        val taskLength = intraPriceArray.size
                        timeStampArray = LongArray(taskLength)
                        priceArray = FloatArray(taskLength)
                        var k1: Int = taskLength - 1
                        var k2: Int = 0

                        while (k1 >= 0) {
                            timeStampArray[k2] = intraPriceArray[k1].date
                            priceArray[k2] = intraPriceArray[k1].close
                            k1--
                            k2++
                        }
                    }
                } catch (e: Exception) {
                    //    Log.i("Error", e.printStackTrace().toString())
                    timeStampArray = null
                }
            }
        }

        var count: Int = 0
        var resultArray = ArrayList<DataPoint>()
        //   Log.i("LENGTH",timeStampArray.length().toString())
        //    Log.i("SOL", "$startTime/$tradeTime")
        if (timeStampArray != null && priceArray != null) {
            for (k in 0 until timeStampArray.size) {
                val temp = timeStampArray[k]
                //    Log.i("COMPARE", "${priceArray[k]}/${temp}/${startTime}/${tradeTime}")
                if (temp > startTime && temp < tradeTime) {
                    resultArray.add(DataPoint(count, priceArray[k], temp * 1000L))
                    //    Log.i("UtcDate", UtcDate(temp * 1000).toString())
                    count++
                }
            }
        }
        return resultArray
    }
    override fun GetGlobalArray(stock_name: String, api: String, objectType: Int): JSONObject? {
        var urlTask: String = ""
        when (objectType) {
            _USA -> {
                urlTask = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&apikey=$api&symbol=$stock_name"
            }
            _MOEX -> {
                urlTask = "https://iss.moex.com/cs/engines/stock/markets/shares/boardgroups/57/securities/$stock_name.hs?s1.type=candles&interval=24&candles=400"
            }
            _CRYPTO -> {
                urlTask = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&apikey=$api&symbol=$stock_name&market=USD"
            }
        }

        var response: JSONObject? = null
        try {
            val url = URL(urlTask)
            //    Log.i("REQUEST1M", urlTask)
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                connectTimeout = connectTimeoutServer
                //    Log.i("GET", "\nSent 'GET' request to URL : $url; Response Code : $responseCode")
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    val inputStream: DataInputStream = DataInputStream(inputStream)
                    val responseStrBuilder = StringBuilder()
                    inputStream.bufferedReader().use {
                        it.lines().forEach { line ->
                            responseStrBuilder.append(line)
                        }
                    }
                    response = JSONObject(responseStrBuilder.toString())
                    //    Log.i("RESPONSE", "VALUE=${response.toString()}")
                }
            }
            return response
        }
        catch (e1: Exception) {
            //    Log.i("Error", e1.printStackTrace().toString())
            try {
                var taskStockName = stock_name
                when (objectType) {
                    _CRYPTO -> {
                        taskStockName += "USD"
                    }
                }

                var marketStart = UtcCalendar.getInstance()
                marketStart.time = UtcDate()
                //    marketStart.add(UtcCalendar.HOUR_OF_DAY, outputGreenwichOffset)
                val currentMoment = marketStart.timeInMillis / 1000L

                marketStart.set(marketStart.get(UtcCalendar.YEAR), 0, 1, 0, 0, 0)
                marketStart.add(UtcCalendar.YEAR, -1)
                var startUtcDate = marketStart.timeInMillis / 1000L

                urlTask = "https://api.bcs.ru/udfdatafeed/v1/history?symbol=$taskStockName&resolution=D&from=$startUtcDate&to=$currentMoment"
                val url = URL(urlTask)
                //    Log.i("REQUEST2M", urlTask)
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    connectTimeout = connectTimeoutServer
                    //    Log.i("GET", "\nSent 'GET' request to URL : $url; Response Code : $responseCode")
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        val inputStream: DataInputStream = DataInputStream(inputStream)
                        val responseStrBuilder = StringBuilder()
                        inputStream.bufferedReader().use {
                            it.lines().forEach { line ->
                                responseStrBuilder.append(line)
                            }
                        }
                        response = JSONObject(responseStrBuilder.toString())
                        //    Log.i("RESPONSE", "VALUE=${response.toString()}")
                    }
                }

                if (response != null) {
                    var outputResponse: String = ""
                    when (objectType) {
                        _USA -> {
                            outputResponse += "{\"Time Series (Daily)\": {"
                            var count: Int = 0
                            val outputRowTime = response!!.getJSONArray("t")
                            val outputRow1 = response!!.getJSONArray("o")
                            val outputRow2 = response!!.getJSONArray("l")
                            val outputRow3 = response!!.getJSONArray("h")
                            val outputRow4 = response!!.getJSONArray("c")
                            var s: Int = outputRowTime.length() - 1
                            //    Log.i("USA", "<${outputRowTime.length()}>")
                            while (s >= 0) {
                                val taskUtcDate = outputRowTime.getString(s).toLong() + universalMarketTime * 3600L
                                //    Log.i("COMPARE", "$taskUtcDate/${marketStart.timeInMillis}/${marketFinish.timeInMillis}")
                                val item1 = outputRow1.getString(s).toFloat().toFinite()
                                val item2 = outputRow2.getString(s).toFloat().toFinite()
                                val item3 = outputRow3.getString(s).toFloat().toFinite()
                                val item4 = outputRow4.getString(s).toFloat().toFinite()
                                val cal = UtcCalendar.getInstance()
                                cal.time = UtcDate(false, taskUtcDate * 1000L)
                                outputResponse += "\"${cal.get(UtcCalendar.YEAR)}-${"%02d".format(cal.get(UtcCalendar.MONTH) + 1)}-${"%02d".format(cal.get(UtcCalendar.DAY_OF_MONTH))}\": { \"1. open\": \"$item1\", \"2. high\": \"$item3\", \"3. low\": \"$item2\", \"4. close\": \"$item4\"}"
                                if (count < outputRowTime.length() - 1)
                                    outputResponse += ","
                                //    Log.i("INTRADAY", "${item4}/${UtcDate(taskUtcDate * 1000)}")
                                s--
                                count++
                            }
                            outputResponse += "}}"
                            //    Log.i("RESULT", outputResponse)
                        }
                        _MOEX -> {
                            outputResponse += "{\"candles\": [{\"data\": ["

                            var count: Int = 0
                            val outputRowTime = response!!.getJSONArray("t")
                            val outputRow1 = response!!.getJSONArray("o")
                            val outputRow2 = response!!.getJSONArray("l")
                            val outputRow3 = response!!.getJSONArray("h")
                            val outputRow4 = response!!.getJSONArray("c")
                            var s: Int = 0
                            //    Log.i("MOEX", "<${outputRowTime.length()}>")
                            while (s < outputRowTime.length()) {
                                val taskUtcDate = outputRowTime.getString(s).toLong() + universalMarketTime * 3600L
                                val item1 = outputRow1.getString(s).toFloat().toFinite()
                                val item2 = outputRow2.getString(s).toFloat().toFinite()
                                val item3 = outputRow3.getString(s).toFloat().toFinite()
                                val item4 = outputRow4.getString(s).toFloat().toFinite()
                                outputResponse += "[${taskUtcDate * 1000L}, $item1, $item3, $item2, $item4]"
                                if (s < outputRowTime.length() - 1)
                                    outputResponse += ","
                                s++
                            }
                            outputResponse += "]}]}"
                        }
                        _CRYPTO -> {
                            outputResponse += "{\"Time Series (Digital Currency Daily)\": {"
                            var count: Int = 0
                            val outputRowTime = response!!.getJSONArray("t")
                            val outputRow1 = response!!.getJSONArray("o")
                            val outputRow2 = response!!.getJSONArray("l")
                            val outputRow3 = response!!.getJSONArray("h")
                            val outputRow4 = response!!.getJSONArray("c")
                            var s: Int = outputRowTime.length() - 1
                            //    Log.i("CRYPTO", "<${outputRowTime.length()}>")
                            while (s >= 0) {
                                val taskUtcDate = outputRowTime.getString(s).toLong() + universalMarketTime * 3600L
                                val item1 = outputRow1.getString(s).toFloat().toFinite()
                                val item2 = outputRow2.getString(s).toFloat().toFinite()
                                val item3 = outputRow3.getString(s).toFloat().toFinite()
                                val item4 = outputRow4.getString(s).toFloat().toFinite()
                                val cal = UtcCalendar.getInstance()
                                cal.time = UtcDate(false, taskUtcDate * 1000L)
                                outputResponse += "\"${cal.get(UtcCalendar.YEAR)}-${"%02d".format(cal.get(UtcCalendar.MONTH) + 1)}-${"%02d".format(cal.get(UtcCalendar.DAY_OF_MONTH))}\": { \"1a. open (USD)\": \"$item1\", \"2a. high (USD)\": \"$item3\", \"3a. low (USD)\": \"$item2\", \"4a. close (USD)\": \"$item4\"}"
                                if (count < outputRowTime.length() - 1)
                                    outputResponse += ","
                                s--
                                count++
                            }
                            outputResponse += "}}"
                        }
                    }
                    return JSONObject(outputResponse)
                }
            }
            catch (e2: Exception) {
                //    Log.i("Error", e.printStackTrace().toString())
            }
            return null
        }
    }
    override fun GetSpecialArray(stock_name: String, api: String, task_type: Int, object_type: Int, usOffset: Boolean, multiTask: Boolean, epicList: Array<String>?, task_index: Int?): Pair<Array<PricePoint>?, Float> {
        when (task_type) {
            _PARAM2, _PARAM3 -> {
                var marketStartOffsetHour: Int = 0
                var marketStartOffsetMinute: Int = 0
                when (object_type) {
                    _USA -> {
                        marketStartOffsetHour = 16 + if (usOffset) 1 else 0
                        marketStartOffsetMinute = 30
                    }
                    _MOEX -> {
                        marketStartOffsetHour = 10
                        marketStartOffsetMinute = 0
                    }
                    _CRYPTO -> {
                        marketStartOffsetHour = 0
                        marketStartOffsetMinute = 0
                    }
                }

                var taskTimeframe: String = ""
                var marketStart = UtcCalendar.getInstance()
                marketStart.time = UtcDate()
                //    marketStart.add(UtcCalendar.HOUR_OF_DAY, outputGreenwichOffset)
                val currentMoment = marketStart.timeInMillis / 1000L
                marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), marketStartOffsetHour, marketStartOffsetMinute, 0)
                val rangeValue = getUtcDateDifference(marketStart.timeInMillis / 1000L, currentMoment)

                //    Log.i("RANGE", "${rangeValue}")
                when (object_type) {
                    _USA -> {
                        when {
                            rangeValue >= 4f -> taskTimeframe = "15"
                            rangeValue >= 0.8f && rangeValue < 4f -> taskTimeframe = "5"
                            rangeValue < 0.8f -> taskTimeframe = "1"
                        }
                    }
                    _MOEX -> {
                        when {
                            rangeValue >= 8f -> taskTimeframe = "60"
                            rangeValue >= 0.8f && rangeValue < 8f -> taskTimeframe = "10"
                            rangeValue < 0.8f -> taskTimeframe = "1"
                        }
                    }
                    _CRYPTO -> {
                        when {
                            rangeValue >= 24f -> taskTimeframe = "60"
                            rangeValue >= 12f && rangeValue < 24f -> taskTimeframe = "30"
                            rangeValue >= 4f && rangeValue < 12f -> taskTimeframe = "15"
                            rangeValue >= 0.8f && rangeValue < 4f -> taskTimeframe = "5"
                            rangeValue < 0.8f -> taskTimeframe = "1"
                        }
                    }
                }

                var intraPriceArray = GetIntraArray(stock_name, api, object_type, usOffset, multiTask, taskTimeframe, JapaneseCandleTotal, 1)
                if (intraPriceArray.first.isNullOrEmpty()) {
                    when {
                        rangeValue >= 24f -> taskTimeframe = "60"
                        rangeValue >= 12f && rangeValue < 24f -> taskTimeframe = "30"
                        rangeValue >= 4f && rangeValue < 12f -> taskTimeframe = "15"
                        rangeValue >= 0.8f && rangeValue < 4f -> taskTimeframe = "5"
                        rangeValue < 0.8f -> taskTimeframe = "1"
                    }
                    intraPriceArray = GetIntraArray(stock_name, api, object_type, usOffset, multiTask, taskTimeframe, JapaneseCandleTotal, 2)
                    var specialOffset: Int = 0
                    if (object_type != _MOEX) {
                        specialOffset = 1
                    }
                    intraPriceArray = Pair(PushCurrentPrice(stock_name, api, object_type, usOffset, intraPriceArray.first, specialOffset), intraPriceArray.second)
                    /*    else {
                            var taskPriceOpen: Float
                            var taskHighPrice: Float
                            var taskLowPrice: Float
                            val currentPrice = intraPriceArray.first!![0]
                            val comparePrice = intraPriceArray.first!![1]
                            taskPriceOpen = comparePrice.close
                            taskHighPrice = if (currentPrice.close > comparePrice.close) currentPrice.close else comparePrice.close
                            taskLowPrice = if (currentPrice.close < comparePrice.close) currentPrice.close else comparePrice.close
                            val taskPricePoint = PricePoint(UtcDate().time / 1000, taskPriceOpen, taskLowPrice, taskHighPrice, currentPrice.close, 0)
                        }   */
                    //    if (!intraPriceArray.first.isNullOrEmpty())
                    //        intraPriceArray.first!![0] = ImageCreator.JapaneseCandleMerge(intraPriceArray.first!![0], intraPriceArray.first!![1])
                } else if (object_type != _MOEX) {
                    intraPriceArray = Pair(PushCurrentPrice(stock_name, api, object_type, usOffset, intraPriceArray.first), intraPriceArray.second)
                }
                //    Log.i("JAPANESE", "${intraPriceArray.first!![0].open}/${intraPriceArray.first!![0].high}/${intraPriceArray.first!![0].low}/${intraPriceArray.first!![0].close}")
                return intraPriceArray
            }
            _PARAM4, _PARAM5 -> {
                var currentMoment = UtcCalendar.getInstance()
                currentMoment.time = UtcDate()
                currentMoment.set(currentMoment.get(Calendar.YEAR), currentMoment.get(Calendar.MONTH), currentMoment.get(Calendar.DAY_OF_MONTH), 0, 0, 0)

                var weekLength: Int = 0
                if (epicList != null && task_index != null) {
                    try {
                        var k: Int = 0
                        while (k < epicList.size) {
                            val taskUtcDate = "dd/MM/yyyy".utcParse(epicList[k]).time / 1000L
                            //    Log.i("MOON", taskUtcDate.toString())
                            if (taskUtcDate > currentMoment.timeInMillis / 1000L)
                                break
                            k++
                        }

                        if (k < epicList.size) {
                            //Calculate Moon Phase
                            var epicType: Int = 1
                            if (task_index - (k - 1) > 1)
                                epicType = 2

                            var weekState: Int = 1
                            var totalCount: Int = 0
                            var fridayCount: Int = epicValue1
                            var taskUtcCalendar = UtcCalendar.getInstance()

                            var taskTimestamp = "dd/MM/yyyy".utcParse(epicList[k - 1]).time / 1000L + 86400L
                            var specialTimestamp = "dd/MM/yyyy".utcParse(epicList[k]).time / 1000L
                            val finishTimestamp = "dd/MM/yyyy".utcParse(epicList[task_index]).time / 1000L

                            if (currentMoment.timeInMillis / 1000L < taskTimestamp)
                                weekLength = epicValue1 - 1

                            while (taskTimestamp < finishTimestamp) {
                                if (!checkStockHoliday(taskTimestamp, object_type)) {
                                    taskUtcCalendar.time = UtcDate(false, taskTimestamp * 1000L)
                                    val taskWeek = taskUtcCalendar.get(UtcCalendar.DAY_OF_WEEK)
                                    //    Log.i("specialTimestamp", "${taskTimestamp}/${specialTimestamp}")
                                    if (checkWeek(taskWeek, object_type, taskTimestamp)) {
                                        if (weekState == 0 && epicType == 1 || taskTimestamp >= specialTimestamp && epicType == 2) {
                                            totalCount += (totalCount % epicValue1) * fridayCount
                                            fridayCount *= epicValue1
                                            specialTimestamp = "dd/MM/yyyy".utcParse(epicList[++k]).time / 1000L
                                        }

                                        if (taskTimestamp < currentMoment.timeInMillis / 1000L) {
                                            weekLength++
                                        }
                                        totalCount++
                                        weekState++

                                        //    var testUtcCalendar = UtcCalendar.getInstance()
                                        //    testUtcCalendar.time = UtcDate(false, taskTimestamp * 1000L)
                                        //    Log.i("Compare", "${testUtcCalendar.time}/${currentMoment.time}")
                                        if (taskWeek == 1)
                                            weekState = 0
                                    } else {
                                        weekState = 0
                                    }
                                }
                                taskTimestamp += 86400L
                            }
                            weekLength += totalCount * epicValue1
                            //    Log.i("UtcCalendar", "${weekLength}/${totalCount}")
                            //    weekLength = 5
                        }
                    } catch (e: Exception) {
                        //    Log.i("Error", e.printStackTrace().toString())
                        weekLength = 0
                    }
                }
                return Pair(null, weekLength.toFloat())
            }
            _PARAM6, _PARAM7, _PARAM8 -> {
                var currentMoment = UtcCalendar.getInstance()
                currentMoment.time = UtcDate()

                var taskStart = UtcCalendar.getInstance()
                taskStart.time = UtcDate()
                taskStart.set(taskStart.get(UtcCalendar.YEAR), 0, 1, 0, 0, 0)

                var epicLength: Int = 0
                var totalCount: Int = 0
                var taskTimestamp = taskStart.timeInMillis / 1000L
                if (epicList != null && task_index != null) {
                    if (task_index < epicList.size) {
                        val epicTimestamp = "dd/MM/yyyy".utcParse(epicList[task_index]).time / 1000L
                        var taskUtcCalendar = UtcCalendar.getInstance()

                        while (taskTimestamp < epicTimestamp) {
                            if (!checkStockHoliday(taskTimestamp, object_type)) {
                                taskUtcCalendar.time = UtcDate(false, taskTimestamp * 1000L)
                                val taskWeek = taskUtcCalendar.get(UtcCalendar.DAY_OF_WEEK)
                                if (checkWeek(taskWeek, object_type, taskTimestamp)) {
                                    if (taskTimestamp < currentMoment.timeInMillis / 1000L) {
                                        epicLength++

                                        var testUtcCalendar = UtcCalendar.getInstance()
                                        testUtcCalendar.time = UtcDate(false, taskTimestamp * 1000L)
                                        //    Log.i("UtcCalendar", taskTimestamp.toString())
                                    }
                                    totalCount++
                                }
                            }
                            taskTimestamp += 86400L
                        }
                        epicLength += totalCount * epicValue2
                        //    Log.i("UtcCalendar", "${epicLength}")
                    }
                }
                return Pair(null, epicLength.toFloat())
            }
        }
        return Pair(null, 0f)
    }
    override fun GetIntraArray(stock_name: String, api: String, object_type: Int, usOffset: Boolean, multiTask: Boolean, taskTimeframe: String, taskSize: Int, reserve: Int, startTime: Long, tradeTime: Long): Pair<Array<PricePoint>?, Float> {
        var specialArray: ArrayList<PricePoint>
        var urlTask: String = ""
        var timeframe: String = taskTimeframe
        var response1: JSONArray? = null
        var response2: JSONObject? = null

        val requestSize: Int = if (taskSize > 0) taskSize else 99999
        when (object_type) {
            _USA -> {
                if (reserve == 1) {
                    timeframe += "min"
                    urlTask = "https://financialmodelingprep.com/api/v3/historical-chart/$timeframe/$stock_name?apikey=$api"
                } else {
                    var taskTradeTime = if (tradeTime > 0) tradeTime else UtcDate().time / 1000L
                    timeframe += "m"
                    urlTask = "https://query1.finance.yahoo.com/v8/finance/chart/$stock_name?symbol=$stock_name&period2=${taskTradeTime - universalMarketTime * 3600L}&useYfid=true&interval=$timeframe&includePrePost=true&events=div|split|earn&lang=en-US&region=US"
                }
            }
            _MOEX -> {
                if (reserve == 1) {
                    urlTask = "https://iss.moex.com/cs/engines/stock/markets/shares/boardgroups/57/securities/$stock_name.hs?s1.type=candles&interval=$timeframe&candles=$requestSize"
                } else {
                    var taskStartTime = if (startTime > 0) startTime else UtcDate().time / 1000L - 86400L
                    var taskTradeTime = if (tradeTime > 0) tradeTime else UtcDate().time / 1000L
                    urlTask = "https://api.bcs.ru/udfdatafeed/v1/history?symbol=$stock_name&resolution=$timeframe&from=${taskStartTime - universalMarketTime * 3600L}&to=${taskTradeTime - universalMarketTime * 3600L}"
                }
            }
            _CRYPTO -> {
                if (reserve == 1) {
                    timeframe += "min"
                    urlTask = "https://financialmodelingprep.com/api/v3/historical-chart/$timeframe/${stock_name}USD?apikey=$api"
                } else {
                    var taskTradeTime = if (tradeTime > 0) tradeTime else UtcDate().time / 1000L
                    timeframe += "m"
                    urlTask = "https://query1.finance.yahoo.com/v8/finance/chart/$stock_name-USD?symbol=$stock_name-USD&period2=${taskTradeTime - universalMarketTime * 3600L}&useYfid=true&interval=$timeframe&includePrePost=true&events=div|split|earn&lang=en-US&region=US"
                }
            }
        }

        try {
            if (startTime > 0) {
                val url = URL(urlTask)
                //    Log.i("REQUEST1", urlTask)
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"  // optional default is GET
                    connectTimeout = connectTimeoutServer
                    if (multiTask)
                        readTimeout = readTimeoutServer
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        val inputStream: DataInputStream = DataInputStream(inputStream)
                        val responseStrBuilder = StringBuilder()
                        inputStream.bufferedReader().use {
                            it.lines().forEach { line ->
                                responseStrBuilder.append(line)
                            }
                        }
                        when (object_type) {
                            _USA, _CRYPTO -> {
                                if (reserve == 1)
                                    response1 = JSONArray(responseStrBuilder.toString())
                                else {
                                    val responseStr = responseStrBuilder.toString()
                                    response2 = JSONObject(responseStr).getJSONObject("chart").getJSONArray("result").getJSONObject(0)
                                }
                                //    Log.i("RESPONSE11", "${responseStrBuilder}")
                            }
                            _MOEX -> {
                                if (reserve == 1) {
                                    response1 = JSONObject(responseStrBuilder.toString()).getJSONArray("candles").getJSONObject(0).getJSONArray("data")
                                } else {
                                    response2 = JSONObject(responseStrBuilder.toString())
                                }
                                //    Log.i("RESPONSE12", "${responseStrBuilder}")
                            }
                        }
                    }
                }

                specialArray = ArrayList<PricePoint>()
                when (object_type) {
                    _USA, _CRYPTO -> {
                        var s: Int = 0
                        var count: Int = 0
                        if (reserve == 1 && response1 != null) {
                            while (s < response1!!.length() && count < requestSize) {
                                val outputRow = response1!!.getJSONObject(s)
                                val taskUtcDate = "yyyy-MM-dd HH:mm:ss".utcParse(outputRow["date"].toString()).time / 1000L + universalMarketTime * 3600L + 14400L + if (usOffset) 3600L else 0L
                                //    Log.i("INTRADAY1", "${taskUtcDate}/${outputRow["date"].toString()}")
                                if (taskUtcDate < startTime)
                                    break
                                if (taskUtcDate < tradeTime) {
                                    val item1 = outputRow["open"].toString().toFloat().toFinite()
                                    val item2 = outputRow["low"].toString().toFloat().toFinite()
                                    val item3 = outputRow["high"].toString().toFloat().toFinite()
                                    val item4 = outputRow["close"].toString().toFloat().toFinite()
                                    //    Log.i("INPUT", "${item4}")
                                    specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                    count++
                                }
                                s++
                            }
                        } else if (response2 != null) {
                            val taskTimestampArray = response2!!.getJSONArray("timestamp")
                            val taskPriceOpen = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("open")
                            val taskPriceLow = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("low")
                            val taskPriceHigh = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("high")
                            val taskPriceClose = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close")

                            //    val timeOffset = if (object_type == _USA) (14400 + if (usOffset) 3600 else 0) else 0
                            s = taskTimestampArray.length() - 1
                            while (s >= 0 && count < requestSize) {
                                val outputTimestamp = taskTimestampArray[s].toString().toLong()
                                val taskUtcDate = outputTimestamp + universalMarketTime * 3600L
                                if (taskUtcDate < startTime)
                                    break
                                val item4 = taskPriceClose[s].toString().toFloatOrNull().toFinite()
                                    ?: -1f
                                if (taskUtcDate < tradeTime && item4 >= 0f) {
                                    val item1 = taskPriceOpen[s].toString().toFloatOrNull().toFinite()
                                        ?: 0f
                                    val item2 = taskPriceLow[s].toString().toFloatOrNull().toFinite()
                                        ?: 0f
                                    val item3 = taskPriceHigh[s].toString().toFloatOrNull().toFinite()
                                        ?: 0f
                                    specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                    count++
                                }
                                s--
                            }
                        }
                    }
                    _MOEX -> {
                        var count: Int = 0
                        if (reserve == 1 && response1 != null) {
                            var s: Int = response1!!.length() - 1
                            while (s >= 0 && count < requestSize) {
                                val outputRow = response1!!.getJSONArray(s)
                                val taskUtcDate = outputRow.getString(0).toLong() / 1000L
                                if (taskUtcDate < startTime)
                                    break

                                //    Log.i("INTRADAY", "${taskUtcDate}/${startTime}/${tradeTime}")
                                if (taskUtcDate < tradeTime) {
                                    val item1 = outputRow.getString(1).toFloat().toFinite()
                                    val item2 = outputRow.getString(3).toFloat().toFinite()
                                    val item3 = outputRow.getString(2).toFloat().toFinite()
                                    val item4 = outputRow.getString(4).toFloat().toFinite()
                                    specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                    count++
                                }
                                s--
                            }
                        } else if (response2 != null) {
                            val outputRowTime = response2!!.getJSONArray("t")
                            val outputRow1 = response2!!.getJSONArray("o")
                            val outputRow2 = response2!!.getJSONArray("l")
                            val outputRow3 = response2!!.getJSONArray("h")
                            val outputRow4 = response2!!.getJSONArray("c")
                            var s: Int = outputRow1!!.length() - 1
                            while (s >= 0 && count < outputRowTime.length()) {
                                val taskUtcDate = outputRowTime.getString(s).toLong() + universalMarketTime * 3600L
                                //    Log.i("INTRADAY", "${taskUtcDate}/${startTime}/${tradeTime}")
                                if (taskUtcDate < startTime)
                                    break
                                if (taskUtcDate < tradeTime) {
                                    val item1 = outputRow1.getString(s).toFloat().toFinite()
                                    val item2 = outputRow2.getString(s).toFloat().toFinite()
                                    val item3 = outputRow3.getString(s).toFloat().toFinite()
                                    val item4 = outputRow4.getString(s).toFloat().toFinite()
                                    specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                    count++
                                }
                                s--
                            }
                        }
                    }
                }
                return Pair(specialArray.toTypedArray(), 999f)
            }
            else {
                var marketStart = UtcCalendar.getInstance()
                marketStart.time = UtcDate()
                //    marketStart.add(UtcCalendar.HOUR_OF_DAY, outputGreenwichOffset)

                var rangeCompare: Float = 0f
                var marketStartOffsetHour: Int = 0
                var marketStartOffsetMinute: Int = 0
                var marketFinishOffsetHour: Int = 0
                var marketFinishOffsetMinute: Int = 0
                when (object_type) {
                    _USA -> {
                        rangeCompare = 6.5f
                        marketStartOffsetHour = marketStartHour[_USA]!! + if (usOffset) 1 else 0
                        marketStartOffsetMinute = marketStartMinute[_USA]!!
                        marketFinishOffsetHour = 6
                        marketFinishOffsetMinute = 30
                    }
                    _MOEX -> {
                        //Test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        marketStartOffsetHour = marketStartHour[_MOEX]!!
                        marketStartOffsetMinute = marketStartMinute[_MOEX]!!
                        rangeCompare = 17f
                        marketFinishOffsetHour = 17
                        marketFinishOffsetMinute = 0
                    }
                    _CRYPTO -> {
                        marketStartOffsetHour = marketStartHour[_CRYPTO]!!
                        marketStartOffsetMinute = marketStartMinute[_CRYPTO]!!
                        rangeCompare = 24f
                        marketFinishOffsetHour = 24
                        marketFinishOffsetMinute = 0
                    }
                }

                val taskWeek = marketStart.get(UtcCalendar.DAY_OF_WEEK)
                val currentMoment = marketStart.timeInMillis / 1000L
                marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
                val compareTimestamp = marketStart.timeInMillis / 1000L
                if (checkWeek(taskWeek, object_type, compareTimestamp)) {
                    marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), marketStartOffsetHour, marketStartOffsetMinute, 0)
                    val rangeValue = getUtcDateDifference(marketStart.timeInMillis / 1000L, currentMoment)

                    //    Log.i("COMPARE", "${rangeValue}/${rangeCompare}/${marketStart.time}")
                    if (rangeValue > 0f && rangeValue <= rangeCompare) {
                        var marketFinish = UtcCalendar.getInstance()
                        marketFinish.time = marketStart.time
                        marketFinish.add(UtcCalendar.HOUR_OF_DAY, marketFinishOffsetHour)
                        marketFinish.add(UtcCalendar.MINUTE, marketFinishOffsetMinute)

                        val url = URL(urlTask)
                        var output: Boolean

                        //    Log.i("MARKETSTART", "${marketStart.timeInMillis}/${marketFinish.timeInMillis}")
                        //    Log.i("REQUEST2", urlTask)
                        with(url.openConnection() as HttpURLConnection) {
                            requestMethod = "GET"  // optional default is GET
                            connectTimeout = connectTimeoutServer
                            if (multiTask)
                                readTimeout = readTimeoutServer
                            if (responseCode == HttpsURLConnection.HTTP_OK) {
                                val inputStream: DataInputStream = DataInputStream(inputStream)
                                val responseStrBuilder = StringBuilder()
                                inputStream.bufferedReader().use {
                                    it.lines().forEach { line ->
                                        responseStrBuilder.append(line)
                                    }
                                }
                                when (object_type) {
                                    _USA, _CRYPTO -> {
                                        if (reserve == 1)
                                            response1 = JSONArray(responseStrBuilder.toString())
                                        else {
                                            val responseStr = responseStrBuilder.toString()
                                            response2 = JSONObject(responseStr).getJSONObject("chart").getJSONArray("result").getJSONObject(0)
                                        }
                                        //    Log.i("RESPONSE21", "${responseStrBuilder}")
                                    }
                                    _MOEX -> {
                                        if (reserve == 1) {
                                            response1 = JSONObject(responseStrBuilder.toString()).getJSONArray("candles").getJSONObject(0).getJSONArray("data")
                                        } else {
                                            response2 = JSONObject(responseStrBuilder.toString())
                                        }
                                        //    Log.i("RESPONSE22", "${responseStrBuilder}")
                                    }
                                }
                            }
                        }

                        specialArray = ArrayList<PricePoint>()
                        when (object_type) {
                            _USA, _CRYPTO -> {
                                var s: Int = 0
                                var count: Int = 0
                                if (reserve == 1 && response1 != null) {
                                    while (s < response1!!.length() && count < requestSize) {
                                        val outputRow = response1!!.getJSONObject(s)
                                        val taskUtcDate = "yyyy-MM-dd HH:mm:ss".utcParse(outputRow["date"].toString()).time / 1000L + universalMarketTime * 3600L + 14400L + if (usOffset) 3600L else 0L
                                        //    Log.i("INTRADAY1", "${taskUtcDate}/${outputRow["date"].toString()}")
                                        if (taskUtcDate >= marketStart.timeInMillis / 1000L && taskUtcDate <= marketFinish.timeInMillis / 1000L) {
                                            val item1 = outputRow["open"].toString().toFloat().toFinite()
                                            val item2 = outputRow["low"].toString().toFloat().toFinite()
                                            val item3 = outputRow["high"].toString().toFloat().toFinite()
                                            val item4 = outputRow["close"].toString().toFloat().toFinite()
                                            //    Log.i("INPUT", "${item4}")
                                            specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                            count++
                                        }
                                        s++
                                    }
                                } else if (response2 != null) {
                                    val taskTimestampArray = response2!!.getJSONArray("timestamp")
                                    val taskPriceOpen = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("open")
                                    val taskPriceLow = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("low")
                                    val taskPriceHigh = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("high")
                                    val taskPriceClose = response2!!.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close")

                                    s = taskTimestampArray.length() - 1
                                    while (s >= 0 && count < requestSize) {
                                        val outputTimestamp = taskTimestampArray[s].toString().toLong()
                                        val taskUtcDate = outputTimestamp + universalMarketTime * 3600L
                                        //    Log.i("INTRADAY2", "${taskUtcDate}/${UtcDate(taskUtcDate * 1000)}")
                                        val item4 = taskPriceClose[s].toString().toFloatOrNull().toFinite()
                                            ?: -1f
                                        if (taskUtcDate >= marketStart.timeInMillis / 1000L && taskUtcDate <= marketFinish.timeInMillis / 1000L && item4 >= 0f) {
                                            //    Log.i("ITEM4", "${taskPriceClose[s].toString()}")
                                            val item1 = taskPriceOpen[s].toString().toFloatOrNull().toFinite()
                                                ?: 0f
                                            val item2 = taskPriceLow[s].toString().toFloatOrNull().toFinite()
                                                ?: 0f
                                            val item3 = taskPriceHigh[s].toString().toFloatOrNull().toFinite()
                                                ?: 0f
                                            //    Log.i("INPUT", "${item4}")
                                            specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                            count++
                                        }
                                        s--
                                    }
                                }
                            }
                            _MOEX -> {
                                var count: Int = 0
                                if (reserve == 1 && response1 != null) {
                                    //    Log.i("MOEX", "<${response1!!.length()}>")
                                    var s: Int = response1!!.length() - 1
                                    while (s >= 0 && count < requestSize) {
                                        val outputRow = response1!!.getJSONArray(s)
                                        val taskUtcDate = outputRow.getString(0).toLong() / 1000L
                                        //    Log.i("COMPARE", "$taskUtcDate/${marketStart.timeInMillis}/${marketFinish.timeInMillis}")

                                        if (taskUtcDate >= marketStart.timeInMillis / 1000L && taskUtcDate <= marketFinish.timeInMillis / 1000L) {
                                            val item1 = outputRow.getString(1).toFloat().toFinite()
                                            val item2 = outputRow.getString(3).toFloat().toFinite()
                                            val item3 = outputRow.getString(2).toFloat().toFinite()
                                            val item4 = outputRow.getString(4).toFloat().toFinite()
                                            specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                            //    Log.i("INTRADAY", "${UtcDate(taskUtcDate * 1000)}/${UtcDate(startTime * 1000)}")
                                            count++
                                        }
                                        s--
                                    }
                                } else if (response2 != null) {
                                    val outputRowTime = response2!!.getJSONArray("t")
                                    val outputRow1 = response2!!.getJSONArray("o")
                                    val outputRow2 = response2!!.getJSONArray("l")
                                    val outputRow3 = response2!!.getJSONArray("h")
                                    val outputRow4 = response2!!.getJSONArray("c")
                                    var s: Int = outputRow1!!.length() - 1

                                    while (s >= 0 && count < outputRowTime.length()) {
                                        val taskUtcDate = outputRowTime.getString(s).toLong() + universalMarketTime * 3600L
                                        if (taskUtcDate >= marketStart.timeInMillis / 1000L && taskUtcDate <= marketFinish.timeInMillis / 1000L) {
                                            val item1 = outputRow1.getString(s).toFloat().toFinite()
                                            val item2 = outputRow2.getString(s).toFloat().toFinite()
                                            val item3 = outputRow3.getString(s).toFloat().toFinite()
                                            val item4 = outputRow4.getString(s).toFloat().toFinite()
                                            specialArray.add(PricePoint(taskUtcDate, item1, item2, item3, item4, 0))
                                            count++
                                        }
                                        s--
                                    }
                                }
                            }
                        }
                        return Pair(specialArray.toTypedArray(), rangeValue)
                    }
                }
            }
        }
        catch (e: Exception) {
            //    Log.e("EXCEPTION", e.printStackTrace().toString())
        }
        return Pair(null, 999f)
    }
    override fun GetCurrentPrice(stock_name: String, api: String, object_type: Int, usOffset: Boolean): PricePoint? {
        var marketStart = UtcCalendar.getInstance()
        marketStart.time = UtcDate()
        //    marketStart.add(UtcCalendar.HOUR_OF_DAY, outputGreenwichOffset)
        val currentMoment = marketStart.timeInMillis / 1000L

        val taskWeek = marketStart.get(UtcCalendar.DAY_OF_WEEK)
        marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
        val compareTimestamp = marketStart.timeInMillis / 1000L
        if (checkWeek(taskWeek, object_type, compareTimestamp)) {
            when (object_type) {
                _USA, _CRYPTO -> {
                    var taskStockname = stock_name
                    var rangeCompare: Float = 0f
                    var marketStartOffsetHour: Int = 0
                    var marketStartOffsetMinute: Int = 0
                    var marketFinishOffsetHour: Int = 0
                    var marketFinishOffsetMinute: Int = 0
                    when (object_type) {
                        _USA -> {
                            rangeCompare = 6.5f
                            marketStartOffsetHour = 16 + if (usOffset) 1 else 0
                            marketStartOffsetMinute = 30
                            marketFinishOffsetHour = 6
                            marketFinishOffsetMinute = 30
                        }
                        _CRYPTO -> {
                            marketStartOffsetHour = 0
                            marketStartOffsetMinute = 0
                            rangeCompare = 24f
                            marketFinishOffsetHour = 24
                            marketFinishOffsetMinute = 0
                            taskStockname += "USD"
                        }
                    }

                    marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), marketStartOffsetHour, marketStartOffsetMinute, 0)
                    val rangeValue = getUtcDateDifference(marketStart.timeInMillis / 1000L, currentMoment)
                    //    Log.i("RANGE", "$rangeValue")
                    //    Log.i("MARKETSTART", "${marketStart.timeInMillis / 1000}/${currentMoment}")
                    if (rangeValue > 0f && rangeValue <= rangeCompare) {
                        try {
                            val urlTask = "https://financialmodelingprep.com/api/v3/quote/$taskStockname?apikey=$api"
                            //    Log.i("REQUEST1S", "$urlTask")

                            val url = URL(urlTask)
                            var response: JSONArray? = null
                            with(url.openConnection() as HttpURLConnection) {
                                requestMethod = "GET"  // optional default is GET
                                connectTimeout = connectTimeoutServer
                                readTimeout = readTimeoutServer
                                if (responseCode == HttpsURLConnection.HTTP_OK) {
                                    val inputStream: DataInputStream = DataInputStream(inputStream)
                                    val responseStrBuilder = StringBuilder()
                                    inputStream.bufferedReader().use {
                                        it.lines().forEach { line ->
                                            responseStrBuilder.append(line)
                                        }
                                    }
                                    response = JSONArray(responseStrBuilder.toString())
                                }
                            }
                            if (response != null) {
                                val outputRow = response!!.getJSONObject(0)
                                val item1 = outputRow["open"].toString().toFloat().toFinite()
                                val item2 = outputRow["dayLow"].toString().toFloat().toFinite()
                                val item3 = outputRow["dayHigh"].toString().toFloat().toFinite()
                                val item4 = outputRow["price"].toString().toFloat().toFinite()
                                return PricePoint(UtcDate().time / 1000L, item1, item2, item3, item4, 0)
                            } else {
                                throw Exception("JSON")
                            }
                        } catch (e1: Exception) {
                            try {
                                //    Log.e("Error", e.printStackTrace().toString())
                                taskStockname = stock_name
                                when (object_type) {
                                    _CRYPTO -> taskStockname += "-USD"
                                }
                                val urlTask = "https://query1.finance.yahoo.com/v7/finance/quote?&symbols=$taskStockname&fields=regularMarketPrice"
                                //    Log.i("REQUEST2S", "$urlTask")

                                val url = URL(urlTask)
                                var response: JSONObject? = null
                                with(url.openConnection() as HttpURLConnection) {
                                    requestMethod = "GET"  // optional default is GET
                                    connectTimeout = connectTimeoutServer
                                    readTimeout = readTimeoutServer
                                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                                        val inputStream: DataInputStream = DataInputStream(inputStream)
                                        val responseStrBuilder = StringBuilder()
                                        inputStream.bufferedReader().use {
                                            it.lines().forEach { line ->
                                                responseStrBuilder.append(line)
                                            }
                                        }
                                        response = JSONObject(responseStrBuilder.toString()).getJSONObject("quoteResponse").getJSONArray("result").getJSONObject(0)
                                    }
                                }
                                //    val item1 = 0f
                                //    val item2 = 0f
                                //    val item3 = 0f
                                if (response != null) {
                                    val item4 = response!!["regularMarketPrice"].toString().toFloat().toFinite()
                                    return PricePoint(UtcDate().time / 1000L, item4, item4, item4, item4, 0)
                                }
                            } catch (e2: Exception) {
                                //    Log.e("Error", e.printStackTrace().toString())
                            }
                        }
                    } else {
                        return null
                    }
                }
                _MOEX -> {
                    marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), 10, 0, 0)
                    val rangeValue = getUtcDateDifference(marketStart.timeInMillis / 1000L, currentMoment)
                    //    Log.i("RANGE", "$rangeValue")
                    //    Log.i("MARKETSTART", "${marketStart.timeInMillis / 1000}/${currentMoment}")
                    if (rangeValue > 0f && rangeValue <= 17f) {
                        marketStart.add(UtcCalendar.HOUR_OF_DAY, -10)
                        var outputResult = GetIntraArray(stock_name, api, object_type, usOffset, true, "24", 1, 1, marketStart.timeInMillis / 1000L, currentMoment)
                        if (!outputResult.first.isNullOrEmpty())
                            return outputResult.first!![0]
                        else {
                            outputResult = GetIntraArray(stock_name, api, object_type, usOffset, true, "1", 1, 2, currentMoment - 1200L, currentMoment)
                            if (!outputResult.first.isNullOrEmpty()) {
                                return outputResult.first!![0]
                            }
                        }
                    }
                }
                /*    _CRYPTO -> {
                        marketStart.set(marketStart.get(UtcCalendar.YEAR), marketStart.get(UtcCalendar.MONTH), marketStart.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
                        val outputResult = GetIntraPrice(stock_name, api, object_type, usOffset, "24", 2, marketStart.timeInMillis / 1000, currentMoment)
                        if (outputResult.first != null)
                            return outputResult.first!![0]
                    }   */
            }
        }
        return null
    }
    private fun PushCurrentPrice(stock_name: String, api: String, object_type: Int, usOffset: Boolean, intraPriceArray: Array<PricePoint>?, specialOffset: Int = 0) : Array<PricePoint> {
        val taskPriceArray = ArrayList<PricePoint>()
        if (!intraPriceArray.isNullOrEmpty()) {
            //    Log.i("intraPriceArray", "${intraPriceArray.size}")
            var currentPrice: PricePoint?
            if (specialOffset > 0)
                currentPrice = intraPriceArray[0]
            else
                currentPrice = GetCurrentPrice(stock_name, api, object_type, usOffset)
            var taskPrice: PricePoint
            val taskLength = intraPriceArray.size

            if (currentPrice != null) {
                var comparePrice = PricePoint(UtcDate().time / 1000L, currentPrice.close, currentPrice.close, currentPrice.close, currentPrice.close, 0)
                if (taskLength > specialOffset)
                    taskPrice = ImageCreator.JapaneseCandleMerge(comparePrice, intraPriceArray[specialOffset])
                else
                    taskPrice = comparePrice
            }
            else {
                if (taskLength > specialOffset)
                    taskPrice = intraPriceArray[specialOffset]
                else
                    taskPrice = PricePoint(UtcDate().time / 1000L, 0f, 0f, 0f, 0f, 0)
            }
            //    Log.i("JAPANESE", "${taskPrice.open}/${taskPrice.high}/${taskPrice.low}/${taskPrice.close}")

            taskPriceArray.add(taskPrice)
            for (s in 1 + specialOffset until taskLength)
                taskPriceArray.add(intraPriceArray[s])
        }
        //    Log.i("taskPriceArray", "${taskPriceArray.size}")
        return taskPriceArray.toTypedArray()
    }
    override fun ConvertJSON(inputJson: JSONObject, object_type: Int): Pair<Array<PricePoint>, Int> {
        var compareUtcDateUtcCalendar = UtcCalendar.getInstance()
        compareUtcDateUtcCalendar.time = UtcDate()

        var compareUtcDateUnix: Long
        var compareUtcDateYear1: Int = compareUtcDateUtcCalendar.get(UtcCalendar.YEAR)
        var compareUtcDateYear2: Int = compareUtcDateYear1

        compareUtcDateUtcCalendar.set(compareUtcDateYear1, compareUtcDateUtcCalendar.get(UtcCalendar.MONTH), compareUtcDateUtcCalendar.get(UtcCalendar.DAY_OF_MONTH), 0, 0, 0)
        var currentMoment: Long = compareUtcDateUtcCalendar.timeInMillis / 1000L

        if (compareUtcDateUtcCalendar.get(UtcCalendar.MONTH) < 6)
            compareUtcDateYear2 -= 1
        compareUtcDateUtcCalendar.set(compareUtcDateYear1, 0, 1, 0, 0, 0)

        var compareUtcDateMarketCycle: Long = compareUtcDateUtcCalendar.timeInMillis / 1000L

        compareUtcDateUtcCalendar.set(compareUtcDateYear2, 0, 1, 0, 0, 0)
        compareUtcDateUnix = compareUtcDateUtcCalendar.timeInMillis / 1000L

        var marketCycle: Int = Int.MAX_VALUE
        var outputArray = ArrayList<PricePoint>()

        try {
            when (object_type) {
                _USA -> {
                    val valJson1 = inputJson.getJSONObject("Time Series (Daily)")
                    val valNames1 = valJson1.names()
                    for (k in 0 until valNames1.length()) {
                        val valName2 = valNames1.getString(k)
                        val valUtcDate = "yyyy-MM-dd".utcParse(valName2).time / 1000L
                        //    Log.i("GLOBAL", "${valUtcDate}/${compareUtcDateUnix}/${currentMoment}")
                        if (valUtcDate < compareUtcDateUnix)
                            break

                        if (valUtcDate < compareUtcDateMarketCycle && marketCycle == Int.MAX_VALUE)
                            marketCycle = k

                        if (valUtcDate < currentMoment) {
                            val item1 = valJson1.getJSONObject(valName2)["1. open"].toString().toFloat().toFinite()
                            val item2 = valJson1.getJSONObject(valName2)["3. low"].toString().toFloat().toFinite()
                            val item3 = valJson1.getJSONObject(valName2)["2. high"].toString().toFloat().toFinite()
                            val item4 = valJson1.getJSONObject(valName2)["4. close"].toString().toFloat().toFinite()
                            outputArray.add(PricePoint(valUtcDate, item1, item2, item3, item4, 0))
                        }
                    }
                }
                _MOEX -> {
                    val valJson1 = inputJson.getJSONArray("candles").getJSONObject(0).getJSONArray("data")
                    var k1: Int = valJson1.length() - 1
                    var k2: Int = 0
                    while (k1 >= 0) {
                        val valJson2 = valJson1.getJSONArray(k1)
                        val valUtcDate = valJson2.getString(0).toLong() / 1000L
                        //    Log.i("GLOBAL", "${valUtcDate}/${compareUtcDateUnix}/${currentMoment}")
                        if (valUtcDate < compareUtcDateUnix)
                            break

                        if (valUtcDate < compareUtcDateMarketCycle && marketCycle == Int.MAX_VALUE)
                            marketCycle = k2

                        if (valUtcDate < currentMoment) {
                            val item1 = valJson2.getString(1).toFloat().toFinite()
                            val item2 = valJson2.getString(3).toFloat().toFinite()
                            val item3 = valJson2.getString(2).toFloat().toFinite()
                            val item4 = valJson2.getString(4).toFloat().toFinite()
                            //    Log.i("INPUT", "${item4}")
                            outputArray.add(PricePoint(valUtcDate, item1, item2, item3, item4, 0))
                        }
                        k1--
                        k2++
                    }
                }
                _CRYPTO -> {
                    val valJson1 = inputJson.getJSONObject("Time Series (Digital Currency Daily)")
                    val valNames1 = valJson1.names()
                    for (k in 0 until valNames1.length()) {
                        val valName2 = valNames1.getString(k)
                        val valUtcDate = "yyyy-MM-dd".utcParse(valName2).time / 1000L
                        //    Log.i("GLOBAL", "${valUtcDate}/${compareUtcDateUnix}/${currentMoment}")
                        if (valUtcDate < compareUtcDateUnix)
                            break

                        if (valUtcDate < compareUtcDateMarketCycle && marketCycle == Int.MAX_VALUE)
                            marketCycle = k

                        if (valUtcDate < currentMoment) {
                            val item1 = valJson1.getJSONObject(valName2)["1a. open (USD)"].toString().toFloat().toFinite()
                            val item2 = valJson1.getJSONObject(valName2)["3a. low (USD)"].toString().toFloat().toFinite()
                            val item3 = valJson1.getJSONObject(valName2)["2a. high (USD)"].toString().toFloat().toFinite()
                            val item4 = valJson1.getJSONObject(valName2)["4a. close (USD)"].toString().toFloat().toFinite()
                            //    Log.i("INPUT", "${item4}")
                            outputArray.add(PricePoint(valUtcDate, item1, item2, item3, item4, 0))
                        }
                    }
                }
            }
        }
        catch (e: Exception) {
            //    Log.i("Error", e.printStackTrace().toString())
            return Pair(arrayOf(), 0)
        }
        if (marketCycle == Int.MAX_VALUE)
            marketCycle = 0
        return Pair(outputArray.toTypedArray(), outputArray.size - marketCycle)
    }

    override fun GetMarketState(api: String, taskList: Array<String>, nameList: Array<String>): CustomList {
        val outputResult: CustomList = CustomList()
        //TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //    for (s in 0 until 10) {
        //        outputResult.add("BTC", null, "Oil", "+5.6%", "75.0")
        //    }
        //    return outputResult

        val urlTask = "https://financialmodelingprep.com/api/v3/quote/"
        for (s1 in 0 until taskList.size) {
            var response: JSONArray? = null
            try {
                val url = URL("$urlTask${taskList[s1]}?apikey=$api")
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"  // optional default is GET
                    connectTimeout = connectTimeoutServer
                    readTimeout = readTimeoutServer
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        val inputStream: DataInputStream = DataInputStream(inputStream)
                        val responseStrBuilder = StringBuilder()
                        inputStream.bufferedReader().use {
                            it.lines().forEach { line ->
                                responseStrBuilder.append(line)
                            }
                        }
                        response = JSONArray(responseStrBuilder.toString())
                    }
                }
            }
            catch (e: Exception) { }

            if (response != null) {
                var outputRow: JSONObject? = null
                try {
                    outputRow = response!!.getJSONObject(0)
                }
                catch (e: Exception) { }
                if (outputRow != null) {
                    var name: String = ""
                    var price: Float = 0f
                    var change: Float = 0f
                    try {
                        name = outputRow["name"].toString()
                        price = outputRow["price"].toString().toFloat().toFinite()
                        change = outputRow["changesPercentage"].toString().toFloat().toFinite()
                    }
                    catch (e: Exception) { }

                    var priceStrCount: Int = 0
                    when {
                        price >= 1000f -> priceStrCount = 0
                        price >= 100f && price < 1000f -> priceStrCount = 1
                        price >= 10f && price < 100f -> priceStrCount = 2
                        price < 10f -> priceStrCount = 3
                    }
                    outputResult.add("BTC", null, nameList[s1], change.formatSign(2, 1), "%.${priceStrCount}f".format(price))
                }
            }

            try {
                Thread.sleep(50)
            }
            catch (e: Exception) { }
        }
        return outputResult
    }
    override fun GetSectorIndustry(): CustomList {
        val outputResult: CustomList = CustomList()
        //    for (s in 0 until 40) {
        //        outputResult.add(null, "Elon Musk Rocket Science $s", "+$s.35")
        //    }
        //    return outputResult

        var response: String? = null
        val urlTask = "https://finviz.com/groups.ashx?g=industry"
        try {
            val url = URL(urlTask)
            val headers: HashMap<String, String> = HashMap<String, String>()
            headers["user-agent"] = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Mobile Safari/537.36"

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                connectTimeout = connectTimeoutServer
                readTimeout = readTimeoutServer
                for (headerKey in headers.keys) {
                    setRequestProperty(headerKey, headers[headerKey])
                }
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    val inputStream: DataInputStream = DataInputStream(inputStream)
                    val responseStrBuilder = StringBuilder()
                    inputStream.bufferedReader().use {
                        it.lines().forEach { line ->
                            responseStrBuilder.append(line)
                        }
                    }
                    response = responseStrBuilder.toString()
                }
            }
        }
        catch (e: Exception) { }
        //    Log.i("RESPONSE", "$response")

        if (response != null) {
            val taskTable = HtmlParse.getScript(response!!, "var rows")
            if (taskTable.isNotEmpty()) {
                var jsonStr: JSONArray? = null
                try {
                    jsonStr = JSONArray(taskTable)
                }
                catch (e: Exception) { }

                if (jsonStr != null) {
                    //    Log.i("TABLE", "$jsonStr")
                    for (s in 0 until jsonStr.length()) {
                        var outputRow: JSONObject? = null
                        try {
                            outputRow = jsonStr.getJSONObject(s)
                        }
                        catch (e: Exception) { }
                        if (outputRow == null)
                            continue

                        var sector: String = ""
                        var label: String = ""
                        var change1: Float = 0f
                        var change2: Float = 0f
                        var change3: Float = 0f
                        try {
                            sector = outputRow["ticker"].toString()
                            label = outputRow["label"].toString()
                            change1 = outputRow["perfT"].toString().toFloat().toFinite()
                            change2 = outputRow["perfW"].toString().toFloat().toFinite()
                            change3 = outputRow["perfM"].toString().toFloat().toFinite()
                        }
                        catch (e: Exception) { }
                        outputResult.add(sector, null, label, change1.formatSign(2, 1), change2.formatSign(1, 0), change3.formatSign(1, 1))
                        //    Log.i("SECTOR", "${outputRow["label"]}\t$sector")
                    }
                }
            }
        }
        return outputResult
    }
    override fun GetStockEvaluate(type: Int): CustomList {
        val outputResult: CustomList = CustomList()
        /*    for (s in 0 until 20) {
                if (type == 0)
                    outputResult.add("BTC", null, "AAA$s", "+$s.7%", "+$s.6%", "-$s.4%")
                else
                    outputResult.add("BTC", null, "ZZZ$s", "+$s.7%", "+$s.6%", "-$s.4%")
            }
            for (s in 0 until 20) {
                if (type == 0)
                    outputResult.add("BTC", null, "AAA${s + 20}", "+${s + 20}.7%", "-${s + 20}.6%", "+${s + 20}.4%")
                else
                    outputResult.add("BTC", null, "ZZZ${s + 20}", "+${s + 20}.7%", "-${s + 20}.6%", "+${s + 20}.4%")
            }
            return outputResult     */

        var response: String? = null
        val urlTask = "https://www.investing.com/equities/StocksFilter?index_id=$type&tabletype=performance"
        try {
            val url = URL(urlTask)
            val headers: HashMap<String, String> = HashMap<String, String>()
            headers["user-agent"] = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Mobile Safari/537.36"

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                connectTimeout = connectTimeoutServer
                readTimeout = readTimeoutServer
                for (headerKey in headers.keys) {
                    setRequestProperty(headerKey, headers[headerKey])
                }
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    val inputStream: DataInputStream = DataInputStream(inputStream)
                    val responseStrBuilder = StringBuilder()
                    inputStream.bufferedReader().use {
                        it.lines().forEach { line ->
                            responseStrBuilder.append(line)
                        }
                    }
                    response = responseStrBuilder.toString()
                }
            }
        }
        catch (e: Exception) { }
        //    Log.i("RESPONSE", "$response")

        if (response != null) {
            val taskTable = HtmlParse.getByTag(HtmlParse.getById(response!!, "table", "marketsPerformance"), "tr")
            if (taskTable.isNotEmpty()) {
                //    Log.i("TABLE", "${taskTable.size}")
                //    Log.i("TABLE", "${taskTable.substring(taskTable.length - 100, taskTable.length)}")
                for (s in 1 until taskTable.size) {
                    val index = HtmlParse.getParameter(taskTable[s], "id").replace("pair_", "")
                    val outputRow = HtmlParse.getByTag(taskTable[s], "td")
                    if (outputRow.size > 4) {
                        try {
                            val name = HtmlParse.getValue(HtmlParse.getByTag(outputRow[1], "a")[0])
                            val change1 = HtmlParse.getValue(outputRow[2]).replace("%", "").toFloat().toFinite()
                            val change2 = HtmlParse.getValue(outputRow[3]).replace("%", "").toFloat().toFinite()
                            val change3 = HtmlParse.getValue(outputRow[4]).replace("%", "").toFloat().toFinite()
                            outputResult.add(index, null, name, change1.formatSign(2, 1), change2.formatSign(1, 0), change3.formatSign(1, 1))
                            //    Log.i("ROW", "$name ${"${changeValue1}%.1f".format(change1)}% ${"${changeValue2}%.1f".format(change2)}% ${"${changeValue3}%.1f".format(change3)}%")
                        }
                        catch (e: Exception) { }
                    }
                }
            }
        }
        return outputResult
    }

    override fun GetBase(ip: String, requestType: Int): Pair<JSONArray?, String> {
        if (requestType == 1) {
            var output: String = ""
            var response: JSONArray? = null
            try {
                val url = URL(ip)
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    connectTimeout = connectTimeoutServer
                    //    Log.i("GET", "\nSent 'GET' request to URL : $url; Response Code : $responseCode")
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        val inputStream: DataInputStream = DataInputStream(inputStream)
                        response = CSVFile.read(inputStream)
                        //    Log.i("RESPONSE", "VALUE=${response.toString()}")
                        output = "STOCK"
                    } else {
                        output = "HTTP $responseCode"
                    }
                }
                return Pair(response, output)
            }
            catch (e: Exception) {
                //    Log.e("EXCEPTION12", e.printStackTrace().toString())
                return Pair(null, "${e}/${e.message}")
            }
        }
        else {
            val commandTask = if (requestType == 0) "GET-TASK" else "GET-STORAGE"
            //    Log.i("WIFI", commandTask)

            var socket: Socket? = null
            try {
                socket = Socket(ip, 29000)
                //    socket.soTimeout = connectTimeoutServer
            }
            catch (e: Exception) {
                //    Log.i("EXCEPTION11", "${e.toString()}/${e.message.toString()}")
                return Pair(null, "${e}/${e.message}")
            }

            var msg: String = ""
            try {
                //    Log.i("SOCKET", "$ip/29000")
                val osw = OutputStreamWriter(socket.getOutputStream())
                val _bufferOut = PrintWriter(BufferedWriter(osw), true)

                if (_bufferOut == null || _bufferOut.checkError()) {
                    //    Log.i("SOCKET", "ERROR")
                    return Pair(null, "SOCKET")
                }
                _bufferOut.print(commandTask)
                _bufferOut.flush()
                socket.shutdownOutput()

                val isr = InputStreamReader(socket.getInputStream())
                val _bufferIn = BufferedReader(isr)
                msg = _bufferIn.readLine()
                //    Log.i("SOCKET", "MSG: $msg")
                socket.shutdownInput()
            }
            catch (e: Exception) { }
            try {
                socket.close()
            }
            catch (e: Exception) { }

            val taskIndex = msg.indexOf('^')
            try {
                if (taskIndex > 0 && taskIndex + 1 < msg.length)
                    return Pair(JSONArray(msg.substring(taskIndex + 1, msg.length)), msg.substring(0, taskIndex))
                else
                    return Pair(JSONArray(msg), "")
            }
            catch (e: Exception) {
                //    Log.i("EXCEPTION12", "${e.toString()}/${e.message.toString()}")
                return Pair(null, "${e}/${e.message}")
            }
        }
    }
    override fun SetBase(ip: String, requestType: Int, input: String): String {
        val commandTask = if (requestType == 0) "SET-BASE___" else "SET-STORAGE"
        var socket: Socket? = null
        try {
            socket = Socket(ip, 29000)
            //    socket.soTimeout = connectTimeoutServer
        }
        catch (e: Exception) {
            //    Log.i("EXCEPTION11", "${e.toString()}/${e.message.toString()}")
            return "${e}/${e.message}"
        }

        try {
            //    Log.i("SOCKET", "$ip/29000")
            val osw = OutputStreamWriter(socket.getOutputStream())
            val _bufferOut = PrintWriter(BufferedWriter(osw), true)

            if (_bufferOut == null || _bufferOut.checkError()) {
                //    Log.i("SOCKET", "ERROR")
                return "SOCKET"
            }
            _bufferOut.print(commandTask)
            _bufferOut.print(input)
            _bufferOut.flush()
            socket.shutdownOutput()
            socket.shutdownInput()
            socket.close()
            //    val isr = InputStreamReader(socket.getInputStream())
            //    val _bufferIn = BufferedReader(isr);
            //    val msg = _bufferIn.readLine()
            //    Log.i("SOCKET", "MSG: $msg")
        }
        catch (e1: Exception) {
            try {
                socket.close()
            }
            catch (e2: Exception) { }
            //    Log.i("EXCEPTION12", "${e.toString()}/${e.message.toString()}")
            return "${e1}/${e1.message}"
        }
        return "OK"
    }

    private fun Float.formatSign (length1: Int, length2: Int): String {
        var formatLength: Int = 0
        var compareValue = abs(this)
        when {
            compareValue < 10f -> formatLength = 2.coerceAtMost(length1)
            compareValue >= 10f && compareValue < 100f -> formatLength = 1.coerceAtMost(length2)
            compareValue >= 100f && compareValue < 1000f -> formatLength = 0
        }

        var type: String = "FORMAT"
        when {
            this > 0f -> type = "+"
            this <= 0f -> type = "-"
        }
        var outputResult: String = ""
        try {
            outputResult = "$type${"%.${formatLength}f".format(this).replace("-", "")}"
        }
        catch (e: Exception) { }
        return outputResult
    }
    private fun getUtcDateDifference(startUtcDate: Long, endUtcDate: Long): Float {
        var different = endUtcDate - startUtcDate
        return different / 3600f
    }
    private fun checkWeek(taskWeek: Int, objectType: Int, taskTimestamp: Long): Boolean {
        when (objectType) {
            _USA -> return taskWeek > 1 && taskWeek < 7
            _MOEX -> return (taskWeek > 1 && taskWeek < 7) || taskTimestamp in moexWorkWeek
            _CRYPTO -> return true
        }
        return false
    }
}