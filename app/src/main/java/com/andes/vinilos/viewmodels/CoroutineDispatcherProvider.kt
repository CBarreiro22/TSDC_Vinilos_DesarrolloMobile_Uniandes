package com.andes.vinilos.viewmodels

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}