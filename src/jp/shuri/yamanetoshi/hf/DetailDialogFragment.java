package jp.shuri.yamanetoshi.hf;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailDialogFragment extends DialogFragment {

	static DetailDialogFragment newInstance(AtndData data) {
		DetailDialogFragment f = new DetailDialogFragment();
		
		Bundle args = new Bundle();
		args.putParcelable("data", data);
		f.setArguments(args);
		
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.detail, container, false);
		
		Bundle arguments = getArguments();
		if (arguments != null) {
			AtndData data = (AtndData) arguments.getParcelable("data");
			if (data != null) {
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
		return v;
	}
}
