package com.lepaya.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.lepaya.presentation.ui.theme.Teal600

val samples = listOf("Tag 1", "Tag 2")

@ExperimentalCoilApi
@Composable
@Preview
fun TagsBarView(list: List<String>? = samples) {
    LazyRow (horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(items = list ?: samples, itemContent = { item ->

            Card(

                shape = RoundedCornerShape(6.dp),
                backgroundColor = Teal600,
                elevation = 2.dp,
            ) {
                Column( Modifier.padding(6.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(

                        text = item,
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                }
            }
        })
    }
}