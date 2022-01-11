package com.e.amicummobile.view.notification.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.e.amicummobile.view.notification.GroupNotificationFragment
import com.e.amicummobile.view.notification.PersonalNotificationFragment
import java.lang.IllegalStateException


class VpNotificationAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> GroupNotificationFragment()
            1 -> PersonalNotificationFragment()
            else -> GroupNotificationFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence =
        when (position) {
            0 -> "Групповые"
            1 -> "Личные"
            else -> throw IllegalStateException("Индекс списка больше размера списка")
        }
}