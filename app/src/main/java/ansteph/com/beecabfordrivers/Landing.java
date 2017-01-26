package ansteph.com.beecabfordrivers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;

import ansteph.com.beecabfordrivers.app.GlobalRetainer;
import ansteph.com.beecabfordrivers.helper.SessionManager;
import ansteph.com.beecabfordrivers.model.Driver;
import ansteph.com.beecabfordrivers.view.registration.Login;

public class Landing extends AppCompatActivity {

   SessionManager sessionManager;

    GlobalRetainer mGlobalRetainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        mGlobalRetainer=(GlobalRetainer)getApplicationContext();

        sessionManager = new SessionManager(getApplicationContext());
      //  Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();

        if (sessionManager.isLoggedIn()){

            //if logged in load the global retainer for driver
            HashMap<String, String> user = sessionManager.getUserDetails();
            mGlobalRetainer.set_grDriver(new Driver(user.get(SessionManager.KEY_ID),user.get(SessionManager.KEY_NAME),
                    user.get(SessionManager.KEY_EMAIL) ,user.get(SessionManager.KEY_MOBILE),user.get(SessionManager.KEY_APIKEY)));


        }


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    /**
                     * Call this function whenever you want to check user login
                     * This will redirect user to Login is he is not
                     * logged in
                     * */
                   // sessionManager.checkLogin();
                   sessionManager.checkLogin();
                }
            }
        };
        timerThread.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
