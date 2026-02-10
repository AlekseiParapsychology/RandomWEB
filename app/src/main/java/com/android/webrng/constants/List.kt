package com.android.webrng.constants

import android.graphics.Color
import com.android.webrng.R
import com.android.webrng.utils.UtcDate
import com.android.webrng.utils.utcFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.log10


var GlobalTest = false
const val ResourceTest = false
const val SmilePath = "/Pictures/SmileCapture"
const val ResultPath = "/Pictures/SmileResult"

const val _PARAM0 = 0
//_OTHER
const val _PARAM1 = 1
//_4RP_ES
const val _PARAM2 = 2
//_4RA_ES
const val _PARAM3 = 3
//_8RA_ES
const val _PARAM4 = 4
//_4RP_MO
const val _PARAM5 = 5
//_NAT_MO
const val _PARAM6 = 6
//_4RP_ME
const val _PARAM7 = 7
//_8RP_ME
const val _PARAM8 = 8
//_NAT_ME

const val _OTHER = 0
const val _USA = 1
const val _MOEX = 2
const val _CRYPTO = 3

const val _NYSE = 10 + _USA
const val _NASDAQ = 20 + _USA

const val _1V = 0
const val _1H = 1
const val _2V = 2
const val _2H = 3
const val _1VH = 4
const val _2VH = 5

const val _1RV = 4
const val _1LV = 5
const val _2RV = 6
const val _2LV = 7
const val _1RH = 8
const val _1LH = 9

const val _VERTICAL_S = 0
const val _HORIZONTAL_S = 1
const val _VERTICAL_R = 2
const val _HORIZONTAL_R = 3

const val currentGreenwichOffset = 3
const val weekUniversalOffset = 1

//var outputGreenwichOffset
const val outputGreenwichOffset = 3
const val universalMarketTime = 3

const val maxNameLength = 30
const val connectTimeoutServer = 15000
const val readTimeoutServer = 60000

var marketES = hashMapOf<Int, Int>(_USA to 365, _MOEX to 365, _CRYPTO to 365)
//val marketES = hashMapOf<Int, Int>(_USA to 252, _MOEX to 255, _CRYPTO to 365)
var usHolidays = arrayOf<Long>()
var moexHolidays = arrayOf<Long>()
var moexWorkWeek = arrayOf<Long>()
//val usHolidays = arrayOf<Long>(1642377600, 1645401600, 1649980800, 1653868800, 1655683200, 1656892800, 1662336000, 1672012800)
//val moexHolidays = arrayOf<Long>(1641513600, 1645574400, 1646697600, 1651449600, 1652054400, 1667520000)
//val moexWorkWeek = arrayOf<Long>(1646438400)
val marketStartHour = hashMapOf<Int, Int>(_USA to 16, _MOEX to 7, _CRYPTO to 0)
val marketStartMinute = hashMapOf<Int, Int>(_USA to 30, _MOEX to 0, _CRYPTO to 0)
val exchangeMap: HashMap<String, Int> = hashMapOf("USA" to _USA, "NYSE" to _NYSE, "NASDAQ" to _NASDAQ, "MOEX" to _MOEX, "CRYPTO" to _CRYPTO, "OTHER" to _OTHER)

const val epicStrMax = 0
const val epicSuper: Long = 0L
const val epicValue1: Int = 50
const val epicValue2: Int = 400

//<-------------------------------------------------------------------------------------------------Scale
val typeCurrency: HashMap<Int, String> = hashMapOf(_USA to "$", _MOEX to "₽", _CRYPTO to "$")
val typeCurrencyValue: HashMap<Int, String> = hashMapOf(_USA to "\$USA Stock Price", _MOEX to "₽Rouble Stock Price", _CRYPTO to "\$USA Price Rate")
val epicSauron: HashMap<Int, Int> = hashMapOf(_USA to 23, _MOEX to 19, _CRYPTO to 24)
val epicMorgoth: HashMap<Int, Int> = hashMapOf(_USA to 0, _MOEX to 0, _CRYPTO to 0)
val tradeRange: HashMap<Int, Float> = hashMapOf(_USA to 6.5f, _MOEX to 12f, _CRYPTO to 24f)
val ObjectPosX: HashMap<Int, FloatArray> = hashMapOf(
        _CRYPTO to floatArrayOf(240f, 550f, 0f, 400f, 205f))
val ObjectPosY: HashMap<Int, FloatArray> = hashMapOf(
        _CRYPTO to floatArrayOf(310f, 0f, 280f, 0f, 0f))
val ObjectSizeX: HashMap<Int, IntArray> = hashMapOf(
        _CRYPTO to intArrayOf(240, 250, 240, 400, 180))
val ObjectSizeY: HashMap<Int, IntArray> = hashMapOf(
        _CRYPTO to intArrayOf(120, 165, 120, 89, 144))
val objectResource1V: HashMap<String, Int> = hashMapOf("BTC" to R.array.btc_v, "ETH" to R.array.eth_v)
val objectResource1H: HashMap<String, Int> = hashMapOf("BTC" to R.array.btc_1h, "ETH" to R.array.eth_1h)
val objectResource2V: HashMap<String, Int> = hashMapOf("BTC" to R.array.btc_v, "ETH" to R.array.eth_v)
val objectResource2H: HashMap<String, Int> = hashMapOf("BTC" to R.array.btc_2h, "ETH" to R.array.eth_2h)

const val fontName = "Arial"
val epicRange: HashMap<Int, Int> = hashMapOf(_PARAM1 to 4, _PARAM2 to 0, _PARAM3 to 0, _PARAM4 to 4, _PARAM5 to 2, _PARAM6 to 4, _PARAM7 to 8, _PARAM8 to 2)
val specialList1 = arrayOf<Int>(_PARAM6, _PARAM7, _PARAM8)
val specialList2 = arrayOf<Int>(_PARAM1, _PARAM2, _PARAM3)
val specialList3 = arrayOf<Int>(_PARAM5, _PARAM8)
val specialList4 = arrayOf<Int>(_PARAM2, _PARAM3)
const val intraValue = 1
const val specialSectorValue = 2
val JapaneseCandleList = arrayOf(_PARAM2, _PARAM3, _PARAM4, _PARAM5)
const val JapaneseCandleTotal = 50
val arraySpecialGraph: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM2 to floatArrayOf(105f, 201f, 102f, 201f, 102f),
        _PARAM3 to floatArrayOf(105f, 201f, 102f, 201f, 102f),
        _PARAM4 to floatArrayOf(3f, 5f, 3f, 5f, 3f),
        _PARAM5 to floatArrayOf(4f, 6f, 4f, 6f, 4f),
        _PARAM6 to floatArrayOf(106f, 201f, 103f, 201f, 103f),
        _PARAM7 to floatArrayOf(106f, 201f, 103f, 201f, 103f),
        _PARAM8 to floatArrayOf(173f, 211f, 167f, 211f, 167f))
