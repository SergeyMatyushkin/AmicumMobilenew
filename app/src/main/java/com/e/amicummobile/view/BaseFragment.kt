package com.e.amicummobile.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.amicummobile.viewmodel.StoreAmicum

abstract class BaseFragment<T> : Fragment() {
    // Храним ссылку на презентер
    protected lateinit var storeAmicum: StoreAmicum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeAmicum = ViewModelProvider(requireActivity())[StoreAmicum::class.java]             // подключаем центрально хранилище
    }


}