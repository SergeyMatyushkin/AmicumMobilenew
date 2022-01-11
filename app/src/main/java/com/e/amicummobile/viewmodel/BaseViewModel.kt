package com.e.amicummobile.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    var jobs = mutableMapOf<String, MutableList<Job>>()                                                    // словарь запущенных корутин - ключ - название метода

    // Объявляем свой собственный скоуп
    // В качестве аргумента передается CoroutineContext, который мы составляем через "+" из трех частей:
    // - Dispatchers.Main говорит, что результат работы предназначен для основного потока;
    // - SupervisorJob() позволяет всем дочерним корутинам выполняться независимо, то есть, если какая-то корутина упадёт с ошибкой, остальные будут выполнены нормально;
    // - CoroutineExceptionHandler позволяет перехватывать и отрабатывать ошибки и краши
    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    // Завершаем все незавершённые корутины, потому что пользователь закрыл
// экран
    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    // отменяем запросы выполняющиеся по конкретной конфигурации
    protected fun cancelJobs(nameCoroutine: String) {
        if (jobs.containsKey(nameCoroutine)) {
            for (job in jobs[nameCoroutine]!!) {
                job.cancel()
            }
            jobs.remove(nameCoroutine)
        }
    }

    // Добавляем запросы на отслеживание
    protected fun addJob(nameCoroutine: String, job: Job) {
        if (!jobs.containsKey(nameCoroutine)) {
            jobs[nameCoroutine] = mutableListOf()
        }

        jobs[nameCoroutine]!!.add(job)
    }

    // обрабатываем ошибки в конкретной имплементации базовой ВьюМодели
    abstract fun handleError(error: Throwable)
}