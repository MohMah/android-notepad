package ir.cafebazaar.notepad.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
	@BindView(R.id.drawing_image) ImageView drawingImage;
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
		int noteGap = (int) Utils.dp2px(App.CONTEXT.getResources().getDimensionPixelSize(R.dimen.notes_gap)) / 2;
		lp.setMargins(noteGap, noteGap, 0, 0);
		view.setLayoutParams(lp);
		ButterKnife.bind(this, view);
	}

	public Note getNote(){
		return note;
	}

	public void bindModel(Note note){
		this.note = note;
		boolean isTitleEmpty = TextUtils.isEmpty(note.getTitle());
		title.setText(isTitleEmpty ? "[Untitled]" : note.getTitle());
		if (isTitleEmpty) title.setAlpha(0.5f);
		else title.setAlpha(1f);
		body.setText(note.getSpannedBody());
		if (note.getDrawing() == null)
			drawingImage.setVisibility(View.GONE);
		else{
			drawingImage.setVisibility(View.VISIBLE);
			Bitmap imageBitMap = Utils.getImage(note.getDrawing().getBlob());
			drawingImage.setImageBitmap(imageBitMap);
		}
	}
}
