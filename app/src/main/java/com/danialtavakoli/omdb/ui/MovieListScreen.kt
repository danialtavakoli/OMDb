package com.danialtavakoli.omdb.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danialtavakoli.omdb.R
import com.danialtavakoli.omdb.model.data.Movie
import com.danialtavakoli.omdb.model.net.NetworkChecker
import com.danialtavakoli.omdb.model.net.showToast

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel,
    onItemClick: (String) -> Unit,
    context: MainActivity,
    modifier: Modifier = Modifier,
) {
    var title by remember { mutableStateOf("") }
    val moviesList by viewModel.moviesList.collectAsState()
    var year by remember { mutableStateOf("") }
    LaunchedEffect(title, moviesList, year) {
        val isInternetConnected = NetworkChecker(context).isInternetConnected
        if (year.equals(""))
            viewModel.fetchMovies(
                title = title, isInternetConnected = isInternetConnected
            )
        else
            viewModel.fetchMoviesByYear(
                title = title,
                isInternetConnected = isInternetConnected,
                year = year
            )
        if (!isInternetConnected && moviesList.isEmpty()) context.showToast("Internet is not connected!")
    }

    Column(modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onSearch = { newTitle ->
                title = newTitle
            },
            onFilterClick = {},
            onApplyFilter = { year = it },
            clearYear = {year=""}
        )
        if (moviesList.isEmpty()) {
            EmptyListView()
        } else {
            LazyColumn {
                items(items = moviesList) { movie ->
                    MovieListItem(movie = movie, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
fun EmptyListView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_film),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Start exploring by searching for movies using the search bar above.",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick(movie.imdbID) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            AsyncImage(
                model = movie.poster,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.year,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onFilterClick: () -> Unit,
    onApplyFilter: (String) -> Unit,
    clearYear:()->Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var isFilterDialogVisible by remember { mutableStateOf(false) } // State to control dialog visibility
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(it.text)
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            singleLine = true,
            placeholder = {
                Row {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )

                    Text(text = "Search here...")
                }
            },
        )
        IconButton(onClick = {
            isFilterDialogVisible = true
            onFilterClick()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                modifier = Modifier.size(24.dp)
            )
        }

    }
    if (isFilterDialogVisible) {
        FilterSearchDialog(
            // Pass necessary parameters and callbacks
            onDismiss = { isFilterDialogVisible = false
                clearYear()},
            onApplyFilter = {
                onApplyFilter(it)
                isFilterDialogVisible = false
            },
            // Pass initial values or current filter values here
            minPrice = 1888,
            maxPrice = 2024,
        )
    }
}
@Composable
fun FilterSearchDialog(
    minPrice: Int,
    maxPrice: Int,
    onApplyFilter: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var year by remember { mutableStateOf(minPrice.toFloat()) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter Search") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SliderRow("Year", minPrice.toFloat(), maxPrice.toFloat(), year) { value ->
                    year = value
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        onApplyFilter(year.toInt().toString())
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Apply Filter")
                }
            }
        }
    )
}

@Composable
fun SliderRow(
    label: String,
    minValue: Float,
    maxValue: Float,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(label)
        Spacer(modifier = Modifier.width(8.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = minValue..maxValue, // Set minimum and maximum values for the slider
            steps = (maxValue - minValue).toInt(),
            modifier = Modifier.weight(1f)
        )
        Text(text = "${value.toInt()}")
    }
}