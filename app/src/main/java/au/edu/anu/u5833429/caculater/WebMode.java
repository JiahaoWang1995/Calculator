package au.edu.anu.u5833429.caculater;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by lenovo on 2016/5/14.
 */
public class WebMode extends Activity{
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webmode);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        try {
            webView.loadUrl("http://m.wolframalpha.com/");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        setContentView(webView);
    }
}
