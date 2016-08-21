package ir.cafebazaar.notepad.activities.addtofolders;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import ir.cafebazaar.notepad.R;

/**
 * Created by MohMah on 8/21/2016.
 */
public class AddToFoldersActivity extends AppCompatActivity{
	private static final String TAG = "AddToFoldersActivity";

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
	Adapter adapter;
}
