package tcs.app.dev.homework.data

data class Euro(val cent: UInt) {
    override fun toString(): String {
        return "%.2f €".format(cent.toDouble() / 100)
    }
}

operator fun Euro.plus(other: Euro): Euro {
    return Euro(this.cent + other.cent)
}

operator fun Euro.minus(other: Euro): Euro {
    if (this.cent < other.cent) {
        return Euro(0u)
    }
    return Euro(this.cent - other.cent)
}
operator fun Euro.minus(other: UInt): Euro {
    if (this.cent < other) {
        return Euro(0u)
    }
    return Euro(this.cent - other)
}

operator fun Euro.times(other: Euro): Euro {
    return Euro(this.cent * other.cent)
}
operator fun UInt.times(other: Euro): Euro {
    return Euro(this * other.cent)
}
operator fun Euro.times(other: UInt): Euro {
    return Euro(this.cent * other)
}
operator fun Euro.div(other: UInt): Euro {
    return Euro(this.cent / other)
}
infix operator fun Euro.compareTo(other: Euro): Int {
    return (other.cent - this.cent).toInt()
}
val UInt.cents: Euro
    get() = Euro(this)

val UInt.euro: Euro
    get() = Euro(this * 100u)
