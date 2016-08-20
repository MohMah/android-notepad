package ir.cafebazaar.notepad.activities.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.database.AppDatabase;
import ir.cafebazaar.notepad.models.Folder;
import java.util.List;

/**
 * Created by MohMah on 8/17/2016.
 */
public class HomeActivity extends AppCompatActivity{
	private static final String TAG = "HomeActivity";

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.navigation_view) NavigationView mNavigationView;
	@BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
	@BindView(R.id.new_note) FloatingActionButton mNewNoteFAB;
	@BindView(R.id.zero_notes_view) View zeroNotesView;
	Adapter adapter;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}
		});
		AppDatabase.Utils.createSomeFolders(4);
		AppDatabase.Utils.createSomeNotes(7);
		inflateNavigationMenus();
		StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		slm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
		mRecyclerView.setLayoutManager(slm);
		adapter = new Adapter(zeroNotesView);
		mRecyclerView.setAdapter(adapter);
		adapter.loadFromDatabase();
	}

	public void inflateNavigationMenus(){
		Menu menu = mNavigationView.getMenu();
		menu.add("Notes").setIcon(R.drawable.ic_note_white_24dp).setChecked(true);
		final SubMenu subMenu = menu.addSubMenu("Folders");
		List<Folder> folders = SQLite.select().from(Folder.class).queryList();
		for (Folder folder : folders){
			subMenu.add(folder.getName()).setIcon(R.drawable.ic_folder_black_24dp);
		}
		menu.add("Create new folder").setIcon(R.drawable.ic_add_white_24dp);
		menu.addSubMenu(" ").add(" ");
	}

	@Override public void onBackPressed(){
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}else{
			super.onBackPressed();
		}
	}
}
