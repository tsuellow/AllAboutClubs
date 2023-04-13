package com.example.android.allaboutclubs.presentation.clubs_list.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.example.android.allaboutclubs.presentation.clubs_list.ListState
import com.example.android.allaboutclubs.presentation.ui.theme.AllAboutClubsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubsList(
    viewModel: ClubsListViewModel = hiltViewModel(),
    navController: NavController
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "All AboutClubs") },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(ClubsListEvent.ReOrderEvent) }) {
                        if (viewModel.sorting.value is Sorting.AlphabeticallyAsc) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = "sort by value"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.SortByAlpha,
                                contentDescription = "sort alphabetically"
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
            
            Row(
                modifier = Modifier.animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
            ) {
                when(viewModel.listState.value){
                    is ListState.Loading -> {
                        CircularProgressIndicator()
                        Text(text = "loading...")
                    }
                    ListState.Refreshing -> {
                        CircularProgressIndicator()
                        Text(text = "updating...")
                    }
                    ListState.NotUpToDate -> {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "attention")
                        Text(text = "data might not be up to date")
                    }
                    ListState.Failed -> {
                        Icon(imageVector = Icons.Filled.Warning, contentDescription = "attention")
                        Text(text = "something went wrong!")
                    }
                    ListState.Success -> {}
                }

            }
            LazyColumn {
                items(viewModel.listClub.value) { item ->
                    ClubItem(club = item)
                }
            }
        }

    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ClubItem(club: Club) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
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
                contentDescription = "club insignia",
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(Modifier.weight(2f)) {
                Text(
                    text = club.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = club.country,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(2f))
                    Text(text = "${club.value} million")
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
            stadiumName = "Brernabeu",
            value = 567
        )
        ClubItem(club = club)
    }
}