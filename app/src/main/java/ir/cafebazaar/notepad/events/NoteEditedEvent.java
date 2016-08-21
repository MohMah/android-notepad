package ir.cafebazaar.notepad.events;

import ir.cafebazaar.notepad.database.NotesDAO;
import ir.cafebazaar.notepad.models.Note;

/**
 * Created by MohMah on 8/21/2016.
 */
public class NoteEditedEvent{
	int noteId;

	public NoteEditedEvent(int noteId){
		this.noteId = noteId;
	}

	public int getNoteId(){
		return noteId;
	}
	
	public Note getNote(){
		return NotesDAO.getNote(noteId);
	}
}