val arraySpecialGraphText: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(347f, 602f, 313f, 602f, 308f),
        _PARAM2 to floatArrayOf(347f, 602f, 313f, 602f, 308f),
        _PARAM3 to floatArrayOf(347f, 602f, 313f, 602f, 308f),
        _PARAM4 to floatArrayOf(347f, 602f, 313f, 602f, 308f),
        _PARAM5 to floatArrayOf(413f, 611f, 377f, 611f, 372f),
        _PARAM6 to floatArrayOf(347f, 602f, 313f, 602f, 308f),
        _PARAM7 to floatArrayOf(347f, 602f, 313f, 602f, 308f),
        _PARAM8 to floatArrayOf(413f, 611f, 377f, 611f, 372f))
val arrayGraphOffsetY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM2 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM3 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM4 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM5 to floatArrayOf(1f, 1f, 1f, 1f, 1f),
        _PARAM6 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM7 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM8 to floatArrayOf(1f, 1f, 1f, 1f, 1f))
//    val JapaneseCandleSize: HashMap<Int, FloatArray> = hashMapOf(
//            _4RA_ES to floatArrayOf(3f, 7f, 3f, 7f))
val specialResource: HashMap<Int, IntArray> = hashMapOf(
        _PARAM1 to intArrayOf(R.array.t1_type_1, R.array.t1_type_2),
    //    _PARAM3 to intArrayOf(R.array.t5),
        _PARAM4 to intArrayOf(R.array.t1_type_1, R.array.t1_type_2),
        _PARAM5 to intArrayOf(R.array.t4_type_1, R.array.t4_type_2),
        _PARAM6 to intArrayOf(R.array.t1_type_1, R.array.t1_type_2),
        _PARAM7 to intArrayOf(R.array.t1_type_1, R.array.t1_type_2),
        _PARAM8 to intArrayOf(R.array.t4_type_1, R.array.t4_type_2))
val specialResourcePosX: HashMap<Int, Array<FloatArray>> = hashMapOf(
        _PARAM1 to arrayOf<FloatArray>(floatArrayOf(52f, 93f, 37f, 93f, 50f), floatArrayOf(359f, 637f, 323f, 637f, 299f), floatArrayOf(119f, 198f, 100f, 198f, 107f), floatArrayOf(0f, 0f, 0f, 0f, 320f)),
        _PARAM3 to arrayOf<FloatArray>(floatArrayOf(0f, 0f, 0f, 0f, 0f)),
        _PARAM4 to arrayOf<FloatArray>(floatArrayOf(52f, 93f, 37f, 93f, 50f), floatArrayOf(359f, 637f, 323f, 637f, 299f), floatArrayOf(119f, 198f, 100f, 198f, 107f), floatArrayOf(0f, 0f, 0f, 0f, 320f)),
        _PARAM5 to arrayOf<FloatArray>(floatArrayOf(352f, 550f, 316f, 550f, 311f), floatArrayOf(343f, 541f, 307f, 541f, 302f), floatArrayOf(0f, 0f, 0f, 0f, 0f)),
        _PARAM6 to arrayOf<FloatArray>(floatArrayOf(52f, 93f, 37f, 93f, 50f), floatArrayOf(359f, 637f, 323f, 637f, 299f), floatArrayOf(119f, 198f, 100f, 198f, 107f), floatArrayOf(0f, 0f, 0f, 0f, 320f)),
        _PARAM7 to arrayOf<FloatArray>(floatArrayOf(52f, 93f, 37f, 93f, 50f), floatArrayOf(359f, 637f, 323f, 637f, 299f), floatArrayOf(0f, 0f, 0f, 0f, 0f), floatArrayOf(0f, 0f, 0f, 0f, 320f)),
        _PARAM8 to arrayOf<FloatArray>(floatArrayOf(352f, 550f, 316f, 550f, 316f), floatArrayOf(343f, 541f, 307f, 541f, 307f), floatArrayOf(0f, 0f, 0f, 0f, 0f)))
val specialResourcePosY: HashMap<Int, Array<FloatArray>> = hashMapOf(
        _PARAM1 to arrayOf<FloatArray>(floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(224f, 193f, 266f, 188f, 235f), floatArrayOf(0f, 0f, 0f, 0f, 181f)),
        _PARAM3 to arrayOf<FloatArray>(floatArrayOf(310f, 0f, 350f, 0f, 313f)),
        _PARAM4 to arrayOf<FloatArray>(floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(224f, 193f, 266f, 188f, 235f), floatArrayOf(0f, 0f, 0f, 0f, 181f)),
        _PARAM5 to arrayOf<FloatArray>(floatArrayOf(166f, 120f, 206f, 120f, 173f), floatArrayOf(277f, 231f, 317f, 231f, 284f), floatArrayOf(296f, 250f, 336f, 250f, 303f)),
        _PARAM6 to arrayOf<FloatArray>(floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(224f, 193f, 266f, 188f, 235f), floatArrayOf(0f, 0f, 0f, 0f, 181f)),
        _PARAM7 to arrayOf<FloatArray>(floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(192f, 158f, 234f, 153f, 201f), floatArrayOf(310f, 0f, 350f, 0f, 313f), floatArrayOf(0f, 0f, 0f, 0f, 181f)),
        _PARAM8 to arrayOf<FloatArray>(floatArrayOf(166f, 120f, 206f, 120f, 173f), floatArrayOf(277f, 231f, 317f, 231f, 284f), floatArrayOf(296f, 250f, 336f, 250f, 303f)))
val specialResourceSizeX: HashMap<Int, Array<IntArray>> = hashMapOf(
        _PARAM1 to arrayOf<IntArray>(intArrayOf(0, 0, 39, 0, 39), intArrayOf(0, 0, 80, 0, -1), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-1, -1, -1, -1, 39)),
        _PARAM3 to arrayOf<IntArray>(intArrayOf(480, -1, 420, -1, 411)),
        _PARAM4 to arrayOf<IntArray>(intArrayOf(0, 0, 39, 0, 39), intArrayOf(0, 0, 80, 0, -1), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-1, -1, -1, -1, 39)),
        _PARAM5 to arrayOf<IntArray>(intArrayOf(0, 0, 0, 0, 0), intArrayOf(0, 0, 0, 0, 0), intArrayOf(480, 800, 420, 800, 411)),
        _PARAM6 to arrayOf<IntArray>(intArrayOf(0, 0, 39, 0, 39), intArrayOf(0, 0, 80, 0, -1), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-1, -1, -1, -1, 39)),
        _PARAM7 to arrayOf<IntArray>(intArrayOf(0, 0, 39, 0, 39), intArrayOf(0, 0, 80, 0, -1), intArrayOf(480, -1, 420, -1, 411), intArrayOf(-1, -1, -1, -1, 39)),
        _PARAM8 to arrayOf<IntArray>(intArrayOf(0, 0, 0, 0, 0), intArrayOf(0, 0, 0, 0, 0), intArrayOf(480, 800, 420, 800, 411)))
