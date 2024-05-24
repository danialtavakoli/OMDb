/**
 * MovieDetailsScreen is a composable function responsible for displaying the details of a movie.
 * It shows the movie's title, year, genre, poster, IMDb votes, director, and writer.
 * Users can click on the poster to view it in full screen.
 * If internet connectivity is not available, it shows a toast message indicating the same.
 *
 * @param viewModel The view model responsible for managing movie details data.
 * @param imdbId The IMDb ID of the movie to fetch details for.
 * @param context The context of the MainActivity.
 * @param modifier The modifier for the layout.
 */
package com.danialtavakoli.omdb.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danialtavakoli.omdb.model.net.NetworkChecker
import com.danialtavakoli.omdb.model.net.showToast

@Composable
fun MovieDetailsScreen(
    viewModel: MovieViewModel,
    imdbId: String,
    context: MainActivity,
    modifier: Modifier = Modifier,
) {
    val movieDetails by viewModel.movieDetails.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val isInternetConnected = NetworkChecker(context).isInternetConnected
        viewModel.fetchMovieDetails(isInternetConnected = isInternetConnected, imdbID = imdbId)
        if (!isInternetConnected) context.showToast("Internet not connected!")
    }

    if (!NetworkChecker(context).isInternetConnected && movieDetails.imdbID.isEmpty()) return

    val gradient = Brush.linearGradient(
        colors = listOf(Color.White, MaterialTheme.colorScheme.primary),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = movieDetails.poster,
            contentDescription = movieDetails.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movieDetails.poster))
                    context.startActivity(intent)
                }
        )
        Text(
            text = movieDetails.title,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(color = Color.Gray)
        Text(
            text = "Year: ${movieDetails.year}\nGenre: ${movieDetails.genre}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    RoundedCornerShape(8.dp),
                )
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "IMDb Votes",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movieDetails.imdbVotes,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Text(
            text = "Director: ${movieDetails.director}\nWriter: ${movieDetails.writer}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}
