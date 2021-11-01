package com.corradodev.todo.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.corradodev.todo.data.DataError
import com.corradodev.todo.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class ErrorViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowErrorText() {
        val error = "Error"
        composeTestRule.setContent {
            AppTheme {
                ErrorView(DataError(error))
            }
        }
        composeTestRule.onNodeWithText(error).assertIsDisplayed()
    }

    @Test
    fun shouldShowErrorDefault() {
        val error = "Unknown"
        composeTestRule.setContent {
            AppTheme {
                ErrorView(DataError(null))
            }
        }
        composeTestRule.onNodeWithText(error).assertIsDisplayed()
    }
}
