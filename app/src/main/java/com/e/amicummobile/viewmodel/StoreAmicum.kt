package com.e.amicummobile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.utils.network.Network
import com.e.amicummobile.interactor.MainInteractor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.collections.ArrayList
import com.example.models.Company
import com.example.models.ConfigToRequest
import com.example.models.JsonFromServer
import com.example.models.UserSession
import com.example.utils.Assistant


/**
 * Главное хранилище системы АМИКУМ:
 *  - справочники системы
 *  - сведения о пользователе/сессия
 *  - выбор меню системы (дата, подразделение, шахта и т.д.)
 */
class StoreAmicum(

    // STATE
    private val interactor: MainInteractor,                                                         // определяет откуда берем данные
    private val network: Network,                                                                   // состояние сети
    private var userSession: MutableLiveData<UserSession> = MutableLiveData(),                      // сессия пользователя
    private var departmentList: MutableLiveData<ArrayList<Company>> = MutableLiveData(),            // список подразделений

) : BaseViewModel() {

    // GETTER
    fun getUserSession() = userSession                                                              // получение объекта сессии
    fun getDepartmentList() = departmentList                                                        // получение подразделений компании

    // ACTION
    /**
     * Метод авторизации пользователя на сервер и получения сессионных данных о нем
     */
    fun initLogin(login: String, pwd: String, typeAuthorization: Boolean) {
        Log.println(Log.INFO, "storeAmicum.getLogin", "Запрос авторизации на сервере")
        Log.println(
            Log.INFO,
            "storeAmicum.getLogin",
            "login: $login pwd: $pwd typeAuthorization: $typeAuthorization"
        )

        val payload = UserAutorizationActionLoginRequest(
            login = login,
            password = pwd,
            activeDirectoryFlag = typeAuthorization
        )

        val jsonString: String = Assistant.toJson(payload)

        val config = ConfigToRequest(
            "UserAutorization",
            "actionLogin",
            "",
            jsonString
        )

        cancelJobs("requestSession")

        val job = viewModelCoroutineScope.launch {
            requestSession(config, network.getTypeRequest())
        }
        addJob("requestSession", job)
    }

    /**
     * Метод получения списка подразделений
     */
    fun initDepartments() {
        Log.println(Log.INFO, "storeAmicum.requestDepartments", "Запрос уведомлений на сервере")

        val config = ConfigToRequest(
            "handbooks\\Department",
            "GetDepartmentList",
            "",
            ""
        )

        cancelJobs("requestDepartments")

        val job = viewModelCoroutineScope.launch { requestDepartments(config, network.getTypeRequest()) }
        addJob("requestDepartments", job)

        Log.println(Log.INFO, "storeAmicum.requestDepartments", "Закончил выполнение: ")
    }

    /**
     * класс запроса авторизации пользователя
     */
    data class UserAutorizationActionLoginRequest(
        val login: String,
        val password: String,
        val activeDirectoryFlag: Boolean
    )

    /**
     * Метод проверки наличия авторизации пользователя на сервере
     */
    fun checkUserSession(): Boolean {
        var statusSession = false
        if (userSession.value != null && userSession.value?.worker_id != -1) {
            statusSession = true
        }
        return statusSession
    }

    private suspend fun requestSession(configToRequest: ConfigToRequest, isOnline: String) =
        withContext(Dispatchers.IO) {
            class Token : TypeToken<JsonFromServer<UserSession>>()

            val response = interactor.getData(configToRequest, isOnline)
            cancelJobs("requestSession:SaveHandbookData")
            val job = viewModelCoroutineScope.launch {
                interactor.saveHandbookData(configToRequest.method, response)
            }                                  // TODO - под большим вопросом!!! Рассмотреть вопрос сохранения сессии на телефоне - ограничение корпоративной безопасности
            addJob("requestSession:SaveHandbookData", job)
            val temp: JsonFromServer<UserSession> = Gson().fromJson(response, Token().type)
            userSession.postValue(temp.getItems())
        }

    private suspend fun requestDepartments(configToRequest: ConfigToRequest, isOnline: String) =
        withContext(Dispatchers.IO) {
            class Token : TypeToken<JsonFromServer<ArrayList<Company>>>()
            val response = interactor.getData(configToRequest, isOnline)
            cancelJobs("requestDepartments:SaveHandbookData")

            val job = viewModelCoroutineScope.launch { interactor.saveHandbookData(configToRequest.method, response) }
            addJob("requestDepartments:SaveHandbookData", job)

            val temp: JsonFromServer<ArrayList<Company>> = Gson().fromJson(response, Token().type)
            departmentList.postValue(temp.getItems())
        }


    // Обрабатываем ошибки
    override fun handleError(error: Throwable) {
        Log.println(
            Log.ERROR,
            "storeAmicum.handleError",
            error.message.toString()
        )
    }

    fun searchInDepartmentList(query: String): Flow<ArrayList<Company>> {
        return flow {
            var result: ArrayList<Company> = ArrayList()
            if (departmentList.value != null) {
                result = departmentList.value!!.filter { company -> company.title.indexOf(query) > -1 } as ArrayList<Company>
            }
            if (result.isEmpty()) {
                result.add(Company(0, "Результат пуст", 0, ArrayList(), ArrayList())) //TODO переделать на sealed класс
            }
            emit(result)
        }
    }
}