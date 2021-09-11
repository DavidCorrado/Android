package com.corradodev.todo.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.corradodev.todo.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class LoadingViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowLoading() {
        composeTestRule.setContent {
            AppTheme {
                LoadingView()
            }
        }
        composeTestRule.onNodeWithTag(TestTags.LOADING_VIEW_PROGRESS_INDICATOR).assertIsDisplayed()
    }
}
