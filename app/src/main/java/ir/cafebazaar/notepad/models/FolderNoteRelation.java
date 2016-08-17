package ir.cafebazaar.notepad.models;

/**
 * Created by MohMah on 8/17/2016.
 */
public class FolderNoteRelation{

	private int id;
	private Folder folder;
	private Note note;

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public Folder getFolder(){
		return folder;
	}

	public void setFolder(Folder folder){
		this.folder = folder;
	}

	public Note getNote(){
		return note;
	}

	public void setNote(Note note){
		this.note = note;
	}
}
