package com.acchallenge.rickandmorty.ui.screens.listcharacters

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class CharacterListScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testCharacterListScreen_isDisplayed() {
        with(composeTestRule) {
            setContent {
                CharacterList(navigate = {})
            }
        }

        val textElement = composeTestRule.onNode(hasTestTag("characterListText"), true)
        textElement.assertIsDisplayed()
        val floatingActionButtonElement = composeTestRule.onNode(hasTestTag("floatingActionButton"), true)
        floatingActionButtonElement.assertIsDisplayed()
    }
}