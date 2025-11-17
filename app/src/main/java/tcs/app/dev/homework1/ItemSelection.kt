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
import tcs.app.dev.homework1.data.*
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.R.string.*
import tcs.app.dev.homework1.data.Screen.*
import tcs.app.dev.homework1.data.MockData.ExampleShop

@Composable
fun ItemSelection(
    cart: Cart,
    modifier: Modifier = Modifier,
    onScreen: (Screen) -> Unit,
    onCart: (Cart) -> Unit
) {

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
                onClick = { DISCOUNT.let(onScreen) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    Icons.Outlined.Discount,
                    contentDescription = stringResource(description_go_to_discount),
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
                    .size(196.dp, 64.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surfaceBright)
            ) {
                Text(
                    text = stringResource(name_shop),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { CART.let(onScreen) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
                enabled = cart.items.isNotEmpty()
            ) {
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = stringResource(description_go_to_shop),
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
            items(cart.shop.items.toList()) { item ->
                ItemRow(
                    item = item,
                    cart = cart,
                    price = cart.shop.prices[item]?:Euro(0u),
                    onCart = onCart,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
@Preview
fun ItemSelectionPreview() {
    AppTheme {
        ItemSelection(
            cart = Cart(shop = ExampleShop, items = mapOf(Item("Banana") to 4u)),
            onCart = {}, onScreen = {}
        )
    }
}
