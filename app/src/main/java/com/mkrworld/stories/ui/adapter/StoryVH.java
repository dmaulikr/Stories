package com.mkrworld.stories.ui.adapter;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.customs.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.utils.Tracer;
import com.mkrworld.stories.utils.Utils;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class StoryVH extends BaseViewHolder<StoryData> {
    private static final String TAG = BuildConfig.BASE_TAG + ".StoryVH";
    private WebView mWebView;

    public StoryVH(View itemView) {
        super(itemView);
        mWebView = (WebView) itemView.findViewById(R.id.adapter_story_webView);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setUseWideViewPort(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void bindData(StoryData storyData) {
        Tracer.debug(TAG, "bindData: " + storyData.getId() + "   " + getParent().getResources().getString(R.string.adapter_story_text_color));
        mWebView.loadDataWithBaseURL(null, getHtmlContent(storyData), "text/html", "utf-8", null);
    }

    /**
     * Method to get the Content in the HTML form
     *
     * @param storyData
     * @return
     */
    private String getHtmlContent(StoryData storyData) {
        String html = "<html>";
        html += getHeader();
        html += "<body>";
        html += "<center><h2><font color=\"" + getParent().getContext().getString(R.string.adapter_story_text_title_color) + "\">";
        html += storyData.getTitle();
        html += "</font></h2></center>";
        html += "<p style=text-align:justify>";
        html += storyData.getDescription();
        html += "</p>";
        html += "</body>";
        html += "</html>";
        return html;
    }

    /**
     * Method to get the Header
     *
     * @return
     */
    private String getHeader() {
        return "<head><style>body {color:" + getParent().getContext().getString(R.string.adapter_story_text_color) + "; font-size:" + Utils.getStoryTextSize(getParent().getContext()) + "em;}</style></head>";
    }
}
