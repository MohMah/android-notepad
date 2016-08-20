package ir.cafebazaar.notepad.database;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.FolderNoteRelation;
import ir.cafebazaar.notepad.models.FolderNoteRelation_Table;
import ir.cafebazaar.notepad.models.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MohMah on 8/20/2016.
 */
public class FolderNoteDAO{
	public static List<Folder> getFolders(Note note){
		List<FolderNoteRelation> folderNoteRelations = SQLite
				.select()
				.from(FolderNoteRelation.class)
				.where(FolderNoteRelation_Table.note_id.is(note.getId()))
				.queryList();
		List<Folder> folders = new ArrayList<>(folderNoteRelations.size());
		for (FolderNoteRelation folderNoteRelation : folderNoteRelations){
			folders.add(folderNoteRelation.getFolder().load());
		}
		return folders;
	}
}
