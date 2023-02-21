package com.example.emaveganfood.network

import androidx.room.PrimaryKey
import com.example.emaveganfood.database.DatabaseFood
import com.google.firebase.Timestamp

@JsonClass(generateAdapter = true)
data class NetworkFoodContainer(val foods: List<NetworkFood>)

@JsonClass(generateAdapter = true)
data class NetworkFood(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val addedDateInSeconds: Long = Timestamp.now().seconds,
    val imageRef: String = ""
)


fun NetworkFoodContainer.asDatabaseModel(): List<DatabaseFood> {
    return foods.map {
        DatabaseFood(
            id = it.id,
            title = it.title,
            description = it.description,
            addedDateInSeconds = it.addedDateInSeconds,
            imageRef = it.imageRef
        )
    }
}