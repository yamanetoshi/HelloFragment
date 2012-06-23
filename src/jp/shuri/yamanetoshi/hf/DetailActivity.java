package jp.shuri.yamanetoshi.hf;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class DetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		
		if(savedInstanceState == null) {
			Intent intent = getIntent();
			AtndData data = (AtndData)intent.getParcelableExtra("data");
			
			if(data != null) {
				DetailFragment details = DetailFragment.newInstance(data);
				FragmentManager manager = getSupportFragmentManager();
				FragmentTransaction ft = manager.beginTransaction();
				ft.add(android.R.id.content, details);
				ft.commit();
			}
		}
		/*
		setContentView(R.layout.detail_fragment);
		
		Intent intent = getIntent();
		AtndData data = (AtndData)intent.getParcelableExtra("data");
		if(data != null) {
			FragmentManager manager = getSupportFragmentManager();
			DetailFragment fragment = (DetailFragment)manager.findFragmentById(R.id.detail_fragment);
			fragment.setAtndData(data);
		}
		*/
	}

}
