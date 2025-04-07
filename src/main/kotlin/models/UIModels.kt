package com.example.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Модель экрана
 */
@Serializable
data class ScreenResponse(
    val id: String,
    val title: String? = null,
    val rootComponent: ComponentWrapper? = null,
    val toolbarColor: String? = null,
    val backgroundColor: String? = null,
    val statusBarColor: String? = null
)

/**
 * Обертка для компонентов UI
 */
@Serializable
data class ComponentWrapper(
    val type: String,
    val component: JsonObject
)

/**
 * Базовый интерфейс для UI компонентов
 */
interface UIComponent {
    val id: String
    val type: String
}

/**
 * Модель контейнера
 */
@Serializable
data class ContainerComponent(
    override val id: String,
    override val type: String = "container",
    val children: List<ComponentWrapper>,
    val orientation: String = "vertical",
    val padding: Padding? = null,
    val margin: Margin? = null,
    val backgroundColor: String? = null,
    val width: String? = null,
    val height: String? = null,
    val alignment: String? = null,
    val action: Action? = null  // Добавляем поле action
) : UIComponent

/**
 * Модель текста
 */
@Serializable
data class TextComponent(
    override val id: String,
    override val type: String = "text",
    val text: String,
    val textColor: String? = null,
    val textSize: Int? = null,
    val textStyle: String? = null,
    val textAlign: String? = null,
    val padding: Padding? = null,
    val margin: Margin? = null,
    val maxLines: Int? = null
) : UIComponent

/**
 * Модель кнопки
 */
@Serializable
data class ButtonComponent(
    override val id: String,
    override val type: String = "button",
    val text: String,
    val textColor: String? = null,
    val backgroundColor: String? = null,
    val padding: Padding? = null,
    val margin: Margin? = null,
    val action: Action? = null,
    val enabled: Boolean = true,
    val style: String? = null
) : UIComponent

/**
 * Модель изображения
 */
@Serializable
data class ImageComponent(
    override val id: String,
    override val type: String = "image",
    val url: String? = null,
    val resourceId: String? = null, // Теперь это String вместо Int
    val contentDescription: String? = null,
    val width: String? = null,
    val height: String? = null,
    val scaleType: String? = null,
    val padding: Padding? = null,
    val margin: Margin? = null
) : UIComponent

/**
 * Модель ввода текста
 */
@Serializable
data class InputComponent(
    override val id: String,
    override val type: String = "input",
    val hint: String? = null,
    val initialValue: String? = null,
    val inputType: String? = null,
    val textColor: String? = null,
    val backgroundColor: String? = null,
    val padding: Padding? = null,
    val margin: Margin? = null,
    val onChange: Action? = null,
    val maxLength: Int? = null,
    val maxLines: Int? = null
) : UIComponent

/**
 * Модель списка
 */
@Serializable
data class ListComponent(
    override val id: String,
    override val type: String = "list",
    val items: List<ComponentWrapper>,
    val orientation: String = "vertical",
    val scrollable: Boolean = true,
    val padding: Padding? = null,
    val margin: Margin? = null,
    val divider: Boolean = false,
    val dividerColor: String? = null,
    val backgroundColor: String? = null
) : UIComponent

/**
 * Модель padding
 */
@Serializable
data class Padding(
    val top: Int = 0,
    val bottom: Int = 0,
    val start: Int = 0,
    val end: Int = 0
)

/**
 * Модель margin
 */
@Serializable
data class Margin(
    val top: Int = 0,
    val bottom: Int = 0,
    val start: Int = 0,
    val end: Int = 0
)

/**
 * Модель действия
 */
@Serializable
data class Action(
    val type: String, // "navigate", "api", "none"
    val url: String? = null,
    val data: Map<String, String>? = null
)
