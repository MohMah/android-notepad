package ir.cafebazaar.notepad.activities.home;

import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import ir.cafebazaar.notepad.activities.note.NoteActivityIntentBuilder;
import ir.cafebazaar.notepad.database.NotesDAO;
import ir.cafebazaar.notepad.events.NoteDeletedEvent;
import ir.cafebazaar.notepad.events.NoteEditedEvent;
import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;
import ir.cafebazaar.notepad.utils.SimpleViewHolder;
import ir.cafebazaar.notepad.views.NoteCardView;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by MohMah on 8/19/2016.
 */
class Adapter extends RecyclerView.Adapter{

	private static final String TAG = "Adapter";
	private final Folder folder;
	List<Note> notes;
	View.OnClickListener noteOnClickListener = new View.OnClickListener(){
		@Override public void onClick(View v){
			if (v instanceof NoteCardView){
				NoteCardView noteCardView = (NoteCardView) v;
				Intent intent = new NoteActivityIntentBuilder().noteId(noteCardView.getNote().getId()).build(v.getContext());
				v.getContext().startActivity(intent);
			}
		}
	};

	private View zeroNotesView;

	public Adapter(View zeroNotesView, Folder folder){
		this.zeroNotesView = zeroNotesView;
		this.folder = folder;
	}

	@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		View view = new NoteCardView(parent.getContext());
		view.setOnClickListener(noteOnClickListener);
		return new SimpleViewHolder(view);
	}

	@Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
		if (holder.itemView instanceof NoteCardView){
			NoteCardView noteCardView = (NoteCardView) holder.itemView;
			noteCardView.bindModel(notes.get(position));
		}
	}

	@Override public int getItemCount(){
		int size = notes == null ? 0 : notes.size();
		if (size == 0) zeroNotesView.setVisibility(View.VISIBLE);
		else zeroNotesView.setVisibility(View.GONE);
		return size;
	}

	void loadFromDatabase(){
		notes = NotesDAO.getLatestNotes(folder);
		notifyDataSetChanged();
	}

	void registerEventBus(){
		EventBus.getDefault().register(this);
	}

	void unregisterEventBus(){
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(sticky = true) public void onNoteEditedEvent(NoteEditedEvent noteEditedEvent){
		Log.e(TAG, "onNoteEditedEvent() called with: " + "noteEditedEvent = [" + noteEditedEvent + "]");
		Note note = noteEditedEvent.getNote();
		if (notes.contains(note)){
			int index = notes.indexOf(note);
			notes.set(index, note);
			notifyItemChanged(index);
		}else{
			notes.add(0, note);
			notifyItemInserted(0);
		}
		EventBus.getDefault().removeStickyEvent(NoteEditedEvent.class);
	}

	@Subscribe(sticky = true) public void onNoteDeletedEvent(NoteDeletedEvent noteDeletedEvent){
		Log.e(TAG, "onNoteDeletedEvent() called with: " + "noteDeletedEvent = [" + noteDeletedEvent.getNote() + "]");
		final Note note = noteDeletedEvent.getNote();
		if (notes.contains(note)){
			int index = notes.indexOf(note);
			notes.remove(index);
			notifyItemRemoved(index);
			Snackbar
					.make(zeroNotesView, "Deleted Note " + note.getTitle(), Snackbar.LENGTH_LONG)
					.setAction("UNDO",
							new View.OnClickListener(){
								@Override public void onClick(View v){
									note.save();
									EventBus.getDefault().post(new NoteEditedEvent(note.getId()));
								}
							})
					.show();
		}
		EventBus.getDefault().removeStickyEvent(NoteDeletedEvent.class);
	}
}
