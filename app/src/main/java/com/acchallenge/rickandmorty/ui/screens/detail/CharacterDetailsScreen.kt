package com.acchallenge.rickandmorty.ui.screens.detail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import com.acchallenge.rickandmorty.R
import com.acchallenge.rickandmorty.domain.model.CharacterDetails
import com.acchallenge.rickandmorty.ui.common.MediumSpacer
import com.acchallenge.rickandmorty.ui.screens.listcharacters.components.PictureCard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.min

@ExperimentalCoroutinesApi
@Composable
fun CharacterDetailsScreen(
    navigate: () -> Unit,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),

    ) {

    val lazyListState = rememberLazyListState()
    val scrollOffset = min(
        1f.coerceAtMost(1f),
        (1 - (remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }.value / 2000f + remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value)).coerceAtLeast(
            0f
        )
    )

    val imageSize by animateFloatAsState(
        targetValue = 0.4f * scrollOffset,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = FastOutSlowInEasing
        ), label = ""

    )

    val colorPalette by remember {
        mutableStateOf<Palette?>(null)
    }

    val vibrantColor = colorPalette?.lightVibrantSwatch?.rgb ?: 0
    val mutedColor = colorPalette?.lightMutedSwatch?.rgb ?: 0

    val gradient = Brush.verticalGradient(
        listOf(
            Color(vibrantColor),
            Color(mutedColor)
        )

    )

    val episodesList =
        viewModel.episodesList.collectAsState(initial = CharacterEpisodesState(isLoading = true)).value

    val detailsState = viewModel.detailsState.value
    detailsState.errorMessage?.let { Text(text = it) }

    if (detailsState.isLoading) {
        Loading()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        detailsState.data?.let { character ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),

                ) {
                CharacterImage(imageSize, gradient, character, scrollOffset)
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.h4.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("nameCharacter")
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Text(
                        text = stringResource(
                            id = R.string.status
                        ), fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                    Text(text = character.status, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.species
                        ), fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                    Text(text = character.species, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.gender
                        ), fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                    Text(text = character.gender, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.location
                        ), fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                    Text(text = character.location, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = lazyListState
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(
                                        id = R.string.episodes
                                    ) + "-${episodesList.episodesDataList.size}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        items(episodesList.episodesDataList) { episode ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                                MediumSpacer()
                                Column {
                                    Text(text = episode.episode)
                                    Text(text = episode.name, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (episodesList.isLoading) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }

                        item {
                            Text(text = episodesList.errorMessage, color = Color.Red)
                        }
                    }
                }
            }
        }
    }
    BackButton(navigate)
}

@Composable
private fun Loading(
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(shape = CircleShape) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun BackButton(navigate: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            shape = CircleShape
        ) {
            IconButton(onClick = navigate, modifier = Modifier.padding(4.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "navigate back"
                )
            }
        }
    }
}

@Composable
private fun CharacterImage(
    imageSize: Float,
    gradient: Brush,
    character: CharacterDetails,
    scrollOffset: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(imageSize)
            .background(
                brush = gradient,
            ),
        contentAlignment = Alignment.Center
    ) {
        PictureCard(
            character.image,
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollOffset / 600f))
                    translationY = -scrollOffset * 0.1f
                }
        )
    }
}
