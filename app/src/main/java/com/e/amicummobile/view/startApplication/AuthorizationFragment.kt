package com.e.amicummobile.view.startApplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.e.amicummobile.R
import com.example.config.Bootstrap
import com.example.config.Const
import com.e.amicummobile.databinding.AuthorizationFragmentBinding
import com.e.amicummobile.view.menu.IAppMain
import com.e.amicummobile.view.BaseFragment

/**
 * Страница авторизации пользователя в системе
 */
class AuthorizationFragment : BaseFragment<com.example.models.UserSession>() {

    private var _binding: AuthorizationFragmentBinding? = null
    private val binding get() = _binding!!


    private var mCallback: IAppMain? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeAmicum.getUserSession().observe(viewLifecycleOwner, {
            renderData(it)
        })

        binding.btnCloseApplication.setOnClickListener {                                            // обработка нажания кнопки закрыть
            closeApp()
        }

        binding.txtLogin.addTextChangedListener {                                                   // сброс вывода ошибки при начале ввода текста в поле логина
            binding.layoutLogin.error = null
        }

        binding.txtPwd.addTextChangedListener {                                                     // сброс вывода ошибки при начале ввода текста в поле пароля
            binding.layoutPwd.error = null
        }

        binding.btnLogin.setOnClickListener {                                                       // обработчик кнопки авторизации
            var statusCheckField = true                                                             // статус проверки полей авторизации

            // делаем проверку на пустое поле пароля, если оно пустое, то красим выводим ошибку
            if (binding.txtPwd.text?.isEmpty() == true) {
                statusCheckField = false
                binding.layoutPwd.error = "Пароль не может быть пустым!"
            } else {
                binding.layoutPwd.error = null
            }

            // делаем проверку на пустое поле логина, если оно пустое, то красим выводим ошибку
            if (binding.txtLogin.text?.isEmpty() == true) {
                statusCheckField = false
                binding.layoutLogin.error = "Логин не может быть пустым!"
            } else {
                binding.layoutLogin.error = null
            }

            // выполняем авторизацию
            if (statusCheckField || com.example.config.Bootstrap.TYPE_BUILD == com.example.config.Const.VERSION_DEBUG) {
                storeAmicum.initLogin(
                    binding.txtLogin.text.toString(),
                    binding.txtPwd.text.toString(),
                    binding.checkBox.isChecked
                )
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = AuthorizationFragment()
    }

    /**
     * Метод закрытия приложения
     */
    private fun closeApp() {
        activity?.finishAffinity()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCallback = context as IAppMain
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }


    private fun renderData(userSession: com.example.models.UserSession?) {
        if (userSession == null) {
            showErrorScreen(getString(R.string.empty_server_response_on_success))
            binding.layoutLogin.error = " "
            binding.layoutPwd.error = "Введены неверные логин / пароль"
        } else {
            showViewSuccess()
            binding.layoutLogin.error = null
            binding.layoutPwd.error = null
            parentFragmentManager.beginTransaction().remove(this).commitNow()
            mCallback!!.initApp("Init")                                                   // Инициализируем приложение
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()

    }

    private fun showViewSuccess() {

    }

    private fun showViewLoading() {

    }

    private fun showViewError() {

    }

}