package ir.cafebazaar.notepad;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by MohMah on 8/17/2016.
 */
public class App extends Application {
    public static volatile Context CONTEXT;
    public static JobManager JOB_MANAGER;

    @Override
    public void onCreate()
    {
        super.onCreate();
        CONTEXT = getApplicationContext();
        FlowManager.init(this);
        Stetho.initializeWithDefaults(this);
        configureJobManager();
    }

    private void configureJobManager()
    {
        Configuration.Builder builder = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled()
                    {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args)
                    {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args)
                    {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args)
                    {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args)
                    {

                    }
                })
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120);//wait 2 minute
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
        //	builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(this,
        //			MyJobService.class), true);
        //}else{
        //	int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        //	if (enableGcm == ConnectionResult.SUCCESS){
        //		builder.scheduler(GcmJobSchedulerService.createSchedulerFor(this,
        //				MyGcmJobService.class), true);
        //	}
        //}
        JOB_MANAGER = new JobManager(builder.build());
    }
}
