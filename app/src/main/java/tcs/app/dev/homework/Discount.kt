package tcs.app.dev.homework

sealed interface Discount {
    fun calc(total: Euro) : Euro
}

// e.g., 20% off the current total
data class Percentage(val value: UInt): Discount {
    override fun toString(): String {
        return "$value% off"
    }
    override fun calc(total: Euro): Euro {
        return (total * (100u-value)) / 100u
    }
}

// e.g., €5.00 off
data class Fixed(val value: UInt): Discount {
    override fun toString(): String {
        return "%.2f € off".format(value.toDouble() / 100)
    }
    override fun calc(total: Euro): Euro {
        return total - value
    }
}

// e.g. “Buy 3, pay 2” for a specific item
/**
 * @param pair (a, b): a for the price of b
 * @param price the price of the given item
 * @param amount how many times the item appears in the cart
 * @param item what item the bundle targets
 */
data class Bundle(val pair: Pair<UInt, UInt>, val price: Euro?, val amount: UInt, val item: Item): Discount {
    override fun toString(): String {
        return "${pair.first} $item${if (pair.first>1u) "s" else ""} for the price of ${pair.second}"
    }
    override fun calc(total: Euro): Euro {
        if (price == null) return total
        val bundleTimes: UInt = amount/pair.first // how often does the bundle apply
        val priceTimes: UInt = bundleTimes * (this.pair.first - this.pair.second) // how often is the price subtracted from the total
        return total - (price * priceTimes)
    }
}
infix operator fun Bundle.compareTo(other: Bundle): Int {
    if (this.item != other.item) return -1
    if (this.pair == other.pair) return 0
    val thisTimes = this.amount/this.pair.first
    val otherTimes = other.amount/other.pair.first
    return (otherTimes * other.pair.second + (other.amount % otherTimes) - thisTimes * this.pair.second + (this.amount % thisTimes)).toInt()
}
