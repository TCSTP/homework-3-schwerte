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
import tcs.app.dev.homework1.data.MockData.ExampleShop
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun ItemSelection(
    cart: Cart,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onCart: (Cart) -> Unit
) {

    LazyColumn(
        modifier = modifier
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
                onCart = onCart,
                modifier = modifier
            )
        }
    }
}

@Composable
@Preview
fun ItemSelectionPreview() {
    AppTheme {
        ItemSelection(
            cart = Cart(
                shop = ExampleShop,
                items = mapOf(Item("Banana") to 4u)
            )
        ) { }
    }
}
