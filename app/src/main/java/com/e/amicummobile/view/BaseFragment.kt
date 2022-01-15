package com.e.amicummobile.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.amicummobile.viewmodel.StoreAmicum
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent

abstract class BaseFragment<T> : Fragment() {
    protected lateinit var storeAmicum: StoreAmicum                                                 // Храним ссылку на вьюмодель
    private lateinit var storeAmicumScopeInstance: Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeAmicumScopeInstance = KoinJavaComponent.getKoin().getOrCreateScope("storeAmicumScopeId", named("AMICUM_STORE"))
        storeAmicum = storeAmicumScopeInstance.get()
    }

}