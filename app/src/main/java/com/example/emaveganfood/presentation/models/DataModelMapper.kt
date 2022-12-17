package com.example.emaveganfood.presentation.models

import com.example.emaveganfood.data.models.Model

interface DataModelMapper<M : Model, VD : ViewData> {
    fun mapToModel(viewData: VD): M

    fun mapToViewData(model: M): VD
}