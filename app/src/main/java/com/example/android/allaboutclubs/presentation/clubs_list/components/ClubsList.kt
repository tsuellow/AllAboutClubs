package com.example.android.allaboutclubs.presentation.clubs_list.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.use_case.Sorting
import com.example.android.allaboutclubs.presentation.clubs_list.ClubsListEvent
import com.example.android.allaboutclubs.presentation.clubs_list.ClubsListViewModel
import com.example.android.allaboutclubs.R
import com.example.android.allaboutclubs.common.Constants
import com.example.android.allaboutclubs.common.getCountryResource
import com.example.android.allaboutclubs.presentation.clubs_list.ListState
import com.example.android.allaboutclubs.presentation.common_components.Screen
import com.example.android.allaboutclubs.presentation.ui.theme.AllAboutClubsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubsList(
    viewModel: ClubsListViewModel = hiltViewModel(),
    navController: NavController
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = stringResource(R.string.all_about_clubs)) },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(ClubsListEvent.ReOrderEvent)
                        scope.launch { listState.animateScrollToItem(0) }
                    }) {
                        if (viewModel.sorting.value is Sorting.AlphabeticallyAsc) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = stringResource(R.string.sort_by_value)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.SortByAlpha,
                                contentDescription = stringResource(R.string.sort_alpha)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (viewModel.listState.value !is ListState.Success) {
                Spacer(Modifier.height(12.dp))
            }
            Card(
                modifier = Modifier.animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                if (viewModel.listState.value !is ListState.Success) {

                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(0.85f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        when (val state = viewModel.listState.value) {
                            is ListState.Loading -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                                    Text(
                                        text = stringResource(R.string.loading),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                            is ListState.Refreshing -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                                    Text(
                                        text = stringResource(R.string.updating),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                            is ListState.NotUpToDate -> {
                                var show by remember {
                                    mutableStateOf(false)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "attention"
                                    )
                                    Text(
                                        text = stringResource(R.string.not_up_to_date),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    OutlinedButton(
                                        onClick = { show = !show },
                                        contentPadding = PaddingValues(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        )
                                    ) {
                                        Text(text = stringResource(if (show) R.string.less else R.string.more))
                                    }
                                }
                                if (show) {
                                    Row {
                                        Text(text = stringResource(R.string.showing_data))
                                        Text(
                                            text = " " + viewModel.lastUpdated.value,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    Text(
                                        text = stringResource(R.string.details),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        text = explainError(state.msg),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center
                                    )
                                }


                            }
                            is ListState.Failed -> {
                                var show by remember {
                                    mutableStateOf(false)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = stringResource(id = R.string.attention)
                                    )
                                    Text(
                                        text = stringResource(R.string.error),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    OutlinedButton(
                                        onClick = { show = !show },
                                        contentPadding = PaddingValues(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(if (show) R.string.less else R.string.more)
                                        )
                                    }
                                }
                                if (show) {
                                    Text(
                                        text = "Details:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(0.8f),
                                        text = explainError(state.msg),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            ListState.Success -> {}
                        }
                    }

                }
            }
            if (viewModel.listState.value !is ListState.Success) {
                Spacer(Modifier.height(12.dp))
            }
            LazyColumn(state = listState) {
                items(viewModel.listClub.value) { item ->
                    ClubItem(
                        club = item,
                        onItemClick = { id ->
                            navController.navigate(
                                Screen.ClubDetailScreen.withId(id)
                            )
                        }
                    )
                }
            }

        }

    }

}

@Composable
private fun explainError(type: String) =
    when (type) {
        Constants.NETWORK_ERROR -> stringResource(R.string.http_exep)
        Constants.IO_ERROR -> stringResource(R.string.io_exep)
        else -> stringResource(R.string.unknown_exep)
    }

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ClubItem(club: Club, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clickable { onItemClick(club.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberImagePainter(
                data = club.image.ifEmpty { R.mipmap.ic_launcher_foreground },
                builder = {
                    placeholder(R.mipmap.ic_launcher_foreground)
                }
            )
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.club_insignia),
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(Modifier.weight(2f)) {
                Text(
                    text = club.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(id = club.country.getCountryResource()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(2f))
                    Text(text = stringResource(id = R.string.value_million, club.value))
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    AllAboutClubsTheme {
        val club = Club(
            id = "dfdg",
            country = "Spanien",
            european_titles = 2,
            image = "",
            name = "Real Madrid",
            stadiumName = "Bernabeu",
            value = 567
        )
        ClubItem(club = club, onItemClick = {})
    }
}