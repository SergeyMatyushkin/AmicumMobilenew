package com.e.amicummobile.view.menu

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.utils.CoinImageLoader
import com.e.amicummobile.databinding.HeaderNavigationMainDrawerBinding
import com.example.models.UserSession
import com.e.amicummobile.viewmodel.StoreAmicum
import org.koin.android.ext.android.inject

/**
 * Главное навигационное меню - слева
 */
class NavigationMainMenuFragment : Fragment() {

    private var _binding: HeaderNavigationMainDrawerBinding? = null
    private val binding get() = _binding!!

    private val coinImageLoader: com.example.utils.CoinImageLoader by inject()

    private lateinit var storeAmicum: StoreAmicum

    private var mCallback: IAppMainMenu? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HeaderNavigationMainDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeAmicum = ViewModelProvider(requireActivity())[StoreAmicum::class.java]

        binding.btnMainMenuOnMain.setOnClickListener {                                              // обработка нажания кнопки перейти на главный фрагмент
            mCallback!!.openFragment("MainFragment")
        }

        binding.btnMainMenuNotifications.setOnClickListener {                                       // обработка нажания кнопки открыть уведомления
            mCallback!!.openFragment("NotificationFragment")
        }

        storeAmicum.getUserSession().observe(viewLifecycleOwner, {
            renderData(it)
        })


    }

    @SuppressLint("SetTextI18n")
    fun renderData(userSession: com.example.models.UserSession) {
        binding.tvFIOWorker.text = userSession.userStaffNumber + " | " + userSession.userName + " \n " + userSession.position_title
        binding.tvCompanyWorker.text = userSession.userDepartmentPath
        coinImageLoader.loadImageFromServer(userSession.user_image_src, binding.imgPhotoWorker)
    }

    companion object {
        fun newInstance() = NavigationMainMenuFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCallback = context as IAppMainMenu
    }
}