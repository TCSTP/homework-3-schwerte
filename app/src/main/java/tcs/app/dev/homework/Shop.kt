package tcs.app.dev.homework

import kotlin.collections.iterator

data class Shop(val items: Map<Item, Euro>) {
    override fun toString(): String {
        var str = ""
        for ((item, price) in items) {
            str += "$item: $price\n"
        }
        return str.trim()
    }
}