package ru.aydarov.randroid.presentation.ui.user

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user_no_log_in.*
import ru.aydarov.randroid.BuildConfig
import ru.aydarov.randroid.R
import ru.aydarov.randroid.data.util.LiveConnectUtil
import ru.aydarov.randroid.data.util.TokensSharedHelper
import ru.aydarov.randroid.databinding.UserFragmentBinding
import ru.aydarov.randroid.presentation.common.App
import ru.aydarov.randroid.presentation.common.ImageViewActivity
import ru.aydarov.randroid.presentation.common.SingleActivity
import ru.aydarov.randroid.presentation.ui.bottom_sheet.AboutAppBottomSheetFragment
import ru.aydarov.randroid.presentation.ui.web.WebViewActivity
import ru.aydarov.randroid.theme_helper.ThemeChanger
import ru.aydarov.randroid.theme_helper.ThemeChanger.Companion.ACTION_THEME_CHANGE
import ru.aydarov.randroid.theme_helper.ThemeHelper
import javax.inject.Inject

/**
 * @author Aydarov Askhar 2020
 */
class UserFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserFragmentBinding: UserFragmentBinding
    private var mActionDown = false
    @Inject
    lateinit var mUserViewModelFactory: UserViewModel.UserViewModelFactory
    private val mThemeChangeReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                activity?.recreate()
            }
        }
    }
    private lateinit var mAlertDialogLogout: AlertDialog


    private val mAboutBottomSheetFragment by lazy {
        AboutAppBottomSheetFragment()
    }

//    private fun openImage(iv: ImageView) {
//        if (iv.drawable != null)
//            activity?.let {
//                //                val bitmap = getBitmapFromDrawable(iv.drawable)
////                val baos = ByteArrayOutputStream()
////                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
////                val b: ByteArray = baos.toByteArray()
//
//
//            }
//    }

//    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap? {
//        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.width, canvas.height)
//        drawable.draw(canvas)
//        return bitmap
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getAppComponent().inject(this)
        mUserViewModel = ViewModelProvider(this, mUserViewModelFactory).get(UserViewModel::class.java)
        registerLocalReceiver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (hasTokens()) {
            mUserFragmentBinding = UserFragmentBinding.inflate(inflater, container, false)
            mUserFragmentBinding.lifecycleOwner = this
            mUserFragmentBinding.vm = mUserViewModel
            return mUserFragmentBinding.root
        }
        return inflater.inflate(R.layout.fragment_user_no_log_in, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (hasTokens()) initViewLogin() else
            initViewNoLogin()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun hasTokens() =
            (activity as SingleActivity).mAccessToken != TokensSharedHelper.NONE && (activity as SingleActivity).mRefreshToken != TokensSharedHelper.NONE

    private fun initViewNoLogin() {
        btnSingIn.setOnClickListener {
            startActivityForResult(Intent(activity, WebViewActivity::class.java), WebViewActivity.TOKEN_REQUEST)
        }

    }

    private fun registerLocalReceiver() {
        activity?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(mThemeChangeReceiver, IntentFilter(ACTION_THEME_CHANGE))
        }
    }

    private fun initViewLogin() {
        LiveConnectUtil.getInstance().observe(this, Observer {
            if (it) {
                mUserViewModel.fetchMyData((activity as SingleActivity).mAccessToken)
            } else {
                mUserViewModel.dispose()
                Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
        })
        setListeners()
        tvAppVersion.text = BuildConfig.VERSION_NAME

    }

    private fun setListeners() {
        ivChangeTheme.setOnTouchListener { _, event ->
            mActionDown = if (event.action == MotionEvent.ACTION_DOWN) true
            else if (event.action == MotionEvent.ACTION_UP && mActionDown) {
                changeTheme(event.rawX.toInt(), event.rawY.toInt())
                false
            } else false
            true
        }
        tvAppVersion.setOnClickListener { checkBeforeShowBottomSheet() }
        tvLogout.setOnClickListener { createLogoutAlertDialog() }
        //  ivBackground.setOnLongClickListener { openGallery(Request.BACKGROUND) }
        ivBackground.setOnClickListener {
            mUserViewModel.user.value?.sub?.banner?.openImage(it)
        }
        ivProfile.setOnClickListener {
            mUserViewModel.user.value?.icon?.openImage(it)
        }

        // ivProfile.setOnLongClickListener { openGallery(Request.PROFILE) }
        //   tvAbout.setOnClickListener { changeAboutText() }
        mUserViewModel.result.observe(this, Observer {
            if (it == UserViewModel.Result.ERROR)
                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show()
        })
    }

    private infix fun String.openImage(view: View) {
        activity.let {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(it!!, view, getString(R.string.iv_transition))
            activity?.startActivityFromFragment(this@UserFragment, Intent(activity, ImageViewActivity::class.java).putExtra(ImageViewActivity.IMAGE_OPEN_KEY, this), REQUEST_TRANSITION, activityOptions.toBundle())
        }
    }

    private fun openGallery(requestCode: Request): Boolean {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode.code)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (resultCode == Activity.RESULT_OK && null != intent) {
            when (requestCode) {
                Request.BACKGROUND.code -> ivBackground.setImageURI(intent.data)
                Request.PROFILE.code -> ivProfile.setImageURI(intent.data)
                EditTextActivity.REQUEST_CODE_EDIT -> intent.getStringExtra(EditTextActivity.EDIT_KEY_EXTRA)?.let { changeAboutText(text = it) }
                WebViewActivity.TOKEN_REQUEST -> {
                    (activity as SingleActivity).navigateFromUserToSelf()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent)
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

    private fun createLogoutAlertDialog() {
        activity?.let {
            mAlertDialogLogout = MaterialAlertDialogBuilder(it)
                    .setTitle(R.string.log_out)
                    .setMessage(R.string.question_logout)
                    .setPositiveButton(R.string.yes_string) { _, _ ->
                        mUserViewModel.logOut()
                        (activity as SingleActivity).nullifyTokens()
                        (activity as SingleActivity).navigateFromUserToSelf()
                    }
                    .setNegativeButton(R.string.no_string, null)
                    .create()
            if (!mAlertDialogLogout.isShowing) mAlertDialogLogout.show()
        }

    }

    private fun changeAboutText() {
        startActivityForResult(EditTextActivity.newIntent(requireActivity(), tvAbout.text.toString()), EditTextActivity.REQUEST_CODE_EDIT)
    }

    private fun changeAboutText(text: String) {
        if (text.isNotEmpty() && text != getString(R.string.about_me))
            tvAbout.text = text

    }


    override fun onDestroy() {
        activity?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(mThemeChangeReceiver) }
        mUserViewModel.dispose()
        LiveConnectUtil.getInstance().removeObservers(this)
        super.onDestroy()
    }

    companion object {
        const val REQUEST_TRANSITION = 505
    }
}

enum class Request(val code: Int) {
    BACKGROUND(198),
    PROFILE(891)

}

