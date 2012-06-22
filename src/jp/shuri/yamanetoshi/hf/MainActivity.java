package jp.shuri.yamanetoshi.hf;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {
	private static final String ATND_SEARCH_QUERY = "http://api.atnd.org/events/?keyword=";
	
	private Button mSearchBtn;
	private EditText mEditText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        FragmentManager manager = getSupportFragmentManager();
        final ResultFragment fragment = (ResultFragment)manager.findFragmentById(R.id.result_fragment);
        fragment.setOnAtndListSelected(new ResultFragment.OnAtndListSelectedListener() {

			@Override
			public void onAtndListSelected(AtndData data) {
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				intent.putExtra("data", data);
				startActivity(intent);
			}
        	
        });
        
        mSearchBtn = (Button)findViewById(R.id.search_btn);
        mEditText = (EditText)findViewById(R.id.keyword);
        
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = mEditText.getText().toString();
				if(!TextUtils.isEmpty(keyword)) {
					FragmentManager manager = getSupportFragmentManager();
					ResultFragment fragment = (ResultFragment)manager.findFragmentById(R.id.result_fragment);
					fragment.searchEvents(ATND_SEARCH_QUERY +URLEncoder.encode(keyword));
				}
			}
		});
    }
}