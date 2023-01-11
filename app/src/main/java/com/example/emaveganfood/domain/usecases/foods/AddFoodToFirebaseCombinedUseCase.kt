package com.example.emaveganfood.domain.usecases.foods

import android.net.Uri
import com.example.emaveganfood.core.utils.State
import com.example.emaveganfood.data.models.Food
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class AddFoodToFirebaseCombinedUseCase @Inject constructor(
    private val addFoodUseCase: AddFoodUseCase,
    private val addFoodImageToStorageUseCase: AddFoodImageToStorageUseCase
) {

    suspend operator fun invoke(food: Food, imageUri: Uri?) = channelFlow<State<Food>> {
        addFoodImageToStorageUseCase(food, imageUri).collectLatest { storageReferenceState ->
            when (storageReferenceState) {
                is State.Failed -> {
                    send(State.failed(storageReferenceState.message))
                }
                is State.Loading -> {
                    send(State.loading())
                }
                is State.Success -> {
                    addFoodUseCase(food, imageUri).collectLatest { foodState ->
                        when(foodState) {
                            is State.Failed -> {
                                send(State.failed(foodState.message))
                            }
                            is State.Loading -> {
                                send(State.loading())
                            }
                            is State.Success -> {
                                send(State.success(foodState.data))
                            }
                        }
                    }
                }
            }
        }
    }
}
