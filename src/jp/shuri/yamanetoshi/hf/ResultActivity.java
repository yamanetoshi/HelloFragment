package jp.shuri.yamanetoshi.hf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.TextView;

public class ResultActivity extends FragmentActivity {

	private static final String ATND_SEARCH_QUERY = "http://api.atnd.org/events/?keyword=";
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_fragment);
		
		Intent intent = getIntent();
		String keyword = intent.getStringExtra(Intent.EXTRA_TEXT);
		
		if(!TextUtils.isEmpty(keyword)) {
			FragmentManager manager = getSupportFragmentManager();
			ResultFragment fragment = (ResultFragment) manager.findFragmentById(R.id.result_fragment);
			fragment.searchEvents(ATND_SEARCH_QUERY + URLEncoder.encode(keyword));
		}
	}
}
