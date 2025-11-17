package tcs.app.dev.homework1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.*
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.homework1.data.MockData.getName
import tcs.app.dev.homework1.data.MockData.getImage
import tcs.app.dev.R.string.*

@Composable
fun ItemRow(
    item: Item,
    cart: Cart,
    price: Euro,
    modifier: Modifier,
    onCart: (Cart) -> Unit
) {

    var amount by remember { mutableStateOf(cart.items[item]) }

    val border = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline
    )
    val purple = MaterialTheme.colorScheme.onPrimaryContainer

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
            Box(modifier, contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(getImage(item)),
                    contentDescription = stringResource(getName(item)),
                    modifier = modifier.size(40.dp)
                )
                if (amount != null) {
                    Text(
                        cart.items[item].toString(),
                        modifier
                            .padding(2.dp)
                            .align(Alignment.BottomEnd)
                            .drawBehind {
                                drawCircle(
                                    radius = this.size.maxDimension / 1.8f,
                                    brush = SolidColor(purple),
                                )
                                drawCircle(
                                    radius = this.size.maxDimension / 2,
                                    brush = SolidColor(Color.White),
                                )
                            },
                        style = MaterialTheme.typography.labelSmall,
                        color = purple
                    )
                }
            }
            Text(stringResource(getName(item)), modifier = modifier)
            Text(price.toString(), modifier = modifier)
            Button(
                onClick = {
                    (cart + item).let(onCart)
                    amount = amount?.plus(1u) ?: 1u
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = purple,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
            {
                Icon(
                    Icons.Outlined.AddShoppingCart,
                    contentDescription = stringResource(description_add_to_cart),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(32.dp),
                    tint = purple,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowApplePreview() {
    val apple = Item("Apple")
    val price = ExampleShop.prices[apple]
    AppTheme {
        ItemRow(
            item = apple,
            cart = Cart(ExampleShop),
            price = price ?: 0u.cents,
            modifier = Modifier
        ) { {} }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowBananaPreview() {
    val banana = Item("Banana")
    val price = ExampleShop.prices[banana]
    AppTheme {
        ItemRow(
            item = banana,
            cart = Cart(ExampleShop, mapOf(banana to 4u)),
            price = price ?: 0u.cents,
            modifier = Modifier
        ) { {} }
    }
}
