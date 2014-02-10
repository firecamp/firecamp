package ca.xef6.firecamp.people;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import ca.xef6.firecamp.R;

public class PeopleFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_people, container, false);
		ListView listView = (ListView) view.findViewById(R.id.list_view);
		listView.addView(inflater.inflate(R.layout.people_row, null));
		return view;
	}

}