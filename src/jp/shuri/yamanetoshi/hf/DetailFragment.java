package jp.shuri.yamanetoshi.hf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	/*
	private TextView mTitleView;
	private TextView mDescriptionView;
	private TextView mDateView;
	private TextView mLimitView;
	private TextView mPlaceView;
	*/
	static DetailFragment newInstance(AtndData data) {
		DetailFragment f = new DetailFragment();
		
		Bundle args = new Bundle();
		args.putParcelable("data", data);
		f.setArguments(args);
		
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.detail, container, false);
		
		Bundle arguments = getArguments();
		if(arguments != null) {
			AtndData data = (AtndData)arguments.getParcelable("data");
			if(data != null) {
				TextView titleView = (TextView) v.findViewById(R.id.title);
				TextView descriptionView = (TextView) v.findViewById(R.id.description);
				TextView dateView = (TextView) v.findViewById(R.id.date);
				TextView limitView = (TextView) v.findViewById(R.id.limit);
				TextView placeView = (TextView) v.findViewById(R.id.place);
				
				titleView.setText(data.title);
				descriptionView.setText(data.description);
				dateView.setText(data.startedAt + " - " + data.endedAt);
				limitView.setText("定員 : " + data.limit + "人");
				placeView.setText(data.address + ", " + data.place);
			}
		}
		/*
		mTitleView = (TextView) v.findViewById(R.id.title);
		mDescriptionView = (TextView) v.findViewById(R.id.description);
		mDateView = (TextView) v.findViewById(R.id.date);
		mLimitView = (TextView) v.findViewById(R.id.limit);
		mPlaceView = (TextView) v.findViewById(R.id.place);
		
		Bundle arguments = getArguments();
		if(arguments != null) {
			AtndData data = (AtndData)arguments.getParcelable("data");
			if(data != null) {
				setAtndData(data);
			}
		}
		*/
		return v;
	}
	/*
	public void setAtndData(AtndData data) {
		mTitleView.setText(data.title);
		mDescriptionView.setText(data.description);
		mDateView.setText(data.startedAt + " - " + data.endedAt);
		mLimitView.setText("定員 : " + data.limit + "人");
		mPlaceView.setText(data.address + ", " + data.place);
	}
*/
}
