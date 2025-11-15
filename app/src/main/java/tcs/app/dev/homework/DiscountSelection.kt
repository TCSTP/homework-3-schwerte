package tcs.app.dev.homework

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
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework.data.*
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.R.string.*
import tcs.app.dev.homework.data.Screen.*
import tcs.app.dev.homework.data.MockData.ExampleShop
import tcs.app.dev.homework.data.MockData.ExampleDiscounts

@Composable
fun DiscountSelection(
    modifier: Modifier = Modifier,
    onShop: (Screen) -> Unit = {},
    onCart: (Screen) -> Unit = {},
    cart: Cart,
    discounts: List<Discount>
) {
    var screen: Screen? by rememberSaveable { mutableStateOf(DISCOUNT) }
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
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = modifier
                    .size(180.dp, 64.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surfaceBright)
            ) {
                Text(
                    text = stringResource(title_discounts),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { screen = SHOP; screen?.let(onShop) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
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
            Button(
                onClick = { screen = CART; screen?.let(onCart) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
                enabled = cart.currentCart.isNotEmpty()
            ) {
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = stringResource(description_go_to_cart),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(32.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
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
            items(discounts) { discount ->
                val title = stringResource(
                    when (discount) {
                        is Fixed -> amount_off
                        is Percentage -> percentage_off
                        is Bundle -> pay_n_items_and_get
                    }
                )
                DiscountRow(discount, cart, modifier)
            }
        }
    }
}

@Composable
@Preview
fun DiscountSelectionPreview() {
    AppTheme {
        DiscountSelection(
            cart = Cart(ExampleShop, discount = listOf(ExampleDiscounts[2])),
            discounts = ExampleDiscounts
        )
    }
}
