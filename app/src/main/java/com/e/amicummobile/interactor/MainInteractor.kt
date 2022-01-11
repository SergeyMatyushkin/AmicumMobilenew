package com.e.amicummobile.interactor

import com.example.config.Const.LOCAL_REQUEST_METHOD
import com.example.config.Const.SERVER_REMOTE_REQUEST_METHOD
import com.example.models.ConfigToRequest
import com.example.repository.IRepositoryLocal
import com.example.repository.IRepositoryRemote
import com.example.repository.localRepository.TestDataRepository

import java.util.*

/**
 * Итерактор запрашивает данные:
 *  - с удаленного сервера
 *  - с локальной БД мобильного приложения
 *  - с тестовых данных
 */
class MainInteractor(
    private val remoteRepository: IRepositoryRemote,
    private val localRepository: IRepositoryLocal
) : IInteractor {

    override suspend fun getData(configToRequest: ConfigToRequest, modeRequest: String): String =
        when (modeRequest) {
            SERVER_REMOTE_REQUEST_METHOD -> remoteRepository.getData(configToRequest)               // данные с сервера
            LOCAL_REQUEST_METHOD -> localRepository.getData(configToRequest)                        // данные с локальной БД
            else -> TestDataRepository().getData(configToRequest)                                   // тестовые данные
        }

    override suspend fun saveHandbookData(nameMethod: String, json: String) {
        localRepository.saveHandbookData(nameMethod, json)
    }

    override suspend fun saveModuleData(period: String, date: Date, shift: Int, companyId: Int, methodName: String, json: String) {
        TODO("Not yet implemented")
    }
}
