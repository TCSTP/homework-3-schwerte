package tcs.app.dev.homework.data

import kotlin.collections.iterator

// note to self: in real apps, don't use var
data class Cart(
    val shop: Shop,
    val currentCart: Map<Item, UInt> = mapOf(),
    val discount: List<Discount> = listOf()
) {
    override fun toString(): String {
        var str = ""
        for ((item, amount) in currentCart) {
            str += "$amount x $item: ${shop.items[item]}\n"
        }
        str += "---------------------------\n"
        for (d in discount) {
            str += "$d\n"
        }
        str += "---------------------------\n"
        str += "total: $total"
        return str
    }

    // Bundle → Fixed → Percentage.
    val total: Euro by lazy {
        var currentTotal = Euro(0u)
        for ((item, amount) in currentCart) {
            val price = this.shop.items[item]
            if (price != null) {
                currentTotal += price * amount
            }
        }
        val bundles: MutableMap<Item, Bundle> = mutableMapOf()
        // for each bundle
        for (reduction in this.discount.filterIsInstance<Bundle>()) {
            var bundle = reduction
            val temp = bundles[bundle.item]
            // check if there already is a bundle targeting the same item
            if (temp != null) {
                // check which bundle gives a greater reduction
                if (bundle.calc(currentTotal, this) > temp.calc(currentTotal, this)) {
                    bundles += (bundle.item to bundle) // the new one gives more
                } else {
                    bundles += (bundle.item to temp)   // the old one gives more
                }
            } else {
                bundles += (bundle.item to reduction) // there wasn't another bundle
            }
        }
        for (bundle in bundles) {
            currentTotal = bundle.value.calc(currentTotal, this)
        }
        val discounts =
            this.discount.filterIsInstance<Fixed>() +
            this.discount.filterIsInstance<Percentage>()
        for (reduction in discounts) {
            currentTotal = reduction.calc(currentTotal, this)
        }
        currentTotal
    }
}

operator fun Cart.plus(pair: Pair<Item, UInt>): Cart {
    return copy(currentCart = currentCart + (pair.first to (currentCart[pair.first] ?: 0u) + pair.second))
}

operator fun Cart.plus(item: Item): Cart {
    return this + (item to 1u)
}

operator fun Cart.plus(discount: Discount): Cart {
    return this.copy(discount = this.discount + discount)
}

operator fun Cart.plus(discounts: List<Discount>): Cart {
    return this.copy(discount = this.discount + discounts)
}