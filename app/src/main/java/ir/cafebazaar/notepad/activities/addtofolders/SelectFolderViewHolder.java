package ir.cafebazaar.notepad.activities.addtofolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.models.Folder;

/**
 * Created by MohMah on 8/19/2016.
 */
class SelectFolderViewHolder extends RecyclerView.ViewHolder{
	private final Adapter adapter;
	Folder folder;
	@BindView(R.id.checkbox) CheckBox checkBox;
	@BindView(R.id.folder_name_text) TextView folderName;

	public SelectFolderViewHolder(final View itemView, Adapter adapter){
		super(itemView);
		ButterKnife.bind(this, itemView);
		this.adapter = adapter;
	}

	public void setFolderName(String folderNameStr){
		folderName.setText(folderNameStr);
	}

	public void setChecked(boolean checked){
		checkBox.setChecked(checked);
	}
}
