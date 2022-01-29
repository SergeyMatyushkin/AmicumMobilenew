package com.e.amicummobile.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.e.amicummobile.interactor.MainInteractor
import com.example.models.Company
import com.example.models.ConfigToRequest
import com.example.models.UserSession
import com.example.utils.Assistant
import com.example.utils.network.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@Config(
    sdk = [30]
)
class StoreAmicumTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

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
        testCoroutineRule.runBlockingTest {
            val login = "1"
            val pwd = "1"
            val typeAuthorization = false
            store.initLogin(login, pwd, typeAuthorization)



            val payload = StoreAmicum.UserAutorizationActionLoginRequest(
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

            Mockito.verify(interactor, Mockito.times(1)).getData(config, network.getTypeRequest())
        }
    }

}