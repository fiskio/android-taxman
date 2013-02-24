package com.luckybrews.taxman;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class GuideActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		String url = getIntent().getExtras().getString("url");
		WebView webguide = (WebView) findViewById(R.id.webguide);
		webguide.loadUrl("file:///android_asset/guide.html#" + url);
	}

}
