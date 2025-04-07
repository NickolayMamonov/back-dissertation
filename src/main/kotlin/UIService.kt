package com.example

import com.example.models.*
import kotlinx.serialization.json.*

/**
 * Сервис для создания UI компонентов
 */
class UIService {

    /**
     * Создаем простой демонстрационный экран приветствия
     */
    fun getWelcomeScreen(): ScreenResponse {
        // Создаем компоненты
        val welcomeText = TextComponent(
            id = "welcome_text",
            text = "Добро пожаловать в приложение BDUI!",
            textColor = "#000000",
            textSize = 20,
            textAlign = "center",
            margin = Margin(top = 16, bottom = 24)
        )
        
        val descriptionText = TextComponent(
            id = "description_text",
            text = "Это приложение демонстрирует возможности BDUI (Backend-Driven UI) для Android.",
            textColor = "#666666",
            textSize = 16,
            margin = Margin(bottom = 32)
        )
        
        val showCardsButton = ButtonComponent(
            id = "show_cards_button",
            text = "Показать карточки",
            textColor = "#FFFFFF",
            backgroundColor = "#2196F3",
            padding = Padding(top = 12, bottom = 12, start = 24, end = 24),
            margin = Margin(bottom = 16),
            action = Action(
                type = "navigate",
                url = "/cards"
            )
        )
        
        val image = ImageComponent(
            id = "welcome_image",
            resourceId = "2131165278", // Используем ID изображения из drawable
            width = "match_parent",
            height = "200",
            margin = Margin(top = 24, bottom = 24)
        )
        
        // Создаем контейнер
        val container = ContainerComponent(
            id = "root_container",
            children = listOf(
                ComponentWrapper("text", Json.encodeToJsonElement(welcomeText).jsonObject),
                ComponentWrapper("text", Json.encodeToJsonElement(descriptionText).jsonObject),
                ComponentWrapper("image", Json.encodeToJsonElement(image).jsonObject),
                ComponentWrapper("button", Json.encodeToJsonElement(showCardsButton).jsonObject)
            ),
            padding = Padding(top = 16, bottom = 16, start = 16, end = 16),
            backgroundColor = "#FFFFFF",
            alignment = "center"
        )
        
        // Создаем экран
        return ScreenResponse(
            id = "welcome_screen",
            title = "Добро пожаловать",
            rootComponent = ComponentWrapper(
                type = "container", 
                component = Json.encodeToJsonElement(container).jsonObject
            ),
            backgroundColor = "#FFFFFF",
            toolbarColor = "#2196F3"
        )
    }
    
    /**
     * Создаем экран просмотра карточек
     */
    fun getCardsScreen(): ScreenResponse {
        val cardService = CardService()
        val cards = cardService.getAllCards()
        
        // Создаем компоненты для карточек
        val cardComponents = cards.mapIndexed { index, card ->
            // Контейнер для каждой карточки
            val cardContainer = ContainerComponent(
                id = "card_${card.title.replace(" ", "_").lowercase()}",
                children = listOf(
                    ComponentWrapper(
                        type = "text",
                        component = Json.encodeToJsonElement(
                            TextComponent(
                                id = "card_title_${card.title.replace(" ", "_").lowercase()}",
                                text = card.title,
                                textColor = if (card.type == "HEADER") "#2196F3" else "#000000",
                                textSize = if (card.type == "HEADER") 18 else 16,
                                textStyle = if (card.type == "HEADER") "bold" else "normal",
                                margin = Margin(bottom = if (card.type == "HEADER") 8 else 4)
                            )
                        ).jsonObject
                    ),
                    ComponentWrapper(
                        type = "image",
                        component = Json.encodeToJsonElement(
                            ImageComponent(
                                id = "card_image_${card.title.replace(" ", "_").lowercase()}",
                                resourceId = card.image,
                                width = "match_parent",
                                height = if (card.type == "HEADER") "120" else "80",
                                scaleType = "centerCrop",
                                margin = Margin(bottom = 8)
                            )
                        ).jsonObject
                    )
                ),
                padding = Padding(all = 8),
                margin = Margin(bottom = 8),
                backgroundColor = "#FFFFFF",
                // Добавляем действие по нажатию
                action = if (card.type != "HEADER") Action(
                    type = "navigate",
                    url = "/card/${index}"
                ) else null
            )
            
            ComponentWrapper(
                type = "container",
                component = Json.encodeToJsonElement(cardContainer).jsonObject
            )
        }
        
        // Создаем список карточек
        val listComponent = ListComponent(
            id = "cards_list",
            items = cardComponents,
            padding = Padding(all = 8),
            divider = true,
            dividerColor = "#EEEEEE"
        )
        
        // Создаем экран
        return ScreenResponse(
            id = "cards_screen",
            title = "Список карточек",
            rootComponent = ComponentWrapper(
                type = "list", 
                component = Json.encodeToJsonElement(listComponent).jsonObject
            ),
            backgroundColor = "#F5F5F5",
            toolbarColor = "#2196F3"
        )
    }
    
