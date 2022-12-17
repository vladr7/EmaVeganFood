package com.example.emaveganfood.presentation.models.helper

import com.example.emaveganfood.data.models.helper.Model

interface DataModelMapper<M : Model, VD : ViewData> {
    fun mapToModel(viewData: VD): M

    fun mapToViewData(model: M): VD
}