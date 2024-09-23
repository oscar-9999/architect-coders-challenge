package com.acchallenge.rickandmorty.utils

sealed class PermissionAction {
    data object OnPermissionGranted : PermissionAction()
    data object OnPermissionDenied : PermissionAction()
}