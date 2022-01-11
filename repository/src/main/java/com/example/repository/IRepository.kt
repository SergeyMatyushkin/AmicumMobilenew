package com.example.repository

import com.example.models.ConfigToRequest
import java.util.*


interface IRepository {
    suspend fun getData(
        configToRequest: ConfigToRequest,                                                           // конфигураия запроса
    ): String                                                                                       // главный метод получения данных
}

interface IRepositoryLocal : IRepository {
    suspend fun saveHandbookData(methodName: String, json: String)
    suspend fun saveModuleData(
        period: String,             // период - год,месяц
        date: Date,                 // дата и время
        shift: Int,                 // смена
        companyId: Int,             // подразделение
        methodName: String,         // название метода
        json: String                // ответ от сервера
    )
}

interface IRepositoryRemote : IRepository {
}
