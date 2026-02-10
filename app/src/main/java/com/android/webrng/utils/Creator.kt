package com.android.webrng.utils


interface Creator {
    val CanReset: Boolean
    fun Reset(): Boolean
    fun Extract(minValue: Int, maxValue: Int): Int
}

class SuperCreator: Creator {
    private val IntToDoubleMultiplier: Double
            get() {
                return 1.0 / (Int.MAX_VALUE.toDouble() + 1.0)
            }
    @ExperimentalUnsignedTypes
    private val UIntToDoubleMultiplier: Double
        get() {
            return 1.0 / (UInt.MAX_VALUE.toDouble() + 1.0)
        }

    var shortLag: Int
        get() {
            return field
        }
        set(value: Int) {
            if (this.IsValidShortLag(value)) {
                field = value
            }
        }
    @ExperimentalUnsignedTypes
    var longLag: Int
        get() {
            return field
        }
        set(value: Int) {
            if (this.IsValidLongLag(value)) {
                field = value
                this.Reset()
            }
        }
    @ExperimentalUnsignedTypes
    private var x: UIntArray
    private var i = 0
    @ExperimentalUnsignedTypes
    private val seed: UInt
    @ExperimentalUnsignedTypes
    private var bitBuffer: UInt = 0U
    private var bitCount = 0

    @ExperimentalUnsignedTypes
    constructor() : this(System.currentTimeMillis().toUInt())

    @ExperimentalUnsignedTypes
    constructor(seed: Long) : this(kotlin.math.abs(seed).toUInt())

    @ExperimentalUnsignedTypes
    constructor(seed: UInt) {
        this.seed = seed
        this.shortLag = 418
        this.longLag = 1279
        this.x = UIntArray(0)
        this.ResetCreator()
    }

    private fun IsValidShortLag(value: Int): Boolean {
        return (value > 0)
    }

    private fun IsValidLongLag(value: Int): Boolean {
        return (value > this.shortLag)
    }

    @ExperimentalUnsignedTypes
    private fun ResetCreator() {
        val gen: MegaCreator = MegaCreator(this.seed)
        this.x = UIntArray(this.longLag)
        for (k in 0 until this.longLag) {
            this.x[k] = gen.ExtractUint()
        }
        this.i = this.longLag
        this.bitBuffer = 0U
        this.bitCount = 0
    }

    @ExperimentalUnsignedTypes
    private fun Fill() {
        for (k in 0 until this.shortLag) {
            this.x[k] = this.x[k] + this.x[k + (this.longLag - this.shortLag)]
        }
        for (k in this.shortLag until this.longLag) {
            this.x[k] = this.x[k] + this.x[k - this.shortLag]
        }
        this.i = 0
    }

    @ExperimentalUnsignedTypes
    private fun ExtractUInt(): UInt {
        if (this.i >= this.longLag) {
            this.Fill()
        }
        return this.x[this.i++]
    }

    //public override bool CanReset
    override val CanReset: Boolean
        get() {
            return true
        }

    //public override bool Reset()
    @ExperimentalUnsignedTypes
    override fun Reset(): Boolean {
        this.ResetCreator()
        return true
    }

    //public override int Next(int minValue, int maxValue)
    @ExperimentalUnsignedTypes
    override fun Extract(minValue: Int, maxValue: Int): Int {
        if (minValue > maxValue) {
        //    Log.i("RNG", "MinValue > MaxValue")
            return Int.MAX_VALUE
        }
        if (this.i >= this.longLag) {
            this.Fill()
        }
        var _x: UInt = this.x[this.i++]

        val range: Int = maxValue - minValue
        if (range < 0) {
            return minValue + (_x.toDouble() * this.UIntToDoubleMultiplier * (maxValue.toDouble() - minValue.toDouble())).toInt()
        }
        else {
            return minValue + ((_x.shr(1)).toInt().toDouble() * this.IntToDoubleMultiplier * range.toDouble()).toInt()
        }
    }
}

class MegaCreator: Creator {
    private val IntToDoubleMultiplier: Double
        get() {
            return 1.0 / (Int.MAX_VALUE.toDouble() + 1.0)
        }
    @ExperimentalUnsignedTypes
    private val UIntToDoubleMultiplier: Double
        get() {
            return 1.0 / (UInt.MAX_VALUE.toDouble() + 1.0)
        }

    private val N: Int = 624
    private val M: Int = 397
    @ExperimentalUnsignedTypes
    private val VectorA: UInt = 0x9908b0dfU
    @ExperimentalUnsignedTypes
    private val UpperMask: UInt = 0x80000000U
    @ExperimentalUnsignedTypes
    private val LowerMask: UInt = 0x7fffffffU

    @ExperimentalUnsignedTypes
    private var mt: UIntArray
    @ExperimentalUnsignedTypes
    private var mti: UInt = 0U
    @ExperimentalUnsignedTypes
    private val seed: UInt
    @ExperimentalUnsignedTypes
    private var seedArray: UIntArray? = null
    @ExperimentalUnsignedTypes
    private var bitBuffer: UInt = 0U
    private var bitCount: Int = 0

    @ExperimentalUnsignedTypes
    constructor() : this(System.currentTimeMillis().toUInt())

    @ExperimentalUnsignedTypes
    constructor(seed: Long) : this(kotlin.math.abs(seed).toUInt())

