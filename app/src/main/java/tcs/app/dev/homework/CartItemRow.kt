package tcs.app.dev.homework

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Close
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

@Composable
fun CartItemRow(image: Painter, amount: UInt, title: String, price: Euro, modifier: Modifier) {

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
            Surface(modifier = modifier) {
                Icon(
                    Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Text(amount.toString(), modifier = modifier)
            Surface(modifier = modifier) {
                Icon(
                    Icons.Rounded.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Text(title, modifier = modifier)
            Text((price * amount).toString(), modifier = modifier)
            Surface(
                onClick = { }, color = MaterialTheme.colorScheme.error.copy(0.85f),
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
    val image = getImage(apple)
    val title = getName(apple)
    val price = ExampleShop.items[apple]
    AppTheme {
        CartItemRow(
            image = painterResource(image),
            amount = 5u,
            title = stringResource(title),
            price = price ?: Euro(0u),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartRowBananaPreview() {
    val banana = Item("Banana")
    val image = getImage(banana)
    val title = getName(banana)
    val price = ExampleShop.items[banana]
    AppTheme {
        CartItemRow(
            image = painterResource(image),
            amount = 2u,
            title = stringResource(title),
            price = price ?: Euro(0u),
            modifier = Modifier
        )
    }
}
