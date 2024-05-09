package com.danialtavakoli.omdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danialtavakoli.omdb.ui.theme.OMDbTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OMDbTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MoviesScreen()
                }
            }
        }
    }

    @Composable
    fun MoviesScreen(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "movieList") {
            composable("movieList") {
                val viewModel = hiltViewModel<MovieViewModel>()
                MovieListScreen(
                    viewModel = viewModel,
                    onItemClick = { imdbId ->
                        navController.navigate("movieDetails/$imdbId")
                    },
                    context = this@MainActivity,
                    modifier = modifier,
                )
            }
            composable("movieDetails/{imdbId}") { backStackEntry ->
                val viewModel = hiltViewModel<MovieViewModel>()
                val imdbId = backStackEntry.arguments?.getString("imdbId")
                if (imdbId != null) MovieDetailsScreen(
                    viewModel = viewModel,
                    imdbId = imdbId,
                    context = this@MainActivity,
                    modifier = modifier,
                )
            }
        }
    }
}