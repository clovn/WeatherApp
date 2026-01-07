Для запуска нужен api_key https://developer.accuweather.com/
Его нужно записать в .env файл либо в модуле :core:util в файле Constants.kt захардкодить
```kotlin
val API_KEY: String? = "{api_key}"
```
тесты есть в следующих подмодулях feature: auth:impl, detail:impl, home:impl, pick:impl
