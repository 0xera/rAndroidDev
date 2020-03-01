package ru.aydarov.randroid.presentation.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.theme_helper.ThemeHelper;

/**
 * @author Aydarov Askhar 2020
 */
public class SearchActivity extends AppCompatActivity {

    public static final int SEARCH_REQUEST_CODE = 98;
    public static final String SEARCH_KEY_EXTRA = "keuss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.setTheme(this);
        setContentView(R.layout.activity_search);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, SearchFragment.newInstance())
                .commit();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return super.onNavigateUp();
    }
}
