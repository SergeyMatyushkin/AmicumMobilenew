package com.e.amicummobile.view.menu

interface IAppMainMenu {
    fun openMainMenu(string: String)                                                                // открыть главное меню
    fun openFragment(nameFragment: String)                                                          // открыть нужный фрагмент
    fun openMain(nameFragment: String)                                                              // открыть главную страницу
    fun backFragment(nameFragment: String)                                                          // перейти назад по фрагментам
}