val specialResourceSizeY: HashMap<Int, Array<IntArray>> = hashMapOf(
        _PARAM1 to arrayOf<IntArray>(intArrayOf(0, 0, 20, 0, 20), intArrayOf(0, 0, 20, 0, -1), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-1, -1, -1, -1, 40)),
        _PARAM3 to arrayOf<IntArray>(intArrayOf(-2, -1, -2, -1, -2)),
        _PARAM4 to arrayOf<IntArray>(intArrayOf(0, 0, 20, 0, 20), intArrayOf(0, 0, 20, 0, -1), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-1, -1, -1, -1, 40)),
        _PARAM5 to arrayOf<IntArray>(intArrayOf(0, 0, 0, 0, 0), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-2, -2, -2, -2, -2)),
        _PARAM6 to arrayOf<IntArray>(intArrayOf(0, 0, 20, 0, 20), intArrayOf(0, 0, 20, 0, -1), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-1, -1, -1, -1, 40)),
        _PARAM7 to arrayOf<IntArray>(intArrayOf(0, 0, 20, 0, 20), intArrayOf(0, 0, 20, 0, -1), intArrayOf(-2, -1, -2, -1, -2), intArrayOf(-1, -1, -1, -1, 40)),
        _PARAM8 to arrayOf<IntArray>(intArrayOf(0, 0, 0, 0, 0), intArrayOf(0, 0, 0, 0, 0), intArrayOf(-2, -2, -2, -2, -2)))
val matrixArray: HashMap<Int, Array<FloatArray>> = hashMapOf(
        _PARAM1 to arrayOf<FloatArray>(
                floatArrayOf(1f, 15f, 31f, 44f, 59f, 72f, 88f, 101f, 116f, 129f, 145f, 158f, 173f, 186f),
                floatArrayOf(1f, 21f, 43f, 63f, 85f, 105f, 127f, 147f, 170f, 190f, 212f, 232f, 254f, 274f, 297f, 317f, 339f, 359f),
                floatArrayOf(1f, 14f, 29f, 42f, 57f, 69f, 84f, 97f, 112f, 125f, 140f, 152f, 167f, 180f),
                floatArrayOf(1f, 21f, 43f, 63f, 85f, 105f, 127f, 147f, 170f, 190f, 212f, 232f, 254f, 274f, 297f, 317f, 339f, 359f),
                floatArrayOf(1f, 14f, 29f, 42f, 57f, 69f, 84f, 97f, 112f, 125f, 140f, 152f, 167f, 180f)),
        _PARAM2 to arrayOf<FloatArray>(
                floatArrayOf(1f, 11f, 23f, 32f, 44f, 53f, 65f, 74f, 86f, 95f),
                floatArrayOf(1f, 21f, 42f, 61f, 82f, 101f, 122f, 141f, 162f, 181f),
                floatArrayOf(1f, 11f, 23f, 32f, 43f, 52f, 64f, 73f, 84f, 93f),
                floatArrayOf(1f, 21f, 42f, 61f, 82f, 101f, 122f, 141f, 162f, 181f),
                floatArrayOf(1f, 11f, 23f, 32f, 43f, 52f, 64f, 73f, 84f, 93f)),
        _PARAM3 to arrayOf<FloatArray>(
                floatArrayOf(1f, 11f, 23f, 32f, 44f, 53f, 65f, 74f, 86f, 95f),
                floatArrayOf(1f, 21f, 42f, 61f, 82f, 101f, 122f, 141f, 162f, 181f),
                floatArrayOf(1f, 11f, 23f, 32f, 43f, 52f, 64f, 73f, 84f, 93f),
                floatArrayOf(1f, 21f, 42f, 61f, 82f, 101f, 122f, 141f, 162f, 181f),
                floatArrayOf(1f, 11f, 23f, 32f, 43f, 52f, 64f, 73f, 84f, 93f)),
        _PARAM4 to arrayOf<FloatArray>(
                floatArrayOf(1f, 15f, 31f, 44f, 59f, 72f, 88f, 101f, 116f, 129f, 145f, 158f, 173f, 186f),
                floatArrayOf(1f, 21f, 43f, 63f, 85f, 105f, 127f, 147f, 170f, 190f, 212f, 232f, 254f, 274f, 297f, 317f, 339f, 359f),
                floatArrayOf(1f, 14f, 29f, 42f, 57f, 69f, 84f, 97f, 112f, 125f, 140f, 152f, 167f, 180f),
                floatArrayOf(1f, 21f, 43f, 63f, 85f, 105f, 127f, 147f, 170f, 190f, 212f, 232f, 254f, 274f, 297f, 317f, 339f, 359f),
                floatArrayOf(1f, 14f, 29f, 42f, 57f, 69f, 84f, 97f, 112f, 125f, 140f, 152f, 167f, 180f)),
        _PARAM5 to arrayOf<FloatArray>(
                floatArrayOf(1f, 25f, 50f, 73f, 98f, 121f, 146f, 169f, 194f, 217f, 242f, 265f, 290f, 312f),
                floatArrayOf(1f, 23f, 47f, 69f, 93f, 115f, 138f, 160f, 184f, 206f, 230f, 252f, 275f, 297f, 321f, 343f, 367f, 388f),
                floatArrayOf(1f, 23f, 48f, 70f, 94f, 116f, 140f, 162f, 187f, 209f, 233f, 255f, 279f, 300f),
                floatArrayOf(1f, 23f, 47f, 69f, 93f, 115f, 138f, 160f, 184f, 206f, 230f, 252f, 275f, 297f, 321f, 343f, 367f, 388f),
                floatArrayOf(1f, 23f, 48f, 70f, 94f, 116f, 140f, 162f, 187f, 209f, 233f, 255f, 279f, 300f)),
        _PARAM6 to arrayOf<FloatArray>(
                floatArrayOf(1f, 13f, 27f, 39f, 54f, 66f, 81f, 93f, 107f, 119f, 134f, 146f, 161f, 173f, 187f, 200f),
                floatArrayOf(1f, 22f, 46f, 67f, 90f, 111f, 135f, 156f, 180f, 201f, 224f, 245f, 269f, 290f, 314f, 335f, 358f, 379f),
                floatArrayOf(1f, 16f, 33f, 46f, 62f, 76f, 92f, 106f, 122f, 136f, 152f, 166f, 183f, 194f),
                floatArrayOf(1f, 22f, 46f, 67f, 90f, 111f, 135f, 156f, 180f, 201f, 224f, 245f, 269f, 290f, 314f, 335f, 358f, 379f),
                floatArrayOf(1f, 16f, 33f, 46f, 62f, 76f, 92f, 106f, 122f, 136f, 152f, 166f, 183f, 194f),),
        _PARAM7 to arrayOf<FloatArray>(
                floatArrayOf(1f, 13f, 27f, 39f, 54f, 66f, 81f, 93f, 107f, 119f, 134f, 146f, 161f, 173f, 187f, 200f),
                floatArrayOf(1f, 22f, 46f, 67f, 90f, 111f, 135f, 156f, 180f, 201f, 224f, 245f, 269f, 290f, 314f, 335f, 358f, 379f),
                floatArrayOf(1f, 16f, 33f, 46f, 62f, 76f, 92f, 106f, 122f, 136f, 152f, 166f, 183f, 194f),
                floatArrayOf(1f, 22f, 46f, 67f, 90f, 111f, 135f, 156f, 180f, 201f, 224f, 245f, 269f, 290f, 314f, 335f, 358f, 379f),
                floatArrayOf(1f, 16f, 33f, 46f, 62f, 76f, 92f, 106f, 122f, 136f, 152f, 166f, 183f, 194f)),
        _PARAM8 to arrayOf<FloatArray>(
                floatArrayOf(1f, 25f, 50f, 73f, 98f, 121f, 146f, 169f, 194f, 217f, 242f, 265f, 290f, 312f),
                floatArrayOf(1f, 23f, 47f, 69f, 93f, 115f, 138f, 160f, 184f, 206f, 230f, 252f, 275f, 297f, 321f, 343f, 367f, 388f),
                floatArrayOf(1f, 23f, 48f, 70f, 94f, 116f, 140f, 162f, 187f, 209f, 233f, 255f, 279f, 300f),
                floatArrayOf(1f, 23f, 47f, 69f, 93f, 115f, 138f, 160f, 184f, 206f, 230f, 252f, 275f, 297f, 321f, 343f, 367f, 388f),
                floatArrayOf(1f, 23f, 48f, 70f, 94f, 116f, 140f, 162f, 187f, 209f, 233f, 255f, 279f, 300f)))

