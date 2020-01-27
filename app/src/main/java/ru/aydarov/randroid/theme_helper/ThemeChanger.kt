package ru.aydarov.randroid.theme_helper

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_theme_change.*
import ru.aydarov.randroid.R
import java.io.ByteArrayOutputStream
import kotlin.math.hypot

/**
 * @author Aydarov Askhar 2020
 */
class ThemeChanger : AppCompatActivity() {
    private var mPositionX: Int = 0
    private var mPositionY: Int = 0

    private val listenerAnimator: AnimatorListener by lazy {
        object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                LocalBroadcastManager.getInstance(this@ThemeChanger).sendBroadcast(Intent(ACTION_THEME_CHANGE))
            }

            override fun onAnimationEnd(animation: Animator) {
                ivScreenshot.visibility = View.INVISIBLE
                this@ThemeChanger.finish()
                overridePendingTransition(0, 0)
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_theme_change)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        intentData()
        ivScreenshot.post { this.revealEffectImage() }
        super.onCreate(savedInstanceState)
    }

    private fun intentData() = with(intent) {
        val bitmapData = getByteArrayExtra(IMG_EXTRA)
        bitmapData?.let {
            ivScreenshot.setImageBitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
            mPositionX = getIntExtra(X_POS, -1)
            mPositionY = getIntExtra(Y_POS, -1)
        }


    }


    private fun revealEffectImage() {
        calculateValues()
        startAnimation()
    }

    private fun startAnimation() {
        ViewAnimationUtils.createCircularReveal(ivScreenshot, mPositionX, mPositionY, calculateValues().toFloat(), 0f)
                .apply {
                    duration = DURATION_ANIM.toLong()
                    interpolator = DecelerateInterpolator()
                    addListener(listenerAnimator)
                    start()
                }
    }

    private fun calculateValues(): Int {
//        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
//        if (resId > 0)
//            mPositionY += resources.getDimensionPixelSize(resId)
        return hypot(ivScreenshot.width.toDouble(), ivScreenshot.height.toDouble()).toInt()
    }

    companion object {

        const val IMG_EXTRA = "img"
        const val X_POS = "X_POS"
        const val Y_POS = "Y_POS"
        const val DURATION_ANIM = 400
        const val ACTION_THEME_CHANGE = "ru.aydarov.randroid+theme-change"

        fun changeTheme(context: Context, xButton: Int, yButton: Int) = with(context) {
            val bs = ByteArrayOutputStream()
            Screenshot.takeScreenshot((this as AppCompatActivity).window.decorView).compress(Bitmap.CompressFormat.JPEG, 100, bs)
            val intent = Intent(this, ThemeChanger::class.java).apply {
                putExtra(IMG_EXTRA, bs.toByteArray())
                putExtra(X_POS, xButton)
                putExtra(Y_POS, yButton)
            }
            startActivity(intent)
        }
    }

}
