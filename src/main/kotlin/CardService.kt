package com.example

import com.example.models.CardResponse
import com.example.models.CardsListResponse

/**
 * Сервис для работы с карточками
 */
class CardService {
    // Объект для хранения строковых идентификаторов изображений
    object DrawableIds {
        const val IC_HEADER = "header_image"
        const val IC_CARD_1 = "card_image_1"
        const val IC_CARD_2 = "card_image_2"
        const val IC_CARD_3 = "card_image_3"
        const val IC_CARD_4 = "card_image_4"
        const val IC_CARD_5 = "card_image_5"
    }
    
    // Создаем список карточек. В реальном приложении это могло бы быть в базе данных
    private val cards = listOf(
        CardResponse(
            title = "Рекомендованные для вас", 
            type = "HEADER", 
            image = DrawableIds.IC_HEADER
        ),
        CardResponse(
            title = "Недвижимость: 5 лучших объектов", 
            type = "ROW", 
            image = DrawableIds.IC_CARD_1
        ),
        CardResponse(
            title = "Автомобили: новинки месяца", 
            type = "ROW", 
            image = DrawableIds.IC_CARD_2
        ),
        CardResponse(
            title = "Путешествия: горячие туры", 
            type = "ROW", 
            image = DrawableIds.IC_CARD_3
        ),
        CardResponse(
            title = "Новые категории", 
            type = "HEADER", 
            image = DrawableIds.IC_HEADER
        ),
        CardResponse(
            title = "Технологии: смартфоны 2025", 
            type = "ROW", 
            image = DrawableIds.IC_CARD_4
        ),
        CardResponse(
            title = "Спорт: товары для активного отдыха", 
            type = "ROW", 
            image = DrawableIds.IC_CARD_5
        )
    )
    
    /**
     * Получить все карточки
     */
    fun getAllCards(): List<CardResponse> {
        return cards
    }
    
    /**
     * Получить карточки с ограничением по количеству
     */
    fun getCards(count: Int): List<CardResponse> {
        return cards.take(count)
    }
    
    /**
     * Получить карточку по ID
     */
    fun getCard(id: Int): CardResponse? {
        return if (id in cards.indices) cards[id] else null
    }
    
    /**
     * Поиск карточек по названию
     */
    fun searchCards(query: String): List<CardResponse> {
        // Игнорируем карточки с типом HEADER при поиске
        return cards.filter { 
            it.type != "HEADER" && it.title.contains(query, ignoreCase = true) 
        }
    }
}