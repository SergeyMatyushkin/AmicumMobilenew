package com.e.amicummobile.view.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.amicummobile.R
import com.e.amicummobile.view.notification.adapters.RvPersonalNotificationAdapter
import com.e.amicummobile.view.viewById
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent

class PersonalNotificationFragment : Fragment() {
    private lateinit var notificationStore: StoreNotification
    private lateinit var notificationScopeInstance: Scope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.personal_notification_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationScopeInstance = KoinJavaComponent.getKoin().getOrCreateScope("notificationScopeId", named("NOTIFICATION_STORE"))
        notificationStore = notificationScopeInstance.get()

        val rvPersonalNotification by viewById<RecyclerView>(R.id.rvPersonalNotification)           // делегат - биндинг выпелен полностью в данном фрагменте
        rvPersonalNotification.layoutManager = LinearLayoutManager(requireContext())

        rvPersonalNotification.adapter = notificationStore.getNotificationPersonal().value?.let { RvPersonalNotificationAdapter(it) }

        notificationStore.getNotificationAll().observe(viewLifecycleOwner, {
            rvPersonalNotification.adapter = RvPersonalNotificationAdapter(it)
        })

    }
}