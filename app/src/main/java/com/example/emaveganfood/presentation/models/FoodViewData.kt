package com.example.emaveganfood.presentation.models

import com.example.emaveganfood.data.models.Food
import javax.inject.Inject

data class FoodViewData(
    val title: String = "",
    val description: String = "",
    val imageRef: String = "",
): ViewData()

class FoodMapper @Inject constructor(): DataModelMapper<Food, FoodViewData> {

    override fun mapToModel(viewData: FoodViewData): Food {
        TODO("Not yet implemented")
    }

    override fun mapToViewData(model: Food): FoodViewData {
        val title = model.title
        val description = model.description
        val imageRef = model.imageRef
        return FoodViewData(title, description, imageRef)
    }

}