package ir.cafebazaar.notepad.Views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.cafebazaar.notepad.App;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.models.Note;
import ir.cafebazaar.notepad.utils.Utils;

/**
 * Created by MohMah on 8/19/2016.
 */
public class NoteCardView extends CardView{

	private static final String TAG = "NoteCardView";
	@BindView(R.id.title) TextView title;
	@BindView(R.id.body) TextView body;
	private Note note;

	public NoteCardView(Context context){
		this(context, null);
	}

	public NoteCardView(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public NoteCardView(Context context, AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
		View view = LayoutInflater.from(context).inflate(R.layout.view_note_card, this, true);
		RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		int noteGap = (int) Utils.dp2px(App.CONTEXT.getResources().getDimensionPixelSize(R.dimen.notes_gap))/2;
		lp.setMargins(noteGap,noteGap,0,0);
		view.setLayoutParams(lp);
		ButterKnife.bind(this, view);
	}

	public Note getNote(){
		return note;
	}

	public void bindModel(Note note){
		this.note = note;
		title.setText(note.getTitle());
		body.setText(note.getBody());
	}

}