    @ExperimentalUnsignedTypes
    constructor(seed: UInt) {
        this.seed = seed
        this.mt = UIntArray(this.N)
        this.seedArray = null
        this.ResetCreator()
    }

    @ExperimentalUnsignedTypes
    constructor(seed: UIntArray) {
    //    if (this.seedArray == null) {
    //        Log.i("RNG", "Seed")
    //    }
        this.mt = UIntArray(this.N)
        this.seed = 19650218U
        this.seedArray = UIntArray(this.seedArray!!.size)
        for (index in 0 until seedArray!!.size) {
            this.seedArray!![index] = (kotlin.math.abs(this.seedArray!![index].toInt())).toUInt()
        }
        this.ResetCreator()
    }

    @ExperimentalUnsignedTypes
    private fun ResetCreator() {
        this.mt[0] = this.seed.and(0xffffffffU)
        this.mti = 1U
        while (this.mti < this.N.toUInt()) {
            this.mt[this.mti.toInt()] = (1812433253U * (this.mt[(this.mti - 1U).toInt()].xor( (this.mt[(this.mti - 1U).toInt()].shr(30))) + this.mti))
            this.mti++
        }

        if (this.seedArray != null) {
            this.ResetSpecial()
        }

        this.bitBuffer = 0U;
        this.bitCount = 32;
    }

    @ExperimentalUnsignedTypes
    private fun ResetSpecial() {
        var i: UInt = 1U
        var j: UInt = 0U

        var k: Int = if (this.N > this.seedArray!!.size) this.N else this.seedArray!!.size
        while (k > 0) {
            this.mt[i.toInt()] = this.mt[i.toInt()].xor((this.mt[(i - 1U).toInt()].xor(this.mt[(i - 1U).toInt()].shr(30)) * 1664525U)) + this.seedArray!![j.toInt()] + j
            i++
            j++
            if (i >= this.N.toUInt()) {
                this.mt[0] = this.mt[this.N - 1]
                i = 1U
            }
            if (j >= this.seedArray!!.size.toUInt()) {
                j = 0U
            }
            k--
        }

        k = this.N - 1
        while (k > 0) {
            this.mt[i.toInt()] = this.mt[i.toInt()].xor((this.mt[(i - 1U).toInt()].xor(this.mt[(i - 1U).toInt()].shr(30)) * 1566083941U)) - i
            i++
            if(i >= this.N.toUInt()) {
                this.mt[0] = mt[this.N - 1]
                i = 1U
            }
            k--
        }
    }

    @ExperimentalUnsignedTypes
    private fun CreateNUInts() {
        var kk: Int = 0
        var y: UInt

        var mag01 = uintArrayOf(0x0U, this.VectorA)
        while (kk < this.N - this.M) {
            y = (this.mt[kk].and(this.UpperMask)).or(this.mt[kk + 1].and(this.LowerMask))
            this.mt[kk] = this.mt[kk + this.M].xor(y.shr(1)).xor(mag01[y.and(0x1U).toInt()])
            kk++
        }

        while(kk < this.N - 1) {
            y = (this.mt[kk].and(this.UpperMask)).or(this.mt[kk + 1].and(this.LowerMask))
            this.mt[kk] = this.mt[kk + (this.M - this.N)].xor(y.shr( 1)).xor(mag01[y.and(0x1U).toInt()])
            kk++
        }

        y = (this.mt[this.N - 1].and(this.UpperMask)).or(this.mt[0].and(this.LowerMask))
        this.mt[this.N - 1] = this.mt[this.M - 1].xor(y.shr(1)).xor(mag01[y.and(0x1U).toInt()])

        this.mti = 0U
    }

    @ExperimentalUnsignedTypes
    fun ExtractUint(): UInt {
        if (this.mti >= this.N.toUInt()) {
            this.CreateNUInts()
        }
        var _y = this.mt[this.mti.toInt()]
        this.mti++
        _y = _y.xor(_y.shr(11))
        _y = _y.xor(_y.shl(7).and(0x9d2c5680U))
        _y = _y.xor(_y.shl(15).and(0xefc60000U))
        return _y.xor(_y.shr(18))
    }

    //public override bool CanReset
    override val CanReset: Boolean
        get() {
            return true
        }

    //public override bool Reset()
    @ExperimentalUnsignedTypes
    override fun Reset(): Boolean {
        this.ResetCreator()
        return true
    }

    //public override int Next(int minValue, int maxValue)
    @ExperimentalUnsignedTypes
    override fun Extract(minValue: Int, maxValue: Int): Int {
        if (minValue > maxValue) {
        //    Log.i("RNG", "MinValue > MaxValue")
            return Int.MAX_VALUE
        }
        if (this.mti >= this.N.toUInt()) {
            this.CreateNUInts()
        }
        var _y = this.mt[this.mti.toInt()]
        this.mti++
        _y = _y.xor(_y.shr(11))
        _y = _y.xor(_y.shl(7).and(0x9d2c5680U))
        _y = _y.xor(_y.shl(15).and(0xefc60000U))
        _y = _y.xor(_y.shr(18))

        val range: Int = maxValue - minValue
        if (range < 0) {
            return minValue + (_y.toDouble() * this.UIntToDoubleMultiplier * (maxValue.toDouble() - minValue.toDouble())).toInt()
        }
        else {
            return minValue + ((_y.shr(1)).toInt().toDouble() * this.IntToDoubleMultiplier * range.toDouble()).toInt()
        }
    }
}