//val specialAreaSize: IntArray = intArrayOf(184, 184, 184, 184)
val superPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(249f, 220f, 289f, 215f, 256f),
        _PARAM2 to floatArrayOf(221f, 188f, 264f, 183f, 231f),
        _PARAM3 to floatArrayOf(204f, 170f, 246f, 165f, 213f),
        _PARAM4 to floatArrayOf(249f, 220f, 289f, 215f, 256f),
        _PARAM5 to floatArrayOf(296f, 250f, 336f, 250f, 303f),
        _PARAM6 to floatArrayOf(249f, 220f, 289f, 215f, 256f),
        _PARAM7 to floatArrayOf(238f, 220f, 278f, 215f, 245f),
        _PARAM8 to floatArrayOf(296f, 250f, 336f, 250f, 303f))

val floatEpicPosX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM4 to floatArrayOf(308f, 308f, 306f, 308f, 306f),
        _PARAM5 to floatArrayOf(308f, 309f, 306f, 309f, 307f))
val floatEpicPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM4 to floatArrayOf(69f, 84f, 89f, 76f, 84f),
        _PARAM5 to floatArrayOf(69f, 84f, 89f, 76f, 84f))
val floatEpicSizeX: HashMap<Int, IntArray> = hashMapOf(
        _PARAM4 to intArrayOf(87, 87, 74, 95, 80),
        _PARAM5 to intArrayOf(87, 87, 74, 95, 80))
val floatEpicSizeY: HashMap<Int, IntArray> = hashMapOf(
        _PARAM4 to intArrayOf(87, 87, 74, 95, 80),
        _PARAM5 to intArrayOf(87, 87, 74, 95, 80))
val floatLogoPosX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(15f, 14f, 28f, 7f, 22f),
        _PARAM2 to floatArrayOf(15f, 14f, 28f, 7f, 22f),
        _PARAM3 to floatArrayOf(15f, 14f, 28f, 7f, 22f),
        _PARAM4 to floatArrayOf(15f, 14f, 28f, 7f, 22f),
        _PARAM5 to floatArrayOf(18f, 17f, 31f, 10f, 25f),
        _PARAM6 to floatArrayOf(15f, 14f, 28f, 7f, 22f),
        _PARAM7 to floatArrayOf(15f, 14f, 28f, 7f, 22f),
        _PARAM8 to floatArrayOf(15f, 14f, 40f, 7f, 40f))
val floatLogoPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(115f, 115f, 125f, 108f, 121f),
        _PARAM2 to floatArrayOf(115f, 115f, 125f, 108f, 121f),
        _PARAM3 to floatArrayOf(115f, 115f, 125f, 108f, 121f),
        _PARAM4 to floatArrayOf(115f, 115f, 125f, 108f, 121f),
        _PARAM5 to floatArrayOf(94f, 94f, 107f, 89f, 102f),
        _PARAM6 to floatArrayOf(115f, 115f, 125f, 108f, 121f),
        _PARAM7 to floatArrayOf(115f, 115f, 125f, 108f, 121f),
        _PARAM8 to floatArrayOf(103f, 102f, 115f, 94f, 115f))
val floatLogoSizeX: HashMap<Int, IntArray> = hashMapOf(
        _PARAM1 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM2 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM3 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM4 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM5 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM6 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM7 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM8 to intArrayOf(74, 74, 64, 80, 64))
val floatLogoSizeY: HashMap<Int, IntArray> = hashMapOf(
        _PARAM1 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM2 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM3 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM4 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM5 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM6 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM7 to intArrayOf(80, 80, 70, 87, 74),
        _PARAM8 to intArrayOf(74, 74, 64, 80, 64))

val epicPosX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM4 to floatArrayOf(350f, 612f, 319f, 612f, 319f),
        _PARAM5 to floatArrayOf(350f, 629f, 321f, 629f, 319f),
        _PARAM6 to floatArrayOf(240f, 550f, 240f, 0f, 240f),
        _PARAM7 to floatArrayOf(240f, 550f, 240f, 0f, 240f),
        _PARAM8 to floatArrayOf(240f, 550f, 240f, 0f, 240f))
val epicPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM4 to floatArrayOf(60f, 7f, 135f, 4f, 102f),
        _PARAM5 to floatArrayOf(44f, 22f, 115f, 22f, 79f),
        _PARAM6 to floatArrayOf(310f, 0f, 280f, 89f, 0f),
        _PARAM7 to floatArrayOf(310f, 0f, 280f, 89f, 0f),
        _PARAM8 to floatArrayOf(310f, 0f, 280f, 89f, 0f))
val epicSizeX: HashMap<Int, IntArray> = hashMapOf(
        _PARAM4 to intArrayOf(120, 145, 87, 145, 80),
        _PARAM5 to intArrayOf(120, 145, 87, 145, 80),
        _PARAM6 to intArrayOf(240, 250, 240, 194, 180),
        _PARAM7 to intArrayOf(240, 250, 240, 194, 180),
        _PARAM8 to intArrayOf(240, 250, 240, 194, 180))
val epicSizeY: HashMap<Int, IntArray> = hashMapOf(
        _PARAM4 to intArrayOf(120, 145, 87, 145, 80),
        _PARAM5 to intArrayOf(120, 145, 87, 145, 80),
        _PARAM6 to intArrayOf(120, 165, 120, 96, 72),
        _PARAM7 to intArrayOf(120, 165, 120, 96, 72),
        _PARAM8 to intArrayOf(120, 165, 120, 96, 72))
