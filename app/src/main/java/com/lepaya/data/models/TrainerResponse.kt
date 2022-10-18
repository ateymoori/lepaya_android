package com.lepaya.data.models

import com.google.gson.annotations.SerializedName
import com.lepaya.domain.entities.TrainerEntity


data class TrainerResponse(
    @SerializedName("name") val name: NameResponse,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("isAvailable") val isAvailable: Boolean,
    @SerializedName("picture") val picture: String,
    @SerializedName("email") val email: String,
    @SerializedName("about") val about: String,
    @SerializedName("favoriteFruit") val favoriteFruit: String,
    @SerializedName("tags") val tags: List<String>
)
data class NameResponse(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)

fun TrainerResponse.mapToTrainerEntity(): TrainerEntity {
    return TrainerEntity(
        picture = picture,
        first_name = name.first,
        last_name = name.last,
        isAvailable = isAvailable,
        favoriteFruit = favoriteFruit,
        about = about,
        email = email,
        full_name = "${name.first} ${name.last} ",
        tags = tags
    )
}

fun List<TrainerResponse>.mapToTrainersEntity(): List<TrainerEntity> {
    return this.map {
        it.mapToTrainerEntity()
    }
}

//for test and compose preview purposes
val SampleTrainer = TrainerResponse(
    name = NameResponse(first = "Amir", last = "Teymoori"),
    avatar = "https://en.wikipedia.org/wiki/Michael_Scott_(The_Office)#/media/File:MichaelScott.png",
    isAvailable = true,
    picture = "https://en.wikipedia.org/wiki/Michael_Scott_(The_Office)#/media/File:MichaelScott.png",
    email = "test@test.com",
    favoriteFruit = "Banana",
    about = "he is an Android developer.",
    tags = listOf("funny","smart","honest")
)

