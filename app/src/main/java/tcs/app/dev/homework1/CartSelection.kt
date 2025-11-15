package tcs.app.dev.homework1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Discount
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.*
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.R.string.*
import tcs.app.dev.homework1.data.Screen.*
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.homework1.data.MockData.getName
import tcs.app.dev.homework1.data.MockData.getImage
import tcs.app.dev.homework1.data.Discount.*

@Composable
fun CartSelection(
    modifier: Modifier = Modifier,
    onDiscount: (Screen) -> Unit = {},
    onShop: (Screen) -> Unit = {},
    cart: Cart
) {
    var screen: Screen? by rememberSaveable { mutableStateOf(SHOP) }
    val shop = cart.shop
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            Button(
                onClick = { screen = DISCOUNT; screen?.let(onDiscount) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    Icons.Outlined.Discount,
                    contentDescription = stringResource(label_discounts),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(32.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
            Button(
                onClick = { screen = SHOP; screen?.let(onShop) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
                enabled = cart.items.isNotEmpty()
            ) {
                Icon(
                    Icons.Outlined.ShoppingBag,
                    contentDescription = stringResource(description_go_to_shop),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(32.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = modifier
                    .size(180.dp, 64.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surfaceBright)
            ) {
                Text(
                    text = stringResource(title_cart),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }, bottomBar = {
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
                onClick = { screen = SHOP; screen?.let(onShop) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
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
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(cart.items.entries.toList()) { item ->
                CartItemRow(
                    image = painterResource(getImage(item.key)),
                    title = stringResource(getName(item.key)),
                    price = shop.prices[item.key]?:Euro(0u),
                    amount = item.value,
                    modifier = Modifier
                )
            }
            items(1) { line ->
                HorizontalDivider(modifier = modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.secondary, thickness = 1.dp)}
            items(cart.discounts) { discount ->
                CartDiscountRow(
                    title = stringResource(when (discount) {
                        is Fixed -> amount_off
                        is Percentage -> percentage_off
                        is Bundle -> pay_n_items_and_get
                    }),
                    discount = discount,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
@Preview
fun CartSelectionPreview() {
    AppTheme {
        CartSelection(
            cart = Cart(shop = ExampleShop, items = mapOf(Item("Apple") to 4u, Item("Banana") to 8u), allDiscounts = listOf(Fixed(50u.cents),
                Bundle(amountItemsGet = 3u, amountItemsPay = 2u, item = Item("Apple"))))
        )
    }
}
