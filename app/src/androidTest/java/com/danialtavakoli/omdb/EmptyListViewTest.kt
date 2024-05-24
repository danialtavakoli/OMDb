package com.danialtavakoli.omdb

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danialtavakoli.omdb.ui.EmptyListView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmptyListViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyListView() {
        composeTestRule.setContent {
            EmptyListView()
        }

        composeTestRule.onNodeWithText("No movies found").assertExists()
    }
}