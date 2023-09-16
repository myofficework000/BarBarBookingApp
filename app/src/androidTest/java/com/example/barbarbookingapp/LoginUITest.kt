package com.example.barbarbookingapp

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.barbarbookingapp.view.MainActivity
import com.example.barbarbookingapp.view.intro_screens.Login
import com.example.barbarbookingapp.viewmodel.BarberViewModel
import com.example.barbarbookingapp.viewmodel.UserViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class LoginUITest {

    @get:Rule
    val composable = createAndroidComposeRule<MainActivity>()

    private lateinit var emailText: SemanticsNodeInteraction
    private lateinit var passwordInput: SemanticsNodeInteraction
    private lateinit var loginButton: SemanticsNodeInteraction
    private lateinit var visibleButton: SemanticsNodeInteraction

    @Before
    fun setUp(){

        composable.activity.setContent{
            val navController = rememberNavController()
            val viewModel = hiltViewModel<UserViewModel>()
            Login(navController = navController, viewModel = viewModel)
        }

        with(composable){
            emailText = onNodeWithText("Email")
            passwordInput = onNodeWithTag("passwordInput")
            loginButton = onNodeWithText("LOGIN")
            visibleButton = onNodeWithTag("visibility")
        }
    }

    @Test
    fun verifyAllViewsExists() {
        emailText.assertExists()
        passwordInput.assertExists()
        loginButton.assertExists()
        visibleButton.assertExists()
    }

    @Test
    fun verifyInput(){
        emailText.performTextInput("test@email.com")
        passwordInput.performTextInput("testpassword")
        emailText.assertTextEquals("test@email.com")
        passwordInput.assertExists("testpassword")
    }

    //not passed
    @Test
    fun verifyPasscodeVisibility(){
        passwordInput.performTextInput("testpassword")
        passwordInput.assertTextEquals("testpassword")
        visibleButton.performClick()
        passwordInput.assertTextEquals("testpassword")
    }
}
