package ru.aydarov.randroid.presentation.ui.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_single.*
import ru.aydarov.randroid.R
import ru.aydarov.randroid.domain.util.TokensSharedHelper
import ru.aydarov.randroid.presentation.common.INavigatorFragment
import ru.aydarov.randroid.presentation.common.IScrollUp
import ru.aydarov.randroid.presentation.ui.post.PostListFragment
import ru.aydarov.randroid.presentation.ui.searched.SearchedActivity
import ru.aydarov.randroid.presentation.ui.searched.SearchedFragment
import ru.aydarov.randroid.presentation.ui.user.UserFragment
import ru.aydarov.randroid.theme_helper.ThemeHelper

/**
 * @author Aydarov Askhar 2020
 */
class SingleActivity : AppCompatActivity(), INavigatorFragment {

    val mAccessToken: String
        get() = TokensSharedHelper.getAccessToken(this)
    val mRefreshToken: String
        get() = TokensSharedHelper.getRefreshToken(this)


    private lateinit var postListFragment: PostListFragment
    private lateinit var userFragment: UserFragment

    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this)
        setContentView(R.layout.activity_single)
        if (savedInstanceState == null) {
            postListFragment = PostListFragment.newInstance()
            userFragment = UserFragment.newInstance()
            postShow()
        } else {
            postListFragment = supportFragmentManager.findFragmentByTag(PostListFragment::class.java.simpleName) as PostListFragment
            userFragment = supportFragmentManager.findFragmentByTag(UserFragment::class.java.simpleName) as UserFragment
        }
        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_news -> {
                    if (it.isChecked && activeFragment !is SearchedFragment) {
                        val findFragmentByTag = supportFragmentManager.findFragmentByTag(PostListFragment::class.java.simpleName)
                        if (findFragmentByTag != null && findFragmentByTag.isVisible && findFragmentByTag is IScrollUp)
                            ((findFragmentByTag as IScrollUp).scrollUp())
                        false
                    } else {
                        navigateToNews()
                        true
                    }
                }
                R.id.navigation_user -> {
                    if (it.isChecked)
                        false
                    else {
                        navigateToUser()
                        true
                    }
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        activeFragment = if (navView.selectedItemId == R.id.navigation_news)
            postListFragment
        else
            userFragment
        super.onResume()
    }

    fun nullifyTokens() {
        TokensSharedHelper.saveAccessToken(this, TokensSharedHelper.NONE)
        TokensSharedHelper.saveRefreshToken(this, TokensSharedHelper.NONE)
    }

    companion object {

        fun newIntent(context: Context) = with(context) {
            Intent(this, SingleActivity::class.java)
        }
    }

    override fun navigateToNews() {
        val transaction = supportFragmentManager.beginTransaction()
                .show(postListFragment)
        activeFragment?.let {
            transaction.hide(it)
        }
        transaction.commit()
        activeFragment = postListFragment
    }


    override fun navigateFromUserToSelf() {
        val newInstance = UserFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .hide(userFragment)
                .add(R.id.nav_host_fragment, newInstance, UserFragment::class.java.simpleName)
                .commit()
        userFragment = newInstance
        activeFragment = newInstance
    }

    override fun navigateToUser() {
        val transaction = supportFragmentManager.beginTransaction()
                .show(userFragment)
        activeFragment?.let {
            transaction.hide(it)
        }
        transaction.commit()

        activeFragment = userFragment
    }

    private fun userShow() {
        val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, postListFragment, PostListFragment::class.java.simpleName)
                .add(R.id.nav_host_fragment, userFragment, UserFragment::class.java.simpleName)
                .hide(postListFragment)
        transaction.commit()
    }

    private fun postShow() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, postListFragment, PostListFragment::class.java.simpleName)
                .add(R.id.nav_host_fragment, userFragment, UserFragment::class.java.simpleName)
                .hide(userFragment)
                .commit()
        activeFragment = postListFragment
    }


    override fun navigateFromNewsToSearchedFragment(extras: Bundle) {
        val intent = Intent(this, SearchedActivity::class.java)
        intent.putExtra(SearchedActivity.SEARCH_KEY_EXTRA, extras)
        startActivity(intent)
    }


}

