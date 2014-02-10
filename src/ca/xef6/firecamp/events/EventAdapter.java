package ca.xef6.firecamp.events;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ca.xef6.firecamp.R;

public class EventAdapter extends ResourceCursorAdapter {

	public EventAdapter(Context context, int layout, Cursor c, int flags) {
		super(context, layout, c, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView name = (TextView) view.findViewById(R.id.event_row_name);
		TextView author = (TextView) view.findViewById(R.id.event_row_author);
		TextView description = (TextView) view
				.findViewById(R.id.event_row_description);
		TextView date = (TextView) view.findViewById(R.id.event_row_date);
		TextView time = (TextView) view.findViewById(R.id.event_row_time);
		ImageView image = (ImageView) view.findViewById(R.id.event_row_image);
		name.setText(cursor.getString(1));
		author.setText(cursor.getString(2));
		description.setText(cursor.getString(3));
		date.setText(cursor.getString(4));
		time.setText(cursor.getString(5));
		image.setImageBitmap(BitmapFactory.decodeFile(cursor.getString(6)));
	}
}