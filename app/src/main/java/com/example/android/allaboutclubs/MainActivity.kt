package com.example.android.allaboutclubs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.allaboutclubs.presentation.club_detail.components.ClubDetail
import com.example.android.allaboutclubs.presentation.clubs_list.components.ClubsList
import com.example.android.allaboutclubs.presentation.common_components.Screen
import com.example.android.allaboutclubs.presentation.ui.theme.AllAboutClubsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AllAboutClubsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ClubsListScreen.route
                    ){
                        composable(route = Screen.ClubsListScreen.route){
                            ClubsList(navController=navController)
                        }
                        composable(route = Screen.ClubDetailScreen.route+"/{clubId}"){
                            ClubDetail(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AllAboutClubsTheme {
        Greeting("Android")
    }
}