package ir.cafebazaar.notepad.activities.addtofolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.activities.editfolders.NewFolderViewHolder;
import ir.cafebazaar.notepad.database.FolderNoteDAO;
import ir.cafebazaar.notepad.database.FoldersDAO;
import ir.cafebazaar.notepad.events.FolderCreatedEvent;
import ir.cafebazaar.notepad.events.FolderDeletedEvent;
import ir.cafebazaar.notepad.models.Folder;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by MohMah on 8/19/2016.
 */
class Adapter extends RecyclerView.Adapter{
	private static final int VIEW_TYPE_NEW_FOLDER = 0;
	private static final int VIEW_TYPE_SELECT_A_FOLDER = 1;

	private List<Folder> folders;
	private List<Folder> checkedFolders;
	private int noteId;

	public Adapter(int noteId){
		this.noteId = noteId;
	}

	@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		if (viewType == VIEW_TYPE_NEW_FOLDER){
			return new NewFolderViewHolder(
					LayoutInflater.from(parent.getContext()).inflate(R.layout.view_new_folder, parent, false));
		}else if (viewType == VIEW_TYPE_SELECT_A_FOLDER){
			return new SelectFolderViewHolder(
					LayoutInflater.from(parent.getContext()).inflate(R.layout.view_select_folder, parent, false), this);
		}
		return null;
	}

	@Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
		if (holder instanceof SelectFolderViewHolder){
			position--;
			SelectFolderViewHolder selectFolderViewHolder = (SelectFolderViewHolder) holder;
			Folder thisFolder = folders.get(position);
			selectFolderViewHolder.setFolderName(thisFolder.getName());
			selectFolderViewHolder.setChecked(checkedFolders.contains(thisFolder));
		}
	}

	@Override public int getItemViewType(int position){
		if (position == 0) return VIEW_TYPE_NEW_FOLDER;
		else return VIEW_TYPE_SELECT_A_FOLDER;
	}

	@Override public int getItemCount(){
		return 1 + (folders == null ? 0 : folders.size());
	}

	public void loadFromDatabase(){
		folders = FoldersDAO.getLatestFolders();
		checkedFolders = FolderNoteDAO.getFolders(noteId);
		notifyDataSetChanged();
	}

	void registerEventBus(){
		EventBus.getDefault().register(this);
	}

	void unregisterEventBus(){
		EventBus.getDefault().unregister(this);
	}

	@Subscribe public void onFolderDeletedEvent(FolderDeletedEvent folderDeletedEvent){
		int index = folders.indexOf(folderDeletedEvent.getFolder());
		if (index == -1) return;
		folders.remove(index);
		notifyItemRemoved(index + 1);
	}

	@Subscribe public void onFolderCreatedEvent(FolderCreatedEvent folderCreatedEvent){
		if (folders == null) folders = new ArrayList<>();
		folders.add(0, folderCreatedEvent.getFolder());
		notifyItemInserted(1);
	}
}
