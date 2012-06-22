package jp.shuri.yamanetoshi.hf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ResultFragment extends Fragment {
	
	private TextView mTextView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.result, container, false);
		mTextView = (TextView) v.findViewById(R.id.result);
		return v;
	}

	public void searchEvents(String query) {
		AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
			private static final String ERROR = "can not search";
			private ProgressDialog mDialog;
			
			@Override
			protected void onPreExecute() {
				mDialog = new ProgressDialog(getActivity());
				mDialog.setMessage("Searching...");
				mDialog.show();
				super.onPreExecute();
			}
			
			@Override
			protected String doInBackground(String... params) {
				if (params.length < 1)
					return ERROR;
				if (TextUtils.isEmpty(params[0]))
					return ERROR;
				
				String url = params[0];
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet hg = new HttpGet(url);
				
				StringBuilder sb = new StringBuilder();
				
				try {
					HttpResponse httpResponse = httpClient.execute(hg);
					
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity httpEntity = httpResponse.getEntity();
						InputStream in = httpEntity.getContent();
						
						BufferedReader br = new BufferedReader(new InputStreamReader(in, HTTP.UTF_8), 8);
						String line;
						while ((line = br.readLine()) != null) {
							sb.append(line + "\n");
						}
						in.close();
						hg.abort();

						return sb.toString();				
					} else {
						hg.abort();
						return ERROR;
					}
				} catch (ClientProtocolException e) {
					return ERROR;
				} catch (IOException e) {
					return ERROR;
				} catch (NullPointerException e) {
					return ERROR;
				}
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (mDialog != null)
					mDialog.dismiss();
				
				if (mTextView != null) {
					mTextView.setText(result);
				}
				super.onPostExecute(result);
			}
		};
		task.execute(query);
	}
}
