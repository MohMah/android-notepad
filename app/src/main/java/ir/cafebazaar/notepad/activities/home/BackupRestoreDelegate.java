package ir.cafebazaar.notepad.activities.home;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.raizlabs.android.dbflow.config.FlowManager;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.database.AppDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by MohMah on 8/22/2016.
 */
class BackupRestoreDelegate{
	//TODO NEEDS REFACTORING
	HomeActivity homeActivity;

	public BackupRestoreDelegate(HomeActivity homeActivity){
		this.homeActivity = homeActivity;
	}

	void backupDataToFile(){
		View view = homeActivity.findViewById(R.id.zero_notes_view);
		try{
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()){
				//String currentDBPath = "//data//{package name}//databases//{database name}";
				String backupDBPath = "notepad_backup.nbu";
				File currentDB = homeActivity.getDatabasePath(
						FlowManager
								.getDatabase(AppDatabase.NAME)
								.getDatabaseFileName());
				final File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()){
					FileChannel src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();

					Snackbar
							.make(view, "Backup file 'notepad_backup.nbu' created successfully", Snackbar.LENGTH_LONG)
							.setAction("Share",
									new View.OnClickListener(){
										@Override public void onClick(View v){
											ShareCompat.IntentBuilder
													.from(homeActivity)
													.setType("message/rfc822")
													.setStream(Uri.fromFile(backupDB))
													.setSubject("Notepad add backup")
													.startChooser();
										}
									})
							.show();
				}else{
					Snackbar
							.make(view, "Couldn't backup, database not found", Snackbar.LENGTH_LONG)
							.show();
					Log.e(TAG, "onNavigationItemSelected: NO DB ");
				}
			}
		}catch (Exception e){
			Snackbar
					.make(view, "Couldn't backup database", Snackbar.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
	}

	void importDB(){
		View view = homeActivity.findViewById(R.id.zero_notes_view);
		try{
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()){
				//String currentDBPath = "//data//{package name}//databases//{database name}";
				String backupDBPath = "notepad_backup.nbu";
				File currentDB = homeActivity.getDatabasePath(
						FlowManager
								.getDatabase(AppDatabase.NAME)
								.getDatabaseFileName());
				final File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()){
					FileChannel src = new FileInputStream(backupDB).getChannel();
					FileChannel dst = new FileOutputStream(currentDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();

					//Snackbar
					//		.make(view, "", Snackbar.LENGTH_LONG)
					//		.show();
					Toast
							.makeText(homeActivity, "Restored successfully, please start application", Toast.LENGTH_SHORT)
							.show();
					homeActivity.finish();
					System.exit(0);
				}else{
					Snackbar
							.make(view, "Couldn't backup, database not found", Snackbar.LENGTH_LONG)
							.show();
					Log.e(TAG, "onNavigationItemSelected: NO DB ");
				}
			}
		}catch (Exception e){
			Snackbar
					.make(view, "Couldn't backup database", Snackbar.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
	}

	private static final String TAG = "BackupRestoreDelegate";

	public void showImportDialog(){
		new AlertDialog.Builder(homeActivity, R.style.DialogTheme)
				.setTitle("Restore Data")
				.setMessage(
						"Restoring data needs application restart, by hitting restore button the app is going to shut down and you must open it again")
				.setPositiveButton("Restore", new DialogInterface.OnClickListener(){
					@Override public void onClick(DialogInterface dialog, int which){
						importDB();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					@Override public void onClick(DialogInterface dialog, int which){
					}
				})
				.show();
	}
}
