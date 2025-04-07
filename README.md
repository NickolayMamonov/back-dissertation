# Backend для отправки карточек на Android-клиент

## Описание
Этот Ktor-бэкенд предоставляет API для отправки JSON с карточками, содержащими текст и ID изображений для Android-клиента.

## API Endpoints

### GET `/api/cards`
Возвращает список всех карточек с текстом и ID изображений.

**Пример ответа:**
```json
{
  "cards": [
    {
      "title": "Заголовок категории A",
      "type": "HEADER",
      "image": null
    },
    {
      "title": "Ancientruins",
      "type": "ROW",
      "image": 2131165326
    },
    ...
  ]
}
```

### GET `/api/cards/{count}`
Возвращает указанное количество карточек.

**Пример:** `/api/cards/5` - вернет первые 5 карточек.

### GET `/api/card/{id}`
Возвращает карточку по ID.

**Пример:** `/api/card/1` - вернет вторую карточку.

### GET `/api/cards/search?query=текст`
Ищет карточки по названию.

**Пример:** `/api/cards/search?query=Ancientruins` - вернет все карточки, содержащие слово "Ancientruins" в названии.

## Структура данных

Каждая карточка имеет следующую структуру:
```json
{
  "title": "Название карточки",
  "type": "ROW",
  "image": 2131165326
}
```

Где:
- `title` - текст карточки
- `type` - тип карточки: "HEADER" для заголовка раздела, "ROW" для обычной карточки с изображением
- `image` - ID drawable ресурса в Android-приложении (null для карточек типа HEADER)

## Важное примечание по использованию
1. ID drawable ресурсов должны соответствовать реальным ID в вашем Android-приложении
2. В примере бэкенда используется примерное значение ID (2131165326), которое нужно заменить на реальное значение из вашего приложения

## Инструкции по настройке и запуску

1. Настройте ID drawable ресурсов в CardService:
```kotlin
private object DrawableIds {
    const val IC_LAUNCHER_BACKGROUND = 2131165326 // Заменить на реальное значение
}
```

2. Запустите бэкенд:
```
./gradlew run
```

3. Сервер будет запущен на порту 8080.

4. Проверьте работу API, открыв в браузере http://localhost:8080/api/cards

## Интеграция с Android-клиентом

Для работы с API в Android-приложении необходимо:

1. Добавить зависимости в build.gradle:
```gradle
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation "com.squareup.okhttp3:okhttp:4.10.0"
implementation "com.squareup.okhttp3:logging-interceptor:4.10.0"
```

2. Создать модели данных для API:
```kotlin
data class CardResponse(
    val title: String,
    val type: String,
    val image: Int? = null
)

data class CardsListResponse(
    val cards: List<CardResponse>
)
```

3. Создать интерфейс для Retrofit:
```kotlin
interface ApiService {
    @GET("api/cards")
    suspend fun getAllCards(): CardsListResponse
    
    @GET("api/cards/{count}")
    suspend fun getCards(@Path("count") count: Int): CardsListResponse
    
    @GET("api/cards/search")
    suspend fun searchCards(@Query("query") query: String): CardsListResponse
}
```

4. Преобразовать данные с сервера в модель Card:
```kotlin
fun CardResponse.toCard(): Card {
    return Card(
        title = title,
        type = when(type) {
            "HEADER" -> CardType.HEADER
            "ROW" -> CardType.ROW
            else -> CardType.ROW
        },
        image = image
    )
}
```

## Как определить ID drawable ресурса в Android?

Чтобы определить ID drawable ресурса в Android, можно использовать следующий код:

```kotlin
val resourceId = R.drawable.ic_launcher_background
Log.d("ResourceID", "ID ресурса ic_launcher_background: $resourceId")
```

Это поможет вам получить ID для использования в бэкенде.
