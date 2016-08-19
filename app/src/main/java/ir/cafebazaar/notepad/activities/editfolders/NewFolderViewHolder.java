package ir.cafebazaar.notepad.activities.editfolders;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.cafebazaar.notepad.App;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.utils.Utils;

/**
 * Created by MohMah on 8/19/2016.
 */
public class NewFolderViewHolder extends RecyclerView.ViewHolder{
	@BindView(R.id.left_button) AppCompatImageButton leftButton;
	@BindView(R.id.folder_name_text) TextView folderName;
	@BindView(R.id.done_button) AppCompatImageButton doneButton;

	public NewFolderViewHolder(final View itemView){
		super(itemView);
		ButterKnife.bind(this, itemView);
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

	@OnClick(R.id.left_button) void clickLeftButton(View view){
		if (folderName.hasFocus()){
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

	private void open(){
		doneButton.setVisibility(View.VISIBLE);
		leftButton.setImageResource(R.drawable.ic_close_white_24dp);
		leftButton.setAlpha(0.5f);
	}

	private void close(){
		Utils.hideSoftKeyboard(itemView);
		doneButton.setVisibility(View.GONE);
		folderName.setText(null);
		folderName.clearFocus();
		leftButton.setAlpha(1f);
		leftButton.setImageResource(R.drawable.ic_add_white_24dp);
	}

	private void add(){
		if (TextUtils.isEmpty(folderName.getText())) return;
		Toast.makeText(App.CONTEXT, "Folder Name: " + folderName.getText(), Toast.LENGTH_SHORT).show();
		//TODO add new folder
	}
}
