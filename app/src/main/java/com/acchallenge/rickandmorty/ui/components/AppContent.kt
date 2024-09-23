package com.acchallenge.rickandmorty.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.acchallenge.rickandmorty.ui.navigation.NavigationGraph

@Composable
fun AppContent() {
    val navController = rememberNavController()
    NavigationGraph(navController)
}
