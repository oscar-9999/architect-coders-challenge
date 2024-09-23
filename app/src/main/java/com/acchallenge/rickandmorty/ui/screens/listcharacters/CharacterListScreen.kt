package com.acchallenge.rickandmorty.ui.screens.listcharacters

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.acchallenge.rickandmorty.R
import com.acchallenge.rickandmorty.ui.screens.listcharacters.components.CharactersListColumn
import com.acchallenge.rickandmorty.utils.PermissionAction
import com.acchallenge.rickandmorty.utils.PermissionUI
import com.acchallenge.rickandmorty.utils.ResetSystemBars
import kotlinx.coroutines.launch
import kotlin.math.min

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CharacterList(navigate: (Int) -> Unit, viewModel: CharacterListViewModel = hiltViewModel()) {
    ResetSystemBars()

    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val characterListState by viewModel.characterListState.collectAsState()
    val requestLocationPermission by viewModel.requestLocationPermission.collectAsState()

    val scrollOffset = min(
        1f.coerceAtMost(1f),
        (1 - (remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }.value/ 2000f + remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value)).coerceAtLeast(
            0f
        )
    )

    val characters = characterListState.dataList?.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Text(
                text = stringResource(
                    id = R.string.character_list
                ),
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .testTag("characterListText"),
                style = MaterialTheme.typography.h3
            )
        },
        scaffoldState = scaffoldState,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            AnimatedVisibility(visible = scrollOffset == 0f) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.padding(bottom = 50.dp).testTag("floatingActionButton"),
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                }
            }

        }
    ) {
        if (requestLocationPermission) {
            PermissionUI(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                "request Permissions",
                scaffoldState
            ) { permissionAction ->
                when (permissionAction) {
                    is PermissionAction.OnPermissionGranted -> {
                        viewModel.setRequestLocationPermission(false)
                    }

                    is PermissionAction.OnPermissionDenied -> {
                        viewModel.setRequestLocationPermission(false)
                    }
                }
            }
        }
        if (!requestLocationPermission) {
            Box(modifier = Modifier.padding(bottom = 32.dp)) {
                if (characterListState.errorMessage.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = characterListState.errorMessage)
                    }
                }
                characters?.let { items ->
                    CharactersListColumn(items = items, listState = lazyListState) { characterId ->
                        navigate(characterId)
                    }
                }
            }
        }
    }
}
