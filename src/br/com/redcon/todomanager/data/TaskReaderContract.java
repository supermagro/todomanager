package br.com.redcon.todomanager.data;

import android.provider.BaseColumns;

public class TaskReaderContract {

	public TaskReaderContract() {}
	
	public static abstract class TaskEntry implements BaseColumns {
		public static final String TABLE_NAME = "task";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_PLACE = "place";
		public static final String COLUMN_NAME_DESC = "desc";
		public static final String COLUMN_NAME_LIST = "list";
		public static final String COLUMN_NAME_DONE = "done";
		
		public static final String TEXT_TYPE = " TEXT";
		public static final String TIMESTAMP_TYPE = " TIMESTAMP";
		public static final String BOOLEAN_TYPE = " BOOLEAN";
		public static final String NOT_NULL = " NOT NULL";
		public static final String COMMA_SEP = ",";
		
		public static final String SQL_CREATE_ENTRIES =
				"CREATE TABLE " + TaskEntry.TABLE_NAME + " (" + TaskEntry._ID + " " + "INTEGER PRIMARY KEY" + TaskEntry.COMMA_SEP +
				TaskEntry.COLUMN_NAME_TITLE + TaskEntry.TEXT_TYPE + TaskEntry.NOT_NULL + TaskEntry.COMMA_SEP +
				TaskEntry.COLUMN_NAME_DATE + TaskEntry.TIMESTAMP_TYPE + TaskEntry.COMMA_SEP +
				TaskEntry.COLUMN_NAME_PLACE + TaskEntry.TEXT_TYPE + TaskEntry.COMMA_SEP +
				TaskEntry.COLUMN_NAME_DESC + TaskEntry.TEXT_TYPE + TaskEntry.COMMA_SEP +
				TaskEntry.COLUMN_NAME_LIST + " INTEGER" + TaskEntry.COMMA_SEP +
				"FOREIGN KEY(" + TaskEntry.COLUMN_NAME_LIST + ") references " + ListReaderContract.ListEntry.TABLE_NAME +
				" (" + ListReaderContract.ListEntry._ID + ")" + TaskEntry.COMMA_SEP +
				TaskEntry.COLUMN_NAME_DONE + TaskEntry.BOOLEAN_TYPE + TaskEntry.NOT_NULL +
				")";
		public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTIS " + TaskEntry.TABLE_NAME;
	}
}