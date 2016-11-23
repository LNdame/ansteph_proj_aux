package ansteph.com.beecabfordrivers.app;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import ansteph.com.beecabfordrivers.model.Client;
import ansteph.com.beecabfordrivers.model.Driver;
import ansteph.com.beecabfordrivers.model.JourneyRequest;

/**
 * Created by loicStephan on 26/07/16.
 * This my alternative to having to extends parcelable in some objects
 * and the existence of this object are needed at a moment notice
 */
public class GlobalRetainer extends Application {

    private static GlobalRetainer mInstance;
    private static Context mAppContext;

    public GlobalRetainer()
    {}

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalRetainer.mAppContext = getApplicationContext();
        mInstance = this;
    }

    public static GlobalRetainer getInstance(){
        return mInstance;
    }

    public ArrayList<JourneyRequest > _grPendingJobs = new ArrayList<>();
    public ArrayList<JourneyRequest > _grAssignedJobs = new ArrayList<>();
    public Driver _grDriver = new Driver();   //(only one per login)



    public Driver get_grDriver() {
        return _grDriver;
    }

    public void set_grDriver(Driver _grDriver) {
        this._grDriver = _grDriver;
    }

    public ArrayList<JourneyRequest> get_grPendingJobs() {
        return _grPendingJobs;
    }

    public void set_grPendingJobs(ArrayList<JourneyRequest> _grPendingJobs) {
        this._grPendingJobs = _grPendingJobs;
    }

    public ArrayList<JourneyRequest> get_grAssignedJobs() {
        return _grAssignedJobs;
    }

    public void set_grAssignedJobs(ArrayList<JourneyRequest> _grAssignedJobs) {
        this._grAssignedJobs = _grAssignedJobs;
    }

    public void addPendingJob(JourneyRequest jr)
    {
        this._grPendingJobs.add(jr);
    }

    public void addAssignedJob(JourneyRequest jr)
    {
        this._grAssignedJobs.add(jr);
    }


    public static Context getAppContext() {
        return mAppContext;
    }

    public static void setAppContext(Context mAppContext) {
        GlobalRetainer.mAppContext = mAppContext;
    }
}
