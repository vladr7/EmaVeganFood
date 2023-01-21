package com.example.emaveganfood.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectionManager {

    val isNetworkConnectedFlow: StateFlow<Boolean>

    val isNetworkConnected: Boolean

    fun startListenNetworkState()

    fun stopListenNetworkState()
}