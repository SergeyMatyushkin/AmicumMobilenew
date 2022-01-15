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

class RvGroupNotificationItemAdapter(private val notification: ArrayList<Notification>, private val notification_id: String) :
    RecyclerView.Adapter<RvGroupNotificationItemAdapter.NotificationHolder>
        () {

    class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFirstField: TextView? = null
        var tvSecondField: TextView? = null
        var viewNotificationStatus: View? = null
        var viewNotificationLine: View? = null
        var context: Context? = null

        init {
            context = itemView.context
            tvFirstField = itemView.findViewById(R.id.tvFirstField)
            tvSecondField = itemView.findViewById(R.id.tvSecondField)
            viewNotificationStatus = itemView.findViewById(R.id.viewNotificationStatus)
            viewNotificationLine = itemView.findViewById(R.id.viewNotificationLine)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.group_notification_item_fragment, parent, false)
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
                holder.tvFirstField?.text = notification[position].worker_full_name + " | " + notification[position].worker_staff_number
                holder.tvSecondField?.text = convertDateToFront(notification[position].checkup_date_start) + " - " + convertDateToFront(notification[position].checkup_date_start)
            }
            "siz" -> {
                holder.tvFirstField?.text = notification[position].siz_title
                holder.tvSecondField?.text = notification[position].worker_full_name + " | " + notification[position].worker_staff_number
                holder.tvSecondField?.textSize = 14f
            }
            "ppkPab" -> {
                holder.tvFirstField?.text = notification[position].worker_full_name + " | " + notification[position].worker_staff_number
                holder.tvSecondField?.text = convertDateToFront(notification[position].ppk_date_time)
            }
            "audit" -> {
                holder.tvFirstField?.text = notification[position].audit_place_title
                holder.tvSecondField?.text = convertDateToFront(notification[position].audit_date_time)
            }
            "check_knowledge" -> {
                holder.tvFirstField?.text = notification[position].worker_full_name + " | " + notification[position].worker_staff_number
                holder.tvSecondField?.text = convertDateToFront(notification[position].check_knowledge_date_time)
            }
            "briefing" -> {
                holder.tvFirstField?.text = notification[position].worker_full_name + " | " + notification[position].worker_staff_number
                holder.tvSecondField?.text = convertDateToFront(notification[position].briefing_date_time)
            }
            "ppkInjunction" -> {
                val ppkPab = holder.context?.getText(R.string.ppkPab).toString()
                var ppkIdTemp = "№ " + notification[position].injunction_id + "(" + notification[position].ppk_id + ")"
                if (notification[position].ppk_id > 0) {
                    ppkIdTemp += ppkPab + " " + notification[position].ppk_id + ")"
                }
                holder.tvFirstField?.text = ppkIdTemp
                holder.tvSecondField?.text = convertDateToFront(notification[position].ppk_date_time)
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