val logoPosX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM0 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM1 to floatArrayOf(12f, 42f, 9f, 42f, 15f),
        _PARAM2 to floatArrayOf(12f, 42f, 9f, 42f, 15f),
        _PARAM3 to floatArrayOf(12f, 42f, 9f, 42f, 15f),
        _PARAM4 to floatArrayOf(12f, 42f, 9f, 42f, 15f),
        _PARAM5 to floatArrayOf(14f, 22f, 12f, 22f, 16f),
        _PARAM6 to floatArrayOf(12f, 42f, 9f, 42f, 15f),
        _PARAM7 to floatArrayOf(12f, 42f, 9f, 42f, 15f),
        _PARAM8 to floatArrayOf(10f, 22f, 6f, 22f, 16f))
val logoPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM0 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM1 to floatArrayOf(76f, 7f, 141f, 4f, 108f),
        _PARAM2 to floatArrayOf(76f, 7f, 141f, 4f, 108f),
        _PARAM3 to floatArrayOf(76f, 7f, 141f, 4f, 108f),
        _PARAM4 to floatArrayOf(76f, 7f, 141f, 4f, 108f),
        _PARAM5 to floatArrayOf(49f, 22f, 115f, 22f, 79f),
        _PARAM6 to floatArrayOf(76f, 7f, 141f, 4f, 108f),
        _PARAM7 to floatArrayOf(76f, 7f, 141f, 4f, 108f),
        _PARAM8 to floatArrayOf(49f, 22f, 112f, 22f, 79f))
val logoSizeX: HashMap<Int, IntArray> = hashMapOf(
        _PARAM0 to intArrayOf(480, 800, 480, 800, 393),
        _PARAM1 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM2 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM3 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM4 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM5 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM6 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM7 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM8 to intArrayOf(110, 145, 87, 145, 80))
val logoSizeY: HashMap<Int, IntArray> = hashMapOf(
        _PARAM0 to intArrayOf(785, 465, 800, 480, 480),
        _PARAM1 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM2 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM3 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM4 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM5 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM6 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM7 to intArrayOf(110, 145, 87, 145, 80),
        _PARAM8 to intArrayOf(110, 145, 87, 145, 80))

val stockAxisX: HashMap<Int, Float> = hashMapOf(
        _1V to 239f, _1H to 398f, _2V to 209f, _2H to 398f, _1VH to 204f)
val scaleAxisX: HashMap<Int, Float> = hashMapOf(
        _1V to 240f, _1H to 400f, _2V to 210f, _2H to 400f, _1VH to 205f)
val currencyAxisX: HashMap<Int, Float> = hashMapOf(
        _1V to 239f, _1H to 398f, _2V to 209f, _2H to 398f, _1VH to 204f)
val stockIndexPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(12f, 7f, 52f, 7f, 19f),
        _PARAM2 to floatArrayOf(12f, 7f, 52f, 7f, 19f),
        _PARAM3 to floatArrayOf(12f, 7f, 52f, 7f, 19f),
        _PARAM4 to floatArrayOf(12f, 7f, 52f, 7f, 19f),
        _PARAM5 to floatArrayOf(21f, 7f, 61f, 7f, 28f),
        _PARAM6 to floatArrayOf(12f, 7f, 52f, 7f, 19f),
        _PARAM7 to floatArrayOf(12f, 7f, 52f, 7f, 19f),
        _PARAM8 to floatArrayOf(21f, 7f, 61f, 7f, 28f))
val currencyValuePosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(52f, 43f, 93f, 43f, 60f),
        _PARAM2 to floatArrayOf(52f, 43f, 93f, 43f, 60f),
        _PARAM3 to floatArrayOf(52f, 43f, 93f, 43f, 60f),
        _PARAM4 to floatArrayOf(52f, 43f, 93f, 43f, 60f),
        _PARAM5 to floatArrayOf(61f, 43f, 100f, 43f, 67f),
        _PARAM6 to floatArrayOf(52f, 43f, 93f, 43f, 60f),
        _PARAM7 to floatArrayOf(52f, 43f, 93f, 43f, 60f),
        _PARAM8 to floatArrayOf(61f, 43f, 100f, 43f, 67f))
val epicValuePosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(80f, 66f, 120f, 66f, 87f),
        _PARAM2 to floatArrayOf(80f, 66f, 120f, 66f, 87f),
        _PARAM3 to floatArrayOf(80f, 66f, 120f, 66f, 87f),
        _PARAM4 to floatArrayOf(80f, 66f, 120f, 66f, 87f),
        _PARAM5 to floatArrayOf(89f, 66f, 129f, 66f, 96f),
        _PARAM6 to floatArrayOf(79f, 66f, 119f, 66f, 86f),
        _PARAM7 to floatArrayOf(79f, 66f, 119f, 66f, 86f),
        _PARAM8 to floatArrayOf(89f, 66f, 129f, 66f, 96f))
val utcPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(99f, 84f, 139f, 84f, 106f),
        _PARAM2 to floatArrayOf(99f, 84f, 139f, 84f, 106f),
        _PARAM3 to floatArrayOf(99f, 84f, 139f, 84f, 106f),
        _PARAM4 to floatArrayOf(99f, 84f, 139f, 84f, 106f),
        _PARAM5 to floatArrayOf(108f, 84f, 148f, 84f, 115f),
        _PARAM6 to floatArrayOf(100f, 86f, 140f, 86f, 107f),
        _PARAM7 to floatArrayOf(100f, 86f, 140f, 86f, 107f),
        _PARAM8 to floatArrayOf(110f, 86f, 150f, 86f, 117f))
val sectorPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(116f, 98f, 156f, 98f, 123f),
        _PARAM2 to floatArrayOf(116f, 98f, 156f, 98f, 123f),
        _PARAM3 to floatArrayOf(116f, 98f, 156f, 98f, 123f),
        _PARAM4 to floatArrayOf(116f, 98f, 156f, 98f, 123f),
        _PARAM5 to floatArrayOf(125f, 98f, 165f, 98f, 132f),
        _PARAM6 to floatArrayOf(116f, 99f, 156f, 99f, 123f),
        _PARAM7 to floatArrayOf(116f, 99f, 156f, 99f, 123f),
        _PARAM8 to floatArrayOf(125f, 99f, 165f, 99f, 132f))
val sectorArray = floatArrayOf(1f, 19f, 28f, 46f, 55f, 73f, 82f, 100f, 109f, 127f)
val stockNamePosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(129f, 105f, 169f, 105f, 136f),
        _PARAM2 to floatArrayOf(129f, 105f, 169f, 105f, 136f),
        _PARAM3 to floatArrayOf(129f, 105f, 169f, 105f, 136f),
        _PARAM4 to floatArrayOf(129f, 105f, 169f, 105f, 136f),
        _PARAM5 to floatArrayOf(138f, 105f, 178f, 105f, 145f),
        _PARAM6 to floatArrayOf(129f, 106f, 169f, 106f, 136f),
        _PARAM7 to floatArrayOf(129f, 106f, 169f, 106f, 136f),
        _PARAM8 to floatArrayOf(138f, 106f, 178f, 106f, 145f))
val currentPricePosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(210f, 181f, 250f, 179f, 217f),
        _PARAM2 to floatArrayOf(214f, 180f, 256f, 178f, 223f),
        _PARAM3 to floatArrayOf(210f, 180f, 250f, 178f, 217f),
        _PARAM4 to floatArrayOf(210f, 181f, 250f, 179f, 217f),
        _PARAM5 to floatArrayOf(380f, 578f, 344f, 578f, 339f),
        _PARAM6 to floatArrayOf(210f, 181f, 250f, 179f, 217f),
        _PARAM7 to floatArrayOf(209f, 179f, 249f, 177f, 216f),
        _PARAM8 to floatArrayOf(274f, 228f, 314f, 226f, 281f))
val priceValuePosX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(73f, 114f, 56f, 114f, 69f),
        _PARAM4 to floatArrayOf(73f, 114f, 56f, 114f, 69f),
        _PARAM5 to floatArrayOf(380f, 578f, 344f, 578f, 339f),
        _PARAM6 to floatArrayOf(73f, 114f, 56f, 114f, 69f),
        _PARAM7 to floatArrayOf(35f, 67f, 31f, 67f, 48f),
        _PARAM8 to floatArrayOf(380f, 578f, 344f, 578f, 339f))
val priceValuePosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(231f, 200f, 271f, 195f, 240f),
        _PARAM4 to floatArrayOf(231f, 200f, 271f, 195f, 240f),
        _PARAM5 to floatArrayOf(182f, 136f, 222f, 134f, 189f),
        _PARAM6 to floatArrayOf(231f, 200f, 271f, 195f, 240f),
        _PARAM7 to floatArrayOf(227f, 205f, 268f, 200f, 237f),
        _PARAM8 to floatArrayOf(182f, 136f, 222f, 134f, 189f))
val graphPosX: HashMap<Int, IntArray> = hashMapOf(
        _PARAM1 to intArrayOf(134, 198, 106, 198, 101),
        _PARAM2 to intArrayOf(134, 198, 106, 198, 101),
        _PARAM3 to intArrayOf(134, 198, 106, 198, 101),
        _PARAM4 to intArrayOf(134, 198, 106, 198, 101),
        _PARAM5 to intArrayOf(67, 189, 43, 189, 38),
        _PARAM6 to intArrayOf(134, 198, 106, 198, 101),
        _PARAM7 to intArrayOf(134, 198, 106, 198, 101),
        _PARAM8 to intArrayOf(67, 189, 43, 189, 38))
val graphPosY: HashMap<Int, IntArray> = hashMapOf(
        _PARAM1 to intArrayOf(160, 130, 200, 128, 167),
        _PARAM2 to intArrayOf(160, 130, 200, 128, 167),
        _PARAM3 to intArrayOf(160, 130, 200, 128, 167),
        _PARAM4 to intArrayOf(160, 130, 200, 128, 167),
        _PARAM5 to intArrayOf(176, 130, 216, 128, 183),
        _PARAM6 to intArrayOf(160, 130, 200, 128, 167),
        _PARAM7 to intArrayOf(160, 130, 200, 128, 167),
        _PARAM8 to intArrayOf(176, 130, 216, 128, 183))
val epicValueGraphPosX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(336f, 585f, 302f, 585f, 297f),
        _PARAM2 to floatArrayOf(242f, 401f, 211f, 401f, 206f),
        _PARAM3 to floatArrayOf(242f, 401f, 211f, 401f, 206f),
        _PARAM4 to floatArrayOf(335f, 585f, 301f, 585f, 296f),
        _PARAM5 to floatArrayOf(388f, 586f, 352f, 586f, 347f),
        _PARAM6 to floatArrayOf(336f, 585f, 302f, 585f, 297f),
        _PARAM7 to floatArrayOf(336f, 585f, 302f, 585f, 297f),
        _PARAM8 to floatArrayOf(388f, 586f, 352f, 586f, 347f))
val epicValueGraphPosY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(185f, 155f, 225f, 153f, 192f),
        _PARAM2 to floatArrayOf(185f, 155f, 225f, 153f, 192f),
        _PARAM3 to floatArrayOf(185f, 155f, 225f, 153f, 192f),
        _PARAM4 to floatArrayOf(185f, 155f, 225f, 153f, 192f),
        _PARAM5 to floatArrayOf(228f, 182f, 268f, 180f, 235f),
        _PARAM6 to floatArrayOf(185f, 155f, 225f, 153f, 192f),
        _PARAM7 to floatArrayOf(185f, 155f, 225f, 153f, 192f),
        _PARAM8 to floatArrayOf(228f, 182f, 268f, 180f, 235f))

//combo stock exchange = 360-411 floating size
val superSizeX: HashMap<Int, Int> = hashMapOf(_1V to 480, _1H to 800, _2V to 420, _2H to 800, _1VH to 411, _2VH to 411)
val superSizeY: HashMap<Int, Int> = hashMapOf(_1V to 355, _1H to 300, _2V to 400, _2H to 295, _1VH to 465, _2VH to 480)
val intraSizeX: HashMap<Int, Int> = hashMapOf(_1RV to 480, _1LV to 480, _2RV to 480, _2LV to 480, _1RH to 393, _1LH to 393)
val intraSizeY: HashMap<Int, Int> = hashMapOf(_1RV to 396, _1LV to 360, _2RV to 411, _2LV to 375, _1RH to 465, _1LH to 465)
val stockIndexSize: HashMap<Int, Float> = hashMapOf(_1V to 31f, _1H to 31f, _2V to 31f, _2H to 31f, _1VH to 31f)
val currencyValueSize: HashMap<Int, Float> = hashMapOf(_1V to 17f, _1H to 17f, _2V to 17f, _2H to 17f, _1VH to 17f)
val utcSize: HashMap<Int, Float> = hashMapOf(
        _PARAM1 to 11f,
        _PARAM2 to 11f,
        _PARAM3 to 11f,
        _PARAM4 to 11f,
        _PARAM5 to 11f,
        _PARAM6 to 9f,
        _PARAM7 to 9f,
        _PARAM8 to 9f)
val sectorSize: HashMap<Int, Float> = hashMapOf(
        _1V to 3f, _1H to 3f, _2V to 3f, _2H to 3f, _1VH to 3f)
val epicValueSize: HashMap<Int, Float> = hashMapOf(_1V to 15f, _1H to 15f, _2V to 15f, _2H to 15f, _1VH to 15f)
val stockNameSize: HashMap<Int, Float> = hashMapOf(_1V to 15f, _1H to 17f, _2V to 15f, _2H to 15f, _1VH to 15f)
val stockNameMaxLength: HashMap<Int, Float> = hashMapOf(_1V to 214f, _1H to 280f, _2V to 200f, _2H to 280f, _1VH to 175f)
val currentPriceSize: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(9f, 9f, 9f, 7f, 9f),
        _PARAM2 to floatArrayOf(9f, 9f, 9f, 7f, 9f),
        _PARAM3 to floatArrayOf(6f, 7f, 6f, 7f, 6f),
        _PARAM4 to floatArrayOf(9f, 9f, 9f, 7f, 9f),
        _PARAM5 to floatArrayOf(7f, 7f, 7f, 7f, 7f),
        _PARAM6 to floatArrayOf(9f, 9f, 9f, 7f, 9f),
        _PARAM7 to floatArrayOf(6f, 7f, 6f, 7f, 6f),
        _PARAM8 to floatArrayOf(7f, 7f, 7f, 7f, 7f))
