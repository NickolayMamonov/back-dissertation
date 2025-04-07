package com.example.models

import kotlinx.serialization.Serializable

/**
 * Упрощенная модель экрана
 */
@Serializable
data class SimpleScreenResponse(
    val id: String,
    val title: String,
    val items: List<SimpleCardResponse>,
    val backgroundColor: String = "#F5F5F5",
    val toolbarColor: String = "#2196F3"
)

/**
 * Упрощенная модель карточки
 */
@Serializable
data class SimpleCardResponse(
    val id: String,
    val title: String,
    val type: String,  // "HEADER" или "ROW"
    val image: String, // строковый идентификатор изображения
    val actionUrl: String? = null
)