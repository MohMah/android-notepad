package ir.cafebazaar.notepad.activities.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import ir.cafebazaar.notepad.App;
import ir.cafebazaar.notepad.Views.NoteCardView;
import ir.cafebazaar.notepad.models.Note;
import ir.cafebazaar.notepad.utils.SimpleViewHolder;
import java.util.List;

/**
 * Created by MohMah on 8/19/2016.
 */
class Adapter extends RecyclerView.Adapter{

	private static final String TAG = "Adapter";
	List<Note> notes;
	View.OnClickListener noteOnClickListener = new View.OnClickListener(){
		@Override public void onClick(View v){
			if (v instanceof NoteCardView){
				NoteCardView noteCardView = (NoteCardView) v;
				Toast.makeText(App.CONTEXT, "Clicked "+noteCardView.getNote().getTitle(), Toast.LENGTH_SHORT).show();
			}
		}
	};

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
		return notes == null ? 0 : notes.size();
	}

	void loadFromDatabase(){
		notes = SQLite.select().from(Note.class).queryList();
		notifyDataSetChanged();
	}
}
