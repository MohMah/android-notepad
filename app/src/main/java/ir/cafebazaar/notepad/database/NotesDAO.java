package ir.cafebazaar.notepad.database;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;
import ir.cafebazaar.notepad.models.Note_Table;

/**
 * Created by MohMah on 8/21/2016.
 */
public class NotesDAO{
	public static List<Note> getLatestNotes(Folder folder){
		if (folder == null)
			return SQLite.select().from(Note.class).orderBy(Note_Table.createdAt, false).queryList();
		else
			return FolderNoteDAO.getLatestNotes(folder);
	}

	public static Note getNote(int noteId){
		return SQLite.select().from(Note.class).where(Note_Table.id.is(noteId)).querySingle();
	}
}
