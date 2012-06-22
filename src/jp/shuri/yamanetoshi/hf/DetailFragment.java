package jp.shuri.yamanetoshi.hf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	
	private TextView mTitleView;
	private TextView mDescriptionView;
	private TextView mDateView;
	private TextView mLimitView;
	private TextView mPlaceView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.detail, container, false);
		mTitleView = (TextView) v.findViewById(R.id.title);
		mDescriptionView = (TextView) v.findViewById(R.id.description);
		mDateView = (TextView) v.findViewById(R.id.date);
		mLimitView = (TextView) v.findViewById(R.id.limit);
		mPlaceView = (TextView) v.findViewById(R.id.place);
		return v;
	}
	
	public void setAtndData(AtndData data) {
		mTitleView.setText(data.title);
		mDescriptionView.setText(data.description);
		mDateView.setText(data.startedAt + " - " + data.endedAt);
		mLimitView.setText("定員 : " + data.limit + "人");
		mPlaceView.setText(data.address + ", " + data.place);
	}

}
