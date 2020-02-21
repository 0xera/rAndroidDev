package ru.aydarov.randroid.presentation.ui.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import ru.aydarov.randroid.R;
import ru.aydarov.randroid.databinding.WebViewActivityBinding;
import ru.aydarov.randroid.presentation.common.App;
import ru.aydarov.randroid.theme_helper.ThemeHelper;

public class WebViewActivity extends AppCompatActivity {

    public static final int TOKEN_REQUEST = 12;
    public static final int MINUTE_IN_SECOND = 60;
    @Inject
    WebViewViewModel.WebViewViewModelFactory mViewModelFactory;
    private WebViewActivityBinding mWebViewActivityBinding;
    private WebViewViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(WebViewViewModel.class);
        ThemeHelper.setTheme(this);
        mWebViewActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        mWebViewActivityBinding.setLifecycleOwner(this);
        initView();
        setLiveDataObserver();

    }

    private void setLiveDataObserver() {
        mViewModel.getState().observe(this, state -> {
            switch (state) {
                case SUCCESS:
                    exitSuccess();
                    break;
                case ERROR:
                    exitError();
                    break;
                case DENIED:
                    exitDenied();
                    break;
            }
        });
    }


    private void initView() {
        initToolbar();
        initWebView();
    }

    private void initWebView() {
        WebView webView = mWebViewActivityBinding.webviewLogin;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(getWebViewClient());
        webView.loadUrl(mViewModel.getUrl());
    }

    private void initToolbar() {
        Toolbar toolbar = mWebViewActivityBinding.toolbarLogin;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void exitSuccess() {
        Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }

    private void exitError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void exitDenied() {
        Toast.makeText(this, R.string.denied, Toast.LENGTH_SHORT).show();
        finish();

    }


    @NotNull
    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!mViewModel.checkUrlAndGetTokens(url))
                    view.loadUrl(url);
                return true;
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!mViewModel.checkUrlAndGetTokens(request.getUrl().toString()))
                    view.loadUrl(request.getUrl().toString());
                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        };
    }

    @Override
    protected void onDestroy() {
        mViewModel.dispose();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mViewModel.dispose();
            finish();
            return true;
        }
        return false;
    }

}