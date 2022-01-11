package com.example.utils

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.*

class CoinImageLoader(val context: Context) {
    val imageLoader = ImageLoader(context)

    val coroutineScope = CoroutineScope(                                                            // корутина для распараллеливания процессов загрузки изображений
        Dispatchers.Main +                                                                   // выполняться в главном потоке Диспетчера
                SupervisorJob()                                                                     // дочерние джобы независымы друг от дргуа
    )

    fun loadImageFromServer(urlRequest: String, imageView: ImageView) {
        val request = ImageRequest.Builder(context)
            .data("https:$urlRequest")
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                },
                onError = {
                    imageView.setImageResource(R.drawable.icon_exit)
                }
            )
            .transformations(
                CircleCropTransformation(),
            )
            .build()
        coroutineScope.launch {
            imageLoader.execute(request).drawable
        }
    }
}