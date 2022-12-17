package com.example.emaveganfood.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Food(
    @DocumentId
    @SerialName("id") val id: String = "",
    @SerialName("title") val title: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("timeInSeconds") val timeInSeconds: Long = Timestamp.now().seconds,
    val imageRef: String = ""
): java.io.Serializable, Model()
