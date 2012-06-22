package jp.shuri.yamanetoshi.hf;

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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultFragment extends ListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		ArrayAdapter<AtndData> adapter = new ArrayAdapter<AtndData>(getActivity(), 
				android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		super.onActivityCreated(savedInstanceState);
	}

	public void searchEvents(String query) {
		AsyncTask<String, Void, List<AtndData>> task = new AsyncTask<String, Void, List<AtndData>>() {
			private static final String ERROR = "can not search";
			
			@Override
			protected void onPreExecute() {
				setListShown(false);
				super.onPreExecute();
			}
			
			@Override
			protected List<AtndData> doInBackground(String... params) {
				
				List<AtndData> atndList = new ArrayList<AtndData>();
				
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
						
						ArrayList<AtndData> data = parseXml(in);
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
			
			private ArrayList<AtndData> parseXml(InputStream in) throws XmlPullParserException, IOException {
				ArrayList<AtndData> atndList = new ArrayList<AtndData>();
				
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
						
						AtndData atnd = new AtndData();
						
						while (true) {
							eventType = parser.next();
							tagName = parser.getName();
			
							if (eventType == XmlPullParser.END_TAG && tagName.equals("event")) {
								atndList.add(atnd);
								break;
							}
			
							if (eventType != XmlPullParser.START_TAG)
								continue;
			
							if (tagName.equals("title")) {
								atnd.title = parser.nextText();
								continue;
							} else if(tagName.equals("description")) {
								atnd.description = parser.nextText();
								continue;
							} else if(tagName.equals("eventUrl")) {
								atnd.eventUrl = parser.nextText();
								continue;
							} else if(tagName.equals("startedAt")) {
								atnd.startedAt = parser.nextText();
								continue;
							} else if(tagName.equals("endedAt")) {
								atnd.endedAt = parser.nextText();
								continue;
							} else if(tagName.equals("url")) {
								atnd.url = parser.nextText();
								continue;
							} else if(tagName.equals("limit")) {
								atnd.limit = parser.nextText();
								continue;
							} else if(tagName.equals("address")) {
								atnd.address = parser.nextText();
								continue;
							} else if(tagName.equals("place")) {
								atnd.place = parser.nextText();
								continue;
							}
						}
					}
				}
				in.close();
			
				return atndList;
			}
			
			protected void onPostExecute(List<AtndData> result) {
				setListShown(true);

				if(result != null) {
					ArrayAdapter<AtndData> adapter = new ArrayAdapter<AtndData>(getActivity(), android.R.layout.simple_list_item_1, result);
					setListAdapter(adapter);
				} else {
					Toast.makeText(getActivity(), ERROR, Toast.LENGTH_SHORT).show();
				}
				
				super.onPostExecute(result);
			}
		};
		task.execute(query);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		AtndData data = (AtndData) l.getAdapter().getItem(position);
		
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra("data", data);
		startActivity(intent);
	}
	
}
