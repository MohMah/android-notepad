package ir.cafebazaar.notepad.database;

import java.util.Collections;
import java.util.List;

import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;

/**
 * Created by MohMah on 8/21/2016.
 */
public class NotesDAO{
	public static List<Note> getLatestNotes(Folder folder){
//		if (folder == null)
//			return SQLite.select().from(Note.class).orderBy(Note_Table.createdAt, false).queryList();
//		else
//			return FolderNoteDAO.getLatestNotes(folder);
		//TODO get Latest Notes
		return Collections.emptyList();
	}

	public static Note getNote(int noteId){
//		return SQLite.select().from(Note.class).where(Note_Table.id.is(noteId)).querySingle();
		//TODO get note with id
		return new Note();
	}

	public static void delete(Note note) {
		//TODO delete note
	}

	public static void save(Note note) {
		//TODO save note
	}
}
