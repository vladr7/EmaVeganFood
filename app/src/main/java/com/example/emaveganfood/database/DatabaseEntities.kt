package com.example.emaveganfood.database

import android.provider.ContactsContract.Data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.emaveganfood.data.models.Food
import com.google.firebase.Timestamp

@Entity
data class DatabaseFood (
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val addedDateInSeconds: Long = Timestamp.now().seconds,
    val imageRef: String = ""
)

fun List<DatabaseFood>.asDomainModel(): List<Food> {
    return map {
        Food(
            id = it.id,
            title = it.title,
            description = it.description,
            addedDateInSeconds = it.addedDateInSeconds,
            imageRef = it.imageRef
        )
    }
}

fun DatabaseFood.asDomainModel(): Food {
    return Food(
        id = this.id,
        title = this.title,
        description = this.description,
        addedDateInSeconds = this.addedDateInSeconds,
        imageRef = this.imageRef
    )
}

fun List<Food>.asDatabaseModel(): List<DatabaseFood> {
    return this.map {
        DatabaseFood(
            id = it.id,
            title = it.title,
            description = it.description,
            addedDateInSeconds = it.addedDateInSeconds,
            imageRef = it.imageRef
        )
    }
}

fun Food.asDatabaseModel() : DatabaseFood {
    return DatabaseFood(
        id = this.id,
        title = this.title,
        description = this.description,
        addedDateInSeconds = this.addedDateInSeconds,
        imageRef = this.imageRef

    )
}

