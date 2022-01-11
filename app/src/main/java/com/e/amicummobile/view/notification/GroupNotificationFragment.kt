package com.e.amicummobile.view.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.amicummobile.view.notification.adapters.RvGroupNotificationAdapter
import com.e.amicummobile.databinding.GroupNotificationFragmentBinding
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupNotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupNotificationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: GroupNotificationFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationStore: StoreNotification
    private lateinit var notificationScopeInstance: Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GroupNotificationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationScopeInstance = KoinJavaComponent.getKoin().getOrCreateScope("notificationScopeId", named("NOTIFICATION_STORE"))
        notificationStore = notificationScopeInstance.get()

        val rvGroupNotification = binding.rvGroupNotification
        rvGroupNotification.layoutManager = LinearLayoutManager(requireContext())

        rvGroupNotification.adapter = notificationStore.getNotificationAll().value?.let { RvGroupNotificationAdapter(it) }

        notificationStore.getNotificationAll().observe(viewLifecycleOwner, {
            rvGroupNotification.adapter = RvGroupNotificationAdapter(it)
        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupNotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupNotificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}