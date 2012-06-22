package jp.shuri.yamanetoshi.hf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class DetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_fragment);
		
		Intent intent = getIntent();
		AtndData data = (AtndData)intent.getParcelableExtra("data");
		if(data != null) {
			FragmentManager manager = getSupportFragmentManager();
			DetailFragment fragment = (DetailFragment)manager.findFragmentById(R.id.detail_fragment);
			fragment.setAtndData(data);
		}
	}

}
