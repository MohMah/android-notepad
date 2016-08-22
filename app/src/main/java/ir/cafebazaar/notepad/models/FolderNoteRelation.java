package ir.cafebazaar.notepad.models;

import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import ir.cafebazaar.notepad.database.AppDatabase;

/**
 * Created by MohMah on 8/17/2016.
 */
@ModelContainer
@Table(database = AppDatabase.class, allFields = true)
public class FolderNoteRelation extends BaseModel{
	private static final String TAG = "FolderNoteRelation";

	@PrimaryKey
	@ForeignKey(onDelete = ForeignKeyAction.CASCADE)
	private ForeignKeyContainer<Folder> folder;

	@PrimaryKey
	@ForeignKey(onDelete = ForeignKeyAction.CASCADE)
	private ForeignKeyContainer<Note> note;

	public ForeignKeyContainer<Folder> getFolder(){
		return folder;
	}

	public void setFolder(ForeignKeyContainer<Folder> folder){
		this.folder = folder;
	}

	public ForeignKeyContainer<Note> getNote(){
		return note;
	}

	public void setNote(ForeignKeyContainer<Note> note){
		this.note = note;
	}

	public void associateNote(Note note){
		this.note = FlowManager.getContainerAdapter(Note.class).toForeignKeyContainer(note);
	}

	public void associateFolder(Folder folder){
		this.folder = FlowManager.getContainerAdapter(Folder.class).toForeignKeyContainer(folder);
	}
}
