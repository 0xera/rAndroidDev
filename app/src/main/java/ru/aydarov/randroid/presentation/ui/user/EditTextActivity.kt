package ru.aydarov.randroid.presentation.ui.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activty_edit_text.*
import ru.aydarov.randroid.R
import ru.aydarov.randroid.theme_helper.ThemeHelper


/**
 * @author Aydarov Askhar 2020
 */
class EditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this)
        setContentView(R.layout.activty_edit_text)
        initView()
    }


    private fun initView() {
        etInput.text = if (intent.extras?.getString(EDIT_KEY_EXTRA) != getString(R.string.about_me))
            intent.extras?.getString(EDIT_KEY_EXTRA)?.toEditable()
        else
            "".toEditable()
        etInput.requestFocus()
        setListeners()
    }

    private fun setListeners() {
        btnApply.setOnClickListener {
            hideKeyboard(it)
            setResult(Activity.RESULT_OK, Intent().putExtra(EDIT_KEY_EXTRA, etInput.text.toString()))
            finish()
        }
        btnCancel.setOnClickListener {
            hideKeyboard(it)
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun hideKeyboard(view: View) {
        view.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }

//    private fun showKeyboard(view: View) {
//        view.apply {
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            Objects.requireNonNull<InputMethodManager>(imm).showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
//        }
//    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    companion object {
        const val EDIT_KEY_EXTRA: String = "edi_ke"
        const val REQUEST_CODE_EDIT: Int = 91

        fun newIntent(context: Context, text: String): Intent {
            return Intent(context, EditTextActivity::class.java).putExtra(EDIT_KEY_EXTRA, text)
        }

    }
}
