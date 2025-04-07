package com.example

import com.example.models.SimpleCardResponse
import com.example.models.SimpleScreenResponse

/**
 * Простой сервис для создания UI
 */
class SimpleUIService {
    /**
     * Создает экран со списком карточек
     */
    fun getSimpleCardsScreen(): SimpleScreenResponse {
        val cardService = CardService()
        val cards = cardService.getAllCards()
        
        // Преобразуем карточки в SimpleCardResponse
        val simpleCards = cards.mapIndexed { index, card ->
            SimpleCardResponse(
                id = "card_${index}",
                title = card.title,
                type = card.type,
                image = card.image,
                actionUrl = if (card.type != "HEADER") "/card/${index}" else null
            )
        }
        
        // Создаем экран
        return SimpleScreenResponse(
            id = "cards_screen",
            title = "Список карточек",
            items = simpleCards,
            backgroundColor = "#F5F5F5",
            toolbarColor = "#2196F3"
        )
    }
    
    /**
     * Создает экран с деталями карточки
     */
    fun getSimpleCardDetailScreen(cardId: Int): SimpleScreenResponse {
        val cardService = CardService()
        val card = cardService.getCard(cardId) ?: throw IllegalArgumentException("Карточка не найдена с ID $cardId")
        
        // Создаем карточку с деталями
        val detailCard = SimpleCardResponse(
            id = "card_detail_${cardId}",
            title = "Детали: ${card.title}",
            type = "DETAIL",
            image = card.image
        )
        
        // Создаем карточку с кнопкой назад
        val backButton = SimpleCardResponse(
            id = "back_button",
            title = "Назад к списку",
            type = "BUTTON",
            image = card.image,
            actionUrl = "/cards"
        )
        
        // Создаем экран
        return SimpleScreenResponse(
            id = "card_detail_screen_${cardId}",
            title = "Детали карточки",
            items = listOf(detailCard, backButton),
            backgroundColor = "#FFFFFF",
            toolbarColor = "#2196F3"
        )
    }
}