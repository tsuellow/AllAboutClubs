package com.example.android.allaboutclubs.presentation.club_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.android.allaboutclubs.R
import com.example.android.allaboutclubs.common.annotatedStringResource
import com.example.android.allaboutclubs.common.getCountryResource
import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.presentation.club_detail.ClubDetailViewModel
import com.example.android.allaboutclubs.presentation.ui.theme.AllAboutClubsTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun ClubDetail(
    navController: NavController,
    viewModel: ClubDetailViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = viewModel.club.value?.name ?: stringResource(id = R.string.loading)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back
                            )
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ClubDetailBody(club = viewModel.club.value)
        }
    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ClubDetailBody(club: Club?) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                val painter = rememberImagePainter(
                    data = club?.image?.ifEmpty { R.mipmap.ic_launcher_foreground }
                        ?: R.mipmap.ic_launcher_foreground,
                    builder = {
                        placeholder(R.mipmap.ic_launcher_foreground)
                    }
                )
                Image(
                    painter = painter,
                    contentDescription = stringResource(id = R.string.club_insignia),
                    //modifier = Modifier.size(72.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    club?.let {
                        Text(
                            text = stringResource(id = it.country.getCountryResource()),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(2f))
                }

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        club?.let { club ->

            Text(
                annotatedStringResource(
                    id = R.string.first_line_detail, club.name, stringResource(
                        id = club.country.getCountryResource()
                    ), club.value
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                annotatedStringResource(
                    id = R.string.second_line_detail,
                    club.name,
                    club.european_titles
                )
            )

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
        ClubDetailBody(club = club)
    }
}
