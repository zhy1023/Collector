package com.zy.common.utils;

import static android.webkit.WebSettings.LOAD_DEFAULT;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @Author Liudeli
 * @Describe：WebView操作处理相关工具类
 */
public class WebViewUtils extends WebView{

    public WebViewUtils(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WebViewUtils(Context context) {
        super(context);
    }

    public WebViewUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化webview
     * @param url
     * @param openWay:true :在webview打开，false在手机默认浏览器打开
     */
    public void initWebView(final ProgressBar progressBar,final String url, final boolean openWay){

        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        this.post(new Runnable() {
            @Override
            public void run() {
                WebViewUtils.this.loadUrl(url);
            }
        });
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return openWay;
            }
        });
    }

    /**
     * 得到html并显示到webView中
     * @param url 要打开html的路径
     * @param web WebView控件
     */
    public static void getHtml( String url , WebView web){
        initSetting(web);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false

        web.getSettings().setSupportZoom(true);// 是否可以缩放，默认true
        web.getSettings().setBuiltInZoomControls(true);// 是否显示缩放按钮，默认false
        web.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放。大视图模式
        web.getSettings().setLoadWithOverviewMode(true);// 和setUseWideViewPort(true)一起解决网页自适应问题
        web.getSettings().setCacheMode(LOAD_DEFAULT);// 是否使用缓存
        web.getSettings().setDomStorageEnabled(true);// DOM Storage
        // w.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 设置webView初始值信息
     * @param web
     */
    private static void initSetting(WebView web) {
        // TODO Auto-generated method stub
        WebSettings settings = web.getSettings();
        // 是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setJavaScriptEnabled(true);
        /*
         * LOAD_DEFAULT设置如何缓存 默认使用缓存，当缓存没有，或者缓存过期，才使用网络
         * LOAD_CACHE_ELSE_NETWORK 设置默认使用缓存，即使是缓存过期，也使用缓存
         * 只有缓存消失，才使用网络
         */
        settings.setCacheMode(LOAD_DEFAULT);
        //是否展示一个缩放按钮（）
        settings.setBuiltInZoomControls(true);
    }

    /**
     * 设置webView初始值信息并且绑定url等操作
     * @param webView
     * @param url
     */
    public static void initSetting(WebView webView, String url) {
        // 登录西安交通发布，查询交通
        // webView.loadUrl("https://www.xaglkp.com.cn/BusPage/bus_realtime");
        webView.loadUrl(url);
        webView.requestFocus();//获取焦点
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(true);
        //添加客户端支持
        webView.setWebViewClient(new WebViewClient(){
            //点击不会跳转到浏览器外
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true); //设置加载进来的页面自适应手机屏幕（可缩放）
        webSettings.setLoadWithOverviewMode(true);
    }

    /**
     * 返回Html的上一个页面
     * @param webView
     */
    public static void backHtml(WebView webView) {
        webView.goBack();// 返回前一个页面
    }
}

