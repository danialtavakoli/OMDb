package com.danialtavakoli.omdb

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danialtavakoli.omdb.ui.SearchBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchBar() {
        var searchText = ""
        composeTestRule.setContent {
            SearchBar(
                onSearch = { searchText = it },
                onFilterClick = {},
                onApplyFilter = {},
                clearYear = {}
            )
        }

        val testInput = "Inception"
        composeTestRule.onNodeWithText("Search here...").performTextInput(testInput)

        assert(searchText == testInput)
    }
}
