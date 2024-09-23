package com.acchallenge.rickandmorty.utils

import androidx.compose.runtime.Composable

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ScaffoldState
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect

@Composable
fun PermissionUI(
    context: Context,
    permission: String,
    permissionRationale: String,
    scaffoldState: ScaffoldState,
    permissionAction: (PermissionAction) -> Unit
) {

    val permissionGranted =
        Common.checkIfPermissionGranted(
            context,
            permission
        )

    if (permissionGranted) {
        permissionAction(PermissionAction.OnPermissionGranted)
        return
    }


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted
            permissionAction(PermissionAction.OnPermissionGranted)
        } else {
            // Permission Denied
            permissionAction(PermissionAction.OnPermissionDenied)
        }
    }


    val showPermissionRationale = Common.shouldShowPermissionRationale(
        context,
        permission
    )


    if (showPermissionRationale) {
        LaunchedEffect(showPermissionRationale) {

            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = permissionRationale,
                actionLabel = "Grant Access",
                duration = SnackbarDuration.Long
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    //User denied the permission, do nothing
                    permissionAction(PermissionAction.OnPermissionDenied)
                }
                SnackbarResult.ActionPerformed -> {
                    launcher.launch(permission)
                }
            }
        }
    } else {
        //Request permissions again
        SideEffect {
            launcher.launch(permission)
        }
    }
}


