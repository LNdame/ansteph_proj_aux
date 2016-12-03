package ansteph.com.beecabfordrivers.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ansteph.com.beecabfordrivers.app.Config;

/**
 * Created by loicStephan on 04/08/16.
 */
public class BeeCabFbInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG =BeeCabFbInstanceIdService.class.getSimpleName();


    @Override
    public void onTokenRefresh() {
        String token  = FirebaseInstanceId.getInstance().getToken();

        Log.d("token", token);
        // Saving reg id to shared preferences
        storeRegIdInPref(token);

        // sending reg id to your server
        sendRegistrationToServer(token);

        //Notify CabCaller that the token registration is complete
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);


    }

    private void registerToken(String token){

    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.putString("regoldId", "");
        editor.commit();
    }
}
