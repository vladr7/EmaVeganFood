package com.example.emaveganfood.domain.repository

import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import com.example.emaveganfood.database.asDatabaseModel
import com.example.emaveganfood.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface NewFoodRepository {

    val foods: Flow<List<Food>>

    suspend fun refreshFoods()
}