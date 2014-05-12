package br.com.redcon.todomanager.data;

import android.provider.BaseColumns;

public final class ListReaderContract {

	public ListReaderContract() {}
	
	public static abstract class ListEntry implements BaseColumns {
		public static final String TABLE_NAME = "list";
		public static final String COLUMN_NAME_TITLE = "title";
		
		public static final String TEXT_TYPE = " TEXT";
		public static final String COMMA_SEP = ",";
		public static final String NOT_NULL = " NOT NULL";
		
		public static final String SQL_CREATE_ENTRIES =
				"CREATE TABLE " + ListEntry.TABLE_NAME + " (" + ListEntry._ID + " " + "INTEGER PRIMARY KEY" + ListEntry.COMMA_SEP +
				ListEntry.COLUMN_NAME_TITLE + ListEntry.TEXT_TYPE + ListEntry.NOT_NULL + ")";
		public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTIS " + ListEntry.TABLE_NAME;
	}
}
