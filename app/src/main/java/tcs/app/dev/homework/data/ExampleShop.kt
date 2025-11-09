package tcs.app.dev.homework.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import tcs.app.dev.R

private data class UIItem(
    val item: Item,
    val price: Euro,
    @param:DrawableRes val image: Int,
    @param:StringRes val name: Int
)

/**
 * Mock items and associated resources.
 * In the next lecture we'll learn, how it works if we get this data dynamically.
 **/
object MockData {
    val Apple = Item("Apple")
    val applePrice = Euro(60u)
    val Banana = Item("Banana")
    val bananaPrice = Euro(90u)

    private val ExampleItems = listOf(
        UIItem(Apple, applePrice, R.drawable.apple, R.string.name_apple),
        UIItem(Banana, bananaPrice, R.drawable.banana, R.string.name_banana),
        UIItem(Item("GoldenFig"), 16u.euro + 80u.cents, R.drawable.golden_fig, R.string.name_golden_fig),
        UIItem(Item("Bread"), 1u.euro + 90u.cents, R.drawable.bread, R.string.name_bread),
        UIItem(Item("Chair"), 49u.euro + 99u.cents, R.drawable.chair, R.string.name_chair),
        UIItem(Item("Computer"), 1499u.euro + 99u.cents, R.drawable.computer, R.string.name_computer),
        UIItem(Item("Knives"), 40u.euro, R.drawable.knives, R.string.name_knives),
        UIItem(Item("Onion"), 40u.cents, R.drawable.onion, R.string.name_onion),
        UIItem(Item("Scissor"), 3u.euro + 50u.cents, R.drawable.scissor, R.string.name_scissor),
        UIItem(Item("Spaghetti"), 1u.euro, R.drawable.spaghetti, R.string.name_spaghetti),
        UIItem(Item("Table"), 400u.euro, R.drawable.table, R.string.name_table),
        UIItem(Item("Tomato"), 50u.cents, R.drawable.tomato, R.string.name_tomato)
    )

    val ExampleShop = Shop(
        items = ExampleItems.associate { item -> (item.item to item.price )}
    )

    val ExampleDiscounts = listOf(
        Fixed(3u.euro),
        Percentage(10u),
        Bundle(5u, 3u, Apple),
        Bundle(3u, 2u, Banana)
    )

    @StringRes
    fun getName(item: Item): Int = ExampleItems.first { uiItem -> uiItem.item == item }.name

    @DrawableRes
    fun getImage(item: Item): Int = ExampleItems.first { uiItem -> uiItem.item == item }.image
}
