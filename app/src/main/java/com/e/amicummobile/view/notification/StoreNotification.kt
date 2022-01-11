package com.e.amicummobile.view.notification

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.utils.network.Network
import com.e.amicummobile.interactor.MainInteractor
import com.e.amicummobile.viewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.collections.ArrayList


/**
 * Главное хранилище системы АМИКУМ:
 *  - справочники системы
 *  - сведения о пользователе/сессия
 *  - выбор меню системы (дата, подразделение, шахта и т.д.)
 */
class StoreNotification(

    // STATE
    private val interactor: MainInteractor,                                                         // определяет откуда берем данные
    private val network: Network,                                                                   // состояние сети
    private var notificationAll: MutableLiveData<ArrayList<com.example.models.NotificationList<com.example.models.Notification>>> = MutableLiveData(),// список уведомлений пользователя
) : BaseViewModel() {

    // GETTER
    fun getNotificationAll() = notificationAll                                                      // получение всех уведомлений пользователя
    fun getNotificationPersonal(): MutableLiveData<ArrayList<com.example.models.NotificationList<com.example.models.Notification>>> {     // получение персональных уведомлений пользователя
        return notificationAll
    }

    // ACTION
    /**
     * Метод получения всех уведомлений
     */
    fun initNotifications(companyId: Int?) {
        Log.println(Log.INFO, "storeAmicum.getNotification", "Запрос уведомлений на сервере")
        Log.println(Log.INFO, "storeAmicum.getNotification", "companyId: $companyId")

        val payload = NotificationAllRequest(
            company_id = companyId
        )

        val jsonString: String = com.example.utils.Assistant.toJson(payload)

        val config = com.example.models.ConfigToRequest(
            "notification\\Notification",
            "GetNotificationAll",
            "",
            jsonString
        )

        // TODO тут может быть кАсяк, т.к. разные справочники идут через одну корутину - могут отменяться другие запросы - оттестить дополнительно под нагрузкой
        cancelJobs("requestNotification")

        val job = viewModelCoroutineScope.launch() { requestNotification(config, network.getTypeRequest()) }
        addJob("requestNotification", job)

        Log.println(Log.INFO, "storeAmicum.getNotification", "Закончил выполнение: ")
    }

    /**
     * класс запроса уведомлений
     */
    data class NotificationAllRequest(
        val company_id: Int?
    )

    private suspend fun requestNotification(configToRequest: com.example.models.ConfigToRequest, isOnline: String) =
        withContext(Dispatchers.IO) {
            class Token : TypeToken<com.example.models.JsonFromServer<ArrayList<com.example.models.NotificationList<com.example.models.Notification>>>>()

            val response = interactor.getData(configToRequest, isOnline)
            cancelJobs("requestNotification:SaveHandbookData")

            val job = viewModelCoroutineScope.launch { interactor.saveHandbookData(configToRequest.method, response) }
            addJob("requestNotification:SaveHandbookData", job)

            val temp: com.example.models.JsonFromServer<ArrayList<com.example.models.NotificationList<com.example.models.Notification>>> = Gson().fromJson(response, Token().type)
            notificationAll.postValue(temp.getItems())
        }

    // Обрабатываем ошибки
    override fun handleError(error: Throwable) {
        Log.println(
            Log.ERROR,
            "storeAmicum.handleError",
            error.message.toString()
        )
    }
}