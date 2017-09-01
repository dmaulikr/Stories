package com.mkrworld.stories.ui.adapter.viewholder;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class StoryWebVH extends BaseViewHolder<StoryData> {

    public static final int START_LOADING_URL = 1;
    public static final int STOP_LOADING_URL = 2;
    public static final int CLICKED_URL = 3;
    private static final String TAG = BuildConfig.BASE_TAG + ".StoryWebVH";
    private WebView mWebView;

    public StoryWebVH(View itemView) {
        super(itemView);
        mWebView = (WebView) itemView.findViewById(R.id.adapter_web_webView);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setHorizontalScrollBarEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Tracer.debug(TAG, "shouldOverrideUrlLoading: " + request.getUrl().toString());
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Tracer.debug(TAG, "shouldOverrideUrlLoading: " + url);
                mParent.setTag(new StoryClickData(CLICKED_URL, url));
//                onClick(mParent);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                webView.invalidate();
                mParent.setTag(new StoryClickData(STOP_LOADING_URL, url));
//                onClick(mParent);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mParent.setTag(new StoryClickData(START_LOADING_URL, url));
//                onClick(mParent);
            }
        });
    }

    @Override
    protected void bindData(StoryData storyData) {
        Tracer.debug(TAG, "bindData: " + storyData.getDescription());
        mWebView.loadUrl(storyData.getDescription());
    }

    /**
     * Callback event to check the click event
     */
    public class StoryClickData {
        private String mUrl;
        private int mOperation;

        public StoryClickData(int operation, String url) {
            mUrl = url;
            mOperation = operation;
        }

        /**
         * Method to get the Operation
         *
         * @return
         */
        public int getOperation() {
            Tracer.debug(TAG, "getOperation: " + mOperation);
            return mOperation;
        }

        /**
         * Method to get the URL
         *
         * @return
         */
        public String getUrl() {
            Tracer.debug(TAG, "getUrl: " + mUrl);
            return mUrl;
        }
    }
}
