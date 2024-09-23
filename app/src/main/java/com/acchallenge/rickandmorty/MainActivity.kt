package com.acchallenge.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.acchallenge.rickandmorty.ui.components.AppContent
import com.acchallenge.rickandmorty.ui.theme.ArchitectCodersChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
        }
        super.onCreate(savedInstanceState)
        setContent {
            BuildContent()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BuildContent() {
        ArchitectCodersChallengeTheme {
            AppContent()
        }
    }
}

