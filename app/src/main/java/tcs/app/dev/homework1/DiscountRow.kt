package tcs.app.dev.homework1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.Euro
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.RemoveShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.*
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.homework1.data.MockData.ExampleDiscounts
import tcs.app.dev.R.string.*
import tcs.app.dev.homework1.data.Discount.*
import tcs.app.dev.homework1.data.MockData.getName

@Composable
fun DiscountRow(
    discount: Discount,
    cart: Cart,
    modifier: Modifier,
    onCart: (Cart) -> Unit
) {
    var inCart: Boolean by rememberSaveable { mutableStateOf(cart.discounts.contains(discount)) }

    val border = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline
    )

    val color = MaterialTheme.colorScheme.surface
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        border = border,
        color = color,
    ) {
        val icons = when (discount) {
            is Fixed -> (Icons.Outlined.Euro to "Euro")
            is Percentage -> (Icons.Outlined.Percent to "Percent")
            is Bundle -> (Icons.Outlined.Backpack to "Bundle")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icons.first,
                contentDescription = icons.second,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(32.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            val text = when (discount) {
                is Fixed -> stringResource(amount_off).format(discount.amount)
                is Percentage -> stringResource(percentage_off).format(discount.value)
                is Bundle -> stringResource(pay_n_items_and_get).format(
                    discount.amountItemsPay,
                    stringResource(getName(discount.item)),
                    discount.amountItemsGet
                )
            }
            Text(text, modifier = modifier)
            Button(
                onClick = {
                    (if (inCart) {
                        cart - discount
                    } else {
                        cart + discount
                    }).let(onCart)
                    inCart = !inCart
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Icon(
                    imageVector = if (inCart) Icons.Outlined.RemoveShoppingCart else Icons.Outlined.AddShoppingCart,
                    contentDescription = "Close",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(32.dp),
                    tint = if (inCart) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscountRowAmountPreview() {
    AppTheme {
        DiscountRow(
            modifier = Modifier,
            discount = ExampleDiscounts[0],
            cart = Cart(ExampleShop)
        ) { {} }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscountRowPercentPreview() {
    AppTheme {
        DiscountRow(
            modifier = Modifier,
            discount = ExampleDiscounts[1],
            cart = Cart(ExampleShop)
        ) { {} }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscountRowBundleInCartPreview() {
    AppTheme {
        val discount = ExampleDiscounts[2]
        DiscountRow(
            modifier = Modifier,
            discount = discount,
            cart = Cart(ExampleShop, allDiscounts = listOf(discount))
        ) { {} }
    }
}
