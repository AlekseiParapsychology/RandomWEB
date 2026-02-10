package com.android.webrng.image

import android.os.Parcel
import android.os.Parcelable
import com.android.webrng.utils.PricePoint


class ImageParam : Parcelable {
    val taskType: Int
    val objectType: Int
    val stockIndex: String
    val stockName: String
    val stockLogo: String
    val epicValue: Long
    val param1: Int
    val param2: Int
    val param3: Int
    var param4: Int
    var param5: Int
    val marketCycle: Int
    val taskPrice: Float
    var currentPrice: PricePoint?
    val PriceSet: FloatArray
    val PriceArray: Array<PricePoint>?
    var SpecialPriceArray: Array<PricePoint>?
    var specialLength: Float
    val epicType: String
    val param6: Int
    val param7: Int
    val param8: Int
    var param9: Int
    var size: Int
    var density: Int
    var sleep: Int
    var url: String
    var token: String
    var mode: Int

    constructor(_input1: Int, _input2: Int, _input3: String, _input4: String, _input5: String, _input6: Long, _input7: Int, _input8: Int, _input9: Int, _input10: Int, _input11: Int, _input12: Int, _input13: Float, _input14: PricePoint?, _input15: FloatArray, _input16: Array<PricePoint>?, _input17: Array<PricePoint>?, _input18: Float, _input19: String, _input20: Int, _input21: Int, _input22: Int, _input23: Int, _input24: Int, _input25: Int, _input26: Int, _input27: String, _input28: String, _input29: Int) {
        taskType = _input1
        objectType = _input2
        stockIndex = _input3
        stockName = _input4
        stockLogo = _input5
        epicValue = _input6
        param1 = _input7
        param2 = _input8
        param3 = _input9
        param4 = _input10
        param5 = _input11
        marketCycle = _input12
        taskPrice = _input13
        currentPrice = _input14
        PriceSet = _input15
        PriceArray = _input16
        SpecialPriceArray = _input17
        specialLength = _input18
        epicType = _input19
        param6 = _input20
        param7 = _input21
        param8 = _input22
        param9 = _input23
        size = _input24
        density = _input25
        sleep = _input26
        url = _input27
        token = _input28
        mode = _input29
    }

    constructor(input: Parcel) {
        taskType = input.readInt()
        objectType = input.readInt()
        stockIndex = input.readString()
        stockName = input.readString()
        stockLogo = input.readString()
        epicValue = input.readLong()
        param1 = input.readInt()
        param2 = input.readInt()
        param3 = input.readInt()
        param4 = input.readInt()
        param5 = input.readInt()
        marketCycle = input.readInt()
        taskPrice = input.readFloat()
        currentPrice = input.readParcelable(PricePoint::class.java.classLoader)
        PriceSet = input.createFloatArray()
        PriceArray = input.createTypedArray(PricePoint.CREATOR)
        SpecialPriceArray = input.createTypedArray(PricePoint.CREATOR)
        specialLength = input.readFloat()
        epicType = input.readString()
        param6 = input.readInt()
        param7 = input.readInt()
        param8 = input.readInt()
        param9 = input.readInt()
        size = input.readInt()
        density = input.readInt()
        sleep = input.readInt()
        url = input.readString()
        token = input.readString()
        mode = input.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(taskType)
        out.writeInt(objectType)
        out.writeString(stockIndex)
        out.writeString(stockName)
        out.writeString(stockLogo)
        out.writeLong(epicValue)
        out.writeInt(param1)
        out.writeInt(param2)
        out.writeInt(param3)
        out.writeInt(param4)
        out.writeInt(param5)
        out.writeInt(marketCycle)
        out.writeFloat(taskPrice)
        out.writeParcelable(currentPrice, 0)
        out.writeFloatArray(PriceSet)
        out.writeTypedArray(PriceArray, 0)
        out.writeTypedArray(SpecialPriceArray, 0)
        out.writeFloat(specialLength)
        out.writeString(epicType)
        out.writeInt(param6)
        out.writeInt(param7)
        out.writeInt(param8)
        out.writeInt(param9)
        out.writeInt(size)
        out.writeInt(density)
        out.writeInt(sleep)
        out.writeString(url)
        out.writeString(token)
        out.writeInt(mode)
    }

    fun set(_input: Int) {
        param9 = _input
    }

        companion object {
            @JvmField
            //creator - used when un-parceling our parcle (creating the object)
            val CREATOR: Parcelable.Creator<ImageParam> = object : Parcelable.Creator<ImageParam> {
                override fun createFromParcel(input: Parcel): ImageParam {
                    return ImageParam(input)
                }

                override fun newArray(size: Int): Array<ImageParam?> {
                    return arrayOfNulls<ImageParam>(size)
                }
        }
    }
}