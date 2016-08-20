package ir.cafebazaar.notepad.events;

import ir.cafebazaar.notepad.models.Note;

/**
 * Created by MohMah on 8/21/2016.
 */
public class NoteDeletedEvent{
	Note note;

	public NoteDeletedEvent(Note note){
		this.note = note;
	}

	public Note getNote(){
		return note;
	}
}
