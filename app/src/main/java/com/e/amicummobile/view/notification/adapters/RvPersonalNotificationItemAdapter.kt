package com.e.amicummobile.view.notification.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.amicummobile.R
import com.example.utils.Assistant.convertDateToFront
import com.example.models.Notification

class RvPersonalNotificationItemAdapter(private val notification: ArrayList<Notification>, private val notification_id: String) :
    RecyclerView.Adapter<RvPersonalNotificationItemAdapter.NotificationHolder>
        () {

    class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFirstField: TextView? = null
        var viewNotificationStatus: View? = null
        var viewNotificationLine: View? = null
        var context: Context? = null

        init {
            context = itemView.context
            tvFirstField = itemView.findViewById(R.id.tvFirstField)
            viewNotificationStatus = itemView.findViewById(R.id.viewNotificationStatus)
            viewNotificationLine = itemView.findViewById(R.id.viewNotificationLine)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.personal_notification_item_fragment, parent, false)
        return NotificationHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        if (notification[position].flag) {
            holder.viewNotificationStatus?.setBackgroundResource(R.drawable.status_notification_color_true)
        } else if (!notification[position].flag) {
            holder.viewNotificationStatus?.setBackgroundResource(R.drawable.status_notification_color_false)
        } else {
            holder.viewNotificationStatus?.setBackgroundResource(R.drawable.status_notification_color_null)
        }

        when (notification_id) {
            "medicalExam" -> {
                val medText = holder.context?.getText(R.string.medicalNotification).toString()
                val poText = holder.context?.getText(R.string.po).toString()
                holder.tvFirstField?.text =
                    medText + " " + convertDateToFront(notification[position].checkup_date_start) + " " + poText + " " + convertDateToFront(notification[position].checkup_date_start)
            }
            "siz" -> {
                holder.tvFirstField?.text = notification[position].siz_title
            }
            "ppkPab" -> {
                val pabText = holder.context?.getText(R.string.pabNotification).toString()
                val otText = holder.context?.getText(R.string.ot).toString()
                holder.tvFirstField?.text = pabText + " " + notification[position].ppk_id + " " + otText + " " + convertDateToFront(notification[position].ppk_date_time)
            }
            "check_knowledge" -> {
                val checkKnowledgeText = holder.context?.getText(R.string.checkKnowledgeNotification).toString()
                holder.tvFirstField?.text = checkKnowledgeText + " " + convertDateToFront(notification[position].check_knowledge_date_time)
            }
            "briefing" -> {
                val briefingText = holder.context?.getText(R.string.briefingNotification).toString()
                holder.tvFirstField?.text = briefingText + " " + convertDateToFront(notification[position].briefing_date_time)
            }
        }

        if (position == notification.size - 1) {
            holder.viewNotificationLine?.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return notification.size
    }
}