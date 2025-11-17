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
import tcs.app.dev.R.string.*

@Composable
fun CartItemRow(
    item: Item,
    cart: Cart,
    modifier: Modifier = Modifier,
    onCart: (Cart) -> Unit = {}
) {

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
            modifier = modifier
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
                    when (cart.items[item]) {
                        null -> cart.let(onCart)
                        1u -> (cart - item).let(onCart)
                        else -> (cart.copy(items = cart.items.filter { it.key != item } + (item to cart.items[item]!! - 1u))).let(
                            onCart
                        )
                    }
                }
            ) {
                Icon(
                    Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Text(cart.items[item].toString(), modifier = modifier)
            Surface(
                modifier = modifier,
                onClick = { (cart + item).let(onCart) }
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = null,
                    modifier = modifier
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
                onClick = { (cart - item).let(onCart) },
                color = MaterialTheme.colorScheme.error.copy(0.85f),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = modifier.size(26.dp),
            )
            {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = stringResource(description_close),
                    modifier = modifier
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
            cart = Cart(ExampleShop, items = mapOf(apple to 3u))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartRowBananaPreview() {
    val banana = Item("Banana")
    AppTheme {
        CartItemRow(
            item = banana,
            cart = Cart(ExampleShop, items = mapOf(banana to 5u))
        )
    }
}
