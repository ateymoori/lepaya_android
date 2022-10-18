package com.lepaya.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.lepaya.data.models.SampleTrainer
import com.lepaya.data.models.mapToTrainerEntity
import com.lepaya.domain.entities.TrainerEntity

@ExperimentalCoilApi
@Composable
@Preview
fun TrainerDetailsView(trainerEntity: TrainerEntity? = SampleTrainer.mapToTrainerEntity()) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(trainerEntity?.picture),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .weight(2f),
            contentScale = ContentScale.Fit
        )

        Card(
            shape = RoundedCornerShape(6.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 3.dp,
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .height(140.dp)
                .padding(12.dp)
        ) {
            Column(
                Modifier.padding(8.dp)
            ) {
                Text(
                    text = trainerEntity?.full_name ?: "",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Column {
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Availability : ")
                            }
                            append(if (trainerEntity?.isAvailable == true) "Available" else "Not available")
                        })
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Email : ")
                            }
                            append(trainerEntity?.email ?: "")
                        })
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Favorite Fruit : ")
                            }
                            append(trainerEntity?.favoriteFruit ?: "")
                        })
                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        TagsBarView(trainerEntity?.tags)
                    }
                }

            }
        }
    }
}