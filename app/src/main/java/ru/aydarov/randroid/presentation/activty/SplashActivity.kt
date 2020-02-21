package ru.aydarov.randroid.presentation.activty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.aydarov.randroid.R

/**
 * @author Aydarov Askhar 2020
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(SingleActivity.newIntent(this))
        finish()

    }


}