package com.danialtavakoli.omdb

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danialtavakoli.omdb.ui.EmptyListView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * EmptyListViewTest is an instrumentation test class for testing the EmptyListView composable.
 * It verifies whether the EmptyListView is displayed correctly with the appropriate message.
 */
@RunWith(AndroidJUnit4::class)
class EmptyListViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    /**
     * Test method to verify the rendering of the EmptyListView composable.
     * It checks if the "No movies found" text is displayed correctly.
     */
    @Test
    fun testEmptyListView() {
        composeTestRule.setContent {
            EmptyListView()
        }

        composeTestRule.onNodeWithText("No movies found").assertExists()
    }
}