package br.com.redcon.todomanager.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

public class TaskReaderDbHelper extends SQLiteOpenHelper {

	public TaskReaderDbHelper (Context context) {
		super(context, ListReaderDbHelper.DATABASE_NAME, null, ListReaderDbHelper.DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TaskReaderContract.TaskEntry.SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public void insertTask(String task_title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(TaskReaderContract.TaskEntry.COLUMN_NAME_TITLE, task_title);
		contentValues.put(TaskReaderContract.TaskEntry.COLUMN_NAME_DONE, "false");
		db.insert(TaskReaderContract.TaskEntry.TABLE_NAME, null, contentValues);
		db.close();
	}
	
	public int updateTask(Task task) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TaskReaderContract.TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
		values.put(TaskReaderContract.TaskEntry.COLUMN_NAME_DATE, task.getDate().toString());
		values.put(TaskReaderContract.TaskEntry.COLUMN_NAME_PLACE, task.getPlace());
		values.put(TaskReaderContract.TaskEntry.COLUMN_NAME_DESC, task.getDescription());
		values.put(TaskReaderContract.TaskEntry.COLUMN_NAME_DONE, task.getDone());
		return database.update(ListReaderContract.ListEntry.TABLE_NAME, values, "_Id = ?", new String[]{task.getId().toString()});
	}
	
	public int deleteTask(String id) {
		SQLiteDatabase database = this.getWritableDatabase();
		return database.delete(TaskReaderContract.TaskEntry.TABLE_NAME, "_Id = ?", new String[]{id});
	}
	
	public List<SparseArray<String>> findAll(String list_id) {
		ArrayList<SparseArray<String>> itemList = new ArrayList<SparseArray<String>>();
		String selectQuery = "SELECT * FROM " + TaskReaderContract.TaskEntry.TABLE_NAME + 
				" WHERE " + TaskReaderContract.TaskEntry.COLUMN_NAME_DONE + " IS FALSE" +
				"AND " + TaskReaderContract.TaskEntry.COLUMN_NAME_LIST + " = " + list_id;
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				SparseArray<String> data = new SparseArray<String>();
				data.put(cursor.getInt(0), cursor.getString(1));
				itemList.add(data);
			} while (cursor.moveToNext());
		}
		return itemList;
	}

}