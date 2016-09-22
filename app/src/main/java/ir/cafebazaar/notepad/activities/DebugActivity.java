package ir.cafebazaar.notepad.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.database.AppDatabase;

/**
 * Created by MohMah on 8/20/2016.
 */
public class DebugActivity extends AppCompatActivity{
	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.assign_to_folders) void assignToFolders(){
//		Note note = SQLite.select().from(Note.class).orderBy(Note_Table.createdAt, false).querySingle();
//		List<Folder> folders = FoldersDAO.getLatestFolders();
//		for (Folder folder : folders){
//			FolderNoteRelation fnr = new FolderNoteRelation();
//			fnr.associateFolder(folder);
//			fnr.associateNote(note);
//			fnr.save();
//		}
		//TODO
	}

	@OnClick(R.id.create_5_notes) void create5Notes(){
		AppDatabase.Utils.createSomeNotes(5);
	}
}
