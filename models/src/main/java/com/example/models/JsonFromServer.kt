package com.example.models

/**
 * Модель данных приходящих с сервера
 */

class JsonFromServer<T>(
    var Items: T,                                                                                   // полезная нагрузка
    var errors: Any,                                                                                // массив ошибок
    var debug: Any,                                                                                 // массив отладочной информации
    var debugData: Any,                                                                             // массив отладочных данных
    var status: Int = 0                                                                             // статус выполнения метода на сервере
) {
    @JvmName("getItems1")
    fun getItems(): T {
        return Items
    }
}