package ru.aydarov.randroid.presentation.activty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

import kotlinx.android.synthetic.main.activity_single.*
import ru.aydarov.randroid.R
import ru.aydarov.randroid.domain.util.TokensSharedHelper
import ru.aydarov.randroid.presentation.common.INavigatorFragment
import ru.aydarov.randroid.theme_helper.ThemeHelper


/**
 * @author Aydarov Askhar 2020
 */
class SingleActivity : AppCompatActivity(), INavigatorFragment {

    val mAccessToken: String
        get() = TokensSharedHelper.getAccessToken(this)
    val mRefreshToken: String
        get() = TokensSharedHelper.getRefreshToken(this)

    private val mNavController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this)
        setContentView(R.layout.activity_single)
        NavigationUI.setupWithNavController(navView, mNavController)
    }


    fun nullifyTokens() {
        saveTokens(TokensSharedHelper.NONE, TokensSharedHelper.NONE)
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        TokensSharedHelper.saveAccessToken(this, accessToken)
        TokensSharedHelper.saveRefreshToken(this, refreshToken)
    }

    override fun onSupportNavigateUp() = mNavController.navigateUp()


    companion object {
        fun newIntent(context: Context) = with(context) {
            Intent(this, SingleActivity::class.java)
        }
    }

    override fun navigateFromSearchedToSelf(extras: Bundle?) {
        mNavController.navigate(R.id.action_searchedFragment_self, extras)
    }

    override fun navigateFromUserToSelf() {
        mNavController.navigate(R.id.action_navigation_user_self)
    }

    override fun navigateFromSearchedToCommentsFragment(extras: Bundle?) {
        mNavController.navigate(R.id.action_searchedFragment_to_commentsFragment, extras)
    }

    override fun navigateFromNewsToCommentsFragment(extras: Bundle?) {
        mNavController.navigate(R.id.action_navigation_news_to_commentsFragment, extras)
    }

    override fun navigateFromNewsToSearchedFragment(extras: Bundle) {
        mNavController.navigate(R.id.action_navigation_news_to_searchedFragment, extras)
    }


}