val priceValueSizeX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(332f, 567f, 306f, 567f, 270f),
        _PARAM4 to floatArrayOf(332f, 567f, 306f, 567f, 270f),
        _PARAM5 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM6 to floatArrayOf(332f, 567f, 306f, 567f, 270f),
        _PARAM7 to floatArrayOf(410f, 663f, 356f, 663f, 314f),
        _PARAM8 to floatArrayOf(0f, 0f, 0f, 0f, 0f))
val priceValueSizeY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM4 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM5 to floatArrayOf(88f, 88f, 88f, 88f, 88f),
        _PARAM6 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM7 to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        _PARAM8 to floatArrayOf(88f, 88f, 88f, 88f, 88f))
val priceValueSizeFont: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(20f, 24f, 20f, 24f, 18f),
        _PARAM4 to floatArrayOf(20f, 24f, 20f, 24f, 18f),
        _PARAM5 to floatArrayOf(10f, 10f, 10f, 10f, 10f),
        _PARAM6 to floatArrayOf(20f, 24f, 20f, 24f, 18f),
        _PARAM7 to floatArrayOf(12f, 18f, 11f, 18f, 9f),
        _PARAM8 to floatArrayOf(10f, 10f, 10f, 10f, 10f))
val graphSizeX: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(201f, 380f, 195f, 380f, 195f),
        _PARAM2 to floatArrayOf(107f, 202f, 104f, 202f, 104f),
        _PARAM3 to floatArrayOf(107f, 202f, 104f, 202f, 104f),
        _PARAM4 to floatArrayOf(200f, 379f, 194f, 379f, 194f),
        _PARAM5 to floatArrayOf(312f, 388f, 300f, 388f, 300f),
        _PARAM6 to floatArrayOf(201f, 380f, 195f, 380f, 195f),
        _PARAM7 to floatArrayOf(201f, 380f, 195f, 380f, 195f),
        _PARAM8 to floatArrayOf(313f, 389f, 301f, 389f, 301f))
val graphSizeY: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(50f, 50f, 50f, 50f, 50f),
        _PARAM2 to floatArrayOf(50f, 50f, 50f, 50f, 50f),
        _PARAM3 to floatArrayOf(50f, 50f, 50f, 50f, 50f),
        _PARAM4 to floatArrayOf(50f, 50f, 50f, 50f, 50f),
        _PARAM5 to floatArrayOf(100f, 100f, 100f, 100f, 100f),
        _PARAM6 to floatArrayOf(50f, 50f, 50f, 50f, 50f),
        _PARAM7 to floatArrayOf(50f, 50f, 50f, 50f, 50f),
        _PARAM8 to floatArrayOf(100f, 100f, 100f, 100f, 100f))
val finishGraphSize: HashMap<Int, Float> = hashMapOf(
        _PARAM1 to 1f,
        _PARAM2 to 2f,
        _PARAM3 to 2f,
        _PARAM4 to 1f,
        _PARAM5 to 1f,
        _PARAM6 to 1f,
        _PARAM7 to 1f,
        _PARAM8 to 1f)
val epicValueGraphSize: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(9f, 9f, 9f, 9f, 9f),
        _PARAM2 to floatArrayOf(9f, 9f, 9f, 9f, 9f),
        _PARAM3 to floatArrayOf(9f, 9f, 9f, 9f, 9f),
        _PARAM4 to floatArrayOf(9f, 9f, 9f, 9f, 9f),
        _PARAM5 to floatArrayOf(15f, 15f, 15f, 15f, 15f),
        _PARAM6 to floatArrayOf(9f, 9f, 9f, 9f, 9f),
        _PARAM7 to floatArrayOf(9f, 9f, 9f, 9f, 9f),
        _PARAM8 to floatArrayOf(15f, 15f, 15f, 15f, 15f))
val epicValueGraphScale: HashMap<Int, FloatArray> = hashMapOf(
        _PARAM1 to floatArrayOf(0.8f, 0.8f, 0.8f, 0.8f, 0.8f),
        _PARAM2 to floatArrayOf(0.8f, 0.8f, 0.8f, 0.8f, 0.8f),
        _PARAM3 to floatArrayOf(0.8f, 0.8f, 0.8f, 0.8f, 0.8f),
        _PARAM4 to floatArrayOf(0.8f, 0.8f, 0.8f, 0.8f, 0.8f),
        _PARAM5 to floatArrayOf(1f, 1f, 1f, 1f, 1f),
        _PARAM6 to floatArrayOf(0.8f, 0.8f, 0.8f, 0.8f, 0.8f),
        _PARAM7 to floatArrayOf(0.8f, 0.8f, 0.8f, 0.8f, 0.8f),
        _PARAM8 to floatArrayOf(1f, 1f, 1f, 1f, 1f))

/*  val turingColor = Color.rgb(201, 255, 68)
val superColor: HashMap<Int, Int> = hashMapOf(
        _OTHER to Color.rgb(255, 255, 255),
        _USA to Color.rgb(114, 205, 125),
        _MOEX to Color.rgb(107, 157, 14),
        _CRYPTO to Color.rgb(250, 165, 27)) */
val stockIndexColor: HashMap<Int, Int> = hashMapOf(
        _USA to Color.rgb(7, 56, 38),
        _MOEX to Color.rgb(7, 56, 38),
        _CRYPTO to Color.rgb(170, 68, 0))
val currencyValueColor: HashMap<Int, Int> = hashMapOf(
        _USA to Color.rgb(106, 158, 75),
        _MOEX to Color.rgb(106, 158, 75),
        _CRYPTO to Color.rgb(106, 158, 75))
val utcColor: HashMap<Int, Int> = hashMapOf(
        _USA to Color.rgb(222, 235, 248),
        _MOEX to Color.rgb(222, 235, 248),
        _CRYPTO to Color.rgb(222, 235, 248))
val sectorColor: HashMap<Int, Int> = hashMapOf(
        _USA to Color.rgb(200, 200, 200),
        _MOEX to Color.rgb(200, 200, 200),
        _CRYPTO to Color.rgb(200, 200, 200))
val graphColor: HashMap<Int, Int> = hashMapOf(
        _USA to Color.rgb(246, 246, 246),
        _MOEX to Color.rgb(246, 246, 246),
        _CRYPTO to Color.rgb(246, 246, 246))
