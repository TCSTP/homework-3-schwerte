package tcs.app.dev.homework

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework.data.*
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.R
import tcs.app.dev.homework.data.Screen.*
import tcs.app.dev.homework.data.MockData.ExampleShop
import tcs.app.dev.homework.data.MockData.getName
import tcs.app.dev.homework.data.MockData.getImage

@Composable
fun ItemSelection(
    title: String,
    modifier: Modifier = Modifier,
    onDiscount: (Screen) -> Unit = {},
    onCart: (Screen) -> Unit = {},
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
                    contentDescription = "Discount",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(32.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
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
                    contentDescription = "Cart",
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
            items(shop.items.toList()) { item ->
                ItemRow(
                    image = painterResource(getImage(item.first)),
                    title = stringResource(getName(item.first)),
                    price = item.second.toString(),
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
            title = stringResource(R.string.label_shop),
            cart = Cart(shop = ExampleShop, currentCart = mapOf(), discount = listOf())
        )
    }
}
