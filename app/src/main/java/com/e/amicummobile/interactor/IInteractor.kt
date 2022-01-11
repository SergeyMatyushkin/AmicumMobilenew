package com.e.amicummobile.interactor

import com.example.models.ConfigToRequest
import java.util.*

interface IInteractor {
    /**
     * @param configToRequest - конфигурация запроса
     * @param modeRequest - режим запроса, локальная сеть, из базы, из внешней сети
     */
    suspend fun getData(configToRequest: com.example.models.ConfigToRequest, modeRequest: String): String
    suspend fun saveHandbookData(nameMethod: String, json: String)
    suspend fun saveModuleData(period: String, date: Date, shift: Int, companyId: Int, methodName: String, json: String)

}