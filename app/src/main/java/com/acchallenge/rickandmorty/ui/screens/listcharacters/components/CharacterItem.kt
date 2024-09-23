package com.acchallenge.rickandmorty.ui.screens.listcharacters.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.request.ImageRequest
import coil.transform.Transformation
import com.acchallenge.rickandmorty.R
import com.acchallenge.rickandmorty.domain.model.CharacterItem
import com.acchallenge.rickandmorty.ui.common.MediumSpacer
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CharacterItem(character: CharacterItem, modifier: Modifier = Modifier, onClick: (Int) -> Unit) {

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Card(
            modifier = modifier
                .animateContentSize()
                .padding(4.dp)
                .clickable {
                    onClick(character.id)
                },
            shape = RoundedCornerShape(8.dp),
            elevation = 2.dp
        ) {
            Row {
                PictureCard(
                    imageLink = character.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                )
                MediumSpacer()
                CharacterInfo(
                    character = character,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun PictureCard(
    imageLink: String,
    modifier: Modifier,
    transformations: List<Transformation> = emptyList(),
) {
    CoilImage(
        imageRequest =
        ImageRequest
            .Builder(LocalContext.current)
            .data(imageLink)
            .crossfade(true)
            .transformations(transformations)
            .build(),
        alignment = Alignment.Center,
        loading = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                    )
            ) {
                val indicator = createRef()
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(indicator) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        },
        circularReveal = CircularReveal(
            duration = 200,
        ),
        modifier = modifier

    )
}

@Composable
fun CharacterInfo(
    character: CharacterItem,
    modifier: Modifier = Modifier,
    showExtraInfo: Boolean = true,
    alignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Text(
            text = character.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6.copy(
                textDecoration = TextDecoration.Underline
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (showExtraInfo) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.origin
                    ), fontWeight = FontWeight.Bold, fontSize = 14.sp
                )
                Text(text = character.origin)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(
                    id = R.string.status
                ), fontWeight = FontWeight.Bold, fontSize = 14.sp
            )
            Text(text = character.status + " - " + character.species)
        }
    }
}