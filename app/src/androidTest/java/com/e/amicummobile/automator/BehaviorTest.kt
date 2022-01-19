package com.e.amicummobile.automator

import android.content.Context
import android.content.Intent
import androidx.room.FtsOptions
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.e.amicummobile.R
import com.e.amicummobile.view.menu.MainMenuFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.room.FtsOptions.Order
import org.junit.FixMethodOrder
import org.junit.runner.OrderWith
import org.junit.runners.MethodSorters
import java.lang.StringBuilder


@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)                   // порядок выполнения тестов
class BehaviorTest {
    // Класс UiDevice предоставляет доступ к вашему устройству.
    // Именно через UiDevice вы можете управлять устройством, открывать приложения
    // и находить нужные элементы на экране
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    // Контекст нам понадобится для запуска нужных экранов и получения packageName
    private val context = ApplicationProvider.getApplicationContext<Context>()

    // Путь к классам нашего приложения, которые мы будем тестировать
    private val packageName = context.packageName

    @Before
    fun setup() {

        //Для начала сворачиваем все приложения, если у нас что-то запущено
        uiDevice.pressHome()

        //Запускаем наше приложение
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        //Мы уже проверяли Интент на null в предыдущем тесте, поэтому допускаем, что Интент у нас не null
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)                                          //Чистим бэкстек от запущенных ранее Активити
        context.startActivity(intent)

        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    //Убеждаемся, что приложение открыто. Для этого достаточно найти на экране любой элемент и проверить его на null
    @Test
    fun test_1MainActivityIsStarted() {

        //Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, "txtLogin"))

        //Проверяем на null
        Assert.assertNotNull(editText)
    }

    //Убеждаемся, что поиск работает как ожидается
    @Test
    fun test_2TxtLoginPositive() {
        // Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, "txtLogin"))

        // Устанавливаем значение
        editText.text = "UiAutomator"

        // Убеждаемся, что сервер вернул корректный результат. Обратите внимание, что количество
        // результатов может варьироваться во времени, потому что количество репозиториев постоянно меняется .
        Assert.assertEquals(editText.text.toString(), "UiAutomator")
    }

    //Убеждаемся, что DetailsScreen открывается
    @Test
    fun test_3openMainMenuScreen() {
        // Находим кнопку
        val toDetails = uiDevice.findObject(
            By.res(
                packageName,
                "btnLogin"
            )
        )
        // Кликаем по ней
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "openMainMenu")),
                TIMEOUT
            )
    }

    companion object {
        private const val TIMEOUT = 5000L
    }
}