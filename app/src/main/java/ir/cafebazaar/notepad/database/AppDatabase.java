package ir.cafebazaar.notepad.database;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;
import java.util.Date;

/**
 * Created by MohMah on 8/17/2016.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase{
	public static final String NAME = "AppDatabase";
	public static final int VERSION = 1;

	public static class Utils{
		public static void createSomeFolders(int count){
			for (int i = 0; i < count; i++){
				Folder folder = new Folder();
				folder.setCreatedAt(new Date());
				folder.setId(i);
				folder.setName("Folder " + (i + 1));
				folder.save();
			}
		}

		public static void createSomeNotes(int count){
			for (int i = 0; i < count; i++){
				Note note = new Note();
				String ithNote = "Note " + (i + 1);
				note.setBody(ithNote + "'s Body Text Goes Here");
				note.setCreatedAt(new Date());
				note.setLastModified(new Date());
				note.setId(i);
				note.setTitle(ithNote + "'s Title");
			}
		}

		public static void deleteAllFolders(){
			SQLite.delete().from(Folder.class).execute();
		}
	}
}
