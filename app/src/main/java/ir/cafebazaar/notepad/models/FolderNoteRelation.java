package ir.cafebazaar.notepad.models;

/**
 * Created by MohMah on 8/17/2016.
 */
public class FolderNoteRelation {
	private static final String TAG = "FolderNoteRelation";

	private int folderId;

	private int noteId;

	public int getFolderId(){
		return folderId;
	}

	public void setFolderId(int folderId){
		this.folderId = folderId;
	}

	public int getNoteId(){
		return noteId;
	}

	public void setNote(int noteId){
		this.noteId = noteId;
	}
}
