package br.com.redcon.todomanager;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import br.com.redcon.todomanager.data.ListReaderDbHelper;

public class TodoManagerActivity extends ListActivity implements OnClickListener {

	final Context context = this;
	ListReaderDbHelper controller = new ListReaderDbHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_manager);
		
		Button newTodoList = (Button)findViewById(R.id.newTodoList);
		newTodoList.setOnClickListener(this);
		
		returnListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_manager, menu);
		
		// Enable Home Button
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		
		//Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_task).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		      goHome();
		      return true;
		case R.id.new_item:
			newList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		TextView id = (TextView)info.targetView.findViewById(R.id.list_id);
		switch(item.getItemId()) {
		case R.id.delete:
			deleteList(id.getText().toString());
            return true;
		default:
            return super.onContextItemSelected(item);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.newTodoList:
			newList();
		default:
			
		}
	}

	private void newList() {
		LayoutInflater li = LayoutInflater.from(context);
		View dialog = li.inflate(R.layout.dialog_new_item, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set dialog to alertdialog builder
		alertDialogBuilder.setView(dialog);

		final EditText userInput = (EditText) dialog.findViewById(R.id.editTextDialogItemNameInput);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result edit text
			    	saveList(userInput.getText());
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	protected void saveList(Editable text) {
		controller.insertList(text.toString());
		returnListView();
	}
	
	protected void deleteList(String id) {
		controller.deleteList(id);
		returnListView();
	}

	private void goHome() {
		Intent intent = new Intent(this, TodoManagerActivity.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    startActivity(intent);
	}
	
	private void returnListView() {
		ArrayList<SparseArray<String>> list = (ArrayList<SparseArray<String>>)controller.findAll();
        if (list.size() > 0) {
        	ListView listView = getListView();
        	listView.setOnItemClickListener(
        			new OnItemClickListener() {
        				@Override
        				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        					TextView listId = (TextView) view.findViewById(R.id.list_id);
        					Intent editList = new Intent(getApplicationContext(), EditListActivity.class);
        					editList.putExtra("id", listId.getText());
        					startActivity(editList);
        				}
					});
        	
        	ListAdapter adapter = new ListAdapter();
        	adapter.setData(list);
        	
        	setListAdapter(adapter);
        	
        	registerForContextMenu(getListView());
        }
	}
	
	public class ListAdapter extends BaseAdapter {

		private List<SparseArray<String>> list; 
		
		public void setData(List<SparseArray<String>> list) {
			this.list = list;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
	            LayoutInflater inflater = (LayoutInflater)TodoManagerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.list_item, parent, false);
	        }
			
			TextView list_id = (TextView)convertView.findViewById(R.id.list_id);
			TextView list_description = (TextView)convertView.findViewById(R.id.list_description);
			
			String id = ((Integer)list.get(position).keyAt(0)).toString();
			list_id.setText(id);
			
			String description = list.get(position).get(Integer.parseInt(id));
			list_description.setText(description);
			
			return convertView;
		}
	}
}

/*
 * http://developer.android.com/design/patterns/actionbar.html
 * http://developer.android.com/reference/android/app/ActionBar.html
 * http://www.vogella.com/tutorials/AndroidActionBar/article.html
 * http://www.androidhive.info/2013/11/android-working-with-action-bar/
 * http://commons.wikimedia.org/wiki/File:Wikiproject_todo_icon.png
 */
