package ir.cafebazaar.notepad.activities.note;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.commonsware.cwac.richedit.RichEditText;
import com.greenfrvr.hashtagview.HashtagView;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.database.FolderNoteDAO;
import ir.cafebazaar.notepad.events.NoteEditedEvent;
import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;
import java.util.Date;
import org.greenrobot.eventbus.EventBus;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

/**
 * Created by MohMah on 8/20/2016.
 */
@IntentBuilder
public class NoteActivity extends AppCompatActivity{
	private static final String TAG = "NoteActivity";

	@Extra @Nullable Note note;

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.title) EditText title;
	@BindView(R.id.body) RichEditText body;
	@BindView(R.id.folders_tag_view) HashtagView foldersTag;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		NoteActivityIntentBuilder.inject(getIntent(), this);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
		upArrow.setColorFilter(getResources().getColor(R.color.md_blue_grey_700), PorterDuff.Mode.SRC_ATOP);
		mToolbar.setNavigationIcon(upArrow);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				onBackPressed();
			}
		});

		if (note == null){
			note = new Note();
			Date now = new Date();
			note.setCreatedAt(now);
			note.setLastModified(now);
			note.save();
		}

		foldersTag.addOnTagClickListener(new HashtagView.TagsClickListener(){
			@Override public void onItemClicked(Object item){
				Toast.makeText(NoteActivity.this, "Folder Clicked", Toast.LENGTH_SHORT).show();
			}
		});

		body.enableActionModes(true);
		//body.setOnSelectionChangedListener(new RichEditText.OnSelectionChangedListener(){
		//	@Override public void onSelectionChanged(int i, int i1, List<Effect<?>> list){
		//		Log.e(TAG, "onSelectionChanged() called with: " + "i = [" + i + "], i1 = [" + i1 + "], list = [" + list + "]");
		//	}
		//});
		//startSupportActionMode(new ActionMode.Callback(){
		//	@Override public boolean onCreateActionMode(ActionMode mode, Menu menu){
		//		menu.add("Action 1").setIcon(R.drawable.ic_folder_black_24dp);
		//		return true;
		//	}
		//
		//	@Override public boolean onPrepareActionMode(ActionMode mode, Menu menu){
		//		return false;
		//	}
		//
		//	@Override public boolean onActionItemClicked(ActionMode mode, MenuItem item){
		//		return false;
		//	}
		//
		//	@Override public void onDestroyActionMode(ActionMode mode){
		//
		//	}
		//});
	}

	private void bind(){
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
	}

	@Override protected void onStop(){
		super.onStop();
		assert note != null;
		String processedTitle = title.getText().toString().trim();
		String processedBody = body.getText().toString().trim();
		if (TextUtils.isEmpty(processedTitle) && TextUtils.isEmpty(processedBody)){
			note.delete();
			return;
		}
		note.setSpannedBody(new SpannableString(processedBody));
		note.setTitle(processedTitle);
		note.save();
		note.setLastModified(new Date());
		EventBus.getDefault().post(new NoteEditedEvent(note));
	}

	@Override protected void onStart(){
		super.onStart();
		bind();
	}

	@Override public boolean onCreateOptionsMenu(Menu menu){
		return super.onCreateOptionsMenu(menu);
	}
}