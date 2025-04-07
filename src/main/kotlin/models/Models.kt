package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    val title: String,
    val type: String, // "HEADER" или "ROW"
    val image: String // Строковый идентификатор изображения
)

@Serializable
data class CardsListResponse(
    val cards: List<CardResponse>
)

@Serializable
data class TextRequest(
    val text: String
)