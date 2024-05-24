/**
 * MainActivity is the entry point of the application.
 * It sets up the main user interface and navigation using Jetpack Compose and Hilt.
 */

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


/**
 * MainActivity class responsible for handling the main entry point of the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    /**
     * Called when the activity is starting. This is where most initialization should go.
     * It sets up the content view using Jetpack Compose.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in [onSaveInstanceState].
     */
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


    /**
     * Composable function that defines the main screen of the application.
     * It sets up the navigation structure using [NavHost].
     *
     * @param modifier A [Modifier] for this composable.
     */
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

    companion object {
        /**
         * The minimum year value for movie searches.
         */

        const val MIN_YEAR = 1888

        /**
         * The maximum year value for movie searches.
         */
        const val MAX_YEAR = 2024
    }
}