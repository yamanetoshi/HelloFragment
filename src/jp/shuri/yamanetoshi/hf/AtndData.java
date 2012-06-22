package jp.shuri.yamanetoshi.hf;

import android.os.Parcel;
import android.os.Parcelable;

public class AtndData implements Parcelable {
	public String title;
	public String description;
	public String eventUrl;
	public String startedAt;
	public String endedAt;
	public String url;
	public String limit;
	public String address;
	public String place;
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeString(description);
		out.writeString(eventUrl);
		out.writeString(startedAt);
		out.writeString(endedAt);
		out.writeString(url);
		out.writeString(limit);
		out.writeString(address);
		out.writeString(place);
	}
	
	public static final Parcelable.Creator<AtndData> CREATOR = new Parcelable.Creator<AtndData>() {
		public AtndData createFromParcel(Parcel in) {
			return new AtndData(in);
		}
		public AtndData[] newArray(int size) {
			return new AtndData[size];
		}
	};
	
	private AtndData(Parcel in) {
		title = in.readString();
		description = in.readString();
		eventUrl = in.readString();
		startedAt = in.readString();
		endedAt = in.readString();
		url = in.readString();
		limit = in.readString();
		address = in.readString();
		place = in.readString();
	}
	
	public AtndData() {
	}
}
