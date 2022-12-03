package com.example.emaveganfood.ui.models

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Food(
    @DocumentId
    @SerialName("id") val id: String = "",
    @SerialName("title") val title: String = "",
    @SerialName("description") val description: String = "",
): java.io.Serializable
