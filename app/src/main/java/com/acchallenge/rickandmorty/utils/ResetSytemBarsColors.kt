package com.acchallenge.rickandmorty.utils

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ResetSystemBars() {
    val uiController = rememberSystemUiController()
    val darkIcons = isSystemInDarkTheme().not()
    LaunchedEffect(key1 = true, block = {
        uiController.setStatusBarColor(color = Color.Transparent, darkIcons = darkIcons)
    })

}
