package com.example.models

/**
 * Модель запроса данных на сервере
 */
data class ConfigToRequest(
    val controller: String,                                                                                             // название контроллера
    val method: String,                                                                                                 // название метода
    val subscribe: String,                                                                                              // подписка
    val data: String                                                                                                    // полезная нагрузка
)
