package com.e.amicummobile.view

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

/**
 * Делегат, который возвращает View и следит за тем, чтобы корневой View был актуален
 */
class ViewByIdDelegate<out T : View>(
    private val rootGetter: () -> View?,
    private val viewId: Int
) {
    private var rootRef: WeakReference<View>? = null                                                // Ссылка на root

    private var viewRef: T? = null                                                                  // Ссылка на View

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {                               // Метод вызывается при каждом обращении к переменной
        var view = viewRef
        val cachedRoot = rootRef?.get()

        val currentRoot = rootGetter()                                                              // Получаем root
        if (currentRoot != cachedRoot || view == null) {
            if (currentRoot == null) {
                if (view != null) {
                    return view                                                                     // Failsafe, возвращать хотя бы последнюю View
                }
                throw IllegalStateException("Cannot get View, there is no root yet")
            }

            view = currentRoot.findViewById(viewId)                                                 // Создаём View

            viewRef = view                                                                          // Сохраняем ссылку на View, чтобы не создавать её каждый раз заново

            rootRef = WeakReference(currentRoot)                                                    // Сохраняем ссылку на root, чтобы не искать его каждый раз заново
        }
        checkNotNull(view) { "View with id \"$viewId\" not found in root" }

        return view                                                                                 // Возвращаем View в момент обращения к ней
    }
}

fun <T : View> Activity.viewById(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({ window.decorView.findViewById(android.R.id.content) }, viewId)        // Возвращаем корневую View
}

fun <T : View> Fragment.viewById(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({ view }, viewId)
}