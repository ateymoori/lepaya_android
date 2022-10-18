package com.lepaya.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.lepaya.domain.entities.TrainerEntity

@ExperimentalCoilApi
@Composable
fun TrainersListItemView(trainerEntity: TrainerEntity?) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp , end=8.dp)
                .height(250.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(trainerEntity?.picture),
                contentDescription = null,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(CircleShape),

                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .padding(start = 8.dp , end=8.dp)
                    .height(55.dp)
            ) {
                Text(
                    trainerEntity?.full_name ?: "",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    trainerEntity?.email.toString() ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
}