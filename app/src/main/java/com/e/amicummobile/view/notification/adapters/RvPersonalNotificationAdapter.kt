package com.e.amicummobile.view.notification.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.amicummobile.R
import com.example.models.Notification
import com.example.models.NotificationList
import com.google.android.material.imageview.ShapeableImageView

class RvPersonalNotificationAdapter(private val notificationList: ArrayList<com.example.models.NotificationList<com.example.models.Notification>>) :
    RecyclerView.Adapter<RvPersonalNotificationAdapter.PersonalNotificationHolder>() {

    class PersonalNotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageNotificationIcon: ShapeableImageView? = null
        var notificationTitle: TextView? = null
        var rvNotificationItem: RecyclerView? = null
        var context: Context? = null

        init {
            context = itemView.context
            imageNotificationIcon = itemView.findViewById(R.id.imageNotificationIcon)
            notificationTitle = itemView.findViewById(R.id.notification_title)
            rvNotificationItem = itemView.findViewById(R.id.rvNotificationItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalNotificationHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.personal_notification_list_item_fragment, parent, false)
        return PersonalNotificationHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonalNotificationHolder, position: Int) {
        holder.notificationTitle?.text = notificationList[position].title

        holder.rvNotificationItem?.layoutManager = LinearLayoutManager(holder.context)

        holder.rvNotificationItem?.adapter = RvPersonalNotificationItemAdapter(notificationList[position].notifications, notificationList[position].id)
        when (notificationList[position].id) {
            "medicalExam" -> holder.imageNotificationIcon?.setImageResource(R.drawable.notification_medical)
            "siz" -> holder.imageNotificationIcon?.setImageResource(R.drawable.notification_siz)
            "ppkPab" -> holder.imageNotificationIcon?.setImageResource(R.drawable.notification_pab)
            "audit" -> holder.imageNotificationIcon?.setImageResource(R.drawable.notification_audit)
            "check_knowledge" -> holder.imageNotificationIcon?.setImageResource(R.drawable.notification_check_knowledge)
            "briefing" -> holder.imageNotificationIcon?.setImageResource(R.drawable.notification_briefing)
            "ppkInjunction" -> holder.imageNotificationIcon?.setImageResource(R.drawable.notification_injunction)
        }
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}