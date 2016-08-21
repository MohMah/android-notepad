package ir.cafebazaar.notepad.activities.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.commonsware.cwac.richedit.RichEditText;
import com.greenfrvr.hashtagview.HashtagView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.database.FolderNoteDAO;
import ir.cafebazaar.notepad.database.NotesDAO;
import ir.cafebazaar.notepad.events.NoteDeletedEvent;
import ir.cafebazaar.notepad.events.NoteEditedEvent;
import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;
import ir.cafebazaar.notepad.models.Note_Table;
import ir.cafebazaar.notepad.utils.Utils;
import ir.cafebazaar.notepad.utils.ViewUtils;
import java.util.Date;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

/**
 * Created by MohMah on 8/20/2016.
 */
@IntentBuilder
public class NoteActivity extends AppCompatActivity{
	private static final String TAG = "NoteActivity";

	@Extra @Nullable
	Integer noteId;
	Note note;

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.title) EditText title;
	@BindView(R.id.body) RichEditText body;
	@BindView(R.id.folders_tag_view) HashtagView foldersTag;
	@BindView(R.id.drawing_image) ImageView drawingImage;
	private boolean shouldFireDeleteEvent = false;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		NoteActivityIntentBuilder.inject(getIntent(), this);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationIcon(ViewUtils.tintDrawable(R.drawable.ic_arrow_back_white_24dp, R.color.md_blue_grey_400));
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				onBackPressed();
			}
		});

		Log.e(TAG, "onCreate() called with: " + "Note = [" + note + "]");

		if (noteId == null){
			note = new Note();
			Date now = new Date();
			note.setCreatedAt(now);
			note.setLastModified(now);
			note.save();
			noteId = note.getId();
		}

		bind();

		foldersTag.addOnTagClickListener(new HashtagView.TagsClickListener(){
			@Override public void onItemClicked(Object item){
				Toast.makeText(NoteActivity.this, "Folder Clicked", Toast.LENGTH_SHORT).show();
			}
		});

		body.enableActionModes(true);
	}

	private void bind(){
		note = NotesDAO.getNote(noteId);
		if (note.getTitle() != null){
			title.setText(note.getTitle());
		}
		if (note.getBody() != null){
			body.setText(note.getSpannedBody());
		}
		foldersTag.setData(FolderNoteDAO.getFolders(note), new HashtagView.DataTransform<Folder>(){
			@Override public CharSequence prepare(Folder item){
				return item.getName();
			}
		});
		if (note.getDrawing() == null)
			drawingImage.setVisibility(View.GONE);
		else{
			if (drawingImage.getVisibility() == View.VISIBLE){
				drawingImage.setAlpha(1f);
			}else{
				drawingImage.setVisibility(View.VISIBLE);
			}
			drawingImage.setImageBitmap(Utils.getImage(note.getDrawing().getBlob()));
		}
	}

	@Override protected void onStop(){
		super.onStop();
		EventBus.getDefault().unregister(this);
		assert note != null;
		if (shouldFireDeleteEvent){
			EventBus.getDefault().post(new NoteDeletedEvent(note));
		}else{
			String processedTitle = title.getText().toString().trim();
			String processedBody = body.getText().toString().trim();
			if (TextUtils.isEmpty(processedTitle) && TextUtils.isEmpty(processedBody)){
				note.delete();
				return;
			}
			//note = NotesDAO.getNote(note.getId());
			note.setSpannedBody(body.getText());
			note.setTitle(processedTitle);
			note.setLastModified(new Date());
			note.save();
			EventBus.getDefault().post(new NoteEditedEvent(note));
		}
	}

	@Override protected void onStart(){
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.note_menu, menu);
		ViewUtils.tintMenu(menu, R.id.delete_note, R.color.md_blue_grey_400);
		return super.onCreateOptionsMenu(menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item){
		if (item.getItemId() == R.id.delete_note){
			SQLite.delete().from(Note.class).where(Note_Table.id.is(note.getId())).execute();
			shouldFireDeleteEvent = true;
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@OnClick({ R.id.edit_drawing_button, R.id.drawing_image }) void clickEditDrawingButton(){
		drawingImage.setAlpha(0.5f);
		Intent intent = new DrawingActivityIntentBuilder(note.getId()).build(this);
		startActivity(intent);
	}

	@Subscribe(threadMode = ThreadMode.MAIN) public void onNoteEditedEvent(NoteEditedEvent noteEditedEvent){
		Log.e(TAG, "onNoteEditedEvent() called with: "
				+ "noteEditedEvent = ["
				+ noteEditedEvent.getNote()
				+ ", note id="
				+ note.getId()
				+ "]");
		if (note.getId() == noteEditedEvent.getNote().getId()){
			bind();
		}
	}
}