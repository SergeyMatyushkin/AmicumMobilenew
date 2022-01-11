package com.e.amicummobile.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.e.amicummobile.R
import com.example.config.Const
import com.e.amicummobile.databinding.MainMenuFragmentBinding
import com.e.amicummobile.viewmodel.StoreAmicum
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Главное меню системы АМИКУМ
 */
class MainMenuFragment : Fragment() {

    private var _binding: MainMenuFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var storeAmicum: StoreAmicum

    private val job: Job = Job()
    private val queryStateFlow = MutableStateFlow("")
    private lateinit var searchView: SearchView
    private lateinit var searchResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainMenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: StoreAmicum by viewModel()
        storeAmicum = viewModel
        initFragment()                                                                              // инициализируем фрагмент

        searchView = binding.searchView
        searchResult = binding.txtSearchResult
        initSearch(searchView, searchResult)

    }

    /**
     * Метод инициализации фрагмента
     */
    private fun initFragment() {
        childFragmentManager.beginTransaction()                                                    // загружаем AppBarTop                                                                 // поверх открываем всплывающее окно, которое закроется через 5 секунд
            .add(
                R.id.containerAppBar, AppBarTopMainFragment.newInstance(
                    "Amicum_mobile",
                    com.example.config.Const.APP_BAR_MAIN,
                    "",
                    ""
                )
            )
            .commitNow()
    }

    /**
     * Живой поиск
     */
    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun initSearch(searchView: SearchView, searchResult: TextView) {
        storeAmicum.initDepartments()                                                               // загрузка данных с репозитория

        // инициализация корутины поиска
        CoroutineScope(Dispatchers.Main + job).launch {
            queryStateFlow.debounce(500)                                                 // задержка перед запуском поиска
                .filter { query ->
                    if (query.isEmpty()) {
                        searchResult.text = ""
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()                                                             // ожидание изменения значения
                .flatMapLatest { query ->                                                           // если был поток до этого то прибивает его и делает новый
                    storeAmicum.searchInDepartmentList(query)                                       // запрос данных из вью модели
                        .catch {                                                                    // обработка ошибок потока
                            var depList: ArrayList<com.example.models.Company> = ArrayList()
                            depList.add(com.example.models.Company(0, "Ошибка", 0, ArrayList(), ArrayList()))
                            emit(depList)
                        }
                }
                .collect { result ->                                                                // принимает результат и оправляет его на построение

                    searchResult.text = result[0].title            // TODO реализовать заполнение списка. Оставлена как заглушка, т.к будет выноситься в верхнее меню приложения
                }
        }

        // обработчик запуска поиска
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // нажали кнопку поиска
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { queryStateFlow.value = it }
                return true
            }

            // текст в строке поиска изменился
            override fun onQueryTextChange(newText: String): Boolean {
                queryStateFlow.value = newText
                return true
            }
        })
    }


    override fun onDestroyView() {
        job.cancel()
        _binding = null
        super.onDestroyView()
    }

//    companion object {
//        fun newInstance() = MainMenuFragment()
//    }
}