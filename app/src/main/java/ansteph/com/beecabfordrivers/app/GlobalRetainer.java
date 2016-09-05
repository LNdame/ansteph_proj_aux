package ansteph.com.beecabfordrivers.app;

import android.app.Application;

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

    public GlobalRetainer()
    {}


    public ArrayList<JourneyRequest > _grPendingJobs = new ArrayList<>();

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

    public void addPendingJob(JourneyRequest jr)
    {
        this._grPendingJobs.add(jr);
    }
}