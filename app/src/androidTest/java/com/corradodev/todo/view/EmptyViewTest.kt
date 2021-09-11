package com.corradodev.todo.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.corradodev.todo.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class EmptyViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowNoData() {
        composeTestRule.setContent {
            AppTheme {
                EmptyView()
            }
        }
        composeTestRule.onNodeWithText("No Data").assertIsDisplayed()
    }
}
