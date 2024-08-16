package com.shashank.countrylist.country.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shashank.countrylist.R
import com.shashank.countrylist.country.countryList.model.CountryListItem
import com.shashank.countrylist.country.presentation.viewModels.CountryViewModel

/**
 * 'CountryItem' contains UI for a row in a list. It displays information related to a specific country.
 */

@Composable
fun CountryItem(countryListItem: CountryListItem, countryViewModel: CountryViewModel = hiltViewModel()){
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.spacer_10))
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.spacer_5)),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .alpha(0.1f)
                        .width(IntrinsicSize.Min),
                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.text_180).value.sp
                    ),
                    text = countryViewModel.getCountryFlag(countryListItem.countryCode.toString())
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(dimensionResource(id = R.dimen.spacer_20)),
                    text = countryViewModel.getCountryFlag(countryListItem.countryCode.toString()),
                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.text_80).value.sp),
                    textAlign = TextAlign.Center,
                )
                Column(Modifier.padding(dimensionResource(id = R.dimen.spacer_8))) {
                    Text(
                        text = stringResource(id = R.string.country_name)+"${countryListItem.countryName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_20)))
                    Text(
                        text = stringResource(id = R.string.iso_2)+"${countryListItem.isoAlpha2}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_20)))
                    Text(
                        text = stringResource(id = R.string.iso_3)+"${countryListItem.isoAlpha3}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}