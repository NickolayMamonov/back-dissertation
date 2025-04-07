package com.example

import com.example.models.*
import com.example.models.SimpleScreenResponse
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import org.slf4j.event.*

fun Application.configureRouting() {
    install(Resources)
    
    // Создаем классы для работы с карточками и UI
    val cardService = CardService()
    val uiService = UIService()
    val simpleUiService = SimpleUIService()
    
    routing {
        get("/") {
            call.respondText("Backend API для BDUI")
        }
        
        // Получение UI для экранов
        get("/api/ui/welcome") {
            val screen = uiService.getWelcomeScreen()
            call.respond(screen)
        }
        
        get("/api/ui/cards") {
            val screen = uiService.getCardsScreen()
            // Добавляем логирование
            application.log.info("Sending cards screen: ${Json.encodeToString(screen)}")
            call.respond(screen)
        }
        
        // Упрощенные эндпоинты
        get("/api/simple/cards") {
            val screen = simpleUiService.getSimpleCardsScreen()
            call.respond(screen)
        }
        
        get("/api/simple/card/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: 0
            try {
                val screen = simpleUiService.getSimpleCardDetailScreen(id)
                call.respond(screen)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, "Карточка не найдена: ${e.message}")
            }
        }
        
        get("/api/ui/card/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: 0
            try {
                val screen = uiService.getCardDetailScreen(id)
                call.respond(screen)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, "Карточка не найдена: ${e.message}")
            }
        }
        
        // Получение списка карточек
        get("/api/cards") {
            val cards = cardService.getAllCards()
            // Отправляем напрямую список карточек
            call.respond(cards)
        }
        
        // Получение определенного количества карточек
        get("/api/cards/{count}") {
            val count = call.parameters["count"]?.toIntOrNull() ?: 10
            val cards = cardService.getCards(count)
            call.respond(cards)
        }
        
        // Получение одной карточки
        get("/api/card/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: 0
            val card = cardService.getCard(id)
            if (card != null) {
                call.respond(card)
            } else {
                call.respond(HttpStatusCode.NotFound, "Карточка не найдена")
            }
        }
        
        // Поиск карточек по названию
        get("/api/cards/search") {
            val query = call.request.queryParameters["query"] ?: ""
            val cards = cardService.searchCards(query)
            call.respond(cards)
        }
        
        get<Articles> { article ->
            // Get all articles ...
            call.respond("List of articles sorted starting from ${article.sort}")
        }
    }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
