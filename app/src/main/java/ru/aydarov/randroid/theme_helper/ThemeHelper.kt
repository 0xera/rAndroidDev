package ru.aydarov.randroid.theme_helper

import android.content.Context
import ru.aydarov.randroid.R

/**
 * @author Aydarov Askhar 2020
 */
object ThemeHelper {
    private const val THEME_KEY = "THEME_KEY101"
    private const val PREF_NAME = "pref_theme"

    @JvmStatic
    fun setTheme(context: Context) = with(context) {
        if (getTheme(this))
            setTheme(R.style.ThemeLight)
        else
            setTheme(R.style.ThemeDark)
    }

    //    fun setThemeSplash(context: Context) = with(context) {
//        if (getTheme(this))
//            setTheme(R.style.SplashThemeLight)
//        else
//            setTheme(R.style.SplashThemeDark)
//    }
    @JvmStatic
    fun updateTheme(context: Context, light: Boolean) = with(context) {
        getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(THEME_KEY, light).apply()
    }

    @JvmStatic
    fun getTheme(context: Context): Boolean = with(context) {
        return getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(THEME_KEY, true)

    }

}