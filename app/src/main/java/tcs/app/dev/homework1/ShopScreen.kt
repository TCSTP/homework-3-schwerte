package tcs.app.dev.homework1

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Discount
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.R.string.description_go_to_discount
import tcs.app.dev.R.string.description_go_to_shop
import tcs.app.dev.R.string.name_shop
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.Screen
import tcs.app.dev.homework1.data.Shop
import tcs.app.dev.homework1.data.Screen.*
import tcs.app.dev.R.string.*

/**
 * # Homework 3 — Shop App
 *
 * Build a small shopping UI with ComposeUI using the **example data** from the
 * `tcs.app.dev.homework.data` package (items, prices, discounts, and ui resources).
 * The goal is to implement three tabs: **Shop**, **Discounts**, and **Cart**.
 *
 * ## Entry point
 *
 * The composable function [ShopScreen] is your entry point that holds the UI state
 * (selected tab and the current `Cart`).
 *
 * ## Data
 *
 * - Use the provided **example data** and data types from the `data` package:
 *   - `Shop`, `Item`, `Discount`, `Cart`, and `Euro`.
 *   - There are useful resources in `res/drawable` and `res/values/strings.xml`.
 *     You can add additional ones.
 *     Do **not** hard-code strings in the UI!
 *
 * ## Requirements
 *
 * 1) **Shop item tab**
 *    - Show all items offered by the shop, each row displaying:
 *      - item image + name,
 *      - item price,
 *      - an *Add to cart* button.
 *    - Tapping *Add to cart* increases the count of that item in the cart by 1.
 *
 * 2) **Discount tab**
 *    - Show all available discounts with:
 *      - an icon + text describing the discount,
 *      - an *Add to cart* button.
 *    - **Constraint:** each discount can be added **at most once**.
 *      Disable the button (or ignore clicks) for discounts already in the cart.
 *
 * 3) **Cart tab**
 *    - Only show the **Cart** tab contents if the cart is **not empty**. Within the cart:
 *      - List each cart item with:
 *        - image + name,
 *        - per-row total (`price * amount`),
 *        - an amount selector to **increase/decrease** the quantity (min 0, sensible max like 99).
 *      - Show all selected discounts with a way to **remove** them from the cart.
 *      - At the bottom, show:
 *        - the **total price** of the cart (items minus discounts),
 *        - a **Pay** button that is enabled only when there is at least one item in the cart.
 *      - When **Pay** is pressed, **simulate payment** by clearing the cart and returning to the
 *        **Shop** tab.
 *
 * ## Navigation
 * - **Top bar**:
 *      - Title shows either the shop name or "Cart".
 *      - When not in Cart, show a cart icon.
 *        If you feel fancy you can add a badge to the icon showing the total count (capped e.g. at "99+").
 *      - The cart button is enabled only if the cart contains items. In the Cart screen, show a back
 *        button to return to the shop.
 *
 * - **Bottom bar**:
 *       - In Shop/Discounts, show a 2-tab bottom bar to switch between **Shop** and **Discounts**.
 *       - In Cart, hide the tab bar and instead show the cart bottom bar with the total and **Pay**
 *         action as described above.
 *
 * ## Hints
 * - Keep your cart as a single source of truth and derive counts/price from it.
 *   Rendering each list can be done with a `LazyColumn` and stable keys (`item.id`, discount identity).
 * - Provide small reusable row components for items, cart rows, and discount rows.
 *   This keeps the screen implementation compact.
 *
 * ## Bonus (optional)
 * Make the app feel polished with simple animations, such as:
 * - `AnimatedVisibility` for showing/hiding the cart,
 * - `animateContentSize()` on rows when amounts change,
 * - transitions when switching tabs or updating the cart badge.
 *
 * These can help if want you make the app feel polished:
 * - [NavigationBar](https://developer.android.com/develop/ui/compose/components/navigation-bar)
 * - [Card](https://developer.android.com/develop/ui/compose/components/card)
 * - [Swipe to dismiss](https://developer.android.com/develop/ui/compose/touch-input/user-interactions/swipe-to-dismiss)
 * - [App bars](https://developer.android.com/develop/ui/compose/components/app-bars#top-bar)
 * - [Pager](https://developer.android.com/develop/ui/compose/layouts/pager)
 *
 */

