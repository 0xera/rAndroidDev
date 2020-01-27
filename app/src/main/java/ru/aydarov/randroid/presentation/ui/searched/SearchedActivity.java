package ru.aydarov.randroid.presentation.ui.searched;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.presentation.ui.search.SearchActivity;
import ru.aydarov.randroid.theme_helper.ThemeHelper;

public class SearchedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.setTheme(this);
        setContentView(R.layout.activity_searched);
        toFragment(savedInstanceState);
    }

    public void toFragment(Bundle bundle) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SearchedFragment.newInstance(bundle))
                .commit();
    }

    public static Intent newIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SearchedActivity.class);
        intent.putExtra(SearchActivity.SEARCH_KEY_EXTRA, bundle);
        return intent;
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
