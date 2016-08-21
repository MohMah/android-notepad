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
import com.greenfrvr.hashtagview.HashtagView;
import ir.cafebazaar.notepad.App;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.database.FolderNoteDAO;
import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;
import ir.cafebazaar.notepad.utils.Utils;
import java.util.List;

/**
 * Created by MohMah on 8/19/2016.
 */
public class NoteCardView extends CardView{

	private static final String TAG = "NoteCardView";
	@BindView(R.id.title) TextView title;
	@BindView(R.id.body) TextView body;
	@BindView(R.id.drawing_image) ImageView drawingImage;
	@BindView(R.id.folders_tag_view) HashtagView foldersTagView;
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
		boolean isTitleEmpty = TextUtils.isEmpty(note.getTitle());
		boolean isBodyEmpty = TextUtils.isEmpty(note.getSpannedBody());
		title.setText(note.getTitle());
		body.setText(note.getSpannedBody());
		title.setVisibility(isTitleEmpty ? GONE : VISIBLE);
		body.setVisibility(isBodyEmpty ? GONE : VISIBLE);

		List<Folder> folders = FolderNoteDAO.getFolders(note.getId());
		HashtagView.DataTransform<Folder> dt = new HashtagView.DataTransform<Folder>(){
			@Override public CharSequence prepare(Folder item){
				return item.getName();
			}
		};
		List<Folder> shownFolders;
		if (folders.size() <= 2)
			shownFolders = folders;
		else{
			shownFolders = folders.subList(0, 2);
			Folder folder = new Folder();
			folder.setName("+" + (folders.size() - 2));
			shownFolders.add(folder);
		}
		foldersTagView.setData(shownFolders, dt);
		if (note.getDrawing() == null)
			drawingImage.setVisibility(View.GONE);
		else{
			drawingImage.setVisibility(View.VISIBLE);
			Bitmap imageBitMap = Utils.getImage(note.getDrawing().getBlob());
			drawingImage.setImageBitmap(imageBitMap);
		}
		this.note = note;
	}
}