    /**
     * Создаем экран с деталями карточки
     */
    fun getCardDetailScreen(cardId: Int): ScreenResponse {
        val cardService = CardService()
        val card = cardService.getCard(cardId) ?: throw IllegalArgumentException("Карточка не найдена с ID $cardId")
        
        // Создаем компоненты
        val titleText = TextComponent(
            id = "card_title",
            text = card.title,
            textColor = "#000000",
            textSize = 24,
            textStyle = "bold",
            margin = Margin(top = 16, bottom = 16)
        )
        
        val typeText = TextComponent(
            id = "card_type",
            text = "Тип: ${card.type}",
            textColor = "#666666",
            textSize = 16,
            margin = Margin(bottom = 24)
        )
        
        val image = ImageComponent(
            id = "card_image",
            resourceId = card.image,
            width = "match_parent",
            height = "300",
            scaleType = "centerCrop",
            margin = Margin(bottom = 24)
        )
        
        val descriptionText = TextComponent(
            id = "card_description",
            text = "Это детальное описание карточки '${card.title}'. Здесь может быть любая дополнительная информация о карточке.",
            textColor = "#333333",
            textSize = 16,
            margin = Margin(bottom = 32)
        )
        
        val backButton = ButtonComponent(
            id = "back_button",
            text = "Вернуться к списку",
            textColor = "#FFFFFF",
            backgroundColor = "#2196F3",
            padding = Padding(top = 12, bottom = 12, start = 24, end = 24),
            action = Action(
                type = "navigate",
                url = "/cards"
            )
        )
        
        // Создаем контейнер
        val container = ContainerComponent(
            id = "root_container",
            children = listOf(
                ComponentWrapper("text", Json.encodeToJsonElement(titleText).jsonObject),
                ComponentWrapper("text", Json.encodeToJsonElement(typeText).jsonObject),
                ComponentWrapper("image", Json.encodeToJsonElement(image).jsonObject),
                ComponentWrapper("text", Json.encodeToJsonElement(descriptionText).jsonObject),
                ComponentWrapper("button", Json.encodeToJsonElement(backButton).jsonObject)
            ),
            padding = Padding(top = 16, bottom = 16, start = 16, end = 16),
            backgroundColor = "#FFFFFF",
            alignment = "center"
        )
        
        // Создаем экран
        return ScreenResponse(
            id = "card_detail_screen_${cardId}",
            title = "Детали карточки",
            rootComponent = ComponentWrapper(
                type = "container", 
                component = Json.encodeToJsonElement(container).jsonObject
            ),
            backgroundColor = "#FFFFFF",
            toolbarColor = "#2196F3"
        )
    }
}

// Расширение для удобства создания Padding
private fun Padding(all: Int): Padding {
    return Padding(all, all, all, all)
}