@Preview
@Composable
fun ShopScreen(
    shop: Shop = ExampleShop,
    availableDiscounts: List<Discount> = listOf(),
    modifier: Modifier = Modifier
) {
    var cart: Cart by rememberSaveable { mutableStateOf(Cart(shop = shop)) }
    var screen: Screen by rememberSaveable { mutableStateOf(SHOP) }

    val buttonColors = ButtonColors(
        containerColor = MaterialTheme.colorScheme.onSecondary,
        contentColor = MaterialTheme.colorScheme.secondary,
        disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
        disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .animateContentSize(), topBar = {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            Button( // Discounts
                onClick = { DISCOUNT.let { screen = it } },
                colors = buttonColors
            ) {
                AnimatedContent(targetState = screen) { targetState ->
                    when (targetState) {
                        DISCOUNT ->
                            Text(
                                text = stringResource(title_discounts),
                                modifier = Modifier.padding(
                                    horizontal = 4.dp,
                                    vertical = 10.dp
                                ),
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )

                        else ->
                            Icon(
                                Icons.Outlined.Discount,
                                contentDescription = stringResource(description_go_to_discount),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(32.dp)
                            )
                    }
                }
            }

            Button( // Shop
                onClick = { SHOP.let { screen = it } },
                colors = buttonColors
            ) {
                AnimatedContent(targetState = screen) { targetState ->
                    when (targetState) {
                        SHOP ->
                            Text(
                                text = stringResource(name_shop),
                                modifier = Modifier.padding(
                                    horizontal = 4.dp,
                                    vertical = 10.dp
                                ),
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )

                        else ->
                            Icon(
                                Icons.Outlined.ShoppingBag,
                                contentDescription = stringResource(description_go_to_shop),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(32.dp)
                            )
                    }
                }
            }
            Button( // Cart
                onClick = { CART.let { screen = it } },
                colors = buttonColors,
                enabled = cart.items.isNotEmpty()
            ) {
                AnimatedContent(targetState = screen) { targetState ->
                    when (targetState) {
                        CART ->
                            Text(
                                text = stringResource(title_cart),
                                modifier = Modifier.padding(
                                    horizontal = 4.dp,
                                    vertical = 10.dp
                                ),
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )

                        else ->
                            Icon(
                                Icons.Outlined.ShoppingCart,
                                contentDescription = stringResource(description_go_to_shop),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(32.dp)
                            )
                    }
                }
            }
        }
    }, bottomBar = {
        AnimatedVisibility(
            visible = screen == CART,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(total_price).format(cart.price),
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Button(
                    onClick = {
                        (Cart(cart.shop)).let { cart = it }
                        SHOP.let { screen = it }
                    },
                    colors = buttonColors,
                    enabled = cart.items.isNotEmpty()
                ) {
                    Text(
                        text = stringResource(label_pay),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
    ) { paddingValues ->
        when (screen) {
            SHOP -> ItemSelection(
                cart = cart,
                paddingValues = paddingValues,
                modifier = modifier
            ) { currentCart -> cart = currentCart }

            DISCOUNT -> {
                BackHandler { screen = SHOP }
                DiscountSelection(
                    cart = cart,
                    discounts = availableDiscounts,
                    paddingValues = paddingValues,
                    modifier = modifier
                ) { currentCart -> cart = currentCart }
            }

            CART -> {
                BackHandler { screen = SHOP }
                CartSelection(
                    cart = cart,
                    paddingValues = paddingValues,
                    modifier = modifier
                ) { currentCart -> cart = currentCart }
            }
        }
    }
}
