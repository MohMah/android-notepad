package ir.cafebazaar.notepad.models;


import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import ir.cafebazaar.notepad.database.AppDatabase;

/**
 * Created by MohMah on 8/17/2016.
 */
@Table(database = AppDatabase.class, allFields = true)
public class FolderNoteRelation extends BaseModel {
	private static final String TAG = "FolderNoteRelation";

	@PrimaryKey
	@ForeignKey(onDelete = ForeignKeyAction.CASCADE)
	private Folder folder;

	@PrimaryKey
	@ForeignKey(onDelete = ForeignKeyAction.CASCADE)
	private Note note;

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
