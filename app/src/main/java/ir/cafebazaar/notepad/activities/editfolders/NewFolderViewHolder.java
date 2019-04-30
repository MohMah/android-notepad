package ir.cafebazaar.notepad.activities.editfolders;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.events.FolderCreatedEvent;
import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.utils.Utils;

/**
 * Created by MohMah on 8/19/2016.
 */
public class NewFolderViewHolder extends RecyclerView.ViewHolder implements OpenCloseable{
	private final Adapter adapter;
	@BindView(R.id.left_button) AppCompatImageButton leftButton;
	@BindView(R.id.folder_name_text) TextView folderName;
	@BindView(R.id.done_button) AppCompatImageButton doneButton;

	public NewFolderViewHolder(final View itemView, Adapter adapter){
		super(itemView);
		ButterKnife.bind(this, itemView);
		this.adapter = adapter;
		folderName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
			@Override public void onFocusChange(View v, boolean hasFocus){
				if (hasFocus){
					open();
				}
			}
		});
		folderName.setOnEditorActionListener(new TextView.OnEditorActionListener(){
			@Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
				if (actionId == EditorInfo.IME_ACTION_DONE){
					add();
					close();
					return true;
				}
				return false;
			}
		});
	}

	public NewFolderViewHolder(final View itemView){
		this(itemView, null);
	}

	@OnClick(R.id.left_button) void clickLeftButton(View view){
		if (isOpen()){
			close();
		}else{
			folderName.requestFocus();
			Utils.showSoftKeyboard(folderName);
			open();
		}
	}

	@OnClick(R.id.done_button) void clickDoneButton(View view){
		add();
		close();
	}

	@Override public void open(){
		doneButton.setVisibility(View.VISIBLE);
		leftButton.setImageResource(R.drawable.ic_close_white_24dp);
		leftButton.setAlpha(0.5f);
		itemView.setBackgroundResource(R.color.md_white_1000);
		if (adapter != null){
			if (adapter.getLastOpened() != null)
				adapter.getLastOpened().close();
			adapter.setLastOpened(this);
		}
	}

	@Override public boolean isOpen(){
		return folderName.hasFocus();
	}

	@Override public void close(){
		Utils.hideSoftKeyboard(itemView);
		doneButton.setVisibility(View.GONE);
		folderName.setText(null);
		folderName.clearFocus();
		leftButton.setAlpha(1f);
		leftButton.setImageResource(R.drawable.ic_add_white_24dp);
		itemView.setBackgroundResource(0);
		if (adapter!=null && adapter.getLastOpened() == this) adapter.setLastOpened(null);
	}

	private void add(){
		if (TextUtils.isEmpty(folderName.getText())) return;
		Folder folder = new Folder();
		folder.setCreatedAt(new Date());
		folder.setName(folderName.getText().toString().trim());
		folder.save();
		EventBus.getDefault().post(new FolderCreatedEvent(folder));
	}
}
