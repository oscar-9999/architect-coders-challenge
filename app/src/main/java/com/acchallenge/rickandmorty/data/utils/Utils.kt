package com.acchallenge.rickandmorty.data.utils

import android.util.Log
import kotlinx.serialization.json.Json
import java.lang.Exception

fun getPageIntFromUrl(url: String): Int? {
    return try {
        url.substringAfterLast("=").toInt()
    } catch (e: Exception) {
        null
    }
}


fun getEpisodeIntFromUrl(url: String): Int? {
    return try {
        url.substringAfterLast("/").toInt()
    } catch (e: Exception) {
        null
    }
}

val json = Json { ignoreUnknownKeys = true }