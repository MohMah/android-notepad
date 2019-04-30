package ir.cafebazaar.notepad.activities.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ShareCompat;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.database.AppDatabase;

/**
 * Created by MohMah on 8/22/2016.
 */
class BackupRestoreDelegate{
	static final int PICK_RESTORE_FILE_REQUEST_CODE = 12;
	private Activity activity;

	public BackupRestoreDelegate(Activity activity){
		this.activity = activity;
	}

	void backupDataToFile(){
		View view = activity.findViewById(R.id.zero_notes_view);
		try{
			File sd = Environment.getExternalStorageDirectory();

			if (sd.canWrite()){
				String backupDBPath = "notepad_backup.nbu";
				File currentDB = activity.getDatabasePath(
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
							.make(view, "Backup file 'notepad_backup.nbu' created successfully in your sdcard", Snackbar.LENGTH_LONG)
							.setAction("Share",
									new View.OnClickListener(){
										@Override public void onClick(View v){
											ShareCompat.IntentBuilder
													.from(activity)
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

	private void restoreDataFromFile(){
		if (backupFilePath == null) throw new IllegalArgumentException("Backup file path not specified");
		View view = activity.findViewById(R.id.zero_notes_view);
		try{
			File sd = Environment.getExternalStorageDirectory();

			if (sd.canWrite()){
				File currentDB = activity.getDatabasePath(
						FlowManager
								.getDatabase(AppDatabase.NAME)
								.getDatabaseFileName());
				final File backupDB = new File(backupFilePath);

				if (currentDB.exists()){
					FileChannel src = new FileInputStream(backupDB).getChannel();
					FileChannel dst = new FileOutputStream(currentDB).getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();

					activity.finish();
					System.exit(0);
				}else{
					Snackbar
							.make(view, "Couldn't restore, database not found", Snackbar.LENGTH_LONG)
							.show();
					Log.e(TAG, "onNavigationItemSelected: NO DB ");
				}
			}
		}catch (Exception e){
			Snackbar
					.make(view, "Couldn't restore database", Snackbar.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
	}

	private static final String TAG = "BackupRestoreDelegate";

	private void showRestoreDialog(String backupFilePath){
		this.backupFilePath = backupFilePath;
		new AlertDialog.Builder(activity, R.style.DialogTheme)
				.setTitle("Restore Data")
				.setMessage(
						"Restoring data needs application restart, by hitting restore button the app is going to shut down and you must open it again")
				.setPositiveButton("Restore", new DialogInterface.OnClickListener(){
					@Override public void onClick(DialogInterface dialog, int which){
						restoreDataFromFile();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					@Override public void onClick(DialogInterface dialog, int which){
					}
				})
				.show();
	}

	public void startFilePickerIntent(){
		Toast.makeText(activity, "Select a restore file with .nbu extension", Toast.LENGTH_LONG).show();
		new MaterialFilePicker()
				.withActivity(activity)
				.withRequestCode(PICK_RESTORE_FILE_REQUEST_CODE)
				.withFilter(Pattern.compile(".*\\.nbu$")) // Filtering files and directories by file name using regexp
				.withFilterDirectories(false) // Set directories filterable (false by default)
				.withHiddenFiles(false) // Show hidden files and folders
				.start();
	}

	String backupFilePath;

	public void handleFilePickedWithFilePicker(int resultCode, Intent data){
		if (resultCode == Activity.RESULT_OK){
			showRestoreDialog(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
		}else{
			Toast.makeText(activity, "Couldn't pick the file", Toast.LENGTH_SHORT).show();
		}
	}

	public void handleFilePickedWithIntentFilter(@NonNull Uri data){
		showRestoreDialog(data.getPath());
	}
}
