package ir.cafebazaar.notepad.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by MohMah on 8/17/2016.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase{
	public static final String NAME = "AppDatabase";
	public static final int VERSION = 1;
}
