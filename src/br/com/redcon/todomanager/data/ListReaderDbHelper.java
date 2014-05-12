package br.com.redcon.todomanager.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

public class ListReaderDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ListReader.db";
	
	public ListReaderDbHelper (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ListReaderContract.ListEntry.SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public void insertList(String list_title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(ListReaderContract.ListEntry.COLUMN_NAME_TITLE, list_title);
		db.insert(ListReaderContract.ListEntry.TABLE_NAME, null, contentValues);
		db.close();
	}
	
	public int updateList(String id, String list_title) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ListReaderContract.ListEntry.COLUMN_NAME_TITLE, list_title);
		return database.update(ListReaderContract.ListEntry.TABLE_NAME, values, "_Id = ?", new String[]{id});
	}
	
	public int deleteList(String id) {
		SQLiteDatabase database = this.getWritableDatabase();
		return database.delete(ListReaderContract.ListEntry.TABLE_NAME, "_Id = ?", new String[]{id});
	}
	
	public List<SparseArray<String>> findAll() {
		ArrayList<SparseArray<String>> itemList = new ArrayList<SparseArray<String>>();
		String selectQuery = "SELECT * FROM " + ListReaderContract.ListEntry.TABLE_NAME;
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
	
	public String findTextById(String id) {
		String selectQuery = "SELECT " + ListReaderContract.ListEntry.COLUMN_NAME_TITLE + " FROM " + ListReaderContract.ListEntry.TABLE_NAME +
				" WHERE " + ListReaderContract.ListEntry._ID + " = " + id;
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			return cursor.getString(1);
		}
		return "";
	}
}