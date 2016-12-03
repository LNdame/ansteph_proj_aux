package ansteph.com.beecabfordrivers.view.CabResponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import ansteph.com.beecabfordrivers.R;
import ansteph.com.beecabfordrivers.app.Config;
import ansteph.com.beecabfordrivers.app.GlobalRetainer;
import ansteph.com.beecabfordrivers.helper.SessionManager;
import ansteph.com.beecabfordrivers.model.Driver;
import ansteph.com.beecabfordrivers.service.FirebaseServerRegistration;
import ansteph.com.beecabfordrivers.util.NotificationUtils;
import ansteph.com.beecabfordrivers.view.CabResponder.jobfragments.AnsweredFragment;
import ansteph.com.beecabfordrivers.view.CabResponder.jobfragments.AssignedFragment;
import ansteph.com.beecabfordrivers.view.extraAction.ActionList;

public class JobsBoard extends AppCompatActivity {

    private static final String TAG = JobsBoard.class.getSimpleName();

    SessionManager sessionManager;

    GlobalRetainer mGlobalRetainer;


    FragmentPagerAdapter adapterViewPager;
    ViewPager viewPager;

    TabLayout tabLayout;
    public static boolean isInFront=true;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
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

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapterViewPager = new JobCatAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

      /*  Fragment fragment = new BoardLangdingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .addToBackStack(BoardLangdingFragment.TAG);
        fragmentTransaction.replace(R.id.container_body, fragment,BoardLangdingFragment.TAG);
        fragmentTransaction.commit();*/

        final ViewAnimator viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);


        final Animation inAmin = AnimationUtils.loadAnimation(getApplication(),android.R.anim.slide_in_left);
        final Animation outAmin = AnimationUtils.loadAnimation(getApplication(),android.R.anim.slide_out_right);

        viewAnimator.setInAnimation(inAmin);
        viewAnimator.setOutAnimation(outAmin);

        viewAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAnimator.showNext();
            }
        });



        try{

            Glide.with(getApplication()).load(R.drawable.denys).into((ImageView) findViewById(R.id.imgadvplace2));
            Glide.with(getApplication()).load(R.drawable.auto).into((ImageView) findViewById(R.id.imgadvplace3));
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        Button btnCaller = (Button) findViewById(R.id.btnCaller);
        btnCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), JoRPickupBoard.class);
                startActivity(i);
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //Checking for the type of intent filter
                if(intent.getAction().equals(Config.REGISTRATION_COMPLETE))
                {
                    //gcm successful reg
                    //now subscribe to the topic beecab topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                }else if (intent.getAction().equals(Config.PUSH_NOTIFICATION))
                {
                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }

            }
        };

    }


    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (TextUtils.isEmpty(regId))
            Log.e(TAG, "Firebase reg id: Firebase Reg Id is not received yet! " );

    }

    private void updateRegIDonServer()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        String regoldId = pref.getString("regoldId", null);

        if(!regId.equals(regoldId))
        {
            FirebaseServerRegistration fbRegistration = new FirebaseServerRegistration
                    (getApplicationContext(), mGlobalRetainer.get_grDriver(),regId);

            fbRegistration.registerFBToken();



            SharedPreferences.Editor editor = pref.edit();
            editor.putString("regoldId", regId);
            editor.commit();
        }

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



    public static class JobCatAdapter extends FragmentPagerAdapter{

        private static int NUM_ITEMS = 2;
        private String tabTitles[] = new String[]{"Answered Job", "Assigned Job"};

        public JobCatAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0: return AnsweredFragment.newInstance("Page # 1","op");
                case 1: return AssignedFragment.newInstance("Page # 2","op");
                default: return null;
            }
        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position] ;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        setInFront(true);
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(GlobalRetainer.getAppContext()).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(GlobalRetainer.getAppContext()).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(GlobalRetainer.getAppContext());
    }


    @Override
    protected void onPause() {
        super.onPause();
        setInFront(false);
    }

    public boolean isInFront() {
        return isInFront;
    }

    public void setInFront(boolean inFront) {
        isInFront = inFront;
    }



}
