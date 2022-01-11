package com.e.amicummobile.viewmodel

import androidx.lifecycle.MutableLiveData
import com.e.amicummobile.interactor.MainInteractor
import com.example.config.Const
import com.example.models.Company
import com.example.models.UserSession
import com.example.utils.network.Network
import kotlinx.coroutines.runBlocking
import org.junit.Assert


import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class StoreAmicumTest {

    private lateinit var store: StoreAmicum

    @Mock
    private lateinit var interactor: MainInteractor

    @Mock
    private lateinit var network: Network

    @Mock
    private var userSession: MutableLiveData<UserSession> = MutableLiveData()

    @Mock
    private var departmentList: MutableLiveData<ArrayList<Company>> = MutableLiveData()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        store = StoreAmicum(interactor, network)
    }

    @Test
    fun initLogin_Test(): Unit = runBlocking {

        val login = "1"
        val pwd = "1"
        val typeAuthorization = false

        store.initLogin(login, pwd, typeAuthorization)

        val payload = StoreAmicum.UserAutorizationActionLoginRequest(
            login = login,
            password = pwd,
            activeDirectoryFlag = typeAuthorization
        )

        val jsonString: String = com.example.utils.Assistant.toJson(payload)

        val config = com.example.models.ConfigToRequest(
            "UserAutorization",
            "actionLogin",
            "",
            jsonString
        )
        Mockito.verify(interactor, Mockito.times(1)).getData(config, Const.LOCAL_REQUEST_METHOD)
    }

}