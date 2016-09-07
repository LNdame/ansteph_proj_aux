package ansteph.com.beecabfordrivers.view.CabResponder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.GlobalRetainer;
import ansteph.com.beecabfordrivers.helper.SessionManager;
import ansteph.com.beecabfordrivers.model.Driver;
import ansteph.com.beecabfordrivers.service.FirebaseServerRegistration;
import ansteph.com.beecabfordrivers.testzone.GetImages;
import ansteph.com.beecabfordrivers.testzone.JoRPickupBoard;
import ansteph.com.beecabfordrivers.testzone.UploadService;
import ansteph.com.beecabfordrivers.view.extraAction.ActionList;

public class JobsBoard extends AppCompatActivity {

    SessionManager sessionManager;

    GlobalRetainer mGlobalRetainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());
        mGlobalRetainer= (GlobalRetainer) getApplicationContext();

        HashMap<String, String> user = sessionManager.getUserDetails();
        // String id, String name, String companyname, String email, String mobile, String licence, String year, String apikey, int companyid


        mGlobalRetainer.set_grDriver(new Driver(user.get(SessionManager.KEY_ID),user.get(SessionManager.KEY_NAME),user.get(SessionManager.KEY_COMNAME),user.get(SessionManager.KEY_EMAIL)
                ,user.get(SessionManager.KEY_MOBILE),user.get(SessionManager.KEY_LICENSE),user.get(SessionManager.KEY_YEAR),user.get(SessionManager.KEY_APIKEY),Integer.parseInt(user.get(SessionManager.KEY_COMPANY_ID)) ));


        //Try to register the firebase messaging token
        FirebaseMessaging.getInstance().subscribeToTopic("BeeCab");
        String token= FirebaseInstanceId.getInstance().getToken();

        Toast.makeText(getApplicationContext(), token,Toast.LENGTH_LONG).show();
        if(!token.isEmpty())
        {

            FirebaseServerRegistration fbRegistration = new FirebaseServerRegistration
                    (getApplicationContext(), mGlobalRetainer.get_grDriver(),token);

            fbRegistration.registerFBToken();

        }



        Fragment fragment = new BoardLangdingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .addToBackStack(BoardLangdingFragment.TAG);
        fragmentTransaction.replace(R.id.container_body, fragment,BoardLangdingFragment.TAG);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        //Do nothing...
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jobs_board, menu);

        MenuItem loggedUser = menu.findItem(R.id.action_loggedUser);
        if(mGlobalRetainer.get_grDriver().getName()!=null)
        {
            loggedUser.setTitle(mGlobalRetainer.get_grDriver().getName());
        }
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
            //  return true;
            Intent i = new Intent(getApplicationContext(),ActionList.class);
           // Intent i = new Intent(getApplicationContext(),GetImages.class);
            startActivity(i);
        }


        if (id == R.id.action_logout) {
            sessionManager.logoutUser();
        }
        if (id == R.id.action_profile) {
           Intent i = new Intent(getApplicationContext(), JoRPickupBoard.class);
           startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}
