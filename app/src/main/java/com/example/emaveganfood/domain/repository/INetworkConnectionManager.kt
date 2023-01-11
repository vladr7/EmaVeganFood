package com.example.emaveganfood.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface INetworkConnectionManager {

    val isNetworkConnectedFlow: StateFlow<Boolean>

    val isNetworkConnected: Boolean

    fun startListenNetworkState()

    fun stopListenNetworkState()
}