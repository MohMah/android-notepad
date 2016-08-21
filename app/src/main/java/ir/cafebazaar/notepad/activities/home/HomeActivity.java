package ir.cafebazaar.notepad.activities.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.ViewTreeObserver;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.activities.editfolders.EditFoldersActivityIntentBuilder;
import ir.cafebazaar.notepad.database.FoldersDAO;
import ir.cafebazaar.notepad.models.Folder;
import java.util.List;

/**
 * Created by MohMah on 8/17/2016.
 */
public class HomeActivity extends AppCompatActivity{
	private static final String TAG = "HomeActivity";
	private static final int ALL_NOTES_MENU_ID = -1;

	@BindView(R.id.navigation_view) NavigationView mNavigationView;
	@BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	List<Folder> latestFolders;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		//setSupportActionBar(mToolbar);
		mDrawerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
			@Override public void onGlobalLayout(){
				mDrawerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				setFragment(null);
			}
		});
		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
			@Override public boolean onNavigationItemSelected(MenuItem item){
				Log.e(TAG, "onNavigationItemSelected() called with: " + "item id = [" + item.getItemId() + "]");
				int folderId = item.getItemId();
				if (folderId == ALL_NOTES_MENU_ID){
					setFragment(null);
				}else{
					setFragment(FoldersDAO.getFolder(folderId));
				}
				mDrawerLayout.closeDrawer(Gravity.LEFT);
				inflateNavigationMenus(folderId);
				return true;
			}
		});
	}

	@Override protected void onStart(){
		super.onStart();
		inflateNavigationMenus(ALL_NOTES_MENU_ID);
	}

	public void inflateNavigationMenus(int checkedItemId){
		Menu menu = mNavigationView.getMenu();
		menu.clear();
		menu
				.add(Menu.NONE, ALL_NOTES_MENU_ID, Menu.NONE, "Notes")
				.setIcon(R.drawable.ic_note_white_24dp)
				.setChecked(checkedItemId == ALL_NOTES_MENU_ID);
		final SubMenu subMenu = menu.addSubMenu("Folders");
		latestFolders = FoldersDAO.getLatestFolders();
		for (Folder folder : latestFolders){
			subMenu
					.add(Menu.NONE, folder.getId(), Menu.NONE, folder.getName())
					.setIcon(R.drawable.ic_folder_black_24dp)
					.setChecked(folder.getId() == checkedItemId);
		}
		menu
				.add("Create or edit folders")
				.setIcon(R.drawable.ic_add_white_24dp)
				.setIntent(new EditFoldersActivityIntentBuilder().build(this));
		menu.addSubMenu(" ").add(" ");
	}

	@Override public void onBackPressed(){
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}else{
			super.onBackPressed();
		}
	}

	public void setFragment(Folder folder){
		// Create a new fragment and specify the fragment to show based on nav item clicked
		Fragment fragment = new NoteListFragment();
		if (folder != null){
			Bundle bundle = new Bundle();
			bundle.putParcelable(NoteListFragment.FOLDER, folder);
			fragment.setArguments(bundle);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

		// Highlight the selected item has been done by NavigationView
		//menuItem.setChecked(true);
		//// Set action bar title
		//setTitle(menuItem.getTitle());
		//// Close the navigation drawer
		//mDrawer.closeDrawers();
	}
}
