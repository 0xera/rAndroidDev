package ru.aydarov.randroid.presentation.ui.user

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user_no_log_in.*
import ru.aydarov.randroid.BuildConfig
import ru.aydarov.randroid.R
import ru.aydarov.randroid.presentation.common.SingleActivity
import ru.aydarov.randroid.presentation.ui.bottom_sheet.AboutAppBottomSheetFragment
import ru.aydarov.randroid.theme_helper.ThemeChanger
import ru.aydarov.randroid.theme_helper.ThemeChanger.Companion.ACTION_THEME_CHANGE
import ru.aydarov.randroid.theme_helper.ThemeHelper

/**
 * @author Aydarov Askhar 2020
 */
class UserFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private var mActionDown = false
    private val mThemeChangeReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                activity?.recreate()
            }
        }
    }
    private val mAboutBottomSheetFragment by lazy {
        AboutAppBottomSheetFragment()
    }
    private val onClickListenerThemeChange: View.OnClickListener by lazy {
        View.OnClickListener { v -> changeTheme((v.left + v.right) / 2, (v.top + v.bottom) / 2) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLocalReceiver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        if (SingleActivity.count == 1)
            return inflater.inflate(R.layout.fragment_user, container, false)
        return inflater.inflate(R.layout.fragment_user_no_log_in, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (SingleActivity.count == 1)
            initViewLogin()
        else
            initViewNoLogin()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewNoLogin() {
        btnSingIn.setOnClickListener {
            // checkBeforeShowBottomSheet()
            SingleActivity.count = 1
            (activity as SingleActivity).navigateFromUserToSelf()
        }
    }

    private fun registerLocalReceiver() {
        activity?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(mThemeChangeReceiver, IntentFilter(ACTION_THEME_CHANGE))
        }
    }

    private fun initViewLogin() {
        ivChangeTheme.setOnTouchListener { _, event ->
            mActionDown = if (event.action == MotionEvent.ACTION_DOWN) true
            else if (event.action == MotionEvent.ACTION_UP && mActionDown) {
                changeTheme(event.rawX.toInt(), event.rawY.toInt())
                false
            } else false
            true
        }
        tvAppVersion.text = BuildConfig.VERSION_NAME
        tvAppVersion.setOnClickListener { checkBeforeShowBottomSheet() }

    }

    private fun checkBeforeShowBottomSheet() {
        val fragmentByTag = childFragmentManager.findFragmentByTag(AboutAppBottomSheetFragment.getFragmentTag())
        if (fragmentByTag != null && !fragmentByTag.isAdded) {
            showSortBottomSheet()
        } else if (fragmentByTag == null) {
            showSortBottomSheet()
        }
    }

    private fun showSortBottomSheet() {
        mAboutBottomSheetFragment.show(childFragmentManager, AboutAppBottomSheetFragment.getFragmentTag())

    }

    private fun changeTheme(xButton: Int, yButton: Int) {
        activity?.let {
            if (ThemeHelper.getTheme(it)) {
                ThemeHelper.updateTheme(it, false)
                ThemeChanger.changeTheme(it, xButton, yButton)
            } else {
                ThemeHelper.updateTheme(it, true)
                ThemeChanger.changeTheme(it, xButton, yButton)

            }
        }
    }


    override fun onDestroy() {
        activity?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(mThemeChangeReceiver) }
        super.onDestroy()
    }
}