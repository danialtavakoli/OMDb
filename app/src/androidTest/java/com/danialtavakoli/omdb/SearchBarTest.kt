package com.danialtavakoli.omdb

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danialtavakoli.omdb.ui.SearchBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * SearchBarTest is an instrumentation test class to test the functionality of the SearchBar composable.
 * It verifies that the SearchBar composable correctly handles user input and updates the searchText variable.
 *
 * @see [testing documentation](http://d.android.com/tools/testing) Official documentation for Android testing.
 */
@RunWith(AndroidJUnit4::class)
class SearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    /**
     * Test method to verify the behavior of the SearchBar composable.
     * It simulates user input in the search field and asserts that the searchText variable is updated accordingly.
     */
    @Test
    fun testSearchBar() {
        var searchText = ""
        composeTestRule.setContent {
            SearchBar(
                onSearch = { searchText = it },
                onApplyFilter = {},
                clearFilters = {},
            )
        }

        val testInput = "Inception"
        composeTestRule.onNodeWithText("Search here...").performTextInput(testInput)

        assert(searchText == testInput)
    }
}
