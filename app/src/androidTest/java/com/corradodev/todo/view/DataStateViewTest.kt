package com.corradodev.todo.view

import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.corradodev.todo.data.DataError
import com.corradodev.todo.data.DataState
import com.corradodev.todo.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class DataStateViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowLoadingWithLoadingState() {
        composeTestRule.setContent {
            AppTheme(darkTheme = true) {
                DataStateView(DataState.Loading) {}
            }
        }
        composeTestRule.onNodeWithTag(TestTags.LOADING_VIEW_PROGRESS_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun shouldShowErrorWithErrorState() {
        val error = "Error"
        composeTestRule.setContent {
            AppTheme(darkTheme = true) {
                DataStateView(DataState.Error(DataError(error))) {}
            }
        }
        composeTestRule.onNodeWithText(error).assertIsDisplayed()
    }

    @Test
    fun shouldShowContentWithSuccessState() {
        val content = "Content"
        composeTestRule.setContent {
            AppTheme {
                DataStateView(DataState.Success(content)) {
                    Text(it)
                }
            }
        }
        composeTestRule.onNodeWithText(content).assertIsDisplayed()
    }

    @Test
    fun shouldShowEmptyWithSuccessEmptyState() {
        composeTestRule.setContent {
            AppTheme {
                DataStateView(DataState.Success<List<Unit>>(listOf())) {}
            }
        }
        composeTestRule.onNodeWithText("No Data").assertIsDisplayed()
    }
}
