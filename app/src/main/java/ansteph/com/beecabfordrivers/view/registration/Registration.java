package ansteph.com.beecabfordrivers.view.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ansteph.com.beecabfordrivers.R;

public class Registration extends AppCompatActivity {

    private String driverClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();

        if(b!=null)
        {
            int status = b.getInt("OTP");
            if(status==0){
                Fragment fragment = new CheckOTPFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .addToBackStack(CheckOTPFragment.class.getSimpleName());
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
            }else{
                Fragment fragment = new RegistrationTypeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .addToBackStack(RegistrationTypeFragment.class.getSimpleName());
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
            }



        }else
        {
            Fragment fragment = new RegistrationTypeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                    .addToBackStack(RegistrationTypeFragment.class.getSimpleName());
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }



    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
