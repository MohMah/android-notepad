package ir.cafebazaar.notepad;

import android.app.Application;
import android.content.Context;

/**
 * Created by MohMah on 8/17/2016.
 */
public class App extends Application{
	public static volatile Context CONTEXT;

	@Override
	public void onCreate(){
		super.onCreate();
		CONTEXT = getApplicationContext();
	}
}
