package com.kr.jmaster;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import static com.kr.jmaster.R.id.webview;


/**
 * Created by Administrator on 2016-03-21.
 */
public class WebActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;


    //웹뷰 관련
    private static final String TAG = "WebActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int FILECHOOSER_RESULTCODE = 2;
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    public static final int AREA_RESULTCODE = 3;
    private WebView mWebView;
    private ProgressBar pb;
    private MyWebChromeClient mChromeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initViews();
        hideWebView();
        hideProgress();
        configureOfflineWebView(savedInstanceState);
    }

    private void initViews() {
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        mWebView = (WebView) findViewById(webview);
    }

    private void configureOfflineWebView(Bundle savedInstanceState) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);

// Set cache size to 8 mb by default. should be more than enough
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);

        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        String userAgent = mWebView.getSettings().getUserAgentString();
        mChromeClient = new MyWebChromeClient(this);
        mWebView.setWebChromeClient(mChromeClient);
        mWebView.setWebViewClient(new OfflineWebViewClient(this));

        if (savedInstanceState == null) {
                loadURL("http://wat.kr/bbs/board.php?bo_table=aes03&wr_id=11&sca=%EA%B7%B8%EB%A6%AC%EC%8A%A4%EB%8F%84%EA%B5%B0%EC%82%AC%ED%9B%88%EB%A0%A8%EA%B3%BC%EC%A0%95");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadURL(String url) {
        mWebView.loadUrl(url);
    }


    public void showProgress(){
        pb.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        pb.setVisibility(View.GONE);
    }

    public void showWebView(){
        mWebView.setVisibility(View.VISIBLE);
    }

    public void hideWebView(){
        mWebView.setVisibility(View.GONE);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage("어플을 종료 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
