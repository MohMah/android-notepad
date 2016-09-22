package ir.cafebazaar.notepad.database;

import java.util.Collections;
import java.util.List;

import ir.cafebazaar.notepad.models.Folder;

/**
 * Created by MohMah on 8/19/2016.
 */
public class FoldersDAO {
    public static List<Folder> getLatestFolders() {
//		return SQLite.select().from(Folder.class).orderBy(Folder_Table.createdAt, false).queryList();
        //TODO return latest folders
        return Collections.emptyList();
    }

    public static Folder getFolder(int id) {
//		return SQLite.select().from(Folder.class).where(Folder_Table.id.is(id)).querySingle();
        //TODO return  folder with id
        return new Folder();
    }

    public static void delete(Folder folder) {
        //TODO remove folder from database
    }

    public static void save(Folder folder) {
        //TODO save flder to database
    }
}
