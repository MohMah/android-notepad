package ir.cafebazaar.notepad;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by MohMah on 8/17/2016.
 */
public class App extends Application{
	public static volatile Context CONTEXT;

	@Override
	public void onCreate(){
		super.onCreate();
		CONTEXT = getApplicationContext();
		FlowManager.init(new FlowConfig.Builder(this).build());
		Stetho.initializeWithDefaults(this);
	}
}
