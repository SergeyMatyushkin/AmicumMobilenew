package com.example.config

/**
 * Константы приложения
 */
object Const {
    // тип запроса данных
    const val LOCAL_REQUEST_METHOD = "LOCAL_REQUEST_METHOD"                                         // локальные данные
    const val SERVER_REMOTE_REQUEST_METHOD = "SERVER_REMOTE_REQUEST_METHOD"                         // данные с внешней сети сервера
    const val SERVER_LOCAL_REQUEST_METHOD = "SERVER_LOCAL_REQUEST_METHOD"                           // данные с внутренней сети сервера
    const val TEST_REQUEST_METHOD = "TEST_REQUEST_METHOD"                                           // данные тестовые

    // тип сборки проекта
    const val VERSION_PRODUCTION = 1                                                                // версия продуктив
    const val VERSION_QAS = 2                                                                       // версия опытная/проверка качества
    const val VERSION_DEBUG = 2                                                                     // версия тестовая

    // тип заголовка приложения (верхний бар)
    const val APP_BAR_MAIN = 1                                                                      // бар на главной странице
    const val APP_BAR_SECOND = 2                                                                    // бар на вспомогательных фрагментах
    const val APP_BAR_MODAL = 3                                                                     // бар в модальных фрагментах
    const val APP_BAR_ONLY_BACK = 4                                                                 // бар только c кнопкой назад
}