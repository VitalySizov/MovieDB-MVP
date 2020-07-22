package ru.vitalysizov.moviedb.presentation.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import ru.vitalysizov.moviedb.R
import ru.vitalysizov.moviedb.presentation.base.view.BaseFragment
import ru.vitalysizov.moviedb.presentation.main.utils.setupWithNavController
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navigationAdapter = AHBottomNavigationAdapter(activity, R.menu.bottom_navigation_menu)

        val mainBottomNavigation = main_bottom_navigation
        mainBottomNavigation.accentColor =
            ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        navigationAdapter.setupWithBottomNavigation(mainBottomNavigation)

        val navGraphIds = listOf(
            R.navigation.nav_home_tab_graph,
            R.navigation.nav_search_tab_graph,
            R.navigation.nav_account_tab_graph
        )
        val bottomItemIds = listOf(
            R.id.home_tab,
            R.id.search_tab,
            R.id.account_tab
        )
        main_bottom_navigation.setupWithNavController(
            navGraphIds,
            bottomItemIds,
            childFragmentManager,
            R.id.main_fragment_container
        )
    }
}