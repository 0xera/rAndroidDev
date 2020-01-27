package ru.aydarov.randroid.presentation.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_single.*
import ru.aydarov.randroid.R
import ru.aydarov.randroid.theme_helper.ThemeHelper

/**
 * @author Aydarov Askhar 2020
 */
class SingleActivity : AppCompatActivity(), INavigator {
    private val mNavController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this)
        setContentView(R.layout.activity_single)
        NavigationUI.setupWithNavController(navView, mNavController)
    }

    override fun onSupportNavigateUp() = mNavController.navigateUp()


    companion object {
        fun newIntent(context: Context) = with(context) {
            Intent(this, SingleActivity::class.java)
        }

        public var count = 0;
    }

    override fun navigateFromSearchedToSelf(extras: Bundle?) {
        mNavController.navigate(R.id.action_searchedFragment_self, extras)
    }

    override fun navigateFromUserToSelf() {
        mNavController.navigate(R.id.action_navigation_user_self)
    }

    override fun navigateFromNewsToSearchedFragment(extras: Bundle) {
        mNavController.navigate(R.id.action_navigation_news_to_searchedFragment, extras)
    }
}

