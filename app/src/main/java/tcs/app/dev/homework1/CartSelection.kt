package tcs.app.dev.homework1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.*
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.homework1.data.Discount.*

@Composable
fun CartSelection(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    cart: Cart,
    onCart: (Cart) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(cart.items.keys.toList()) { item ->
            CartItemRow(
                item = item,
                cart = cart,
                onCart = onCart,
                modifier = modifier
            )
        }
        items(1) { _ ->
            HorizontalDivider(
                modifier = modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.secondary,
                thickness = 1.dp
            )
        }
        items(cart.discounts) { discount ->
            CartDiscountRow(
                discount = discount,
                modifier = modifier,
                onCart = onCart,
                cart = cart
            )
        }
    }
}


@Composable
@Preview
fun CartSelectionPreview() {
    AppTheme {
        CartSelection(
            cart = Cart(
                shop = ExampleShop,
                items = mapOf(Item("Apple") to 4u, Item("Banana") to 8u),
                allDiscounts = listOf(
                    Fixed(50u.cents),
                    Bundle(amountItemsGet = 3u, amountItemsPay = 2u, item = Item("Apple"))
                )
            )
        )
    }
}
