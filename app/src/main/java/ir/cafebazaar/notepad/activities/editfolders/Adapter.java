package ir.cafebazaar.notepad.activities.editfolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.models.Folder;
import java.util.List;

/**
 * Created by MohMah on 8/19/2016.
 */
class Adapter extends RecyclerView.Adapter{
	private static final int VIEW_TYPE_NEW_FOLDER = 0;
	private static final int VIEW_TYPE_EDIT_A_FOLDER = 1;

	List<Folder> folders;

	@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		if (viewType == VIEW_TYPE_NEW_FOLDER){
			return new NewFolderViewHolder(
					LayoutInflater.from(parent.getContext()).inflate(R.layout.view_new_folder, parent, false));
		}else if (viewType == VIEW_TYPE_EDIT_A_FOLDER){
			//return new EditFolderViewHolder(
			//		LayoutInflater.from(parent.getContext()).inflate(R.layout.view_edit_folder, parent, false));
		}
		return null;
	}

	@Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){

	}

	@Override public int getItemCount(){
		return 1 + (folders == null ? 0 : folders.size());
	}

	@Override public int getItemViewType(int position){
		if (position == 0) return VIEW_TYPE_NEW_FOLDER;
		else return VIEW_TYPE_EDIT_A_FOLDER;
	}

	public void loadFromDatabase(){
		folders = SQLite.select().from(Folder.class).queryList();
		notifyDataSetChanged();
	}
}
