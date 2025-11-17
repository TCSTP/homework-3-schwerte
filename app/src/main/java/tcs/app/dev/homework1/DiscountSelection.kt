package tcs.app.dev.homework1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.*
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.homework1.data.MockData.ExampleDiscounts

@Composable
fun DiscountSelection(
    modifier: Modifier = Modifier,
    cart: Cart,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    discounts: List<Discount> = ExampleDiscounts,
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
        items(discounts) { discount ->
            DiscountRow(
                discount = discount,
                cart = cart,
                modifier = modifier,
                onCart = onCart
            )
        }
    }
}

@Composable
@Preview
fun DiscountSelectionPreview() {
    AppTheme {
        DiscountSelection(
            cart = Cart(ExampleShop, allDiscounts = listOf(ExampleDiscounts[2]))
        )
    }
}
