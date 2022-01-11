package com.e.amicummobile.di

import androidx.room.Room
import com.example.utils.network.Network
import com.e.amicummobile.interactor.MainInteractor
import com.e.amicummobile.view.notification.StoreNotification
import com.e.amicummobile.viewmodel.StoreAmicum
import com.example.db.AmicumDB
import com.example.repository.IRepositoryLocal
import com.example.repository.IRepositoryRemote
import com.example.repository.localRepository.RoomRepository
import com.example.repository.localRepository.TestDataRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Общие для всего приложения инъекции
 */
val application = module {
    single<IRepositoryRemote> { TestDataRepository() }                                              // тестовый репозиторий
//    single<IRepositoryRemote>() { RetrofitImpl() }                                                  // удаленный репозиторий
    single<IRepositoryLocal> { RoomRepository(get()) }                                              // локальный репозиторий
    single { com.example.utils.CoinImageLoader(androidContext()) }                                                    // контекст приложения
    single { Network(androidContext()) }                                                            // проваерка состояния сети
}
val mainScreen = module {
    factory { MainInteractor(get(), get()) }

    factory { StoreAmicum(get(), get()) }                                                           // главное вьюмодель приложения - хранит справочники
}

val db = module {
    single { Room.databaseBuilder(get(), AmicumDB::class.java, "AmicumDB").build() }          // база данных
    single { get<AmicumDB>().handbooksDao() }                                                       // доступ к справочникам системы
}

val notification = module {
    scope(named("NOTIFICATION_STORE")) {
        scoped { StoreNotification(get(), get()) }
    }
}