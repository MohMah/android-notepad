package ir.cafebazaar.notepad.database;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Folder_Table;

/**
 * Created by MohMah on 8/19/2016.
 */
public class FoldersDAO{
	public static List<Folder> getLatestFolders(){
		return SQLite.select().from(Folder.class).orderBy(Folder_Table.createdAt, false).queryList();
	}

	public static Folder getFolder(int id){
		return SQLite.select().from(Folder.class).where(Folder_Table.id.is(id)).querySingle();
	}
}
