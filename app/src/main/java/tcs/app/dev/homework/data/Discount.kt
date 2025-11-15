package tcs.app.dev.homework.data

import kotlin.math.max

sealed interface Discount {
    fun calc(total: Euro, cart: Cart) : Euro
}

// e.g., 20% off the current total
data class Percentage(val value: UInt): Discount {
    override fun toString(): String {
        return "$value% off"
    }
    override fun calc(total: Euro, cart: Cart): Euro {
        return (total * (100u-value)) / 100u
    }
}

// e.g., €5.00 off
data class Fixed(val value: UInt): Discount {
    override fun toString(): String {
        return "%.2f € off".format(value.toDouble() / 100)
    }
    override fun calc(total: Euro, cart: Cart): Euro {
        return total - value
    }
    constructor(value: Euro) : this(value.cent) { }
}

// e.g. “Buy 3, pay 2” for a specific item
/**
 * @param get how many items you get from the bundle
 * @param pay how many items you have to pay for
 * @param item what item the bundle targets
 */
data class Bundle(val get: UInt, val pay: UInt, val item: Item): Discount {
    override fun toString(): String {
        return "$get $item${if (get>1u) "s" else ""} for the price of $pay"
    }
    override fun calc(total: Euro, cart: Cart): Euro {
        val itemCost: Euro = cart.shop.items[item]?: Euro(0u)
        val itemAmount: UInt =  (cart.currentCart[item])?: 0u
        val bundleTimes: UInt = itemAmount/get // how often does the bundle apply
        val priceTimes: UInt = bundleTimes * (this.get - this.pay) // how often is the price subtracted from the total
        return total - (itemCost * priceTimes)
    }
}

//infix operator fun Bundle.compareTo(other: Bundle): Int {
//    if (this.item != other.item) return -1
//    if (this.get == other.get) return (this.pay - other.pay).toInt()
//    val amount = max(this.get, other.get)
//    val thisTimes = other.get/this.get
//    val otherTimes = this.get/other.get
//    return (otherTimes * other.pay + (amount % otherTimes) - thisTimes * this.pay + (amount % thisTimes)).toInt()
//}
