package ir.cafebazaar.notepad.events;

import ir.cafebazaar.notepad.models.Note;

/**
 * Created by MohMah on 8/21/2016.
 */
public class NoteEditedEvent{
	Note note;

	public NoteEditedEvent(Note note){
		this.note = note;
	}

	public Note getNote(){
		return note;
	}
}
