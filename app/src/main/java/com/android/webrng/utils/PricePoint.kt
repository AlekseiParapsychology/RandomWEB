package com.android.webrng.utils

import android.os.Parcel
import android.os.Parcelable


class PricePoint : Parcelable {
    var date: Long
    var open: Float
    var low: Float
    var high: Float
    var close: Float
    var liquidity: Int

    constructor(u: Long, v: Float, w: Float, x: Float, y: Float, z: Int)
    {
        date = u
        open = v
        low = w
        high = x
        close = y
        liquidity = z
    }

    constructor(input: Parcel) {
        date = input.readLong()
        open = input.readFloat()
        low = input.readFloat()
        high = input.readFloat()
        close = input.readFloat()
        liquidity = input.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeLong(date)
        out.writeFloat(open)
        out.writeFloat(low)
        out.writeFloat(high)
        out.writeFloat(close)
        out.writeInt(liquidity)
    }

        companion object {
            @JvmField
            //creator - used when un-parceling our parcle (creating the object)
            val CREATOR: Parcelable.Creator<PricePoint> = object : Parcelable.Creator<PricePoint> {
                override fun createFromParcel(input: Parcel): PricePoint {
                    return PricePoint(input)
                }

                override fun newArray(size: Int): Array<PricePoint?> {
                    return arrayOfNulls<PricePoint>(size)
                }
        }
    }
}