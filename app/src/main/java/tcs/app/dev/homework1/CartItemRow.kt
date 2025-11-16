package tcs.app.dev.homework1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.*
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.homework1.data.MockData.getName
import tcs.app.dev.homework1.data.MockData.getImage

@Composable
fun CartItemRow(
    item: Item,
    cart: Cart,
    modifier: Modifier,
    onCart: (Cart) -> Unit
) {
    var amount by remember { mutableStateOf(cart.items[item]) }
    var cart by remember { mutableStateOf(cart) }

    val border = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline
    )
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        border = border,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(getImage(item)),
                contentDescription = null,
                modifier = modifier.size(40.dp)
            )
            Surface(
                modifier = modifier,
                onClick = {
                    cart.items[item]?.let { i ->
                        if (i > 1u) {
                            val newItems = cart.items.filter { it.key != item } + (item to (i - 1u))
                            cart = cart.copy(items = newItems)
                        } else {
                            cart -= item
                            //cart = cart.copy(items = cart.items.filter { it.key != item })
                        }
                    }
                    cart.let(onCart)
                    when (amount) {
                        null -> {}
                        1u -> amount = null
                        else -> amount = amount!! - 1u
                    }
                }
            ) {
                Icon(
                    Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Text((cart.items[item]?:0u).toString(), modifier = modifier)
            Surface(
                modifier = modifier,
                onClick = {
                    cart += item
                    cart.let(onCart)
                    amount = when (amount) {
                        null -> 1u
                        else -> amount!! + 1u
                    }
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Text(stringResource(getName(item)), modifier = modifier)
            Text(
                ((cart.shop.prices[item] ?: 0u.cents) * (cart.items[item] ?: 0u)).toString(),
                modifier = modifier
            )
            Surface(
                onClick = {
                    cart = cart.copy(items = cart.items.filter { it.key != item })
                    cart.let(onCart)
                },
                color = MaterialTheme.colorScheme.error.copy(0.85f),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = modifier.size(26.dp),
            )
            {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(32.dp),
                    tint = MaterialTheme.colorScheme.errorContainer,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartRowApplePreview() {
    val apple = Item("Apple")
    AppTheme {
        CartItemRow(
            item = apple,
            cart = Cart(ExampleShop, items = mapOf(apple to 3u)),
            modifier = Modifier
        ) { {} }
    }
}

@Preview(showBackground = true)
@Composable
fun CartRowBananaPreview() {
    val banana = Item("Banana")
    AppTheme {
        CartItemRow(
            item = banana,
            cart = Cart(ExampleShop, items = mapOf(banana to 5u)),
            modifier = Modifier
        ) { {} }
    }
}
