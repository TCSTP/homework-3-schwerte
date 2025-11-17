package tcs.app.dev.homework

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework.data.*
import tcs.app.dev.homework.data.MockData.ExampleShop
import tcs.app.dev.homework.data.MockData.getName
import tcs.app.dev.homework.data.MockData.getImage
import tcs.app.dev.R.string.*

@Composable
fun ItemRow(image: Painter, title: String, price: String, modifier: Modifier) {

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(painter = image, contentDescription = null, modifier = modifier.size(40.dp))
            Text(title, modifier = modifier)
            Text(price, modifier = modifier)
            Button(
                onClick = { }, content =
                    {
                        Icon(
                            Icons.Outlined.AddShoppingCart,
                            contentDescription = stringResource(description_add_to_cart),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(32.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowApplePreview() {
    val apple = Item("Apple")
    val image = getImage(apple)
    val title = getName(apple)
    val price = ExampleShop.items[apple]
    AppTheme {
        ItemRow(
            image = painterResource(image),
            title = stringResource(title),
            price = price.toString(),
            modifier = Modifier
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ItemRowBananaPreview() {
    val banana = Item("Banana")
    val image = getImage(banana)
    val title = getName(banana)
    val price = ExampleShop.items[banana]
    AppTheme {
        ItemRow(
            image = painterResource(image),
            title = stringResource(title),
            price = price.toString(),
            modifier = Modifier
        )
    }
}
