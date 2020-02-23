package ru.aydarov.randroid.presentation.ui.searched;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.presentation.common.INavigatorFragmentSearch;
import ru.aydarov.randroid.theme_helper.ThemeHelper;

/**
 * @author Aydarov Askhar 2020
 */
public class SearchedActivity extends AppCompatActivity implements INavigatorFragmentSearch {

    public static final int SEARCH_REQUEST_CODE = 98;
    public static final String SEARCH_KEY_EXTRA = "keuss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.setTheme(this);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null && getIntent().getExtras().getBundle(SEARCH_KEY_EXTRA) != null)
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, SearchedFragment.newInstance(getIntent().getExtras().getBundle(SEARCH_KEY_EXTRA)))
                        .commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchedActivity.class);
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

    @Override
    public void navigateFromSearchedToSelf(Bundle extras) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, SearchedFragment.newInstance(getIntent().getExtras()), SearchedFragment.class.getSimpleName())
                .addToBackStack(SearchedFragment.class.getSimpleName())
                .commit();
    }
}
