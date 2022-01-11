package com.example.repository.localRepository

import com.example.db.HandbooksDAO
import com.example.db.model.Handbooks
import com.example.models.ConfigToRequest
import com.example.repository.IRepositoryLocal
import java.util.*

class RoomRepository(
    private val handbooksDao: HandbooksDAO
) : IRepositoryLocal {
    override suspend fun getData(configToRequest: ConfigToRequest): String {
        return handbooksDao.json(configToRequest.method).jsonString
    }

    override suspend fun saveHandbookData(methodName: String, json: String) {
        if (json != "") {
            handbooksDao.insert(Handbooks(methodName, json))
        }
    }

    override suspend fun saveModuleData(period: String, date: Date, shift: Int, companyId: Int, methodName: String, json: String) {
        TODO("Not yet implemented")
    }
}