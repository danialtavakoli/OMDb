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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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


/**
 * MovieListScreen is a composable function responsible for displaying a list of movies.
 * Users can search for movies using a search bar and apply filters based on year and type.
 * It also provides an option to view movie details by clicking on a movie item.
 *
 * @param viewModel The view model responsible for managing movie list data.
 * @param onItemClick A callback function triggered when a movie item is clicked.
 * @param context The context of the MainActivity.
 * @param modifier The modifier for the layout.
 */
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
    var type by remember { mutableStateOf("") }
    LaunchedEffect(title, moviesList, year, type) {
        val isInternetConnected = NetworkChecker(context).isInternetConnected
        if (year == "" && type == "")
            viewModel.fetchMovies(
                title = title, isInternetConnected = isInternetConnected
            )
        else if (type == "" && year.isNotEmpty())
            viewModel.fetchMoviesByYear(
                title = title,
                isInternetConnected = isInternetConnected,
                year = year
            )
        else if (type.isNotEmpty() && (year == "" || year == "1888"))
            viewModel.fetchMoviesByType(
                title = title,
                isInternetConnected = isInternetConnected,
                type = type
            )
        else {
            viewModel.fetchMoviesByYearAndType(
                title = title,
                isInternetConnected = isInternetConnected,
                year = year,
                type = type
            )
        }

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
            onApplyFilter = { year = it.first;type = it.second },
            clearYear = { year = "" }
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


/**
 * EmptyListView is a composable function responsible for displaying an empty view
 * when the movies list is empty.
 */
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
            text = "No movies found",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Start exploring by searching for movies using the search bar above",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

/**
 * MovieListItem is a composable function responsible for displaying an individual movie item.
 *
 * @param movie The movie item to be displayed.
 * @param onItemClick A callback function triggered when the movie item is clicked.
 * @param modifier The modifier for the layout.
 */
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

/**
 * SearchBar is a composable function responsible for displaying the search bar
 * and filtering options.
 *
 * @param modifier The modifier for the layout.
 * @param onSearch The callback function triggered when a new search query is entered.
 * @param onFilterClick The callback function triggered when the filter button is clicked.
 * @param onApplyFilter The callback function triggered when filters are applied.
 * @param clearYear The callback function triggered when the year filter is cleared.
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onFilterClick: () -> Unit,
    onApplyFilter: (Pair<String, String>) -> Unit,
    clearYear: () -> Unit
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
            onDismiss = {
                isFilterDialogVisible = false
                clearYear()
            },
            onApplyFilter = {
                onApplyFilter(it)
                isFilterDialogVisible = false
            },
            // Pass initial values or current filter values here
            minYear = 1888,
            maxYear = 2024,
            contentTypes = listOf("movie", "series", "episode"),
            selectedContentType = "",
            onContentTypeSelected = {}
        )
    }
}


/**
 * FilterSearchDialog is a composable function responsible for displaying the filter dialog
 * for applying additional filters such as year and content type.
 *
 * @param minYear The minimum value for the year filter.
 * @param maxYear The maximum value for the year filter.
 * @param contentTypes The list of content types for filtering.
 * @param selectedContentType The selected content type.
 * @param onContentTypeSelected The callback function triggered when a content type is selected.
 * @param onApplyFilter The callback function triggered when filters are applied.
 * @param onDismiss The callback function triggered when the dialog is dismissed.
 */
@Composable
fun FilterSearchDialog(
    minYear: Int,
    maxYear: Int,
    contentTypes: List<String>, // List of content types
    selectedContentType: String,
    onContentTypeSelected: (String) -> Unit,
    onApplyFilter: (Pair<String, String>) -> Unit,
    onDismiss: () -> Unit
) {
    var year by remember { mutableStateOf(minYear.toFloat()) }
    var currentSelectedContentType by remember { mutableStateOf(selectedContentType) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter Search") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SliderRow("Year", minYear.toFloat(), maxYear.toFloat(), year) { value ->
                    year = value
                }
                Spacer(modifier = Modifier.height(16.dp))
                DropdownMenuRow(
                    label = "Content Type",
                    options = contentTypes,
                    selectedOption = currentSelectedContentType,
                    onOptionSelected = {
                        currentSelectedContentType = it
                        onContentTypeSelected(it)
                    }
                )
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
                        onApplyFilter(Pair(year.toInt().toString(), currentSelectedContentType))
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Apply Filter")
                }
            }
        }
    )
}

/**
 * DropdownMenuRow is a composable function responsible for displaying a row with a dropdown menu.
 *
 * @param label The label for the dropdown menu.
 * @param options The list of options for the dropdown menu.
 * @param selectedOption The selected option.
 * @param onOptionSelected The callback function triggered when an option is selected.
 */
@Composable
fun DropdownMenuRow(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelectedOption by remember { mutableStateOf(selectedOption) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label)
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = TextFieldValue(currentSelectedOption),
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        currentSelectedOption = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


/**
 * SliderRow is a composable function responsible for displaying a row with a slider.
 *
 * @param label The label for the slider.
 * @param minValue The minimum value for the slider.
 * @param maxValue The maximum value for the slider.
 * @param value The current value of the slider.
 * @param onValueChange The callback function triggered when the slider value changes.
 */
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