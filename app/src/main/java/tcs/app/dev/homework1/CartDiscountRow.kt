package tcs.app.dev.homework1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.Euro
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.ui.theme.AppTheme
import tcs.app.dev.homework1.data.*
import tcs.app.dev.homework1.data.MockData.ExampleDiscounts
import tcs.app.dev.R.string.*
import tcs.app.dev.homework1.data.Discount.*
import tcs.app.dev.homework1.data.MockData.getName

@Composable
fun CartDiscountRow(discount: Discount, title: String, modifier: Modifier) {

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
            val icons = when (discount) {
                is Fixed -> (Icons.Outlined.Euro to stringResource(description_euro))
                is Percentage -> (Icons.Outlined.Percent to stringResource(description_percent))
                is Bundle -> (Icons.Outlined.Backpack to stringResource(description_bundle))
            }
            Icon(
                imageVector = icons.first,
                contentDescription = icons.second,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(32.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            val text = when (discount) {
                is Fixed -> title.format( discount.amount.cents)
                is Percentage -> title.format(discount.value)
                is Bundle -> title.format(discount.amountItemsPay, stringResource(getName(discount.item)), discount.amountItemsGet)
            }
            Text(text, modifier = modifier)
            Surface(
                onClick = { }, color = MaterialTheme.colorScheme.error.copy(0.85f),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = modifier.size(26.dp),
            )
            {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = stringResource(description_euro),
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
fun CartDiscountRowFixedPreview() {
    AppTheme {
        CartDiscountRow(
            discount = ExampleDiscounts[0],
            title = stringResource(amount_off),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartDiscountRowPercentagePreview() {
    AppTheme {
        CartDiscountRow(
            discount = ExampleDiscounts[1],
            title = stringResource(percentage_off),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartDiscountRowBundlePreview() {
    AppTheme {
        CartDiscountRow(
            discount = ExampleDiscounts[2],
            title = stringResource(pay_n_items_and_get),
            modifier = Modifier
        )
    }
}
