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

public class ResultFragment extends ListFragment {
	
	private TextView mTextView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		super.onActivityCreated(savedInstanceState);
	}

	public void searchEvents(String query) {
		AsyncTask<String, Void, List<String>> task = new AsyncTask<String, Void, List<String>>() {
			private static final String ERROR = "can not search";
			
			@Override
			protected void onPreExecute() {
				setListShown(false);
				super.onPreExecute();
			}
			
			@Override
			protected List<String> doInBackground(String... params) {
				if (params.length < 1)
					return null;
				if (TextUtils.isEmpty(params[0]))
					return null;
				
				String url = params[0];
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet hg = new HttpGet(url);
				
				StringBuilder sb = new StringBuilder();
				
				try {
					HttpResponse httpResponse = httpClient.execute(hg);
					
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity httpEntity = httpResponse.getEntity();
						InputStream in = httpEntity.getContent();
						
						ArrayList<String> data = parseXml(in);
						hg.abort();

						return data;				
					} else {
						hg.abort();
						return null;
					}
				} catch (XmlPullParserException e) {
					return null;
				} catch (ClientProtocolException e) {
					return null;
				} catch (IOException e) {
					return null;
				} catch (IllegalArgumentException e) {
					return null;
				} catch (NullPointerException e) {
					return null;
				}
			}
			
			private ArrayList<String> parseXml(InputStream in) throws XmlPullParserException, IOException {
				ArrayList<String> titleList = new ArrayList<String>();
				
				final XmlPullParser parser = Xml.newPullParser();
				parser.setInput(new InputStreamReader(in));
				
				String tagName;
				int eventType;
				
				// parse XML
				while (true) {
					eventType = parser.next();
			
					if (eventType == XmlPullParser.END_DOCUMENT)
						break;
			
					if (eventType != XmlPullParser.START_TAG)
						continue;
			
					tagName = parser.getName();
			
					if (tagName.equals("event")) {
						while (true) {
							eventType = parser.next();
							tagName = parser.getName();
			
							if (eventType == XmlPullParser.END_TAG && tagName.equals("event")) {
								break;
							}
			
							if (eventType != XmlPullParser.START_TAG)
								continue;
			
							if (tagName.equals("title")) {
								titleList.add(parser.nextText());
								break;
							}
						}
					}
				}
				in.close();
			
				return titleList;
			}
			
			@Override
			protected void onPostExecute(List<String> result) {
				setListShown(true);

				if(result != null) {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, result);
					setListAdapter(adapter);
				} else {
					Toast.makeText(getActivity(), ERROR, Toast.LENGTH_SHORT).show();
				}
				
				super.onPostExecute(result);
			}
		};
		task.execute(query);
	}
}