val finishGraph1Color: HashMap<Int, Int> = hashMapOf(
        _PARAM1 to Color.rgb(250, 175, 185),
        _PARAM2 to Color.rgb(250, 175, 185),
        _PARAM3 to Color.rgb(250, 175, 185),
        _PARAM4 to Color.rgb(250, 175, 185),
        _PARAM5 to Color.rgb(250, 175, 185),
        _PARAM6 to Color.rgb(250, 175, 185),
        _PARAM7 to Color.rgb(250, 175, 185),
        _PARAM8 to Color.rgb(250, 175, 185))
val finishGraph2Color: HashMap<Int, Int> = hashMapOf(
        _USA to Color.rgb(234, 135, 0),
        _MOEX to Color.rgb(234, 135, 0),
        _CRYPTO to Color.rgb(234, 135, 0))
val epicValueAreaColor: HashMap<Int, Int> = hashMapOf(
        _USA to Color.rgb(255, 244, 223),
        _MOEX to Color.rgb(255, 244, 223),
        _CRYPTO to Color.rgb(255, 244, 223))
val epicValueColor: HashMap<Int, Int> = hashMapOf(
        _PARAM1 to Color.rgb(125, 175, 230),
        _PARAM2 to Color.rgb(125, 175, 230),
        _PARAM3 to Color.rgb(125, 175, 230),
        _PARAM4 to Color.rgb(125, 175, 230),
        _PARAM5 to Color.rgb(125, 175, 230),
        _PARAM6 to Color.rgb(125, 175, 230),
        _PARAM7 to Color.rgb(125, 175, 230),
        _PARAM8 to Color.rgb(125, 175, 230))
val stockNameColor: HashMap<Int, Int> = hashMapOf(
        _PARAM1 to Color.rgb(145, 145, 145),
        _PARAM2 to Color.rgb(145, 145, 145),
        _PARAM3 to Color.rgb(145, 145, 145),
        _PARAM4 to Color.rgb(145, 145, 145),
        _PARAM5 to Color.rgb(145, 145, 145),
        _PARAM6 to Color.rgb(145, 145, 145),
        _PARAM7 to Color.rgb(145, 145, 145),
        _PARAM8 to Color.rgb(145, 145, 145))
val currentPriceColor: HashMap<Int, Int> = hashMapOf(
        _PARAM1 to Color.rgb(222, 203, 254),
        _PARAM2 to Color.rgb(222, 203, 254),
        _PARAM3 to Color.rgb(222, 203, 254),
        _PARAM4 to Color.rgb(222, 203, 254),
        _PARAM5 to Color.rgb(222, 203, 254),
        _PARAM6 to Color.rgb(222, 203, 254),
        _PARAM7 to Color.rgb(222, 203, 254),
        _PARAM8 to Color.rgb(222, 203, 254))
val priceValueColor: HashMap<Int, IntArray> = hashMapOf(
        _PARAM1 to intArrayOf(Color.rgb(57, 225, 184), Color.rgb(120, 228, 105), Color.rgb(237, 154, 175), Color.rgb(254, 135, 122)),
        _PARAM4 to intArrayOf(Color.rgb(57, 225, 184), Color.rgb(120, 228, 105), Color.rgb(237, 154, 175), Color.rgb(254, 135, 122)),
        _PARAM5 to intArrayOf(Color.rgb(150, 233, 154), Color.rgb(234, 203, 217)),
        _PARAM6 to intArrayOf(Color.rgb(57, 225, 184), Color.rgb(120, 228, 105), Color.rgb(237, 154, 175), Color.rgb(254, 135, 122)),
        _PARAM7 to intArrayOf(Color.rgb(15, 200, 170), Color.rgb(57, 225, 184), Color.rgb(120, 228, 105), Color.rgb(234, 225, 117), Color.rgb(254, 205, 107), Color.rgb(237, 154, 175), Color.rgb(254, 135, 122), Color.rgb(254, 117, 100)),
        _PARAM8 to intArrayOf(Color.rgb(150, 233, 154), Color.rgb(234, 203, 217)))
val epicValueGraphColor: HashMap<Int, Int> = hashMapOf(
        _PARAM1 to Color.rgb(200, 200, 200),
        _PARAM2 to Color.rgb(250, 175, 185),
        _PARAM3 to Color.rgb(250, 175, 185),
        _PARAM4 to Color.rgb(200, 200, 200),
        _PARAM5 to Color.rgb(254, 233, 203),
        _PARAM6 to Color.rgb(200, 200, 200),
        _PARAM7 to Color.rgb(200, 200, 200),
        _PARAM8 to Color.rgb(254, 233, 203))
val graphPointColor: HashMap<Int, Int> = hashMapOf(_PARAM1 to Color.BLUE, _PARAM2 to Color.BLUE, _PARAM3 to Color.BLUE, _PARAM4 to Color.BLUE, _PARAM5 to Color.BLUE, _PARAM6 to Color.BLUE, _PARAM7 to Color.BLUE, _PARAM8 to Color.BLUE)

const val _annatar = "param1"
const val _api = "param2"
const val _array = "param3"
const val _globalRequest = "param4"
const val _inputValue = "param5"
const val _layout = "param6"
const val _layoutType = "param7"
const val _mairon = "param8"
const val _message = "param9"
const val _morgoth = "param10"
const val _paramObject = "param11"
const val _result = "param12"
const val _sauron = "param13"
const val _sector = "param14"
const val _sectorType = "param15"
const val _special = "param16"
const val _sqlFunction = "param17"
const val _usOffset = "param18"
const val _arrayStart = "param19"
const val _iluvatar = "param20"

fun Int.toNonZero() : Int {
    return if (this != 0) this else 1
}
fun Int.NumLength(): Int {
        return if (this != 0) log10(abs(this.toDouble())).toInt() + 1 else return 1
}
fun Float.toNonZero() : Float {
    return if (this != 0f) this else 1f
}
fun Float.toFinite() : Float {
    return if (this.isFinite()) this else 1f
}
fun Float?.toFinite() : Float? {
    return if (this == null) null else (if (this.isFinite()) this else 1f)
}
fun Boolean?.toInt() : Int {
    return if (this == null) 0 else if (this) 1 else 0
}
fun set_number_output(a: String, b: Int, c: Int): String {
        var output = a
        try {
                if (b % 10 != 0) {
                        val taskSize = c.NumLength() + (b % 10) - a.length
                        //Log.i("GIGA1", "=[$b]\t$a\t$c")
                        for (s in 0 until taskSize)
                                output = "0" + output
                } else if (b >= 10) {
                        val taskSize = c.NumLength() - a.length
                        //Log.i("GIGA2", "=[$b]\t$a\t$c")
                        for (s in 0 until taskSize)
                                output = "0" + output
                }
        }
        catch(e: Exception) { }
        return output
}
fun checkStockHoliday(timestamp: Long, objectType: Int): Boolean {
        when (objectType) {
                _USA -> return timestamp in usHolidays
                _MOEX -> return timestamp in moexHolidays
                _CRYPTO -> return false
        }
        return true
}
fun getCsvSeparator(): String {
        //    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        val date = UtcDate(false)
        return "yyyy-MM-dd HH:mm:ss.SSS".utcFormat(date, Int.MAX_VALUE)
}