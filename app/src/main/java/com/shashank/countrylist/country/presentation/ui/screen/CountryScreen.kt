package com.shashank.countrylist.country.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shashank.countrylist.R
import com.shashank.countrylist.core.presentation.ui.component.rememberForeverLazyListState
import com.shashank.countrylist.core.utils.NetworkResult
import com.shashank.countrylist.country.presentation.ui.component.CountryItem
import com.shashank.countrylist.country.presentation.ui.component.SearchField
import com.shashank.countrylist.country.presentation.viewModels.CountryViewModel

/**
 * 'CountryScreen' currently displays List of Countries. Unlike traditional UIs where Loading,
 * Displaying Error, Headers used to be different UI components at different sections of the code.
 * LazyColumn helps to keep related UI component together while displaying a list.
 * CountryList also has a feature to Search between countries, it can search through Country Names,
 * Country ISO-Alpha-2 & Country ISO-Alpha-3 codes.
 */

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CountryScreen(countryViewModel: CountryViewModel = hiltViewModel()) {

    val refreshing = countryViewModel.isRefreshing.collectAsStateWithLifecycle().value
    val pullRefreshState = rememberPullRefreshState(refreshing, { countryViewModel.refresh() })
    val countryResult = countryViewModel.countryList.collectAsStateWithLifecycle().value
    var searchTerm by remember { mutableStateOf("") }
    val lazyColumnListState = rememberForeverLazyListState(
        key = stringResource(id = R.string.app_name).toLowerCase(
            Locale.current
        )
    )

    LaunchedEffect(key1 = Unit) {
        countryViewModel.getCountryList()
    }

    Box {
        LazyColumn(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxWidth(),
            state = lazyColumnListState
        ) {
            when (countryResult) {
                is NetworkResult.Success -> {
                    stickyHeader(key = countryResult.data) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillParentMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top,
                        ) {
                            SearchField(
                                value = searchTerm,
                                onChange = { data -> searchTerm = data },
                                modifier = Modifier.border(
                                    width = dimensionResource(id = R.dimen.spacer_2),
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacer_10))
                                )
                            )
                        }
                    }

                    val countries = countryResult.data
                    countries?.distinctBy { countryList -> countryList.id }
                        ?.let { countryListItems ->
                            items(countryListItems.sortedBy { it.displayOrder }.filter {
                                    it.countryName?.contains(
                                        searchTerm, ignoreCase = true
                                    ) == true || it.isoAlpha2?.contains(
                                        searchTerm, ignoreCase = true
                                    ) == true || it.isoAlpha3?.contains(
                                        searchTerm, ignoreCase = true
                                    ) == true
                                }, key = { countries -> countries.id as Int }) { countryItem ->
                                CountryItem(
                                    countryListItem = countryItem
                                )
                            }
                        }
                }

                is NetworkResult.Loading -> {
                    item(
                        key = countryResult.message,
                    ) {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                is NetworkResult.Error -> {
                    item(
                        key = countryResult.message,
                    ) {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = stringResource(id = R.string.error_colon) + "${countryResult.message}")
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_20)))
                            TextButton(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacer_8)),
                                onClick = {
                                    countryViewModel.getCountryList()
                                },
                                content = {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(text = stringResource(id = R.string.retry))
                                    }
                                })
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing, pullRefreshState, Modifier.align(alignment = Alignment.TopCenter)
        )
    }
}