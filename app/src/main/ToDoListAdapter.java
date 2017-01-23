package course.labs.todomanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Get the current ToDoItem
		final ToDoItem toDoItem = (ToDoItem) getItem(position);


		// TODO - Inflate the View for this ToDoItem
		// from todo_item.xml
		android.view.LayoutInflater inflater = android.view.LayoutInflater.from(mContext);
		final RelativeLayout itemLayout = (RelativeLayout) inflater.inflate(R.layout.todo_item, parent, false);

		// Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file
		itemLayout.setLongClickable(true);

		// TODO - Display Title in TextView
		final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
		titleView.setText(toDoItem.getTitle());

		// TODO - Set up Status CheckBox
		final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
		statusView.setChecked(toDoItem.getStatus() == ToDoItem.Status.DONE);


		// TODO - Must also set up an OnCheckedChangeListener,
		// which is called when the user toggles the status checkbox
		statusView.setOnCheckedChangeListener(new StatusChangeListener(toDoItem, itemLayout));

		// TODO - Display Priority in a TextView
		final android.widget.TextView priorityView = (android.widget.TextView) itemLayout.findViewById(R.id.priorityView);
//		priorityView.setSelection(toDoItem.getPriority().getPosition());
//		priorityView.setOnItemSelectedListener(new PrioritySelectedListener(toDoItem));
		if (toDoItem.getPriority() == ToDoItem.Priority.HIGH) {
			priorityView.setText(R.string.priority_high_string);
		} else if (toDoItem.getPriority() == ToDoItem.Priority.MED) {
			priorityView.setText(R.string.priority_medium_string);
		} else {
			priorityView.setText(R.string.priority_low_string);
		}


		// TODO - Display Time and Date.
		// Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and
		// time String
		final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateView);
		dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));
		// Return the View you just created
		return itemLayout;

	}


	private class StatusChangeListener implements OnCheckedChangeListener {
		public StatusChangeListener(ToDoItem toDoItem, View view) {
			this.toDoItem = toDoItem;
			this.view = view;
		}
		private final ToDoItem toDoItem;
		private final View view;

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			toDoItem.setStatus(isChecked ? ToDoItem.Status.DONE : ToDoItem.Status.NOTDONE);
		}
	}

	private class PrioritySelectedListener implements AdapterView.OnItemSelectedListener {
		private final ToDoItem toDoItem;

		public PrioritySelectedListener(ToDoItem toDoItem) {
			this.toDoItem = toDoItem;
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			toDoItem.setPriority(ToDoItem.Priority.valueFromPosition(position));
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent){}
	}
}
